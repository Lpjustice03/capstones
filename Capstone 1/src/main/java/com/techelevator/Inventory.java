package com.techelevator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Inventory {

	private Map <String, Item> itemList = new TreeMap <String, Item>();
	private Set <String> keys = itemList.keySet();
	
	public Inventory(String fileName) {
		loadItemData(fileName);
	}
	
	private void loadItemData(String fileName) {
		FileRead fileReader = new FileRead(fileName);
		List<Item> itemFileList = fileReader.loadData();
		
	    for (Item i : itemFileList) {
	    	this.itemList.put(i.getItemCode(), i);
	    }	
	}
	
	public void printCateringItems() {
		System.out.print("*******************List of Catering Items Available*******************\n");
		for (String key: keys) {
			
			System.out.print(itemList.get(key).getItemCode() + "\t");
			System.out.print(itemList.get(key).getItemName() + "\t$");
			System.out.printf("%.2f", itemList.get(key).getItemPrice());
			if(itemList.get(key).getItemInventory() == 0) {
				System.out.println("\tSOLD OUT");
			}
			else {
				System.out.println("\t" + itemList.get(key).getItemInventory());
			}
		}
		System.out.print("*******************End of the List*******************\n");
	}

	public void updateInventoryAfterCheckout(Map<String, Integer> cartList, Set<String> cartKeys) {
		for (String key: cartKeys) {
			int newInventory = itemList.get(key).getItemInventory() - cartList.get(key);
			itemList.get(key).setItemInventory(newInventory);
		}
	}
	
	public Map<String, Item> getItemList() {
		return itemList;
	}
	
	public Set<String> getKeys() {
		return keys;
	}
}
