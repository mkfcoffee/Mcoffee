package personal.mcoffee.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mcoffee.
 */

public class SplashImg {
//    @SerializedName("text")
//    public String author;
//    @SerializedName("img")
//    public String imgUrl;
    @SerializedName("creatives")
    public List<Creatives> creativesList;

    public class Creatives {
        public String url;
        @SerializedName("start_time")
        public long startTime;
        public int type;
        public String id;
    }
}
