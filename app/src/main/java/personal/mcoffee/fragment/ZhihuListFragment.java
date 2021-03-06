package personal.mcoffee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import personal.mcoffee.R;
import personal.mcoffee.activity.WebActivity;
import personal.mcoffee.adapter.ZhihuListAdapter;
import personal.mcoffee.base.BaseFragment;
import personal.mcoffee.bean.Banner;
import personal.mcoffee.listener.EndlessRecyclerOnScrollListener;
import personal.mcoffee.listener.RecyclerViewTListener;
import personal.mcoffee.mvp.contract.ZhiHuDailyContract;
import personal.mcoffee.mvp.model.DailyStories;
import personal.mcoffee.mvp.model.News;
import personal.mcoffee.mvp.model.Story;
import personal.mcoffee.utils.Log;
import personal.mcoffee.utils.TimeUtils;
import personal.mcoffee.widget.BannerView;

/**
 * Created by Mcoffee.
 */

public class ZhihuListFragment extends BaseFragment implements ZhiHuDailyContract.View {


    @BindView(R.id.zhihu_swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.zhihu_recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.zhihu_toolbar)
    Toolbar toolbar;

    @BindView(R.id.zhihu_banner)
    BannerView bannerView;

    private Unbinder unbinder;

    private DailyStories dailyStories;

    private ZhihuListAdapter zhihuListAdapter;

    private LinearLayoutManager linearLayoutManager;

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener;

    private ZhiHuDailyContract.Presenter zhiHuDailyPresenter;

    public static ZhihuListFragment getInstance() {
        return new ZhihuListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dailyStories = new DailyStories();
        if (zhihuListAdapter == null) {
            zhihuListAdapter = new ZhihuListAdapter(dailyStories, getActivity());
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_zhihu_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        toolbar.setTitle("知乎");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        fixToolbar(toolbar);
        setHasOptionsMenu(true);
        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.v("zhihu", "isDataLoaded" + isDataLoaded);
                if (!isDataLoaded) {
                    zhiHuDailyPresenter.initialLoad();
                } else {
                    //数据已加载
                    Log.v("zhihu", "数据已加载");
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "最新数据已加载", Snackbar.LENGTH_SHORT).show();
                    hideRefresh();
                }
            }
        };
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(zhihuListAdapter);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                Log.v("zhihu", "request load more currentPage ：" + currentPage);
                String date = TimeUtils.getDate2StringBefore(currentPage - 2);
                Log.v("zhihu", "request load more date ：" + date);
                zhiHuDailyPresenter.loadMore(date);
            }
        });
//        zhihuListAdapter.setOnBannerClickListener(new BannerView.OnBannerClickListener() {
//            @Override
//            public void onClick(Banner banner, int position, View view) {
//                zhiHuDailyPresenter.displayNews(banner.id);
//            }
//        });
        zhihuListAdapter.setRecyclerViewTListener(new RecyclerViewTListener<Story>() {
            @Override
            public void onItemClick(Story story) {
                Log.v("zhihu setRecyclerViewTListener", story.id + story.title);
                zhiHuDailyPresenter.displayNews(story.id);
            }
        });

        return view;
    }

    @Override
    public void showRefresh() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideRefresh() {
        if (swipeRefreshLayout != null) {
//            swipeRefreshLayout.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    swipeRefreshLayout.setRefreshing(false);
//                }
//            }, Constant.REFRESH_DELAY_MILLIS);
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showBanners(List<Banner> banners) {
        bannerView.setData(banners, new BannerView.OnBannerClickListener() {
            @Override
            public void onClick(Banner banner, int position, View view) {
                zhiHuDailyPresenter.displayNews(banner.id);
            }
        });
    }

    @Override
    public void showStories(DailyStories dailyStories) {
        Log.v("zhihu", "showStories");
//        this.dailyStories = dailyStories;
        if (zhihuListAdapter != null) {
            zhihuListAdapter.setDailyStories(dailyStories);
            zhihuListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void appendStories(List<Story> stories) {
        if (zhihuListAdapter != null) {
            zhihuListAdapter.addStories(stories);
            zhihuListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showNews(News news) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("url", news.shareUrl);
        startActivity(intent);
    }

    @Override
    public void setPresenter(ZhiHuDailyContract.Presenter presenter) {
        this.zhiHuDailyPresenter = presenter;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
            swipeRefreshLayout.destroyDrawingCache();
            swipeRefreshLayout.clearAnimation();
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void fetchData() {
        swipeRefreshLayout.setProgressViewOffset(false, 0, 100);
        swipeRefreshLayout.setRefreshing(true);
        onRefreshListener.onRefresh();
    }

}
