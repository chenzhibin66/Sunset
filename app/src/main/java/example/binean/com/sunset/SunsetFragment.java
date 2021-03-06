package example.binean.com.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

public class SunsetFragment extends Fragment {

    private View mSceneView;
    private View mSunView;
    private View mSkyView;

    private int mBuleSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    public static SunsetFragment newInstance(){
        return new SunsetFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_sunset,container,false);

        mSceneView=view;
        mSunView=view.findViewById(R.id.sun);
        mSkyView=view.findViewById(R.id.sky);
        Resources resources=getResources();
        mBuleSkyColor=resources.getColor(R.color.blue_sky);
        mSunsetSkyColor=resources.getColor(R.color.sunset_sky);
        mNightSkyColor=resources.getColor(R.color.night_sky);

        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnimation();
            }
        });
        return view;
    }

    private void startAnimation(){
        float sunYStart=mSunView.getTop();
        float sunYEnd=mSkyView.getHeight();

        ObjectAnimator heightAnimator=ObjectAnimator.ofFloat(mSunView,"y",sunYStart,sunYEnd).setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());


        ObjectAnimator sunsetSkyAnimator=ObjectAnimator
                .ofInt(mSkyView,"backgroundColor",mBuleSkyColor,mSunsetSkyColor).setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());

        ObjectAnimator nightSkyAnimator=ObjectAnimator
                .ofInt(mSkyView,"backgroundColor",mSunsetSkyColor,mNightSkyColor).setDuration(1500);
        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(heightAnimator).with(sunsetSkyAnimator).before(nightSkyAnimator);
        animatorSet.start();
    }
}
