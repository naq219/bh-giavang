package com.bhmedia.tigia;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.bhmedia.tigia.R;
import com.bhmedia.tigia.giavang.KetquaFm;
import com.bhmedia.tigia.thitruong.TructiepFm;
import com.bhmedia.tigia.utils.TabId;
import com.telpoo.frame.ui.BaseFmActivity;

public class TabActivity extends BaseFmActivity implements OnTabChangeListener {
	protected TabHost mTabHost;
	public TabActivity me;

	public TabActivity() {
		super(TabId.keys, R.id.realtabcontent, "Bấm lần nữa để thoát ứng dụng");
	}// bac thu tao project moi copy qua xem sao :D
	//ok

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		me = TabActivity.this;
		initView(savedInstanceState);

	}

	private void initView(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tabs_layout);

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		mTabHost.setOnTabChangedListener(this);

		initialiseTabHost(savedInstanceState);

	}

	private void initialiseTabHost(Bundle args) {

		for (int i = 0; i < TabId.keys.length; i++) {
			TabHost.TabSpec spec = CreateTabSpec(TabId.keys[i]);
			spec.setIndicator(createTabView(this, TabId.name[i],
					TabId.resource[i]));
			mTabHost.addTab(spec);
		}

		mTabHost.getTabWidget().setStripEnabled(false);
		mTabHost.getTabWidget().setDividerDrawable(null);
		mTabHost.getTabWidget().getChildAt(0).findViewById(R.id.line)
				.setVisibility(View.GONE);

	}

	private TabHost.TabSpec CreateTabSpec(String tab_identifier) {
		TabHost.TabSpec spec = mTabHost.newTabSpec(tab_identifier);
		spec.setContent(new TabHost.TabContentFactory() {
			public View createTabContent(String tag) {
				return findViewById(R.id.realtabcontent);
			}
		});
		return spec;
	}

	protected static View createTabView(final Context context, String tag,
			int resId) {
		View view = LayoutInflater.from(context)
				.inflate(R.layout.tabs_bg, null);
		TextView tv = (TextView) view.findViewById(R.id.tabsText);
		tv.setText(tag);
		tv.setTextColor(Color.GRAY);

		ImageView iv = (ImageView) view.findViewById(R.id.tabsIcon);
		iv.setImageResource(resId);
		return view;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString("tab", mTabHost.getCurrentTabTag());
		super.onSaveInstanceState(outState);
	}

	private TextView txtTab;

	@Override
	public void onTabChanged(String tabId) {
		final int TRANSPARENT_GREY = Color.argb(0, 185, 185, 185);
		final int FILTERED_GREY = Color.argb(500, 2, 109, 225);

		for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
			txtTab = (TextView) mTabHost.getTabWidget().getChildAt(i)
					.findViewById(R.id.tabsText);
			txtTab.setTextColor(Color.GRAY);
			((ImageView) mTabHost.getTabWidget().getChildAt(i)
					.findViewById(R.id.tabsIcon))
					.setColorFilter(TRANSPARENT_GREY);

		}

		int pos = getPostByTabid(tabId);
		TextView txtTab = (TextView) mTabHost.getTabWidget().getChildAt(pos)
				.findViewById(R.id.tabsText);
		txtTab.setTextColor(Color.WHITE);
		((ImageView) mTabHost.getTabWidget().getChildAt(pos)
				.findViewById(R.id.tabsIcon)).setColorFilter(FILTERED_GREY);

		setCurrentTab(tabId);

		if (mStacks.get(tabId).size() == 0) {

			if (tabId.equals(TabId.GIAVANG)) {
				pushFragments(TabId.GIAVANG, new KetquaFm(), true, null);
			} else if (tabId.equals(TabId.THITRUONG)) {
				pushFragments(TabId.THITRUONG, new KetquaFm(), true, null);
			} else if (tabId.equals(TabId.THEM)) {
				pushFragments(TabId.THEM, new More(), true, null);
			} else if (tabId.equals(TabId.NGOAITE)) {
				// pushFragments(TabId.STATS, new ThongkeFm(), true, null);
			} else if (tabId.equals(TabId.TINTUC)) {
				// pushFragments(TabId.UTILS, new TienichFm(), true, null);

			}

		} else {

			pushFragments(tabId, mStacks.get(tabId).lastElement(), false, null);

		}

	}

	private int getPostByTabid(String tabId) {
		if (tabId.equalsIgnoreCase(TabId.GIAVANG))
			return 0;
		if (tabId.equalsIgnoreCase(TabId.NGOAITE))
			return 1;
		if (tabId.equalsIgnoreCase(TabId.THITRUONG))
			return 2;
		if (tabId.equalsIgnoreCase(TabId.TINTUC))
			return 3;
		if (tabId.equalsIgnoreCase(TabId.THEM))
			return 4;
		return 0;
	}

}
