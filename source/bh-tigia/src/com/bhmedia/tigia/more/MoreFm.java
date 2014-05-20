package com.bhmedia.tigia.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.bhmedia.tigia.HomeActivity;
import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigiagoc.R;
import com.bhmedia.tigia.utils.TabId;
import com.telpoo.bhlib.BHUtils;
import com.telpoo.bhlib.object.ShareFbOj;
import com.telpoo.bhlib.share.BHShareUtils;
import com.telpoo.bhlib.share.facebook.ShareFacebook;
import com.telpoo.frame.net.BaseNetSupportBeta;
import com.telpoo.frame.object.BaseObject;
import com.telpoo.frame.utils.FileSupport;

public class MoreFm extends MyFragment implements OnClickListener {

	View xoso, thoitiet, lichphatsong, lichchieurap, gopy, danhgia, email, facebook, twitter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.them, container, false);

	}

	@Override
	public void onViewCreated(View v, Bundle savedInstanceState) {
		super.onViewCreated(v, savedInstanceState);
		xoso = v.findViewById(R.id.xoso);
		thoitiet = v.findViewById(R.id.thoitiet);
		lichphatsong = v.findViewById(R.id.lichphatsong);
		lichchieurap = v.findViewById(R.id.lichchieurap);
		gopy = v.findViewById(R.id.gopy);
		danhgia = v.findViewById(R.id.danhgia);
		email = v.findViewById(R.id.email);
		facebook = v.findViewById(R.id.facebook);
		twitter = v.findViewById(R.id.twitter);

		xoso.setOnClickListener(this);
		thoitiet.setOnClickListener(this);
		lichphatsong.setOnClickListener(this);
		lichchieurap.setOnClickListener(this);
		gopy.setOnClickListener(this);
		danhgia.setOnClickListener(this);
		email.setOnClickListener(this);
		facebook.setOnClickListener(this);
		twitter.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.xoso:
			HomeActivity.getInstance().pushFragments(TabId.THEM, new WvFm("http://m.xoso.com", getString(R.string.xoso)), true, null);
			break;

		case R.id.thoitiet:
			HomeActivity.getInstance().pushFragments(TabId.THEM, new WvFm("http://app.vietbao.vn/tienich/thoitiet.html", getString(R.string.thoitiet)), true, null);
			break;

		case R.id.lichphatsong:
			HomeActivity.getInstance().pushFragments(TabId.THEM, new WvFm("http://app.vietbao.vn/tienich/lichphatsong.html", getString(R.string.lichphatsong)), true, null);
			break;

		case R.id.lichchieurap:
			HomeActivity.getInstance().pushFragments(TabId.THEM, new WvFm("http://app.vietbao.vn/tienich/lichchieuphimrap.html", getString(R.string.lichchieuphimrap)), true, null);
			break;

		case R.id.gopy:
			BHShareUtils.emailSupport(getActivity(),MoreFm.this, null, "Ứng dụng 'Tỉ giá' - Góp ý", "Ý kiến đóng góp của bạn về ứng dụng");
			break;

		case R.id.danhgia:
			BHShareUtils.rating(getActivity());
			break;

		case R.id.twitter:
			
			if(!BaseNetSupportBeta.isNetworkAvailable(getActivity())){
				showToastMessage(R.string.network_not_avaiable);
				return;
			}
			
			FileSupport.saveBitmap(FileSupport.drawableToBitmap(getResources().getDrawable(R.drawable.ic_launcher)), "imgShare", FileSupport.getCatcheDir(getActivity()).getPath());

			BHShareUtils.shareTwitter(getActivity(), getContext().getString(R.string.share_twitter_content), FileSupport.getCatcheDir(getActivity()).getPath() + "/imgShare");
			break;

		case R.id.facebook:
			
			if(!BaseNetSupportBeta.isNetworkAvailable(getActivity())){
				showToastMessage(R.string.network_not_avaiable);
				return;
			}

			FileSupport.saveBitmap(FileSupport.drawableToBitmap(getResources().getDrawable(R.drawable.ic_launcher)), "imgShare", FileSupport.getCatcheDir(getActivity()).getPath());

			Intent intentFb = new Intent(getActivity(), ShareFacebook.class);
			  BaseObject ojShare = new BaseObject();
              ojShare.set(ShareFbOj.CAPTION, "BHMedia");
              ojShare.set(ShareFbOj.DESCRIPTION, "Ứng dụng tỉ giá");
              ojShare.set(ShareFbOj.LINK, BHUtils.linkGooglePlay(getActivity()));
              ojShare.set(ShareFbOj.NAME, "Tỉ giá");
              ojShare.set(ShareFbOj.PATH, FileSupport.getCatcheDir(getActivity()).getPath() + "/imgShare");
              intentFb.putExtra("oj-share", ojShare);
              startActivityForResult(intentFb, 122);

			break;

		case R.id.email:
			BHShareUtils.sentEmail(getActivity(), "", "", getContext().getString(R.string.ungdunggiavangpro_chiase), "Chia sẻ ứng dụng hay: "+BHUtils.linkGooglePlay(getActivity()));
			
			break;

		default:
			break;
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 122) {
			if(data!=null){
				
				String a = data.getStringExtra("value");
				showToast(a);
			}
		}
	}
}
