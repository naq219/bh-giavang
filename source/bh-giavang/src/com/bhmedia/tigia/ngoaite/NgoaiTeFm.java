package com.bhmedia.tigia.ngoaite;

import java.util.ArrayList;

import android.os.Bundle;

import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.task.TaskNetWork;
import com.bhmedia.tigia.task.TaskType;

public class NgoaiTeFm extends MyFragment {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		TaskNetWork netWork = new TaskNetWork(getModel(), TaskType.TASK_GET_TIGIA, null, getActivity());

		getModel().exeTask(null, netWork);
		
		
	}
	
	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		// TODO Auto-generated method stub
		super.onSuccess(taskType, list, msg);
	}
}
