package com.max.spring;

import javax.sql.*;
import java.util.*;
import org.springframework.jdbc.core.*;

public class PostsJDBC implements PostsDAO
{
    private JdbcTemplate jdbctemplate;
    private DataSource dataSource;
    private String text;
    
    @Override
    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbctemplate = new JdbcTemplate(this.dataSource);
    }
    
    @Override
    public String create(final int topic_id, final String name, final int users_id) {
        System.out.println("name  : " + name);
        this.text = name;
        final String Vresult = this.Validation();
        if (Vresult != "true") {
            return Vresult;
        }
        final String SQL = "insert into posts(`users_id`,`topics_id`,`text`) values(?,?,?)";
        final int result = this.jdbctemplate.update(SQL, new Object[] { users_id, topic_id, name });
        if (result == 1) {
            return "true";
        }
        return "error while creating post";
    }
    
    @Override
    public List<Posts> getAllPosts(final int topic_id) {
        final String SQL = "select posts.id as id,posts.users_id,posts.text,users.username from `posts` left join users on users.id = posts.users_id where topics_id = ?";
        final List<Posts> post = (List<Posts>)this.jdbctemplate.query(SQL, new Object[] { topic_id }, (RowMapper)new PostsMapping());
        return post;
    }
    
    @Override
    public boolean delete(final int id) {
        final String SQL = "delete from `posts` where id = ?";
        final int result = this.jdbctemplate.update(SQL, new Object[] { id });
        return result == 1;
    }
    
    @Override
    public String Validation() {
        final Validation validation = new Validation();
        if (!validation.length(this.text, 1, 500)) {
            return "post length must be greater than 1 and less than 500";
        }
        if (!validation.isAlphaNumericWithSpace(this.text)) {
            return "invalid post text found";
        }
        return "true";
    }
    
    @Override
    public String update(final int id, final int topic_id, final String post_txt) {
        this.text = post_txt;
        final String Vresult = this.Validation();
        if (Vresult != "true") {
            return Vresult;
        }
        System.out.println("update posts set text = " + post_txt + " where id = " + id + " and topics_id = " + topic_id);
        final String SQL = "update posts set text = ? where id = ? and topics_id = ?";
        final int result = this.jdbctemplate.update(SQL, new Object[] { post_txt, id, topic_id });
        if (result == 1) {
            return "true";
        }
        return "failed to update post";
    }
}