package com.bhmedia.tigia.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.os.SystemClock;
import android.util.Pair;

import com.bhmedia.tigia.object.GiaVangOj;
import com.bhmedia.tigia.object.TiGiaOj;
import com.bhmedia.tigia.object.TiGiaOj;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;

public class DataLvTiGia {
	public static final String TAG = DataLvTiGia.class.getSimpleName();

	public static List<Pair<String, List<BaseObject>>> getAllData(ArrayList<BaseObject> ojs, int type) {
		List<Pair<String, List<BaseObject>>> res = new ArrayList<Pair<String, List<BaseObject>>>();
		HashMap<String, ArrayList<BaseObject>> mapedData = grouping(ojs, type);
		for (int i = 0; i < mapedData.size(); i++) {
			res.add(getOneSection(i, mapedData));
		}

		return res;
	}

	public static List<BaseObject> getFlattenedData(HashMap<String, ArrayList<BaseObject>> mapedData) {
		List<BaseObject> res = new ArrayList<BaseObject>();

		for (int i = 0; i < mapedData.size(); i++) {
			res.addAll(getOneSection(i, mapedData).second);
		}

		return res;
	}

	public static Pair<Boolean, List<BaseObject>> getRows(int page, HashMap<String, ArrayList<BaseObject>> mapedData) {
		List<BaseObject> flattenedData = getFlattenedData(mapedData);
		if (page == 1) {
			return new Pair<Boolean, List<BaseObject>>(true, flattenedData.subList(0, 5));
		} else {
			SystemClock.sleep(2000); // simulate loading
			return new Pair<Boolean, List<BaseObject>>(page * 5 < flattenedData.size(), flattenedData.subList((page - 1) * 5, Math.min(page * 5, flattenedData.size())));
		}
	}

	public static Pair<String, List<BaseObject>> getOneSection(int index, HashMap<String, ArrayList<BaseObject>> mapedData) {
		Set<String> titles = mapedData.keySet();
		String[] mtitle = titles.toArray(new String[0]);
		String key = mtitle[index];

		ArrayList<BaseObject> ojs = mapedData.get(key);
		String headerString = "";
		if (ojs.size() > 0) {
			headerString = ojs.get(0).get(TiGiaOj.BANKNAME);
		}

		Pair<String, List<BaseObject>> res = new Pair<String, List<BaseObject>>(headerString, ojs);
		return res;
	}

	public static HashMap<String, ArrayList<BaseObject>> grouping(ArrayList<BaseObject> ojs1, int type) {
		ArrayList<BaseObject> ojs = sortFloat(ojs1, type);
		HashMap<String, ArrayList<BaseObject>> map = new HashMap<String, ArrayList<BaseObject>>();
		for (BaseObject person : ojs) {
			String key = person.get(TiGiaOj.BANKID);
			if (map.get(key) == null) {
				map.put(key, new ArrayList<BaseObject>());
			}
			map.get(key).add(person);
		}
		return map;

	}

	private static ArrayList<BaseObject> sortFloat(ArrayList<BaseObject> ojRes, int type) {
		ArrayList<BaseObject> cojs = ojRes;
		String key = "";
		if (type == 1 || type == 11)
			key = TiGiaOj.BUY;
		if (type == 2 || type == 22)
			key = TiGiaOj.SELL;
		if (type == 3 || type == 33)
			key = TiGiaOj.TRANSFER;
		if (type == 0 || type == 10)
			key = TiGiaOj.BANKID;
		

		for (int i = 0; i < cojs.size() - 1; i++) {

			float a;
			try {
				a = cojs.get(i).getFloat(key);
			} catch (Exception e) {
				a = Float.MIN_VALUE;
			}

			for (int j = i + 1; j < cojs.size(); j++) {
				float b;
				try {
					b = cojs.get(j).getFloat(key);
				} catch (Exception e) {
					b = Float.MIN_VALUE;
				}

				if (type >= 10 && a < b) { // sap xep tang
					BaseObject oj = cojs.get(i);
					cojs.set(i, cojs.get(j));
					cojs.set(j, oj);
				}

				if (type < 10 && a > b) { // sap xep giam
					BaseObject oj = cojs.get(i);
					cojs.set(i, cojs.get(j));
					cojs.set(j, oj);
				}

			}

		}

		return cojs;

	}

	/*
	 * public static ArrayList<BaseObject> fixPosition(ArrayList<BaseObject>
	 * ojs){ HashMap<String, ArrayList<BaseObject>> map=grouping(ojs);
	 * ArrayList<BaseObject> ojs1=new ArrayList<BaseObject>();
	 * 
	 * Set<String> keys = map.keySet(); for (String string : keys) {
	 * ArrayList<BaseObject> ojscon= map.get(string); ojs1.addAll(ojscon); }
	 * 
	 * return ojs1;
	 * 
	 * 
	 * }
	 */

}
