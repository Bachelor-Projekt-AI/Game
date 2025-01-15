package org.bachelorprojekt;

import java.lang.reflect.Method;

import org.bachelorprojekt.character.Character;
import org.bachelorprojekt.inventory.Item;
import org.bachelorprojekt.util.DB;
import org.junit.jupiter.api.Test;

public class DBTest {
	@Test
	public void test() {
		Character testChar = new Character("Testa");
		Item testItem = new Item("Testa", 1, 2, 3, true, false);
		DB.add(testChar);
		DB.add(testItem);
		Character getChar = DB.get(Character.class, "Testa");
		Item getItem = DB.get(Item.class, testItem.getId());
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
		for(int i=0; i<4; i++) {
			for(int j=0; j<4; j++) {
				Item i1 = c1.getInventory().getItem(i, j);
				Item i2 = c2.getInventory().getItem(i, j);
				if(!itemsEqual(i1, i2)) {
					return false;
				}
			}
		}
		return true;
	}

	private boolean itemsEqual(Item i1, Item i2) {
		Method[] methods = Item.class.getMethods();
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
