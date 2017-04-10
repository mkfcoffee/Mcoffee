package personal.mcoffee.mvp.contract;

import personal.mcoffee.mvp.model.SplashImg;
import personal.mcoffee.mvp.presenter.BasePresenter;
import personal.mcoffee.mvp.view.BaseView;

/**
 * Created by Mcoffee on 2016/10/10.
 */

public interface SplashContract {
    interface View extends BaseView<Presenter> {
        void showBackgroundImage(String imgUrl);

//        void showAuthor(SplashImg splashImg);
    }

    interface Presenter extends BasePresenter {
        void fetchImage(int width ,int height);
    }

}
