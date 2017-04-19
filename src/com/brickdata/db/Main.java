package com.brickdata.db;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;

//import com.brickdata.db.beans.Location;
import com.brickdata.db.tables.ContainerManager;
import com.brickdata.db.tables.CustomerManager;
import com.brickdata.db.tables.LocationManager;
import com.brickdata.db.tables.WorkOrderManager;
import com.brickdata.util.InputHelper;

public class Main {

	public static void main(String[] args) throws Exception{
		String select;
		String location;
		String container;
		String containerId;
		String customerId;
		String altId;
		String companyName;
		String customerID;
		String companyLike;
		int workOrderID;
		System.out.println("Menu:");
		System.out.println("LOCATION");
		System.out.println("1. Add Location");
		System.out.println("2. Display all Locations");
		System.out.println("3. Select Location");
		System.out.println("4. Edit Location");
		System.out.println("CONTAINER");
		System.out.println("5. Add Container");
		System.out.println("6. Permanently Remove Container");
		System.out.println("7. Find Container by Number");
		System.out.println("8. Containers by Customer");
		System.out.println("9. Find Containers by Alt ID");
		System.out.println("CUSTOMER");
		System.out.println("10. Add Customer");
		System.out.println("11. Remove Customer");
		System.out.println("12. Find Customer by ID");
		System.out.println("13. Find Customer by Name");
		System.out.println("WORK ORDER");
		System.out.println("14. Create Work Order");
		System.out.println("15. Find Work Order by Number");
		System.out.println("20. End Program");
		select = InputHelper.getInput("Selection:");
		
		
		switch (select) {
		case "1":
			LocationManager.addLocation();			
			break;
		case "2":
			LocationManager.displayAllRows();
			break;
		case "3":
			location = InputHelper.getInput("Location:");
			System.out.println(LocationManager.getLocation(location));
			
			break;
		case "4":
			LocationManager.updateLocation();
			break;
		case "5":
			ContainerManager.addContainer();
			break;
		case "6":
			containerId = InputHelper.getInput("Container Number: ");
			ContainerManager.delete(containerId);
//			if(ContainerManager.delete(containerId)){
//				System.out.println("Container number " + containerId + "has been permanently removed.");
//			} else {
//				System.err.println("No records affected.");
//			}
			break;
		case "7":
			container = InputHelper.getInput("Container Number: ");
			System.out.println("Container:" + ContainerManager.getContainer(container));
			break;
		case "8":
			customerId = InputHelper.getInput("Customer ID: ");
			System.out.println(ContainerManager.displayContainersByCustomer(customerId));
			break;
		case "9":
			altId = InputHelper.getInput("Alt ID: ");
			System.out.println(ContainerManager.displayContainersByAltId(altId));
			break;
		case "10":
			CustomerManager.addCustomer();
			break;
		case "11":
			companyName = InputHelper.getInput("Company Name: ");
			CustomerManager.delete(companyName);
//			if(CustomerManager.delete(companyName)){
//				System.out.println("Customer " + companyName + "has been permanently removed.");
//			} else {
//				System.err.println("No records affected.");
//			}
		case "12":
			customerID = InputHelper.getInput("Customer ID: ");
			System.out.println("Customer:" + CustomerManager.getCustomer(customerID));
			break;
		case "13":
			companyLike = InputHelper.getInput("Company Name (can be incomplete name): ");
			System.out.println("Customer:" + CustomerManager.getCustomerLike(companyLike));
			break;
		case "14":
			WorkOrderManager.addworkOrder();
			break;
		case "15":
			workOrderID = InputHelper.getIntegerInput("Work Order Number: ");
			System.out.println("WO: " + WorkOrderManager.getWorkOrder(workOrderID));
			break;
		case "20":
			System.exit(0);

		default:System.out.println("Not a vaild selection."); 
			break;
		}
	}
}

	



