package com.max.spring;

public class Users
{
    private int id;
    private String username;
    private String password;
    private int role;
    
    public int getRole() {
        return this.role;
    }
    
    public void setRole(final int role) {
        this.role = role;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
}