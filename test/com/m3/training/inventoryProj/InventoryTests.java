package com.m3.training.inventoryProj;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class InventoryTests {

//	@Before
//	public void createTestObject() {
//		
//	}

	@Test
	void getConnectionTest() {
		TestDAO item = new TestDAO();
		assertEquals(item != null, true);
	}

	@Test
	void crudTest() {
		TestDAO item = new TestDAO();
//		conn.setAutoCommit(false);
//		try {
//			
//			dao.insertInventory()
//			
//		}

	}
}
