package com.un.componentax.act;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.util.SparseArray;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;


/**
 * 基础Activity,实现
 */
public abstract class ActivityBase extends AppCompatActivity {

	protected FragmentActivity mSelfActivity;

	SparseArray<IOnActivityResult> listOnActivityResult = new SparseArray<>();

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

	//使用回调处理Activity返回
	public void addActivityResult(int requestCode, IOnActivityResult onActivityResult) {
		listOnActivityResult.append(requestCode, onActivityResult);
	}

	public void removeActivityResult(int requestCode) {
		listOnActivityResult.delete(requestCode);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		IOnActivityResult onActivityResult = listOnActivityResult.get(requestCode);
		if (onActivityResult != null) {
			onActivityResult.onActivityResult(requestCode, resultCode, data);
			removeActivityResult(requestCode);
		}
	}

	/**
	 * 暂时解决WebView在android5上崩溃的问题
	 * 下一版androidx.appcompat会解决此问题
	 *
	 * @return
	 */
	@Override
	public AssetManager getAssets() {
		return getResources().getAssets();
	}
}
