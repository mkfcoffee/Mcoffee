package personal.mcoffee.fragment;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import personal.mcoffee.R;
import personal.mcoffee.adapter.GankFragmentPagerAdapter;
import personal.mcoffee.base.BaseFragment;
import personal.mcoffee.di.component.DaggerGankComponent;
import personal.mcoffee.di.component.GankComponent;
import personal.mcoffee.di.module.GankModule;

/**
 * Created by Mcoffee on 2016/8/27.
 */
public class GankFragment extends BaseFragment {

    @BindView(R.id.gank_toolbar)
    Toolbar toolbar;

    @BindView(R.id.gank_tablayout)
    TabLayout tabLayout;

    @BindView(R.id.gank_viewpager)
    ViewPager viewPager;

    private Unbinder unbinder;

    @Inject
    GankFragmentPagerAdapter adapter;

    @Inject
    List<String> titles;

    SearchView searchView;

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
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbar.setTitle("Gank");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        fixToolbar(toolbar);
        setHasOptionsMenu(true);
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i);
            if ("福利".equals(title)) {
                adapter.addTab(GankWelfareFragment.getInstance(title), title);
            } else {
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.activity_main_toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if (searchItem != null) {
            searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            EditText searchEt = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchEt.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
            searchEt.setHintTextColor(ContextCompat.getColor(getContext(), R.color.white));
            searchView.setSubmitButtonEnabled(false);
            searchView.setQueryHint("请输入搜索内容");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            SearchManager searchManager = (SearchManager) getActivity().getSystemService(getActivity().SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                break;
            case R.id.action_settings:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
