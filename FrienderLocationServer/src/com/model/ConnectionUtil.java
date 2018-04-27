package com.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ConnectionUtil {
	private static Connection con;
	
	public static Connection getConnection()
	{
		ResourceBundle rb=ResourceBundle.getBundle("com\\model\\database");
		try {
			System.out.println(rb.getString("driverclass"));
			Class.forName(rb.getString("driverclass"));
			con=DriverManager.getConnection(rb.getString("url"),rb.getString("username"),rb.getString("password"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	
	}
	static public void connectionClose(Connection con)
	{
		try {
			if(con!=null)
				con.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	static public void connectionClose(Connection con,Statement st)
	{
		connectionClose(con);
		try {
			if(st!=null)
				st.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
	static public void connectionClose(Connection con,Statement st,ResultSet rs)
	
	{
		connectionClose(con, st);
		try {
			if(rs!=null)
				rs.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

}
