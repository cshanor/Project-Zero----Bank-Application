package com.revature.screens;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.services.AccountService;
import com.revature.services.CustomerService;
import com.revature.utils.AppState;

public class CreateAccountScreen implements Screen {

	private CustomerService custService = new CustomerService();
	private AccountService acctService = new AccountService();

	@Override
	public Screen start(Scanner scan) {

		// Method-scoped references to hold registration input
		String username;
		String password;
		String firstName;
		String lastName;
		boolean isValid = false;

		try {

			// Get user registration input
			System.out.println("\n+---------------------------------+\n");
			System.out.println("Sign up for a new account");
			System.out.print("Username: ");
			username = scan.nextLine().toLowerCase();
			System.out.print("Password: ");
			password = scan.nextLine();

			// Accept user input and verify they have entered only letters.
			// If any numbers are entered, an error message will display and
			// then after a five second delay the create user fields will reload.

			System.out.print("First name: ");
			firstName = scan.nextLine();
			isValid = custService.verifyNameInput(firstName);

			System.out.print("Last name: ");
			lastName = scan.nextLine();
			isValid = custService.verifyNameInput(lastName);

			if (isValid) {
				// Create a user object using the input provided, set the role to USER by
				// default
				Customer newCust = new Customer(username, password, firstName, lastName, "USER");

				System.out.println("[LOG] - Attempting to create user: ");

				// Attempt to register user and update the currentUser field of AppState to the
				// return value of .addUser
				AppState.setCurrentUser(custService.addUser(newCust));
				Account acct = new Account(newCust.getUsername(), 0);
				acctService.addAccount(acct);

			} else {
				System.out.println("[WARN] - User not created, invalid field values");

				// Delay program so that WARN message lingers long enough for user to see before
				// it scrolls out of view.
				Thread.sleep(5000);
				return this.start(scan);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {

			// If any exceptions are thrown restart the application
			System.out.println("[ERROR] - Error reading input from console");
			System.out.println("Restarting application...");
			AppState.setRestartingApp(true);
			AppState.setAppRunning(false);
			return null;
		}
		// If the application flow makes it to this point, show the Account screen
		return new AccountScreen().start(scan);
	}
}
