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
import com.brickdata.db.beans.Container;
//import com.brickdata.db.beans.Location;
import com.brickdata.util.InputHelper;
//import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

public class ContainerManager {

	static String add;
	public static boolean insert(Container bean) throws Exception {

		String sql = "INSERT into container (accessed_by, alt_id, container_number, create_date, customer_id, description, destroy_date, last_accessed, location_number, next_access, sub_containers, super_container ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		ResultSet keys = null;
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				) {

			stmt.setString(1, bean.getAccessedby());
			stmt.setString(2, bean.getAltId());
			stmt.setString(3, bean.getContainerNumber());
			stmt.setDate(4, bean.getCreateDate());
			stmt.setString(5, bean.getCustomerId());
			stmt.setString(6, bean.getDescription());
			stmt.setDate(7, bean.getDestroyDate());
			stmt.setDate(8, bean.getLastAccessed());
			stmt.setString(9, bean.getLocationNumber());
			stmt.setDate(10, bean.getNextAccess());
			stmt.setString(11, bean.getSubContainers());
			stmt.setString(12, bean.getSuperContainer());
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
	public static void addContainer() throws Exception {
		Container bean = new Container();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		bean.setAccessedby(InputHelper.getInput("Accessed By: "));
		bean.setAltId(InputHelper.getInput("Alt ID: "));
		bean.setContainerNumber(InputHelper.getInput("Container Number: "));
		bean.setCreateDate(InputHelper.getDateInput("Create Date: " + dateFormat.format(date)));
		bean.setCustomerId(InputHelper.getInput("Customer ID: "));
		bean.setDescription(InputHelper.getInput("Description: "));
		bean.setDestroyDate(InputHelper.getDateInput("Destroy Date: "));
		bean.setLastAccessed(InputHelper.getDateInput("Last Accessed: "));
		bean.setLocationNumber(InputHelper.getInput("Location Number: "));
		bean.setNextAccess(InputHelper.getDateInput("Next Access: "));
		bean.setSubContainers(InputHelper.getInput("Sub-Containers: "));
		bean.setSuperContainer(InputHelper.getInput("Super-Container: "));

		boolean result = ContainerManager.insert(bean);

		if (result){
			System.out.println("New container created:");
			System.out.println(bean.getAccessedby());
			System.out.println("Alt ID: " + bean.getAltId());
			System.out.println("Container Number: " + bean.getContainerNumber());
			System.out.println("Create Date: " + bean.getCreateDate());
			System.out.println("Customer ID: " + bean.getCustomerId());
			System.out.println("Description: " + bean.getDescription());
			System.out.println("Destroy Date: " + bean.getDestroyDate());
			System.out.println("Last Accessed: " + bean.getLastAccessed());
			System.out.println("Location Number: " + bean.getLocationNumber());
			System.out.println("Next Access: " + bean.getNextAccess());
			System.out.println("Sub-Containers: " + bean.getSubContainers());
			System.out.println("Super-Containers: "+ bean.getSuperContainer());
			
			add = InputHelper.getInput("Add another container? (Y/N)");
			if (add.equalsIgnoreCase("Y")){
				addContainer();
			}else{
				System.exit(0);
			}
			
		}

	}
	
	public static String getContainer(String container_number) throws SQLException {

		String sql = "SELECT * FROM container WHERE container_number = ?";
		ResultSet rs = null;

		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setString(1, container_number);
			rs = stmt.executeQuery();

			if (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getString("container_number") + "     Location:");
				bf.append(rs.getString("location_number") + "   Customer:");
				bf.append(rs.getString("customer_id") + "     Alt ID:");
				bf.append(rs.getString("alt_id"));
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
	public static boolean delete(String containerId) throws Exception {

		String sql = "DELETE FROM container WHERE container_number = ?";
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			
			stmt.setString(1, containerId);
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
	
	public static String displayContainersByCustomer(String customer_id) throws SQLException {

		String sql = "SELECT * FROM container WHERE customer_id = ?";
		ResultSet rs = null;

		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setString(1, customer_id);
			rs = stmt.executeQuery();
			StringBuffer bf = new StringBuffer();
			while (rs.next()) {
				bf.append(rs.getString("customer_id") + "	Container:");
				bf.append(rs.getString("container_number") + "	Location:");
				bf.append(rs.getString("location_number") + "    Alt ID:");
				bf.append(rs.getString("alt_id") + "    Desc:");
				bf.append(rs.getString("description") + "    Created:");
				bf.append(rs.getString("create_date")+ ("\n" + "    Last Access:" ));
				bf.append(rs.getString("last_accessed") + "    Next Access:");
				bf.append(rs.getString("next_access") + "    Accessed by:");
				bf.append(rs.getString("accessed_by") + "    Destroy");
				bf.append(rs.getString("destroy_date") + "    Subs");
				bf.append(rs.getString("sub_containers") + "    Super");
				bf.append(rs.getString("super_container")+ ("\n") );
			} 
			return bf.toString();

		} catch (SQLException e) {
			System.err.println(e);
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}

	public static String displayContainersByAltId(String alt_id) throws SQLException {

		String sql = "SELECT * FROM container WHERE alt_id = ?";
		ResultSet rs = null;

		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setString(1, alt_id);
			rs = stmt.executeQuery();
			StringBuffer bf = new StringBuffer();
			while (rs.next()) {
				bf.append(rs.getString("customer_id") + "	Container:");
				bf.append(rs.getString("container_number") + "	Location:");
				bf.append(rs.getString("location_number") + "    Alt ID:");
				bf.append(rs.getString("alt_id") + "\n");
//				bf.append(rs.getString("description") + "    Created:");
//				bf.append(rs.getString("create_date")+ ("\n" + "    Last Access:" ));
//				bf.append(rs.getString("last_accessed") + "    Next Access:");
//				bf.append(rs.getString("next_access") + "    Accessed by:");
//				bf.append(rs.getString("accessed_by") + "    Destroy");
//				bf.append(rs.getString("destroy_date") + "    Subs");
//				bf.append(rs.getString("sub_containers") + "    Super");
//				bf.append(rs.getString("super_container")+ ("\n") );
			} 
			return bf.toString();

		} catch (SQLException e) {
			System.err.println(e);
			return null;
		} finally {
			if (rs != null) {
				rs.close();
			}
		}
	}
	public static Container getRow(String container_number) throws SQLException {

		String sql = "SELECT * FROM container WHERE container_number = ?";
		ResultSet rs = null;

		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setString(1, container_number);
			rs = stmt.executeQuery();

			if (rs.next()) {
				

				Container bean = new Container();
				bean.setContainerNumber("container_number");
				bean.setAltId("alt_id");
				bean.setLocationNumber("location_number");
				bean.setCustomerId("customer_id");
				bean.setCreateDate(rs.getDate("create_date"));
				bean.setLastAccessed(rs.getDate("last_accessed"));
				bean.setNextAccess(rs.getDate("next_access"));
				bean.setAccessedby("accessed_by");
				bean.setDestroyDate(rs.getDate("destroy_date"));
				bean.setDescription("description");
				bean.setSubContainers("sub_containers");
				bean.setSuperContainer("super_container");
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
//	public static boolean update(Container bean) throws Exception {
//
//		String sql =
//				"UPDATE container SET location_number = ? WHERE container_number = ?";
//
//		try (
//				Connection conn = DBUtil.getConnection(DBType.MYSQL);
//				PreparedStatement stmt = conn.prepareStatement(sql);
//				){
//
//			stmt.setString(1, WorkOrderManager.getRow());
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
