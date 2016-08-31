package personal.mcoffee.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import personal.mcoffee.R;
import personal.mcoffee.adapter.GankFragmentPagerAdapter;
import personal.mcoffee.base.BaseFragment;

/**
 * Created by Mcoffee on 2016/8/27.
 */
public class GankFragment extends BaseFragment {

    @BindView(R.id.gank_tablayout)
    TabLayout tabLayout;

    @BindView(R.id.gank_viewpager)
    ViewPager viewPager;

    private Unbinder unbinder;

    private GankFragmentPagerAdapter adapter;

    String[] titles = {"Android", "iOS", "前端", "拓展资源", "福利"};


    public static GankFragment getInstance() {
        GankFragment gankFragment = new GankFragment();
        return  gankFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank,container,false);
        unbinder = ButterKnife.bind(this,view);
        adapter = new GankFragmentPagerAdapter(getChildFragmentManager());
        for (int i = 0; i < titles.length; i++) {
            adapter.addTab(GankListFragment.getInstance(titles[i]), titles[i]);
        }
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void fetchData() {

    }
}
