using Android.App;
using Android.Content;
using Android.Views;
using Android.Widget;
using Android.OS;
using com.devspark.sidenavigation;

using Com.Actionbarsherlock.App;
using Com.Actionbarsherlock.View;
using Java.Lang;
using IMenu = global::Com.Actionbarsherlock.View.IMenu;
using IMenuItem = global::Com.Actionbarsherlock.View.IMenuItem;
using MenuItem = global::Com.Actionbarsherlock.View.MenuItem;
using ISubMenu = global::Com.Actionbarsherlock.View.ISubMenu;

/**
 * 
 * @author hram
 *
 */
namespace sample.net
{
    [Activity(Label = "@string/app_name", MainLauncher = true, Icon = "@drawable/ic_launcher")]
    public class MainActivity : SherlockFragmentActivity, ISideNavigationCallback
    {
        public static string EXTRA_TITLE = "com.devspark.sidenavigation.sample.extra.MTGOBJECT";
	    public static string EXTRA_RESOURCE_ID = "com.devspark.sidenavigation.sample.extra.RESOURCE_ID";

        private ImageView icon;
        private SideNavigationView sideNavigationView;

        protected override void OnCreate(Bundle bundle)
        {
            base.OnCreate(bundle);

            SetContentView(Resource.Layout.activity_main);
            icon = FindViewById<ImageView>(Android.Resource.Id.Icon);
            sideNavigationView = FindViewById<SideNavigationView>(Resource.Id.side_navigation_view);
            sideNavigationView.setMenuItems(Resource.Menu.side_navigation_menu);
            sideNavigationView.setMenuClickCallback(this);

            if (Intent.HasExtra(EXTRA_TITLE))
            {
                string title = Intent.GetStringExtra(EXTRA_TITLE);
                int resId = Intent.GetIntExtra(EXTRA_RESOURCE_ID, 0);
                Title = title;
                icon.SetImageResource(resId);
            }

            SupportActionBar.SetDisplayHomeAsUpEnabled(true);
        }

        public override bool OnOptionsItemSelected(IMenuItem item)
        {
            switch (item.ItemId)
            {
		    case Android.Resource.Id.Home:
			    sideNavigationView.toggleMenu();
			    break;
		    default:
			    return base.OnOptionsItemSelected(item);
		    }
		    return true;
	    }

        public void onSideNavigationItemClick(int itemId)
        {
		    switch (itemId) {
		    case Resource.Id.side_navigation_menu_item1:
			    invokeActivity(GetString(Resource.String.title1), Resource.Drawable.ic_android1);
			    break;

		    case Resource.Id.side_navigation_menu_item2:
			    invokeActivity(GetString(Resource.String.title2), Resource.Drawable.ic_android2);
			    break;

		    case Resource.Id.side_navigation_menu_item3:
			    invokeActivity(GetString(Resource.String.title3), Resource.Drawable.ic_android3);
			    break;

		    case Resource.Id.side_navigation_menu_item4:
			    invokeActivity(GetString(Resource.String.title4), Resource.Drawable.ic_android4);
			    break;

		    case Resource.Id.side_navigation_menu_item5:
			    invokeActivity(GetString(Resource.String.title5), Resource.Drawable.ic_android5);
			    break;

		    default:
			    return;
		    }
		    Finish();
	    }

        public override void OnBackPressed()
        {
		    // hide menu if it shown
		    if (sideNavigationView.Visibility == ViewStates.Gone) {
			    sideNavigationView.hideMenu();
		    } else {
                base.OnBackPressed();
		    }
	    }

	    /**
	     * Start activity from SideNavigation.
	     * 
	     * @param title
	     *            title of Activity
	     * @param resId
	     *            resource if of background image
	     */
	    private void invokeActivity(string title, int resId) {
		    Intent intent = new Intent(this, typeof (MainActivity));
		    intent.PutExtra(EXTRA_TITLE, title);
		    intent.PutExtra(EXTRA_RESOURCE_ID, resId);

		    // all of the other activities on top of it will be closed and this
		    // Intent will be delivered to the (now on top) old activity as a
		    // new Intent.
            intent.AddFlags(ActivityFlags.ClearTop);

		    StartActivity(intent);
		    // no animation of transition
		    OverridePendingTransition(0, 0);
	    }
    }
}

