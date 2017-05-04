package personal.mcoffee.bean;

/**
 * Created by Mcoffee on 2016/10/12.
 */

public class Banner {

    public String title;
    public String url;
    public int id;

    public Banner(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public Banner(String title, String url, int id) {
        this.title = title;
        this.url = url;
        this.id = id;
    }


}
