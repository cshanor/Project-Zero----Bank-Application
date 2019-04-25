package com.revature.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.models.Customer;
import com.revature.screens.CreateAccountScreen;
import com.revature.utils.ConnectionFactory;
import oracle.jdbc.OracleTypes;

public class CustomerDAO implements DAO<Customer>{

	private Scanner scan = new Scanner(System.in);

	@Override
	public Customer getById(int user_id) {
		
		Customer cust = new Customer();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			if(conn != null) {
				
				System.out.println("Connection established!");
				
				String sql = "SELECT * FROM users WHERE user_id = ?";
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, user_id);
				
				ResultSet rs = pstmt.executeQuery();
				
				while(rs.next()) {
					cust.setID(rs.getInt("user_id"));
					cust.setUsername(rs.getString("username"));
					cust.setPassword(rs.getString("password"));
					cust.setFirstName(rs.getString("first_name"));
					cust.setLastName(rs.getString("last_name"));
					cust.setRole(rs.getString("role"));
				}
			} else {
				throw new SQLException("A connection could not be established");
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}
		
		if(cust.getId() == 0) {
			return null;
		} else {
			return cust;
		}
	}
	
	public List<Customer> getByUsername(String username) {
		
		List<Customer> users = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			CallableStatement cstmt = conn.prepareCall("{CALL get_all_users(?)}");
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			
			ResultSet rs = (ResultSet) cstmt.getObject(1);
			//users = this.mapResultSet(rs);
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}

		return users;
	} // CallableStatement

	@Override
	public Customer add(Customer newUser) {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO users VALUES (0, ?, ?, ?, ?, ?)";
			
			String[] keys = new String[1];
			keys[0] = "user_id";
			
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setString(1, newUser.getUsername());
			pstmt.setString(2, newUser.getPassword());
			pstmt.setString(3, newUser.getFirstName());
			pstmt.setString(4, newUser.getLastName());
			pstmt.setString(5, newUser.getRole());
			
			int rowsInserted = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if(rowsInserted != 0) {
				while(rs.next()) {
					newUser.setID(rs.getInt(1));
				}
				conn.commit();
			}
		} catch (SQLIntegrityConstraintViolationException sicve) {
			System.out.println("[ERROR] - Username already taken!");
			CreateAccountScreen caScreen = new CreateAccountScreen();
			caScreen.start(scan);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		if(newUser.getId() == 0) {
			return null;
		}
		return newUser;
	}

	@Override
	public Customer update(Customer updatedUser) {
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			String sql = "UPDATE users SET password = ?, first_name = ?, last_name = ? WHERE user_id = ?";
		
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,  updatedUser.getPassword());
			pstmt.setString(2, updatedUser.getFirstName());
			pstmt.setString(3, updatedUser.getLastName());
			pstmt.setInt(4, updatedUser.getId());
			
			int rowsUpdated = pstmt.executeUpdate();
			
			if(rowsUpdated != 0) {
				conn.commit();
				return updatedUser;
			}
		} catch (SQLIntegrityConstraintViolationException sicve) {
			System.out.println("[ERROR] - Username already taken");
			CreateAccountScreen caScreen = new CreateAccountScreen();
			caScreen.start(scan);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean delete(int user_id) {

		// Attempt connection
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

			// Check the connection
			if (conn != null) {

				System.out.println("Connection Successfully Established");

				// Don't let JDBC auto commit
				conn.setAutoCommit(false);

				// Create a SQL string
				String sql = "DELETE FROM USERS WHERE USER_ID = ?";

				// Get a prepared statement object from the connection
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, user_id);

				// Execute the delete
				int result = pstmt.executeUpdate();

				if (result == 0)
					return false;

				return true;
			} else {
				throw new SQLException("A connection could not be established");
			}

		} catch (SQLIntegrityConstraintViolationException sicve) {
			sicve.printStackTrace();
		} catch (SQLException sqle) {
			System.out.println(sqle.getMessage());
		}
		// Return false if the user was not deleted
		return false;
	}
	
	public List<Customer> getAll() {
		
		ArrayList<Customer> users = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			String sql = "SELECT * FROM users";
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
		
			while(rs.next()) {
				Customer temp = new Customer();
				temp.setID(rs.getInt("user_id"));
				temp.setUsername(rs.getString("username"));
				temp.setPassword(rs.getString("password"));
				temp.setFirstName(rs.getString("first_name"));
				temp.setLastName(rs.getString("last_name"));
				temp.setRole(rs.getString("role"));
				users.add(temp);
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return users;
	} // Statement

	public Customer getByCredentials(String username, String password) {
		
		Customer cust = new Customer();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cust.setID(rs.getInt("user_id"));
				cust.setUsername(rs.getString("username"));
				cust.setPassword(rs.getString("password"));
				cust.setFirstName(rs.getString("first_name"));
				cust.setLastName(rs.getString("last_name"));
				cust.setRole(rs.getString("role"));
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		if(cust.getId() == 0) return null;
		return cust;
	}
}
