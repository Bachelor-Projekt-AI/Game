package org.bachelorprojekt;

import java.io.File;
import java.lang.reflect.Method;

import org.bachelorprojekt.character.Character;
import org.bachelorprojekt.inventory.Item;
import org.bachelorprojekt.util.DB;
import org.junit.jupiter.api.Test;

public class DBTest {
	@Test
	public void test() {
		new File("gamestate.db").delete();
		Character testChar = new Character("Testa");
		Item testItem = new Item("Testa", 1, 2, 3, true, false);
		DB.add(testChar);
		DB.add(testItem);
		Character getChar = DB.get(Character.class, "Testa");
		Item getItem = DB.get(Item.class, testItem.getId());
		assert(charsEqual(testChar, getChar));
		assert(itemsEqual(testItem, getItem));
		new File("gamestate.db").delete();
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
			Item i1 = c1.getInventory().getItem(i);
			Item i2 = c2.getInventory().getItem(i);
			if(!itemsEqual(i1, i2)) {
				return false;
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
