package com.un.componentax.act;

import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;


/**
 * 基础Activity
 */
public abstract class ActivityBase extends AppCompatActivity {

	protected FragmentActivity mSelfActivity;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mSelfActivity = this;

		onThemeSetup();

		onWindowSetup();

		Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
			@Override
			public boolean queueIdle() {
				onAfterUIInited();
				return false;
			}
		});
	}

	/**
	 * UI初始化成功后的回调
	 */
	protected void onAfterUIInited() {
	}

	/**
	 * 设置UI主题
	 */
	protected void onThemeSetup() {

	}

	/**
	 * 设置Window属性
	 */
	protected void onWindowSetup() {

	}
}
