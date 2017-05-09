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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import personal.mcoffee.R;
import personal.mcoffee.adapter.base.HeaderAndFooterAdapter;
import personal.mcoffee.bean.Gank;
import personal.mcoffee.helper.PaddingAnimation;
import personal.mcoffee.widget.ResizableImageView;

/**
 * Created by Mcoffee on 2016/8/31.
 */
public class GankWelfareAdapter extends HeaderAndFooterAdapter {

    private static final int TYPE_NORMAL=1;

    private List<Gank> list;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public GankWelfareAdapter(List<Gank> list, Context context){
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
        return new GankWelfareViewHolder(mLayoutInflater.inflate(R.layout.item_gank_welfare, parent, false));
    }

    @Override
    public void bindNormalItemView(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GankWelfareViewHolder ){
            GankWelfareViewHolder normalVH = (GankWelfareViewHolder)holder;
//            ViewGroup.LayoutParams lp = normalVH.welfareIv.getLayoutParams();
            Gank gank = list.get(position);
            if(gank.url !=null) {
                Glide.with(mContext)
                        .load(gank.url)
//               .placeholder(R.drawable.img_loading)
                        .error(R.drawable.img_load_error)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(normalVH.welfareIv);
//                 .into(new GlideDrawableImageViewTarget(normalVH.welfareIv){
//                     @Override
//                     public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
//                         super.onResourceReady(resource, new PaddingAnimation<>(animation));
//                     }
//                 });
            }else{
                Glide.clear(normalVH.welfareIv);
            }
        }
    }

    static class GankWelfareViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.gank_welfare_iv)
        ImageView welfareIv;

        public GankWelfareViewHolder(View itemView) {
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

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

    }
}
