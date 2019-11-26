package com.max.spring;

import org.springframework.jdbc.core.simple.*;
import java.sql.*;

public class SectionsMapping implements ParameterizedRowMapper<Sections>
{
    public Sections mapRow(final ResultSet rs, final int rowNum) throws SQLException {
        final Sections sections = new Sections();
        sections.setId(rs.getInt("id"));
        sections.setName(rs.getString("name"));
        return sections;
    }
}