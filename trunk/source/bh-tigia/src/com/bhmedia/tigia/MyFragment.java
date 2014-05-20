package com.bhmedia.tigia;

import android.os.Bundle;

import com.bhmedia.tigia.utils.Defi;
import com.telpoo.bhlib.ads.BHAds;
import com.telpoo.frame.ui.BaseFragment;
//import com.telpoo.bhlib.ads.BHAds;

public class MyFragment extends BaseFragment implements Defi.categoryTrack{
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		BHAds.getInstance().countclick_hidekeyboard(getView(), getActivity());
	}

}
