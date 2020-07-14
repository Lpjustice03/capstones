package com.techelevator.tenmo.model;

import java.math.BigDecimal;


public class Transfer {
	
	private long transferId;
	private int transferTypeId;
	private int transferStatusId;
	private int accountFromId;
	private int accountTo;
	private BigDecimal amount;
	private String FromName;
	private String ToName;
	
	
	
	public long getTransferId() {
		return transferId;
	}
	public void setTransferId(long transferId) {
		this.transferId = transferId;
	}
	public int getTransferTypeId() {
		return transferTypeId;
	}
	
	public String getFromName() {
		return FromName;
	}
	public void setFromName(String fromName) {
		this.FromName = fromName;
	}
	public String getToName() {
		return ToName;
	}
	public void setToName(String toName) {
		this.ToName = toName;
	}
	public void setTransferTypeId(int transferTypeId) {
		this.transferTypeId = transferTypeId;
	}
	public int getTransferStatusId() {
		return transferStatusId;
	}
	public void setTransferStatusId(int transferStatusId) {
		this.transferStatusId = transferStatusId;
	}
	public int getAccountFromId() {
		return accountFromId;
	}
	public void setAccountFromId(int accountFromId) {
		this.accountFromId = accountFromId;
	}
	public int getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(int accountTo) {
		this.accountTo = accountTo;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
	

}
