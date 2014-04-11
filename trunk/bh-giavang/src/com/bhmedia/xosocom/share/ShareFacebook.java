package com.bhmedia.xosocom.share;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.bhmedia.tigia.R;
import com.bhmedia.xosocom.utils.Utils1;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.widget.FacebookDialog;
import com.telpoo.bhlib.share.BHShareUtils;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.ui.BaseActivity;
import com.telpoo.frame.utils.Mlog;

public class ShareFacebook extends BaseActivity {
	private static final String URL_PREFIX_FRIENDS = "https://graph.facebook.com/me/friends?access_token=";

	String path = "";
	// private UiLifecycleHelper uiHelper;
	ProgressDialog loadingProgress1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sharefb);
		path = getIntent().getStringExtra("path");
		
		if(path!=null)
		new uploadImg().execute(path);
		
		int type = getIntent().getIntExtra("typeShare", 9);
		String mstatus = getIntent().getStringExtra("status");
		if(mstatus!=null)
		new ManagerFacebook(ShareFacebook.this).shareInBackground(ShareFacebook.this, new Idelegate() {
			
			@Override
			public void callBack(Object value, int where) {
			//	showToast("ok");
				Intent it =new Intent();
				it.putExtra("value", ""+value);
				it.putExtra("where", where);
				setResult(RESULT_OK, it);
			}
		},type);
		


	}

	@Override
	protected void onResume() {
		super.onResume();
		// uiHelper.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// uiHelper.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// uiHelper.onDestroy();
	}

	@Override
	public void onStart() {
		super.onStart();
		// Session.getActiveSession().addCallback(statusCallback);
	}

	@Override
	public void onStop() {
		super.onStop();
		// Session.getActiveSession().removeCallback(statusCallback);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);

		/*
		 * uiHelper.onActivityResult(requestCode, resultCode, data, new
		 * FacebookDialog.Callback() {
		 * 
		 * @Override public void onError(FacebookDialog.PendingCall pendingCall,
		 * Exception error, Bundle data) { Mlog.w("Activity" +
		 * String.format("Error: %s", error.toString())); }
		 * 
		 * @Override public void onComplete( FacebookDialog.PendingCall
		 * pendingCall, Bundle data) { Mlog.I("Activity" + "Success!"); } });
		 */
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Session session = Session.getActiveSession();
		Session.saveSession(session, outState);
		// uiHelper.onSaveInstanceState(outState);
	}

	class uploadImg extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			// showProgressDialog(ShareFacebook.this);
			loadingProgress1 = new ProgressDialog(ShareFacebook.this);
			loadingProgress1.setMessage(getString(R.string.loading));
			loadingProgress1.setCanceledOnTouchOutside(false);
			loadingProgress1.setOnCancelListener(new OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					finish();

				}
			});
			if (loadingProgress1 != null)
				loadingProgress1.show();
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			return BHShareUtils.uploadImgur(params[0], null);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (loadingProgress1 != null)
				loadingProgress1.dismiss();
			if (result == null) {
				showToast("Lỗi kết nối");
				finish();
			}
			ManagerFacebook facebook = new ManagerFacebook(ShareFacebook.this);
			facebook.updateStatus("name", "caption", "description", result, result, ShareFacebook.this, new Idelegate() {

				@Override
				public void callBack(Object value, int where) {
					String msg = "" + value;
					if (msg.length() > 0)
						showToast(msg);
					finish();

				}
			});

			// share();
		}

	}

	private void share() {
		FacebookDialog fbDialog = new FacebookDialog.ShareDialogBuilder(this).setLink("https://developers.facebook.com/android").build();
		// uiHelper.trackPendingDialogCall(fbDialog.present());
	}

	StatusCallback callback = new StatusCallback() {

		@Override
		public void call(Session session, SessionState state, Exception exception) {
			// TODO Auto-generated method stub

		}
	};

	public void onBackPressed() {

		finish();
	};

	public void onClickDone(View v) {
		finish();

	}

}
