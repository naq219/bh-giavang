package com.bhmedia.tigia.more;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhmedia.tigia.HomeActivity;
import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.utils.Defi;

@SuppressLint("ValidFragment")
public class WvFm extends MyFragment {
	WebView wv;
	ImageView btn_close;
	String url;
	String title;

	public WvFm(String url, String title) {
		this.url = url;
		this.title = title;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.wv, container, false);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);

		wv = (WebView) view.findViewById(R.id.wv);
		btn_close = (ImageView) view.findViewById(R.id.btn_top);

		
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((TextView) getView().findViewById(R.id.tv_title)).setText(title);
		wv.loadUrl(url);
		wv.setWebViewClient(new WebViewClient() {
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);

				wv.loadUrl(failingUrl);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				showProgressDialog(getActivity());
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				closeProgressDialog();
			}
		});

		
		btn_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				HomeActivity.getInstance().popFragments();

			}
		});
		btn_close.setImageResource(R.drawable.close);

	}

}
