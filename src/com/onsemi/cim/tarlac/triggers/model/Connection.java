package com.onsemi.cim.tarlac.triggers.model;

public class Connection {

	protected String userName;
	protected String password;
	protected String hostName;
	protected String port;
	protected String sid;
	protected String type;
	public static final String SQLServer = "SQLServer";
	public static final String Postgres = "Postgres";
	public static final String Oracle = "Oracle";
	
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public String getHostName() {
		return hostName;
	}
	public String getPort() {
		return port;
	}
	public String getSid() {
		return sid;
	}
	public String getType() {
		return type;
	}
	
}
