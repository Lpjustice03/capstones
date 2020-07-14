package com.techelevator.view;


import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferStatus;
import com.techelevator.tenmo.models.TransferType;
import com.techelevator.tenmo.models.User;

public class ConsoleService {

	private PrintWriter out;
	private Scanner in;

	public ConsoleService(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output, true);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		out.println();
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}

	public String getUserInput(String prompt) {
		out.print(prompt+": ");
		out.flush();
		return in.nextLine();
	}

	public Integer getUserInputInteger(String prompt) {
		Integer result = null;
		do {
			out.print(prompt+": ");
			out.flush();
			String userInput = in.nextLine();
			try {
				result = Integer.parseInt(userInput);
			} catch(NumberFormatException e) {
				out.println("\n*** " + userInput + " is not valid ***\n");
			}
		} while(result == null);
		return result;
	}
	
	public void printBalance(BigDecimal balance) {
		System.out.println("Your current Balance is $" + balance);
	}
	
	
	public long printUserTranfers(Transfer[] transfers, AuthenticatedUser currentUser) {
		System.out.println("--------------------------------------------");
	    System.out.println("Transfers");
	    System.out.println("--------------------------------------------");
	    System.out.println("ID\t\tFrom/To\t\tAmount");
	    System.out.println("--------------------------------------------");
	    
		for (Transfer ts : transfers) {
			if (ts.getAccountTo() == (currentUser.getUser().getId())) {
				System.out.println(ts.getTransferId() + "\t\t From: " + ts.getFromName() +"\t\t$" + ts.getAmount());
			}			
			else if (ts.getAccountFromId() == (currentUser.getUser().getId())) {
				System.out.println(ts.getTransferId() + "\t\t To: " + ts.getToName() + "\t\t$" + ts.getAmount());
		}
			else {
				break;
			}
			
		}
		System.out.println("Please enter transfer ID to view details (0 to cancel):");
		long answer = in.nextLong();
		in.hasNextLine();
		return answer;
	}
	
	public void printTransferDetails (Transfer transfer, TransferStatus status, TransferType type, User[] user) {
		
		System.out.println("--------------------------------------------");
	    System.out.println("Transfer Details");
	    System.out.println("--------------------------------------------");
	    System.out.println("ID: " + transfer.getTransferId());
	    
	    for(User s : user) {
	    	
	    	if (s.getId() == transfer.getAccountFromId()) {
	    		System.out.println("From: " + s.getUsername());
	    	}
	    }
	    for(User s : user) {
	    	
	    	if (s.getId() == transfer.getAccountTo()) {
	    		System.out.println("To: " + s.getUsername());
	    	}
	    }
	    System.out.println("Type: " + type.getTransferTypeDesc());
	    System.out.println("Status: " + status.getStatusDec());
	    System.out.println("Amount: $" + transfer.getAmount());
		
	}
	
	public List<String> printListOfUsers(User[] users){
		
		List<String> answer = new ArrayList<String>();
		
		System.out.println("--------------------------------------------");
	    System.out.println("Users");
	    System.out.println("--------------------------------------------");
	    System.out.println("ID\t\tName");
	    System.out.println("--------------------------------------------");
		
	    for(User user : users) {
	    	System.out.println(user.getId() + "\t" + "\t" + user.getUsername());
	    }
	    
	    System.out.println("Enter ID of user you are sending to (0 to cancel):");
	    
	  
	    String id = in.nextLine();
	    answer.add(id);
	    System.out.println("Enter amount:");
	    
	    String amount = in.nextLine();
	    answer.add(amount);
		
		return answer;
		
	}
	
public List<String> printListOfUsersToRequestBucks(User[] users){   //this is for requesting bucks
		
		List<String> answer = new ArrayList<String>();
		
		System.out.println("--------------------------------------------");
	    System.out.println("Users");
	    System.out.println("--------------------------------------------");
	    System.out.println("ID\t\tName");  
	    System.out.println("--------------------------------------------");
		
	    for(User user : users) {
	    	System.out.println(user.getId() + "\t" + "\t" + user.getUsername());
	    }
	    
	    System.out.println("Enter ID of user you are requesting from (0 to cancel):");
	    
	    String id = in.nextLine();
	    answer.add(id);
	    System.out.println("Enter amount:");
	    
	    String amount = in.nextLine();
	    answer.add(amount);
		
		return answer;
		
	}

}
