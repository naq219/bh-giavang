package com.bhmedia.tigia.giavang;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.bhmedia.tigia.HomeActivity;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.adapter.DataLv;
import com.bhmedia.tigia.adapter.SectionComposerAdapter;
import com.bhmedia.tigia.more.WebvFm;
import com.bhmedia.tigia.object.GiaVangOj;
import com.bhmedia.tigia.task.TaskNetWork;
import com.bhmedia.tigia.task.TaskType;
import com.bhmedia.tigia.utils.Defi;
import com.bhmedia.tigia.utils.Defi.whereIdelegate;
import com.bhmedia.tigia.utils.MyDialog;
import com.bhmedia.tigia.utils.SaveDataFragment;
import com.bhmedia.tigia.utils.TabId;
import com.bhmedia.tigia.utils.Utils1;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.delegate.WhereIdelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.DialogUtils;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.frame.utils.TimeUtils;

public class GiaVangFm extends GiaVangLayout implements OnClickListener, TaskType, Idelegate {
	ArrayList<BaseObject> curLvData = new ArrayList<BaseObject>();
	Calendar curcal;
	int type = 0;
	private boolean firstGoto = true;;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		btn_reload.setOnClickListener(this);
		btnShare.setOnClickListener(this);

		lsComposer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				MyDialog.detail(getActivity(), adapter.getItem(arg2));

			}
		});

		
		curcal = Calendar.getInstance();
		

		btnMaSo.setOnClickListener(this);
		btnMua.setOnClickListener(this);
		btnBan.setOnClickListener(this);
		
		if(SaveDataFragment.arrGiaVang!=null){
			
			String date= SaveDataFragment.arrGiaVang.get(0).get(GiaVangOj.CREATED);
			tv_date.setText(getString(R.string.c_p_nh_t_ng_y_) + (date.length()>10?date.substring(0, 11):date));
			
			adapter = new SectionComposerAdapter(getActivity(), SaveDataFragment.arrGiaVang, type, this);
			LayoutInflater inflater = LayoutInflater.from(getActivity());
			View v = inflater.inflate(R.layout.item_composer_header, lsComposer, false);
			lsComposer.setPinnedHeaderView(v);
			lsComposer.setAdapter(adapter);
		}
		else Utils1.runTaskGiaVang(Calendar.getInstance(), getModel(), getActivity(), this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_reload:
			curcal = Calendar.getInstance();
			Utils1.runTaskGiaVang(Calendar.getInstance(), getModel(), getActivity(), this);
			// tv_date.setText(R.string.c_p_nh_t_ng_y_ +
			// TimeUtils.cal2String(Calendar.getInstance(), Defi.FORMAT_DATE));

			break;

		case R.id.btnShare:
			MyDialog.dialogChoose(this, getActivity(), 0);
			break;

		case R.id.btnMaSo:
			if (type != 0)
				type = 0;
			else
				type = 10;

			updateSort();

			loadBg();
			btnMaSo.setImageResource(R.drawable.maso);
			break;
		case R.id.btnMua:
			if (type != 1)
				type = 1;
			else
				type = 11;

			updateSort();

			loadBg();
			btnMua.setImageResource(R.drawable.mua);

			break;
		case R.id.btnBan:
			if (type != 2)
				type = 2;
			else
				type = 22;

			updateSort();

			loadBg();
			btnBan.setImageResource(R.drawable.ban);

			break;

		default:
			break;
		}
	}

	private void updateSort() {
		// curLvData = DataLv.fixPosition(curLvData, type);

		adapter = new SectionComposerAdapter(getActivity(), curLvData, type, this);
		adapter.notifyDataSetChanged();
		lsComposer.setAdapter(adapter);
	}

	@Override
	public void onSuccess(int taskType, ArrayList<?> list, String msg) {
		super.onSuccess(taskType, list, msg);

		switch (taskType) {
		
		case TASK_GIAVANG_OFFLINE:
		case TASK_GET_GIAVANG:

			closeProgressDialog();
			ArrayList<BaseObject> ojres = (ArrayList<BaseObject>) list;

			if (list == null || list.size() == 0) {
				showToast(getContext().getString(R.string.khongcodulieu));
				return;
			}

			tv_date.setText(getString(R.string.c_p_nh_t_ng_y_) + TimeUtils.cal2String(curcal, Defi.FORMAT_DATE_TV_DATE));
			curLvData = ojres;
			SaveDataFragment.arrGiaVang=ojres;
			// curLvData = DataLv.fixPosition(ojres, type);
			adapter = new SectionComposerAdapter(getActivity(), ojres, type, this);
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
		case TASK_GIAVANG_OFFLINE:
		case TASK_GET_GIAVANG:
			closeProgressDialog();
			if (firstGoto) {
				firstGoto = false;
				MyDialog.dialogAskOffline(getActivity(), new Idelegate() {

					@Override
					public void callBack(Object value, int where) {
						boolean vl = (Boolean) value;
						if (vl) {
							TaskNetWork netWork = new TaskNetWork(getModel(), TASK_GIAVANG_OFFLINE, null, getActivity());
							getModel().exeTask(null, netWork);
						}
						//else HomeActivity.getInstance().finish();

					}
				}, 1);
			} else
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

			if (vlChoose == 0)// quy doi gia vang
				if (curLvData != null && curLvData.size() > 0)
					HomeActivity.getInstance().pushFragments(TabId.GIAVANG, new QuyDoiGiaVang(DataLv.grouping(curLvData, type)), true, null);
				else
					showToast(getContext().getString(R.string.chuacodulieu));

			if (vlChoose == 2)// bieu do

				MyDialog.dialogChoose(new Idelegate() {

					@Override
					public void callBack(Object value, int where) {
						int vlChoose = (Integer) value;
						switch (vlChoose) {
						case 0:// bieu do trong nuoc
							HomeActivity.getInstance().pushFragments(TabId.GIAVANG, new BieuDoTrongNuoc(), true, null);

							break;

						case 1:// bieu do the gioi
							HomeActivity.getInstance().pushFragments(TabId.GIAVANG, new BieudoTheGioi(), true, null);

							break;

						case 2:// bieu do san kitco
							HomeActivity.getInstance().pushFragments(TabId.GIAVANG, new WebvFm(Defi.url.KITCO, "Biểu đồ sàn Kitco"), true, null);

							break;

						default:
							break;
						}

						
					}
				}, getActivity(), 2);

			
			break;

		case WhereIdelegate.DIALOGUTILS_DATEPICKER:
			curcal = (Calendar) value;
			if(curcal.getTimeInMillis()>Calendar.getInstance().getTimeInMillis()){
				showToast(getContext().getString(R.string.khongduocnhapquangayhientai));
			}
			else
			Utils1.runTaskGiaVang(curcal, getModel(), getActivity(), this);
			// tv_date.setText(getString(R.string.c_p_nh_t_ng_y_) +
			// TimeUtils.cal2String(cal, Defi.FORMAT_DATE));

			break;

		case whereIdelegate.LV_ITEM_CLICK:
			MyDialog.detail(getActivity(), (BaseObject) value);

		default:
			break;
		}

	}

	private void loadBg() {
		btnMaSo.setImageResource(R.drawable.maso2);
		btnMua.setImageResource(R.drawable.mua2);
		btnBan.setImageResource(R.drawable.ban2);

	}

}
