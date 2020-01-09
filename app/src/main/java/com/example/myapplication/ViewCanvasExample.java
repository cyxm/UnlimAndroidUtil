package com.example.myapplication;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class ViewCanvasExample extends View {
	public ViewCanvasExample(Context context) {
		super(context);
		init();
	}

	public ViewCanvasExample(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ViewCanvasExample(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public ViewCanvasExample(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init();
	}

	private void init() {
		setLayerType(LAYER_TYPE_SOFTWARE, null);
	}

	Paint paint = new Paint();

	@Override
	protected void onDraw(Canvas c) {
		//绘制纯色
		c.drawColor(Color.LTGRAY);
		//		c.drawARGB(255, 255, 0, 0);
		//		c.drawRGB(255, 255, 0);


		//绘制弧线区域
		//		paint.setColor(Color.RED);
		//		RectF rect = new RectF(0, 0, 100, 100);
		//		c.drawArc(rect, //弧线所使用的矩形区域大小
		//				0, //开始角度
		//				270, //扫过的角度
		//				false, //是否使用中心
		//				paint);

		//绘制圆形
		//		c.drawCircle(0, 0, 100, paint);

		//		RectF outRect = new RectF(0, 0, 100, 100);
		//		RectF inRect = new RectF(0, 0, 50, 50);
		//绘制圆环
		//		c.drawDoubleRoundRect(outRect, 0, 0, inRect, 0, 0, paint);

		//绘制线
		//		paint.setColor(Color.RED);
		//		paint.setStrokeWidth(20);
		//		c.drawLine(0, 0, 200, 200, paint);
		//		c.drawLines(new float[]{34, 50, 88, 99}, paint);

		//绘制椭圆
		//		paint.setColor(Color.RED);
		//		RectF outRect = new RectF(0, 0, 200, 100);
		//		c.drawOval(outRect, paint);

		//绘制点
		//		paint.setColor(Color.RED);
		//		paint.setStrokeWidth(5);
		//		c.drawPoint(100, 100, paint);

		//绘制矩形
		//		paint.setColor(Color.RED);
		//		RectF outRect = new RectF(0, 0, 200, 100);
		//		c.drawRect(outRect, paint);

		//绘制圆角矩形
		paint.setColor(Color.RED);
		//		paint.setStrokeWidth(2);
		//		paint.setAntiAlias(true);
		//		paint.setTextSize(50);
		//		paint.setShadowLayer(5, 15, 20, Color.GREEN);
		paint.setMaskFilter(new BlurMaskFilter(20, BlurMaskFilter.Blur.OUTER));
		RectF outRect = new RectF(0, 0, 200, 100);
		c.drawRoundRect(outRect, 20, 20, paint);
		c.drawCircle(200, 200, 50, paint);

		c.clipRect(outRect);

		//绘制文字
		//		paint.setColor(Color.RED);
		//		paint.setTextSize(40);
		//		paint.setShadowLayer(20, 20, 20, Color.BLUE);
		//		c.drawText("AndroidText", 0, 20, paint);

		//		c.drawPath();

		//		paint.setColor(Color.RED);
		//		c.drawPaint(paint);
	}
}
