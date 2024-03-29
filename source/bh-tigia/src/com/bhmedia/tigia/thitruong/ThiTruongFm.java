package com.bhmedia.tigia.thitruong;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

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

import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigiagoc.R;
import com.bhmedia.tigia.utils.Defi;
import com.telpoo.frame.utils.ViewUtils;

@SuppressLint("ValidFragment")
public class ThiTruongFm extends MyFragment {
	WebView wv;
	ImageView btn_top;
	String url;

	public ThiTruongFm(String url) {
		this.url = url;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.thitruong, container, false);
		return v;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		super.onViewCreated(view, savedInstanceState);

		wv = (WebView) view.findViewById(R.id.wv);
		btn_top = (ImageView) view.findViewById(R.id.btn_top);
	}

	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


		wv.loadUrl(url);
		wv.getSettings().setJavaScriptEnabled(true);
		wv.setWebViewClient(new WebViewClient() {
			
			
			
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);

				wv.loadUrl(failingUrl);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				view.loadUrl("javascript:var divs = document.getElementsByTagName('div');  divs[divs.length-3].style.display = 'none';");
			}
		});

		((TextView) getView().findViewById(R.id.tv_title)).setText(getString(R.string.gia_ca_thi_truong));
		btn_top.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				wv.loadUrl(url);

			}
		});
		btn_top.setImageResource(R.drawable.refresh);

	}

}
