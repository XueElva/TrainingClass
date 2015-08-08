package com.xue.trainingclass.adapter;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.datatype.BmobFile;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xue.trainingclass.activity.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {
	List<com.xue.trainingclass.bean.Class> mMenuList;
	Context context;

	public MenuAdapter(List<com.xue.trainingclass.bean.Class> menuList,
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

		BmobFile file = mMenuList.get(position).getIcon();
		if(file!=null){
			ImageLoader.getInstance()
			.displayImage(file.getFileUrl(context), vh.img);
		}
		

		vh.text.setText((CharSequence) mMenuList.get(position).getClassname());

		return convertView;
	}

	class ViewHolder {
		ImageView img;
		TextView text;
	}

}
