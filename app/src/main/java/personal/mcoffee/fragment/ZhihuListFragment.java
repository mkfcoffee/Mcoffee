package personal.mcoffee.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import personal.mcoffee.R;
import personal.mcoffee.base.BaseFragment;
import personal.mcoffee.bean.Banner;
import personal.mcoffee.widget.BannerView;

/**
 * Created by Mcoffee on 2016/10/12.
 */

public class ZhihuListFragment extends BaseFragment {

    @BindView(R.id.zhihu_banner)
    BannerView bannerView;

    private Unbinder unbinder;

    public static ZhihuListFragment getInstance(){
        return new ZhihuListFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zhihu_list,container,false);
        final List<Banner> banners = new ArrayList<>();
        banners.add(new Banner("标题1",
                "http://img2.3lian.com/2014/c7/25/d/40.jpg"));
        banners.add(new Banner("标题2",
                "http://img2.3lian.com/2014/c7/25/d/41.jpg"));
        banners.add(new Banner("标题3",
                "http://imgsrc.baidu.com/forum/pic/item/b64543a98226cffc8872e00cb9014a90f603ea30.jpg"));
        banners.add(new Banner("标题4",
                "http://imgsrc.baidu.com/forum/pic/item/261bee0a19d8bc3e6db92913828ba61eaad345d4.jpg"));

        unbinder = ButterKnife.bind(this,view);
        bannerView.setData(banners, new BannerView.OnBannerClickListener() {
            @Override
            public void onClick(Banner banner, int position, View view) {
                Toast.makeText(getActivity(),"这是标题："+banners.get(position-1).title,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void fetchData() {

    }
}
