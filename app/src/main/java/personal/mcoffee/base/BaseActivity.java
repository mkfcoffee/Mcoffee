package personal.mcoffee.base;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;

import personal.mcoffee.R;
import personal.mcoffee.constant.Constant;
import personal.mcoffee.di.component.ApplicationComponent;
import personal.mcoffee.utils.Log;

/**
 * BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String ACTION_EXIT = "action_exit";

    private long firstClickBackTime = 0;

    private BroadcastReceiver brc = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (ACTION_EXIT.equals(intent.getAction())) {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);
        initData();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_EXIT);
        registerReceiver(brc, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(brc);
    }

    protected ApplicationComponent getApplicationComponent(){
        return  ((BaseApplication)getApplication()).getmApplicationComponent();
    }

    /**
     * add Fragment
     * @param fragment
     */
    protected void addFragment(@IdRes int containerViewId, @NonNull Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                                   .replace(containerViewId,fragment,fragment.getClass().getSimpleName())
                                   .addToBackStack(fragment.getClass().getSimpleName())
                                   .commitAllowingStateLoss();
        Log.v("Fragment transaction","addFragment "+fragment.getClass().getSimpleName());
    }

    /**
     * add Fragment
     * @param fragment
     */
    protected void replaceFragment(@IdRes int containerViewId, @NonNull Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                                   .replace(containerViewId,fragment,fragment.getClass().getSimpleName())
                                   .commitAllowingStateLoss();
        Log.v("Fragment transaction","rePlaceFragment "+fragment.getClass().getSimpleName());
    }

    /**
     * remove Fragment
     */
    protected void removeFragment(){
        if(getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }else{
            finish();
        }
    }

    /**
     * 退出app
     */
    protected void exitApp() {
        Intent intent = new Intent();
        intent.setAction(ACTION_EXIT);
        sendBroadcast(intent);
    }

    protected  void doubleClickExit(){
        if(System.currentTimeMillis() - firstClickBackTime > Constant.EXIT_MILLIS){
            firstClickBackTime = System.currentTimeMillis();
        }else{
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
//                finish();
                doubleClickExit();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化View
     */
    protected abstract void initView();
}
