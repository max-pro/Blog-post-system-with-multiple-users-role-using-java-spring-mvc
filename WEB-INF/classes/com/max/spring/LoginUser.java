package com.max.spring;

import javax.sql.*;
import javax.servlet.http.*;
import org.springframework.jdbc.core.*;

public class LoginUser implements Login
{
    private JdbcTemplate jdbctemplate;
    private DataSource dataSource;
    private HttpSession sessions;
    private String username;
    private String password;
    private final String login_error = "invalid username or password found";
    
    @Override
    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbctemplate = new JdbcTemplate(this.dataSource);
    }
    
    @Override
    public String AttempLogin(final String username, final String password, final HttpSession session) {
        this.username = username;
        this.password = password;
        this.sessions = session;
        final String result = this.Validation();
        if (result != "true") {
            return result;
        }
        if (!this.CheckCredentials()) {
            return "invalid username or password found";
        }
        return "true";
    }
    
    @Override
    public boolean CheckCredentials() {
        try {
            final Users user = (Users)this.jdbctemplate.queryForObject("select users.id,roles_id,users.username,users.password from `users_roles` left join users ON users.id = users_roles.users_id where users.username = ? and users.password = ? limit 1", new Object[] { this.username, this.password }, (RowMapper)new UsersMapping());
            this.sessions.setAttribute("user", (Object)user);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public String Validation() {
        final Validation validate = new Validation();
        if (!validate.isAlphaNumericOnly(this.username) || !validate.length(this.username, 3, 32) || !validate.length(this.password, 8, 16)) {
            return "invalid username or password found";
        }
        return "true";
    }
}