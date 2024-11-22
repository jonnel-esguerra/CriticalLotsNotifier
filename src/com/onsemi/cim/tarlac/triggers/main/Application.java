package com.onsemi.cim.tarlac.triggers.main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.onsemi.cim.tarlac.triggers.services.AppUtils;
import com.onsemi.cim.tarlac.triggers.services.DateTimeUtils;
import com.onsemi.cim.tarlac.triggers.main.Application;
import com.onsemi.cim.tarlac.triggers.model.ToldLotsInfo;
import com.onsemi.cim.tarlac.triggers.services.EmailManager;
import com.onsemi.cim.tarlac.triggers.dbconnections.Oracle;

public class Application {
	
	private final static Logger logger = Logger.getLogger(Application.class);
	//private final static Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		try {
			AppUtils.loadProperties();
		} catch (Exception ex) {
			logger.error("Exeption : ", ex);
		}
			
		logger.info("<==== Application Initializing ====>");
		createTriggerFiles(AppUtils.getProperty("PRDHost"), AppUtils.getProperty("TriggerPath"));
		logger.info("<==== Application Terminating ====>");

	}
	
	static void createTriggerFiles(String app, String path) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		Date currdatetime = new Date();
		ResultSet rs = null;	
		ArrayList<ToldLotsInfo> record = new ArrayList();
		try {
			logger.info("Rule Status: " + AppUtils.getProperty("RuleStatus"));
			logger.info("<==== Connecting to " + app + " DB ====>");
				
			Oracle OraConn = new Oracle(AppUtils.getProperty("PRDHost"), 
										AppUtils.getProperty("PRDUserName"), 
										AppUtils.getProperty("PRDPassword"));
				
			logger.info("<==== Connected to " + app + " DB ====>");
			logger.info("Query Statement: " + AppUtils.getProperty("PRDQuery") + " " + AppUtils.getProperty("LastRunDateTime"));
			PreparedStatement preparedSelect = OraConn.Conn.prepareStatement(AppUtils.getProperty("PRDQuery"));
			preparedSelect.setString(1, AppUtils.getProperty("LastRunDateTime"));
			rs = preparedSelect.executeQuery();
			//rs.first();			
			if (rs.next() != false) {
				try {
					do {
						ToldLotsInfo toldLotsInfo = new ToldLotsInfo();
						toldLotsInfo.setStepName(rs.getString("StepName"));	
						toldLotsInfo.setLotNo(rs.getString("LotNo"));
						toldLotsInfo.setToldRule(rs.getString("ToldRule"));
						toldLotsInfo.setToldRuleStatus(rs.getString("ToldRuleStatus"));
						toldLotsInfo.setToldRuleMsg(rs.getString("ToldRuleMsg"));
						toldLotsInfo.setToldRuleCreated(rs.getString("ToldRuleCreated"));
						System.out.println(rs.getString("StepName") + "." + rs.getString("LotNo"));
						record.add(toldLotsInfo);
					} while(rs.next());
				
					logger.info("Number of TOLD Lots w/ FAILED Status: " + record.size());
					logger.info("Trigger File Path: " + path);
					
					String bgcolor;
					if (record.size() > 0) {
						bgcolor = "#ff0000";
						EmailManager.loadEmailConfig();
						StringBuilder report = new StringBuilder();	
						report.append("<table border=\"1\" cellspacing=\"0\" cellpadding=\"0\" width=\"auto\" align=\"left\">");
							report.append("<thead>");
								report.append("<tr bgcolor=\"" + bgcolor + "\">");
									report.append("<th>STEP NAME</th>");
									report.append("<th>LOT NUMBER</th>");
									report.append("<th>TOLD RULE</th>");
									report.append("<th>RULE STATUS</th>");
									report.append("<th>RULE MESSAGE</th>");
									report.append("<th>RULE CREATED</th>");									
								report.append("</tr>");
							report.append("</thead>");						
							for (ToldLotsInfo r : record) {
								report.append("<tbody>");
									report.append("<tr>");
										report.append("<td>" + r.getStepName() +"</td>");
										report.append("<td>" + r.getLotNo() + "</td>");
										report.append("<td>" + r.getToldRule() + "</td>");
										report.append("<td>" + r.getToldRuleStatus() +"</td>");
										report.append("<td>" + r.getToldRuleMsg() + "</td>");
										report.append("<td>" + r.getToldRuleCreated() + "</td>");
									report.append("</tr>");
								report.append("</tbody>");
							}							
						report.append("</table>");	
						EmailManager.sendMailReport("TOLD Lots w/ FAILED Status", report);								
						logger.info("<==== Sending of TOLD Failed Lots Sent! ====>");
					} else {
						logger.info("<==== No TOLD Lots w/ FAILED Status! ====>");
					}
						
				} catch (SQLException ex) {
					logger.error("Exeption : ", ex);
				}
				rs.close();
				OraConn.Stmt.close();
				OraConn.Conn.close();					
			}
			else {
				logger.info("<==== No records retrieved. ====>");
			}								

			logger.info("Previous Property ID/Value: " + AppUtils.getProperty("PropertyID") + "/" + AppUtils.getProperty("LastRunDateTime"));
			
			DateTimeUtils formatdatetime = new DateTimeUtils(currdatetime);
			AppUtils.updateProperties(AppUtils.getProperty("PropertyID"), formatdatetime.formatDateTime());
			
			logger.info("New Property ID/Value: " + AppUtils.getProperty("PropertyID") + "/" + formatdatetime.formatDateTime());
			logger.info("Property ID Value Successfully Updated! ====>");
			
		} catch (SQLException ex) {
				logger.error("Exeption : ", ex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}		
}

