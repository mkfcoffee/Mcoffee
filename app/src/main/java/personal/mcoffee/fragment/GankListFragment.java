package personal.mcoffee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import personal.mcoffee.R;
import personal.mcoffee.activity.WebActivity;
import personal.mcoffee.adapter.GankListFooterAdapter;
import personal.mcoffee.base.BaseFragment;
import personal.mcoffee.bean.Gank;
import personal.mcoffee.bean.GankData;
import personal.mcoffee.constant.BaseUrl;
import personal.mcoffee.listener.EndlessRecyclerOnScrollListener;
import personal.mcoffee.listener.RecyclerViewListener;
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
public class GankListFragment extends BaseFragment {

    @BindView(R.id.gank_swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.gank_recyclerView)
    RecyclerView mRecyclerView;

    private String category;

    private Unbinder unbinder;

    private GankListFooterAdapter gankListAdapter;

    private List<Gank> gankList;

    private Snackbar mSnackbar;

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    private static final int PAGE_ONE = 1;

    private static final long REFRESH_DELAY_MILLIS = 1000;


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
        Log.v("Type","---------"+category);
        if(gankList == null) gankList = new ArrayList<Gank>();
        if (gankListAdapter == null) {
            gankListAdapter = new GankListFooterAdapter(gankList, getActivity());
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v("GankListFragment","Type:"+category +"invoke onCreateView");
        final View view = inflater.inflate(R.layout.fragment_gank_list,container,false);
        unbinder = ButterKnife.bind(this,view);
        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.v("GankListFragment","Type:"+category +" invoke onRefresh");
                if (!isDataLoaded){
                    getBackendData(category, PAGE_ONE);
                    swipeRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    },REFRESH_DELAY_MILLIS);
                }else{
                    mSnackbar = Snackbar.make(getActivity().findViewById(android.R.id.content),"最新数据已加载",Snackbar.LENGTH_SHORT);
                    mSnackbar.show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        };
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(gankListAdapter);

        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                getBackendData(category,currentPage);
            }
        });
        gankListAdapter.setRecyclerViewListener(new RecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(),"current position : "+position +"url: "+gankList.get(position).url,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("url",gankList.get(position).url);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void fetchData() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        onRefreshListener.onRefresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 请求相应接口，获取后台数据
     */
    public void getBackendData(String category , final int page){
        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(BaseUrl.CATEGORY_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
        GankRequest request = retrofit.create(GankRequest.class);
        Call<GankData> call = request.gankData(category,page);
        call.enqueue(new Callback<GankData>() {
            @Override
            public void onResponse(Call<GankData> call, Response<GankData> response) {
                GankData gankData = response.body();
                Log.v("Data", gankData.toString());
                gankList.addAll(gankData.results);
                if (gankListAdapter != null) {
                    gankListAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GankData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
