package personal.mcoffee.network;

import java.util.List;

import personal.mcoffee.mvp.model.DailyStories;
import personal.mcoffee.mvp.model.SplashImg;
import personal.mcoffee.mvp.model.Story;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Mcoffee.
 */

public interface ZhiHuService {
    //http://news-at.zhihu.com/api/4/start-image/1080*1776 失效
    //http://news-at.zhihu.com/api/7/prefetch-launch-images/1080*1920
    @GET("prefetch-launch-images/{width}*{height}")
    Observable<SplashImg> getStartImage(@Path("width") int width, @Path("height") int height);

    //http://news-at.zhihu.com/api/4/news/latest
    @GET("news/latest")
    Observable<DailyStories> getLatestNews();

    //http://news.at.zhihu.com/api/4/news/before/20131119
    @GET("news/before/{date}")
    Observable<DailyStories> getBeforeNews(@Path("date") String date);
}
