package com.bhmedia.tigia.db;

import java.util.ArrayList;

import android.content.Context;

import com.bhmedia.tigia.object.GiaVangOj;
import com.telpoo.frame.database.BaseDBSupport;
import com.telpoo.frame.database.BaseDBSupport2;
import com.telpoo.frame.object.BaseObject;

public class DbSupport extends BaseDBSupport2 {

	protected DbSupport(Context context) {
		super(context);
	}

	public static ArrayList<BaseObject> getTiGiaOffline() {
		return DbSupport.getAllOfTable(TableDb.TIGIA, TableDb.keytable);
	}

	public static ArrayList<BaseObject> getGiaVangOffline() {
		return DbSupport.getAllOfTable(TableDb.GIAVANG, TableDb.keytable);
	}

	public static void saveTiGiaOffline(BaseObject oj) {

		ArrayList<BaseObject> pl = new ArrayList<BaseObject>();
		pl.add(oj);
		DbSupport.addToTable(pl, TableDb.TIGIA);
	}
	
	public static void saveGiaVangOffline(BaseObject oj) {

		ArrayList<BaseObject> pl = new ArrayList<BaseObject>();
		pl.add(oj);
		DbSupport.addToTable(pl, TableDb.GIAVANG);
	}
}
