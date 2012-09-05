package com.devspark.sidenavigation;

import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * View of displaying side navigation.
 * 
 * @author e.shishkin
 * 
 */
public class SideNavigationView extends LinearLayout {
	private static final String LOG_TAG = SideNavigationView.class.getSimpleName();

	private LinearLayout navigationMenu;
	private ListView listView;
	private View outsideView;

	private ISideNavigationCallback callback;
	private ArrayList<SideNavigationItem> menuItems;

	/**
	 * Constructor of {@link SideNavigationView}.
	 * @param context
	 */
	public SideNavigationView(Context context) {
		super(context);
		load();
	}

	/**
	 * Constructor of {@link SideNavigationView}.
	 * @param context
	 * @param attrs
	 */
	public SideNavigationView(Context context, AttributeSet attrs) {
		super(context, attrs);
		load();
	}

	/**
	 * Loading of side navigation view.
	 */
	private void load() {
		if (isInEditMode()) {
			return;
		}
		initView();
	}

	/**
	 * Initialization layout of side menu.
	 */
	private void initView() {
		LayoutInflater.from(getContext()).inflate(R.layout.side_navigation, this, true);
		navigationMenu = (LinearLayout) findViewById(R.id.side_navigation_menu);
		listView = (ListView) findViewById(R.id.side_navigation_listview);
		outsideView = (View) findViewById(R.id.side_navigation_outside_view);
		outsideView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hideMenu();
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (callback != null) {
					callback.onSideNavigationItemClick(menuItems.get(position).getId());
				}
				hideMenu();
			}
		});
	}

	/**
	 * Setup of {@link ISideNavigationCallback} for callback of item click.
	 * @param callback
	 */
	public void setMenuClickCallback(ISideNavigationCallback callback) {
		this.callback = callback;
	}

	/**
	 * Setup of side menu items.
	 * @param menu - resource ID
	 */
	public void setMenuItems(int menu) {
		parseXml(menu);
		if (menuItems != null && menuItems.size() > 0) {
			listView.setAdapter(new SideNavigationAdapter());
		}
	}

	/**
	 * 
	 */
	public void setBackgroundResource(int resource) {
		listView.setBackgroundResource(resource);
	}

	/**
	 * Show side navigation menu.
	 */
	public void showMenu() {
		outsideView.setVisibility(View.VISIBLE);
		outsideView.startAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.side_navigation_fade_in));
		navigationMenu.setVisibility(View.VISIBLE);
		navigationMenu.startAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.side_navigation_in_from_left));
	}

	/**
	 * Hide side navigation menu.
	 */
	public void hideMenu() {
		outsideView.setVisibility(View.GONE);
		outsideView.startAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.side_navigation_fade_out));
		navigationMenu.setVisibility(View.GONE);
		navigationMenu.startAnimation(AnimationUtils.loadAnimation(getContext(),
				R.anim.side_navigation_out_to_left));
	}

	/**
	 * Show/Hide side navigation menu depending on visibility.
	 */
	public void toggleMenu() {
		if (isShown()) {
			hideMenu();
		} else {
			showMenu();
		}
	}
	
	@Override
	public boolean isShown() {
		return navigationMenu.isShown();
	}

	/**
	 * Parse XML describe menu.
	 * @param menu - resource ID
	 */
	private void parseXml(int menu) {
		menuItems = new ArrayList<SideNavigationItem>();
		try {
			XmlResourceParser xrp = getResources().getXml(menu);
			xrp.next();
			int eventType = xrp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					String elemName = xrp.getName();
					if (elemName.equals("item")) {
						String textId = xrp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"title");
						String iconId = xrp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"icon");
						String resId = xrp.getAttributeValue(
								"http://schemas.android.com/apk/res/android",
								"id");
						SideNavigationItem item = new SideNavigationItem();
						item.setId(Integer.valueOf(resId.replace("@", "")));
						item.setText(resourceIdToString(textId));
						item.setIcon(Integer.valueOf(iconId.replace("@", "")));
						menuItems.add(item);
					}
				}
				eventType = xrp.next();
			}
		} catch (Exception e) {
			Log.w(LOG_TAG, e);
		}
	}

	/**
	 * Convert resource ID to String.
	 * @param text
	 * @return
	 */
	private String resourceIdToString(String resId) {
		if (!resId.contains("@")) {
			return resId;
		} else {
			String id = resId.replace("@", "");
			return getResources().getString(Integer.valueOf(id));
		}
	}

	private class SideNavigationAdapter extends BaseAdapter {
		private LayoutInflater inflater;

		public SideNavigationAdapter() {
			inflater = LayoutInflater.from(getContext());
		}

		@Override
		public int getCount() {
			return menuItems.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.side_navigation_item, null);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.side_navigation_item_icon);
				holder.text = (TextView) convertView.findViewById(R.id.side_navigation_item_text);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.image.setImageResource(menuItems.get(position).getIcon());
			holder.text.setText(menuItems.get(position).getText());
			return convertView;
		}

		class ViewHolder {
			TextView text;
			ImageView image;
		}

	}

}
