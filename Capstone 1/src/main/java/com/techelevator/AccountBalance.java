package com.techelevator;

import java.io.FileNotFoundException;

public class AccountBalance {
	
	private double customerBalance;
	private double change;
	private int[] billCounter;
	FileWrite logText;
	
	public AccountBalance(){
		logText = new FileWrite();
	}
	
	public double addMoney(double depositAmount) {
		if (depositAmount  + customerBalance <= 5000.00) {
			customerBalance += depositAmount;
			
			//logging Add Money
			String s = " ADD MONEY: $" + depositAmount + " $" + customerBalance;
			try {
				logText.writeLog(s);
			} 
			catch (NullPointerException | FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("You have exceeded the account balance maximum, cannot deposit the amount.");
		}
		return customerBalance;
	}
	
	public int[] splitToBills() {
		//[20s, 10s, 5s, 1s, quarters, dimes, nickels]

		double change100 = change *100; 
		int changeAsInt = (int) change100;
		
		billCounter = new int[7];
		int remainder = 0;
		
		billCounter[0] = changeAsInt /2000;
		remainder = changeAsInt % 2000;
		
		billCounter[1] = remainder /1000;
		remainder %= 1000;
		
		billCounter[2] = remainder /500;
		remainder %= 500;
		
		billCounter[3] = remainder /100;
		remainder %= 100;
		
		billCounter[4] = remainder / 25;
		remainder %= 25;
		
		billCounter[5] = remainder / 10;
		remainder %= 10;
		
		billCounter[6] = remainder / 5;
		remainder %= 5;
		
		return billCounter;
	}
	
	public double getCustomerBalance() {
		return customerBalance;
	}
	
	public double getChange(double totalTransaction) {
			change = customerBalance - totalTransaction;
			customerBalance = 0;
		
		//logging Get Change
		String s = " GIVE CHANGE: $" + change + " $" + customerBalance;
		try {
			logText.writeLog(s);
		} 
		catch (NullPointerException | FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return change;
	}
}