package com.brickdata.db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;




import com.brickdata.db.DBType;
import com.brickdata.db.DBUtil;
import com.brickdata.db.beans.Customer;
import com.brickdata.util.InputHelper;
//import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class CustomerManager {
	
	static String add;
	public static void addCustomer() throws Exception {
		Customer bean = new Customer();
//		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//		Date date = new Date();
		bean.setCustomerId(InputHelper.getInput("Customer ID: "));
		bean.setCompanyName(InputHelper.getInput("Company Name: "));
		bean.setContact(InputHelper.getInput("Contact: "));
		bean.setStreetAddress(InputHelper.getInput("Street Address: "));
		bean.setCity(InputHelper.getInput("City: "));
		bean.setZip(InputHelper.getInput("Zip Code: "));
		bean.setPhone(InputHelper.getInput("Phone: "));
		bean.setAuthUsers(InputHelper.getInput("Authorized Users: "));

		boolean result = CustomerManager.insert(bean);

		if (result){
			System.out.println("New Customer created:");
			System.out.println(bean.getCompanyName());
			System.out.println("Alt ID: " + bean.getContact());
			System.out.println("Container Number: " + bean.getStreetAddress());
			System.out.println("Create Date: " + bean.getCity());
			System.out.println("Customer ID: " + bean.getZip());
			System.out.println("Description: " + bean.getPhone());
			System.out.println("Destroy Date: " + bean.getAuthUsers());
			
			add = InputHelper.getInput("Add another Customer? (Y/N)");
			if (add.equalsIgnoreCase("Y")){
				addCustomer();
			}else{
				System.exit(0);
			}
			
		}

	}
	public static boolean insert(Customer bean) throws Exception {

		String sql = "INSERT into customer (customer_id, company_name, contact, street_address, city, zip, phone, auth_users) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		ResultSet keys = null;
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				) {

			stmt.setString(1, bean.getCustomerId());
			stmt.setString(2, bean.getCompanyName());
			stmt.setString(3, bean.getContact());
			stmt.setString(4, bean.getStreetAddress());
			stmt.setString(5, bean.getCity());
			stmt.setString(6, bean.getZip());
			stmt.setString(7, bean.getPhone());
			stmt.setString(8, bean.getAuthUsers());

			int affected = stmt.executeUpdate();

			if (affected == 1) {
				return true;
			} else {
				System.err.println("No rows affected");
				return false;
			}

//		} catch (MySQLIntegrityConstraintViolationException) {
//			System.err.println("Container already exists in system.");
//			return false;
		} catch (SQLException e) {
			System.err.println(e);
			return false;
		} finally{
			if (keys != null) keys.close();
		}
	}
	
	public static boolean delete(String companyName) throws Exception {

		String sql = "DELETE FROM customer WHERE company_name = ?";
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			
			stmt.setString(1, companyName);
			int affected = stmt.executeUpdate();
			
			if(affected == 1){
				return true;
			} else {
				return false;
			}
			
		}
		catch(SQLException e) {
			System.err.println(e);
			return false;
		}

	}
	public static String getCustomer(String customerId) throws SQLException {

		String sql = "SELECT * FROM customer WHERE customer_id = ?";
		ResultSet rs = null;

		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setString(1, customerId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getString("customer_id") + "     Company Name:");
				bf.append(rs.getString("company_name") + "   Contact:");
				bf.append(rs.getString("contact") + "     Street Address:");
				bf.append(rs.getString("street_address") + "     City:");
				bf.append(rs.getString("city") + "     Zip:");
				bf.append(rs.getString("zip") + "     Phone:");
				bf.append(rs.getString("phone") + "     Authorized Users:");
				bf.append(rs.getString("auth_users"));
				return bf.toString();
				
			} else {
				return null;
			}

		} catch (SQLException e) {
			System.err.println(e);
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
	public static String getCustomerLike(String companyLike) throws SQLException {

		String sql = "SELECT * FROM customer WHERE company_name LIKE ?";
		ResultSet rs = null;

		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setString(1, '%' + companyLike + '%');
			rs = stmt.executeQuery();

			if (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getString("customer_id") + "     Company Name:");
				bf.append(rs.getString("company_name") + "   Contact:");
				bf.append(rs.getString("contact") + "     Street Address:");
				bf.append(rs.getString("street_address") + "     City:");
				bf.append(rs.getString("city") + "     Zip:");
				bf.append(rs.getString("zip") + "     Phone:");
				bf.append(rs.getString("phone") + "     Authorized Users:");
				bf.append(rs.getString("auth_users"));
				return bf.toString();
				
			} else {
				return null;
			}

		} catch (SQLException e) {
			System.err.println(e);
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
}
