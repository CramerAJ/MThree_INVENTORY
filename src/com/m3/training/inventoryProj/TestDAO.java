package com.m3.training.inventoryProj;

import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDAO {

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
			return conn;
		} catch (SQLException | ClassNotFoundException e) {
			// bye throw something, with e nested inside that new exception
			System.out.println("We threw an exception " + e);
		}
		
		return conn;
	}
	
	public static void InsetItem(int itemID, String itemName,int itemQuantity) {
		int newItemID=itemID;
		String newItemName=itemName;
		int newItemQuantity=itemQuantity;
		
		System.out.println(newItemID+" " + newItemName +" "+newItemQuantity);
		
		
	}

	public static void main(String args[]) throws Exception {
		
		TestDAO item = new TestDAO();
		item.InsetItem(10,"Hello",10);
		

	}

}