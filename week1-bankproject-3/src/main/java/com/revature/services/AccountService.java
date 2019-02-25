package com.revature.services;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import com.revature.dao.AccountDAO;
import com.revature.dao.CustomerDAO;
import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.utils.AppState;

public class AccountService {
	
	Account acct = new Account();
	AccountDAO acctDao = new AccountDAO();
	CustomerDAO custDao = new CustomerDAO();
	Scanner scan = new Scanner(System.in);
	File file = new File("src/main/resources/money.txt");
	List<Customer> custList = custDao.getAll();
	
	int balance = 0;
	
	public Account addAccount(Account newAcct) {
		
			return acctDao.add(newAcct);
	}

	public void makeDeposit(double deposit) {
		if(deposit < 0) {
			System.out.println("You cannot deposit a negative amount!");
			return;
		} else {
			Account acct = acctDao.getById(AppState.getCurrentUser().getId());
			acct.setBalance(acct.getBalance() + deposit);
			acctDao.update(acct);
		}
		
	}
	
	public void makeWithdrawal(double withdrawal) {
	
		Account acct = acctDao.getById(AppState.getCurrentUser().getId());
		
		if(withdrawal > acct.getBalance()) {
			System.out.println("You have insufficient funds to withdraw that amount!");
		} else {
			acct.setBalance(acct.getBalance() - withdrawal);
			acctDao.update(acct);
		}
	}
}
