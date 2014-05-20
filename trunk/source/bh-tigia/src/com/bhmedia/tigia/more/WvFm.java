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
import com.bhmedia.tigiagoc.R;
import com.bhmedia.tigia.utils.Defi;

@SuppressLint("ValidFragment")
public class WvFm extends MyFragment {
	public WebView wv;
	ImageView btn_close;
	String url;
	String title;
	Integer resource;
	int countLoadErr=0;
	public WvFm(String url, String title) {
		this.url = url;
		this.title = title;
		countLoadErr=0;
	}

	public WvFm(String url, String title,int resource) {
		this.url = url;
		this.title = title;
		this.resource=resource;
		
		trackScreen(title);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.wv, container, false);
		if(resource!=null) v = inflater.inflate(resource, container, false);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);

		wv = (WebView) view.findViewById(R.id.wv);
		wv.getSettings().setJavaScriptEnabled(true);
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
				countLoadErr++;
				if(countLoadErr<4)
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
				view.loadUrl("javascript:var divs = document.getElementsByTagName('div');  divs[divs.length-3].style.display = 'none';");
				
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
