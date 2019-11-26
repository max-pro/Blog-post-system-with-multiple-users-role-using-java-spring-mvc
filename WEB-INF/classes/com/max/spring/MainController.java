package com.max.spring;

import org.springframework.stereotype.*;
import javax.servlet.http.*;
import org.springframework.web.servlet.*;
import org.springframework.context.support.*;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController
{
    private HttpSession session;
    private Users user;
    
    private boolean CheckUserisLogin() {
        this.user = (Users)this.session.getAttribute("user");
        return this.user != null;
    }
    
    private boolean isUserAdmin() {
        return this.user.getRole() == 1;
    }
    
    @RequestMapping(value = { "/changepassword" }, method = { RequestMethod.POST })
    public ModelAndView ChangePasswordPost(final HttpServletRequest request, final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        final String username = request.getParameter("username");
        final String old_password = request.getParameter("old_password");
        final String new_password = request.getParameter("new_password");
        System.out.println("useername : " + username);
        final ModelAndView mv = new ModelAndView("profile");
        mv.addObject("user", (Object)this.user);
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final UserList user_list = (UserList)context.getBean("userList");
        final String result = user_list.ChangePassword(username, old_password, new_password);
        context.close();
        if (result != "true") {
            mv.addObject("error", (Object)result);
        }
        else {
            mv.addObject("success", (Object)"successfully password changed");
        }
        return mv;
    }
    
    @RequestMapping(value = { "/changepassword" }, method = { RequestMethod.GET })
    public ModelAndView ChangePassword(final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        final ModelAndView mv = new ModelAndView("profile");
        mv.addObject("user", (Object)this.user);
        return mv;
    }
    
    @RequestMapping(value = { "/users" }, method = { RequestMethod.GET })
    public ModelAndView UserLists(final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        if (!this.isUserAdmin()) {
            final ModelAndView mv = new ModelAndView("error");
            mv.addObject("error", (Object)"User don't have permission to access these page");
            return mv;
        }
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final UserList user_list = (UserList)context.getBean("userList");
        final List<Users> users_list = (List<Users>)user_list.getAllUser();
        context.close();
        final ModelAndView mv2 = new ModelAndView("Userlist");
        mv2.addObject("users_list", (Object)users_list);
        return mv2;
    }
    
    @RequestMapping(value = { "/" }, method = { RequestMethod.GET })
    public ModelAndView home(final HttpServletRequest request, final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final TopicsJDBC topics = (TopicsJDBC)context.getBean("TopicsJDBC");
        final List<Topics> getRecentTopics = (List<Topics>)topics.getRecentTopics();
        final SectionsJDBC sections = (SectionsJDBC)context.getBean("SectionsJDBC");
        final List<Sections> getAllSections = (List<Sections>)sections.getAllSections();
        context.close();
        final ModelAndView mv = new ModelAndView("home");
        mv.addObject("sections", (Object)getAllSections);
        mv.addObject("topics", (Object)getRecentTopics);
        mv.addObject("user", (Object)this.user);
        return mv;
    }
    
    @RequestMapping(value = { "/login" }, method = { RequestMethod.GET })
    public ModelAndView login(final HttpServletRequest request, final HttpSession session) {
        this.session = session;
        if (this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/");
        }
        final ModelAndView mv = new ModelAndView("loginForm");
        if (request.getParameter("message") != null) {
            mv.addObject("message", (Object)request.getParameter("message"));
        }
        return mv;
    }
    
    @RequestMapping(value = { "/login" }, method = { RequestMethod.POST })
    public ModelAndView loginPost(final HttpServletRequest request, final HttpSession session) {
        this.session = session;
        if (this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/");
        }
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final LoginUser User = (LoginUser)context.getBean("loginUser");
        final String result = User.AttempLogin(username, password, session);
        context.close();
        System.out.println(result);
        if (result != "true") {
            final ModelAndView mv = new ModelAndView("loginForm");
            mv.addObject("error", (Object)result);
            return mv;
        }
        return new ModelAndView("redirect:/");
    }
    
    @RequestMapping(value = { "/logout" }, method = { RequestMethod.POST })
    public ModelAndView logout(final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        try {
            final Users user = (Users)session.getAttribute("user");
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
            System.out.println(user.getRole());
            session.removeAttribute("user");
            return new ModelAndView("redirect:/login");
        }
        catch (NullPointerException e) {
            return new ModelAndView("redirect:/login");
        }
    }
    
    @RequestMapping(value = { "/register" }, method = { RequestMethod.GET })
    public ModelAndView register(final HttpSession session) {
        this.session = session;
        if (this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("registerForm");
    }
    
    @RequestMapping(value = { "/register" }, method = { RequestMethod.POST })
    public ModelAndView registerPost(final HttpServletRequest request, final HttpSession session) {
        this.session = session;
        if (this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/");
        }
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final RegisterUser User = (RegisterUser)context.getBean("registerUser");
        final String result = User.CreateAccount(username, password);
        System.out.println(result);
        context.close();
        if (result != "true") {
            final ModelAndView mv = new ModelAndView("registerForm");
            mv.addObject("error", (Object)result);
            return mv;
        }
        return new ModelAndView("redirect:/login?message=successfully user register");
    }
    
    @RequestMapping(value = { "/sections" }, method = { RequestMethod.GET })
    public ModelAndView sections(final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final SectionsJDBC sections = (SectionsJDBC)context.getBean("SectionsJDBC");
        final List<Sections> getAllSections = (List<Sections>)sections.getAllSections();
        context.close();
        final ModelAndView mv = new ModelAndView("sections");
        mv.addObject("sections", (Object)getAllSections);
        return mv;
    }
    
    @RequestMapping(value = { "/sections" }, method = { RequestMethod.POST })
    public ModelAndView sectionsPost(final HttpServletRequest request, final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        final String section_name = request.getParameter("section_name");
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final SectionsJDBC sections = (SectionsJDBC)context.getBean("SectionsJDBC");
        final String result = sections.create(section_name, this.user.getId());
        final List<Sections> getAllSections = (List<Sections>)sections.getAllSections();
        final ModelAndView mv = new ModelAndView("sections");
        mv.addObject("sections", (Object)getAllSections);
        if (result == "true") {
            mv.addObject("recentAdd", (Object)section_name);
            mv.addObject("success", (Object)"successfully section created");
        }
        else {
            mv.addObject("error", (Object)result);
        }
        context.close();
        return mv;
    }
    
    @RequestMapping(value = { "/sectionsDelete" }, method = { RequestMethod.POST })
    public ModelAndView sectionsDelete(final HttpServletRequest request, final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        final int delete_id = Integer.parseInt(request.getParameter("delete_id"));
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final SectionsJDBC sections = (SectionsJDBC)context.getBean("SectionsJDBC");
        sections.delete(delete_id);
        context.close();
        return new ModelAndView("redirect:/sections");
    }
    
    @RequestMapping(value = { "/topics/{topic_id}" }, method = { RequestMethod.GET })
    public ModelAndView topics(@PathVariable("topic_id") final int id, final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final TopicsJDBC topics = (TopicsJDBC)context.getBean("TopicsJDBC");
        final List<Topics> getAllTopics = (List<Topics>)topics.getAllTopics(id);
        context.close();
        final ModelAndView mv = new ModelAndView("topics");
        mv.addObject("topics", (Object)getAllTopics);
        mv.addObject("topics_id", (Object)id);
        return mv;
    }
    
    @RequestMapping(value = { "/topics/{topic_id}" }, method = { RequestMethod.POST })
    public ModelAndView topicsPost(@PathVariable("topic_id") final int id, final HttpServletRequest request, final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        final String topic_name = request.getParameter("topic_name");
        System.out.println("id : " + id);
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final TopicsJDBC topics = (TopicsJDBC)context.getBean("TopicsJDBC");
        final String result = topics.create(topic_name, id, this.user.getId());
        final List<Topics> getAllTopics = (List<Topics>)topics.getAllTopics(id);
        final ModelAndView mv = new ModelAndView("topics");
        mv.addObject("topics_id", (Object)id);
        mv.addObject("topics", (Object)getAllTopics);
        if (result == "true") {
            mv.addObject("recentAdd", (Object)topic_name);
            mv.addObject("success", (Object)"successfully topic created");
        }
        else {
            mv.addObject("error", (Object)result);
        }
        context.close();
        return mv;
    }
    
    @RequestMapping(value = { "/topics/topicsDelete/{topic_id}" }, method = { RequestMethod.GET })
    public ModelAndView topicsDelete2(@PathVariable("topic_id") final int id, final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("redirect:/topics/" + id);
    }
    
    @RequestMapping(value = { "/topics/topicsDelete/{topic_id}" }, method = { RequestMethod.POST })
    public ModelAndView topicsDelete(@PathVariable("topic_id") final int id, final HttpServletRequest request, final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        final int topic_id = Integer.parseInt(request.getParameter("delete_id"));
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final TopicsJDBC topics = (TopicsJDBC)context.getBean("TopicsJDBC");
        final boolean result = topics.delete(topic_id, id);
        final List<Topics> getAllTopics = (List<Topics>)topics.getAllTopics(id);
        final ModelAndView mv = new ModelAndView("topics");
        mv.addObject("topics", (Object)getAllTopics);
        if (result) {
            mv.addObject("success", (Object)"successfully topic deleted");
        }
        else {
            mv.addObject("error", (Object)"failed to delete topic");
        }
        context.close();
        return mv;
    }
    
    @RequestMapping(value = { "/posts/{topic_id}" }, method = { RequestMethod.GET })
    public ModelAndView posts(@PathVariable("topic_id") final int id, final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final TopicsJDBC topics = (TopicsJDBC)context.getBean("TopicsJDBC");
        final PostsJDBC post = (PostsJDBC)context.getBean("PostsJDBC");
        final Topics topicData = topics.getTopics(id);
        final List<Posts> postData = (List<Posts>)post.getAllPosts(id);
        context.close();
        final ModelAndView mv = new ModelAndView("post");
        if (topicData != null) {
            mv.addObject("topic", (Object)topicData.getText());
            mv.addObject("id", (Object)id);
            mv.addObject("posts", (Object)postData);
            return mv;
        }
        final ModelAndView mv2 = new ModelAndView("error");
        mv.addObject("error", (Object)"Invalid Url found");
        return mv2;
    }
    
    @RequestMapping(value = { "/posts/{topic_id}" }, method = { RequestMethod.POST })
    public ModelAndView postsCreate(@PathVariable("topic_id") final int id, final HttpServletRequest request, final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        final String post_txt = request.getParameter("post_txt");
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final TopicsJDBC topics = (TopicsJDBC)context.getBean("TopicsJDBC");
        final PostsJDBC post = (PostsJDBC)context.getBean("PostsJDBC");
        final String result = post.create(id, post_txt, this.user.getId());
        final Topics topicData = topics.getTopics(id);
        final List<Posts> postData = (List<Posts>)post.getAllPosts(id);
        context.close();
        final ModelAndiew mv = new ModelAndView("post");
        if (topicData == null) {
            final ModelAndView mv2 = new ModelAndView("error");
            mv.addObject("error", (Object)"Invalid Url found");
            return mv2;
        }
        mv.addObject("topic", (Object)topicData.getText());
        mv.addObject("posts", (Object)postData);
        mv.addObject("id", (Object)id);
        if (result != "true") {
            mv.addObject("error", (Object)result);
            return mv;
        }
        mv.addObject("success", (Object)"successfully post created");
        return mv;
    }
    
    @RequestMapping(value = { "/posts/postDelete/{id}" }, method = { RequestMethod.GET })
    public ModelAndView postsDelete2(@PathVariable("id") final int id, final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("redirect:/posts/" + id);
    }
    
    @RequestMapping(value = { "/posts/postUpdate/{id}" }, method = { RequestMethod.GET })
    public ModelAndView postsUpdate2(@PathVariable("id") final int id, final HttpServletRequest request, final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("redirect:/posts/" + id);
    }
    
    @RequestMapping(value = { "/posts/postUpdate/{id}" }, method = { RequestMethod.POST })
    public ModelAndView postsUpdate(@PathVariable("id") final int id, final HttpServletRequest request, final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        final int post_id = Integer.parseInt(request.getParameter("post_id"));
        final String post_txt = request.getParameter("post_update_txt");
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final TopicsJDBC topics = (TopicsJDBC)context.getBean("TopicsJDBC");
        final PostsJDBC post = (PostsJDBC)context.getBean("PostsJDBC");
        final String result = post.update(post_id, id, post_txt);
        final Topics topicData = topics.getTopics(id);
        final List<Posts> postData = (List<Posts>)post.getAllPosts(id);
        context.close();
        final ModelAndView mv = new ModelAndView("post");
        if (topicData == null) {
            final ModelAndView mv2 = new ModelAndView("error");
            mv.addObject("error", (Object)"Invalid Url found");
            return mv2;
        }
        mv.addObject("topic", (Object)topicData.getText());
        mv.addObject("posts", (Object)postData);
        mv.addObject("id", (Object)id);
        if (result != "true") {
            mv.addObject("error", (Object)result);
            return mv;
        }
        mv.addObject("success", (Object)"successfully post updated");
        return mv;
    }
    
    @RequestMapping(value = { "/posts/postDelete/{id}" }, method = { RequestMethod.POST })
    public ModelAndView postsDelete(@PathVariable("id") final int id, final HttpServletRequest request, final HttpSession session) {
        this.session = session;
        if (!this.CheckUserisLogin()) {
            return new ModelAndView("redirect:/login");
        }
        final int post_id = Integer.parseInt(request.getParameter("delete_id"));
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final TopicsJDBC topics = (TopicsJDBC)context.getBean("TopicsJDBC");
        final PostsJDBC post = (PostsJDBC)context.getBean("PostsJDBC");
        final boolean result = post.delete(post_id);
        final Topics topicData = topics.getTopics(id);
        final List<Posts> postData = (List<Posts>)post.getAllPosts(id);
        context.close();
        final ModelAndView mv = new ModelAndView("post");
        if (result) {
            mv.addObject("success", (Object)"successfully post deleted");
        }
        else {
            mv.addObject("error", (Object)"failed to delete post");
        }
        if (topicData != null) {
            mv.addObject("topic", (Object)topicData.getText());
            mv.addObject("id", (Object)id);
            mv.addObject("posts", (Object)postData);
            return mv;
        }
        final ModelAndView mv2 = new ModelAndView("error");
        mv.addObject("error", (Object)"Invalid Url found");
        return mv2;
    }
    
    @RequestMapping({ "error" })
    public ModelAndView error404() {
        final ModelAndView mv = new ModelAndView("error");
        mv.addObject("error", (Object)"Invalid Url Found");
        return mv;
    }
}