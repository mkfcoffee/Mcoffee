package personal.mcoffee.di.component;

import dagger.Component;
import personal.mcoffee.activity.MainActivity;
import personal.mcoffee.di.PerActivity;
import personal.mcoffee.di.module.MainActivityModule;

/**
 * Created by Mcoffee on 2016/9/7.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {
    void inject(MainActivity activity);
}
