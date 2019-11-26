package com.max.spring;

import javax.sql.*;
import java.util.*;

public interface TopicsDAO
{
    void setDataSource(final DataSource p0);
    
    String create(final String p0, final int p1, final int p2);
    
    Topics getTopics(final int p0);
    
    List<Topics> getRecentTopics();
    
    List<Topics> getAllTopics(final int p0);
    
    boolean delete(final int p0, final int p1);
    
    boolean deleteChild(final int p0);
    
    String Validation();
}