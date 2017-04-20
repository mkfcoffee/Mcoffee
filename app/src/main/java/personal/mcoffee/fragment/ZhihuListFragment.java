package personal.mcoffee.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import personal.mcoffee.R;
import personal.mcoffee.adapter.ZhihuListAdapter;
import personal.mcoffee.base.BaseFragment;
import personal.mcoffee.constant.Constant;
import personal.mcoffee.mvp.contract.ZhiHuDailyContract;
import personal.mcoffee.mvp.model.DailyStories;
import personal.mcoffee.mvp.model.Story;
import personal.mcoffee.utils.Log;

/**
 * Created by Mcoffee.
 */

public class ZhihuListFragment extends BaseFragment implements ZhiHuDailyContract.View {

    @BindView(R.id.zhihu_swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.zhihu_recyclerView)
    RecyclerView mRecyclerView;

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
        View view = inflater.inflate(R.layout.fragment_zhihu_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.v("zhihu","isDataLoaded" +isDataLoaded);
                if (!isDataLoaded) {
                    zhiHuDailyPresenter.initialLoad();
                } else {
                    //数据已加载
                    Log.v("zhihu","数据已加载");
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

        return view;
    }

    @Override
    public void showRefresh() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideRefresh() {
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, Constant.REFRESH_DELAY_MILLIS);
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
    public void setPresenter(ZhiHuDailyContract.Presenter presenter) {
        this.zhiHuDailyPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void fetchData() {
        showRefresh();
        onRefreshListener.onRefresh();
    }
}
