package com.example.myapplication;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

public class ActivityTest extends FragmentActivity {

	LinearLayout vContainer;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.layout_test);

		vContainer = findViewById(R.id.vContainer);
		vContainer.addView(
				new ViewCanvasExample(getBaseContext()),
				new LinearLayout.LayoutParams(300, 300)
		);
	}
}
