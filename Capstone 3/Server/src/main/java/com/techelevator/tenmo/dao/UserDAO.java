package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDAO {

	public  List<User> findAll();

    public  User findByUsername(String username);

    public  int findIdByUsername(String username);

    public  boolean create(String username, String password);
    
    public Account getAccountByUserId(int id);
    
    public void updateBalance(Account account);
    
//    public String getUserByAccountId(int id);   // this id is the account id
    
//    public void updateSenderBalance(Account account);
    
}
