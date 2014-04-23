package com.bhmedia.tigia.tintuc;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.utils.Defi;
import com.telpoo.frame.utils.Utils;

@SuppressLint({ "NewApi", "ValidFragment" })
public class FragmentWebview extends MyFragment implements NgViewPageFragmentLife {
	//
	WebView ngWebView;
	ProgressDialog progressDialog;
	String data;
	String html;
	int textSize;
	public static final int SMALLEST = 50;
	public static final int SMALLER= 75;
	public static final int NORMAL = 100;
	public static final int LARGER = 150;
	public static final int LARGEST = 200;
	boolean isHB = Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	//
	public Handler handler = new Handler(new Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			if( msg.what == 1 )
			{
				setTextSize((Integer) msg.obj);
				return true;
			}
			return false;
		}
	});
	
	
	
	public FragmentWebview() {
		
	}
	public FragmentWebview(String html) {
		this.html=html;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		
		View rootView = inflater.inflate(R.layout.web_view, container, false);
		ngWebView = (WebView) rootView.findViewById(R.id.webview_news);
		ngWebView.getSettings().setJavaScriptEnabled(false);
		ngWebView.getSettings().setBuiltInZoomControls(true);
		try {
			textSize = Integer.parseInt(Utils.getStringSPR(Defi.spr.TEXT_ZOOM, getActivity()));
			Log.d("textsize", textSize + "");

		} catch (Exception e) {
			// TODO: handle exception
		}
		return rootView;
	}
	
	@Override
	public void onPauseFragment() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onResumeFragment() {
		// TODO Auto-generated method stub
		Log.d("teststatus", "dang resume "+ html);		
		
		if(isHB)
		{
			String size = Utils.getStringSPR(Defi.spr.TEXT_ZOOM, getActivity());
			int msize = 0;
			try {
				msize = Integer.parseInt(size);
				Log.d("test size", "msize : " +msize +"");

			} catch (Exception e) {
				// TODO: handle exception
			}
			
			ngWebView.getSettings().setTextZoom(msize);
			Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(msize), getActivity());
		}
		else
		{
			String size = Utils.getStringSPR(Defi.spr.TEXT_ZOOM, getActivity());
			int msize = 0;
			try {
				msize = Integer.parseInt(size);
				Log.d("test size", "msize : " +msize +"");

			} catch (Exception e) {
				// TODO: handle exception
			}
			setTextSize(msize);
		}
		
	}	
	
	@Override
	public void onResume() {
		super.onResume();		
		Log.d("teststatus", "dang resume "+ html);
		
		
		
		ngWebView.setWebViewClient(new WebViewClient());
		if(isHB)
		{
			String size = Utils.getStringSPR(Defi.spr.TEXT_ZOOM, getActivity());
			int msize = 0;
			try {
				msize = Integer.parseInt(size);
				Log.d("test size", "msize : " +msize +"");

			} catch (Exception e) {
				// TODO: handle exception
			}
			
			ngWebView.getSettings().setTextZoom(msize);
			Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(msize), getActivity());
		}
		else
		{
			String size = Utils.getStringSPR(Defi.spr.TEXT_ZOOM, getActivity());
			int msize = 0;
			try {
				msize = Integer.parseInt(size);
				Log.d("test size", "msize : " +msize +"");

			} catch (Exception e) {
				// TODO: handle exception
			}
			setTextSize(msize);
		}
		ngWebView.loadUrl(html);	
	}	
	
	// settextsize
	private void setTextSize(int size) {
		
		String nsize = Utils.getStringSPR(Defi.spr.TEXT_ZOOM, getActivity());
		int msize = 0;
		try {
			msize = Integer.parseInt(nsize);
			Log.d("test size", "msize : " +msize +"");
		} catch (Exception e) {
			// TODO: handle exception
		}
		Log.d("text size", msize+"");

		if (isHB) {
			if( size > 0 )//zoom in
			{
				int temp = msize + 25;
				if( temp <= 200 )
				{
					ngWebView.getSettings().setTextZoom(temp);
					Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(temp), getActivity());
				}
				else
				{
					showToast("Max size");
				}
			}
			else
				if( size < 0)//zoom out
				{
					int temp = msize - 25;
				
					if( temp >= 50  )
					{
						ngWebView.getSettings().setTextZoom(temp);
						Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(temp), getActivity());
					}
					else
					{
						showToast("Min size");
					}
				}
		} else {
//			if (size < 25)
//				ngWebView.getSettings().setTextSize(TextSize.SMALLEST);
//			else if (size < 50)
//				ngWebView.getSettings().setTextSize(TextSize.SMALLER);
//			else if (size < 75)
//				ngWebView.getSettings().setTextSize(TextSize.LARGER);
//			else
//				ngWebView.getSettings().setTextSize(TextSize.LARGEST);
//
//			if (45 < size && size < 55)
//				ngWebView.getSettings().setTextSize(TextSize.NORMAL);
			//
			switch (size) {
			//SMALLEST			
			case SMALLEST - 1:
				ngWebView.getSettings().setTextSize(TextSize.SMALLEST);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(SMALLEST), getActivity());
				showToast("Min size text");
				break;
			case SMALLEST :
				ngWebView.getSettings().setTextSize(TextSize.SMALLEST);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(SMALLEST), getActivity());
				break;
			case SMALLEST + 1:
				ngWebView.getSettings().setTextSize(TextSize.SMALLER);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(SMALLER), getActivity());
				break;
			//SMALLER
			case SMALLER -1 :
				ngWebView.getSettings().setTextSize(TextSize.SMALLEST);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(SMALLEST), getActivity());
				break;
			case SMALLER:
				ngWebView.getSettings().setTextSize(TextSize.SMALLER);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(SMALLER), getActivity());
				break;
			case SMALLER + 1:
				ngWebView.getSettings().setTextSize(TextSize.NORMAL);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(NORMAL), getActivity());
				break;
			case NORMAL - 1:
				ngWebView.getSettings().setTextSize(TextSize.SMALLER);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(SMALLER), getActivity());
				break;
			case NORMAL:
				ngWebView.getSettings().setTextSize(TextSize.NORMAL);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(NORMAL), getActivity());
				break;
			case NORMAL + 1:
				ngWebView.getSettings().setTextSize(TextSize.LARGER);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(LARGER), getActivity());
				break;			
			case LARGER - 1:
				ngWebView.getSettings().setTextSize(TextSize.NORMAL);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(NORMAL), getActivity());
				break;
			case LARGER:
				ngWebView.getSettings().setTextSize(TextSize.LARGER);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(LARGER), getActivity());
				break;	
			case LARGER + 1:
				ngWebView.getSettings().setTextSize(TextSize.LARGEST);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(LARGEST), getActivity());
				break;
			case LARGEST - 1:
				ngWebView.getSettings().setTextSize(TextSize.LARGER);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(LARGER), getActivity());
				break;
			case LARGEST:
				ngWebView.getSettings().setTextSize(TextSize.LARGEST);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(LARGEST), getActivity());
				break;
			case LARGEST + 1:
				ngWebView.getSettings().setTextSize(TextSize.LARGEST);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(LARGEST), getActivity());
				showToast("Max size text");
				break;

			default:
				break;
			}

			
		}
	}
	

}
