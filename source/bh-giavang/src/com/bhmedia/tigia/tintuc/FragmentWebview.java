package com.bhmedia.tigia.tintuc;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.utils.Defi;
import com.telpoo.frame.utils.Utils;

@SuppressLint({ "NewApi", "ValidFragment" })
public class FragmentWebview extends MyFragment {
	public static final String INTRO_HTML = "INTRO_HTML";
	WebView ngWebView;
	ProgressDialog progressDialog;
	String data;
	String html;
	
	public FragmentWebview() {
		
	}
	public FragmentWebview(String html) {
		this.html=html;
	}
//	public static final FragmentWebview newsInstance(String html) {
//
//		FragmentWebview fragmentWebview = new FragmentWebview();
//		Bundle bundle = new Bundle(1);
//		bundle.putString(INTRO_HTML, html);
//		fragmentWebview.setArguments(bundle);
//		return fragmentWebview;
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rootView = inflater.inflate(R.layout.web_view, container, false);
		ngWebView = (WebView) rootView.findViewById(R.id.webView1);
		ngWebView.getSettings().setJavaScriptEnabled(true);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//showToast(Utils.getStringSPR(Defi.spr.TEXT_ZOOM, getActivity()));
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		String size = Utils.getStringSPR(Defi.spr.TEXT_ZOOM, getActivity());
		int msize = 0;
		try {
			msize = Integer.parseInt(size);

		} catch (Exception e) {
			// TODO: handle exception
		}
		setTextSize(msize);
		ngWebView.setWebViewClient(new WebViewClient());
		//String html = getArguments().getString(INTRO_HTML);
		
		
		Log.d("test", "data : " + html);
		ngWebView.loadUrl(html);

	}

	public static void loadDataWv(WebView wv, String data) {
		String header = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		wv.loadDataWithBaseURL(null, header + data, "text/html", "charset=UTF-8", null);
	}

	private void setTextSize(int size) {
		boolean isHB = Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH;

		if (isHB) {
			ngWebView.getSettings().setTextZoom(50 + size);
		} else {
			if (size < 25)
				ngWebView.getSettings().setTextSize(TextSize.SMALLEST);
			else if (size < 50)
				ngWebView.getSettings().setTextSize(TextSize.SMALLER);
			else if (size < 75)
				ngWebView.getSettings().setTextSize(TextSize.LARGER);
			else
				ngWebView.getSettings().setTextSize(TextSize.LARGEST);

			if (45 < size && size < 55)
				ngWebView.getSettings().setTextSize(TextSize.NORMAL);
		}
	}

}
