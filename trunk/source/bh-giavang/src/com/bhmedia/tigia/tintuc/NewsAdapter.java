package com.bhmedia.tigia.tintuc;

import java.util.List;

import com.bhmedia.tigia.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.telpoo.frame.utils.FileSupport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsAdapter extends ArrayAdapter<ObjectNews> {

	Context context;
	int layoutID;
	List<ObjectNews> objects;
	DisplayImageOptions displayImageOptions;

	public NewsAdapter(Context context, int layoutID, List<ObjectNews> objects) {
		super(context, layoutID, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.layoutID = layoutID;
		this.objects = objects;
		/// 
		//sua code image null se replace la ic_lacncher
		this.displayImageOptions = new DisplayImageOptions.Builder().showImageForEmptyUri(R.drawable.ic_launcher)
															//.showStubImage(R.drawable.ic_launcher)
															//.resetViewBeforeLoading(true)
															.cacheOnDisc(true)
															.build();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stu
		ViewHolder viewHolder;
		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater.inflate(layoutID, parent, false);
			// find id item
			viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img_item_listview);
			viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.title_item_listview);
			viewHolder.timeTextView = (TextView) convertView.findViewById(R.id.time_item_listview);
			// set tag for view
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// imageloader tellpoo support

		String nUrl = objects.get(position).get(ObjectNews.URL_IMAGE);

		ImageLoader.getInstance().displayImage(nUrl, viewHolder.imageView, displayImageOptions);
		//displayImage(nUrl, viewHolder.imageView);

		//
		viewHolder.titleTextView.setText(objects.get(position).get(ObjectNews.TITTLE));

		viewHolder.timeTextView.setText(objects.get(position).get(ObjectNews.TIME));
		notifyDataSetChanged();
		// --------------------------------
		return convertView;
	}

	class ViewHolder {
		ImageView imageView;
		TextView titleTextView;
		TextView timeTextView;
	}

	public void SetItems(List<ObjectNews> items) {
		clear();

		Adds(items);

	}
	public void ChangeArrayList(List<ObjectNews> items)
	{
		clear();
		objects = items;
	}

	public void Adds(List<ObjectNews> items) {
		if (items != null) {
			for (ObjectNews item : items) {
				add(item);
			}
		}
	}

}
