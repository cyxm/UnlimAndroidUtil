package com.un.componentax.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

public class TextViewAutoFitSize extends AppCompatTextView {
	private Paint mTextPaint = new Paint();
	private float mTextSize;

	public TextViewAutoFitSize(Context context) {
		super(context);
	}

	public TextViewAutoFitSize(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		refitText(this.getText().toString(), this.getWidth());
	}

	@Override
	public void setAutoSizeTextTypeWithDefaults(int autoSizeTextType) {
		super.setAutoSizeTextTypeWithDefaults(autoSizeTextType);
	}

	private void refitText(String text, int textViewWidth) {
		if (text == null || textViewWidth <= 0) {
			return;
		}
		mTextPaint.set(this.getPaint());
		int availableTextViewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
		float[] charsWidthArr = new float[text.length()];
		Rect boundsRect = new Rect();
		mTextPaint.getTextBounds(text, 0, text.length(), boundsRect);
		int textWidth = boundsRect.width();
		mTextSize = getTextSize();
		while (textWidth > availableTextViewWidth) {
			if (mTextSize < 6) {
				break;
			}
			mTextSize -= 1;
			mTextPaint.setTextSize(mTextSize);
			textWidth = mTextPaint.getTextWidths(text, charsWidthArr);
		}
		this.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
	}
}
