package personal.mcoffee.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import personal.mcoffee.R;
import personal.mcoffee.mvp.model.Option;

/**
 * Created by Mcoffee on 2016/8/30.
 */
public class MeOptionsAdapter extends RecyclerView.Adapter<MeOptionsAdapter.MeArrowViewHolder> {
    private List<Option> list;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public MeOptionsAdapter(List<Option> list, Context context) {
        this.list = list;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public MeOptionsAdapter.MeArrowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MeArrowViewHolder(mLayoutInflater.inflate(R.layout.item_me_settings, parent, false));
    }

    @Override
    public void onBindViewHolder(MeArrowViewHolder holder, int position) {
        holder.optionTv.setText(list.get(position).option);
        holder.resultTv.setText(list.get(position).result);
    }

    static class MeArrowViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_me_tv)
        TextView optionTv;
        @BindView(R.id.item_me_arrow)
        ImageView arrowIv;
        @BindView(R.id.item_me_result)
        TextView resultTv;

        public MeArrowViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
