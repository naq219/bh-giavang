package com.bhmedia.tigia.tintuc;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NgPageViewAdapter  extends FragmentPagerAdapter{
	
	List<FragmentWebview> listFragments;
	public NgPageViewAdapter(FragmentManager fm, List<FragmentWebview> fragmentWebviews) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.listFragments = fragmentWebviews;
	}

	@Override
	public Fragment getItem(int postision) {
		// TODO Auto-generated method stub
		return this.listFragments.get(postision);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.listFragments.size();
	}
	
	

}
