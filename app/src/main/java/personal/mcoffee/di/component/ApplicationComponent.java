package personal.mcoffee.di.component;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import personal.mcoffee.base.BaseActivity;
import personal.mcoffee.base.BaseApplication;
import personal.mcoffee.di.module.ApplicationModule;

/**
 * Created by Mcoffee on 2016/9/8.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context getApplicationContext();

    void inject(BaseApplication mBaseApplication);

    void inject(BaseActivity mBaseActivity);
}
