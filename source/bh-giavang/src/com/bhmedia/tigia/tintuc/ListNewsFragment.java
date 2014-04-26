package com.bhmedia.tigia.tintuc;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.task.TaskType;
import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;
import com.telpoo.frame.net.BaseNetSupportBeta;

public class ListNewsFragment extends MyFragment implements OnItemClickListener,TaskType{
	// ListView listViewNews;
	ArrayList<ObjectNews> objectNewsList;

	NewsAdapter newsAdapter;
	PullToRefreshListView listViewNews;
	ArrayList<String> urlList;
	ArrayList<String> urlListWeb;
	public  int numpage, numitem ;
	public int oldSizeUrlList;
	String urlHead = "http://app.vietbao.vn/v2.0/vbcat/";
	String urlTail = "/giavang-tygia.rss";
	String CHECK_LOG = "CHECK_LOG";
	View ngFooterListView;
	//
	//create view
	//
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//
		View rootView = inflater.inflate(R.layout.tintuc, container, false);	
			// find id
		boolean checkNet = BaseNetSupportBeta.isNetworkAvailable(getActivity());		
			listViewNews = (PullToRefreshListView) rootView.findViewById(R.id.lv);		
			if( checkNet )
			{	
				if( objectNewsList == null || objectNewsList.size() == 0 )
				{
					//listViewNews.
					objectNewsList = new ArrayList<ObjectNews>();
					urlList = new ArrayList<String>();
					urlListWeb = new ArrayList<String>();
			
					numpage = 0;
					String url = urlHead + numpage + urlTail;
					urlList.add(url);					
					NewsAsyntask asyntask = new NewsAsyntask(getModel(), NEWS_ASYNCTASK, urlList,
							getActivity());
					getModel().exeTask(null, asyntask);
					// turn on progressdialog
					showProgressDialog(getActivity());
					
				}
					newsAdapter = new NewsAdapter(getActivity(), R.layout.item_tintuc_list,
							objectNewsList);
					listViewNews.setAdapter(newsAdapter);
					
					listViewNews.setOnItemClickListener(this);
							
			}
			else
			{
				showToast("No connection Network");
			}
		return rootView;
	}

	boolean isLoading = true;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) { 
		super.onActivityCreated(savedInstanceState);
		

		listViewNews.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				if (totalItemCount - visibleItemCount < firstVisibleItem + 4) {
					// load more :D
					if ( !isLoading ) {
						ngFooterListView = ((LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tintuc_loadmore, null);
						listViewNews.addFooterView(ngFooterListView);	
						// thuc hien loadmore
						numpage= numpage+15; // = numpage + 1;
						String url = urlHead + numpage + urlTail;
						Log.d("testurl"," url:   "+ url);
						urlList.set(0, url);//get(0) = url;						
						NewsAsyntask asyntask = new NewsAsyntask(getModel(), TASKTYPE_LOADMORE, urlList,
								getActivity());    // khai bao tasktype ro rang. task chay ngam co the co ow tab khac, rat nguy hiem nu ko khai bao
						getModel().exeTask(null, asyntask);
						// show toast for user						
						//LayoutInflater layoutInflater = getActivity(
						isLoading= true; 
					}
				}

			}
		});
		listViewNews.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				if ( !isLoading ) {
					// thuc hien pullload
					objectNewsList = new ArrayList<ObjectNews>();
					urlList = new ArrayList<String>();
					urlListWeb = new ArrayList<String>();
			
					numpage = 0;
					String url = urlHead + numpage + urlTail;
					urlList.add(url);					
					NewsAsyntask asyntask = new NewsAsyntask(getModel(), NEWS_ASYNCTASK, urlList,
							getActivity());
					getModel().exeTask(null, asyntask);					
									
					isLoading= true; 
				}
			}
		});

	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		// TODO Auto-generated method stub
		super.onSuccess(taskType, list, msg);

		switch (taskType) {
		case NEWS_ASYNCTASK:
			isLoading=false;
			objectNewsList = (ArrayList<ObjectNews>) list;			
			// haha ga qua ^^			
			for (int i = 0; i < objectNewsList.size(); i++) {
				urlListWeb.add(objectNewsList.get(i).get(ObjectNews.URL_WEB));
				Log.d("test size state ", ""+i);
				
			}
			//add size 
			oldSizeUrlList = objectNewsList.size();
			for (int i = 0; i < urlListWeb.size()-1; i++) {
				
				Log.d("test url ", ""+urlListWeb.get(i));
				
			}
			newsAdapter.SetItems(objectNewsList);
			newsAdapter.notifyDataSetChanged();
			closeProgressDialog();
			listViewNews.onRefreshComplete();
			Log.d("test size 1 ", ""+newsAdapter.getCount());
			break;
//			
			// moi lan load phai cap nhat old size de xem xet
			//
		case TASKTYPE_LOADMORE:
			
			// them vao listview
			for(int i =0; i < list.size(); i++)
			{
				
				objectNewsList.add((ObjectNews) list.get(i));
			}
			//objectNewsList.addAll((Collection<? extends ObjectNews>) list);
			
			for (int i = oldSizeUrlList ; i < objectNewsList.size(); i++) {
				urlListWeb.add(objectNewsList.get(i).get(ObjectNews.URL_WEB));
			}
			oldSizeUrlList = objectNewsList.size();			
			newsAdapter.Adds((List<ObjectNews>) list);
			//remove footer
			listViewNews.removeFooterView(ngFooterListView);// them item vao chu ko phai set moi , 
			newsAdapter.notifyDataSetChanged();
			Log.d("test size", ""+newsAdapter.getCount());
			
			isLoading=false;
			break;
		default:
			break;
		}
	}

	@Override
	public void onFail(int taskType, String msg) {
		// TODO Auto-generated method stub
		super.onFail(taskType, msg);
		switch (taskType) {
		case NEWS_ASYNCTASK: // kieu j the nay @@ :D
			showToast("Loading failded");
			break;
		case TASKTYPE_LOADMORE:
			isLoading=false;
			showToast("Loading more failded");
			break;		
		default:
			break;
		}
		showToast("Loading failded");
	}	

	public void switchFragment(Fragment fragment) {
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.realtabcontent, fragment).addToBackStack(null)
				.commit();		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switchFragment(new FragmentWebViewManager(urlListWeb, position-1));		
		
	}
	ArrayList<String> getAllUrlList()
	{
		return urlListWeb;
	}
	//
	// i miss you
	//

}
