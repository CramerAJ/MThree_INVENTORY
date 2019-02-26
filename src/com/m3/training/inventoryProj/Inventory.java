package com.m3.training.inventoryProj;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.Statement;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Inventory {

	private List<ItemWrapper> inventory = new ArrayList<>();
	
	public static Connection getOracleConnection() throws IOException {
		File path = new File("config.properties");
		FileInputStream propFile = new FileInputStream(path.getPath());
		Properties prop = new Properties();
		prop.load(propFile);
		String driver = prop.getProperty("driverLnDev");
		String url = prop.getProperty("urlLnDev");
		String username = prop.getProperty("usernameLnDev");
		String password = prop.getProperty("passwordLnDev");
		Connection conn = null;
		try {
			Class.forName(driver); // load Oracle driver
			conn = DriverManager.getConnection(url, username, password);

		} catch (SQLException | ClassNotFoundException e) {
			// bye throw something, with e nested inside that new exception
			System.out.println("We threw an exception " + e);
		}
		return conn;
	}

	public static void InsetItem(int itemID, String itemName, int itemQuantity, Date itemDate) throws IOException {
		int newItemID = itemID;
		String newItemName = itemName;
		int newItemQuantity = itemQuantity;
		Date newItemDate = itemDate;
		try {
			Connection conn = getOracleConnection();	
			Statement stmt = conn.createStatement();
			stmt.executeQuery(
					"INSERT INTO AJ08_INVENTORY VALUES (" + newItemID + "," 
                    + newItemName + "," + newItemQuantity + ","+ newItemDate+ ","+ newItemDate +")");
			
		} catch (SQLException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void DeleteItem(int itemID, String itemName, int itemQuantity) throws IOException {
		int deleteItemID = itemID;
		String deleteItemName = itemName;
		int deleteItemQuantity = itemQuantity;
		
	}

	public static void main(String args[]) throws Exception {
		Date date = new Date(0);
		Inventory.InsetItem(10, "Hello", 10,date);
		
	}
}