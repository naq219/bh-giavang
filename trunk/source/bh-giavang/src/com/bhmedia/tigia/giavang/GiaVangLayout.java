package com.bhmedia.tigia.giavang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;

public class GiaVangLayout extends MyFragment {
	ExpandableListView elv;
	ImageView btn_reload, btnShare, btnMaSo, btnMua, btnBan;
	TextView tv_title, tv_date;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.giavang, container, false);
		return v;
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		btn_reload = (ImageView) v.findViewById(R.id.btn_reload);
		btnShare = (ImageView) v.findViewById(R.id.btnShare);
		btnMaSo = (ImageView) v.findViewById(R.id.btnMaSo);
		btnMua = (ImageView) v.findViewById(R.id.btnMua);
		btnBan = (ImageView) v.findViewById(R.id.btnBan);
	}
}
