package personal.mcoffee.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mcoffee on 2016/10/9.
 */

public class Story {
    public List<String> images;
    public int type;
    public int id;
    @SerializedName("ga_prefix")
    public String gaPrefix;
    public String title;

}
