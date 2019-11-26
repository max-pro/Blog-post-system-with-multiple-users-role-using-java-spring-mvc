package com.max.spring;

import org.springframework.jdbc.core.simple.*;
import java.sql.*;

public class UsersMapper implements ParameterizedRowMapper<Users>
{
    public Users mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Users users = new Users();
        users.setId(rs.getInt("id"));
        users.setUsername(rs.getString("username"));
        return users;
    }
}