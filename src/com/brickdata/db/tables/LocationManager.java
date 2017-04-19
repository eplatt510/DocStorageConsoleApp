package com.brickdata.db.tables;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.brickdata.db.DBType;
import com.brickdata.db.DBUtil;
import com.brickdata.db.beans.Location;
import com.brickdata.util.InputHelper;



public class LocationManager {

	public static void displayAllRows() throws SQLException {

		String sql = "SELECT location_number, quantity FROM location";
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				){

			System.out.println("Location Quantity");
			while (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getString("location_number") + ":    ");
				bf.append(rs.getInt("quantity") );
				//				bf.append(rs.getString("password"));
				System.out.println(bf.toString());
			}
		}
	}


	public static Location getRow(String location_number) throws SQLException {

		String sql = "SELECT * FROM location WHERE location_number = ?";
		ResultSet rs = null;

		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setString(1, location_number);
			rs = stmt.executeQuery();

			if (rs.next()) {
				//					StringBuffer bf = new StringBuffer();
				//					bf.append(rs.getString("location_number") + ":    ");
				//					bf.append(rs.getInt("quantity") );
				//					return bf.toString();

				Location bean = new Location();
				bean.setLocationNumber("location_number");
				bean.setQuantity(rs.getInt("quantity"));
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
	public static String getLocation(String location_number) throws SQLException {

		String sql = "SELECT * FROM location WHERE location_number = ?";
		ResultSet rs = null;

		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){
			stmt.setString(1, location_number);
			rs = stmt.executeQuery();

			if (rs.next()) {
				StringBuffer bf = new StringBuffer();
				bf.append(rs.getString("location_number") + ":    ");
				bf.append(rs.getInt("quantity") );
				return bf.toString();

				//				Location bean = new Location();
				//				bean.setLocationNumber("location_number");
				//				bean.setQuantity(rs.getInt("quantity"));
				//return bean;
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

	public static boolean insert(Location bean) throws Exception {

		String sql = "INSERT into location (location_number, quantity) " +
				"VALUES (?, ?)";
		ResultSet keys = null;
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				) {

			stmt.setString(1, bean.getLocationNumber());
			stmt.setInt(2, bean.getQuantity());
			int affected = stmt.executeUpdate();

			if (affected == 1) {
				return true;
				//				keys = stmt.getGeneratedKeys();
				//				keys.next();
				//				int newKey = keys.getInt(1);
				//				bean.setLocationNumber(newKey);
			} else {
				System.err.println("No rows affected");
				return false;
			}

		} catch (SQLException e) {
			System.err.println(e);
			return false;
		} finally{
			if (keys != null) keys.close();
		}
	}
	public static void addLocation() throws Exception {
		Location bean = new Location();
		bean.setLocationNumber(InputHelper.getInput("Location Number: "));
		bean.setQuantity(InputHelper.getIntegerInput("Quantity: "));

		boolean result = LocationManager.insert(bean);

		if (result){
			System.out.println("New row with location number " 
					+ bean.getLocationNumber() + " and quantity " + bean.getQuantity() + " was created.");

		}
	}
	public static void updateLocation() throws Exception{

		displayAllRows();


		String location_number = InputHelper.getInput("Select a row to update: ");

		Location bean = getRow(location_number);
		if (bean == null) {
			System.err.println("Row not found");
			return;
		}

		int quantity = InputHelper.getIntegerInput("Enter new quantity: ");
		bean.setQuantity(quantity);

		if (update(bean)){
			System.out.println("Success!");
		} else{
			System.err.println("whoops!");

		}


	}
	public static boolean update(Location bean) throws Exception {

		String sql =
				"UPDATE location SET quantity = ? WHERE location_number = ?";

		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL);
				PreparedStatement stmt = conn.prepareStatement(sql);
				){

			stmt.setInt(1, bean.getQuantity());
			stmt.setString(2, bean.getLocationNumber());
			//stmt.setInt(3, bean.getAdminId());

			int affected = stmt.executeUpdate();
			if (affected == 1){
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

}



