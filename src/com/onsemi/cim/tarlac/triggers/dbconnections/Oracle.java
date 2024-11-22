package com.onsemi.cim.tarlac.triggers.dbconnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Oracle {

	public String Driver;
	public String Url;
	public Connection Conn;
	public Statement Stmt;
	
	public Oracle(String database, String user, String password) throws 
																SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		Url = "jdbc:oracle:thin:@" + database;
		Driver = "oracle.jdbc.driver.OracleDriver";
		Class.forName(Driver).newInstance();
		Conn = DriverManager.getConnection(Url, user, password);
		Stmt = Conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);	
	}	
}
