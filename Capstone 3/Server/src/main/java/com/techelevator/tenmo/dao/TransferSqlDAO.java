package com.techelevator.tenmo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;


@Service
public class TransferSqlDAO implements TransferDAO {
	private JdbcTemplate jdbcTemplate;

    public TransferSqlDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	
	@Override
	public List<Transfer>  getAllTransfers(int id) {  // account id
		List<Transfer> transfers = new ArrayList<>();
        String sql = "select * from transfers where account_from = ? or account_to = ?";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id, id);
        while(results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            if (transfer.getAccountFromId() != id) {
            	String sql2 = "select users.username from users join accounts on accounts.user_id = users.user_id where account_id =?";
            	SqlRowSet results2 = jdbcTemplate.queryForRowSet(sql2, transfer.getAccountFromId());
            	if(results2.next()) {
            		transfer.setFromName(results2.getString("username"));
            	}
            }
            else {
            	String sql3 = "select users.username from users join accounts on accounts.user_id = users.user_id where account_id =?";
            	SqlRowSet results3 = jdbcTemplate.queryForRowSet(sql3, transfer.getAccountTo());
            	if (results3.next()) {
            	transfer.setToName(results3.getString("username"));
            	}
            }
            transfers.add(transfer);
        }

        return transfers;
	}

	

	@Override
	public Transfer getTransferById(long id) {
		
		Transfer transfer = null;
		String sql = "Select * from transfers where transfer_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
    	
    	
    	if (results.next()) {
    		transfer = mapRowToTransfer(results);
    	}
    	
		return transfer;
	}
	
	
	
	@Override
	public Transfer addTransfer(Transfer transfer) {
		
		String sql = "Insert into transfers(transfer_id, transfer_type_id, transfer_status_id, account_from, account_to, amount ) " 
		+ "Values (default, ?,?,?,?,?) returning transfer_id";
		Long id = jdbcTemplate.queryForObject(sql, Long.class, transfer.getTransferTypeId(), transfer.getTransferStatusId(), transfer.getAccountFromId(), transfer.getAccountTo(), transfer.getAmount());
    	transfer.setTransferId(id);
		return transfer;
	}
	
	@Override
	public TransferStatus getStatusDetails(long id) {
		TransferStatus status = null;
		String sql = "Select * from transfer_statuses " + "where transfer_status_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql,id);
		
		if (results.next()) {
    		status = mapRowToTransferStatus(results);
    	}
    	
		return status;
	}


	@Override
	public TransferType getTypeDetails(long id) {
		TransferType type = null;
		String sql = "Select * from transfer_types " + 
					  "where transfer_type_id =?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql,id);
		
		if (results.next()) {
    		type = mapRowToTransferType(results);
    	}
		return type;
	}
	
//	@Override
//	public List<Transfer> getPendingTransfer() {
//		List<Transfer> pending = new ArrayList<>();
//        String sql = "select * from transfers where transfer_status_id = ?";
//
//        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, 1);
//        while(results.next()) {
//            Transfer transferPen = mapRowToTransfer(results);
//            pending.add(transferPen);
//        }
//
//        return pending;
//	}
//
//
//	@Override
//	public void updatePendingTransfer(long id, int status) { //transfer_id{
//		String sql = "Update transfers " + "set transfer_status_id = ? " + "where transfer_id = ?";
//    	jdbcTemplate.update(sql, status, id );
//		
//	}

private Transfer mapRowToTransfer(SqlRowSet rs) {
		Transfer transfers = new Transfer();
		
		transfers.setTransferId(rs.getLong("transfer_id"));
		transfers.setTransferTypeId(rs.getInt("transfer_type_id"));
		transfers.setTransferStatusId(rs.getInt("transfer_status_id"));
		transfers.setAccountFromId(rs.getInt("account_from"));
		transfers.setAccountTo(rs.getInt("account_to"));
		transfers.setAmount(rs.getBigDecimal("amount"));
		return transfers;
	}


private TransferStatus mapRowToTransferStatus (SqlRowSet rs) {
	TransferStatus st = new TransferStatus();
	
	st.setTransferStatus(rs.getLong("transfer_status_id"));
	st.setStatusDec(rs.getString("transfer_status_desc"));
	
	return st;
	
}

private TransferType mapRowToTransferType(SqlRowSet rs) {
	TransferType typ = new TransferType();
	
	typ.setTransferTypeId(rs.getLong("transfer_type_id"));
	typ.setTransferTypeDesc(rs.getString("transfer_type_desc"));
	
	return typ;
}







}
