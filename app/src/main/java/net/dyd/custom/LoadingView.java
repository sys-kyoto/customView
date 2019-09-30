package net.dyd.custom;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

public class LoadingView extends LinearLayout {
    // 上面的形状
    private  ShapeView mShapeView;
    //中间的阴影
    private View mShadowView;

    AnimatorSet animatorSet;

    private int duration = 1000;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView(context);
    }

    /**
     * 性能优化
     */
    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(INVISIBLE);
        
        if (visibility == GONE){
            stopAnimator();
        }
         
    }

    private void stopAnimator() {
        animatorSet.removeAllListeners();
        animatorSet.cancel();
    }

    /**
     * 初始化加载
     *
     * @param context
     */
    @SuppressLint("ObjectAnimatorBinding")
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.ui_loading_view, this, false);
        addView(view);
        mShapeView = view.findViewById(R.id.shape_view);
        mShadowView = view.findViewById(R.id.shadow);

        post(new Runnable() {
            @Override
            public void run() {
                //在onresume之后执行
                startFallAnimator();
            }
        });

    }


    /**
     * 下落动画
     */
    @SuppressLint("ObjectAnimatorBinding")
    public void startFallAnimator() {
        //平移
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(mShapeView,"translationY",0,dip2Px(80),0);
        //旋转
        ObjectAnimator  rotationAnimator = ObjectAnimator.ofFloat(mShapeView, "rotation", 0f, 180f, 0f);
        //缩放
        ObjectAnimator ScaleX = ObjectAnimator.ofFloat(mShadowView,"scaleX",1f,0.3f,1f);

        //然后通过AnimatorSet把几种动画组合起来
        animatorSet = new AnimatorSet();
        animatorSet.playTogether(translationAnimator,rotationAnimator,ScaleX);
        animatorSet.setDuration(duration);
//        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Log.i("TAHG",":1111");
                mShapeView.exChange();
                animation.start();
            }
        });
    }

    private int dip2Px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());
    }


}
