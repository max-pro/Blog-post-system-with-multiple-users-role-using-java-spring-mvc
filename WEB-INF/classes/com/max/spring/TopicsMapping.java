package com.max.spring;

import org.springframework.jdbc.core.simple.*;
import java.sql.*;

public class TopicsMapping implements ParameterizedRowMapper<Topics>
{
    public Topics mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Topics topics = new Topics();
        topics.setId(rs.getInt("id"));
        topics.setUsers_id(rs.getInt("users_id"));
        topics.setSections_name(rs.getString("sections"));
        topics.setText(rs.getString("text"));
        return topics;
    }
}