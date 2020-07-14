package com.techelevator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CartTest {
	private Inventory inventory;
	private Set<String> inventoryKeys; 
	private Cart cart;
	private Map<String,Integer> productSelection;
	
	@Before
	public void runBeforeEachTest() {
		inventory = new Inventory("cateringsystem.csv");
		inventoryKeys = inventory.getKeys();
		productSelection = new HashMap<String, Integer>();
		productSelection.put("D5", 10);
		cart = new Cart();
	}
	
	@Test
	public void testBalanceInitialization() {
		Assert.assertNotNull(cart);
	}
	
	@Test
	public void testAddToCart() {
		cart.addToCart(productSelection, inventoryKeys, inventory.getItemList(), 100.00);
		Map<String, Integer> test = new HashMap<String, Integer>();
		test.put("D5", 10);
		Assert.assertEquals(test, cart.getCartList());
		
		productSelection.clear();
		productSelection.put("B2", 15);
		cart.addToCart(productSelection, inventoryKeys, inventory.getItemList(), 100.00);
		test.put("B2", 15);
		Assert.assertEquals(test, cart.getCartList());
	}
}
