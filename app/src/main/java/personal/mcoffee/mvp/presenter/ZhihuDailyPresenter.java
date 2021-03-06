package personal.mcoffee.mvp.presenter;

import java.util.ArrayList;
import java.util.List;

import personal.mcoffee.bean.Banner;
import personal.mcoffee.constant.BaseUrl;
import personal.mcoffee.mvp.contract.ZhiHuDailyContract;
import personal.mcoffee.mvp.model.DailyStories;
import personal.mcoffee.mvp.model.News;
import personal.mcoffee.mvp.model.Story;
import personal.mcoffee.network.ZhiHuService;
import personal.mcoffee.utils.Log;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Mcoffee.
 */

public class ZhihuDailyPresenter implements ZhiHuDailyContract.Presenter {

    private ZhiHuDailyContract.View mView;

    public ZhihuDailyPresenter(ZhiHuDailyContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void initialLoad() {
        Log.v("zhihu", "ZhiHuPresenter initiaload");
        getZhihuService().getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DailyStories>() {
                    @Override
                    public void onNext(DailyStories dailyStories) {
                        List<Banner> banners = new ArrayList<>();
                        if (dailyStories.topStories == null || dailyStories.topStories.size() < 1)
                            throw new NullPointerException("there is no banners' data");
                        for (int i = 0; i < dailyStories.topStories.size(); i++) {
                            Story story = dailyStories.topStories.get(i);
                            banners.add(new Banner(story.title, story.image, story.id));
                        }
                        mView.showBanners(banners);
                        mView.showStories(dailyStories);
                        mView.hideRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    @Override
    public void loadMore(String date) {
        getZhihuService().getBeforeNews(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<DailyStories, List<Story>>() {
                    @Override
                    public List<Story> call(DailyStories dailyStories) {
                        return dailyStories.getStories();
                    }
                })
                .subscribe(new Subscriber<List<Story>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Story> stories) {
                        mView.showRefresh();
                        mView.appendStories(stories);
                        mView.hideRefresh();
                    }
                });


    }

    @Override
    public void displayNews(int id) {
        getZhihuService().getDetailNews(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<News>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(News news) {
                        mView.showNews(news);
                    }
                });
    }

    /**
     * 获取知乎API访问Service
     *
     * @return
     */
    public ZhiHuService getZhihuService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseUrl.ZHIHU_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(ZhiHuService.class);
    }


}
