package com.bhmedia.tigia;

import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

import com.bhmedia.tigia.db.DbSupport;
import com.bhmedia.tigia.db.TableDb;
import com.bhmedia.tigia.utils.Defi;
import com.bhmedia.tigia.utils.Utils1;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.MapBuilder;
import com.google.analytics.tracking.android.Tracker;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.telpoo.bhlib.BHAnalytics;
//import com.telpoo.bhlib.BHAnalytics;
import com.telpoo.bhlib.ads.BHGA;
import com.telpoo.frame.net.BaseNetSupportBeta;
import com.telpoo.frame.net.NetConfig;
import com.telpoo.frame.utils.ApplicationUtils;
import com.telpoo.frame.utils.DeviceSupport;
import com.telpoo.frame.utils.FileSupport;
import com.telpoo.frame.utils.RootSupport;

public class HomeActivity extends TabActivity {
	Handler handler;
	static HomeActivity me;

	RelativeLayout rootadView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		me = HomeActivity.this;
		super.onCreate(savedInstanceState);

		NetConfig netConfig = new NetConfig.Builder().numberRetry(3).contentType("text/plain; charset=utf-8")
				.userAgent("Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/32.0.1700.102 Chrome/32.0.1700.102 Safari/537.36").build();
		BaseNetSupportBeta.getInstance().init(netConfig);

		rootadView = (RelativeLayout) findViewById(R.id.rootadView);
		ImageLoader.getInstance().init(Utils1.imageLoaderCf(0, HomeActivity.this));
		setupAds();
		setupDb();
		setupTracking();
		ApplicationUtils.getKeyHash(HomeActivity.this);

	}

	private void setupDb() {
		DbSupport.init(TableDb.tables, TableDb.keys, HomeActivity.this, TableDb.pathDbName, 2);

	}

	private void setupAds() {
		// BHAds.getInstance().init(HomeActivity.this, rootadView, Defi.appname,
		// Defi.appcat, Defi.adposition, Defi.adUnitId);
		// BHAds.getInstance().onCreate();

	}

	public static HomeActivity getInstance() {
		return me;
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	@Override
	protected void onDestroy() {
		// BHAds.getInstance().onDestroy();
		super.onDestroy();
	}

	private void setupTracking() {
		setTrackingId(Defi.GOOGLE_ANALYTIC_ID);
		BHAnalytics.postDimension(Defi.GOOGLE_ANALYTIC_ID, Defi.USER_TYPE, HomeActivity.this);
		
//		Tracker mTracker = GoogleAnalytics.getInstance(HomeActivity.this).getTracker(Defi.GOOGLE_ANALYTIC_ID);
//		int type = DeviceSupport.getNetworkInfo(HomeActivity.this);
//		String ns = "";
//		if (type == ConnectivityManager.TYPE_WIFI)
//			ns = "wifi";
//		if (type == ConnectivityManager.TYPE_MOBILE)
//			ns = "3g";
//		mTracker.set(Fields.customDimension(BHGA.Dimension.CARRIER), DeviceSupport.getCarrier(getBaseContext()));
//		mTracker.set(Fields.customDimension(BHGA.Dimension.EM), "" + DeviceSupport.getMacAdd(getBaseContext()));
//		mTracker.set(Fields.customDimension(BHGA.Dimension.JB), "" + RootSupport.isDeviceRooted());
//		mTracker.set(Fields.customDimension(BHGA.Dimension.NS), ns);
//		mTracker.set(Fields.customDimension(BHGA.Dimension.PLATFORM), "" + Build.VERSION.RELEASE);
//		mTracker.set(Fields.customDimension(BHGA.Dimension.USER_TYPE), Defi.USER_TYPE);
//		mTracker.send(MapBuilder.createAppView().build());

	}

}
