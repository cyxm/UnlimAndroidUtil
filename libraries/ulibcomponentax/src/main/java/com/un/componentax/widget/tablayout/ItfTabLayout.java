package com.un.componentax.widget.tablayout;

import android.view.View;

/**
 * Tab页接口
 */
public interface ItfTabLayout {

	void addTab(View v, Object tag);

	View getTab(Object tag);

	void setOnTabChange(ItfOnTabLayoutChange itf);

	void setOnTabChangeNotify(ItfOnTabLayoutChange itf);

	void setTabViewAdapter(ItfTabViewStateAdapter itf);

	void changeTab(Object tag);

	void changeTabWithoutCallback(Object tag);
}
