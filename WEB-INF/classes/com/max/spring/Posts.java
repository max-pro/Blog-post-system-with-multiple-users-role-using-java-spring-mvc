package com.max.spring;

public class Posts
{
    private int id;
    private int users_id;
    private String username;
    private int topics_id;
    private String text;
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public int getUsers_id() {
        return this.users_id;
    }
    
    public void setUsers_id(final int users_id) {
        this.users_id = users_id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public int getTopics_id() {
        return this.topics_id;
    }
    
    public void setTopics_id(final int topics_id) {
        this.topics_id = topics_id;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
}