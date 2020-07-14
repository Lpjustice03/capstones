package com.techelevator.tenmo.controller;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.dao.TransferDAO;
import com.techelevator.tenmo.dao.UserDAO;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferStatus;
import com.techelevator.tenmo.model.TransferType;
import com.techelevator.tenmo.model.User;

@PreAuthorize("isAuthenticated()")
@RestController
public class ServerController {
	
	private UserDAO userDao;
	private TransferDAO transferDao;

	public ServerController (UserDAO userDAO, TransferDAO transferDAO) {
	this.userDao = userDAO;
	this.transferDao = transferDAO;
	}
	
	@RequestMapping(path = "/users", method = RequestMethod.GET)
	public List<User> findAll() {
		return userDao.findAll();
	}
	
	@RequestMapping(path = "/users/{id}/accounts" , method = RequestMethod.GET)
	public Account getAccountByUserId(@PathVariable int id) {
		return userDao.getAccountByUserId(id);
	}
	
	@ResponseStatus(HttpStatus.ACCEPTED) // fix me
	@RequestMapping (path ="/accounts" , method = RequestMethod.PUT)
	public void updateBalance(@RequestBody Account account) {
		userDao.updateBalance(account);
	}
	
	
	@RequestMapping (path ="/accounts/{id}/transfers" , method = RequestMethod.GET)  //this method returning a list of transfers by account id
	public List<Transfer>  getAllTransfers(@PathVariable int id) {
		return transferDao.getAllTransfers(id);
	}
	
	
	@RequestMapping (path ="/transfers/{id}" , method = RequestMethod.GET) // this gets transfers by transfer id
	public Transfer getTransferById(@PathVariable long id) {
		return transferDao.getTransferById(id);
	}
	
	
	@RequestMapping (path ="/transfers" , method = RequestMethod.POST)
	public Transfer addTransfer(@RequestBody Transfer transfer) {
		return transferDao.addTransfer(transfer);
		
	}
	
	
	@RequestMapping (path ="/transferstatuses/{id}" , method = RequestMethod.GET)
	public TransferStatus getStatusDetails(@PathVariable long id) {
		return transferDao.getStatusDetails(id);
	}
	
	
	@RequestMapping (path ="/transfertypes/{id}" , method = RequestMethod.GET)
	public TransferType getTypeDetails(@PathVariable long id) {
		return transferDao.getTypeDetails(id);
	}
	
}
