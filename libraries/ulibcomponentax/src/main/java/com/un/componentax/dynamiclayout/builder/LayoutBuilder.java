package com.un.componentax.dynamiclayout.builder;

import android.view.View;
import android.view.ViewGroup;

import com.un.componentax.dynamiclayout.itf.ItfViewGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生成编组的LinearLayout
 */
public class LayoutBuilder {

	/**
	 * View渲染相关数据
	 */
	List<List<Object>> viewDataList = null;

	/**
	 * View生成器
	 */
	ItfViewGenerator viewGenerator = null;

	/**
	 * 数据到ID的映射
	 */
	Map<Object, Integer> viewDataMap = new HashMap<>();

	/**
	 * 在调用{@link #add(Object, int)}之前必须调用此函数
	 * 每次调用表示一组重新开始
	 *
	 * @return
	 */
	public LayoutBuilder beginGroup() {
		if (viewDataList == null) {
			viewDataList = new ArrayList<>();
		}
		List<Object> childList = new ArrayList<>();
		viewDataList.add(childList);
		return this;
	}

	/**
	 * 必须在{@link #beginGroup()}之后调用
	 *
	 * @param child
	 *
	 * @return
	 */
	public LayoutBuilder add(Object child, int viewId) {
		if (viewDataList == null || viewDataList.isEmpty()) {
			return this;
		}
		//
		List<Object> childList = viewDataList.get(viewDataList.size() - 1);
		childList.add(child);
		//
		viewDataMap.put(child, viewId);
		return this;
	}

	/**
	 * View的生成接口
	 *
	 * @param pViewGenerator
	 *
	 * @return
	 */
	public LayoutBuilder setViweGenerator(ItfViewGenerator pViewGenerator) {
		this.viewGenerator = pViewGenerator;
		return this;
	}

	/**
	 * @param parent
	 */
	public void buildMerge(ViewGroup parent) {
		//检查参数
		if (parent == null || viewDataList == null || viewGenerator == null) {
			return;
		}
		//
		for (List<Object> childList : viewDataList) {
			//0层组布局
			View[] viewLv0 = viewGenerator.genView(0, null);
			if (viewLv0 != null && viewLv0.length == 2 && viewLv0[1] instanceof ViewGroup) {
				int count = childList.size();
				for (int i = 0; i < count; i++) {
					Object data = childList.get(i);
					//1层item布局
					View[] viewLv1 = viewGenerator.genView(1, data);
					if (viewLv1 == null || viewLv1.length <= 0) {
						continue;
					}
					Integer id = viewDataMap.get(data);
					if (id != null) {
						viewLv1[0].setId(id);
					}
					((ViewGroup) viewLv0[1]).addView(viewLv1[0]);

					//分隔线
					if (i != count - 1) {
						View viewSep = viewGenerator.genDivider();
						((ViewGroup) viewLv0[1]).addView(viewSep);
					}
				}
				parent.addView(viewLv0[0]);
			}
		}
	}
}
