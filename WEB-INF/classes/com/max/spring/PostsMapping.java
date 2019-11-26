package com.max.spring;

import org.springframework.jdbc.core.simple.*;
import java.sql.*;

public class PostsMapping implements ParameterizedRowMapper<Posts>
{
    public Posts mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Posts posts = new Posts();
        posts.setId(rs.getInt("id"));
        posts.setUsers_id(rs.getInt("users_id"));
        posts.setText(rs.getString("text"));
        posts.setUsername(rs.getString("username"));
        return posts;
    }
}