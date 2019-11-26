package com.max.spring;

import javax.sql.*;

public interface Register
{
    String CreateAccount(final String p0, final String p1);
    
    boolean CreateUser();
    
    boolean UserExist();
    
    String ValidateUser();
    
    boolean AttachRole();
    
    void setDataSource(final DataSource p0);
}