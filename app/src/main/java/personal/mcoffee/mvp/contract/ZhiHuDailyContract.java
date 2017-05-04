package personal.mcoffee.mvp.contract;

import java.util.List;

import personal.mcoffee.mvp.model.DailyStories;
import personal.mcoffee.mvp.model.News;
import personal.mcoffee.mvp.model.Story;
import personal.mcoffee.mvp.presenter.BasePresenter;
import personal.mcoffee.mvp.view.BaseView;

/**
 * Created by Mcoffee on 2016/10/9.
 */

public interface ZhiHuDailyContract {
    interface View extends BaseView<Presenter> {
        void showRefresh();

        void hideRefresh();

        void showStories(DailyStories dailyStories);

        void appendStories(List<Story> stories);

        void showNews(News news);

    }

    interface Presenter extends BasePresenter {
        void initialLoad();

        void loadMore(String date);

        void displayNews(int id);
    }
}
