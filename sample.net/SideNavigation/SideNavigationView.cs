using System.Collections.Generic;
using System.Xml;
using Android.Views.Animations;
using Android.Widget;
using Android.Content;
using Android.Views;
using Java.Lang;
using Android.Util;
using sample.net;

/**
 * 
 * @author hram
 * 
 */
namespace com.devspark.sidenavigation
{
    public class SideNavigationView : LinearLayout
    {
        private static string LOG_TAG = "SideNavigationView";

	    private LinearLayout navigationMenu;
	    private ListView listView;
	    private View outsideView;
	    private ISideNavigationCallback callback;
	    private static List<SideNavigationItem> menuItems;

	    public SideNavigationView(Context context) : base(context)
	    {
		    load();
	    }

        public SideNavigationView(Context context, Android.Util.IAttributeSet attrs) : base(context, attrs)
        {
		    load();
	    }

	    private void load() {
		    if (IsInEditMode) {
			    return;
		    }
		    inflateLayout();
		    initUi();
	    }

	    private void inflateLayout() {
		    try {
			    LayoutInflater.From(Context).Inflate(Resource.Layout.side_navigation, this, true);
		    } catch (Exception e) {
			    Log.Warn(LOG_TAG, e);
		    }
	    }

	    private void initUi() {
		    navigationMenu = FindViewById<LinearLayout>(Resource.Id.side_navigation_menu);
		    listView = FindViewById<ListView>(Resource.Id.side_navigation_listview);
		    outsideView = FindViewById<View>(Resource.Id.side_navigation_outside_view);
		
		    outsideView.Click += delegate 
            {
				    hideMenu();
		    };
		
		    listView.ItemClick += delegate (object sender, AdapterView.ItemClickEventArgs args) 
            {	
				    if (callback != null) {
					    callback.onSideNavigationItemClick(menuItems[args.Position].id);
				    }
				    hideMenu();
		    };
	    }

	    public void setMenuClickCallback(ISideNavigationCallback callback) {
		    this.callback = callback;
	    }

        public void resetMenuClickCallback()
        {
            this.callback = null;
        }

	    public void setMenuItems(int menu) {
		    parseXml(menu);
		    if (menuItems != null && menuItems.Count > 0) {
			    listView.Adapter = new Adapter(Context);
		    }
	    }

	    public void setBackgroundResource(int resource) {
		    listView.SetBackgroundResource(resource);
	    }

	    public void showMenu() 
	    {
		    outsideView.Visibility = ViewStates.Visible;
            outsideView.StartAnimation(AnimationUtils.LoadAnimation(Context, Resource.Animation.side_navigation_fade_in));
            navigationMenu.Visibility = ViewStates.Visible;
            navigationMenu.StartAnimation(AnimationUtils.LoadAnimation(Context, Resource.Animation.side_navigation_in_from_left));
	    }

	    public void hideMenu() 
	    {
		    outsideView.Visibility = ViewStates.Gone;
            outsideView.StartAnimation(AnimationUtils.LoadAnimation(Context, Resource.Animation.side_navigation_fade_out));
	        navigationMenu.Visibility = ViewStates.Gone;
            navigationMenu.StartAnimation(AnimationUtils.LoadAnimation(Context, Resource.Animation.side_navigation_out_to_left));
	    }

	    public void toggleMenu() {
            if (outsideView.Visibility == ViewStates.Gone)
            {
			    showMenu();
		    } else {
			    hideMenu();
		    }
	    }

	    private void parseXml(int menu) {
		    menuItems = new List<SideNavigationItem>();
		    try {
                System.Xml.XmlReader reader = Resources.GetXml(menu);

                var sb = new System.Text.StringBuilder();

                while (reader.Read())
                    sb.AppendLine(reader.ReadOuterXml());

                XmlDocument document = new XmlDocument();
		        document.LoadXml(sb.ToString());

                foreach (XmlNode node in document.GetElementsByTagName("item"))
                {
                    string textId = node.Attributes["title", "http://schemas.android.com/apk/res/android"].Value;
                    string iconId = node.Attributes["icon", "http://schemas.android.com/apk/res/android"].Value;
                    string resId = node.Attributes["id", "http://schemas.android.com/apk/res/android"].Value;

                    SideNavigationItem item = new SideNavigationItem();
                    item.id = int.Parse(resId.Replace("@", ""));
                    item.text = resourceIdToString(textId);
                    item.icon = int.Parse(iconId.Replace("@", ""));
                    menuItems.Add(item);
                }
		    } catch (Exception e) {
			    Log.Warn(LOG_TAG, e);
		    }
	    }

	    private string resourceIdToString(string text) {
		    if (!text.Contains("@")) {
			    return text;
		    } else {
			    string id = text.Replace("@", "");
			    return Resources.GetString(int.Parse(id));
		    }
	    }

	    class SideNavigationItem {
	        public int id;
            public string text;
            public int icon;
	    }

        private class Adapter : Android.Widget.BaseAdapter
        {
		    private LayoutInflater inflater;

		    public Adapter(Context context) {
                inflater = LayoutInflater.From(context);
		    }

            public override int Count { get { return menuItems.Count; } }

            public override Java.Lang.Object GetItem(int position)
            {
                return null;
            }

            public override long GetItemId(int position)
            {
                return 0;
            }

		    public override View GetView(int position, View convertView, ViewGroup parent) 
            {
			    ViewHolder holder;
			    if (convertView == null) {
				    convertView = inflater.Inflate(Resource.Layout.side_navigation_item, null);
				    holder = new ViewHolder();
                    holder.image = convertView.FindViewById<ImageView>(Resource.Id.side_navigation_item_icon);
                    holder.text = convertView.FindViewById<TextView>(Resource.Id.side_navigation_item_text);
				    convertView.Tag = holder;
			    } else {
				    holder = (ViewHolder) convertView.Tag;
			    }
			    holder.image.SetImageResource(menuItems[position].icon);
                holder.text.Text = menuItems[position].text;
			    return convertView;
		    }

		    class ViewHolder : Java.Lang.Object {
                public TextView text;
                public ImageView image;
		    }

	    }

    }
}