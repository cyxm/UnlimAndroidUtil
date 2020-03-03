package com.un.componentax.widget;

import android.view.View;
import android.view.ViewGroup;

public interface ItfViewAdapter {

	View genView(ViewGroup parent, Object data);

	void adapter(View v, Object data);

	ViewGroup[] genGroup(ViewGroup parent);
}
