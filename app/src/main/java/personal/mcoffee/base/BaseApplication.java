package personal.mcoffee.base;

import android.app.Application;

import personal.mcoffee.di.component.ApplicationComponent;
import personal.mcoffee.di.component.DaggerApplicationComponent;
import personal.mcoffee.di.module.ApplicationModule;

/**
 * Created by Mcoffee on 2016/9/3.
 */
public class BaseApplication extends Application {


    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initApplicationComponent();
    }

    private void initApplicationComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(getApplicationContext())).build();
        mApplicationComponent.inject(this);
    }

    public ApplicationComponent getmApplicationComponent() {
        return mApplicationComponent;
    }

}
