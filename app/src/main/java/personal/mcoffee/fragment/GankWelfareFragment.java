package personal.mcoffee.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import personal.mcoffee.R;
import personal.mcoffee.activity.PhotoActivity;
import personal.mcoffee.adapter.GankWelfareAdapter;
import personal.mcoffee.base.BaseFragment;
import personal.mcoffee.bean.Gank;
import personal.mcoffee.bean.GankData;
import personal.mcoffee.constant.BaseUrl;
import personal.mcoffee.listener.EndlessWaterfallOnScrollListener;
import personal.mcoffee.listener.OnPhotoClickListener;
import personal.mcoffee.network.GankService;
import personal.mcoffee.utils.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mcoffee on 2016/8/29.
 */
public class GankWelfareFragment extends BaseFragment {

    @BindView(R.id.gank_swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.gank_recyclerView)
    RecyclerView mRecyclerView;

    private String category;

    private Unbinder unbinder;

    private GankWelfareAdapter gankWelfareAdapter;

    private List<Gank> gankList;

    private Snackbar mSnackbar;

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    private static final int PAGE_ONE = 1;

    private static final long REFRESH_DELAY_MILLIS = 1000;

    private static final int SPAN_COUNT = 2;


    public static GankWelfareFragment getInstance(String category) {
        GankWelfareFragment gankListFragment = new GankWelfareFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        gankListFragment.setArguments(bundle);
        return gankListFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getArguments().getString("category");
        Log.v("Type", "---------" + category);
        if (gankList == null) gankList = new ArrayList<Gank>();
        if (gankWelfareAdapter == null) {
            gankWelfareAdapter = new GankWelfareAdapter(gankList, getActivity());
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v("GankWelfareFragment", "Type:" + category + "invoke onCreateView");
        final View view = inflater.inflate(R.layout.fragment_gank_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.v("GankListFragment", "Type:" + category + " invoke onRefresh");
                if (!isDataLoaded) {
                    getBackendData(category, PAGE_ONE);
                    swipeRefreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    }, REFRESH_DELAY_MILLIS);
                } else {
                    mSnackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), "最新数据已加载", Snackbar.LENGTH_SHORT);
                    mSnackbar.show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        };
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(SPAN_COUNT, OrientationHelper.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(gankWelfareAdapter);

        gankWelfareAdapter.setOnPhotoClickListener(new OnPhotoClickListener() {
            @Override
            public void OnPhotoClickListener(View v, String url) {

                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(), new Pair<>(v, PhotoActivity.SHARED_ELEMENT_PHOTO));
                Intent intent = new Intent(getActivity(), PhotoActivity.class);
                intent.putExtra(PhotoActivity.PHOTO_URL, url);
                intent.putExtra(PhotoActivity.PHOTO_THUMBNAIL_SIZE, new int[]{400, 300});
                ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
            }
        });
        mRecyclerView.addOnScrollListener(new EndlessWaterfallOnScrollListener(staggeredGridLayoutManager, getActivity()) {
            @Override
            public void onLoadMore(int currentPage) {
                getBackendData(category, currentPage);
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
    public void getBackendData(String category, final int page) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl.CATEGORY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GankService request = retrofit.create(GankService.class);
        Call<GankData> call = request.gankData(category, page);
        call.enqueue(new Callback<GankData>() {
            @Override
            public void onResponse(Call<GankData> call, Response<GankData> response) {
                GankData gankData = response.body();
                Log.v("Data", gankData.toString());
                gankList.addAll(gankData.results);
                if (gankWelfareAdapter != null) {
                    gankWelfareAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<GankData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
