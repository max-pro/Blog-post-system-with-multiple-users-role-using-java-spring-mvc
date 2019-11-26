package com.max.spring;

import org.springframework.jdbc.core.simple.*;
import java.sql.*;

public class TopicsMapping2 implements ParameterizedRowMapper<Topics>
{
    public Topics mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Topics topics = new Topics();
        topics.setId(rs.getInt("id"));
        topics.setText(rs.getString("text"));
        return topics;
    }
}