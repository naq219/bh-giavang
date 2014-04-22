package com.bhmedia.tigia.ngoaite;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;

import com.bhmedia.tigia.HomeActivity;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.adapter.DataLvTiGia;
import com.bhmedia.tigia.adapter.TiGiaAdapter;
import com.bhmedia.tigia.giavang.BieudoTheGioi;
import com.bhmedia.tigia.task.TaskType;
import com.bhmedia.tigia.utils.Defi;
import com.bhmedia.tigia.utils.MyDialog;
import com.bhmedia.tigia.utils.TabId;
import com.bhmedia.tigia.utils.Utils1;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.delegate.WhereIdelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.DialogUtils;
import com.telpoo.frame.utils.TimeUtils;

public class TiGiaFm extends TiGiaLayout implements OnClickListener, TaskType, Idelegate {
	ArrayList<BaseObject> curLvData = new ArrayList<BaseObject>();
	Calendar curcal;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		btn_reload.setOnClickListener(this);
		btnShare.setOnClickListener(this);

		curcal = Calendar.getInstance();
		Utils1.runTaskTiGia(Calendar.getInstance(), getModel(), getActivity(), this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_reload:
			curcal = Calendar.getInstance();
			Utils1.runTaskTiGia(Calendar.getInstance(), getModel(), getActivity(), this);

			break;

		case R.id.btnShare:
			MyDialog.dialogChoose(this, getActivity(), 1);
			break;

		default:
			break;
		}
	}

	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		super.onSuccess(taskType, list, msg);

		switch (taskType) {
		case TASK_GET_TIGIA:

			closeProgressDialog();
			ArrayList<BaseObject> ojres = (ArrayList<BaseObject>) list;

			if (list == null || list.size() == 0) {
				showToast("Không có dữ liệu");
				return;
			}

			tv_date.setText(getString(R.string.c_p_nh_t_ng_y_) + TimeUtils.cal2String(curcal, Defi.FORMAT_DATE));

			curLvData = DataLvTiGia.fixPosition(ojres);
			adapter = new TiGiaAdapter(getActivity(), ojres);
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			View v = inflater.inflate(R.layout.item_composer_header, lsComposer, false);
			lsComposer.setPinnedHeaderView(v);
			lsComposer.setAdapter(adapter);
			break;

		default:
			break;
		}
	}

	@Override
	public void onFail(int taskType, String msg) {
		super.onFail(taskType, msg);
		switch (taskType) {
		case TASK_GET_GIAVANG:
			closeProgressDialog();
			showToast("" + msg);
			break;

		default:
			break;
		}
	}

	@Override
	public void callBack(Object value, int where) {
		switch (where) {
		case Defi.whereIdelegate.DIALOGCHOOSE:

			int vlChoose = (Integer) value;

			if (vlChoose == 1) // chon ngay
				DialogUtils.datePicker(getActivity(), this);

			if (vlChoose == 0)// quy doi ngoai te
				if (curLvData != null && curLvData.size() > 0)
					HomeActivity.getInstance().pushFragments(TabId.Ti_GIA, new QuyDoiNgoaiTe(DataLvTiGia.grouping(curLvData),tv_date.getText().toString()), true, null);
				else
					showToast(getContext().getString(R.string.chuacodulieu));
			
			if(vlChoose==2) 
				HomeActivity.getInstance().pushFragments(TabId.Ti_GIA, new BieudoNgoaiTe(), true, null);
			
			break;

		case WhereIdelegate.DIALOGUTILS_DATEPICKER:
			curcal = (Calendar) value;
			Utils1.runTaskTiGia(curcal, getModel(), getActivity(), this);

			break;
		default:
			break;
		}

	}

}
