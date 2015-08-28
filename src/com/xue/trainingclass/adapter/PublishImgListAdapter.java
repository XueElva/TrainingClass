package com.xue.trainingclass.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xue.trainingclass.activity.R;
import com.xue.trainingclass.tool.FileSizeUtil;

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

		String fullPath =  mImgList.get(position); //"file://" +
		Bitmap bit = BitmapFactory.decodeFile(fullPath);
		Matrix matrix = new Matrix();
		if (FileSizeUtil.getFileOrFilesSize(fullPath, FileSizeUtil.SIZETYPE_KB) > 500) {
			matrix.postScale(0.2f, 0.2f);
		} else {
			matrix.postScale(0.6f, 0.6f);
		}

		Bitmap bitmap = Bitmap.createBitmap(bit, 0, 0, 70, 70,
				matrix, true);
		BitmapDrawable drawable = new BitmapDrawable(bitmap);
		img.setBackgroundDrawable(drawable);

		return convertView;
	}

}
