package com.techelevator;

import java.util.Map;

public class TransactionBalance {
	
	private double transactionBalance;
	
	public TransactionBalance(){
		transactionBalance = 0;
	}

	public double setCalcTransactionBalance(Map<String, Item> inventory, Cart cart) {
		for (String key: cart.getKeys()) {
			double price = inventory.get(key).getItemPrice();
			transactionBalance += (double) cart.getCartList().get(key) * price;
		}
		return transactionBalance;
	}

	public double getTransactionBalance() {
		return transactionBalance;
	}
}
