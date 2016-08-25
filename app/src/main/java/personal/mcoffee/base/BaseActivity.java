package personal.mcoffee.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import personal.mcoffee.R;

/**
 * BaseActivity
 */
public class BaseActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        ButterKnife.bind(this);

    }

    /**
     * 初始化底部导航栏
     */
    protected  void initBottomNavigation(){
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.icon_gank,"干货"))
                           .addItem(new BottomNavigationItem(R.drawable.icon_zhihu,"知乎"))
                           .addItem(new BottomNavigationItem(R.drawable.icon_video,"视频"))
                           .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }

}
