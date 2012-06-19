package com.devspark.sidenavigation;

/**
 * 
 * @author e.shishkin
 *
 */
public interface ISideNavigationCallback {

	/**
	 * Validation clicking on side navigation item.
	 * @param itemId id of selected item
	 */
	public void onSideNavigationItemClick(int itemId);

}
