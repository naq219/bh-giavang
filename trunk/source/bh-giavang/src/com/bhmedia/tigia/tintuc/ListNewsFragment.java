package com.bhmedia.tigia.tintuc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.task.TaskType;

public class ListNewsFragment extends MyFragment implements OnItemClickListener,TaskType {

	// ListView listViewNews;
	ArrayList<ObjectNews> objectNewsList;

	NewsAdapter newsAdapter;
	ListView listViewNews;
	ArrayList<String> urlList;
	ArrayList<String> urlListWeb;
	public static int numpage = 0;
	public int oldSizeUrlList;
	String urlHead = "http://app.vietbao.vn/v2.0/vbcat/";
	String urlTail = "/giavang-tygia.rss";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		View rootView = inflater.inflate(R.layout.tintuc, container, false);
		// find id
		listViewNews = (ListView) rootView.findViewById(R.id.lv);
		objectNewsList = new ArrayList<ObjectNews>();
		urlList = new ArrayList<String>();
		urlListWeb = new ArrayList<String>();
		String url = urlHead + numpage + urlTail;
		urlList.add(url);
		NewsAsyntask asyntask = new NewsAsyntask(getModel(), 1, urlList,
				getActivity());
		getModel().exeTask(null, asyntask);
		// turn on progressdialog
		showProgressDialog(getActivity());
		//
		// list view
		//
		newsAdapter = new NewsAdapter(getActivity(), R.layout.item_tintuc_list,
				objectNewsList);
		listViewNews.setAdapter(newsAdapter);
		listViewNews.setOnItemClickListener(this);

		// dang ra la ko code xu ly trong nay,, ở day chi khai bao cac view thoi
		//
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
					if (!isLoading) {
						// thuc hien loadmore
						numpage = numpage + 1;
						String url = urlHead + numpage + urlTail;
						urlList.add(url);
						NewsAsyntask asyntask = new NewsAsyntask(getModel(), TASKTYPE_LOADMORE, urlList,
								getActivity());
						getModel().exeTask(null, asyntask);
						showToast("load more");
						isLoading=true;
					}
				}

			}
		});

	}

	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		// TODO Auto-generated method stub
		super.onSuccess(taskType, list, msg);

		switch (taskType) {
		case NEWS_ASYNCTASK:
			isLoading=false;
			objectNewsList = (ArrayList<ObjectNews>) list;
			closeProgressDialog();
			// haha ga qua ^^
			for (int i = 0; i < objectNewsList.size(); i++) {
				urlListWeb.add(objectNewsList.get(i).get(ObjectNews.URL_WEB));
			}
			//add size 
			oldSizeUrlList = objectNewsList.size();
			newsAdapter.SetItems(objectNewsList);
			newsAdapter.notifyDataSetChanged();
			break;
//			
			// moi lan load phai cap nhat old size de xem xet
			//
		case TASKTYPE_LOADMORE:
			isLoading=false;
			// them vao listview
			objectNewsList.addAll((Collection<? extends ObjectNews>) list);
			for (int i = oldSizeUrlList ; i < objectNewsList.size(); i++) {
				urlListWeb.add(objectNewsList.get(i).get(ObjectNews.URL_WEB));
			}
			oldSizeUrlList = objectNewsList.size();
			newsAdapter.Adds((List<ObjectNews>) list);
			newsAdapter.notifyDataSetChanged();
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
		case 1:
			showToast("Loading failded");
			break;
		case TASKTYPE_LOADMORE:
			isLoading=false;
			break;
		default:
			break;
		}
		showToast("Loading failded");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("test", "destroy lsitview");
	}

	public void switchFragment(Fragment fragment) {
		FragmentManager fragmentManager = getActivity()
				.getSupportFragmentManager();
		fragmentManager.beginTransaction()
				.replace(R.id.realtabcontent, fragment).addToBackStack(null)
				.commit();
		// fragmentManager.beginTransaction().replace(arg0, arg1, arg2);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switchFragment(new FragmentWebViewManager(urlListWeb, position));
		
	}

}
