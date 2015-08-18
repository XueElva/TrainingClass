package com.xue.trainingclass.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class HomePageAdapter extends FragmentStatePagerAdapter {

	private ArrayList<Fragment> mFragmentList;
	private String[] title=new String[]{"培训班","最新资讯","风采展示"};
	
	public HomePageAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	public HomePageAdapter(FragmentManager fm,ArrayList<Fragment> fragmentList) {
		super(fm);
		this.mFragmentList=fragmentList;
	}
	
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return mFragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mFragmentList.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return title[position];
	}

	
	
	

}
