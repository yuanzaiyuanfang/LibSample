package com.yzyfdf.library.base

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

/**
 * @author sjj , 2019/5/31 16:14
 * //todo
 */
interface RvAnimator {

    fun getAnims(view: View): Array<Animator>

    class SlideInRightAnimation : RvAnimator {
        override fun getAnims(view: View): Array<Animator> {
            return arrayOf(
                    ObjectAnimator.ofFloat(view, "translationX", view.width.toFloat(), 0F)
            )
        }
    }

    class ScaleInAnimation(private val scale: Float = 0.5F) : RvAnimator {
        override fun getAnims(view: View): Array<Animator> {
            return arrayOf(
                    ObjectAnimator.ofFloat(view, "scaleX", scale, 1f),
                    ObjectAnimator.ofFloat(view, "scaleY", scale, 1f)
            )
        }
    }

    class AlphaInAnimation(private val alpha: Float = 0.5F) : RvAnimator {
        override fun getAnims(view: View): Array<Animator> {
            return arrayOf(ObjectAnimator.ofFloat(view, "alpha", alpha, 1f))
        }
    }

}

