package personal.mcoffee.mvp.contract;

import personal.mcoffee.mvp.model.DailyStories;
import personal.mcoffee.mvp.presenter.BasePresenter;
import personal.mcoffee.mvp.view.BaseView;

/**
 * Created by Mcoffee on 2016/10/9.
 */

public interface ZhiHuDailyContract {
    interface View extends BaseView<Presenter> {
        void showRefresh();

        void hideRefresh();

        void showStory(DailyStories dailyStories);

        void appendStory(DailyStories dailyStories);

        void showError(String error);
    }

    interface Presenter extends BasePresenter {
        void loadMore(String date);
    }
}
