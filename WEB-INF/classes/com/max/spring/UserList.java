package com.max.spring;

import javax.sql.*;
import java.util.*;
import org.springframework.jdbc.core.*;
import org.springframework.dao.*;

public class UserList implements UserListDAO
{
    private JdbcTemplate jdbctemplate;
    private DataSource dataSource;
    
    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbctemplate = new JdbcTemplate(this.dataSource);
    }
    
    public List<Users> getAllUser() {
        final String SQL = "select id,username from users";
        final List<Users> users = (List<Users>)this.jdbctemplate.query(SQL, (RowMapper)new UsersMapper());
        return users;
    }
    
    public String ChangePassword(final String username, final String old_password, final String new_password) {
        try {
            System.out.println("select password from users where username = " + username + " limit 1");
            String SQL = "select password from users where username = ? limit 1";
            final Users user = (Users)this.jdbctemplate.queryForObject(SQL, new Object[] { username }, (RowMapper)new UsersMapper2());
            if (user.getPassword() == old_password) {
                return "invalid Password found";
            }
            SQL = "update users set password = ? where username = ?";
            final int result = this.jdbctemplate.update(SQL, new Object[] { new_password, username });
            if (result == 1) {
                return "true";
            }
            return "failed to change password";
        }
        catch (EmptyResultDataAccessException e) {
            return "invalid user found";
        }
    }
}