package com.example.alex.dstuapp.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public class OffsetRecyclerPaginationListener extends RecyclerView.OnScrollListener {
    private static final String TAG = OffsetRecyclerPaginationListener.class.getSimpleName();
    public static final int DEFAULT_THRESHOLD = 5;

    // True if we are still waiting for the last set of data to load.
    private volatile boolean isLoading;
    // The minimum amount of items to have below your current scroll position before isLoading more.
    private final int visibleThreshold;

    private Loader loader;
    private RecyclerView.LayoutManager layoutManager;

    public OffsetRecyclerPaginationListener(RecyclerView.LayoutManager layoutManager, Loader loader) {
        this(layoutManager, loader, DEFAULT_THRESHOLD);
    }

    public OffsetRecyclerPaginationListener(RecyclerView.LayoutManager layoutManager, Loader loader, int visibleThreshold) {
        this.layoutManager = layoutManager;
        this.loader = loader;
        this.visibleThreshold = visibleThreshold;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        //scroll up, all visible items already loaded
        final int loadedItemsCount = layoutManager.getItemCount();
        final int lastVisibleItem = findLastVisibleItemPosition();
        if (dy <= 0) {
            return;
        }
        if (!isLoading && loadedItemsCount <= (lastVisibleItem + visibleThreshold)) {
            loader.onLoadMore(loadedItemsCount);
            isLoading = true;
        }
    }

    public int findLastVisibleItemPosition() {
        // TODO: 11.09.15 refactor this
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else {
            int[] pos = ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null);
            return pos[pos.length - 1];
        }
    }

    public void startLoading() {
        isLoading = true;
    }

    public void stopLoading() {
        isLoading = false;
    }

    public void checkFitScreen(int loadedItemsCount) {
        final int lastVisibleItemPosition = findLastVisibleItemPosition();
        final int totalItemsCount = layoutManager.getItemCount();
        if (loadedItemsCount != 0 && (lastVisibleItemPosition == -1 ? 0 : lastVisibleItemPosition) + loadedItemsCount >= totalItemsCount) {
            loader.onLoadMore(totalItemsCount);
        }
    }

    public interface Loader {
        void onLoadMore(int offset);
    }

}
