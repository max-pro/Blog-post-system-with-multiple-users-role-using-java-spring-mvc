package com.max.spring;

import org.springframework.jdbc.core.*;
import javax.sql.*;

public class RegisterUser implements Register
{
    private JdbcTemplate jdbctemplate;
    private DataSource dataSource;
    private String username;
    private String password;
    private int lastId;
    
    @Override
    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbctemplate = new JdbcTemplate(this.dataSource);
    }
    
    @Override
    public String CreateAccount(final String username, final String password) {
        this.username = username;
        this.password = password;
        final String result = this.ValidateUser();
        if (result != "true") {
            return result;
        }
        if (!this.UserExist()) {
            this.CreateUser();
            this.AttachRole();
            return "true";
        }
        return "user already exist";
    }
    
    @Override
    public boolean CreateUser() {
        final String SQL = "insert into `users` (username,password) values(?,?)";
        final int result = this.jdbctemplate.update(SQL, new Object[] { this.username, this.password });
        if (result == 1) {
            this.lastId = this.jdbctemplate.queryForInt("SELECT id FROM `users` ORDER BY id DESC limit 1");
            return true;
        }
        return false;
    }
    
    @Override
    public boolean UserExist() {
        final String SQL = "select count(id) from `users` where username = '" + this.username + "'";
        final int rowCount = this.jdbctemplate.queryForInt(SQL);
        return rowCount > 0;
    }
    
    @Override
    public String ValidateUser() {
        final Validation validate = new Validation();
        if (!validate.isAlphaNumericOnly(this.username)) {
            return "invalid username found";
        }
        if (!validate.length(this.username, 3, 32)) {
            return "username must be greater than 3";
        }
        if (!validate.length(this.password, 8, 16)) {
            return "password must be greate than 8 and less than 16";
        }
        return "true";
    }
    
    @Override
    public boolean AttachRole() {
        final int user_role_id = 2;
        final String SQL = "insert into `users_roles` (users_id,roles_id) values(?,?)";
        final int result = this.jdbctemplate.update(SQL, new Object[] { this.lastId, user_role_id });
        return result == 1;
    }
}