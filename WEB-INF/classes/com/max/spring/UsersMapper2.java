package com.max.spring;

import org.springframework.jdbc.core.simple.*;
import java.sql.*;

public class UsersMapper2 implements ParameterizedRowMapper<Users>
{
    public Users mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Users users = new Users();
        users.setUsername(rs.getString("password"));
        return users;
    }
}