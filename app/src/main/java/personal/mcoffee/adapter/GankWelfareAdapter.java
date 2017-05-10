package personal.mcoffee.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import personal.mcoffee.R;
import personal.mcoffee.activity.PhotoActivity;
import personal.mcoffee.adapter.base.HeaderAndFooterAdapter;
import personal.mcoffee.bean.Gank;
import personal.mcoffee.listener.OnPhotoClickListener;
import personal.mcoffee.utils.ScreenUtils;

/**
 * Created by Mcoffee on 2016/8/31.
 */
public class GankWelfareAdapter extends HeaderAndFooterAdapter {

    private static final int TYPE_NORMAL = 1;

    private List<Gank> list;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private OnPhotoClickListener onPhotoClickListener;

    public GankWelfareAdapter(List<Gank> list, Context context) {
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
    public void bindNormalItemView(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof GankWelfareViewHolder) {
            final GankWelfareViewHolder normalVH = (GankWelfareViewHolder) holder;
            ViewGroup.LayoutParams lp = normalVH.welfareIv.getLayoutParams();
            final  Gank gank = list.get(position);
            if (gank.url != null) {
                Glide.with(mContext)
                        .load(gank.url)
                        .asBitmap()
//                        .placeholder(R.drawable.img_loading)
                        .error(R.drawable.img_load_error)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .override(ScreenUtils.getScreenWidth(mContext)/2,Target.SIZE_ORIGINAL)
                        .fitCenter()
                        .into(normalVH.welfareIv);
//                        .dontTransform()
//                        .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL) {
//                            @Override
//                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                                //原始图片宽高
//                                int imageWidth = resource.getWidth();
//                                int imageHeight = resource.getHeight();
//                                //宽度取屏幕一半值
//                                int width = ScreenUtils.getScreenWidth(mContext)/2 ;
//                                //宽高比
//                                float ratio = (float) ((imageWidth*1.0)/(imageHeight*1.0));
//                                //按比例得出高度
//                                int height = (int)(width*1.0/ratio);
//                                ViewGroup.LayoutParams params = normalVH.welfareIv.getLayoutParams();
//                                params.width = width;
//                                params.height = height;
//                                normalVH.welfareIv.setImageBitmap(resource);
//                            }
//                        });
                normalVH.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext,gank.url+"位置："+position,Toast.LENGTH_SHORT).show();
                        if(onPhotoClickListener!=null){
                            onPhotoClickListener.OnPhotoClickListener(normalVH.welfareIv,gank.url);
                        }
                    }
                });
            } else {
                Glide.clear(normalVH.welfareIv);
                ((GankWelfareViewHolder) holder).welfareIv.setImageBitmap(null);
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
        if (holder instanceof GankWelfareViewHolder){
            Glide.clear(((GankWelfareViewHolder) holder).welfareIv);
        }

    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (getItemViewType(holder.getLayoutPosition()) == TYPE_FOOTER) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    public void setOnPhotoClickListener(OnPhotoClickListener onPhotoClickListener){
        this.onPhotoClickListener = onPhotoClickListener;
    }

}
