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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.un.componentax.R;
import com.un.utila.display.SizeUnitUtil;
import com.un.utila.viewhelp.ViewClipUtil;
import com.un.utilax.livedata.LiveDataSetDirect;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基础dialog,提供定制接口
 */
public abstract class DialogFragmentBase extends AppCompatDialogFragment {

	private ItfDialogWindowSetting dialogWindowSetting;

	private ItfDialogSetting dialogSetting;

	private ItfDialogViewSetting dialogViewSetting;

	private LiveDataSetDirect<Boolean> liveDataIfShow = new LiveDataSetDirect<>();

	private AtomicInteger showCount = new AtomicInteger(0);

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

	public void show(final FragmentActivity fragmentActivity) {
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				final int count = showCount.getAndIncrement();

				if (count == 0) {
					liveDataIfShow.observeForever(new Observer<Boolean>() {
						@Override
						public void onChanged(Boolean aBoolean) {
							if (aBoolean == null) {
								return;
							}
							if (aBoolean) {
								if (count == 0) {
									FragmentManager fm = fragmentActivity.getSupportFragmentManager();
									FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
									ft.setCustomAnimations(R.anim.translate_enter_from_left, R.anim.translate_exit_to_left);

									show(ft, "");
								}
							}
						}
					});
				}

				liveDataIfShow.postValue(true);
			}
		});
	}

	public void close() {
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				int count = showCount.decrementAndGet();
				if (count == 0) {
					dismissAllowingStateLoss();
				}
			}
		});
	}
}
