package com.bhmedia.tigia.object;

import com.telpoo.frame.object.BaseObject;

public class GiaVangOj extends BaseObject {

	public static final String[] keys = { "id", "created", "createdold", "GoldTypeID", "location", "gold_name", "ordering", "location_name", "buy", "sale", "buyold", "saleold" , "group" , "group_name"};
	public static final String[] keysJson1 = { "id", "created", "GoldTypeID", "location", "buy", "sale", "gold_name", "ordering", "location_name" };

	public static final String ID = keys[0];
	public static final String CREATED = keys[1];
	public static final String CREATEDOLD = keys[2];
	public static final String GOLDTYPEID = keys[3];
	public static final String LOCATION = keys[4];
	public static final String GOLD_NAME = keys[5];
	public static final String ORDERING = keys[6];
	public static final String LOCATION_NAME = keys[7];
	public static final String BUY = keys[8];
	public static final String SALE = keys[9];
	public static final String BUYOLD = keys[10];
	public static final String SALEOLD = keys[11];
	public static final String GROUP = keys[12];
	public static final String GROUP_NAME = keys[13];
}
