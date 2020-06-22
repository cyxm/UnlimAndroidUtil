package com.un.componentax.component;

import android.content.Intent;
import android.os.Build;
import android.provider.MediaStore;

import com.un.componentax.act.ActivityBase;
import com.un.componentax.act.IOnActivityResult;

public class ComponentSysUtil {

	public static void goGallery(ActivityBase activityBase, IOnActivityResult onActivityResult) {
		Intent intent = new Intent();
		if (Build.VERSION.SDK_INT < 19) {
			intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
		} else {
			intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
		}

		activityBase.addActivityResult(0, onActivityResult);
		activityBase.startActivityForResult(intent, 0);
	}

}
