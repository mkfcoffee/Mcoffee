package personal.mcoffee.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import personal.mcoffee.R;
import personal.mcoffee.adapter.GankFragmentPagerAdapter;
import personal.mcoffee.adapter.GankWelfareAdapter;
import personal.mcoffee.base.BaseFragment;
import personal.mcoffee.di.component.DaggerGankComponent;
import personal.mcoffee.di.component.GankComponent;
import personal.mcoffee.di.module.GankModule;

/**
 * Created by Mcoffee on 2016/8/27.
 */
public class GankFragment extends BaseFragment {

    @BindView(R.id.gank_tablayout)
    TabLayout tabLayout;

    @BindView(R.id.gank_viewpager)
    ViewPager viewPager;

    private Unbinder unbinder;

    @Inject
    GankFragmentPagerAdapter adapter;

    @Inject
    List<String> titles;

    private GankComponent gankComponent;

    public static GankFragment getInstance() {
        return new GankFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gankComponent = DaggerGankComponent.builder().gankModule(new GankModule(getChildFragmentManager())).build();
        gankComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank,container,false);
        unbinder = ButterKnife.bind(this,view);
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i);
            if("福利".equals(title)){
                adapter.addTab(GankWelfareFragment.getInstance(title),title);
            }else{
                adapter.addTab(GankListFragment.getInstance(title), title);
            }
        }
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(adapter);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
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
