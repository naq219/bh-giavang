package com.bhmedia.tigia.ngoaite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhmedia.tigia.HomeActivity;
import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigiagoc.R;
import com.bhmedia.tigia.adapter.SectionComposerAdapter;
import com.bhmedia.tigia.adapter.TiGiaAdapter;
import com.foound.widget.AmazingListView;

public class TiGiaLayout extends MyFragment {
	ExpandableListView elv;
	ImageView btn_reload, btnShare, btnMaSo, btnMua, btnBan,btnTranfer;
	TextView tv_title, tv_date;

	AmazingListView lsComposer;
	TiGiaAdapter adapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.tigia, container, false);
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
		lsComposer= (AmazingListView) v.findViewById(R.id.lsComposer);
		tv_date= (TextView) v.findViewById(R.id.tv_date);
		btnTranfer= (ImageView) v.findViewById(R.id.btnTranfer);
		((TextView)v.findViewById(R.id.tv_title)).setText(R.string.ngoaite);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	}
	
	protected void loadBg() {
		btnMaSo.setImageResource(R.drawable.maso2);
		btnMua.setImageResource(R.drawable.mua2);
		btnBan.setImageResource(R.drawable.ban2);
		btnTranfer.setImageResource(R.drawable.ck2);
	}
}
