package com.bhmedia.tigia.tintuc;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.util.Log;

import com.bhmedia.tigia.task.TaskType;
import com.telpoo.frame.model.BaseTask;
import com.telpoo.frame.model.TaskListener;
import com.telpoo.frame.model.TaskParams;
import com.telpoo.frame.net.BaseNetSupportBeta;

public class NewsAsyntask extends BaseTask implements TaskType{
	
	ArrayList<String> urlWebList;
	

	public NewsAsyntask(TaskListener taskListener, int taskType,
			ArrayList<?> list, Context context) {
		super(taskListener, taskType, list, context);
		// TODO Auto-generated constructor stub
	}
	//
	@Override
	protected Boolean doInBackground(TaskParams... params) {
		// TODO Auto-generated method stub
		switch (taskType) {
		case NEWS_ASYNCTASK:
		{
			ArrayList<ObjectNews> arrayList = new ArrayList<ObjectNews>();
			Document document = null;
			//int page= params[0].getIntParams()[0];
			String url = (String) dataFromModel.get(0);
			String html = BaseNetSupportBeta.getInstance().method_GET(url);
			if( html.equals("") || (html == null ))
			{
				msg = "Mất kết nối";
				return TASK_FAILED;
			}
			else
			{
				document = Jsoup.parse(html);
				Element elementsItemList = document.select("array").get(0);
				Elements dictElements = elementsItemList.select("dict");
				for(Element item : dictElements)
				{
					String urlWeb = "", imgLink = "", title ="", info = "", time = "" ;
					//get 
					Elements StringS = item.select("String");
					//
					//--time
					time = StringS.get(0).text();
					//--title
					title = getInfo(StringS.get(1).text());
					//--thumb
					imgLink = StringS.get(2).text();
					if( imgLink.equals("") || imgLink == null)
					{
						imgLink = "";
					}
					//Log.d("test", "img : " +imgLink);
					//--urlweb mobile
					urlWeb = StringS.get(4).text();
					
					//--urlweb tablet
					//urlWebTab = StringS.get(5).text();
					//
					ObjectNews objectNews = new ObjectNews();
					//set values
					objectNews.set(ObjectNews.URL_WEB, urlWeb);
					objectNews.set(ObjectNews.URL_IMAGE, imgLink);
					objectNews.set(ObjectNews.TITTLE, title);
					objectNews.set(ObjectNews.TIME, time);
					objectNews.set(ObjectNews.INFO, info);
					//
					arrayList.add(objectNews);					
				}
				//return 
				dataReturn = arrayList;
				return TASK_DONE;
			}		
		}	
		//
		//------------load more------------
		//
		case TASKTYPE_LOADMORE:
		{
			ArrayList<ObjectNews> arrayList = new ArrayList<ObjectNews>();
			Document document = null;
			
			//int page= params[0].getIntParams()[0];
			String url = (String) dataFromModel.get(0);
			String html = BaseNetSupportBeta.getInstance().method_GET(url);
			Log.d("testurl", url);
			if( html.equals("") || (html == null ))
			{
				msg = "Mất kết nối";
				return TASK_FAILED;
			}
			else
				
			{
				document = Jsoup.parse(html);
				//Element elementsTemp = document.select("array").get;
				Element elementsItemList = document.select("array").get( 0 );
				Elements dictElements = elementsItemList.select("dict");
				for(Element item : dictElements)
				{
					
					String urlWeb = "", imgLink = "", title ="", info = "", time = "" ;
					//get 
					Elements StringS = item.select("String");
					//
					//--time
					time = StringS.get(0).text();
					//--title
					title = getInfo(StringS.get(1).text());
					//--thumb
					imgLink = StringS.get(2).text();
					if( imgLink.equals("") || imgLink == null)
					{
						imgLink = "";
					}
					//Log.d("test", "img : " +imgLink);
					//--urlweb mobile
					urlWeb = StringS.get(4).text();					
					//--urlweb tablet
					//urlWebTab = StringS.get(5).text();
					//
					ObjectNews objectNews = new ObjectNews();
					//set values
					objectNews.set(ObjectNews.URL_WEB, urlWeb);
					objectNews.set(ObjectNews.URL_IMAGE, imgLink);
					objectNews.set(ObjectNews.TITTLE, title);
					objectNews.set(ObjectNews.TIME, time);
					objectNews.set(ObjectNews.INFO, info);
					//
					arrayList.add(objectNews);					
				}
				//return 
				dataReturn = arrayList;
				return TASK_DONE;
			}		
		}
		default:
			break;
		}
		return super.doInBackground(params);
	}
	
	public String getInfo(String src)
	{
		String intro = "";
		//
		if( (src != null) || ( !src.equals("") ))
		{
			int start = src.indexOf("<\\![CDATA[")+ 10;
			int end = src.indexOf("]]>");
			if( start != -1 && end != -1 )
				intro = src.substring(start, end);			
		}
		if(!intro.equals(""))
		{
			Log.d("test", intro);
			return intro;
		}
		else
			return src;
		
	}

}
