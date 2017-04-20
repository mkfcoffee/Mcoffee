package personal.mcoffee.mvp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mcoffee on 2016/10/9.
 */

public class DailyStories {
    public String date;
    public List<Story> stories = new ArrayList<>();
    @SerializedName("top_stories")
    public List<Story> topStories;

    public List<Story> getStories() {
        return stories;
    }
}
