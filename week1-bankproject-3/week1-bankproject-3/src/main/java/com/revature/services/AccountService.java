package com.revature.services;

import com.revature.dao.AccountDAO;
import com.revature.models.Account;
import com.revature.utils.AppState;

public class AccountService {

	private AccountDAO acctDao = new AccountDAO();

	public Account addAccount(Account newAcct) {
			return acctDao.add(newAcct);
	}

	public void makeDeposit(double deposit) {
		if(deposit < 0) {
			System.out.println("You cannot deposit a negative amount!");
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
