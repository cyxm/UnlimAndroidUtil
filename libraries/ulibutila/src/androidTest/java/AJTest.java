import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class AJTest {
	@Test
	public void useAppContext() throws Exception {
		// Context of the app under test.
		Context appContext = ApplicationProvider.getApplicationContext();

		assertEquals("com.un.utila.test", appContext.getPackageName());
	}
}
