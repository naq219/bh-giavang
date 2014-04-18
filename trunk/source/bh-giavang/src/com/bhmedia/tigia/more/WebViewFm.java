package com.bhmedia.tigia.more;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;

@SuppressLint("ValidFragment")
public class WebViewFm extends MyFragment {
	WebView wv;
	String url;
	public WebViewFm(String url) {
		this.url=url;
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.wv, container,false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		wv= (WebView) getView().findViewById(R.id.wv);
		
		wv.setWebViewClient(new WebViewClient(){
			
		});
		
		
	}
}
