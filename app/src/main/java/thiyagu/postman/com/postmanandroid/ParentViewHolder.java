package thiyagu.postman.com.postmanandroid;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ParentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ParentListItemExpandCollapseListener mParentListItemExpandCollapseListener;
    private boolean mExpanded;

    /**
     * implementations to be notified of expand/collapse state change events.
     */
    public interface ParentListItemExpandCollapseListener {

        /**
         * Called when a list item is expanded.
         *
         * @param position The index of the item in the list being expanded
         */
        void onParentListItemExpanded(int position);

        /**
         * Called when a list item is collapsed.
         *
         * @param position The index of the item in the list being collapsed
         */
        void onParentListItemCollapsed(int position);
    }

    /**
     * Default constructor.
     *
     * @param itemView The {@link View} being hosted in this ViewHolder
     */
    public ParentViewHolder(View itemView) {
        super(itemView);
        mExpanded = false;
    }

    /**
     * Sets a {@link android.view.View.OnClickListener} on the entire parent
     * view to trigger expansion.
     */
    public void setMainItemClickToExpand() {
        itemView.setOnClickListener(this);
    }

    /**
     * corresponding to this {@link ParentViewHolder}.
     *
     * @return true if expanded, false if not
     */
    public boolean isExpanded() {
        return mExpanded;
    }

    /**
     * Setter method for expanded state, used for initialization of expanded state.
     * changes to the state are given in {@link #onExpansionToggled(boolean)}
     *
     * @param expanded true if expanded, false if not
     */
    public void setExpanded(boolean expanded) {
        mExpanded = expanded;
    }

    /**
     * Callback triggered when expansion state is changed, but not during
     * initialization.
     * <p>
     * Useful for implementing animations on expansion.
     *
     * @param expanded true if view is expanded before expansion is toggled,
     *                 false if not
     */
    public void onExpansionToggled(boolean expanded) {

    }

    /**
     * Getter for the {@link ParentListItemExpandCollapseListener} implemented in
     *
     * @return The {@link ParentListItemExpandCollapseListener} set in the {@link ParentViewHolder}
     */
    public ParentListItemExpandCollapseListener getParentListItemExpandCollapseListener() {
        return mParentListItemExpandCollapseListener;
    }

    /**
     * Setter for the {@link ParentListItemExpandCollapseListener} implemented in
     *
     * @param parentListItemExpandCollapseListener The {@link ParentListItemExpandCollapseListener} to set on the {@link ParentViewHolder}
     */
    public void setParentListItemExpandCollapseListener(ParentListItemExpandCollapseListener parentListItemExpandCollapseListener) {
        mParentListItemExpandCollapseListener = parentListItemExpandCollapseListener;
    }

    /**
     * {@link android.view.View.OnClickListener} to listen for click events on
     * the entire parent {@link View}.
     * <p>
     * Only registered if {@link #shouldItemViewClickToggleExpansion()} is true.
     *
     * @param v The {@link View} that is the trigger for expansion
     */
    @Override
    public void onClick(View v) {
        if (mExpanded) {
            collapseView();
        } else {
            expandView();
        }
    }

    /**
     * Used to determine whether a click in the entire parent {@link View}
     * should trigger row expansion.
     * <p>
     * If you return false, you can call {@link #expandView()} to trigger an
     * expansion in response to a another event or {@link #collapseView()} to
     * trigger a collapse.
     *
     * @return true to set an {@link android.view.View.OnClickListener} on the item view
     */
    public boolean shouldItemViewClickToggleExpansion() {
        return true;
    }

    /**
     * Triggers expansion of the parent.
     */
    protected void expandView() {
        setExpanded(true);
        onExpansionToggled(false);

        if (mParentListItemExpandCollapseListener != null) {
            mParentListItemExpandCollapseListener.onParentListItemExpanded(getAdapterPosition());
        }
    }

    /**
     * Triggers collapse of the parent.
     */
    protected void collapseView() {
        setExpanded(false);
        onExpansionToggled(true);

        if (mParentListItemExpandCollapseListener != null) {
            mParentListItemExpandCollapseListener.onParentListItemCollapsed(getAdapterPosition());
        }
    }
}