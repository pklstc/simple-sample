package com.capgemini.capgenie.service.dataexport.dataexport.util;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.SQLException; 

public class SQLConnection {
	    public static final String url = "##";  
	    public static final String name = "com.mysql.jdbc.Driver";  
	    public static final String user = "#";  
	    public static final String password = "##";  
	  
	    public Connection conn = null;  
	    public PreparedStatement pst = null;  
	  
	    public SQLConnection(String sql) {  
	        try {  
	            Class.forName(name);
	            conn = DriverManager.getConnection(url, user, password);
	            pst = conn.prepareStatement(sql);
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    public void close() {  
	        try {  
	            this.conn.close();  
	            this.pst.close();  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	}  

