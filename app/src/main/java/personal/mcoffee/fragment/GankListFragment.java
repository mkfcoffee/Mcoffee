package personal.mcoffee.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import personal.mcoffee.R;
import personal.mcoffee.bean.GankData;
import personal.mcoffee.constant.GankUrl;
import personal.mcoffee.network.GankRequest;
import personal.mcoffee.utils.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mcoffee on 2016/8/29.
 */
public class GankListFragment extends Fragment {

    @BindView(R.id.gank_swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.gank_recyclerView)
    RecyclerView recyclerView;

    private String category;

    private Unbinder unbinder;

    public static GankListFragment getInstance(String category){
        GankListFragment gankListFragment = new GankListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category",category);
        gankListFragment.setArguments(bundle);
        return  gankListFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getArguments().getString("category");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank_list,container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 请求相应接口，获取后台数据
     */
    public void getBackendData(String category , int page){
        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(GankUrl.CATEGORY_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
        GankRequest request = retrofit.create(GankRequest.class);
        Call<GankData> call = request.gankData(category,page);
        call.enqueue(new Callback<GankData>() {
            @Override
            public void onResponse(Call<GankData> call, Response<GankData> response) {
                GankData gankData = response.body();
                Log.v("Data",gankData.toString());
            }

            @Override
            public void onFailure(Call<GankData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
