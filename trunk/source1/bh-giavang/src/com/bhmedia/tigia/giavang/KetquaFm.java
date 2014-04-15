package com.bhmedia.tigia.giavang;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;


public class KetquaFm extends MyFragment  {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.giavang, container, false);
		return v;
	}
}
