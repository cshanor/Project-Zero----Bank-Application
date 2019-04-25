package com.revature.dao;

import java.sql.*;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Customer;
import com.revature.utils.AppState;
import com.revature.utils.ConnectionFactory;

public class AccountDAO implements DAO<Account>{
	
	Customer cust = new Customer();
	List<Account> acctList = getAll();

	@Override
	public Account add(Account newAcct) {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO accounts VALUES (0, ?, ?)";
			
			String[] keys = new String[1];
			keys[0] = "acct_id";
			
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setInt(1, newAcct.getUser_id());
			pstmt.setDouble(2, newAcct.getBalance());
			
			int rowsInserted = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if(rowsInserted != 0) {
				while(rs.next()) {
					newAcct.setUser_id(rs.getInt(1));
				}
				conn.commit();
			}
		} catch (SQLIntegrityConstraintViolationException sicve) {
			System.out.println("[ERROR] - Account for " + AppState.getCurrentUser().getFirstName() + "already created!");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		if(newAcct.getUser_id() == 0) {
			return null;
		}
		return newAcct;
	}

	@Override
	public Account update(Account updatedAccount) {

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			String sql = "UPDATE accounts SET acct_id = ?, balance = ? WHERE user_id = ?";
		
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,  updatedAccount.getAcct_id());
			pstmt.setDouble(2, updatedAccount.getBalance());
			pstmt.setInt(3, updatedAccount.getUser_id());

			
			int rowsUpdated = pstmt.executeUpdate();
			
			if(rowsUpdated != 0) {
				conn.commit();
				return updatedAccount;
			}
		} catch (SQLIntegrityConstraintViolationException sicve) {
			System.out.println("[ERROR] - Username already taken");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Account> getAll() {

		Account acct = new Account();

		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {

			if(conn != null) {

				// System.out.println("Connection established!"); <-- calls this sysout twice.

				String sql = "SELECT * FROM accounts";

				Statement stmt = conn.prepareStatement(sql);

				ResultSet rs = stmt.executeQuery(sql);

				while(rs.next()) {
					acct.setAcct_id(rs.getInt("acct_id"));
					acct.setUser_id(rs.getInt("user_id"));
					acct.setBalance(rs.getDouble("balance"));
				}
			} else {
				throw new SQLException("A connection could not be established");
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}

		if(acct.getAcct_id() == 0) {
			return null;
		} else return (List<Account>) acctList;
	}

	@Override
	public Account getById(int user_id) {
		
		Account acct = new Account();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			if(conn != null) {

				// System.out.println("Connection established!"); <-- calls this sysout once

				String sql = "SELECT * FROM accounts WHERE user_id = ?";
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, user_id);
				
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					acct.setAcct_id(rs.getInt("acct_id"));
					acct.setUser_id(rs.getInt("user_id"));
					acct.setBalance(rs.getDouble("balance"));
				}
			} else {
				throw new SQLException("A connection could not be established");
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		if(acct.getAcct_id() == 0) {
			return null;
		} else {
			return acct;
		}
	}

	@Override
	public boolean delete(int id) {
		return false;
	}
}