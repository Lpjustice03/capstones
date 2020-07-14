package com.techelevator.tenmo.services;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferStatus;
import com.techelevator.tenmo.models.TransferType;
import com.techelevator.tenmo.models.User;


public class ClientService {
	
	 
	  public static String AUTH_TOKEN = "";
	  private final String BASE_URL;
	  private final RestTemplate restTemplate = new RestTemplate();
	  

	  public ClientService(String url) {
	    BASE_URL = url;
	  }
	  
	  
	  public Account getBalance(int id) {
		  Account account = null;
		  account = restTemplate.exchange(BASE_URL + "users/" + id + "/accounts" , HttpMethod.GET ,makeAuthEntity(), Account.class).getBody();
		  
		  return account;
	  }
	  
	  public Transfer[] getAllUserTransfers(int id){
		  Transfer[] transferArray = null;
		  
		  transferArray = restTemplate.exchange(BASE_URL + "accounts/" + id + "/transfers",HttpMethod.GET ,makeAuthEntity() ,Transfer[].class).getBody();
		  return transferArray;
	  }
	  
	  public User[] getAllUsers() {
		  
		  User[] userArray = null;
		  
		  userArray = restTemplate.exchange(BASE_URL + "users/" ,HttpMethod.GET ,makeAuthEntity(), User[].class).getBody();
		  return userArray;

	  }
	  
	  public void updateBalance(Account account) {
		  restTemplate.exchange(BASE_URL + "accounts/",HttpMethod.PUT ,makeAccountEntity(account) , Account.class);
	  }
	  
	  public Transfer addTransfer(Transfer transfer) {
		  Transfer createdTransfer = null;
		  createdTransfer = restTemplate.exchange(BASE_URL + "transfers/",HttpMethod.POST ,makeTransferEntity(transfer) , Transfer.class).getBody();
		  return createdTransfer;
	  }
	  
	  public Transfer getTransferByTransferId(long id) {
		  Transfer currentTransfer = null;
		  currentTransfer = restTemplate.exchange(BASE_URL + "transfers/" + id  , HttpMethod.GET ,makeAuthEntity(),Transfer.class).getBody();
		  return currentTransfer;
	  }
	  
	  public TransferStatus getTransferStatusById(long id) {
		  
		  TransferStatus transferStatus = null;
		  
		  transferStatus = restTemplate.exchange(BASE_URL + "transferstatuses/" + id  , HttpMethod.GET ,makeAuthEntity(),TransferStatus.class).getBody();
		  
		return transferStatus;
		  
	  }
	  
	  public TransferType getTransferTypeById(long id) {
		  
		  TransferType transferType = null;
		  
		  transferType = restTemplate.exchange(BASE_URL + "transfertypes/" + id  , HttpMethod.GET ,makeAuthEntity(),TransferType.class).getBody();
		  
		return transferType;
		  
	  }
	
	  
	  
	  private HttpEntity<Account> makeAccountEntity(Account account) {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.setBearerAuth(AUTH_TOKEN);
		    HttpEntity<Account> entity = new HttpEntity<>(account, headers);
		    return entity;
		  }
	  
	  
	  private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    headers.setBearerAuth(AUTH_TOKEN);
		    HttpEntity<Transfer> entity = new HttpEntity<>(transfer, headers);
		    return entity;
		  }
	  
	  
	  private HttpEntity makeAuthEntity() {
		    HttpHeaders headers = new HttpHeaders();
		    headers.setBearerAuth(AUTH_TOKEN);
		    HttpEntity entity = new HttpEntity<>(headers);
		    return entity;
		  }
	  

}
