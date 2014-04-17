package com.bhmedia.tigia.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.bhmedia.tigia.R;
import com.bhmedia.tigia.R.integer;
import com.bhmedia.tigia.object.GiaVangOj;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.DialogUtils;

public class MyDialog {

	;

	public static void dialogChoose(final Idelegate idelegate, Context context, int from) {
		final int where = Defi.whereIdelegate.DIALOGCHOOSE;
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layoutView = inflater.inflate(R.layout.dialog_choose_lichsu, null);
		builder.setView(layoutView);
		final Dialog dialog = builder.create();
		Button quydoigia, lichsugia, bieudo, huy;
		quydoigia = (Button) layoutView.findViewById(R.id.quydoigia);
		lichsugia = (Button) layoutView.findViewById(R.id.lichsugia);
		bieudo = (Button) layoutView.findViewById(R.id.bieudo);
		huy = (Button) layoutView.findViewById(R.id.dialogSupport_cancel);

		quydoigia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				idelegate.callBack(0, where);
				DialogUtils.dismisDialog(dialog);

			}
		});
		lichsugia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				idelegate.callBack(1, where);
				DialogUtils.dismisDialog(dialog);

			}
		});
		bieudo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				idelegate.callBack(2, where);
				DialogUtils.dismisDialog(dialog);

			}
		});

		huy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				idelegate.callBack(2, where);
				DialogUtils.dismisDialog(dialog);
			}
		});

		dialog.show();

	}

	public static void detail(Context context, BaseObject oj) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layoutView = inflater.inflate(R.layout.dialog_sosanh, null);
		builder.setView(layoutView);
		final Dialog dialog = builder.create();

		TextView ban_homnay, ban_homqua, mua_homnay, mua_homqua, title, ssmua, ssban;
		ban_homnay = (TextView) layoutView.findViewById(R.id.ban_homnay);
		ban_homqua = (TextView) layoutView.findViewById(R.id.ban_homqua);
		mua_homnay = (TextView) layoutView.findViewById(R.id.mua_homnay);
		mua_homqua = (TextView) layoutView.findViewById(R.id.mua_homqua);
		title = (TextView) layoutView.findViewById(R.id.title);

		ssmua = (TextView) layoutView.findViewById(R.id.ssmua);
		ssban = (TextView) layoutView.findViewById(R.id.ssban);

		ban_homnay.setText(oj.get(GiaVangOj.SALE));
		ban_homqua.setText(oj.get(GiaVangOj.SALEOLD));
		mua_homnay.setText(oj.get(GiaVangOj.BUY));
		mua_homqua.setText(oj.get(GiaVangOj.BUYOLD));
		title.setText(oj.get(GiaVangOj.GOLD_NAME));
		layoutView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogUtils.dismisDialog(dialog);
				
			}
		});

		try {
			float banhomnay = Float.parseFloat(oj.get(GiaVangOj.SALE));
			float banhomqua = Float.parseFloat(oj.get(GiaVangOj.SALEOLD));
			float muahomnay = Float.parseFloat(oj.get(GiaVangOj.BUY));
			float muahomqua = Float.parseFloat(oj.get(GiaVangOj.BUYOLD));

			if (banhomnay > banhomqua)
				ssban.setText(">");
			else
				ssban.setText("<");

			if (muahomnay > muahomqua)
				ssmua.setText(">");
			else
				ssmua.setText("<");
		} catch (Exception e) {
			e.toString();
		}

		dialog.show();
		dialog.setCanceledOnTouchOutside(true);

	}

}
