package com.brickdata.db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.brickdata.db.DBType;
import com.brickdata.db.DBUtil;
//import com.brickdata.db.beans.Container;
//import com.brickdata.db.beans.Location;
import com.brickdata.db.beans.WorkOrder;
import com.brickdata.util.InputHelper;

public class WorkOrderManager {

	static String add;
	public static int wo;
	public static boolean insert(WorkOrder bean) throws Exception {

		String sql = "INSERT into work_order (customer_id, origin_location, destination_location, datetime_created, created_by, priority, datetime_delivered, delivered_by, containers) VALUES (?, ?, ?, ? ,?, ?, ?, ? ,?)";
		ResultSet generatedKeys = null;
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				) {

			stmt.setString(1, bean.getCustomerId());
			stmt.setString(2, bean.getOriginLocation());
			stmt.setString(3, bean.getDestinationLocation());
			stmt.setDate(4, bean.getDateTimeCreated());
			stmt.setString(5, bean.getCreatedBy());
			stmt.setString(6, bean.getPriority());
			stmt.setDate(7, bean.getDateTimeDelivered());
			stmt.setString(8, bean.getDeliveredBy());
			stmt.setString(9, bean.getContainers());
			stmt.executeUpdate();
			generatedKeys = stmt.getGeneratedKeys();
			//while (rs.next()){
//				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
		            if (generatedKeys.next()) {
		                bean.setWoNumber(generatedKeys.getInt(1));
		            }
		            else {
		                throw new SQLException("Creating user failed, no ID obtained.");
		            
				
				//wo = rs.getInt(1);
				//System.out.println("Work Order: " + rs.getInt(1));
				//bean.setWoNumber(rs.getInt(1))
				//bean.setWoNumber(wo);
			}
			
			
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
			if (generatedKeys != null) generatedKeys.close();
		}
	}
		
	
	public static void addworkOrder() throws Exception {
		WorkOrder bean = new WorkOrder();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		//bean.setWoNumber();
		bean.setCustomerId(InputHelper.getInput("Customer ID: "));
		System.out.println(joinCustomer(bean.getCustomerId()));
		bean.setOriginLocation(InputHelper.getInput("Pickup Location: "));
		bean.setDestinationLocation(InputHelper.getInput("Delivery Location: "));
		bean.setDateTimeCreated(InputHelper.getDateInput("Create Date: " + dateFormat.format(date)));
		bean.setCreatedBy(InputHelper.getInput("Created By: "));
		bean.setPriority(InputHelper.getInput("Priority: "));
		bean.setDateTimeDelivered(InputHelper.getDateInput("Delivery Date Time: "));
		bean.setDeliveredBy(InputHelper.getInput("Delivered By: "));
		bean.setContainers(InputHelper.getInput("Containers: "));
		//bean.setWoNumber();
		
		boolean result = insert(bean);

		if (result){
			System.out.println("New Work Order Created");
			System.out.println("Work Order: " + bean.getWoNumber());
			System.out.println(joinCustomer(bean.getCustomerId()));
			System.out.println("Customer ID: " + bean.getCustomerId());
			System.out.println("Pickup Location: " + bean.getOriginLocation());
			System.out.println("Delivery Location: " + bean.getDestinationLocation());
			System.out.println("Create Date: " + bean.getDateTimeCreated());
			System.out.println("Created By: " + bean.getCreatedBy());
			System.out.println("Priority: " + bean.getPriority());
			System.out.println("Delivery Date Time: " + bean.getDateTimeDelivered());
			System.out.println("Delivered By: " + bean.getDeliveredBy());
			System.out.println("Containers: " + bean.getContainers());
						
			add = InputHelper.getInput("Create another Work Order? (Y/N)");
			if (add.equalsIgnoreCase("Y")){
				addworkOrder();
			}else{
				System.exit(0);
			}
			
		}

	}
	public static String joinCustomer(String customerID) throws SQLException {

		String sql = "SELECT * FROM customer LEFT JOIN work_order on customer.customer_id = work_order.customer_id WHERE customer.customer_id = ?";
		ResultSet rs = null;
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				){
			stmt.setString(1, customerID);
			rs = stmt.executeQuery();

			if (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getString("customer_id") + "\n");
				bf.append(rs.getString("company_name") + "\n");
				bf.append(rs.getString("contact") + "\n");
				bf.append(rs.getString("street_address") + "\n");
				bf.append(rs.getString("city") + "\n");
				bf.append(rs.getString("zip") + "\n");
				bf.append(rs.getString("phone") + "\nAuthorized Users:");
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
	public static String getWorkOrder(int workOrderId) throws SQLException {

		String sql = "SELECT * FROM work_order WHERE wo_number = ?";
		ResultSet rs = null;

		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setInt(1, workOrderId);
			rs = stmt.executeQuery();

			if (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getString("wo_number") + "\nCustomer ID:");
				bf.append(rs.getString("customer_id") + "\nPickup location:");
				bf.append(rs.getString("origin_location") + "\nDelivery location:");
				bf.append(rs.getString("destination_location") + "\nCreate date time:");
				bf.append(rs.getString("datetime_created") + "\nCreated by: ");
				bf.append(rs.getString("created_by") + "\nPriority: ");
				bf.append(rs.getString("priority") + "\nDelivery date time:");
				bf.append(rs.getString("datetime_delivered") + "\nDelivered By: ");
				bf.append(rs.getString("delivered_by") + "\nContainers: ");
				bf.append(rs.getString("containers"));
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
//	public static boolean update(WorkOrder bean) throws Exception {
//
//		String sql =
//				"SELECT * FROM work_order LEFT JOIN work_order on work_order.customer_id = container.customer_id +"
//				+ "UPDATE container SET location_number = ? WHERE container_number = ?";
//
//		try (
//				Connection conn = DBUtil.getConnection(DBType.MYSQL);
//				PreparedStatement stmt = conn.prepareStatement(sql);
//				){
//
//			stmt.setInt(1, bean.getWoNumber());
//			stmt.setString(2, bean.getContainerNumber());
//			
//			int affected = stmt.executeUpdate();
//			if (affected == 1){
//				return true;
//			} else {
//				return false;
//			}
//
//		}
//		catch(SQLException e) {
//			System.err.println(e);
//			return false;
//		}
//
//	}
	}
	public static WorkOrder createWOBean(int wo_number) throws SQLException {

		String sql = "SELECT * FROM work_order WHERE wo_number = ?";
		ResultSet rs = null;

		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setInt(1, wo_number);
			rs = stmt.executeQuery();

			if (rs.next()) {
				

				WorkOrder bean = new WorkOrder();
				bean.setWoNumber(rs.getInt("wo_number"));
				bean.setCustomerId("customer_id");
				bean.setOriginLocation("origin_location");
				bean.setDestinationLocation("destination_location");
				bean.setDateTimeCreated(rs.getDate("datetime_created"));
				bean.setCreatedBy("created_by");
				bean.setPriority("priority");
				bean.setDateTimeDelivered(rs.getDate("datetime_delivered"));
				bean.setDeliveredBy("delivered_by");
				bean.setContainers("containers");
				return bean;
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
