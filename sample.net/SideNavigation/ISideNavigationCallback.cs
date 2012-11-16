/**
 * 
 * @author hram
 *
 */
namespace com.devspark.sidenavigation
{
    public interface ISideNavigationCallback
    {

        /**
         * Validation clicking on side navigation item.
         * @param itemId id of selected item
         */
        void onSideNavigationItemClick(int itemId);
    }
}
