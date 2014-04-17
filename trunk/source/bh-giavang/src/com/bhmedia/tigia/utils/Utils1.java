package com.bhmedia.tigia.utils;

import java.util.ArrayList;
import java.util.Calendar;

import com.bhmedia.tigia.task.TaskNetWork;
import com.bhmedia.tigia.task.TaskType;
import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.model.ModelListener;
import com.telpoo.frame.model.TaskListener;
import com.telpoo.frame.utils.TimeUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.DateUtils;

public class Utils1 {

	public static void rating(Activity activity) {
		String id = activity.getApplicationContext().getPackageName();
		try {
			Uri marketUri = Uri.parse("market://details?id=" + id);
			Intent marketIntent = new Intent(Intent.ACTION_VIEW)
					.setData(marketUri);
			activity.startActivity(marketIntent);
		} catch (Exception e) {
			Intent marketIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id="
							+ id));
			activity.startActivity(marketIntent);
		}
	}

	public static void shareEmail(Activity activity, String path) {
		Uri uri1 = Uri.parse("file://" + path);
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("image/png");

		
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
				"Kết quả xổ số");
		//emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Defi.nameShare);
		emailIntent.putExtra(Intent.EXTRA_STREAM, uri1);
		Intent a = Intent.createChooser(emailIntent, "Sharing options");
		activity.startActivity(a);

	}
	
	public static void runTaskGiaVang(Calendar cal,BaseModel taskListener,Context context){
		String date= TimeUtils.cal2String(cal, Defi.FORMAT_DATE);
		ArrayList<String> list=new ArrayList<String>();
		list.add(date);
		TaskNetWork netWork=new TaskNetWork(taskListener, TaskType.TASK_GET_GIAVANG	, list, context);
		taskListener.exeTask(null, netWork);
	}

}
