package personal.mcoffee.listener;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import personal.mcoffee.utils.Log;

/**
 * Created by Mcoffee on 2016/8/31.
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0;
    private boolean isLoading = true;
    int firstVisibleItem, childCount, itemCount;

    private int currentPage = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(
            LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        childCount = recyclerView.getChildCount();
        itemCount = mLinearLayoutManager.getItemCount();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        Log.v("EndlessRecyclerOnScrollListener childCount",childCount);
        Log.v("EndlessRecyclerOnScrollListener itemCount",itemCount);
        Log.v("EndlessRecyclerOnScrollListener firstVisibleItem",firstVisibleItem);

        if (isLoading) {
            if (itemCount > previousTotal) {
                isLoading = false;
                previousTotal = itemCount;
            }
        }
        if (!isLoading && (itemCount - childCount) <= firstVisibleItem) {
            currentPage++;
            onLoadMore(currentPage);
            isLoading = true;
        }
    }

    public abstract void onLoadMore(int currentPage);
}
