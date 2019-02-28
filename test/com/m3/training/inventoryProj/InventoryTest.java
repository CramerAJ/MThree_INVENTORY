package com.m3.training.inventoryProj;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class InventoryTest {

	private static final long serialVersionUID = 1L;

	@Test
	void testGetDeleteByNameNull() throws IOException {
		String testName = null;
		String msg = "Illegal Argument Exception has been thrown";
		assertThrows(IllegalArgumentException.class, () -> {
			Inventory.deleteItemByName(testName);
		}, msg);
	}
	@Test
	void testGetDeleteByNameNotFound() throws IOException, NotInDBException {
		String testName = "Not FOUND";
		String msg = "Illegal Argument Exception has been thrown, Item Doesn't Exist";
		assertThrows(NotInDBException.class, () -> {
			Inventory.deleteItemByName(testName);
		}, msg);
	}
	
	@Test
	void testGetDeleteByIdNull() throws IOException{
		int testID = (Integer) null;
		String msg = "Illegal Argument Exception has been thrown";
		assertThrows(IllegalArgumentException.class, () -> {
			Inventory.deleteItemByID(testID);
		}, msg);
	}
	
	@Test
	void testGetDeleteByIDNotFound() throws IOException, NotInDBException {
		int testID = -1 ;
		String msg = "Illegal Argument Exception has been thrown, Item Doesn't Exist";
		assertThrows(NotInDBException.class, () -> {
			Inventory.deleteItemByID(testID);
		}, msg);
	}
}
