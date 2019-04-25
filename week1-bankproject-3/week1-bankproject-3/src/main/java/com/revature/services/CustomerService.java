package com.revature.services;

import com.revature.dao.CustomerDAO;
import com.revature.models.Customer;
import com.revature.screens.CreateAccountScreen;

import java.util.Scanner;

public class CustomerService {


	private CustomerDAO custDao = new CustomerDAO();
	// CreateAccountScreen caScreen = new CreateAccountScreen();
		
	public Customer getUserByCredentials(String username, String password) {
		
		if(!username.equals("") && !password.equals("")) {
			return custDao.getByCredentials(username, password);
		}
		
		return null;
	}
	
	public Customer addUser(Customer newUser) {
		
		if(newUser.getUsername().equals("") || newUser.getPassword().equals("") || newUser.getFirstName().equals("") || newUser.getLastName().equals("")) {
			return null;
		}
		
		return custDao.add(newUser);
	}
	
	public Customer updateUser(Customer updatedUser) {
		
		if(updatedUser.getUsername().equals("") || updatedUser.getPassword().equals("") || updatedUser.getFirstName().equals("") || updatedUser.getLastName().equals("")) {
			return null;
		}
		
		return custDao.update(updatedUser);
	}
	
	public boolean deleteUser(int userId) {
		return custDao.delete(userId);
	}


	/**
	 * The verifyNameInput() method will ensure that the first name and last name entered by the
	 * user is only composed of letters.
	 *
	 * @param name
	 * @return
	 */
	public boolean verifyNameInput(String name) {

		if(name.length() == 0) return false;
		char[] fnChars = name.toCharArray();
		for(char c : fnChars) {

			// Convert the passed String into an array of characters, then use the built in
			// Character.isLetter() method to verify only letters were contained in the character array.
			if(!Character.isLetter(c) ) {
				System.out.println("[ERROR] Your first or last name must contain only letters. Resetting fields...");

				try {
					Thread.sleep(5000);
					return false;
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				} 
			}
		}
		return true;
	}
}
