package com.bhmedia.tigia.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

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

}
