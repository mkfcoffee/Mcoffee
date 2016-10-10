package personal.mcoffee.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import personal.mcoffee.R;
import personal.mcoffee.fragment.SplashFragment;
import personal.mcoffee.mvp.presenter.SplashPresenter;

/**
 * Created by Mcoffee on 2016/10/10.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SplashFragment fragment = SplashFragment.getInstance();
        addFragment(R.id.splash_container, fragment);
        SplashPresenter presenter = new SplashPresenter(fragment);

    }

    /**
     * add Fragment
     *
     * @param fragment
     */
    protected void addFragment(@IdRes int containerViewId, @NonNull Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .add(containerViewId, fragment, fragment.getClass().getSimpleName())
                .commit();
    }
}
