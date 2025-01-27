package org.bachelorprojekt;

import java.lang.reflect.Method;

import org.bachelorprojekt.character.Character;
import org.bachelorprojekt.inventory.InventoryItem;
import org.bachelorprojekt.util.DB;
import org.junit.jupiter.api.Test;

public class DBTest {
	@Test
	public void test() {
		Character testChar = new Character("Testa");
		InventoryItem testItem = new InventoryItem(1, "Testa", 1);
		DB.add(testChar);
		DB.add(testItem);
		Character getChar = DB.get(Character.class, "Testa");
		InventoryItem getItem = DB.get(InventoryItem.class, testItem.getId());
		assert(charsEqual(testChar, getChar));
		assert(itemsEqual(testItem, getItem));
	}

	private boolean charsEqual(Character c1, Character c2) {
		Method[] methods = Character.class.getMethods();
		for(int i=0; i<methods.length; i++) {
			Method method = methods[i];
			String name = method.getName();
			if(name.startsWith("get") && !name.equals("getInventory")) {
				try {
					if (!method.invoke(c1).equals(method.invoke(c2))) {
						return false;
					}
				}
				catch(Exception e) {
				}
			}
		}
		int i1len = c1.getInventory().getItems().size();
		for(int i=0; i<i1len; i++) {
			InventoryItem i1 = c1.getInventory().getItem(i);
			InventoryItem i2 = c2.getInventory().getItem(i);
			if(!itemsEqual(i1, i2)) {
				return false;
			}
		}
		return true;
	}

	private boolean itemsEqual(InventoryItem i1, InventoryItem i2) {
		Method[] methods = InventoryItem.class.getMethods();
		for(int i=0; i<methods.length; i++) {
			Method m = methods[i];
			if(m.getName().startsWith("get")) {
				try {
					if (!m.invoke(i1).equals(m.invoke(i2))) {
						return false;
					}
				}
				catch(Exception e) {
				}
			}
		}
		return true;
	}
}
