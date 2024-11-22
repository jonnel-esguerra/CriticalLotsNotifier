package com.onsemi.cim.tarlac.triggers.services;

import java.util.Date;
import java.text.SimpleDateFormat;

public class DateTimeUtils {

	private Date CurrDateTime;
	
	enum DateTimeFormat {
		yyyyMMddHHmmss;
	}
		
	public DateTimeUtils(Date currdatetime) {
		setCurrDateTime(currdatetime);
	}
	
	public String formatDateTime() {
		DateTimeFormat datetimeformat = DateTimeFormat.yyyyMMddHHmmss;
		SimpleDateFormat formatter = new SimpleDateFormat(datetimeformat.toString());
		return formatter.format(getCurrDateTime());
	}
		
	public void setCurrDateTime(Date currDateTime) {
		CurrDateTime = currDateTime;
	}
	
	public Date getCurrDateTime() {
		return CurrDateTime;
	}
	
}
