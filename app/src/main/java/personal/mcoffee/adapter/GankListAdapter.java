package personal.mcoffee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import personal.mcoffee.R;
import personal.mcoffee.bean.Gank;
import personal.mcoffee.utils.TimeUtils;

/**
 * Created by Mcoffee on 2016/8/30.
 */
public class GankListAdapter extends RecyclerView.Adapter<GankListAdapter.GankListViewHolder> {

    private List<Gank> list;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public GankListAdapter(List<Gank> list, Context context) {
        this.list = list;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public GankListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GankListViewHolder(mLayoutInflater.inflate(R.layout.item_gank_content, parent, false));
    }

    @Override
    public void onBindViewHolder(GankListViewHolder holder, int position) {
        holder.titleTv.setText(list.get(position).desc);
        holder.timeTv.setText(TimeUtils.date2String(list.get(position).publishedAt));
        holder.whoTv.setText(list.get(position).who);
    }

    static class GankListViewHolder extends RecyclerView.ViewHolder {
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

}
