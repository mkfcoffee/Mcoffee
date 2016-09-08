package personal.mcoffee.di.module;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mcoffee on 2016/9/7.
 */
@Module
public class ApplicationModule {
    private Context mContext;

    public ApplicationModule(Context context){ mContext = context;}

    @Provides
    @Singleton
    Context provideApplicationContext(){return mContext;}

}
