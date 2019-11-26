package com.max.spring;

public class Sections
{
    private int Id;
    private String name;
    private int users_id;
    
    public int getUsers_id() {
        return this.users_id;
    }
    
    public void setUsers_id(final int users_id) {
        this.users_id = users_id;
    }
    
    public int getId() {
        return this.Id;
    }
    
    public void setId(final int id) {
        this.Id = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
}