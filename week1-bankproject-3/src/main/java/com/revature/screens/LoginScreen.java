package com.revature.screens;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Customer;
import com.revature.services.CustomerService;
import com.revature.utils.AppState;

public class LoginScreen implements Screen {
	
	// We want this screen to make use of the UserService for login functionality
	private CustomerService custService = new CustomerService();

	@Override
	public Screen start(Scanner scan) {
		
		// Method-scoped variables to store user credentials
		String username;
		String password;
		
		try {
			
			// Get user's credentials
			System.out.println("\n+---------------------------------------------------+\n");
			System.out.println("Please provide your login credentials");
			System.out.print("Username: ");
			username = scan.nextLine();
			System.out.print("Password: ");
			password = scan.nextLine();
			
			// Attempt to log user in using provided credentials
			Customer loggedUser = custService.getUserByCredentials(username, password);
			
			// If loggedUser is not null, then the login attempt was successful and we should navigate the user to their dashboard
			if(loggedUser != null) {
				
				System.out.println("[LOG] - Login successful, navigating to dashboard");
				
				// Change the currentUser of AppState to the User object returned from the login process
				AppState.setCurrentUser(loggedUser);
				
				// Navigate to the Account Menu screen
				return new AccountScreen().start(scan);
			}
			
			/*
			 * If loggedUser is null, the login attempt was unsuccessful and we should inform the user, and throw a
			 * UserNotFoundException for logging purposes.
			 */
			else {
				
				// Ensure that the current of AppState is null
				AppState.setCurrentUser(null);
				
				// throw a UserNotFoundException for logging purposes
				throw new UserNotFoundException("Invalid credentials provided!");
			}
			
		} catch(UserNotFoundException e) {
			
			/*
			 * Logging exceptions is good practice, you could even leverage a logging framework (Log4J) 
			 * to write log information to a separate file.
			 */
			System.out.println("[LOG] - Login attempt unsuccessful, invalid credentials");
			
		} catch (InputMismatchException ioe) {
			
			// If any exceptions are thrown, restart the app
			System.out.println("[ERROR] - Error reading input from console");
			System.out.println("[LOG] - Restarting application...");
			AppState.setRestartingApp(true);
			AppState.setAppRunning(false);
			return null;
		}
		
		System.out.println("[LOG] - Navigating back to home screen...");
		
		return new MenuScreen().start(scan);
	}

}
