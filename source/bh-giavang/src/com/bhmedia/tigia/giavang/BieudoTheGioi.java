package com.bhmedia.tigia.giavang;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.bhmedia.tigia.R;
import com.bhmedia.tigia.more.WvFm;
import com.telpoo.frame.utils.ViewUtils;

@SuppressLint("ValidFragment")
public class BieudoTheGioi extends WvFm implements OnClickListener {
	ImageView motthang, haithang, sauthang, motnam;

	public BieudoTheGioi() {
		super("http://goldprice.org/charts/history/gold_30_day_o_usd.png", "Biểu đồ thế giới", R.layout.bieudothegioi);

	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(v, savedInstanceState);

		motthang = (ImageView) v.findViewById(R.id.motthang);
		haithang = (ImageView) v.findViewById(R.id.haithang);
		sauthang = (ImageView) v.findViewById(R.id.sauthang);
		motnam = (ImageView) v.findViewById(R.id.motnam);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		motthang.setOnClickListener(this);
		haithang.setOnClickListener(this);
		sauthang.setOnClickListener(this);
		motnam.setOnClickListener(this);
		
		loadWv("http://goldprice.org/charts/history/gold_30_day_o_usd.png");
	}

	@Override
	public void onClick(View v) {
		String url = "";
		switch (v.getId()) {

		case R.id.motthang:
			url = "http://goldprice.org/charts/history/gold_30_day_o_usd.png";
			loadBg();
			motthang.setImageResource(R.drawable.motthang);
			break;

		case R.id.haithang:
			loadBg();
			haithang.setImageResource(R.drawable.haithang3);
			url = "http://goldprice.org/charts/history/gold_60_day_o_usd.png";
			break;

		case R.id.sauthang:
			loadBg();
			sauthang.setImageResource(R.drawable.sauthang);
			url = "http://goldprice.org/charts/history/gold_6_month_o_usd.png";
			break;

		case R.id.motnam:
			loadBg();
			motnam.setImageResource(R.drawable.motnam);
			url = "http://goldprice.org/charts/history/gold_1_year_o_usd.png";
			break;

		default:
			break;
		}

	
		
		loadWv(url);
	}

	private void loadWv(String url) {
		String data="<html><head><style>img{width:100%%}</style></head><body><div><img src='"+url+"'/></div></body></html>";
		ViewUtils.loadDataWv(wv, data);
		
	}

	private void loadBg() {
		motthang.setImageResource(R.drawable.motthang2);
		haithang.setImageResource(R.drawable.haithang);
		sauthang.setImageResource(R.drawable.sauthang2);
		motnam.setImageResource(R.drawable.motnam2);

	}
}
