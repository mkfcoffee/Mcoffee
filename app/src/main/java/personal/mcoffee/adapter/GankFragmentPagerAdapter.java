package personal.mcoffee.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import personal.mcoffee.fragment.GankListFragment;

/**
 * Created by Mcoffee on 2016/8/29.
 */
public class GankFragmentPagerAdapter extends FragmentPagerAdapter {

    //福利 | Android | iOS | 休息视频 | 拓展资源 | 前端
    private String[] titles = {"Android", "iOS", "前端", "拓展资源", "福利"};

    private Context context;

    public GankFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return GankListFragment.getInstance(titles[position]);
    }

}
