package com.un.componentax.dynamiclayout.itf;

import android.view.View;

/**
 * 通用的动态生成View的接口
 */
public interface ItfViewGenerator {

	/**
	 * 生成不同层级的View
	 *
	 * @param level
	 * 		从0开始计算
	 *
	 * @return
	 */
	View[] genView(int level, Object viewData);

	/**
	 * 生成分隔线
	 *
	 * @return
	 */
	View genDivider();
}
