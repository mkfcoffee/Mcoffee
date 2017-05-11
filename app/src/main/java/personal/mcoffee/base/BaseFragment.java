package personal.mcoffee.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by Mcoffee on 2016/8/25.
 * fragment懒加载借鉴自http://www.jianshu.com/p/c5d29a0c3f4c
 */
public abstract class BaseFragment extends Fragment {

    private static final String IS_HIDDEN = "IS_HIDDEN";
    //视图是否初始化
    protected boolean isViewInitialized = false;
    //数据是否已加载
    protected boolean isDataLoaded = false;
    //是否可见
    protected boolean isVisibleToUser = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isHidden = savedInstanceState.getBoolean(IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(IS_HIDDEN, isHidden());
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewInitialized = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        isViewInitialized = true ;
//        prepareFetchData();
        if (getUserVisibleHint() && !isDataLoaded) {
            fetchData();
            isDataLoaded = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        this.isVisibleToUser = isVisibleToUser;
//        prepareFetchData();
        if (isViewInitialized && isVisibleToUser && !isDataLoaded) {
            fetchData();
            isDataLoaded = true;
        }
    }

    public abstract void fetchData();

//    public boolean prepareFetchData() {
//        return prepareFetchData(false);
//    }
//
//    /**
//     * fetch data
//     * @param forceUpdate 强制获取
//     * @return
//     */
//    public boolean prepareFetchData(boolean forceUpdate) {
//        if (isVisibleToUser && isViewInitialized && (!isDataLoaded || forceUpdate)) {
//            fetchData();
//            isDataLoaded = true;
//            return true;
//        }
//        return false;
//    }

    protected void fixToolbar(Toolbar toolbar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusHeight = getStatusBarHeight(getActivity());
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
            layoutParams.setMargins(0, statusHeight, 0, 0);
        }
    }

    /**
     * 获取系统状态栏高度
     *
     * @param context
     * @return
     */
    public int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }
}
