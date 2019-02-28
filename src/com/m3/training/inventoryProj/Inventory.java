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

	private static List<ItemWrapper> inventory = new ArrayList<>();

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
		LOGGER.trace(driver, url, username, password, conn);
		try {
			Class.forName(driver); // load Oracle driver
			conn = DriverManager.getConnection(url, username, password);
			LOGGER.info("The Connection has been established:", conn);

		} catch (SQLException | ClassNotFoundException e) {
			// bye throw something, with e nested inside that new exception
			LOGGER.fatal(e);
		}
		return conn;
	}

	public static ItemWrapper getItemByID(int itemID) throws IOException,NotInDBException {
		try {
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			ResultSet resultItem = stmt.executeQuery("SELECT * FROM AJ08_INVENTORY WHERE ITEM_NAME=" + itemID);
			LOGGER.trace(conn);
			if (resultItem.next()) {
				int id = resultItem.getInt("ID");
				String name = resultItem.getString("ITEM_NAME");
				int quantities = resultItem.getInt("QUANTITIES");

				LOGGER.info("The Get Item by ID Query has executed:", id, name, quantities);

				// System.out.println(id);
				// System.out.println(name);
				// System.out.println(quantities);
				return new ItemWrapper(id, name, quantities);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.fatal(e);
		}
		return null;
	}

	public static ItemWrapper getItemByName(String itemName) throws IOException,NotInDBException {
		try {
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			ResultSet resultItem = stmt
					.executeQuery("SELECT * FROM AJ08_INVENTORY WHERE ITEM_NAME=" + "'" + itemName + "'");
			LOGGER.trace(conn);
			if (resultItem.next()) {
				int id = resultItem.getInt("ID");
				String name = resultItem.getString("ITEM_NAME");
				int quantities = resultItem.getInt("QUANTITIES");

				// System.out.println(id);
				// System.out.println(name);
				// System.out.println(quantities);
				LOGGER.info("The Get Item by Name Query has executed:", id, name, quantities);

				return new ItemWrapper(id, name, quantities);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			LOGGER.fatal(e);
			e.printStackTrace();
		}
		return null;
	}

	public static ItemWrapper insertItem(String itemName, int itemQuantity) throws IOException,NotInDBException {
		int maxID = 1;
		String newItemName = itemName;
		int newItemQuantity = itemQuantity;
		try {
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			LOGGER.trace(conn);
			stmt.executeQuery(
					"INSERT INTO AJ08_INVENTORY(ID,ITEM_NAME,QUANTITIES) VALUES ((SELECT MAX(ID) FROM AJ08_INVENTORY)+"
							+ maxID + "," + "'" + newItemName + "'" + "," + newItemQuantity + ")");
			// LOGGER.info("The Inset Item Query has executed: ",stmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.fatal(e);

		}
		return null;
	}

	// Delete Methods
	///////////////////////////////////////
	///////////////////////////////////////
	public static ItemWrapper deleteItemByID(int itemID) throws IOException,NotInDBException {
		int deleteItemID = itemID;
		try {
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			ItemWrapper check = getItemByID(deleteItemID);
			LOGGER.trace(conn);
			if (check != null) {
				stmt.executeQuery("DELETE FROM AJ08_INVENTORY WHERE ID =" + deleteItemID + ";");
				LOGGER.info("The Delete Item by ID Query has executed: ", stmt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.fatal(e);

		}
		return null;
	}

	public static ItemWrapper deleteItemByName(String itemName) throws IOException,NotInDBException {
		String deleteItemName = itemName;
		try {
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			ItemWrapper check = getItemByName(deleteItemName);

			if (check != null) {
				stmt.executeQuery("DELETE FROM AJ08_INVENTORY WHERE ITEM_NAME =" + "'" + deleteItemName + "'" + ";");
				LOGGER.info("The Delete Item by NAME Query has executed: ", stmt);
			}
			throw new NotInDBException("Item name does not exist in database");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error(e);
			LOGGER.fatal(e);
		}
		return null;
	}

	public static List<ItemWrapper> getInventory() {
		return inventory;
	}

	public static void refreshInMemoryItemList() throws IOException {
		try {
			inventory.clear();
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			System.out.println("SELECT * FROM AJ08_INVENTORY");
			ResultSet resultItem = stmt.executeQuery("SELECT * FROM AJ08_INVENTORY");
			LOGGER.trace(conn);
			while (resultItem.next()) {
				int id = resultItem.getInt("ID");
				String name = resultItem.getString("ITEM_NAME");
				int quantities = resultItem.getInt("QUANTITIES");
				inventory.add(new ItemWrapper(id, name, quantities));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ItemWrapper updateItemInventory(String itemName, int itemQuantity) throws IOException,NotInDBException {
		try {
			ItemWrapper item = getItemByName(itemName);
			item.setItemQuantity(itemQuantity + item.getItemQuantity());

			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
			stmt.executeQuery("UPDATE AJ08_INVENTORY SET QUANTITIES = " + item.getItemQuantity() + " WHERE ITEM_NAME = "
					+ "'" + item.getItemName() + "'");
			ItemWrapper itemUpdated = getItemByName(itemName);

			return itemUpdated;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.fatal(e);
		}
		return null;
	}

	public static void main(String args[]) throws IOException {

		//

	}

}