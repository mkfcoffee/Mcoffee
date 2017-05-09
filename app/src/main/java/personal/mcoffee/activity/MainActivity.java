package personal.mcoffee.activity;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import personal.mcoffee.R;
import personal.mcoffee.base.BaseActivity;
import personal.mcoffee.di.component.DaggerMainActivityComponent;
import personal.mcoffee.di.component.MainActivityComponent;
import personal.mcoffee.fragment.GankFragment;
import personal.mcoffee.fragment.ZhihuListFragment;
import personal.mcoffee.mvp.presenter.ZhihuDailyPresenter;
import personal.mcoffee.utils.Log;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

//    @BindView(R.id.main_toolbar)
//    Toolbar toolbar;

    @BindView(R.id.main_container)
    RelativeLayout mRelativeLayout;

    SearchView searchView;

    MainActivityComponent mainActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivityComponent = DaggerMainActivityComponent.builder()
                                                           .applicationComponent(getApplicationComponent())
                                                           .build();
        mainActivityComponent.inject(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        initToolbar();
        initBottomNavigation();
    }


    /**
     * 初始化底部导航栏
     */
    protected void initBottomNavigation() {
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.icon_gank, "干货"))
                .addItem(new BottomNavigationItem(R.drawable.icon_zhihu, "知乎"))
                .addItem(new BottomNavigationItem(R.drawable.icon_my, "我的"))
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                Log.v(MainActivity.this, "onTabSelected postion:" + position);
                switch (position){
                    case 0:
//                        getSupportActionBar().setTitle("Gank");
                        addFragment(R.id.main_container, GankFragment.getInstance());
                        break;
                    case 1:
//                        getSupportActionBar().setTitle("知乎日报");
                        ZhihuListFragment zhihuListFragment = ZhihuListFragment.getInstance();
                        addFragment(R.id.main_container, zhihuListFragment);
                        ZhihuDailyPresenter zhihuDailyPresenter = new ZhihuDailyPresenter(zhihuListFragment);
                        break;
                    case 2:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(int position) {
                Log.v(MainActivity.this, "onTabUnselected postion:" + position);
            }

            @Override
            public void onTabReselected(int position) {
                Log.v(MainActivity.this, "onTabReselected postion:" + position);
            }
        });
//        初次选中
//        getSupportActionBar().setTitle("Gank");
        addFragment(R.id.main_container, GankFragment.getInstance());
        bottomNavigationBar.setFirstSelectedPosition(0);
    }

//    /**
//     * 初始化Toolbar
//     */
//    protected  void initToolbar(){
//        setSupportActionBar(toolbar);
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_toolbar_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        if(searchItem != null){
            searchView =(SearchView) MenuItemCompat.getActionView(searchItem);
            EditText searchEt = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchEt.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            searchEt.setHintTextColor(ContextCompat.getColor(this,R.color.white));
            searchView.setSubmitButtonEnabled(false);
            searchView.setQueryHint("请输入搜索内容");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                break;
            case R.id.action_settings:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }
}
