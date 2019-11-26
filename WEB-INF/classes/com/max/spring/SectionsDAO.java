package com.max.spring;

import javax.sql.*;
import java.util.*;

public interface SectionsDAO
{
    void setDataSource(final DataSource p0);
    
    String create(final String p0, final int p1);
    
    List<Sections> getAllSections();
    
    boolean delete(final int p0);
    
    String Validation();
    
    boolean isSectionExist();
}