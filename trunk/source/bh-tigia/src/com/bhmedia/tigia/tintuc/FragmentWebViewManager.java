package com.bhmedia.tigia.tintuc;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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
import com.bhmedia.tigiagoc.R;
import com.bhmedia.tigia.utils.Defi;
import com.bhmedia.tigia.utils.TabId;
import com.telpoo.frame.utils.Utils;

@SuppressLint("ValidFragment")
public class FragmentWebViewManager extends MyFragment implements OnClickListener,OnPageChangeListener {


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
	//-------------------------------------------------------------------------------------
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
		viewPager.setCurrentItem(position);				
		viewPager.setOnPageChangeListener(this);
		return v;
	}

	

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	// xu li khi page dua vao hien thi se load onresume lai nham cap nhat
	// kich co chu cho webview
	@Override
	public void onPageSelected(int newPosition) {
		// TODO Auto-generated method stub
		NgViewPageFragmentLife fragmentLife =  (NgViewPageFragmentLife) ngPageViewAdapter.getItem(newPosition);	
		fragmentLife.onResumeFragment();
	}
	// xu ly click button
	@Override
	public void onClick(View v) {
		// kiem tra phien ban la bao nhieu
		boolean isHB = Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH;
		// 
		// goi lay fragment de quan li gui message
		FragmentWebview  fragmentWebview = fragmentWebviews.get(viewPager.getCurrentItem());
		Messenger messenger = new Messenger(fragmentWebview.handler);
		
		switch (v.getId()) {
		//quay ve listview
		case R.id.back:
			// lay fragment o dinh cua ngan xep backstack
			//HomeActivity.getInstance().popFragments();
			FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
			fragmentManager.popBackStack();
			break;
	//
	//bam zoom chu
	//---------------------------------------------------
			// lay size text da luu neu ko size luu co thi tra ve size la 100 tuc la size normal
			// gui mot msg den webview 1 la zoom in va -1 la zoom out voi api >= 4.0 
	//---------------------------------------------------
		case R.id.zoomin:
			if(isHB)// la co api lon hon 14
			{
		
				//String size= Utils.getStringSPR(Defi.spr.TEXT_ZOOM, getActivity());
				Log.d("test click", "click zoom");
				try {
					//int msize= Integer.parseInt(size);					
					Message msg = new Message();
					msg.what = 1;
					msg.obj =  1;
					messenger.send(msg);					
				} catch (Exception e) {
					Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, 100+"", getActivity());
				}		
			}
			else
			{
				String size= Utils.getStringSPR(Defi.spr.TEXT_ZOOM, getActivity());
				Log.d("test click", "click zoom");
				try {
					int msize= Integer.parseInt(size);					
					Message msg = new Message();
					msg.what = 1;
					msg.obj = msize + 1;
					messenger.send(msg);					
				} catch (Exception e) {
					Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, FragmentWebview.NORMAL+"", getActivity());
				}
			}
			
			break;
			
			
		case R.id.zoomout:
			if(isHB)
			{
				try {
					Message msg = new Message();
					msg.what = 1;
					msg.obj =  -1;
					messenger.send(msg);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
			}
			else
			{
				String size1= Utils.getStringSPR(Defi.spr.TEXT_ZOOM, getActivity());				
				try {
					int msize= Integer.parseInt(size1);				
					Message msg = new Message();
					msg.what = 1;
					msg.obj = msize - 1;
					messenger.send(msg);				
				} catch (Exception e) {
					Utils.saveStringSPR(Defi.spr.TEXT_ZOOM, FragmentWebview.NORMAL+"", getActivity());
				}
			}			
			break;
		default:
			break;
		}
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

	
}
