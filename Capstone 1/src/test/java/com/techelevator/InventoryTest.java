package com.techelevator;

import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InventoryTest {
	
	private Inventory inventory;
	private Cart cart;
	private Map<String,Integer> productSelection;
	
	@Before
	public void runBeforeEachTest() {
		inventory = new Inventory("cateringsystem.csv");
		productSelection = new HashMap<String, Integer>();
		productSelection.put("D5", 10);
		cart = new Cart();
		cart.addToCart(productSelection, inventory.getKeys(), inventory.getItemList(), 100.00);
	}
	
	@Test
	public void testBalanceInitialization() {
		Assert.assertNotNull(inventory);
	}
	
	@Test
	public void testUpdateInventory() {
		inventory.updateInventoryAfterCheckout(cart.getCartList(), cart.getKeys());
		int newInventory = inventory.getItemList().get("D5").getItemInventory();
		Assert.assertEquals( 40, newInventory);
	}
}
