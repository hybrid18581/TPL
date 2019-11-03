package com.rain.utility;

 
import org.apache.commons.dbcp2.BasicDataSource;
 
 
public class DataBaseUtility
{
    private static BasicDataSource dataSource;
 
    public static BasicDataSource getDataSource()
    {
 
        if (dataSource == null)
        {
            BasicDataSource ds = new BasicDataSource();
            ds.setDriverClassName("org.postgresql.Driver");
            ds.setUrl("jdbc:postgresql://localhost:5432/TPL");
            ds.setUsername("postgres");
            ds.setPassword("pass");   
 
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
 
            dataSource = ds;
        }
        return dataSource;
    }
 

}