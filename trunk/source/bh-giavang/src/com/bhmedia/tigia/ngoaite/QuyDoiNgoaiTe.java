package com.bhmedia.tigia.ngoaite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhmedia.tigia.HomeActivity;
import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.object.TiGiaOj;
import com.bhmedia.tigia.utils.Utils1;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.Mlog;

@SuppressLint("ValidFragment")
public class QuyDoiNgoaiTe extends MyFragment implements Idelegate, OnClickListener {
	protected static final int WHERE_TVFROM = 0;

	TextView created, location_name, tvfrom, tvTo;

	EditText edFrom, edTo;
	BaseObject curOj = new BaseObject();
	String date="";
	String[] dataBankName; // ten ngan hang
	String[] dataBankKey; // key ngan hang cho hashmap
	String dataBankCurKey = "";
	String[] dataChildName;
	int sellFromPosition = 0;
	int sellToPosition = 0;
	ArrayList<BaseObject> curOjs = new ArrayList<BaseObject>();
	HashMap<String, ArrayList<BaseObject>> map;
	ArrayList<String> dataFrom = new ArrayList<String>();
	ArrayList<String> dataTo = new ArrayList<String>();

	public QuyDoiNgoaiTe(HashMap<String, ArrayList<BaseObject>> map,String date) {
		this.map = map;
		this.date=date;
	}

	private void updateSpinner() {
		curOjs = map.get(dataBankCurKey);
		
		BaseObject mb=curOjs.get(0);
		BaseObject oj =new BaseObject();
		
		for (String key : TiGiaOj.keys) {
			oj.set(key, mb.get(key));
		}
		
		oj.set(TiGiaOj.CODE, "VND");
		oj.set(TiGiaOj.NAME, "VietNamDong");
		oj.set(TiGiaOj.BUY, "1");
		oj.set(TiGiaOj.SELL, "1");
		oj.set(TiGiaOj.TRANSFER, "1");
		
		curOjs.add(oj);
		
		dataChildName = new String[curOjs.size()];
		
		sellFromPosition = 0;
		sellToPosition = 0;
		
		for (int i = 0; i < curOjs.size(); i++) {
			dataChildName[i] = curOjs.get(i).get(TiGiaOj.NAME)+"("+curOjs.get(i).get(TiGiaOj.CODE)+")";
			if("USD".equalsIgnoreCase(curOjs.get(i).get(TiGiaOj.CODE)))
				sellFromPosition=i;
			if("VND".equalsIgnoreCase(curOjs.get(i).get(TiGiaOj.CODE)))
				sellToPosition=i;
			
			
			
		}

		

		tvfrom.setText(curOjs.get(sellFromPosition).get(TiGiaOj.NAME));
		tvTo.setText(curOjs.get(sellToPosition).get(TiGiaOj.NAME));
		updateData(3);

	}

	boolean isClickFrom = false;
	boolean isClickTo = false;

	private void updateData(int where) {
		
		Mlog.T("updateData -where="+where);
		double buyFrom = curOjs.get(sellFromPosition).getDouble(TiGiaOj.SELL);
		double buyto = curOjs.get(sellToPosition).getDouble(TiGiaOj.SELL);
		double vledFrom;
		double vledTo;
		String txt="";
		try {
			if (where == 3) {
				if (edFrom.getText().toString().trim().length() != 0)
					where = 0;
				else if (edTo.getText().toString().trim().length() != 0)
					where = 1;
			}

			if (where == 0) {
				txt=getEdText(edFrom);
				vledFrom = Double.parseDouble(txt);
				vledTo = buyFrom * vledFrom / buyto;
				edTo.setText(Utils1.double2String(vledTo));
			}

			if (where == 1) {
				txt=getEdText(edTo);
				vledTo = Double.parseDouble(txt);
				vledFrom = buyto * vledTo / buyFrom;
				edFrom.setText(Utils1.double2String(vledFrom));
			}
		} catch (Exception e) {
			Mlog.E("" + e.toString());
			if(where==0){
				edTo.setText(R.string.dulieukhonghople);
				if(txt.length()==0) {
					edFrom.getText().clear();
				}
			}
			else if(where==1){
				edFrom.setText(R.string.dulieukhonghople);
				if(txt.length()==0) {
					edFrom.getText().clear();
				}
			}
			
			
		}

	}

