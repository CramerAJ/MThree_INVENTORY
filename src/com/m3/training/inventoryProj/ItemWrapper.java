package com.m3.training.inventoryProj;

import java.sql.Date;

public class ItemWrapper {

	private int itemID;
	private String itemName;
	private int itemQuantity;
	
	public ItemWrapper(int itemID, String itemName, int itemQuantity) {
		this.itemID = itemID;
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
	}
	
	public int getItemID() {
		return itemID;
	}
	public void setItemID(int itemID) {
		this.itemID = itemID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	
}
