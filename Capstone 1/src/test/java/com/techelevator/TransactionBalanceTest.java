package com.techelevator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TransactionBalanceTest {
	
	private Inventory inventory;
	private Set<String> inventoryKeys; 
	private Cart cart;
	private Map<String,Integer> productSelection;
	private TransactionBalance balance;
	
	@Before
	public void runBeforeEachTest() {
		inventory = new Inventory("cateringsystem.csv");
		inventoryKeys = inventory.getKeys();
		productSelection = new HashMap<String, Integer>();
		productSelection.put("B1", 10);
		cart = new Cart();
		cart.addToCart(productSelection, inventoryKeys, inventory.getItemList(), 100.00);
		balance = new TransactionBalance();
	}
	
	@Test
	public void testBalanceInitialization() {
		Assert.assertNotNull(balance);
	}
	
	@Test
	public void testSetTransactionBalance() {
		
		Assert.assertEquals(15.00, balance.setCalcTransactionBalance(inventory.getItemList(), cart), 0);
		
	}
	
	

}
