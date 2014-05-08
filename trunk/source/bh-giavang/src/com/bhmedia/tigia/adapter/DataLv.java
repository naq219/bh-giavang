package com.bhmedia.tigia.adapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import android.os.SystemClock;
import android.util.Pair;

import com.bhmedia.tigia.object.GiaVangOj;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;

public class DataLv {
	public static final String TAG = DataLv.class.getSimpleName();

	public static List<Pair<String, List<BaseObject>>> getAllData(ArrayList<BaseObject> ojs, int type) {
		List<Pair<String, List<BaseObject>>> res = new ArrayList<Pair<String, List<BaseObject>>>();
		HashMap<String, ArrayList<BaseObject>> mapedData = grouping(ojs, type);
		
		Set<String> k = mapedData.keySet();
		String[] k1 = k.toArray(new String[0]);
		
		for (int v = 0; v < k1.length-1; v++) {
			for (int l = v; l < k1.length; l++) {
				if(Integer.parseInt(k1[v])>Integer.parseInt(k1[l]))
				{
					String temp= k1[v];
					k1[v]=k1[l];
					k1[l]=temp;
				}
			}
		}
		HashMap<String, ArrayList<BaseObject>> mapedDataN=new HashMap<String, ArrayList<BaseObject>>();
		
		for (String string : k1) {
			mapedDataN.put(string, mapedData.get(string));
		}
		
		for (int i = 0; i < mapedData.size(); i++) {
			res.add(getOneSection(i,k1, mapedData));
		}

		return res;
	}

	/*public static List<BaseObject> getFlattenedData(HashMap<String, ArrayList<BaseObject>> mapedData) {
		List<BaseObject> res = new ArrayList<BaseObject>();

		for (int i = 0; i < mapedData.size(); i++) {
			res.addAll(getOneSection(i, mapedData).second);
		}

		return res;
	}*/

	/*public static Pair<Boolean, List<BaseObject>> getRows(int page, HashMap<String, ArrayList<BaseObject>> mapedData) {
		List<BaseObject> flattenedData = getFlattenedData(mapedData);
		if (page == 1) {
			return new Pair<Boolean, List<BaseObject>>(true, flattenedData.subList(0, 5));
		} else {
			SystemClock.sleep(2000); // simulate loading
			return new Pair<Boolean, List<BaseObject>>(page * 5 < flattenedData.size(), flattenedData.subList((page - 1) * 5, Math.min(page * 5, flattenedData.size())));
		}
	}*/

	public static Pair<String, List<BaseObject>> getOneSection(int index,String[] k1, HashMap<String, ArrayList<BaseObject>> mapedData) {
		/*Set<String> titles = mapedData.keySet();
		String[] mtitle = titles.toArray(new String[0]);
		String key = mtitle[index];*/
		
		String key=k1[index];
		ArrayList<BaseObject> ojs = mapedData.get(key);
		String headerString = "";
		if (ojs.size() > 0) {
			headerString = ojs.get(0).get(GiaVangOj.GROUP_NAME);
		}

		Pair<String, List<BaseObject>> res = new Pair<String, List<BaseObject>>(headerString, ojs);
		return res;
	}

	public static HashMap<String, ArrayList<BaseObject>> grouping(ArrayList<BaseObject> ojs1, final int type) {
		ArrayList<BaseObject> ojs =sortFloat(ojs1, type);
		HashMap<String, ArrayList<BaseObject>> map = new HashMap<String, ArrayList<BaseObject>>();
		for (BaseObject person : ojs) {
			String key = person.get(GiaVangOj.GROUP);
			if (map.get(key) == null) {
				map.put(key, new ArrayList<BaseObject>());
			}
			map.get(key).add(person);
		}
		
		

		/*
		Set<String> key1 = map.keySet();
		for (String string : key1) {
			ArrayList<BaseObject> cojs = map.get(string);
			//sortFloat(cojs, type);
			
			if (type == 0) // sort ma so
			{
				Collections.sort(cojs, new Comparator<BaseObject>() {

					@Override
					public int compare(BaseObject oj1, BaseObject oj2) {

						return oj1.get(GiaVangOj.ID).compareTo(oj2.get(GiaVangOj.ID));
					}
				});
			}

			if (type == 1 || type == 11) { // sort mua

				sortFloat(cojs, type);

				Collections.sort(cojs, new Comparator<BaseObject>() {

					@Override
					public int compare(BaseObject oj1, BaseObject oj2) {
						float a = Float.parseFloat(oj1.get(GiaVangOj.BUY));
						float b = Float.parseFloat(oj2.get(GiaVangOj.BUY));
						if (type == 1)
							return (int) (a - b);
						else
							return (int) (b - a);
					}
				});
			}

			if (type == 2) { // sort ban
				Collections.sort(cojs, new Comparator<BaseObject>() {

					@Override
					public int compare(BaseObject oj1, BaseObject oj2) {
fixPosition
						return oj1.get(GiaVangOj.SALE).compareTo(oj2.get(GiaVangOj.SALE));
					}
				});
			}
		} */

		return map;

	}

	private static ArrayList<BaseObject> sortFloat(ArrayList<BaseObject> ojRes, final int type) {
		ArrayList<BaseObject> cojs=ojRes;
	

		
		Comparator<BaseObject> comparator=new Comparator<BaseObject>() {
			
			@Override
			public int compare(BaseObject oj1, BaseObject oj2) {
				String key="";
				if(type==1||type==11) key= GiaVangOj.BUY;
				if(type==2||type==22) key= GiaVangOj.SALE;
				if(type==0||type==10) key= GiaVangOj.ID;
				
				
				float b,a = 0;
				try {
					a = oj1.getFloat(key);
					b = oj2.getFloat(key);
				} catch (Exception e) {
					b = Float.MIN_VALUE;
				}
				
				if(type < 10){
					
					if (a < b) return -1;
					if (a > b) return 1;
					return 0;
				}
				else{
					if (a > b) return -1;
					if (a < b) return 1;
					return 0;
				}
				
			}
		};
		Collections.sort(cojs, comparator);
		
		return cojs;

	}

	public static ArrayList<BaseObject> fixPosition(ArrayList<BaseObject> ojs, int type) {
		HashMap<String, ArrayList<BaseObject>> map = grouping(ojs, type);
		ArrayList<BaseObject> ojs1 = new ArrayList<BaseObject>();
		
		List<Pair<String, List<BaseObject>>> all = getAllData(ojs, type);
		for (Pair<String, List<BaseObject>> pair : all) {
			ojs1.addAll(pair.second);
		}
		
		
	/*	Set<String> keys = map.keySet();
		for (String string : keys) {
			ArrayList<BaseObject> ojscon = map.get(string);fixPosition
			ojs1.addAll(ojscon);
		}*/

		return ojs1;

	}

}
