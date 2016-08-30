package personal.mcoffee.network;

import personal.mcoffee.bean.GankData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Mcoffee on 2016/8/30.
 */
public interface GankRequest {

    @GET("{category}/10/{page}")
     Call<GankData> gankData(@Path("category")String category,@Path("page") int page);
}
