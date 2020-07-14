package com.techelevator;

import com.techelevator.view.Menu;

public class CateringSystemCLI {
	private Menu menu;
	private Inventory cateringList;
	private AccountBalance balance;
	private TransactionBalance finalBill;
	private Cart cart;
	
	public CateringSystemCLI(Menu menu, Inventory cateringList, AccountBalance balance, Cart cart, TransactionBalance finalBill) {
		this.menu = menu;
		this.cateringList = cateringList;
		this.balance = balance;
		this.finalBill = finalBill;
		this.cart = cart;
	}
	
	public static void main(String[] args) {
		String dataFile = "cateringsystem.csv";
		Inventory cateringList = new Inventory(dataFile);
		AccountBalance balance = new AccountBalance();
		TransactionBalance finalBill = new TransactionBalance();
		Cart cart = new Cart();
		Menu menu = new Menu();
		CateringSystemCLI cli = new CateringSystemCLI(menu, cateringList, balance, cart, finalBill);
		cli.run();
	}	

	/*
	 * Runs main menu for
	 *(1) Display Catering Items
	 *(2) Order
	 *(3) Quit
	 */
	
	public void run() {
			boolean finished = false;

			while (!finished) {
				String response = menu.printMainMenu();
				
				if (response.equals("1")) {
					cateringList.printCateringItems();
				} 
				else if (response.equals("2")) {
					orderItemsMenu();
				} 
				else if (response.equals("3")) {
					finished = true;
				} 
				else {
					System.out.println("Invalid Choice. Please try again!");
				}
			}

			System.out.println("Goodbye!");
	}

	
	/*
	 * Runs sub menu for ordering
	 *(1) Add Money
	 *(2) Select Products
	 *(3) Complete Transaction
	 *Current Account Balance: $
	 */
	
	private void orderItemsMenu() {
		
		boolean finished = false;

		while (!finished) {
			
			String response = menu.printListOrderMenu(balance.getCustomerBalance());

			if (response.equals("1")) {
				subMenuAddMoney();
			} 
			else if (response.equals("2")) {
				subMenuSelectProducts();
			} 
			else if (response.equals("3")) {
				finished = completeTransaction(finished);
			} 
			else {
				System.out.println("Invalid Choice. Please try again!");
			}
		}
	}
	
	
	private void subMenuAddMoney() {
		int depositMoney = menu.addMoneyMenu();
		double addedBalance = balance.addMoney(depositMoney);
		
		System.out.print("Your current balance is: $");
		System.out.printf("%.2f", addedBalance);
		System.out.println();
	}
	
	private void subMenuSelectProducts() {
		cateringList.printCateringItems();
		
		cart.addToCart(menu.selectProductsMenu(), cateringList.getKeys(), cateringList.getItemList(), balance.getCustomerBalance());
		cart.printCart(cateringList.getItemList());
	}
	
	private boolean completeTransaction (boolean finished) {
		double totalTransaction = finalBill.setCalcTransactionBalance(cateringList.getItemList(), cart);
		
		if(totalTransaction < balance.getCustomerBalance()) {
			cateringList.updateInventoryAfterCheckout(cart.getCartList(), cart.getKeys());
			
			double change = balance.getChange(totalTransaction);
			int[] billCount = balance.splitToBills();
			
			System.out.println("**********Purchase Summary**********");
			cart.printCart(cateringList.getItemList());
			System.out.print("Total Cost is: $");
			System.out.printf("%.2f", totalTransaction);
			System.out.println();
			System.out.print("Total Change: $");
			System.out.printf("%.2f", change);
			System.out.println();
			menu.printBillCount(billCount);

			cart.clearCart(change);
			finished = true;
		}
		else {
			System.out.println("**********Insufficient Account Balance. Add more money!!**********");
		}
		return finished;
	}
}
