package com.devspark.sidenavigation.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.devspark.sidenavigation.ISideNavigationCallback;
import com.devspark.sidenavigation.R;
import com.devspark.sidenavigation.SideNavigationView;

/**
 * 
 * @author e.shishkin
 *
 */
public class MainActivity extends Activity implements ISideNavigationCallback {
	
	private ImageView icon;
	private SideNavigationView sideNavigationView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		icon = (ImageView) findViewById(android.R.id.icon);
		sideNavigationView = (SideNavigationView) findViewById(R.id.side_navigation_view);
		sideNavigationView.setMenuItems(R.menu.side_navigation_menu);
		sideNavigationView.setMenuClickCallback(this);
        
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
    public void onSideNavigationItemClick(int itemId) {
        switch (itemId) {
        case R.id.side_navigation_menu_item1:
        	setTitle(R.string.title1);
        	icon.setImageResource(R.drawable.ic_android1);
            break;
                
        case R.id.side_navigation_menu_item2:
        	setTitle(R.string.title2);
        	icon.setImageResource(R.drawable.ic_android2);
            break;
                
        case R.id.side_navigation_menu_item3:
        	setTitle(R.string.title3);
        	icon.setImageResource(R.drawable.ic_android3);
            break;
                
        case R.id.side_navigation_menu_item4:
        	setTitle(R.string.title4);
        	icon.setImageResource(R.drawable.ic_android4);
            break;
        
        case R.id.side_navigation_menu_item5:
        	setTitle(R.string.title5);
        	icon.setImageResource(R.drawable.ic_android5);
            break;

        default:
        	break;
        }
        
    }

}
