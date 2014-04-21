package com.bhmedia.tigia.more;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.bhmedia.tigia.HomeActivity;
import com.bhmedia.tigia.MyFragment;
import com.bhmedia.tigia.R;
import com.bhmedia.tigia.utils.Defi;
import com.bhmedia.tigia.utils.TabId;
import com.telpoo.bhlib.Bhdefi;
import com.telpoo.bhlib.ads.BHAds;
import com.telpoo.bhlib.object.ShareFbOj;
import com.telpoo.bhlib.share.BHShareUtils;
import com.telpoo.bhlib.share.facebook.ShareFacebook;
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
			HomeActivity.getInstance().pushFragments(TabId.THEM, new WvFm("http://m.xoso.com", "Xổ số"), true, null);
			break;
			
		case R.id.thoitiet:
			HomeActivity.getInstance().pushFragments(TabId.THEM, new WvFm("http://app.vietbao.vn/tienich/thoitiet.html", "Thời tiết"), true, null);
			break;
			
		case R.id.lichphatsong:
			HomeActivity.getInstance().pushFragments(TabId.THEM, new WvFm("http://app.vietbao.vn/tienich/lichphatsong.html", "Lịch phát sóng"), true, null);
			break;
			
		case R.id.lichchieurap:
			HomeActivity.getInstance().pushFragments(TabId.THEM, new WvFm("http://app.vietbao.vn/tienich/lichchieuphimrap.html", "Lịch chiếu phim rạp"), true, null);
			break;
			
		case R.id.gopy:
			BHShareUtils.emailSupport(getActivity(), null, "", "");
			break;
			
		case R.id.danhgia:
			BHShareUtils.rating(getActivity());
			break;
			
		case R.id.twitter:
			FileSupport.saveBitmap(FileSupport.drawableToBitmap(getResources().getDrawable(R.drawable.ic_launcher)), "imgShare",
					FileSupport.getCatcheDir(getActivity()).getPath());
			
			
			BHShareUtils.shareTwitter(getActivity(), getContext().getString(R.string.share_twitter_content), FileSupport.getCatcheDir(getActivity()).getPath()+"/imgShare");
			break;
			
		case R.id.facebook:
			
			FileSupport.saveBitmap(FileSupport.drawableToBitmap(getResources().getDrawable(R.drawable.ic_launcher)), "imgShare",
					FileSupport.getCatcheDir(getActivity()).getPath());
			
			
			
			Intent intentFb=new Intent(getActivity(), ShareFacebook.class);
			
			BaseObject ojShare=new BaseObject();
			ojShare.set(ShareFbOj.CAPTION, "caption");
			ojShare.set(ShareFbOj.DESCRIPTION, "DESCRIPTION");
			ojShare.set(ShareFbOj.LINK, "https://play.google.com/store/apps/details?id=vn.com.cmc.vas");
			ojShare.set(ShareFbOj.NAME, "NAME");
			ojShare.set(ShareFbOj.PATH, FileSupport.getCatcheDir(getActivity()).getPath()+"/imgShare");
			intentFb.putExtra("oj-share", ojShare);
			startActivityForResult(intentFb, 122);
			
			break;
			
		default:
			break;
		}

	}
}
