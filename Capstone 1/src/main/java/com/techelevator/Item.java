package com.techelevator;

public class Item {
	
	private String itemCode;
	private String itemName;
	private double itemPrice;
	private String itemType;
	private int itemInventory;

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		if (itemType.equals("B")){
			this.itemType = "Beverage";
		}
		else if (itemType.equals("A")){
			this.itemType = "Appetizer";
		}
		else if (itemType.equals("E")){
			this.itemType = "Entree";
		}
		else if (itemType.equals("D")){
			this.itemType = "Dessert";
		}
	}

	public int getItemInventory() {
		return itemInventory;
	}

	public void setItemInventory(int itemInventory) {
		this.itemInventory = itemInventory;
	}
}
