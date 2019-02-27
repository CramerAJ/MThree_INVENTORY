package com.m3.training.inventoryProj;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Inventory {

	private List<ItemWrapper> inventory = new ArrayList<>();

	public static Connection getOracleConnection() throws IOException {
		File path = new File("config.properties");
		FileInputStream propFile = new FileInputStream(path.getPath());
		Properties prop = new Properties();
		prop.load(propFile);
<<<<<<< HEAD
		String driver = prop.getProperty("driverLocal");
		String url = prop.getProperty("urlLocal");
		String username = prop.getProperty("usernameLocal");
		String password = prop.getProperty("passwordLocal");
=======
		String driver = prop.getProperty("driverAlumni");
		String url = prop.getProperty("urlAlumni");
		String username = prop.getProperty("usernameAlumni");
		String password = prop.getProperty("passwordAlumni");
>>>>>>> ff22b2a1142f22f6fb7ad45935b47131d2dd963f
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
	
	public static ItemWrapper getItem(String itemName) throws IOException {
        try {
            Connection conn = getOracleConnection();
            Statement stmt = conn.createStatement();
            ResultSet resultItem = stmt.executeQuery("SELECT * FROM AJ08_INVENTORY WHERE ITEM_NAME=" + itemName);
            if (resultItem.next()) {
                int id = resultItem.getInt("ID");
                String name = resultItem.getString("ITEM_NAME");
                int quantities = resultItem.getInt("QUANTITIES");
                
//                System.out.println(id);
//                System.out.println(name);
//                System.out.println(quantities);
                return new ItemWrapper(id, name, quantities);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

<<<<<<< HEAD
	public static void InsetItem(String itemName, int itemQuantity) throws IOException {
		int newItemID = 0;
=======
	public static void InsetItem(int itemID, String itemName, int itemQuantity) throws IOException {
		int newItemID = itemID;
>>>>>>> ff22b2a1142f22f6fb7ad45935b47131d2dd963f
		String newItemName = itemName;
		int newItemQuantity = itemQuantity;
		try {
			Connection conn = getOracleConnection();
			Statement stmt = conn.createStatement();
<<<<<<< HEAD
			stmt.executeQuery("INSERT INTO AJ08_INVENTORY(ID,ITEM_NAME,QUANTITIES) VALUES (" + newItemID + "," + "'"
					+ newItemName + "'" + "," + newItemQuantity + ")");

		} catch (SQLException e) {
=======
			stmt.executeQuery(
					"INSERT INTO AJ08_INVENTORY VALUES (" + newItemID + "," 
                    + newItemName + "," + newItemQuantity + ")");
		} catch (SQLException e ) {
>>>>>>> ff22b2a1142f22f6fb7ad45935b47131d2dd963f
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
	
	public static void subtractItemInventory(String itemName, int itemQuantity) throws IOException {
		ItemWrapper item = getItem(itemName);
		
		
//		try {
//			Connection conn = getOracleConnection();
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT * FROM AJ08_INVENTORY WHERE ID = " + itemName);
////				while(rs.next()) {
////					int id = rs.getInt("ID");
////					String itemName = rs.getString("ITEM_NAME");
////					int quantities = rs.getInt("QUANTITIES");
////					ItemWrapper iw = new ItemWrapper(id, itemName, quantities);
////					System.out.println(iw.getItemID()+iw.getItemName()+iw.getItemQuantity());
//				}
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			
//		}
//	}

	public static void main(String args[]) throws Exception {
<<<<<<< HEAD
		Inventory.InsetItem("Ball", 10);

=======
//		Inventory.InsetItem(1, "'Baseball'", 10);
		Inventory.subtractItemInventory("'Baseball'", 5);
>>>>>>> ff22b2a1142f22f6fb7ad45935b47131d2dd963f
	}

}