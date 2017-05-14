package com.unicellular.simpletask.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by szc on 2017/3/21.
 *
 */

public class AnimatorUtils {
    public static AnimatorSet getFloatAddAnimatorSet(View view){
        int width=ScreenUtils.getScreenWidth(view.getContext());
        int height=ScreenUtils.getScreenHeight(view.getContext());

        AnimatorSet animatorSet=new AnimatorSet();

        ObjectAnimator moveXAnimator =ObjectAnimator.ofFloat(view,"X",width-view.getWidth()-20,width/2-view.getWidth()/2);
        moveXAnimator.setDuration(1000);


        ObjectAnimator rotationAnimator=ObjectAnimator.ofFloat(view,"rotation",360f,0f);
        rotationAnimator.setDuration(1000);

        ObjectAnimator moveYAnimator =ObjectAnimator.ofFloat(view,"Y",view.getTop(),view.getTop()-180);
        moveYAnimator.setDuration(1000);


        animatorSet.playTogether(moveXAnimator,rotationAnimator,moveYAnimator);

        return animatorSet;
    }
    public static AnimatorSet getFloatResetAnimatorSet(View view){
        int width=ScreenUtils.getScreenWidth(view.getContext());
        int height=ScreenUtils.getScreenHeight(view.getContext());

        AnimatorSet animatorSet=new AnimatorSet();

        ObjectAnimator moveXAnimator =ObjectAnimator.ofFloat(view,"X",width/2-view.getWidth()/2,width-view.getWidth()-20);
        moveXAnimator.setDuration(1000);


        ObjectAnimator rotationAnimator=ObjectAnimator.ofFloat(view,"rotation",0f,360f);
        rotationAnimator.setDuration(1000);

        ObjectAnimator moveYAnimator =ObjectAnimator.ofFloat(view,"Y",view.getTop(),view.getTop());
        moveYAnimator.setDuration(1000);


        animatorSet.playTogether(moveXAnimator,rotationAnimator,moveYAnimator);

        return animatorSet;
    }

    public static void floatBeatAnimatorSet(View view){
        TranslateAnimation down = new TranslateAnimation(0, 0, -80, 0);//位移动画，从button的上方300像素位置开始
        down.setFillAfter(true);
        down.setInterpolator(new BounceInterpolator());//弹跳动画,要其它效果的当然也可以设置为其它的值
        down.setDuration(2000);//持续时间
        view.startAnimation(down);
    }
}
