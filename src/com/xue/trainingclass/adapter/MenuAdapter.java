package com.xue.trainingclass.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xue.trainingclass.activity.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	ArrayList<HashMap<String, Object>> mMenuList;
	Context context;

	public MenuAdapter(ArrayList<HashMap<String, Object>> menuList,
			Context context) {
		this.mMenuList = menuList;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mMenuList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mMenuList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.item_menu, null);
			vh = new ViewHolder();
			vh.img = (ImageView) convertView.findViewById(R.id.img);
			vh.text = (TextView) convertView.findViewById(R.id.text);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		ImageLoader.getInstance().displayImage(
				(String) mMenuList.get(position).get("imgSrc"), vh.img);

		vh.text.setText((CharSequence) mMenuList.get(position).get("text"));
		int[] color = (int[]) mMenuList.get(position).get("bgColor");
		

		return convertView;
	}

	class ViewHolder {
		ImageView img;
		TextView text;
	}

}
