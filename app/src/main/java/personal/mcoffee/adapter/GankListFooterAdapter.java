package personal.mcoffee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import personal.mcoffee.R;
import personal.mcoffee.adapter.base.HeaderAndFooterAdapter;
import personal.mcoffee.bean.Gank;
import personal.mcoffee.listener.RecyclerViewListener;
import personal.mcoffee.utils.Log;
import personal.mcoffee.utils.TimeUtils;

/**
 * Created by Mcoffee on 2016/8/31.
 */
public class GankListFooterAdapter extends HeaderAndFooterAdapter {

    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_WIHT_IMAGE = 2;

    private List<Gank> list;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private RecyclerViewListener recyclerViewListener;

    public GankListFooterAdapter(List<Gank> list, Context context) {
        this.list = list;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getNormalItemCount() {
        return list.size();
    }

    @Override
    public int getNormalItemType(int position) {
        if (list.get(position).images != null && list.get(position).images.size() > 0)
            return TYPE_WIHT_IMAGE;
        else
            return TYPE_NORMAL;
    }

    @Override
    public boolean isHeaderUsed() {
        return false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType) {
        return null;
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
        return new GankFoooterHolder(mLayoutInflater.inflate(R.layout.recyclerview_footer, parent, false));
    }


    @Override
    public void bindFooterView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GankFoooterHolder) {
            GankFoooterHolder footerVH = (GankFoooterHolder) holder;
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
        if (viewType == TYPE_NORMAL)
            return new GankListViewHolder(mLayoutInflater.inflate(R.layout.item_gank_content, parent, false));
        else
            return new GankListViewImgHolder(mLayoutInflater.inflate(R.layout.item_gank_content_withimg, parent, false));
    }

    @Override
    public void bindNormalItemView(RecyclerView.ViewHolder holder, final int position) {
        if (getNormalItemType(position) == TYPE_NORMAL) {
            GankListViewHolder normalVH = (GankListViewHolder) holder;
            normalVH.titleTv.setText(list.get(position).desc);
            normalVH.timeTv.setText(TimeUtils.date2String(list.get(position).publishedAt));
            normalVH.whoTv.setText(list.get(position).who);
            if (recyclerViewListener != null) {
                normalVH.gankRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerViewListener.onItemClick(position);
                    }
                });
            }
        } else {
            GankListViewImgHolder normalVH = (GankListViewImgHolder) holder;
            normalVH.titleTv.setText(list.get(position).desc);
            normalVH.timeTv.setText(TimeUtils.date2String(list.get(position).publishedAt));
            normalVH.whoTv.setText(list.get(position).who);
//            Glide.with(mContext).load(list.get(position).images.get(0)).asBitmap().centerCrop().into(normalVH.imageIv);
            Glide.with(mContext).load(list.get(position).images.get(0))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .centerCrop()
                    .into(new GlideDrawableImageViewTarget(normalVH.imageIv, 1));

            if (recyclerViewListener != null) {
                normalVH.gankRl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerViewListener.onItemClick(position);
                    }
                });
            }
        }
    }

    static class GankListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.gank_item)
        RelativeLayout gankRl;
        @BindView(R.id.gank_item_title)
        TextView titleTv;
        @BindView(R.id.gank_item_time)
        TextView timeTv;
        @BindView(R.id.gank_item_who)
        TextView whoTv;

        public GankListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class GankListViewImgHolder extends GankListViewHolder {
        @BindView(R.id.gank_item_image)
        ImageView imageIv;

        public GankListViewImgHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class GankFoooterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.footer_progressbar)
        ProgressBar progressBar;
        @BindView(R.id.footer_loading)
        TextView loadingTv;

        public GankFoooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setRecyclerViewListener(RecyclerViewListener recyclerViewListener) {
        this.recyclerViewListener = recyclerViewListener;
    }
}
