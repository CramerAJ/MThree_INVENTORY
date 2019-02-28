package com.m3.training.inventoryProj;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Inventory {

	private static final Logger LOGGER = LogManager.getLogger(Inventory.class);
	
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
			LOGGER.info("The Connection has been established:",conn);
			
		} catch (SQLException | ClassNotFoundException e) {
			// bye throw something, with e nested inside that new exception
			LOGGER.error(e);
		}
		return conn;
	}

	public static ItemWrapper getItemID(int itemID) throws IOException {
		try {
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			ResultSet resultItem = stmt.executeQuery("SELECT * FROM AJ08_INVENTORY WHERE ITEM_NAME=" + itemID);
			if (resultItem.next()) {
				int id = resultItem.getInt("ID");
				String name = resultItem.getString("ITEM_NAME");
				int quantities = resultItem.getInt("QUANTITIES");
				
				LOGGER.info("The Get Item by ID Query has executed:",id,name,quantities);
				
				// System.out.println(id);
				// System.out.println(name);
				// System.out.println(quantities);
				return new ItemWrapper(id, name, quantities);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error(e);
		}
		return null;
	}

	public static ItemWrapper getItemName(String itemName) throws IOException {
		try {
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			ResultSet resultItem = stmt.executeQuery("SELECT * FROM AJ08_INVENTORY WHERE ITEM_NAME=" + itemName);
			if (resultItem.next()) {
				int id = resultItem.getInt("ID");
				String name = resultItem.getString("ITEM_NAME");
				int quantities = resultItem.getInt("QUANTITIES");

				// System.out.println(id);
				// System.out.println(name);
				// System.out.println(quantities);
				LOGGER.info("The Get Item by Name Query has executed:",id,name,quantities);
				
				return new ItemWrapper(id, name, quantities);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.error(e);
			e.printStackTrace();
		}
		return null;
	}

	public static ItemWrapper insetItem(String itemName, int itemQuantity) throws IOException {
		int maxID = 1;
		String newItemName = itemName;
		int newItemQuantity = itemQuantity;
		try {
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			stmt.executeQuery(
					"INSERT INTO AJ08_INVENTORY(ID,ITEM_NAME,QUANTITIES) VALUES ((SELECT MAX(ID) FROM AJ08_INVENTORY +"
							+ maxID + ")," + "'" + newItemName + "'" + "," + newItemQuantity + ");");
			LOGGER.info("The Inset Item Query has executed: ",stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error(e);
		}
		return null;
	}

	// Delete Methods
	///////////////////////////////////////
	///////////////////////////////////////
	public static ItemWrapper deleteItemID(int itemID) throws IOException {
		int deleteItemID = itemID;
		try {
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			ItemWrapper check = getItemID(deleteItemID);
			if (check != null) {
				stmt.executeQuery("DELETE FROM AJ08_INVENTORY WHERE ID =" + deleteItemID + ";");
				LOGGER.info("The Delete Item by ID Query has executed: ",stmt);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error(e);
		}
		return null;
	}

	public static ItemWrapper deleteItemNAME(String itemName) throws IOException {
		String deleteItemName = itemName;
		try {
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			ItemWrapper check = getItemName(deleteItemName);
			if (check != null) {
				stmt.executeQuery("DELETE FROM AJ08_INVENTORY WHERE ITEM_NAME =" + "'" + deleteItemName + "'" + ";");
				LOGGER.info("The Delete Item by NAME Query has executed: ",stmt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error(e);
		}
		return null;
	}

	public static void subtractItemInventory(String itemName, int itemQuantity) throws IOException {
		ItemWrapper item = getItemName(itemName);
		// try {
		// Connection conn = getOracleConnection();
		// Statement stmt = conn.createStatement();
		// ResultSet rs = stmt.executeQuery("SELECT * FROM AJ08_INVENTORY WHERE ID = " +
		// itemName);
		//// while(rs.next()) {
		//// int id = rs.getInt("ID");
		//// String itemName = rs.getString("ITEM_NAME");
		//// int quantities = rs.getInt("QUANTITIES");
		//// ItemWrapper iw = new ItemWrapper(id, itemName, quantities);
		//// System.out.println(iw.getItemID()+iw.getItemName()+iw.getItemQuantity());
		// }
		//
		// } catch (SQLException e) {
		// e.printStackTrace();
		// } finally {
		//
		// }
	}

	public static void main(String args[]) throws Exception {
		
	}

}