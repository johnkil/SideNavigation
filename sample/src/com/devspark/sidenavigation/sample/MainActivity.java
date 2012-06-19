package com.devspark.sidenavigation.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.R;
import com.devspark.sidenavigation.SideNavigationView;

/**
 * 
 * @author e.shishkin
 *
 */
public class MainActivity extends Activity implements ISideNavigationCallback {
	
	private SideNavigationView sideNavigationView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
		sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
        
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			sideNavigationView.toggleMenu();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void RibbonMenuItemClick(int itemId) {
		// TODO Auto-generated method stub
		
	}

}
