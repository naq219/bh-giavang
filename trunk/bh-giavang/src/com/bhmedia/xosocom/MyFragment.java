package com.bhmedia.xosocom;

import android.os.Bundle;

import com.telpoo.bhlib.ads.BHAds;
import com.telpoo.frame.ui.BaseFragment;

public class MyFragment extends BaseFragment{
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		BHAds.getInstance().countclick_hidekeyboard(getView(), getActivity());
	}

}
