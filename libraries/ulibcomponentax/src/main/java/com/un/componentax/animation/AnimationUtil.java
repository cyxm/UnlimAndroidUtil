package com.un.componentax.animation;

import android.view.animation.TranslateAnimation;

public class AnimationUtil {

	public static TranslateAnimation translateX(int fromX, int toX) {
		TranslateAnimation animation = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_SELF, fromX,
				TranslateAnimation.RELATIVE_TO_SELF, toX,
				TranslateAnimation.RELATIVE_TO_SELF, 0,
				TranslateAnimation.RELATIVE_TO_SELF, 0
		);
		return animation;
	}

	public static TranslateAnimation translateY(int fromY, int toY) {
		TranslateAnimation animation = new TranslateAnimation(
				TranslateAnimation.RELATIVE_TO_SELF, 0,
				TranslateAnimation.RELATIVE_TO_SELF, 0,
				TranslateAnimation.RELATIVE_TO_SELF, fromY,
				TranslateAnimation.RELATIVE_TO_SELF, toY
		);
		return animation;
	}

	public static TranslateAnimation translateEnterFromLeft() {
		return translateX(-1, 0);
	}

	public static TranslateAnimation translateExitToLeft() {
		return translateX(0, -1);
	}

}
