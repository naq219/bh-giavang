package com.bhmedia.tigia.task;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;

import android.content.Context;

import com.bhmedia.tigia.NetSupport;
import com.bhmedia.tigia.R;
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

		if (!BaseNetSupportBeta.isNetworkAvailable(context)) {
			msg = context.getString(R.string.network_not_avaiable);
			return TASK_FAILED;

		}
		switch (taskType) {
		case TASK_GET_GIAVANG:
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

		case TASK_GET_TIGIA:
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
			int type = (Integer) dataFromModel.get(0);

			Calendar cal = Calendar.getInstance();
			String fromDate = TimeUtils.cal2String(cal, Defi.FORMAT_DATE);
			cal.add(Calendar.MONTH, type);

			String toDate = TimeUtils.cal2String(cal, Defi.FORMAT_DATE);
			// switch (type) {
			// case 1:
			//
			// break;
			// case 3:
			// cal.add(Calendar.MONTH, -1);
			// break;
			// case 6:
			// cal.add(Calendar.MONTH, -1);
			// break;
			// case :
			// cal.add(Calendar.MONTH, -1);
			// break;
			//
			// default:
			// break;
			// }

			try {
				ArrayList<BaseObject> ojsBd = NetSupport.getBieuDo(fromDate, toDate);
				dataReturn = ojsBd;
				return TASK_FAILED;
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
