package personal.mcoffee.bean;

/**
 * Created by Mcoffee on 2016/8/29.
 */
public class GankContent {
    public String error;
    public Results results;

    public class Results {
        public String _id;
        public String content;
        public String publishedAt;
        public String title;
    }

}
