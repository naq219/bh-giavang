package com.bhmedia.tigia.tintuc;

import java.util.List;

import com.bhmedia.tigia.MyFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class NgPageViewAdapter  extends FragmentStatePagerAdapter{
	
	List<FragmentWebview> listFragments;
	public NgPageViewAdapter(FragmentManager fm, List<FragmentWebview> fragmentWebviews) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.listFragments = fragmentWebviews;
	}
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		
		return listFragments.get(position);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listFragments.size();
	}
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		//MyFragment fragment = (MyFragment) object;
		
		return POSITION_UNCHANGED;
	}

		

}
