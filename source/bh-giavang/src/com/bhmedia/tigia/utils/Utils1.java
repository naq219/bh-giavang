package com.bhmedia.tigia.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bhmedia.tigia.R;
import com.bhmedia.tigia.object.GiaVangOj;
import com.bhmedia.tigia.task.TaskNetWork;
import com.bhmedia.tigia.task.TaskType;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.model.BaseModel;
import com.telpoo.frame.net.BaseNetSupportBeta;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.ui.BaseFragment;
import com.telpoo.frame.utils.TimeUtils;

public class Utils1 {

	public static void rating(Activity activity) {
		String id = activity.getApplicationContext().getPackageName();
		try {
			Uri marketUri = Uri.parse("market://details?id=" + id);
			Intent marketIntent = new Intent(Intent.ACTION_VIEW).setData(marketUri);
			activity.startActivity(marketIntent);
		} catch (Exception e) {
			Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + id));
			activity.startActivity(marketIntent);
		}
	}

	public static void shareEmail(Activity activity, String path) {
		Uri uri1 = Uri.parse("file://" + path);
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		emailIntent.setType("image/png");

		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Kết quả xổ số");
		// emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
		// Defi.nameShare);
		emailIntent.putExtra(Intent.EXTRA_STREAM, uri1);
		Intent a = Intent.createChooser(emailIntent, "Sharing options");
		activity.startActivity(a);

	}

	public static void runTaskGiaVang(Calendar cal, BaseModel taskListener, Activity context, BaseFragment fm) {

		String date = TimeUtils.cal2String(cal, Defi.FORMAT_DATE);
		fm.showProgressDialog(context);
		ArrayList<String> list = new ArrayList<String>();
		list.add(date);
		TaskNetWork netWork = new TaskNetWork(taskListener, TaskType.TASK_GET_GIAVANG, list, context);
		taskListener.exeTask(null, netWork);
	}

	public static void runTaskTiGia(Calendar cal, BaseModel taskListener, Activity context, BaseFragment fm) {

		String date = TimeUtils.cal2String(cal, Defi.FORMAT_DATE);
		fm.showProgressDialog(context);
		ArrayList<String> list = new ArrayList<String>();
		list.add(date);
		TaskNetWork netWork = new TaskNetWork(taskListener, TaskType.TASK_GET_TIGIA, list, context);
		taskListener.exeTask(null, netWork);
	}

	static String curKey;
	static BaseObject ojCur = new BaseObject();
	static ArrayList<BaseObject> ojs;

	public static void setspinnerHeader(final Context ct, final TextView location_name, final TextView gold_name, final HashMap<String, ArrayList<BaseObject>> map,
			final Idelegate idelegate) {
		Set<String> titles = map.keySet();
		final String[] mtitle = new String[titles.size()];
		final String[] mkeys = titles.toArray(new String[0]);

		int dem = 0;
		for (String string : titles) {
			ArrayList<BaseObject> ojs = map.get(string);
			if (ojs.size() > 0) {
				mtitle[dem] = ojs.get(0).get(GiaVangOj.GROUP_NAME);
			}

			dem++;

		}

		curKey = mkeys[0];
		ojs = map.get(curKey);
		final ArrayList<String> goldNames = new ArrayList<String>();
		for (BaseObject baseObject : ojs) {
			goldNames.add(baseObject.get(GiaVangOj.GOLD_NAME));
		}

		ojCur = ojs.get(0);

		gold_name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				dialogSpinner(gold_name, ct, "", goldNames.toArray(new String[0]), new Idelegate() {

					@Override
					public void callBack(Object value, int where) {
						int key = (Integer) value;

						ojCur = ojs.get(key);
						idelegate.callBack(ojCur, Defi.whereIdelegate.UTILS1_SPINNERHEAD);

					}
				}, 2, true);
			}
		});

		location_name.setText(mtitle[0]);
		gold_name.setText(ojCur.get(GiaVangOj.GOLD_NAME));
		idelegate.callBack(ojCur, Defi.whereIdelegate.UTILS1_SPINNERHEAD);

		location_name.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialogSpinner(location_name, ct, "", mtitle, new Idelegate() {

					@Override
					public void callBack(Object value, int where) {
						int key = (Integer) value;

						curKey = mkeys[key];
						ojs = new ArrayList<BaseObject>();

						ojs = map.get(curKey);
						ojCur = ojs.get(0);
						gold_name.setText(ojCur.get(GiaVangOj.GOLD_NAME));

						idelegate.callBack(ojCur, Defi.whereIdelegate.UTILS1_SPINNERHEAD);
						goldNames.clear();
						for (BaseObject baseObject : ojs) {
							goldNames.add(baseObject.get(GiaVangOj.GOLD_NAME));
						}
					}
				}, 1, true);
			}
		});

	}

	public static void dialogSpinner(final TextView tv, Context ct, String title, final String[] items, final Idelegate delegate, final int where) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ct);
		builder.setTitle(title);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				tv.setText("" + items[item]);
				delegate.callBack(items[item], where);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static void dialogSpinner(final TextView tv, Context ct, String title, final String[] items, final Idelegate delegate, final int where, final boolean isReturnPosition) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ct);
		builder.setTitle(title);
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				tv.setText("" + items[item]);

				if (isReturnPosition)
					delegate.callBack(item, where);
				else
					delegate.callBack(items[item], where);
			}
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public static String double2String(double vl) {
	
		Locale locale = new Locale("vi_VN");
	//	NumberFormat f = NumberFormat.getCurrencyInstance(locale);
		NumberFormat f =new DecimalFormat("#,###,###,###.##");
		String a = f.format(vl);
//		a = a.substring(0, a.length() - 1);
//		if (a.indexOf(",00") ==a.length()-4) {
//			a = a.substring(0, a.indexOf(",00"));
//		}
		return a;
		//return vl+"";

	}

	public static String double2String(String vl) {

		try {
			Double mvl = Double.parseDouble(vl);
			String a = double2String(mvl);
			

			return a;
		} catch (Exception e) {
			return vl;
		}

	}

}
