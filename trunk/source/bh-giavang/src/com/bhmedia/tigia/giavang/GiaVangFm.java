package com.bhmedia.tigia.giavang;

import java.util.ArrayList;
import java.util.List;

import com.bhmedia.tigia.R;
import com.bhmedia.tigia.task.TaskNetWork;
import com.bhmedia.tigia.task.TaskType;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class GiaVangFm extends GiaVangLayout implements OnClickListener, TaskType {

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		btn_reload.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_reload:
			TaskNetWork netWork = new TaskNetWork(getModel(), TASK_GET_GIAVANG, null, getActivity());
			getModel().exeTask(null, netWork);
			
			List<String> a=new ArrayList<String>(12);
			break;

		default:
			break;
		}
	}
}
