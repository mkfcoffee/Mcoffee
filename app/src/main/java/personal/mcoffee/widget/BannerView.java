package personal.mcoffee.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import personal.mcoffee.R;
import personal.mcoffee.bean.Banner;
import personal.mcoffee.utils.Log;

/**
 * Created by Mcoffee on 2016/10/12.
 */

public class BannerView extends FrameLayout implements ViewPager.OnPageChangeListener {

    private Context mContext;
    //banner ViewPager
    private ViewPager mViewPager;
    //标题
    private TextView mTitleTv;
    //指示器
    private LinearLayout mIndicatorLayout;

    private List<View> viewLists = new ArrayList<>();

    private ImageView[] indicatorViews;

    private int MOVE = 100;

    private int WAIT = 101;
    //是否滚动
    private boolean isScrolling = false;
    //是否循环轮播
    private boolean isCycle = true;
    //是否轮播
    private boolean isTakeTurns = true;
    //轮播时间
    private int DELAY_TIME = 5000;
    //当前位置
    private int currentPosition = 0;
    //点击松开后的时间
    private long releaseTime = 0;
    //Banner监听
    private OnBannerClickListener onBannerClickListener;

    private BannerAdapter bannerAdapter;

    private List<Banner> bannerList;

    private int indicatorSelected = R.drawable.circledot_selected;

    private int indicatorUnselected = R.drawable.circledot_un_selected;

