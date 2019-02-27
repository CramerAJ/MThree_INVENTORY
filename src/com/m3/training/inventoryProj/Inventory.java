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
		String driver = prop.getProperty("driverLocal");
		String url = prop.getProperty("urlLocal");
		String username = prop.getProperty("usernameLocal");
		String password = prop.getProperty("passwordLocal");
		Connection conn = null;
		try {
			Class.forName(driver); // load Oracle driver
			conn = DriverManager.getConnection(url, username, password);

		} catch (SQLException | ClassNotFoundException e) {
			// bye throw something, with e nested inside that new exception
			System.out.println("We threw the exception " + e);
		}
		return conn;
	}

	public static void InsetItem(String itemName, int itemQuantity) throws IOException {
		int newItemID = 0;
		String newItemName = itemName;
		int newItemQuantity = itemQuantity;
		try {
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			stmt.executeQuery("INSERT INTO AJ08_INVENTORY(ID,ITEM_NAME,QUANTITIES) VALUES (" + newItemID + "," + "'"
					+ newItemName + "'" + "," + newItemQuantity + ")");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Delete Methods
	///////////////////////////////////////
	///////////////////////////////////////
	public static void DeleteItemID(int itemID) throws IOException {
		int deleteItemID = itemID;
		try {
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			Statement checkItem = conn.createStatement();

			try {
				checkItem.executeQuery("SELECT ID FROM AJ08_INVENTORY WHERE ID =" + deleteItemID + ")");

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			stmt.executeQuery("DELETE FROM AJ08_INVENTORY WHERE ID =" + deleteItemID + ")");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void DeleteItemNAME(int itemName) throws IOException {
		int deleteItemName = itemName;
		try {
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			// Do we want to both check if the item exists, also do we want to add a
			// confirmation
			stmt.executeQuery("DELETE FROM AJ08_INVENTORY WHERE ITEM_NAME =" + "'" + deleteItemName + "'" + ")");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws Exception {
		Inventory.InsetItem("Ball", 10);

	}

}