	private String getEdText(EditText edFrom2) {
		String a=edFrom2.getText().toString().trim();
		 return a;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.quydoingoaite, container, false);
		return v;
	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		created = (TextView) v.findViewById(R.id.created);
		location_name = (TextView) v.findViewById(R.id.location_name);
		tvfrom = (TextView) v.findViewById(R.id.tvfrom);
		edFrom = (EditText) v.findViewById(R.id.edFrom);
		tvTo = (TextView) v.findViewById(R.id.tvTo);
		edTo = (EditText) v.findViewById(R.id.edTo);
		((TextView) getView().findViewById(R.id.tv_title)).setText(R.string.quydoingoaite);

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
		
		created.setText(date);
		
		Set<String> titles = map.keySet();
		dataBankKey = titles.toArray(new String[0]);
		dataBankName = new String[titles.size()];
		dataBankCurKey = dataBankKey[0];// ban dau la thang dau tien
		int dem = 0;
		for (String string : titles) {
			ArrayList<BaseObject> ojs = map.get(string);
			if (ojs.size() > 0) {
				dataBankName[dem] = ojs.get(0).get(TiGiaOj.BANKNAME);
			}

			dem++;

		}

		location_name.setText(dataBankName[0]);

		updateSpinner();

		OnTouchListener listener = new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent arg1) {
				switch (v.getId()) {
				case R.id.edFrom:
					isClickFrom = true;
					isClickTo = false;
					
					if(edFrom.getText().toString().equalsIgnoreCase(getString(R.string.dulieukhonghople)))
						edFrom.setText("");
					break;

				case R.id.edTo:
					isClickFrom = false;
					isClickTo = true;
					if(edTo.getText().toString().equalsIgnoreCase(getString(R.string.dulieukhonghople)))
						edTo.setText("");
					break;


				default:
					break;
				}
				
				return false;
			}
		};
		
		edFrom.setOnTouchListener(listener);
		edTo.setOnTouchListener(listener);
		// edFrom.setOnClickListener(this);
		// edTo.setOnClickListener(this);
		tvfrom.setOnClickListener(this);
		tvTo.setOnClickListener(this);
		location_name.setOnClickListener(this);

		edFrom.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				if (isClickFrom)
				{
					
					updateData(0);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub

			}
		});

		edTo.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (isClickTo)
					updateData(1);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public void callBack(Object value, int where) {
		switch (where) {

		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.edFrom:
			isClickFrom = true;
			isClickTo = false;
			break;

		case R.id.edTo:
			isClickFrom = false;
			isClickTo = true;
			break;

		case R.id.tvfrom:

			Utils1.dialogSpinner(tvfrom, getActivity(), "", dataChildName, new Idelegate() {

				@Override
				public void callBack(Object value, int where) {

					sellFromPosition = (Integer) value;
					updateData(3);
				}
			}, WHERE_TVFROM, true);

			break;

		case R.id.tvTo:

			Utils1.dialogSpinner(tvTo, getActivity(), "", dataChildName, new Idelegate() {

				@Override
				public void callBack(Object value, int where) {

					sellToPosition = (Integer) value;
					updateData(3);
				}
			}, WHERE_TVFROM, true);

			break;

		case R.id.location_name:
			Utils1.dialogSpinner(location_name, getActivity(), "", dataBankName, new Idelegate() {

				@Override
				public void callBack(Object value, int where) {

					dataBankCurKey = dataBankKey[(Integer) value];
					updateSpinner();
				}
			}, 1, true);
			break;

		default:
			break;
		}
	}

}
