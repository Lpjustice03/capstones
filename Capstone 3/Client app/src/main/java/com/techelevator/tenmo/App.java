package com.techelevator.tenmo;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.tenmo.models.Account;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.Transfer;
import com.techelevator.tenmo.models.TransferStatus;
import com.techelevator.tenmo.models.TransferType;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.tenmo.services.ClientService;
import com.techelevator.view.ConsoleService;

public class App {

private static final String API_BASE_URL = "http://localhost:8080/";
    
    private static final String MENU_OPTION_EXIT = "Exit";
    private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS, MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS, MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };
	
    private AuthenticatedUser currentUser;
    private ConsoleService console;
    private AuthenticationService authenticationService;
    private ClientService clientService;

    public static void main(String[] args) {
    	App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL) , new ClientService(API_BASE_URL));
    	app.run();
    }

    public App(ConsoleService console, AuthenticationService authenticationService , ClientService clientService) {
		this.console = console;
		this.authenticationService = authenticationService;
		this.clientService = clientService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");
		
		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while(true) {
			String choice = (String)console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if(MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if(MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if(MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if(MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if(MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		Account account = new Account();
		
		account = clientService.getBalance(currentUser.getUser().getId());
		console.printBalance(account.getBalance());;
		
		
	}

	private void viewTransferHistory() {
		
		Transfer[] transferArray = clientService.getAllUserTransfers(currentUser.getUser().getId());
		long answer = console.printUserTranfers(transferArray, currentUser);
		Transfer currentTransfer = clientService.getTransferByTransferId(answer);
		
		TransferStatus transferStatus = clientService.getTransferStatusById(Long.valueOf(currentTransfer.getTransferStatusId()));
		TransferType transferType = clientService.getTransferTypeById(Long.valueOf(currentTransfer.getTransferTypeId()));
		User[] user = clientService.getAllUsers();
		
		console.printTransferDetails(currentTransfer, transferStatus, transferType, user);
	}
	

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
		 
		
		User[] userArray = clientService.getAllUsers();
		Transfer createdTransfer = new Transfer();
		List<String> answer = console.printListOfUsers(userArray);
		Long longAmount = Long.parseLong(answer.get(1));
		BigDecimal amount = BigDecimal.valueOf(longAmount);
		
		//sender account update balance
		Account account = clientService.getBalance(currentUser.getUser().getId());
		
		if (account.getBalance().compareTo(amount)>=0) {
			account.setBalance(account.getBalance().subtract(amount));
			clientService.updateBalance(account);
		
		//receiver account update balance
			Account accountReceiver = clientService.getBalance(Integer.parseInt(answer.get(0)));
			accountReceiver.setBalance(accountReceiver.getBalance().add(amount));
			clientService.updateBalance(accountReceiver);
			
			createdTransfer = addTransfer (currentUser.getUser().getId() , Integer.parseInt(answer.get(0)) ,amount);
				if (createdTransfer.getTransferId() != 0) {
					System.out.println("Your Transfer is approved! Your transfer ID: " + createdTransfer.getTransferId());
				}
		}
		
		else {
			System.out.println("Not enough funds");
		}
	}
	

	private void requestBucks() {
		
		Transfer newTransfer = new Transfer();
		
		User[] userArray = clientService.getAllUsers();
		List<String> answer = console.printListOfUsersToRequestBucks(userArray);
		Long longAmount = Long.parseLong(answer.get(1));
		BigDecimal amount = BigDecimal.valueOf(longAmount);
		
		//sender account update balance
		Account accountSender = clientService.getBalance(Integer.parseInt(answer.get(0)));
		
		if (accountSender.getBalance().compareTo(amount)>=0) {
			accountSender.setBalance(accountSender.getBalance().subtract(amount));
			clientService.updateBalance(accountSender);
		
		//receiver account update balance
			Account account = clientService.getBalance(currentUser.getUser().getId());
			account.setBalance(account.getBalance().add(amount));
			clientService.updateBalance(account);
			
			newTransfer.setTransferStatusId(1);
			newTransfer.setTransferTypeId(1);
			newTransfer.setAccountFromId(Integer.parseInt(answer.get(0)));
			newTransfer.setAccountTo(currentUser.getUser().getId());
			newTransfer.setAmount(amount);
			Transfer createdTransfer = clientService.addTransfer(newTransfer);
			
				if (createdTransfer.getTransferId() != 0) {
					System.out.println("Your Transfer is approved! Your transfer ID: " + createdTransfer.getTransferId());
				}
		}
		
		else {
			Transfer rejectedTransfer = new Transfer();
			rejectedTransfer.setTransferStatusId(3);
			rejectedTransfer.setTransferTypeId(1);
			rejectedTransfer.setAccountFromId(Integer.parseInt(answer.get(0)));
			rejectedTransfer.setAccountTo(currentUser.getUser().getId());
			rejectedTransfer.setAmount(amount);
			Transfer createdTransfer = clientService.addTransfer(rejectedTransfer);
				if (createdTransfer.getTransferId() != 0) {
					System.out.println("Your request is on pending! Your transfer ID: " + createdTransfer.getTransferId());
				}
		}
		
	}
	
	private void exitProgram() {
		System.out.println("Goodbye!");
		System.exit(0);
	}

	private void registerAndLogin() {
		while(!isAuthenticated()) {
			String choice = (String)console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
        while (!isRegistered) //will keep looping until user is registered
        {
            UserCredentials credentials = collectUserCredentials();
            try {
            	authenticationService.register(credentials);
            	
            	isRegistered = true;
            	System.out.println("Registration successful. You can now login.");
            } catch(AuthenticationServiceException e) {
            	System.out.println("REGISTRATION ERROR: "+e.getMessage());
				System.out.println("Please attempt to register again.");
            }
        }
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) //will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
		    try {
				currentUser = authenticationService.login(credentials);
				clientService.AUTH_TOKEN = currentUser.getToken();
				
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: "+e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}
	
	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
	
	private Transfer addTransfer(Integer senderId, int receiverId, BigDecimal amount) {
		Transfer createdTransfer = new Transfer();
		createdTransfer.setTransferStatusId(2);
		createdTransfer.setTransferTypeId(2);
		createdTransfer.setAccountFromId(senderId);
		createdTransfer.setAccountTo(receiverId);
		createdTransfer.setAmount(amount);
		
		Transfer newTransfer = clientService.addTransfer(createdTransfer);
		return newTransfer;
	}
}
