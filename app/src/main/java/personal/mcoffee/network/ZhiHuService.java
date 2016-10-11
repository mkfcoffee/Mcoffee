package personal.mcoffee.network;

import personal.mcoffee.mvp.model.SplashImg;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Mcoffee on 2016/10/10.
 */

public interface ZhiHuService {
    //http://news-at.zhihu.com/api/4/start-image/1080*1776
    @GET("start-image/{width}*{height}")
    Observable<SplashImg> getStartImage(@Path("width") int width , @Path("height") int height);
}
