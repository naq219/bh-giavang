package com.bhmedia.tigia.tintuc;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bhmedia.tigia.HomeActivity;
import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.utils.Defi;
import com.bhmedia.tigia.utils.TabId;
import com.telpoo.frame.utils.Utils;

@SuppressLint("ValidFragment")
public class FragmentWebViewManager extends MyFragment implements OnClickListener {

//	public static final String URL_LIST = "URL_LIST";
//	public static final String POSITION_SHOW = "POSITION_SHOW";
	//private View back, zoomin, zoomout;
	WebView webView;
	List<String> urlS;
	List<FragmentWebview> fragmentWebviews;
	// String html;
	String data;
	NgPageViewAdapter ngPageViewAdapter;
	ViewPager viewPager;
	int position;
	public FragmentWebViewManager(ArrayList<String> urlS, int position) {

		this.urlS=urlS;
		this.position=position;
		
	}	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if(container == null)
		{
			Log.d("test container", "container is null");
		}
		View v = inflater.inflate(R.layout.pager_webview_container, container, false);

		v.findViewById(R.id.back).setOnClickListener(this);
		v.findViewById(R.id.zoomin).setOnClickListener(this);
		v.findViewById(R.id.zoomout).setOnClickListener(this);

		
		fragmentWebviews = new ArrayList<FragmentWebview>();
		fragmentWebviews = getFragments();
		//
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		// set adapter for pager webview
		ngPageViewAdapter = new NgPageViewAdapter(fragmentManager, fragmentWebviews);
		//
		//			
		viewPager = (ViewPager) v.findViewById(R.id.pager_container_web);
		viewPager.setAdapter(ngPageViewAdapter);		
		//if(  != null);
		viewPager.setCurrentItem(position);			
		//viewPager.setSaveEnabled(false);
		//  return my view
		return v;
	}

	private List<FragmentWebview> getFragments() {
		// //
		List<FragmentWebview> fList = new ArrayList<FragmentWebview>();
		// //
		for (String i : urlS) {
			
			fList.add(new FragmentWebview(i));
		}		
		return fList;
	}

	@Override
		public boolean onBackPressed() {
			showToast("back");
			FragmentManager fragmentManager = this.getChildFragmentManager();
			fragmentManager.popBackStack();
			return true;
			// chac ko duoc vi em ko add vao home
		}

	@Override
	public void onClick(View v) {
		FragmentWebview  fragmentWebview = fragmentWebviews.get(viewPager.getCurrentItem());
		Messenger messenger = new Messenger(fragmentWebview.handler);
		
		switch (v.getId()) {
		case R.id.back:

			//HomeActivity.getInstance().popFragments();
			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
			fragmentManager.popBackStack();

			break;
			
		case R.id.zoomin:
			String size= Utils.getStringSPR(Defi.spr.TEXT_ZOOM, getActivity());
			Log.d("test click", "click zoom");
			try {
				int msize= Integer.parseInt(size);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(msize+1), getActivity());
				//((WebView)ngPageViewAdapter.getItem(viewPager.getCurrentItem()).getView()).getSettings().setTextSize(TextSize.LARGEST);
				Message msg = new Message();
				msg.what = 1;
				msg.obj = msize + 1;
				messenger.send(msg);				
			} catch (Exception e) {
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, "0", getActivity());
			}
			
			break;
			
			
		case R.id.zoomout:
			String size1= Utils.getStringSPR(Defi.spr.TEXT_ZOOM, getActivity());
			
			try {
				int msize= Integer.parseInt(size1);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(msize-1), getActivity());
				Message msg = new Message();
				msg.what = 1;
				msg.obj = msize - 1;
				messenger.send(msg);
				
			} catch (Exception e) {
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, FragmentWebview.NORMAL+"", getActivity());
			}
			
			break;

		default:
			break;
		}
	}
}
