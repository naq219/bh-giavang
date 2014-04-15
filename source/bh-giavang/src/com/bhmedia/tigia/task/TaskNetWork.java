package com.bhmedia.tigia.task;

import java.util.ArrayList;

import org.json.JSONException;

import android.content.Context;

import com.bhmedia.tigia.NetSupport;
import com.telpoo.frame.model.BaseTask;
import com.telpoo.frame.model.TaskListener;
import com.telpoo.frame.model.TaskParams;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;

public class TaskNetWork extends BaseTask implements TaskType {

	public TaskNetWork(TaskListener taskListener, int taskType, ArrayList<?> list, Context context) {
		super(taskListener, taskType, list, context);

	}

	@Override
	protected Boolean doInBackground(TaskParams... params) {

		switch (taskType) {
		case TASK_GET_GIAVANG:

			ArrayList<BaseObject> datares;
			try {
				datares = NetSupport.getGiaVang();
			} catch (JSONException e) {
				Mlog.E(e.toString());
			}

			return TASK_FAILED;

		default:
			msg = "no tasktype???";
			return TASK_FAILED;
		}

	}

}
