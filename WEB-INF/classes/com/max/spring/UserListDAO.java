package com.max.spring;

import javax.sql.*;
import java.util.*;

public interface UserListDAO
{
    void setDataSource(final DataSource p0);
    
    List<Users> getAllUser();
    
    String ChangePassword(final String p0, final String p1, final String p2);
}