package com.un.componentax.animation;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.animation.TranslateAnimation;

import androidx.core.graphics.drawable.DrawableCompat;

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

	/**
	 * 生成帧动画
	 *
	 * @param context
	 * @param frames
	 * @param duration
	 *
	 * @return
	 */
	public static Drawable getAnimationDrawable(Context context, int[] frames, int duration) {
		if (frames == null) {
			return null;
		}
		Drawable animDrawable = null;
		Resources res = context.getResources();
		int resLength = frames.length;
		if (resLength == 1) {
			animDrawable = res.getDrawable(frames[0]);
		} else {
			if (duration > 0) {
				Drawable[] drawables = new Drawable[resLength];
				for (int i = 0; i < resLength; i++) {
					drawables[i] = res.getDrawable(frames[i]);
				}
				animDrawable = AnimationUtil.getFrameAnimation(drawables, duration);
			} else {
				animDrawable = res.getDrawable(frames[0]);
			}
		}
		return animDrawable;
	}

	public static Drawable getAnimationDrawable(Context context, int[] frame, int[] colors, int duration) {
		if (colors == null) {
			return null;
		}
		Drawable animDrawable = null;
		Resources res = context.getResources();
		int resLength = colors.length;
		if (resLength == 1) {
			animDrawable = res.getDrawable(frame[0]);
		} else {
			if (duration > 0) {
				Drawable[] drawables = new Drawable[resLength];
				for (int i = 0; i < resLength; i++) {
					int color = colors[i];
					Drawable tempDrawable = res.getDrawable(frame[i]).mutate();
					DrawableCompat.setTintList(tempDrawable, ColorStateList.valueOf(color));
					drawables[i] = tempDrawable;
				}
				animDrawable = AnimationUtil.getFrameAnimation(drawables, duration);
			} else {
				animDrawable = res.getDrawable(frame[0]);
			}
		}
		return animDrawable;
	}

	/**
	 * 帧动画
	 *
	 * @return
	 */
	public static AnimationDrawable getFrameAnimation(Drawable[] drawables, int duration) {
		AnimationDrawable animationDrawable = new AnimationDrawable();
		animationDrawable.setOneShot(true);
		for (Drawable drawable : drawables) {
			animationDrawable.addFrame(drawable, duration);
		}
		return animationDrawable;
	}
}
