package com.onsemi.cim.tarlac.triggers.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import org.apache.log4j.Logger;

public class Database  extends com.onsemi.cim.tarlac.triggers.model.Connection {

	private Connection connection = null;
	private final static Logger logger = Logger.getLogger(Database.class);
	
	// Default constructor. It connects you to PRISM Database Test Environment
	public Database () {
		this.hostName = "";
		this.userName = "";
		this.password = "";
		this.port = "";
		this.sid = "";
	}
	
	// Allows you to define the database credentials during object creation.
	public Database (String hostName, String userName, String password, String port, String sid,String type) {
		this.hostName = hostName;
		this.userName = userName;
		this.password = password;
		this.port = port;
		this.sid = sid;
		this.type = type;
	}
	
	public boolean isConnected () {
		boolean status = false;
		
		try {
			if (this.type.equals(Database.SQLServer)) {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				String url = "jdbc:sqlserver://" + this.hostName + ":" + this.port + ";databaseName=" + this.sid;
                this.connection = DriverManager.getConnection(url, this.userName, this.password);
			}
			else if (this.type.equals(Database.Postgres)) {
				Class.forName("org.postgresql.Driver");
				String url ="jdbc:postgresql://" + this.hostName + ":" + this.port + "/" + this.sid;
            	this.connection = DriverManager.getConnection(url, this.userName, this.password); 
            }
            else if (this.type.equals(Database.Oracle)) {
            	this.connection = DriverManager.getConnection("jdbc:oracle:thin:@" + this.hostName + ":" + this.port + ":" + this.sid, this.userName, this.password);	 
            }
		} catch (SQLException ex) {
			logger.error("Exeption : ", ex);
		} catch (ClassNotFoundException ex) {
			logger.error("Exeption : ", ex);
		}
		
		if (this.connection != null) {
			status = true;
		} 
		
		return status;
	}
	
	public boolean insertRecord (String tableName, String values, String columns) {
		boolean status = false;
		Statement statement = null;
		try {
			statement = this.connection.createStatement();
			logger.info("Inserting into the database : INSERT INTO "+ tableName + " (" + columns + ") VALUES (" + values + ")");
			statement.executeUpdate("INSERT INTO "+ tableName + " (" + columns + ") VALUES (" + values + ")");
			status = true;
		} catch (SQLIntegrityConstraintViolationException ex) {
			logger.error("Exeption : Record exists");
		}
		catch (SQLException ex) {
			logger.error("Exeption : ", ex);
		} finally {
			try {
				statement.close();
			} catch (SQLException ex) {
				logger.error("Exeption : ", ex);
			}
		}
		return status;
	}
	
	public boolean updateRecord (String tableName, String value, String condition) {
		boolean status = false;
		Statement statement = null;
		try {
			statement = this.connection.createStatement();
			logger.info("Updating database record : UPDATE "+ tableName + " SET " + value + " WHERE " + condition);
			statement.execute("UPDATE "+ tableName + " SET " + value + " WHERE " + condition);
			status = true;
		} catch (SQLIntegrityConstraintViolationException ex) {
			logger.error("Exeption : Record exists");
		}
		catch (SQLException ex) {
			logger.error("Exeption : ", ex);
		} finally {
			try {
				statement.close();
			} catch (SQLException ex) {
				logger.error("Exeption : ", ex);
			}
		}
		return status;
	}
	
	
}
