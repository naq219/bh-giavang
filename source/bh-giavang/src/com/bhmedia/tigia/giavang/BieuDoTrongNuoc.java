package com.bhmedia.tigia.giavang;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhmedia.tigia.HomeActivity;
import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.object.BieuDoOj;
import com.bhmedia.tigia.task.TaskNetWork;
import com.bhmedia.tigia.task.TaskType;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;

@SuppressLint("ValidFragment")
public class BieuDoTrongNuoc extends MyFragment implements OnClickListener {
	ImageView motthang, haithang, sauthang, motnam;
	ChartView chartView;
	TextView ban, mua, time, theo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.bieudotrongnuoc, container, false);
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(v, savedInstanceState);

		motthang = (ImageView) v.findViewById(R.id.motthang);
		haithang = (ImageView) v.findViewById(R.id.haithang);
		sauthang = (ImageView) v.findViewById(R.id.sauthang);
		motnam = (ImageView) v.findViewById(R.id.motnam);
		chartView = (ChartView) v.findViewById(R.id.charView);
		ban = (TextView) v.findViewById(R.id.ban);
		mua = (TextView) v.findViewById(R.id.mua);
		time = (TextView) v.findViewById(R.id.time);
		theo = (TextView) v.findViewById(R.id.theo);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		getView().findViewById(R.id.btn_top).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HomeActivity.getInstance().popFragments();

			}
		});
		((TextView) getView().findViewById(R.id.tv_title)).setText(R.string.bieudotrongnuoc);
		motthang.setOnClickListener(this);
		haithang.setOnClickListener(this);
		sauthang.setOnClickListener(this);
		motnam.setOnClickListener(this);

		chartView.setListtener(new Idelegate() {

			@Override
			public void callBack(Object value, int where) {

				BaseObject oj = (BaseObject) value;
				ban.setText("Bán: " + oj.get(BieuDoOj.SALE));
				mua.setText("Mua: " + oj.get(BieuDoOj.BUY));
				time.setText("" + oj.get(BieuDoOj.CREATED));

			}
		});

		motthang.setImageResource(R.drawable.motthang);
		ArrayList<Integer> dataSend1 = new ArrayList<Integer>();
		dataSend1.add(1);
		TaskNetWork netWork1 = new TaskNetWork(getModel(), TaskType.TASK_BIEUDO, dataSend1, getActivity());
		getModel().exeTask(null, netWork1);
		showProgressDialog(getActivity());
	}

	@Override
	public void onClick(View v) {
		ArrayList<Integer> dataSend = new ArrayList<Integer>();
		switch (v.getId()) {

		case R.id.motthang:
			dataSend.add(1);

			loadBg();
			motthang.setImageResource(R.drawable.motthang);
			break;

		case R.id.haithang:
			loadBg();
			haithang.setImageResource(R.drawable.haithang3);
			dataSend.add(2);
			break;

		case R.id.sauthang:
			loadBg();
			sauthang.setImageResource(R.drawable.sauthang);
			dataSend.add(6);
			break;

		case R.id.motnam:
			loadBg();
			motnam.setImageResource(R.drawable.motnam);

			dataSend.add(12);
			break;

		default:
			break;
		}

		TaskNetWork netWork = new TaskNetWork(getModel(), TaskType.TASK_BIEUDO, dataSend, getActivity());
		getModel().exeTask(null, netWork);
		showProgressDialog(getActivity());

	}

	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		super.onSuccess(taskType, list, msg);
		switch (taskType) {
		case TaskType.TASK_BIEUDO:
			closeProgressDialog();

			updateUI(list);

			break;

		default:
			break;
		}

	}

	private void updateUI(ArrayList<?> list) {
		ArrayList<BaseObject> ojs = (ArrayList<BaseObject>) list;
		chartView.setData(ojs);
	}

	@Override
	public void onFail(int taskType, String msg) {
		super.onFail(taskType, msg);
		switch (taskType) {
		case TaskType.TASK_BIEUDO:
			closeProgressDialog();
			showToast(msg);
			break;

		default:
			break;
		}
	}

	private void loadBg() {
		motthang.setImageResource(R.drawable.motthang2);
		haithang.setImageResource(R.drawable.haithang);
		sauthang.setImageResource(R.drawable.sauthang2);
		motnam.setImageResource(R.drawable.motnam2);

	}

}
