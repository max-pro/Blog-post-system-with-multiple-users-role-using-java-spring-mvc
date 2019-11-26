package com.max.spring;

public class Topics
{
    private int id;
    private int users_id;
    private String sections_name;
    private String text;
    private int PostCount;
    
    public int getPostCount() {
        return this.PostCount;
    }
    
    public int getUsers_id() {
        return this.users_id;
    }
    
    public void setUsers_id(final int users_id) {
        this.users_id = users_id;
    }
    
    public void setPostCount(final int postCount) {
        this.PostCount = postCount;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getSections_name() {
        return this.sections_name;
    }
    
    public void setSections_name(final String sections_name) {
        this.sections_name = sections_name;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getText() {
        return this.text;
    }
    
    public void setText(final String text) {
        this.text = text;
    }
}