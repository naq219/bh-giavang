package com.bhmedia.tigia.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhmedia.tigiagoc.R;
import com.bhmedia.tigia.object.TiGiaOj;
import com.bhmedia.tigia.utils.Utils1;
import com.foound.widget.AmazingAdapter;
import com.telpoo.frame.object.BaseObject;

public class TiGiaAdapter extends AmazingAdapter {
	List<Pair<String, List<BaseObject>>> all;
	Activity ct;

	public TiGiaAdapter(Activity ct, ArrayList<BaseObject> ojs,int type) {
		this.ct = ct;
		this.all = DataLvTiGia.getAllData(ojs,type);
	}

	@Override
	public int getCount() {
		int res = 0;
		for (int i = 0; i < all.size(); i++) {
			res += all.get(i).second.size();
		}
		return res;
	}

	@Override
	public BaseObject getItem(int position) {
		int c = 0;
		for (int i = 0; i < all.size(); i++) {
			if (position >= c && position < c + all.get(i).second.size()) {
				return all.get(i).second.get(position - c);
			}
			c += all.get(i).second.size();
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	protected void onNextPageRequested(int page) {
	}

	@Override
	protected void bindSectionHeader(View view, int position, boolean displaySectionHeader) {
		if (displaySectionHeader) {
			view.findViewById(R.id.header).setVisibility(View.VISIBLE);
			TextView lSectionTitle = (TextView) view.findViewById(R.id.header);
			lSectionTitle.setText(getSections()[getSectionForPosition(position)]);
		} else {
			view.findViewById(R.id.header).setVisibility(View.GONE);
		}
	}

	@Override
	public View getAmazingView(int position, View convertView, ViewGroup parent) {
		View res = convertView;
		if (res == null)
			res = ct.getLayoutInflater().inflate(R.layout.item_composer_tigia, null);

		TextView code = (TextView) res.findViewById(R.id.code);
		TextView buy = (TextView) res.findViewById(R.id.buy);
		TextView sell = (TextView) res.findViewById(R.id.sell);
		TextView transfer = (TextView) res.findViewById(R.id.transfer);

		BaseObject composer = getItem(position);
		code.setText(composer.get(TiGiaOj.CODE));
		buy.setText(checkNull(composer.get(TiGiaOj.BUY)));
		sell.setText(checkNull(composer.get(TiGiaOj.SELL)));
		transfer.setText(checkNull(composer.get(TiGiaOj.TRANSFER)));

		return res;
	}
	
	private String checkNull(String value){
		if("0".equalsIgnoreCase(value)||"".equalsIgnoreCase(value)) return "-";
		return Utils1.double2String(value);
		
	}

	@Override
	public void configurePinnedHeader(View header, int position, int alpha) {
		TextView lSectionHeader = (TextView) header;
		lSectionHeader.setText(getSections()[getSectionForPosition(position)]);
		// lSectionHeader.setBackgroundColor(alpha << 24 | (0xbbffbb));
		// lSectionHeader.setTextColor(alpha << 24 | (0x000000));
	}

	@Override
	public int getPositionForSection(int section) {
		if (section < 0)
			section = 0;
		if (section >= all.size())
			section = all.size() - 1;
		int c = 0;
		for (int i = 0; i < all.size(); i++) {
			if (section == i) {
				return c;
			}
			c += all.get(i).second.size();
		}
		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		int c = 0;
		for (int i = 0; i < all.size(); i++) {
			if (position >= c && position < c + all.get(i).second.size()) {
				return i;
			}
			c += all.get(i).second.size();
		}
		return -1;
	}

	@Override
	public String[] getSections() {
		String[] res = new String[all.size()];
		for (int i = 0; i < all.size(); i++) {
			res[i] = all.get(i).first;
		}
		return res;
	}

}