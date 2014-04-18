package com.bhmedia.tigia.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.os.SystemClock;
import android.util.Pair;

import com.bhmedia.tigia.object.TiGiaOj;
import com.bhmedia.tigia.object.TiGiaOj;
import com.telpoo.frame.object.BaseObject;

public class DataLvTiGia {
	public static final String TAG = DataLvTiGia.class.getSimpleName();

	public static List<Pair<String, List<BaseObject>>> getAllData(ArrayList<BaseObject> ojs) {
		List<Pair<String, List<BaseObject>>> res = new ArrayList<Pair<String, List<BaseObject>>>();
		HashMap<String, ArrayList<BaseObject>> mapedData = grouping(ojs);
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

	public static HashMap<String, ArrayList<BaseObject>> grouping(ArrayList<BaseObject> ojs) {

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
	
	public static ArrayList<BaseObject> fixPosition(ArrayList<BaseObject> ojs){
		HashMap<String, ArrayList<BaseObject>> map=grouping(ojs);
		ArrayList<BaseObject> ojs1=new ArrayList<BaseObject>();
		
		Set<String> keys = map.keySet();
		for (String string : keys) {
			ArrayList<BaseObject> ojscon= map.get(string);
			ojs1.addAll(ojscon);
		}
		
		return ojs1;
		
		
	}
	
	
}
