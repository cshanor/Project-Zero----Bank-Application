package com.revature.screens;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.revature.dao.AccountDAO;
import com.revature.models.Account;
import com.revature.services.AccountService;
import com.revature.utils.AppState;

public class AccountScreen implements Screen {

	private AccountDAO acctDao = new AccountDAO();

	private AccountService serv = new AccountService();

	@Override
	public Screen start(Scanner scan) {

		Account acct = acctDao.getById(AppState.getCurrentUser().getId());
		String userSelection;
		double deposit;
		double withdrawal;

		System.out.println(
				"\n\n\n\n\n[LOG] - Rendering " + AppState.getCurrentUser().getFirstName() + "'s Account Menu...");

		// Display the Account menu.
		System.out.println(" A c c o u n t   M e n u");
		System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+");
		System.out.println("");

		// if user does NOT have an account, no balance will be displayed. This circumvents
		// throwing a NullPointerException when the program attempts to find a balance for a
		// non existent account.
		if (acct != null) {
			System.out.printf("Your Current Balance is: $%.2f",acct.getBalance());
		} else {
			System.out.print("Please choose option 3 -- Create an Account -- below to begin using Revature Bank.");
		}
		System.out.println("\n+---------------------------------+\n");

		if(acct != null) {
			System.out.println("1) Deposit Money");
			System.out.println("2) Withdraw Money");
		} else {
			System.out.println("3) Create an Account for " + AppState.getCurrentUser().getFirstName());
		}
		// If user has an account, this option will not display in the menu.
		System.out.println("0) Sign Out");

		try {
			// Get the user's menu selection
			System.out.print("Selection: ");
			userSelection = scan.nextLine();

			// Navigate to the appropriate screen based on the user's selection
			switch (userSelection) {
				case "1":
					// Navigate to deposit gold
					System.out.println("Please enter amount of deposit: ");
					deposit = scan.nextDouble();
					serv.makeDeposit(deposit);
					scan.nextLine();
					return this.start(scan);
				case "2":
					// Navigate to withdraw gold
					System.out.println("Please enter amount of withdrawal: ");
					withdrawal = scan.nextDouble();
					serv.makeWithdrawal(withdrawal);
					scan.nextLine();
					return this.start(scan);
				case "3":
					// Create Account for currentUser
					int user_id = AppState.getCurrentUser().getId();
					double balance = 0;
					Account newAcct = new Account(user_id, balance);
					serv.addAccount(newAcct);
					return this.start(scan);
				case "4":
					// Navigate to view balance
					System.out.println("[TEST] - View balance selected.");
					// return new AccountDAO().getBalance();
					break;
				case "0":
					System.out.println("[LOG] - " + AppState.getCurrentUser().getUsername() + " signing out...");
					AppState.setCurrentUser(null);
					return new MenuScreen().start(scan);
				default:
					System.out.println("Invalid Selection!");
					return this.start(scan);
			}
		} catch (InputMismatchException imme) {
			// If any exceptions are thrown restart the application
			System.out.println("[ERROR] - Error reading input from console");
			System.out.println("Restarting application...");
			AppState.setRestartingApp(true);
			AppState.setAppRunning(false);
			return null;
		}
		// If the application flow makes it to this point, log out the current user and
		// go back to Home screen
		AppState.setCurrentUser(null);
		return new MenuScreen().start(scan);
	}
}
