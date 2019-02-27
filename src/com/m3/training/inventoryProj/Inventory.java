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
		String driver = prop.getProperty("driverAlumni");
		String url = prop.getProperty("urlAlumni");
		String username = prop.getProperty("usernameAlumni");
		String password = prop.getProperty("passwordAlumni");
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

	public static void InsetItem(int itemID, String itemName, int itemQuantity) throws IOException {
		int newItemID = itemID;
		String newItemName = itemName;
		int newItemQuantity = itemQuantity;
		try {
			Connection conn = getOracleConnection();	
			Statement stmt = conn.createStatement();
			stmt.executeQuery(
					"INSERT INTO AJ08_INVENTORY VALUES (" + newItemID + "," 
                    + newItemName + "," + newItemQuantity + ")");
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
//		Inventory.InsetItem(1, "'Baseball'", 10);
		Inventory.subtractItemInventory("'Baseball'", 5);
	}
}