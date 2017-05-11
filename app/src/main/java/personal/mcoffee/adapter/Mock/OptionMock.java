package personal.mcoffee.adapter.Mock;

import java.util.ArrayList;
import java.util.List;

import personal.mcoffee.mvp.model.Option;

/**
 * Created by Mcoffee.
 */

public class OptionMock {
    public static List<Option> provideMeOptions() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("消息通知"));
        options.add(new Option("清除缓存", "MB"));
        options.add(new Option("字体大小", "中"));
        options.add(new Option("图片加载模式"));
        options.add(new Option("推送通知"));

        options.add(new Option("检查版本", "1.0"));
        options.add(new Option("关于我们"));
        return options;
    }
}
