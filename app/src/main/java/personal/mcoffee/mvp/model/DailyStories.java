package personal.mcoffee.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Mcoffee on 2016/10/9.
 */

public class DailyStories {
    public String date;
    public List<Story> stories;
    @SerializedName("top_stories")
    public List<Story> topStories;
}
