package com.onsemi.cim.tarlac.triggers.model;

public class TriggersData {
	
	private String lotID;
	private String waferLot;
	private String waferNumber;
	private String sourcePartName;
	private String targetPartName;
	
	public String getLotID() {
		return lotID;
	}
	public void setLotID(String lotID) {
		this.lotID = lotID;
	}
	public String getWaferLot() {
		return waferLot;
	}
	public void setWaferLot(String waferLot) {
		this.waferLot = waferLot;
	}
	public String getWaferNumber() {
		return waferNumber;
	}
	public void setWaferNumber(String waferNumber) {
		this.waferNumber = waferNumber;
	}
	public String getSourcePartName() {
		return sourcePartName;
	}
	public void setSourcePartName(String sourcePartName) {
		this.sourcePartName = sourcePartName;
	}
	public String getTargetPartName() {
		return targetPartName;
	}
	public void setTargetPartName(String targetPartName) {
		this.targetPartName = targetPartName;
	}

}
