package com.xue.trainingclass.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xu.view.PagerSlidingTabStrip;
import com.xue.trainingclass.activity.R;
import com.xue.trainingclass.adapter.HomePageAdapter;

public class HomeFragment extends Fragment implements OnClickListener {

	private TextView mClassify, mArea;
	private EditText mSearch;
	private OnSelectClassListener mListener;
	private PagerSlidingTabStrip mNavigationBar;
	private ViewPager mVP;
	private HomeInfoFragment mInfoFragment;
	private HomeNewsFragment mNewsFragmnet;
	private HomeShowFragment mShowFragment;
	private ArrayList<Fragment> mFragmentList;
	private HomePageAdapter mAdapter;

	// 标头
	private LinearLayout mTitleInfo;
	private RelativeLayout mTitleShow;
	private TextView mTitleNews;
	private ArrayList<View> mTitleList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = View.inflate(getActivity(), R.layout.fragment_home, null);

		init(view);
		return view;
	}

	public HomeFragment() {

	}

	public HomeFragment(OnSelectClassListener listener) {
		this.mListener = listener;
	}

	private void init(View view) {
		mClassify = (TextView) view.findViewById(R.id.classify);
		mArea = (TextView) view.findViewById(R.id.area);
		mSearch = (EditText) view.findViewById(R.id.search);
		mNavigationBar = (PagerSlidingTabStrip) view
				.findViewById(R.id.navigationBar);
		mVP = (ViewPager) view.findViewById(R.id.homeVP);

		mClassify.setOnClickListener(this);

		mInfoFragment = new HomeInfoFragment();
		mNewsFragmnet = new HomeNewsFragment();
		mShowFragment = new HomeShowFragment();
		mFragmentList = new ArrayList<Fragment>();
		mFragmentList.add(mInfoFragment);
		mFragmentList.add(mNewsFragmnet);
		mFragmentList.add(mShowFragment);

		mAdapter = new HomePageAdapter(getFragmentManager(), mFragmentList);
		mVP.setAdapter(mAdapter);
		mNavigationBar.setViewPager(mVP);
		mVP.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {

				for (int i = 0; i < mTitleList.size(); i++) {
					if (i != position) {
						mTitleList.get(i).setVisibility(View.GONE);
					} else {
						mTitleList.get(i).setVisibility(View.VISIBLE);
					}
				}

			
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		mTitleInfo = (LinearLayout) view.findViewById(R.id.titleInfo);
		mTitleShow = (RelativeLayout) view.findViewById(R.id.titleShow);
		mTitleNews = (TextView) view.findViewById(R.id.titleNews);
		mTitleList = new ArrayList<View>();
		mTitleList.add(mTitleInfo);
		mTitleList.add(mTitleNews);
		mTitleList.add(mTitleShow);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.classify: // 打开分类
			mListener.onSelectClass();

			break;

		default:
			break;
		}

	}

	public interface OnSelectClassListener {
		void onSelectClass();
	}

}
