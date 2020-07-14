package com.techelevator.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Menu {

	private Scanner scanner = new Scanner(System.in);
	
	/*
	 *(1) Display Catering Items
	 *(2) Order
	 *(3) Quit
	 */
	
	public String printMainMenu() {

		System.out.println("************** CateringSystemCLI Main Menu ****************\n" );
		System.out.println("\n");
		System.out.println("(1) Display Catering Items");
		System.out.println("(2) Order");
		System.out.println("(3) Quit");
       
		return scanner.nextLine();
	}
	
	/*
	 *(1) Add Money
	 *(2) Select Products
	 *(3) Complete Transaction
	 *Current Account Balance: $
	 */
	
	public String printListOrderMenu(double balance) {

		System.out.println("************** Ordering Menu ****************\n" );
		System.out.println("\n");
		System.out.println("(1) Add Money");
		System.out.println("(2) Select Products");
		System.out.println("(3) Complete Transaction");
		System.out.print("Current Account Balance: $");
		System.out.printf("%.2f", balance);
		System.out.println();
		
		return scanner.nextLine();
	}

	public int addMoneyMenu() {

		System.out.println("Enter value of money you would like to add to your account: " );
		int moneyDeposit = scanner.nextInt();
		scanner.nextLine();
		
		return moneyDeposit;
	}
	
	public Map<String, Integer> selectProductsMenu() {
		
		Map<String, Integer> productSelection = new HashMap<String, Integer>();
		
		System.out.println("Enter Item Code that you would like to Purchase: " );
		String itemCodeToPurchase = scanner.nextLine();
		System.out.println("Enter Number of Items you woud like to Purchase: " );
		int numberOfItemsToPurchase = scanner.nextInt();
		scanner.nextLine();
       
		productSelection.put(itemCodeToPurchase, numberOfItemsToPurchase);
		
		return productSelection;
	}
	
	public void printBillCount(int[] billCount) {

		System.out.println("***************Bill of change summary***************\n" );
		System.out.println(billCount[0] + ": 20 Dollar Bills");
		System.out.println(billCount[1] + ": 10 Dollar Bills");
		System.out.println(billCount[2] + ": 5 Dollar Bills");
		System.out.println(billCount[3] + ": 1 Dollar Bills");
		System.out.println(billCount[4] + ": Quarters");
		System.out.println(billCount[5] + ": Dimes");
		System.out.println(billCount[6] + ": Nickels");
		System.out.println("***************End of Bill of change summary***************\n" );
	}
}
