package thiyagu.postman.com.postmanandroid;

import java.util.ArrayList;
import java.util.List;

public class ExpandableRecyclerAdapterHelper {

    /**
     * Generates a full list of all {@link ParentListItem} objects and their
     * children, in order.
     *
     * @param parentItemList A list of the {@code ParentListItem} objects from
     *                       the {@link ExpandableRecyclerAdapter}
     * @return A list of all {@code ParentListItem} objects and their children, expanded
     */
    public static List<Object> generateParentChildItemList(List<? extends ParentListItem> parentItemList) {
        List<Object> parentWrapperList = new ArrayList<>();
        ParentListItem parentListItem;
        ParentWrapper parentWrapper;

        int parentListItemCount = parentItemList.size();
        for (int i = 0; i < parentListItemCount; i++) {
            parentListItem = parentItemList.get(i);
            parentWrapper = new ParentWrapper(parentListItem);
            parentWrapperList.add(parentWrapper);

            if (parentWrapper.isInitiallyExpanded()) {
                parentWrapper.setExpanded(true);

                int childListItemCount = parentWrapper.getChildItemList().size();
                for (int j = 0; j < childListItemCount; j++) {
                    parentWrapperList.add(parentWrapper.getChildItemList().get(j));
                }
            }
        }

        return parentWrapperList;
    }
}