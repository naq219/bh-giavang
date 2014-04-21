package com.bhmedia.tigia.giavang;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhmedia.tigia.HomeActivity;
import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.object.GiaVangOj;
import com.bhmedia.tigia.utils.Defi;
import com.bhmedia.tigia.utils.Utils1;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;

@SuppressLint("ValidFragment")
public class QuyDoiGiaVang extends MyFragment implements Idelegate {
	TextView created, location_name, gold_name, luong, buy, sale;
	HashMap<String, ArrayList<BaseObject>> map;
	private String[] keydo;
	private double[] keydoValue = { 1, 10, 26.455d, 26455d, 0.755986667d };
	private int curDovaluePosition = 0;
	EditText ed;
	BaseObject curOj = new BaseObject();

	public QuyDoiGiaVang(HashMap<String, ArrayList<BaseObject>> map) {
		this.map = map;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.quydoigiavang, container, false);
		return v;
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		created = (TextView) v.findViewById(R.id.created);
		location_name = (TextView) v.findViewById(R.id.location_name);
		gold_name = (TextView) v.findViewById(R.id.gold_name);
		luong = (TextView) v.findViewById(R.id.luong);
		buy = (TextView) v.findViewById(R.id.buy);
		sale = (TextView) v.findViewById(R.id.sale);
		keydo = getResources().getStringArray(R.array.arrDo);
		ed = (EditText) v.findViewById(R.id.ed);
		((TextView) getView().findViewById(R.id.tv_title)).setText(R.string.quydoigiavang);

		ImageView iv = (ImageView) getView().findViewById(R.id.btn_top);

		iv.setImageResource(R.drawable.close);
		iv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				HomeActivity.getInstance().popFragments();

			}
		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Utils1.setspinnerHeader(getActivity(), location_name, gold_name, map, this);

		luong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils1.dialogSpinner(luong, getActivity(), "", keydo, new Idelegate() {

					@Override
					public void callBack(Object value, int where) {
						curDovaluePosition = (Integer) value;
						update();

					}
				}, 1, true);

			}
		});
		
		ed.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable paramEditable) {
				update();
				
			}
		});
		
	}

	protected void update() {
		double edText = 0;
		double mua = 0;
		double ban = 0;
		if (ed.getText().toString().length() > 0) {
			try {
				edText = Double.parseDouble(ed.getText().toString());
				mua = Double.parseDouble(curOj.get(GiaVangOj.BUY));
				ban = Double.parseDouble(curOj.get(GiaVangOj.SALE));
				double muavl = mua * edText * keydoValue[curDovaluePosition] * 1000000d;
				double banvl = ban * edText * keydoValue[curDovaluePosition] * 1000000d;

				NumberFormat f = NumberFormat.getInstance();
				f.setGroupingUsed(false);

				String strVal = f.format(muavl);
				String strban = f.format(banvl);

				buy.setText("Mua: " + strVal + " VNĐ");
				sale.setText(getString(R.string.ban_) + strban + " VNĐ");

			} catch (Exception e) {
				Mlog.E(e.toString());
			}

		}
	}

	@Override
	public void callBack(Object value, int where) {
		switch (where) {
		case Defi.whereIdelegate.UTILS1_SPINNERHEAD:
			curOj = (BaseObject) value;

			created.setText(getString(R.string.c_p_nh_t_ng_y_) + curOj.get(GiaVangOj.CREATED).substring(0, 10));
			update();
			break;

		default:
			break;
		}

	}

}
