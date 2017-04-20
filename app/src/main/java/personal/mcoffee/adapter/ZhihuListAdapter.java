package personal.mcoffee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import personal.mcoffee.R;
import personal.mcoffee.adapter.base.HeaderAndFooterAdapter;
import personal.mcoffee.bean.Banner;
import personal.mcoffee.listener.RecyclerViewListener;
import personal.mcoffee.mvp.model.DailyStories;
import personal.mcoffee.mvp.model.Story;
import personal.mcoffee.widget.BannerView;

/**
 * Created by Mcoffee.
 */

public class ZhihuListAdapter extends HeaderAndFooterAdapter {

    private static final int LIST_TYPE_NORMAL = 1;

    private DailyStories dailyStories;
    //    private List<Story> stories;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewListener recyclerViewListener;

    /**
     * 初次加载
     *
     * @param dailyStories
     * @param context
     */
    public ZhihuListAdapter(DailyStories dailyStories, Context context) {
        this.dailyStories = dailyStories;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
//        if (stories == null) {
//            stories = new ArrayList<>();
//            stories.addAll(dailyStories.stories);
//        }
    }

    @Override
    public boolean isHeaderUsed() {
        return true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return new BannerViewHolder(mLayoutInflater.inflate(R.layout.item_zhihu_banner, parent, false));
    }

    @Override
    public void bindHeaderView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BannerViewHolder) {
            BannerViewHolder bannerVH = (BannerViewHolder) holder;
            List<Banner> banners = new ArrayList<>();
            if (dailyStories.topStories == null || dailyStories.topStories.size() < 1)
                return;
            for (int i = 0; i < dailyStories.topStories.size(); i++) {
                Story story = dailyStories.topStories.get(i);
                banners.add(new Banner(story.title, story.image));
            }
            bannerVH.bannerView.setData(banners, new BannerView.OnBannerClickListener() {
                @Override
                public void onClick(Banner banner, int position, View view) {
                    //banners.get(position - 1).title
                }
            });
        }
    }

    @Override
    public boolean isFooterUsed() {
        return true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType) {
        return new ZhihuFoooterHolder(mLayoutInflater.inflate(R.layout.recyclerview_footer, parent, false));
    }

    @Override
    public void bindFooterView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ZhihuFoooterHolder) {
            ZhihuFoooterHolder footerVH = (ZhihuFoooterHolder) holder;
            if (getNormalItemCount() == 0) {
                footerVH.progressBar.setVisibility(View.GONE);
                footerVH.loadingTv.setVisibility(View.GONE);
            } else {
                footerVH.progressBar.setVisibility(View.VISIBLE);
                footerVH.loadingTv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateNormalItemViewHolder(ViewGroup parent, int viewType) {
        return new ZhihuViewHolder(mLayoutInflater.inflate(R.layout.item_zhihu, parent, false));
    }

    @Override
    public void bindNormalItemView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ZhihuViewHolder) {
            ZhihuViewHolder zhihuVH = (ZhihuViewHolder) holder;
            Story story = dailyStories.stories.get(position);
            zhihuVH.titleTv.setText(story.title);
            Glide.with(mContext).load(story.images.get(0)).centerCrop().into(zhihuVH.imageIv);
        }
    }

    @Override
    public int getNormalItemCount() {
        return dailyStories.stories.size();
    }

    @Override
    public int getNormalItemType(int position) {
        return LIST_TYPE_NORMAL;
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_zhihu_banner)
        BannerView bannerView;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setDailyStories(DailyStories dailyStories) {
        this.dailyStories = dailyStories;
    }


    /**
     * 下拉刷新
     */
    public void addStories(List<Story> stories) {
        if (dailyStories.stories != null) {
            dailyStories.stories.addAll(stories);
        }
    }


    static class ZhihuViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_zhihu_title)
        TextView titleTv;
        //        @BindView(R.id.item_zhihu_date)
//        TextView dateTv;
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
