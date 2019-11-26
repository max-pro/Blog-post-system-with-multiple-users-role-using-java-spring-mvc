package com.max.spring;

import javax.sql.*;
import javax.servlet.http.*;

public interface Login
{
    boolean CheckCredentials();
    
    String Validation();
    
    void setDataSource(final DataSource p0);
    
    String AttempLogin(final String p0, final String p1, final HttpSession p2);
}