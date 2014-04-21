package com.bhmedia.tigia;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bhmedia.tigia.object.GiaVangOj;
import com.bhmedia.tigia.object.TiGiaOj;
import com.bhmedia.tigia.utils.Defi;
import com.telpoo.frame.net.BaseNetSupportBeta;
import com.telpoo.frame.object.BaseObject;

public class NetSupport {

	public static ArrayList<BaseObject> getGiaVang(String date) throws JSONException {
		String urlGiaVang = getUrlGiaVang(date);
		String res = BaseNetSupportBeta.getInstance().method_GET(urlGiaVang);

		JSONObject root = new JSONObject(res);
		JSONObject apiJoj = root.getJSONObject("api"); // danh sach cac khu vuc
		Iterator<?> keyLocation = apiJoj.keys();
		ArrayList<BaseObject> arrData = new ArrayList<BaseObject>();

		while (keyLocation.hasNext()) {
			String key = (String) keyLocation.next();

			JSONArray ojJar = apiJoj.getJSONArray(key);

			
			
			for (int i = 0; i < ojJar.length(); i++) {
				BaseObject oj = new BaseObject();
				oj.set(GiaVangOj.GROUP, key);
				JSONArray ojJar2 = ojJar.getJSONArray(i);
				JSONObject ojNow = ojJar2.getJSONObject(0);
				JSONObject ojYesterday = ojJar2.getJSONObject(1);

				for (String keyJson1 : GiaVangOj.keysJson1) { // lay du lieu hom
																// nay
					if (ojNow.has(keyJson1)) {
						oj.set(keyJson1, ojNow.getString(keyJson1));
					}
				}
				oj.set(GiaVangOj.GROUP_NAME, oj.get(GiaVangOj.LOCATION_NAME));

				// lay du lieu ngay truoc do

				if (ojYesterday.has("created"))
					oj.set(GiaVangOj.CREATEDOLD, ojYesterday.getString("created"));

				if (ojYesterday.has("buy"))
					oj.set(GiaVangOj.BUYOLD, ojYesterday.getString("buy"));

				if (ojYesterday.has("sale"))
					oj.set(GiaVangOj.SALEOLD, ojYesterday.getString("sale"));

				arrData.add(oj);
			}

		}

		// danh sach cac ngan hang the gioi
		JSONObject apiGroupJoj = root.getJSONObject("api_group");

		Iterator<?> keyLocation1 = apiGroupJoj.keys();
		ArrayList<BaseObject> arrData1 = new ArrayList<BaseObject>();

		while (keyLocation1.hasNext()) {
			BaseObject oj = new BaseObject();
			String key = (String) keyLocation1.next();
			oj.set(GiaVangOj.GROUP, "999999");

			JSONArray ojJarg2 = apiGroupJoj.getJSONArray(key);
			JSONObject ojgNow = ojJarg2.getJSONObject(0);
			JSONObject ojgYesterday = ojJarg2.getJSONObject(1);

			for (String keyJson1 : GiaVangOj.keysJson1) { // lay du lieu hom nay
				if (ojgNow.has(keyJson1)) {
					oj.set(keyJson1, ojgNow.getString(keyJson1));
				}
			}
			oj.set(GiaVangOj.GROUP_NAME, "Các ngân hàng và thế giới");
			oj.set(GiaVangOj.GOLD_NAME, oj.get(GiaVangOj.LOCATION_NAME));
			// lay du lieu ngay truoc do
			if (ojgYesterday.has("created"))
				oj.set(GiaVangOj.CREATEDOLD, ojgYesterday.getString("created"));

			if (ojgYesterday.has("buy"))
				oj.set(GiaVangOj.BUYOLD, ojgYesterday.getString("buy"));

			if (ojgYesterday.has("sale"))
				oj.set(GiaVangOj.SALEOLD, ojgYesterday.getString("sale"));

			arrData.add(oj);

		}

		return arrData;
	}

	private static String getUrlGiaVang(String date) {
		return Defi.url.API_GIA_VANG + date;
	}

	public static ArrayList<BaseObject> getTiGia(String date) throws JSONException {
		String url = getUrlTiGia(date);

		String res = BaseNetSupportBeta.getInstance().method_GET(url);

		ArrayList<BaseObject> ojs = new ArrayList<BaseObject>();

		JSONArray rootJar = new JSONArray(res);
		for (int i = 0; i < rootJar.length(); i++) {
			JSONObject joj = rootJar.getJSONObject(i);

			JSONObject dataJoj = joj.getJSONObject("data");
			Iterator<?> keydata = dataJoj.keys();

			while (keydata.hasNext()) {
				String key = (String) keydata.next();

				JSONObject ojEnd = dataJoj.getJSONObject(key);

				BaseObject oj = new BaseObject();
				oj.set(TiGiaOj.BANKID, joj.getString(TiGiaOj.BANKID));
				oj.set(TiGiaOj.BANKNAME, joj.getString(TiGiaOj.BANKNAME));

				for (String keyOj : TiGiaOj.keys) {
					if (ojEnd.has(keyOj))
						oj.set(keyOj, ojEnd.getString(keyOj));
				}

				ojs.add(oj);

			}

		}
		return ojs;

	}

	private static String getUrlTiGia(String date) {
		return Defi.url.TI_GIA+date;
	}

}
