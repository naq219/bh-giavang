/**
 * 
 */
package com.bhmedia.tigia.db;

import android.os.Environment;


/**
 * @author naq
 *
 */
public class TableDb {
	public static String[] keytable={"time","value"};
	public static String pathDbName="tigia.sqlite";
	public static String[] tables = { "giavang","tigia"};
	public static String[][] keys = {keytable,keytable};
	public static String GIAVANG=tables[0];
	public static String TIGIA=tables[1];

}

