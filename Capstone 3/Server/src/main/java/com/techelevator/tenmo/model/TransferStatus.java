package com.techelevator.tenmo.model;

public class TransferStatus {

	
	private long transferStatusId;
	private String statusDesc;
	
	
	public long getTransferStatus() {
		return transferStatusId;
	}
	public void setTransferStatus(long transferStatus) {
		this.transferStatusId = transferStatus;
	}
	public String getStatusDec() {
		return statusDesc;
	}
	public void setStatusDec(String statusDec) {
		this.statusDesc = statusDec;
	}
	
	
}
