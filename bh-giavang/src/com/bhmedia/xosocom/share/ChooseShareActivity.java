package com.bhmedia.xosocom.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bhmedia.tigia.R;
import com.bhmedia.xosocom.utils.Defi;
import com.bhmedia.xosocom.utils.Utils1;
import com.telpoo.frame.ui.BaseFragmentActivity;

public class ChooseShareActivity extends BaseFragmentActivity implements OnClickListener {
	String imgPath = "";
	Button shareFb, shareTw, shareEmail, cancel;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.choose_share);
		shareFb = (Button) findViewById(R.id.shareFb);
		shareTw = (Button) findViewById(R.id.shareTw);
		shareEmail = (Button) findViewById(R.id.shareEmail);
		cancel = (Button) findViewById(R.id.cancel);

		shareFb.setOnClickListener(this);
		shareTw.setOnClickListener(this);
		shareEmail.setOnClickListener(this);
		cancel.setOnClickListener(this);
		imgPath = getIntent().getStringExtra("imgPath");
		
//		findViewById(R.id.tro).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				finish();
//				
//			}
//		});

	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		finish();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.shareFb:
			Intent itfb = new Intent(getBaseContext(), ShareFacebook.class);
			itfb.putExtra("path", imgPath);
			startActivity(itfb);
			finish();
			
			break;

		case R.id.shareTw:

			Intent it = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "", null));
			it.putExtra("sms_body", Defi.nameShare);
			try {
				startActivity(it);
			} catch (Exception e) {
				showToast(getString(R.string.sms_no_device));
			}
			
			finish();
			break;

		case R.id.shareEmail:
			
			Utils1.shareEmail(ChooseShareActivity.this, imgPath);
			finish();
			break;

		case R.id.cancel:
			finish();
			break;

		default:
			break;
		}

	}
}


