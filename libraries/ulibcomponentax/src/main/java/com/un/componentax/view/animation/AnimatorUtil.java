package com.un.componentax.view.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

public class AnimatorUtil {

	public static Animator getScaleAnimation(View v) {
		ObjectAnimator animator = ObjectAnimator.ofFloat(v, "scaleX", 1, 0);
		return animator;
	}

	public static Animator getTranslateXAnimation(View v) {
		ObjectAnimator animator = ObjectAnimator.ofFloat(v, "translationX", 0, v.getWidth());
		return animator;
	}
}
