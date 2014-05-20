package com.bhmedia.tigia;

import java.util.Locale;

import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Locale myLocale = new Locale("vi");
		Resources res = getResources();
		DisplayMetrics dm = res.getDisplayMetrics();
		Configuration conf = res.getConfiguration();
		conf.locale = myLocale;
		res.updateConfiguration(conf, dm);

	}
}
