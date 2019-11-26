package com.max.spring;

import javax.sql.*;
import org.springframework.jdbc.core.*;
import org.springframework.dao.*;
import java.util.*;

public class TopicsJDBC implements TopicsDAO
{
    private String text;
    private JdbcTemplate jdbctemplate;
    private DataSource dataSource;
    
    @Override
    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbctemplate = new JdbcTemplate(this.dataSource);
    }
    
    @Override
    public String create(final String name, final int sections_id, final int users_id) {
        this.text = name;
        final String Vresult = this.Validation();
        if (Vresult != "true") {
            return Vresult;
        }
        final String SQL = "insert into topics(`users_id`,`sections_id`,`text`) values(?,?,?)";
        final int result = this.jdbctemplate.update(SQL, new Object[] { users_id, sections_id, name });
        if (result == 1) {
            return "true";
        }
        return "error while creating topic";
    }
    
    @Override
    public Topics getTopics(final int id) {
        try {
            final String SQL = "select id,text from `topics` where id = ? limit 1";
            final Topics topic = (Topics)this.jdbctemplate.queryForObject(SQL, new Object[] { id }, (RowMapper)new TopicsMapping2());
            return topic;
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    @Override
    public List<Topics> getRecentTopics() {
        final String SQL = "SELECT topics.users_id as users_id,topics.id as id,sections.name as sections,topics.text as text FROM `topics`left join `sections` on sections.id = topics.sections_id order by topics.id DESC limit 7";
        final List<Topics> topics = (List<Topics>)this.jdbctemplate.query(SQL, (RowMapper)new TopicsMapping());
        return topics;
    }
    
    @Override
    public List<Topics> getAllTopics(final int section_id) {
        final String SQL = "SELECT topics.users_id as users_id,topics.id as id,sections.name as sections,topics.text as text FROM `topics`left join `sections` on sections.id = topics.sections_id where sections_id = ?";
        final List<Topics> topics = (List<Topics>)this.jdbctemplate.query(SQL, new Object[] { section_id }, (RowMapper)new TopicsMapping());
        return topics;
    }
    
    @Override
    public boolean delete(final int id, final int section_id) {
        if (this.deleteChild(id)) {
            System.out.println("delete from `topics` where id = " + id + " and sections_id = " + section_id);
            final String SQL = "delete from `topics` where id = ? and sections_id = ?";
            final int result = this.jdbctemplate.update(SQL, new Object[] { id, section_id });
            if (result == 1) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String Validation() {
        final Validation validation = new Validation();
        if (!validation.length(this.text, 1, 500)) {
            return "topic length must be greater than 1 and less than 500";
        }
        if (!validation.isAlphaNumericWithSpace(this.text)) {
            return "invalid topic text found";
        }
        return "true";
    }
    
    @Override
    public boolean deleteChild(final int id) {
        final String SQL = "delete from `posts` where topics_id = ?";
        final int result = this.jdbctemplate.update(SQL, new Object[] { id });
        return result == 1;
    }
}