package com.max.spring;

import javax.sql.*;
import java.util.*;

public interface PostsDAO
{
    void setDataSource(final DataSource p0);
    
    String create(final int p0, final String p1, final int p2);
    
    List<Posts> getAllPosts(final int p0);
    
    String update(final int p0, final int p1, final String p2);
    
    boolean delete(final int p0);
    
    String Validation();
}