    private Handler mHandler;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (isTakeTurns) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - releaseTime > DELAY_TIME - 500) {
                    mHandler.sendEmptyMessage(MOVE);
                } else {
                    mHandler.sendEmptyMessage(WAIT);
                }
            }
        }
    };


    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    /**
     * 初始化视图，handler
     */
    private void initView() {

        LayoutInflater.from(mContext).inflate(R.layout.widget_banner, this, true);
        mViewPager = (ViewPager) findViewById(R.id.banner_viewpager);
        mTitleTv = (TextView) findViewById(R.id.banner_title);
        mIndicatorLayout = (LinearLayout) findViewById(R.id.banner_indicator);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (viewLists.size() <= 0) return;
                if (msg.what == MOVE) {
                    if (!isScrolling) {
                        //切换到下一页
                        int position = (currentPosition + 1) % viewLists.size();
                        mViewPager.setCurrentItem(position, true);
                    }
                    releaseTime = System.currentTimeMillis();
                    mHandler.removeCallbacks(runnable);
                    mHandler.postDelayed(runnable, DELAY_TIME);
                } else if (msg.what == WAIT) {
                    mHandler.removeCallbacks(runnable);
                    mHandler.postDelayed(runnable, DELAY_TIME);
                }

            }
        };
    }

    /**
     * 初始化viewpager
     *
     * @param list         要显示的数据
     * @param listener     banner点击监听事件
     */
    public void setData(List<Banner> list, OnBannerClickListener listener) {
        setData(list, listener, 0);
    }

    /**
     * 初始化viewpager
     *
     * @param list         要显示的数据
     * @param listener     banner点击监听事件
     * @param showPosition 默认显示位置
     */
    public void setData(List<Banner> list, OnBannerClickListener listener,
                        int showPosition) {
        if (list == null || list.size() == 0) {
            //没有数据时隐藏整个布局
            this.setVisibility(View.GONE);
            return;
        }
        viewLists.clear();
        bannerList = list;
        if (isCycle) {
            //添加轮播图View，数量为集合数+2
            // 将最后一个View添加进来
            viewLists.add(getBannerView(bannerList.get(bannerList.size() - 1).url));
            for (int i = 0; i < bannerList.size(); i++) {
                viewLists.add(getBannerView(bannerList.get(i).url));
            }
            // 将第一个View添加进来
            viewLists.add(getBannerView(bannerList.get(0).url));
        } else {
            //只添加对应数量的View
            for (int i = 0; i < bannerList.size(); i++) {
                viewLists.add(getBannerView(bannerList.get(i).url));
            }
        }
        if (viewLists == null || viewLists.size() == 0) {
            //没有View时隐藏整个布局
            this.setVisibility(View.GONE);
            return;
        }
        onBannerClickListener = listener;
        int ivSize = viewLists.size();
        // 设置指示器
        indicatorViews = new ImageView[ivSize];
        if (isCycle)
            indicatorViews = new ImageView[ivSize - 2];
        mIndicatorLayout.removeAllViews();
        for (int i = 0; i < indicatorViews.length; i++) {
            indicatorViews[i] = new ImageView(mContext);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(10, 0, 10, 0);
            indicatorViews[i].setLayoutParams(lp);
            mIndicatorLayout.addView(indicatorViews[i]);
        }
        bannerAdapter = new BannerAdapter();
        // 默认指向第一项，下方viewPager.setCurrentItem将触发重新计算指示器指向
        setIndicator(0);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setOnPageChangeListener(this);
        mViewPager.setAdapter(bannerAdapter);
        if (showPosition < 0 || showPosition >= viewLists.size())
            showPosition = 0;
        if (isCycle) {
            showPosition = showPosition + 1;
        }
        mViewPager.setCurrentItem(showPosition);
        setTakeTurns(true);//设置轮播
    }

    private View getBannerView(String url) {
        RelativeLayout rl = new RelativeLayout(mContext);
        //添加一个ImageView，并加载图片
        ImageView imageView = new ImageView(mContext);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(layoutParams);
        Glide.with(mContext).load(url).into(imageView);
        rl.addView(imageView);
        return rl;
    }

    /**
     * 设置指示器图片，在setData之前调用
     *
     * @param selectId   选中时的图片
     * @param unselectId 未选中时的图片
     */
    public void setIndicatorIds(@IdRes int selectId, @IdRes int unselectId) {
        indicatorSelected = selectId;
        indicatorUnselected = unselectId;
    }

    /**
     * 设置指示器
     *
     * @param selectedPosition 默认指示器位置
     */
    private void setIndicator(int selectedPosition) {
        setText(mTitleTv, bannerList.get(selectedPosition).title);
        try {
            for (int i = 0; i < indicatorViews.length; i++) {
                indicatorViews[i]
                        .setBackgroundResource(indicatorUnselected);
            }
            if (indicatorViews.length > selectedPosition)
                indicatorViews[selectedPosition]
                        .setBackgroundResource(indicatorSelected);
        } catch (Exception e) {
            Log.i(BannerView.class.getSimpleName(), "指示器路径不正确");
        }
    }

    /**
     * 为textview设置文字
     *
     * @param textView
     * @param text
     */
    public static void setText(TextView textView, int text) {
        if (textView != null) setText(textView, text + "");
    }

    /**
     * 为textview设置文字
     *
     * @param textView
     * @param text
     */
    public static void setText(TextView textView, String text) {
        if (text != null && textView != null) textView.setText(text);
    }

    /**
     * 是否循环，默认开启。必须在setData前调用
     *
     * @param isCycle 是否循环
     */
    public void setCycle(boolean isCycle) {
        this.isCycle = isCycle;
    }

    /**
     * 是否处于循环状态
     *
     * @return
     */
    public boolean isCycle() {
        return isCycle;
    }

    /**
     * 设置是否轮播，默认轮播,轮播一定是循环的
     *
     * @param isTakeTurns
     */
    public void setTakeTurns(boolean isTakeTurns) {
        this.isTakeTurns = isTakeTurns;
        isCycle = true;
        if (isTakeTurns) {
            mHandler.postDelayed(runnable, DELAY_TIME);
        }
    }

    /**
     * 刷新数据，当外部视图更新后，通知刷新数据
     */
    public void refreshData() {
        if (bannerAdapter != null)
            bannerAdapter.notifyDataSetChanged();
    }

    /**
     * 是否处于轮播状态
     *
     * @return
     */
    public boolean isTakeTurns() {
        return isTakeTurns;
    }

    /**
     * 设置轮播暂停时间,单位毫秒
     *
     * @param delayTime
     */
    public void setDelay(int delayTime) {
        this.DELAY_TIME = delayTime;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        int max = viewLists.size() - 1;
        currentPosition = position;
        if (isCycle) {
            if (position == 0) {
                //滚动到mView的1个（界面上的最后一个），将mCurrentPosition设置为max - 1
                currentPosition = max - 1;
            } else if (position == max) {
                //滚动到mView的最后一个（界面上的第一个），将mCurrentPosition设置为1
                currentPosition = 1;
            }
            position = currentPosition - 1;
        }
        setIndicator(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 1) { // viewPager在滚动
            isScrolling = true;
            return;
        } else if (state == 0) { // viewPager滚动结束

            releaseTime = System.currentTimeMillis();
            //跳转到第mCurrentPosition个页面（没有动画效果，实际效果页面上没变化）
            mViewPager.setCurrentItem(currentPosition, false);
        }
        isScrolling = false;
    }


    /**
     * Banner点击监听
     */
    public  interface OnBannerClickListener {
        void onClick(Banner banner, int position, View view);
    }

    class BannerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return viewLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = viewLists.get(position);
            if (onBannerClickListener != null) {
                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBannerClickListener.onClick(bannerList.get(currentPosition - 1), currentPosition, v);
                    }
                });
            }
            container.addView(view);
            return view;
        }
    }

}
