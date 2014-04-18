package com.bhmedia.tigia.task;

import java.util.ArrayList;

import org.json.JSONException;

import android.content.Context;

import com.bhmedia.tigia.NetSupport;
import com.bhmedia.tigia.R;
import com.telpoo.frame.model.BaseTask;
import com.telpoo.frame.model.TaskListener;
import com.telpoo.frame.model.TaskParams;
import com.telpoo.frame.object.BaseObject;

public class TaskNetWork extends BaseTask implements TaskType {

	public TaskNetWork(TaskListener taskListener, int taskType, ArrayList<?> list, Context context) {
		super(taskListener, taskType, list, context);

	}

	@Override
	protected Boolean doInBackground(TaskParams... params) {

		switch (taskType) {
		case TASK_GET_GIAVANG:
			String date = (String) dataFromModel.get(0);
			ArrayList<BaseObject> datares = null;
			try {
				datares = NetSupport.getGiaVang(date);
			} catch (JSONException e) {
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

		default:
			msg = "no tasktype???";
			return TASK_FAILED;
		}

	}

}
