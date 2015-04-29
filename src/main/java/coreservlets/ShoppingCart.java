package coreservlets;

import java.util.*;

/**
 * A shopping cart data structure used to track orders. The OrderPage servlet
 * associates one of these carts with each user session.
 * <P>
 * Taken from Core Servlets and JavaServer Pages 2nd Edition from Prentice Hall
 * and Sun Microsystems Press, http://www.coreservlets.com/. &copy; 2003 Marty
 * Hall; may be freely used or adapted.
 */

public class ShoppingCart {
	private List<ItemOrder> itemsOrdered;

	/** Builds an empty shopping cart. */

	public ShoppingCart() {
		itemsOrdered = new ArrayList<ItemOrder>();
	}

	/**
	 * Returns List of ItemOrder entries giving Item and number ordered.
	 * Declared as List instead of ArrayList so that underlying implementation
	 * can be changed later.
	 */

	public List<ItemOrder> getItemsOrdered() {
		return (itemsOrdered);
	}

	/**
	 * Looks through cart to see if it already contains an order entry
	 * corresponding to item ID. If it does, increments the number ordered. If
	 * not, looks up Item in catalog and adds an order entry for it.
	 */

	public synchronized void addItem(String itemID) {
		ItemOrder order;
		boolean found = false;
		int i = 0;

		while (!found && i < itemsOrdered.size()) {
			order = itemsOrdered.get(i);
			if (order.getItemID().equals(itemID)) {
				found = true;
				order.incrementNumItems();
			}
			i++;
		}
		if (!found) {
			ItemOrder newOrder = new ItemOrder(Catalog.getItem(itemID));
			itemsOrdered.add(newOrder);
		}
		return;
	}

	/**
	 * Looks through cart to find order entry corresponding to item ID listed.
	 * If the designated number is positive, sets it. If designated number is 0
	 * (or, negative due to a user input error), deletes item from cart.
	 */

	public synchronized void setNumOrdered(String itemID, int numOrdered) {
		ItemOrder order;
		boolean found = false;
		int i = 0;

		while (!found && i < itemsOrdered.size()) {
			order = itemsOrdered.get(i);
			if (order.getItemID().equals(itemID)) {
				found = true;
				if (numOrdered <= 0)
					itemsOrdered.remove(i);
				else
					order.setNumItems(numOrdered);
			}
			i++;
		}
		if (!found) {
			ItemOrder newOrder = new ItemOrder(Catalog.getItem(itemID));
			itemsOrdered.add(newOrder);
		}
		return;
	}
}
