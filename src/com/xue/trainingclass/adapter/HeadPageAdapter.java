package com.xue.trainingclass.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class HeadPageAdapter extends PagerAdapter {
	View view = null;
	List<View> viewList;
	// List<HashMap<String, String>> maps;
	Context context;

	public HeadPageAdapter(List<View> viewList, Context context) {
		// TODO Auto-generated constructor stub
		this.viewList = viewList;
		this.context = context;
	}

	public List<View> getContentList() {
		return this.viewList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return viewList.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return super.getPageTitle(position);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		try {
			if (viewList.size() > 0) {
				container.removeView(viewList.get(position));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {

		container.addView(viewList.get(position));

		return viewList.get(position);
	}
}
