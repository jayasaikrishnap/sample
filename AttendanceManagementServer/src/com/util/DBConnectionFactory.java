package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class DBConnectionFactory {
	
	static ResourceBundle bundle=ResourceBundle.getBundle("com.resource.resource");
	//driver loading...
	static
	{
		try {
			Class.forName(bundle.getString("driver"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public  static Connection getConnectionFactory()
	{
		try {
			return DriverManager.getConnection(bundle.getString("url"),bundle.getString("username"),bundle.getString("password"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public  static void closeConnection(Connection con,Statement st)
	{
		if(st!=null)
		{
			try {
				st.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(con!=null)
		{
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		}
	}
	public  static void closeConnection(Connection con,Statement st,ResultSet rs)
	{
		if(rs!=null)
		{
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			closeConnection(con, st);
		}
	}
}
