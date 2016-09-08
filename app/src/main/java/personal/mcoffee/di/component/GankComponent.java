package personal.mcoffee.di.component;

import dagger.Component;
import personal.mcoffee.di.PerActivity;
import personal.mcoffee.di.module.GankModule;
import personal.mcoffee.fragment.GankFragment;
import personal.mcoffee.fragment.GankListFragment;

/**
 * Created by Mcoffee on 2016/9/8.
 */
@PerActivity
@Component(modules = GankModule.class)
public interface GankComponent {
    void inject(GankFragment gankFragment);

    void inject(GankListFragment gankListFragment);
}
