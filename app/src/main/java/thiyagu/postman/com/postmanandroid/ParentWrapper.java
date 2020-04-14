package thiyagu.postman.com.postmanandroid;

import java.util.List;

public class ParentWrapper {

    private boolean mExpanded;
    private ParentListItem mParentListItem;

    /**
     * Default constructor.
     *
     * @param parentListItem The {@link ParentListItem} to wrap
     */
    public ParentWrapper(ParentListItem parentListItem) {
        mParentListItem = parentListItem;
        mExpanded = false;
    }

    /**
     * Gets the {@link ParentListItem} being wrapped.
     *
     * @return The {@link ParentListItem} being wrapped
     */
    public ParentListItem getParentListItem() {
        return mParentListItem;
    }

    /**
     * Sets the {@link ParentListItem} to wrap.
     *
     * @param parentListItem The {@link ParentListItem} to wrap
     */
    public void setParentListItem(ParentListItem parentListItem) {
        mParentListItem = parentListItem;
    }

    /**
     * Gets the expanded state associated with the {@link ParentListItem}.
     *
     * @return true if expanded, false if not
     */
    public boolean isExpanded() {
        return mExpanded;
    }

    /**
     * Sets the expanded state associated with the {@link ParentListItem}.
     *
     * @param expanded true if expanded, false if not
     */
    public void setExpanded(boolean expanded) {
        mExpanded = expanded;
    }

    public boolean isInitiallyExpanded() {
        return mParentListItem.isInitiallyExpanded();
    }

    public List<?> getChildItemList() {
        return mParentListItem.getChildItemList();
    }
}