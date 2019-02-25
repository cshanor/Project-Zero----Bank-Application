package com.revature.services;

import java.util.Scanner;

import com.revature.dao.CustomerDAO;
import com.revature.models.Customer;

public class CustomerService {
	
	Scanner scan = new Scanner(System.in);
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
	
	public boolean verifyNameInput(String name) {
		
		char[] fnChars = name.toCharArray();
		for(char c : fnChars) {
			if(!Character.isLetter(c)) {
				System.out.println("[ERROR] Your first or last name must contain only letters. Reloading menu...");

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
