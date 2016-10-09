package personal.mcoffee.di.module;

import android.support.v4.app.FragmentManager;

import java.util.Arrays;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import personal.mcoffee.adapter.GankFragmentPagerAdapter;
import personal.mcoffee.di.PerActivity;

/**
 * Created by Mcoffee on 2016/9/8.
 */
@Module
public class GankModule {
    FragmentManager fragmentManager;

    public GankModule(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @PerActivity
    @Provides
    GankFragmentPagerAdapter provideGankFragmentAdapter() {
        return new GankFragmentPagerAdapter(fragmentManager);
    }

    @PerActivity
    @Provides
    List<String> provideGankTitles() {
        return Arrays.asList("Android", "iOS", "前端", "拓展资源", "福利");
    }
}
