package com.techelevator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileRead {
	
	private String fileName = null;
	
	public FileRead(String fileName) {
		this.fileName = fileName;
	}
	
	public List<Item> loadData() {
		List<Item> cateringList = new ArrayList<Item>();
		File cateringSystem = new File (fileName);
		
		try (Scanner itemFileScanner = new Scanner(cateringSystem)){
			while (itemFileScanner.hasNext()) {
				String itemLine = itemFileScanner.nextLine();
				String[] item = itemLine.split("\\|");
				
				cateringList.add(createItemInventoryList(item));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return cateringList;
	}
	
	private Item createItemInventoryList(String[] fields) {
		
		Item item = new Item();
		item.setItemCode(fields[0]);
		item.setItemName(fields[1]);
		item.setItemPrice(Double.valueOf(fields[2]));
		item.setItemType(fields[3]);
		item.setItemInventory(50);
		
		return item;
	}
	
}



