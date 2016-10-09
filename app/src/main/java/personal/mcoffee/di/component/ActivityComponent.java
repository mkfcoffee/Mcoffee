package personal.mcoffee.di.component;

import android.app.Activity;

import dagger.Component;
import personal.mcoffee.di.PerActivity;
import personal.mcoffee.di.module.ActivityModule;

/**
 * Created by Mcoffee on 2016/9/8.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class , modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();
}
