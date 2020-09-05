package com.jmvincenti.myuserdirectory.ui.widget;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    /**
     * The minimum amount of items to have below your current scroll position before loading more.
     */
    private int mVisibleThreshold = 2;

    /**
     * The current offset index of data you have loaded
     */
    private int mCurrentPage = 0;

    /**
     * The total number of items in the dataset after the last load
     */
    private int mPreviousTotalItemCount = 0;

    /**
     * True if we are still waiting for the last set of data to load.
     */
    private boolean mLoading = true;

    /**
     * Sets the starting page index
     */
    private int mStartingPageIndex = 0;

    private RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        mVisibleThreshold = mVisibleThreshold * layoutManager.getSpanCount();
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
        mVisibleThreshold = mVisibleThreshold * layoutManager.getSpanCount();
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        final RecyclerView fView = view;
        int lastVisibleItemPosition = 0;
        final int totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            final int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager)
                    .findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);

        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();

        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < mPreviousTotalItemCount) {
            mCurrentPage = mStartingPageIndex;
            mPreviousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                mLoading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        // HACK Paul:
        // If it's still loading, we need to check with `totalItemCount >=` as we may have added a "loader" item,
        // increasing `mPreviousTotalItemCount` (and it was not updated when the "loader" item was removed)
        // This is a rare condition that happens with our broken API right now: when we retrieve exactly one item from
        // the API, therefore `totalItemCount` could be equal (not strictly superior) to `mPreviousTotalItemCount`
        // leading to never updating `mLoading` var to false (so never load anything again)
        // TODO come back to `mLoading && totalItemCount > mPreviousTotalItemCount` when server is fixed?
        if (mLoading && totalItemCount >= mPreviousTotalItemCount ||
                !mLoading && totalItemCount > mPreviousTotalItemCount) {
            mLoading = false;
            mPreviousTotalItemCount = totalItemCount;
        }

        // If it isn’t currently loading, we check to see if we have breached
        // the mVisibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (!mLoading && (lastVisibleItemPosition + mVisibleThreshold) > totalItemCount) {
            mCurrentPage++;

            // When loading more items, we may want to add/remove some items on the adapter and notify the recyclerView.
            // Unfortunately, we could run into problems because:
            //
            // [Extract from ADB warning]
            // > Scroll callbacks might be run during a measure & layout pass where you cannot change the RecyclerView
            // > data.
            // > Any method call that might change the structure of the RecyclerView or the adapter contents should be
            // > postponed to the next frame.
            //
            // So post the `onLoadMore` callback onto a Runnable to avoid that.
            fView.post(() -> onLoadMore(mCurrentPage, totalItemCount, fView));
            mLoading = true;
        }
    }

    /**
     * Call this method whenever performing new searches
     */
    public void resetState() {
        mCurrentPage = mStartingPageIndex;
        mPreviousTotalItemCount = 0;
        mLoading = true;
    }

    /**
     * Defines the process for actually loading more data based on page
     */
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
}
