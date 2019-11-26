package com.max.spring;

import org.springframework.jdbc.core.simple.*;
import java.sql.*;

public class UsersMapping implements ParameterizedRowMapper<Users>
{
    public Users mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Users users = new Users();
        users.setId(rs.getInt("id"));
        users.setRole(rs.getInt("roles_id"));
        users.setUsername(rs.getString("username"));
        users.setPassword(rs.getString("password"));
        return users;
    }
}