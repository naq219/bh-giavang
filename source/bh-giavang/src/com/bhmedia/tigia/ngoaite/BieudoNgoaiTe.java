package com.bhmedia.tigia.ngoaite;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.bhmedia.tigia.R;
import com.bhmedia.tigia.more.WvFm;
import com.bhmedia.tigia.utils.Utils1;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.utils.ViewUtils;

@SuppressLint("ValidFragment")
public class BieudoNgoaiTe extends WvFm {
	String url5d = "http://chart.finance.yahoo.com/w?s=";
	String url3m = "http://chart.finance.yahoo.com/3m?";
	String url1y = "http://chart.finance.yahoo.com/1y?";
	String url2y = "http://chart.finance.yahoo.com/2y?";
	String endStr = "=x&lang=en-US&region=US";
	String[] code = { "VND", "USD", "EUR", "CHF", "GBP", "DKK", "SGD", "KRW", "CAD", "KWD", "AUD", "MYR", "HKD", "NOK", "JPY", "SEK", "RUB", "THB", "INR" };
	String[] loc = { "VIỆT NAM ĐỒNG", "US DOLLAR", "EURO", "SWISS FRANCE", "BRITISH POUND", "DANISH KRONE", "SINGAPORE DOLLAR", "SOUTH KOREAN WON", "CANADIAN DOLLAR",
			"KUWAITI DINAR", "AUST.DOLLAR", "MALAYSIAN RINGGIT", "HONGKONG DOLLAR", "NORWEGIAN KRONER", "JAPANESE YEN", "SWEDISH KRONA", "RUSSIAN RUBLE", "THAI BAHT",
			"INDIAN RUPEE" };
	String[] code_loc;
	TextView tv1, tv2;
	String curTv1key, curTv2key, curTv1, curTv2;

	public BieudoNgoaiTe() {
		super("http://", "Biểu đồ ngoại tệ", R.layout.bieudongoaite);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		tv1 = (TextView) view.findViewById(R.id.tv1);
		tv2 = (TextView) view.findViewById(R.id.tv2);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		code_loc = new String[code.length];
		for (int i = 0; i < code.length; i++) {
			code_loc[i] = code[i] + " - " + loc[i];
		}
		curTv1key = code[1];
		curTv2key = code[0];
		curTv1 = loc[1];
		curTv2 = loc[0];
		tv1.setText(code_loc[1]);
		tv2.setText(code_loc[0]);

		updateUI();

		tv1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils1.dialogSpinner(tv1, getActivity(), "", code_loc, new Idelegate() {

					@Override
					public void callBack(Object value, int where) {
						curTv1key = code[(Integer) value];
						curTv1 = loc[(Integer) value];
						updateUI();
					}
				}, 1, true);

			}
		});

		tv2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils1.dialogSpinner(tv2, getActivity(), "", code_loc, new Idelegate() {

					@Override
					public void callBack(Object value, int where) {
						curTv2key = code[(Integer) value];
						curTv2 = loc[(Integer) value];
						updateUI();
					}
				}, 1, true);

			}
		});

	}

	private void updateUI() {

		String img1 = url5d + curTv1key + curTv2key + endStr;
		String img2 = url3m + curTv1key + curTv2key + endStr;
		String img3 = url1y + curTv1key + curTv2key + endStr;
		String img4 = url2y + curTv1key + curTv2key + endStr;

		String des1 = "\n Biểu đồ " + curTv1 + "/" + curTv2 + " 5 ngày\n";
		String des2 = "\nBiểu đồ " + curTv1 + "/" + curTv2 + " 3 tháng\n";
		String des3 = "\nBiểu đồ " + curTv1 + "/" + curTv2 + " 1 năm\n";
		String des4 = "\nBiểu đồ " + curTv1 + "/" + curTv2 + " 2 năm\n";

		String data = "<html><head><style>img{width:100%%}</style></head><body><div><img src='" + img1 + "'/></div>" + des1 + "<div><img src='" + img2 + "'/></div>" + des2
				+ "<div><img src='" + img3 + "'/></div>" + des3 + "<div><img src='" + img4 + "'/></div>" + des4 + "</body></html>";

		ViewUtils.loadDataWv(wv, data);

	}

}
