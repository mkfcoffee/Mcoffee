package personal.mcoffee.mvp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mcoffee.
 */

public class News {

    @SerializedName("image_source")
    public String imageSource;

    public String title;

    @SerializedName("image")
    public String imageUrl;

    @SerializedName("share_url")
    public String shareUrl;

}
