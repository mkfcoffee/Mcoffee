package personal.mcoffee.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

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
import personal.mcoffee.utils.Log;

public class MainActivity extends BaseActivity {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_container)
    RelativeLayout mRelativeLayout;

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
        initToolbar();
        initBottomNavigation();
    }


    /**
     * 初始化底部导航栏
     */
    protected void initBottomNavigation() {
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.icon_gank, "干货"))
                .addItem(new BottomNavigationItem(R.drawable.icon_zhihu, "知乎"))
                .addItem(new BottomNavigationItem(R.drawable.icon_video, "视频"))
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                Log.v(MainActivity.this, "onTabSelected postion:" + position);
                switch (position){
                    case 0:
                        getSupportActionBar().setTitle("Gank");
                        addFragment(R.id.main_container, GankFragment.getInstance());
                        break;
                    case 1:
                        getSupportActionBar().setTitle("知乎日报");
                        addFragment(R.id.main_container, ZhihuListFragment.getInstance());
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
        //初次选中
        getSupportActionBar().setTitle("Gank");
        addFragment(R.id.main_container, GankFragment.getInstance());
        bottomNavigationBar.setFirstSelectedPosition(0);
    }

    /**
     * 初始化Toolbar
     */
    protected  void initToolbar(){
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_toolbar_menu,menu);
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
}
