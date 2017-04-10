package personal.mcoffee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import personal.mcoffee.R;
import personal.mcoffee.adapter.base.HeaderAndFooterAdapter;
import personal.mcoffee.listener.RecyclerViewListener;
import personal.mcoffee.mvp.model.DailyStories;
import personal.mcoffee.widget.BannerView;

/**
 * Created by Mcoffee on 2016/10/18.
 */

public class ZhihuListAdapter extends HeaderAndFooterAdapter {

    private DailyStories dailyStories;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewListener recyclerViewListener;


    @Override
    public boolean isHeaderUsed() {
        return true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new BannerViewHolder(mLayoutInflater.inflate(R.layout.item_zhihu_banner,parent,false));
    }

    @Override
    public void bindHeaderView(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public boolean isFooterUsed() {
        return true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void bindFooterView(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateNormalItemViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void bindNormalItemView(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getNormalItemCount() {
        return 0;
    }

    @Override
    public int getNormalItemType(int position) {
        return 0;
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_zhihu_banner)
        BannerView bannerView;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    static class ZhihuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_zhihu_title)
        TextView titleTv;
        @BindView(R.id.item_zhihu_date)
        TextView dateTv;
        @BindView(R.id.item_zhihu_image)
        ImageView imageIv;

        public ZhihuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ZhihuFoooterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.footer_progressbar)
        ProgressBar progressBar;
        @BindView(R.id.footer_loading)
        TextView loadingTv;

        public ZhihuFoooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
