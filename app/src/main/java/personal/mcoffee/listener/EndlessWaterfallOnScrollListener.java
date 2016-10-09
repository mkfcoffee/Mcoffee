package personal.mcoffee.listener;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import personal.mcoffee.utils.Log;

/**
 * Created by Mcoffee on 2016/8/31.
 */
public abstract class EndlessWaterfallOnScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0;
    private boolean isLoading = true;
    private int pastVisibleItems, childCount, itemCount;
    private int[] firstVisibleItems = null;
    private int visibleThreshold = 3;
    private int currentPage = 1;

    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public EndlessWaterfallOnScrollListener(
            StaggeredGridLayoutManager staggeredGridLayoutManager) {
        this.staggeredGridLayoutManager = staggeredGridLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        childCount = recyclerView.getChildCount();
        itemCount = staggeredGridLayoutManager.getItemCount();
        int[] firstVisibleItems = null;
        firstVisibleItems = staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(firstVisibleItems);

        Log.v("EndlessWaterfallOnScrollListener childCount", childCount);
        Log.v("EndlessWaterfallOnScrollListener itemCount", itemCount);

        if (firstVisibleItems != null && firstVisibleItems.length > 0) {
            pastVisibleItems = firstVisibleItems[0];
            Log.v("EndlessWaterfallOnScrollListener pastVisibleItems", pastVisibleItems);
        }
        if (isLoading) {
            if ((childCount + pastVisibleItems) >= previousTotal) {
                isLoading = false;
                previousTotal = itemCount;
                Log.v("EndlessWaterfallOnScrollListener previousTotal", previousTotal);
            }
        }
        if (!isLoading && (itemCount - childCount) <= (pastVisibleItems + visibleThreshold)) {
            currentPage++;
            onLoadMore(currentPage);
            isLoading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);
}
