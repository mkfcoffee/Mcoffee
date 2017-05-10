package personal.mcoffee.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by Mcoffee on 2016/8/31.
 */
public abstract class HeaderAndFooterAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HEADER = -1;
    public static final int TYPE_FOOTER = 0;
    private static final int TYPE_OFFEST = 2;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return onCreateHeaderViewHolder(parent, viewType);
        } else if (viewType == TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, viewType);
        }
        return onCreateNormalItemViewHolder(parent, viewType - TYPE_OFFEST);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0 && holder.getItemViewType() == TYPE_HEADER) {
            bindHeaderView(holder, position);
        } else if (position == getItemCount()-1 && holder.getItemViewType() == TYPE_FOOTER) {
            bindFooterView(holder, position);
        } else {
            bindNormalItemView(holder, position - (isHeaderUsed() ? 1 : 0));
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = getNormalItemCount();
        if (isHeaderUsed()) {
            itemCount += 1;
        }
        if (isFooterUsed()) {
            itemCount += 1;
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && isHeaderUsed()) {
            return TYPE_HEADER;
        }
        if (position == getItemCount()-1 && isFooterUsed()) {
            return TYPE_FOOTER;
        }
        if (getNormalItemType(position) >= Integer.MAX_VALUE - TYPE_OFFEST) {
            new IllegalStateException("HeaderAndFooterAdapter offsets your NormalItemType by " + TYPE_OFFEST + ".");
        }
        return getNormalItemType(position) + TYPE_OFFEST;
    }


    public abstract boolean isHeaderUsed();

    public abstract RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType);

    public abstract void bindHeaderView(RecyclerView.ViewHolder holder, int position);

    public abstract boolean isFooterUsed();

    public abstract RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType);

    public abstract void bindFooterView(RecyclerView.ViewHolder holder, int position);

    public abstract RecyclerView.ViewHolder onCreateNormalItemViewHolder(ViewGroup parent, int viewType);

    public abstract void bindNormalItemView(RecyclerView.ViewHolder holder, int position);

    public abstract int getNormalItemCount();

    public abstract int getNormalItemType(int position);
}
