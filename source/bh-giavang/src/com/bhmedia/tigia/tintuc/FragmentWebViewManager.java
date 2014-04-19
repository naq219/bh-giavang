package com.bhmedia.tigia.tintuc;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bhmedia.tigia.HomeActivity;
import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.utils.Defi;
import com.telpoo.frame.utils.Utils;

public class FragmentWebViewManager extends MyFragment implements OnClickListener {

	public static final String URL_LIST = "URL_LIST";
	public static final String POSITION_SHOW = "POSITION_SHOW";
	private View back, zoomin, zoomout;
	
	
	
	public static final FragmentWebViewManager newsInstance(ArrayList<String> urlS, int position) {

		FragmentWebViewManager fragmentWebViewManager = new FragmentWebViewManager();
		Bundle bundle = new Bundle(1);
		bundle.putStringArrayList("URL_LIST", urlS);
		bundle.putInt("POSITION_SHOW", position);
		fragmentWebViewManager.setArguments(bundle);
		return fragmentWebViewManager;
	}

	WebView webView;
	List<String> urlS;
	List<FragmentWebview> fragmentWebviews;
	// String html;
	String data;
	NgPageViewAdapter ngPageViewAdapter;
	ViewPager viewPager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_webview_content, container, false);

		v.findViewById(R.id.back).setOnClickListener(this);
		v.findViewById(R.id.zoomin).setOnClickListener(this);
		v.findViewById(R.id.zoomout).setOnClickListener(this);

		//
		//
		urlS = new ArrayList<String>();
		urlS = getArguments().getStringArrayList(URL_LIST);
		fragmentWebviews = new ArrayList<FragmentWebview>();
		fragmentWebviews = getFragments();
		//
		int positionShow = getArguments().getInt(POSITION_SHOW);
		//
		//
		FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
		ngPageViewAdapter = new NgPageViewAdapter(fragmentManager, fragmentWebviews);
		//
		//
		viewPager = (ViewPager) v.findViewById(R.id.pager_view);
		viewPager.setAdapter(ngPageViewAdapter);
		viewPager.setCurrentItem(positionShow);
		return v;
	}

	private List<FragmentWebview> getFragments() {
		// //
		List<FragmentWebview> fList = new ArrayList<FragmentWebview>();
		// //
		for (String i : urlS) {
			fList.add(FragmentWebview.newsInstance(i));
		}
		//
		// fList.add(FragmentWebview.newsInstance(url.get(1)));
		// fList.add(FragmentWebview.newsInstance(url.get(2)));
		// //
		return fList;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:

			HomeActivity.getInstance().popFragments();

			break;
			
		case R.id.zoomin:
			String size= Utils.getStringSPR(Defi.spr.TEXT_ZOOM, getActivity());
			
			try {
				int msize= Integer.parseInt(size);
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, ""+(msize+1), getActivity());
				
			} catch (Exception e) {
				Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, "0", getActivity());
			}
			
			break;

		default:
			break;
		}

	}
}
