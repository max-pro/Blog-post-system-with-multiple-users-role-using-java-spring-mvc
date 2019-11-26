package com.max.spring;

import javax.sql.*;
import org.springframework.jdbc.core.*;
import java.util.*;
import org.springframework.dao.*;

public class SectionsJDBC implements SectionsDAO
{
    private String name;
    private JdbcTemplate jdbctemplate;
    private DataSource dataSource;
    
    @Override
    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbctemplate = new JdbcTemplate(this.dataSource);
    }
    
    @Override
    public String create(final String name, final int users_id) {
        this.name = name;
        final String Vresult = this.Validation();
        if (Vresult != "true") {
            return Vresult;
        }
        if (this.isSectionExist()) {
            return "Section already Exist";
        }
        final String SQL = "insert into sections(`users_id`,`name`) values(?,?)";
        final int result = this.jdbctemplate.update(SQL, new Object[] { users_id, name });
        if (result == 1) {
            return "true";
        }
        return "error while creating section";
    }
    
    @Override
    public List<Sections> getAllSections() {
        final String SQL = "select id,name from `sections`";
        final List<Sections> sections = (List<Sections>)this.jdbctemplate.query(SQL, (RowMapper)new SectionsMapping());
        return sections;
    }
    
    @Override
    public boolean delete(final int id) {
        try {
            String SQL = "select id,text from topics where sections_id = ?";
            final List<Topics> topics = (List<Topics>)this.jdbctemplate.query(SQL, new Object[] { id }, (RowMapper)new TopicsMapping2());
            for (final Topics topic : topics) {
                SQL = "delete from `posts` where topics_id = ?";
                this.jdbctemplate.update(SQL, new Object[] { topic.getId() });
            }
            SQL = "delete from `topics` where sections_id = ?";
            this.jdbctemplate.update(SQL, new Object[] { id });
            SQL = "delete from `sections` where id = ?";
            this.jdbctemplate.update(SQL, new Object[] { id });
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public String Validation() {
        final Validation validation = new Validation();
        if (!validation.length(this.name, 1, 500)) {
            return "section length must be greater than 1 and less than 500";
        }
        if (!validation.isAlphaNumericWithSpace(this.name)) {
            return "invalid section name found";
        }
        return "true";
    }
    
    @Override
    public boolean isSectionExist() {
        try {
            final String SQL = "select id from sections where name = ? limit 1";
            final int rowCount = this.jdbctemplate.queryForInt(SQL, new Object[] { this.name });
            return true;
        }
        catch (EmptyResultDataAccessException e) {
            return false;
        }
    }
}