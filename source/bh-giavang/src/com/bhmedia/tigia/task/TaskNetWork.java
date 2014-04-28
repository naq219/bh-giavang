package com.bhmedia.tigia.task;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;

import android.content.Context;

import com.bhmedia.tigia.NetSupport;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.db.DbSupport;
import com.bhmedia.tigia.db.TableDb;
import com.bhmedia.tigia.utils.Defi;
import com.telpoo.frame.model.BaseTask;
import com.telpoo.frame.model.TaskListener;
import com.telpoo.frame.model.TaskParams;
import com.telpoo.frame.net.BaseNetSupportBeta;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.frame.utils.TimeUtils;

public class TaskNetWork extends BaseTask implements TaskType {

	private static final String TAG = TaskNetWork.class.getSimpleName();

	public TaskNetWork(TaskListener taskListener, int taskType, ArrayList<?> list, Context context) {
		super(taskListener, taskType, list, context);

	}

	@Override
	protected Boolean doInBackground(TaskParams... params) {

		
		switch (taskType) {
		case TASK_GET_GIAVANG:
			
			if (!BaseNetSupportBeta.isNetworkAvailable(context)) {
				msg = context.getString(R.string.network_not_avaiable);
				return TASK_FAILED;

			}
			
			String date = (String) dataFromModel.get(0);
			ArrayList<BaseObject> datares = null;
			try {
				datares = NetSupport.getGiaVang(date);
			} catch (JSONException e) {
				Mlog.E(e.toString());
				msg = context.getString(R.string.errconnect);
				return TASK_FAILED;
			}

			dataReturn = datares;
			return TASK_DONE;

		case TASK_GIAVANG_OFFLINE:

			ArrayList<BaseObject> datares3 = DbSupport.getGiaVangOffline();
			if (datares3.size() == 0) {

				msg = "Chưa có dữ liệu gần đây";
				return TASK_FAILED;
			}

			ArrayList<BaseObject> dataGVOffline = null;

			try {
				dataGVOffline = NetSupport.parseGiaVang(datares3.get(0).get(TableDb.keytable[1]));
			} catch (JSONException e) {
				Mlog.E(e.toString());
				msg = context.getString(R.string.errconnect);
				return TASK_FAILED;
			}

			dataReturn = dataGVOffline;
			return TASK_DONE;

		case TASK_TIGIA_OFFLINE:

			ArrayList<BaseObject> datares2 = DbSupport.getTiGiaOffline();
			if (datares2.size() == 0) {

				msg = "Chưa có dữ liệu gần đây";
				return TASK_FAILED;
			}

			ArrayList<BaseObject> dataTgOffline = null;

			try {
				dataTgOffline = NetSupport.parseTiga(datares2.get(0).get(TableDb.keytable[1]));
			} catch (JSONException e) {
				Mlog.E(e.toString());
				msg = context.getString(R.string.errconnect);
				return TASK_FAILED;
			}

			dataReturn = dataTgOffline;
			return TASK_DONE;

		case TASK_GET_TIGIA:
			
			if (!BaseNetSupportBeta.isNetworkAvailable(context)) {
				msg = context.getString(R.string.network_not_avaiable);
				return TASK_FAILED;

			}
			
			String date1 = (String) dataFromModel.get(0);
			ArrayList<BaseObject> datares1 = null;
			try {
				datares1 = NetSupport.getTiGia(date1);
			} catch (JSONException e) {
				msg = context.getString(R.string.errconnect);
				return TASK_FAILED;
			}

			dataReturn = datares1;
			return TASK_DONE;

		case TASK_BIEUDO:
			
			if (!BaseNetSupportBeta.isNetworkAvailable(context)) {
				msg = context.getString(R.string.network_not_avaiable);
				return TASK_FAILED;

			}
			
			int type = (Integer) dataFromModel.get(0);

			Calendar cal = Calendar.getInstance();
			String toDate = TimeUtils.cal2String(cal, Defi.FORMAT_DATE);
			cal.add(Calendar.MONTH, -type);

			String fromDate = TimeUtils.cal2String(cal, Defi.FORMAT_DATE);

			try {
				ArrayList<BaseObject> ojsBd = NetSupport.getBieuDo(fromDate, toDate);
				dataReturn = ojsBd;
				return TASK_DONE;
			} catch (JSONException e) {
				Mlog.E(TAG + " TASK_BIEUDO");

				msg = context.getString(R.string.connect_error);
				return TASK_FAILED;
			}

		default:
			msg = "no tasktype???";
			return TASK_FAILED;
		}

	}

}
