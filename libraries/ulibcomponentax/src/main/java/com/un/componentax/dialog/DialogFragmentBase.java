package com.un.componentax.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.un.componentax.R;
import com.un.utila.display.SizeUnitUtil;
import com.un.utila.viewhelp.ViewClipUtil;
import com.un.utilax.livedata.LiveDataSetDirect;

/**
 * 基础dialog,提供定制接口
 */
public abstract class DialogFragmentBase extends AppCompatDialogFragment {


	//对话框状态
	/**
	 * 未显示
	 */
	private final int STATE_NONE = 0,
	/**
	 * 准备显示
	 */
	STATE_READY = 1,
	/**
	 * 显示中
	 */
	STATE_SHOW = 2;

	/**
	 * 当前状态
	 */
	private int state = STATE_NONE;

	private ItfDialogWindowSetting dialogWindowSetting;

	private ItfDialogSetting dialogSetting;

	private ItfDialogViewSetting dialogViewSetting;

	private LiveDataSetDirect<Object> liveDataIfShow = new LiveDataSetDirect<>();

	private FragmentActivity hostActivity;

	private final Object syncObj = new Object();

	public void setDialogWindowSetting(ItfDialogWindowSetting dialogWindowSetting) {
		this.dialogWindowSetting = dialogWindowSetting;
	}

	public void setDialogSetting(ItfDialogSetting dialogSetting) {
		this.dialogSetting = dialogSetting;
	}

	public void setDialogViewSetting(ItfDialogViewSetting dialogViewSetting) {
		this.dialogViewSetting = dialogViewSetting;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Dialog dialog = getDialog();
		if (dialog == null) {
			return;
		}

		if (dialogSetting != null) {
			dialogSetting.onDialogSetting(dialog);
		}

		Window window = dialog.getWindow();
		if (window == null) {
			return;
		}

		if (dialogWindowSetting != null) {
			WindowManager.LayoutParams params = window.getAttributes();
			params.gravity = dialogWindowSetting.getGravity();
			window.setAttributes(params);

			int width = dialogWindowSetting.getWidth();
			int height = dialogWindowSetting.getHeight();
			window.setLayout(width, height);
		}
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(getResId(), container, false);
		if (dialogViewSetting != null) {
			dialogViewSetting.onViewCreated(v);
		} else {
			ViewClipUtil.clipRoundRect(v, SizeUnitUtil.getPx(getContext(), 8));
		}
		return v;
	}

	protected abstract int getResId();

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		Dialog dialog = getDialog();
		if (dialog == null) {
			return;
		}

		Window window = dialog.getWindow();
		if (window == null) {
			return;
		}

		window.requestFeature(Window.FEATURE_NO_TITLE);
		window.setBackgroundDrawableResource(android.R.color.transparent);

		Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
			@Override
			public boolean queueIdle() {
				onAfterUIInited();
				return false;
			}
		});
	}

	protected void onAfterUIInited() {

	}

	Observer<Object> dialogObserver = new Observer<Object>() {
		@Override
		public void onChanged(Object obj) {
			if (obj == null) {
				return;
			}
			synchronized (syncObj) {
				if (state == STATE_READY) {
					FragmentTransaction ft = hostActivity.getSupportFragmentManager().beginTransaction();
					ft.setCustomAnimations(R.anim.translate_enter_from_left, R.anim.translate_exit_to_left);

					show(ft, "");

					state = STATE_SHOW;
				}
			}
		}
	};

	public void show(final FragmentActivity fragmentActivity) {
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				synchronized (syncObj) {
					if (state == STATE_NONE) {
						liveDataIfShow.setValue(null);
						hostActivity = fragmentActivity;
						liveDataIfShow.observe(fragmentActivity, dialogObserver);
						liveDataIfShow.postValue(new Object());

						state = STATE_READY;
					}
				}
			}
		});
	}

	public void close() {
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				synchronized (syncObj) {
					if (state == STATE_SHOW) {
						liveDataIfShow.removeObservers(hostActivity);
						hostActivity = null;
						dismissAllowingStateLoss();

						state = STATE_NONE;
					} else if (state == STATE_READY) {
						liveDataIfShow.removeObservers(hostActivity);
						hostActivity = null;

						state = STATE_NONE;
					}
				}
			}
		});
	}
}
