package com.techelevator.tenmo.dao;

import java.util.List;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;

public interface TransferDAO {
	
	public List<Transfer> getAllTransfers(int id);   //account id
	
	public Transfer getTransferById(long id);
	
	public Transfer addTransfer(Transfer transfer);
	
	public TransferStatus getStatusDetails(long id);
	
	public TransferType getTypeDetails(long id);

}
