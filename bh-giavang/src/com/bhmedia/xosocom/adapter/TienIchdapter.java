package com.bhmedia.xosocom.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhmedia.tigia.R;
import com.telpoo.frame.object.BaseObject;

public class TienIchdapter extends ArrayAdapter<BaseObject> {

	private Context context;
	private int mLayoutRes;
	private LayoutInflater mInflater;
	ArrayList<BaseObject> listIn;

	public TienIchdapter(Context context, int textViewResourceId, ArrayList<BaseObject> listIn1) {
		super(context, textViewResourceId, listIn1);

		this.context = context;
		this.mLayoutRes = textViewResourceId;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listIn = listIn1;
	}

	static class ViewHolder {
		ImageView icon;
		TextView title, des;

	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		final ViewHolder v;
		if (view == null) {
			view = mInflater.inflate(mLayoutRes, parent, false);
			v = new ViewHolder();

			v.des = (TextView) view.findViewById(R.id.des);
			v.icon = (ImageView) view.findViewById(R.id.icon);
			v.title = (TextView) view.findViewById(R.id.title);

			view.setTag(v);
		} else {
			v = (ViewHolder) view.getTag();
		}

		BaseObject item = getItem(position);

		updateListView(v, item, position);

		return view;
	}

	public void SetItems(ArrayList<BaseObject> items) {

		clear();
		Adds(items);

	}

	public void Adds(ArrayList<BaseObject> items) {
		if (items != null) {
			for (BaseObject item : items) {
				add(item);
			}
		}
	}

	private void updateListView(ViewHolder v, BaseObject item, int position) {
	
	}
}
