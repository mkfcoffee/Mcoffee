package personal.mcoffee.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import personal.mcoffee.R;

/**
 * BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final String ACTION_EXIT = "action_exit";

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

    /**
     * add Fragment
     * @param fragment
     */
    protected void addFragment(@IdRes int containerViewId, @NonNull Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                                   .replace(containerViewId,fragment,fragment.getClass().getSimpleName())
                                   .addToBackStack(fragment.getClass().getSimpleName())
                                   .commitAllowingStateLoss();
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

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化View
     */
    protected abstract void initView();
}
