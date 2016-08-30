package personal.mcoffee.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
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

    private FragmentPagerAdapter fragmentPagerAdapter;


    public static GankFragment getInstance() {
        GankFragment gankFragment = new GankFragment();
        return  gankFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gank,container,false);
        unbinder = ButterKnife.bind(this,view);
        fragmentPagerAdapter = new GankFragmentPagerAdapter(getActivity().getSupportFragmentManager(),getActivity());
        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
