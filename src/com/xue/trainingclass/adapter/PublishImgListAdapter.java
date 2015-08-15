package com.xue.trainingclass.adapter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.xue.trainingclass.activity.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PublishImgListAdapter extends BaseAdapter {
	ArrayList<String> mImgList;
	Context mContext;

	public PublishImgListAdapter(ArrayList<String> list, Context context) {
		this.mImgList = list;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImgList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mImgList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(mContext, R.layout.item_img, null);
		ImageView img = (ImageView) convertView.findViewById(R.id.img);
		ImageLoader.getInstance().displayImage(
				"file://" + mImgList.get(position), img);
		return convertView;
	}

}
