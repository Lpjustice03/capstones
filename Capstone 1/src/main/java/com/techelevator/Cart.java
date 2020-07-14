package com.techelevator;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart {

	private Map <String, Integer> cartList = new HashMap <String, Integer>();
	private Set <String> keys = cartList.keySet();
	FileWrite logText;
	
	public Cart() {
		logText = new FileWrite();
	}
	
	public void addToCart(Map<String, Integer> productSelection, Set<String> inventoryKey, Map<String, Item> inventory, double balance) {
	
		Set <String> productKey = productSelection.keySet();
		
		for(String key: productKey) {
			if (inventoryKey.contains(key) && productSelection.get(key)>0) {

				//logging Cart Items
				double price = productSelection.get(key) * inventory.get(key).getItemPrice();
				String s = " " + productSelection.get(key) + " " + inventory.get(key).getItemName() + " " + key + " $" + price + " $" + balance;
				try {
					logText.writeLog(s);
				} 
				catch (NullPointerException | FileNotFoundException e) {
					e.printStackTrace();
				}

				if (keys.contains(key)) {
					if(productSelection.get(key) + cartList.get(key) <= inventory.get(key).getItemInventory()) {
						int count = productSelection.get(key) + cartList.get(key);
						cartList.put(key, count);
					}
					else {
						System.out.println("***Not enough items are in stock***");
					}
				}
				else {
					if (productSelection.get(key) <= inventory.get(key).getItemInventory()){
						cartList.put(key, productSelection.get(key));
					}
					else {
						System.out.println("***Not enough items are in stock***");
					}
				}
			}
			else {
				System.out.println("***Invalid Item Code and/or Count is Entered. Try Again.***");
			}
		}
	}
	
	public void printCart(Map<String, Item> inventory) {
		System.out.println("***************Cart summary***************\n" );
		for (String key: keys) {
			System.out.print(cartList.get(key) + "\t");
			System.out.print(inventory.get(key).getItemType() + "\t");
			System.out.print(inventory.get(key).getItemName() + "\t$");
			System.out.printf("%.2f", inventory.get(key).getItemPrice());
			System.out.print("\t$");
			double totalPriceByItem = inventory.get(key).getItemPrice() * cartList.get(key);
			System.out.printf("%.2f",totalPriceByItem);
			System.out.println();
		}
		System.out.println("***************End of Cart summary***************\n" );
	}
	
	public void clearCart(double change) {
		cartList.clear();
	}

	public Map<String, Integer> getCartList() {
		return cartList;
	}

	public Set<String> getKeys() {
		return keys;
	}
}
