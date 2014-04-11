package com.bhmedia.xosocom.share;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.bhmedia.tigia.R;
import com.bhmedia.xosocom.utils.Defi;
import com.bhmedia.xosocom.utils.Utils1;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.FeedDialogBuilder;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.telpoo.frame.delegate.Idelegate;
import com.telpoo.frame.utils.Mlog;
import com.telpoo.frame.utils.Utils;

public class ManagerFacebook {
	private Activity mActivity;
	final List<String> PERMISSIONS = Arrays.asList("status_update");


	public ManagerFacebook(Activity mActivity) {
		this.mActivity = mActivity;
	}

	public void updateStatus(final String name, final String caption, final String description, final String link, final String urlPicture, final Activity activity,
			final Idelegate listenerShareFacebook) {
		mActivity = activity;

		// start Facebook Login
		Session.openActiveSession(activity, true, new Session.StatusCallback() {
			// callback when session changes state
			public void call(final Session session, SessionState state, Exception exception) {
				if (session.isOpened()) {
					publishFeedDialog(name, caption, description, link, urlPicture, listenerShareFacebook);

				}

			}
		});
	}

	public void shareInBackground(final Activity activity, final Idelegate listenerShareFacebook,final int type) {

		Session.openActiveSession(activity, true, new Session.StatusCallback() {
			// callback when session changes state
			public void call(final Session session, SessionState state, Exception exception) {
				if (session.isOpened()) {
					//mshare(activity, session, listenerShareFacebook);
					publishFeedShare(listenerShareFacebook,activity,type);

				}

			}
		});

	}
	
	/*
	protected void mshare(final Activity activity, Session session1, final Idelegate listenerShareFacebook) {
		final Bundle params = new Bundle();
		params.putString("name",
				"Ứng dụng xổ số tốt nhất. Hãy vào trang xoso.com để có được những thông tin, phân tích, thống kê, kết quả nhanh nhất, hữu ích nhất, cơ hội trúng xổ số cao." +
						" Download ứng dụng xổ số cho Android : "+Defi.URL_GOOGLE_PLAY
						+". Phiên bản iphone: https://itunes.apple.com/vn/app/xo-so/id505057507?mt=8" +
						". Phiên bản ipad: https://itunes.apple.com/vn/app/xo-so/id505057507?mt=8");
		params.putString("link", Defi.URL_GOOGLE_PLAY);

		if (Session.getActiveSession() != null) {
			NewPermissionsRequest reauthRequest = new Session.NewPermissionsRequest(activity, PERMISSIONS);
			Session.getActiveSession().requestNewPublishPermissions(reauthRequest);
		}

		
		Request request = new Request(Session.getActiveSession(), "/me/feed", params, HttpMethod.POST);

		request.setCallback(new Request.Callback() {

			@Override
			public void onCompleted(Response response) {
				Mlog.T(response.toString());
				listenerShareFacebook.callBack("Posted!" + response.toString(), 0);

				Utils.saveStringSPR(Defi.SPR_SAVE_TIME_SHARE, "" + Calendar.getInstance().getTimeInMillis(), activity);

			}

		});

		request.executeAsync();
		
		
		
	      Request request = Request
                  .newStatusUpdateRequest(Session.getActiveSession(), "12311", null	, null, new Request.Callback() {
                      @Override
                      public void onCompleted(Response response) {
                          Mlog.E("xong");
                      }
                  });
          request.executeAsync();

	}
	
	*/


	public boolean checkPermissions() {
		Session s = Session.getActiveSession();
		if (s != null) {
			return s.getPermissions().contains("publish_actions");
		} else
			return false;
	}

	public void requestPermissions() {
		Session s = Session.getActiveSession();
		if (s != null)
			s.requestNewPublishPermissions(new Session.NewPermissionsRequest(mActivity, PERMISSIONS));
	}

	private void publishFeedShare(final Idelegate listenerShareFacebook,final Activity activity,final int type) {
		Bundle params = new Bundle();
		params.putString("name",
				Defi.nameShare);
		params.putString("link", Defi.URL_GOOGLE_PLAY);
		params.putString("method", "feed");
		Session session = Session.getActiveSession();

		FeedDialogBuilder feedDialogbuilder = new WebDialog.FeedDialogBuilder(mActivity, session, params);
		WebDialog feedDialog = feedDialogbuilder.build();
		feedDialog.setOnCompleteListener(new OnCompleteListener() {

			public void onComplete(Bundle values, FacebookException error) {
				if (error == null) {
					// When the story is posted, echo the success
					// and the post Id.
					final String postId = values.getString("post_id");
					if (postId != null) {
						
						//Utils1.saveTimeShare(""+Calendar.getInstance().getTimeInMillis(), activity,type);
						listenerShareFacebook.callBack("Đăng thành công!", 0);
					} else {
						// User clicked the Cancel button
						listenerShareFacebook.callBack("Publish cancelled", 1);
					}
				} else if (error instanceof FacebookOperationCanceledException) {
					// User clicked the "x" button
					listenerShareFacebook.callBack("Publish cancelled", 2);
				} else {
					// Generic, ex: network error
					listenerShareFacebook.callBack("Error posting story", 3);
				}

				mActivity.finish();
			}

		});

		feedDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// Toast.makeText(mActivity, "1111", 1).show();
				mActivity.finish();

			}
		});

		feedDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				//Toast.makeText(mActivity, "1111-" + arg2, 1).show();
				return false;
			}
		});

		feedDialog.show();
	}

	private void publishFeedDialog(String name, String caption, String description, String link, String urlPicture, final Idelegate listenerShareFacebook) {
		Bundle params = new Bundle();

		params.putString("name",Defi.nameShare);
		params.putString("description", Defi.URL_GOOGLE_PLAY);
		params.putString("link", Defi.URL_GOOGLE_PLAY);
		params.putString("picture", urlPicture);
		params.putString("method", "photos.upload");
		Session session = Session.getActiveSession();

		FeedDialogBuilder feedDialogbuilder = new WebDialog.FeedDialogBuilder(mActivity, session, params);
		WebDialog feedDialog = feedDialogbuilder.build();
		feedDialog.setOnCompleteListener(new OnCompleteListener() {

			public void onComplete(Bundle values, FacebookException error) {
				if (error == null) {
					// When the story is posted, echo the success
					// and the post Id.
					final String postId = values.getString("post_id");
					if (postId != null) {
						listenerShareFacebook.callBack("Posted!", 0);
					} else {
						// User clicked the Cancel button
						listenerShareFacebook.callBack("Publish cancelled", 1);
					}
				} else if (error instanceof FacebookOperationCanceledException) {
					// User clicked the "x" button
					listenerShareFacebook.callBack("Publish cancelled", 2);
				} else {
					// Generic, ex: network error
					listenerShareFacebook.callBack("Error posting story", 3);
				}

				mActivity.finish();
			}

		});

		feedDialog.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				// Toast.makeText(mActivity, "1111", 1).show();
				mActivity.finish();

			}
		});

		feedDialog.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
				//Toast.makeText(mActivity, "1111-" + arg2, 1).show();
				return false;
			}
		});

		feedDialog.show();
	}

}