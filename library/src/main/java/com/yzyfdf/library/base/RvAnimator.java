package com.yzyfdf.library.base;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * @author sjj , 2019/5/31 16:14
 * //todo
 */
public class RvAnimator {

    private static volatile RvAnimator sInstance;


    private RvAnimator() {
    }

    public static RvAnimator getInstance() {

        if (sInstance == null) {
            synchronized (RvAnimator.class) {
                if (sInstance == null) {
                    sInstance = new RvAnimator();
                }
            }
        }
        return sInstance;
    }

    public AnimatorSet getAnim(View view) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0.3f, 1f);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(view, "rotationX", 40, 0);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", 300, 0);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(alpha, rotation, translationY);
        set.setDuration(500);

        return set;
    }

}
