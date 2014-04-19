package com.bhmedia.tigia.tintuc;

import java.util.ArrayList;

import android.R.integer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.bhmedia.tigia.HomeActivity;
import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.utils.TabId;


public class ListNewsFragment extends MyFragment{
	
	//ListView listViewNews;
	ArrayList<ObjectNews> objectNewsList;
	NewsAdapter newsAdapter;
	ListView listViewNews;
	ArrayList<String> urlList;
	ArrayList<String> urlListWeb;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.news_listview, container, false);
		// find id
		listViewNews = (ListView) rootView.findViewById( R.id.listViewNews);
		objectNewsList = new ArrayList<ObjectNews>();
		urlList = new ArrayList<String>();
		urlListWeb = new ArrayList<String>();
		urlList.add("http://app.vietbao.vn/v2.0/vbcat/0/giavang-tygia.rss");
		NewsAsyntask asyntask = new NewsAsyntask(getModel(), 1, urlList, getActivity());
		getModel().exeTask(null, asyntask);
		//turn on progressdialog
		showProgressDialog(getActivity());
		//
		//list view
		//
		newsAdapter = new NewsAdapter(getActivity(), R.layout.item_news_listview, objectNewsList);
		listViewNews.setAdapter(newsAdapter);
		listViewNews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				
				//showToast(""+position);
				HomeActivity.getInstance().pushFragments(TabId.TINTUC, new FragmentWebViewManager(urlListWeb, position), true, null);
				//pushFragment(R.id.realtabcontent, FragmentWebViewManager.newsInstance(urlListWeb, position));
				
//				FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//				fragmentManager.beginTransaction().replace( R.id.realtabcontent, FragmentWebViewManager.newsInstance(urlListWeb, position)).commit();
				
			}
		});
		//
		return rootView;
	}
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		// TODO Auto-generated method stub
		super.onSuccess(taskType, list, msg);
		objectNewsList = (ArrayList<ObjectNews>)list;
		closeProgressDialog();
		// haha ga qua ^^
		for(int i =0; i < objectNewsList.size(); i++)
		{
			urlListWeb.add(objectNewsList.get(i).get(ObjectNews.URL_WEB));
		}
		newsAdapter.SetItems(objectNewsList);
		newsAdapter.notifyDataSetChanged();
	}
	@Override
	public void onFail(int taskType, String msg) {
		// TODO Auto-generated method stub
		super.onFail(taskType, msg);
		switch (taskType) {
		case 1:
			showToast("Loading failded");
			break;

		default:
			break;
		}
		showToast("Loading failded");
	}
	public void switchFragment(Fragment fragment)
	{
		FragmentManager  fragmentManager = getActivity().getSupportFragmentManager();
		//fragmentManager.beginTransaction().replace(arg0, arg1, arg2);
	}
	

}
