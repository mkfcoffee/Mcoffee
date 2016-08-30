package personal.mcoffee.bean;

import java.util.List;

/**
 * Created by Mcoffee on 2016/8/27.
 */
public class DailyGank {

    public String category;
    public String error;
    public GankResults gankResults;

    public class GankResults {
        public List<Gank> androidList;
        public List<Gank> appList;
        public List<Gank> iOSList;
        public List<Gank> restVideoList;
        public List<Gank> frontList;
        public List<Gank> recommendList;
        public List<Gank> benefitList;
    }
}
