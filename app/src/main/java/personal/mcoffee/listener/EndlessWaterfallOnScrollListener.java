package personal.mcoffee.listener;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import personal.mcoffee.utils.Log;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;

/**
 * Created by Mcoffee on 2016/8/31.
 */
public abstract class EndlessWaterfallOnScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0;
    private boolean isLoading = true;
    private int firstVisibleItem, childCount, itemCount;
    private int[] firstVisibleItems = null;
    private int visibleThreshold = 3;
    private int currentPage = 1;
    private Context mContext;

    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    private final RequestManager glide;

//    public EndlessWaterfallOnScrollListener(
//            StaggeredGridLayoutManager staggeredGridLayoutManager) {
//        this.staggeredGridLayoutManager = staggeredGridLayoutManager;
//    }

    public EndlessWaterfallOnScrollListener(
            StaggeredGridLayoutManager staggeredGridLayoutManager, Context context) {
        this.staggeredGridLayoutManager = staggeredGridLayoutManager;
        this.mContext = context;
        this.glide = Glide.with(mContext);
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        childCount = staggeredGridLayoutManager.getChildCount();
        itemCount = staggeredGridLayoutManager.getItemCount();
        int[] firstVisibleItems = null;
        firstVisibleItems = staggeredGridLayoutManager.findFirstCompletelyVisibleItemPositions(firstVisibleItems);

        Log.v("EndlessWaterfallOnScrollListener childCount", childCount);
        Log.v("EndlessWaterfallOnScrollListener itemCount", itemCount);

        if (firstVisibleItems != null && firstVisibleItems.length > 0) {
            firstVisibleItem = firstVisibleItems[0];
            Log.v("EndlessWaterfallOnScrollListener firstVisibleItem", firstVisibleItem);
        }
        if (isLoading) {
            if ((childCount + firstVisibleItem) >= previousTotal) {
                isLoading = false;
                previousTotal = itemCount;
                Log.v("EndlessWaterfallOnScrollListener previousTotal", previousTotal);
            }
        }
        if (!isLoading && (itemCount - childCount) <= (firstVisibleItem + visibleThreshold)) {
            currentPage++;
            onLoadMore(currentPage);
            isLoading = true;
        }
    }

    /**
     * The RecyclerView is not currently scrolling.（静止没有滚动）
     */
    public static final int SCROLL_STATE_IDLE = 0;

    /**
     * The RecyclerView is currently being dragged by outside input such as user touch input.
     * （正在被外部拖拽,一般为用户正在用手指滚动）
     */
    public static final int SCROLL_STATE_DRAGGING = 1;

    /**
     * The RecyclerView is currently animating to a final position while not under outside control.
     * （自动滚动）
     */
    public static final int SCROLL_STATE_SETTLING = 2;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        ((StaggeredGridLayoutManager)recyclerView.getLayoutManager()).invalidateSpanAssignments();
        if (newState == SCROLL_STATE_DRAGGING || newState == SCROLL_STATE_IDLE) {
            // user is touchy or the scroll finished, show images
            glide.resumeRequests();
        } else {
            glide.pauseRequests();
        }
    }


    public abstract void onLoadMore(int currentPage);
}
