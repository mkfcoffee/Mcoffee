package personal.mcoffee.di.module;

import android.app.Activity;

import dagger.Module;
import dagger.Provides;
import personal.mcoffee.di.PerActivity;

/**
 * Created by Mcoffee on 2016/9/8.
 */
@Module
public class ActivityModule {

    private final Activity activity ;

    public ActivityModule(Activity activity){this.activity = activity;}

    @PerActivity
    @Provides
    public  Activity provideActivity(){return activity;}
}
