package com.bhmedia.tigia;

import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;

import com.bhmedia.tigia.db.DbSupport;
import com.bhmedia.tigia.db.TableDb;
import com.bhmedia.tigia.utils.Defi;
import com.bhmedia.tigia.utils.Utils1;
import com.bhmedia.tigiagoc.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.telpoo.bhlib.BHAnalytics;
import com.telpoo.bhlib.ads.BHAds;
import com.telpoo.frame.net.BaseNetSupportBeta;
import com.telpoo.frame.net.NetConfig;
import com.telpoo.frame.utils.ApplicationUtils;

//import com.telpoo.bhlib.BHAnalytics;

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
		BHAds.getInstance().init(HomeActivity.this, rootadView, Defi.appname, Defi.appcat, Defi.adposition);
		BHAds.getInstance().onCreate();

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
		 BHAds.getInstance().onDestroy();
		super.onDestroy();
	}

	private void setupTracking() {
		setTrackingId(Defi.GOOGLE_ANALYTIC_ID);
		BHAnalytics.postDimension(Defi.GOOGLE_ANALYTIC_ID, Defi.USER_TYPE, HomeActivity.this);

	

	}

}
