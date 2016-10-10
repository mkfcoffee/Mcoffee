package personal.mcoffee.mvp.presenter;

import personal.mcoffee.constant.BaseUrl;
import personal.mcoffee.mvp.contract.SplashContract;
import personal.mcoffee.mvp.model.SplashImg;
import personal.mcoffee.network.ZhiHuRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mcoffee on 2016/10/10.
 */

public class SplashPresenter implements SplashContract.Presenter {

    private SplashContract.View mView;

    public SplashPresenter(SplashContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void fetchImage(int width, int height) {
        Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(BaseUrl.ZHIHU_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();
        ZhiHuRequest zhiHuRequest = retrofit.create(ZhiHuRequest.class);
        Call<SplashImg> call = zhiHuRequest.getStartImage(width, height);
        call.enqueue(new Callback<SplashImg>() {
            @Override
            public void onResponse(Call<SplashImg> call, Response<SplashImg> response) {
                SplashImg splashImg = response.body();
                mView.showBackgroundImage(splashImg);
                mView.showAuthor(splashImg);
            }

            @Override
            public void onFailure(Call<SplashImg> call, Throwable t) {

            }
        });
    }
}
