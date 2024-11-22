package com.onsemi.cim.tarlac.triggers.dbconnections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MSSql {
	
	public String Driver;
	public String Url;
	public Connection Conn;
	public Statement Stmt;
	
	public MSSql() {
		
	}
		
	public MSSql(String host, String database, String user, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		Url = "jdbc:sqlserver://" + host + ";DatabaseName=" + database;
		Driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		Class.forName(Driver).newInstance();
		Conn = DriverManager.getConnection(Url, user, password);
		Stmt = Conn.createStatement(
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);	

	}

}
