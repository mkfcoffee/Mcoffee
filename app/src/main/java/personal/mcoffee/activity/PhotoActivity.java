package personal.mcoffee.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.transitionseverywhere.TransitionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import personal.mcoffee.R;
import personal.mcoffee.utils.Log;

/**
 * Created by Mcoffee.
 */

public class PhotoActivity extends AppCompatActivity {

    public static final String SHARED_ELEMENT_PHOTO = "shared_element_photo";
    public static final String PHOTO_URL = "photo_url";
    public static final String PHOTO_THUMBNAIL_SIZE = "photo_thumbnail_size";

    @BindView(R.id.photo)
    ImageView photoIv;

    private String url;
    private int[] thumbanailSizes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra(PHOTO_URL);
        thumbanailSizes = getIntent().getIntArrayExtra(PHOTO_THUMBNAIL_SIZE);
        if (addTransitionListener()) {
            loadThumbnailImage();
            ViewCompat.setTransitionName(photoIv, SHARED_ELEMENT_PHOTO);
        } else {
            loadPhoto();
        }
    }

    private boolean addTransitionListener() {
        if (Build.VERSION.SDK_INT >= 21) {
            Transition transition = getWindow().getSharedElementEnterTransition();
            if (null != transition) {
                transition.addListener(new Transition.TransitionListener() {
                    @Override
                    public void onTransitionStart(Transition transition) {

                    }

                    @Override
                    public void onTransitionEnd(Transition transition) {
                        transition.removeListener(this);
                        loadPhoto();
                    }

                    @Override
                    public void onTransitionCancel(Transition transition) {
                        transition.removeListener(this);
                    }

                    @Override
                    public void onTransitionPause(Transition transition) {

                    }

                    @Override
                    public void onTransitionResume(Transition transition) {

                    }
                });
                return true;
            }
        } else if (Build.VERSION.SDK_INT < 21) {
            com.transitionseverywhere.Transition transition = TransitionManager.getDefaultTransition();
            if (null != transition) {
                transition.addListener(new com.transitionseverywhere.Transition.TransitionListener() {
                    @Override
                    public void onTransitionStart(com.transitionseverywhere.Transition transition) {

                    }

                    @Override
                    public void onTransitionEnd(com.transitionseverywhere.Transition transition) {
                        transition.removeListener(this);
                        loadPhoto();
                    }

                    @Override
                    public void onTransitionCancel(com.transitionseverywhere.Transition transition) {
                        transition.removeListener(this);
                    }

                    @Override
                    public void onTransitionPause(com.transitionseverywhere.Transition transition) {

                    }

                    @Override
                    public void onTransitionResume(com.transitionseverywhere.Transition transition) {

                    }
                });
                return true;
            }
        }
        return false;
    }


    private void loadPhoto() {
        Glide.with(this)
                .load(url)
                .dontAnimate()
                .fitCenter()
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        photoIv.setImageDrawable(resource);
                    }
                });
    }

    private void loadThumbnailImage() {
        Glide.with(this)
                .load(url)
                .override(thumbanailSizes[0], thumbanailSizes[1])
                .priority(Priority.IMMEDIATE)
                .fitCenter()
                .dontAnimate()
                .into(photoIv);
    }

    @Override
    public void onBackPressed() {
        ActivityCompat.finishAfterTransition(this);
        super.onBackPressed();
    }
}
