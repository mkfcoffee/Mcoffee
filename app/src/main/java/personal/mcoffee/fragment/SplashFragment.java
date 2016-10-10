package personal.mcoffee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import personal.mcoffee.R;
import personal.mcoffee.activity.MainActivity;
import personal.mcoffee.activity.SplashActivity;
import personal.mcoffee.mvp.contract.SplashContract;
import personal.mcoffee.mvp.model.SplashImg;
import personal.mcoffee.utils.ScreenUtils;

/**
 * Created by Mcoffee on 2016/10/10.
 */

public class SplashFragment extends Fragment implements SplashContract.View {
    @BindView(R.id.splash_iv)
    ImageView splashIv;
    @BindView(R.id.author_tv)
    TextView authorTv;

    private Animation splashAnimation;

    private Unbinder unbinder;

    SplashContract.Presenter splashPresenter;

    private int screenWidth;
    private int screenHeight;

    public static SplashFragment getInstance() {
        return new SplashFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        splashAnimation = AnimationUtils.loadAnimation(getActivity(),R.anim.splash);
        screenWidth = ScreenUtils.getScreenWidth(getActivity());
        screenHeight = ScreenUtils.getScreenHeight(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash,container,false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        splashAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashIv.setVisibility(View.GONE);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

       splashPresenter.fetchImage(screenWidth,screenHeight);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void setPresenter(SplashContract.Presenter presenter) {
        this.splashPresenter = presenter;
    }

    @Override
    public void showBackgroundImage(final SplashImg splashImg) {
        Glide.with(getActivity())
             .load(splashImg.imgUrl)
             .listener(new RequestListener<String, GlideDrawable>() {
                  @Override
                  public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                      return false;
                  }

                  @Override
                  public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                      splashIv.post(new Runnable() {
                          @Override
                          public void run() {
                              splashIv.startAnimation(splashAnimation);
                          }
                      });
                      return false;
                  }
              })
            .centerCrop()
            .into(splashIv);
    }

    @Override
    public void showAuthor(SplashImg splashImg) {
        authorTv.setText(splashImg.author);
    }
}
