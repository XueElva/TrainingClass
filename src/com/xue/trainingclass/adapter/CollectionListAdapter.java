package com.xue.trainingclass.adapter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.ThumbnailListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xue.trainingclass.activity.R;
import com.xue.trainingclass.bean.CollectionClass;
import com.xue.trainingclass.bean.Publish_Class;
import com.xue.trainingclass.bean.User;
import com.xue.trainingclass.tool.CommonTools;

public class CollectionListAdapter extends BaseAdapter {
	ArrayList<Publish_Class> mClassList;
	ArrayList<CollectionClass> mCollectedList = new ArrayList<CollectionClass>(); // 已收藏的列表
	Context mContext;

	public CollectionListAdapter(ArrayList<Publish_Class> list,
			ArrayList<CollectionClass> collectedList, Context context) {
		this.mClassList = list;
		this.mContext = context;
		this.mCollectedList = collectedList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mClassList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mClassList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	ViewHolder vh;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		vh = null;
		if (convertView == null) {
			convertView = View.inflate(mContext, R.layout.item_newclass, null);
			vh = new ViewHolder();
			vh.className = (TextView) convertView.findViewById(R.id.className);
			vh.time = (TextView) convertView.findViewById(R.id.time);
			vh.classImg = (ImageView) convertView.findViewById(R.id.classImg);
			vh.corpName = (TextView) convertView.findViewById(R.id.corpName);
			vh.address = (TextView) convertView.findViewById(R.id.addr);
			vh.btnShare = (TextView) convertView.findViewById(R.id.share);
			vh.btnCollect = (TextView) convertView.findViewById(R.id.collect);

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		final Publish_Class classInfo = mClassList.get(position);
		vh.className.setText(classInfo.getTitle());
		vh.time.setText(classInfo.getUpdatedAt());
		vh.corpName.setText(classInfo.getStoreName());
		vh.address.setText(classInfo.getAddress());
		vh.btnCollect.setSelected(true);

		try {
			JSONArray imgArray = new JSONArray(classInfo.getImgList());
			if (imgArray.length() > 0) {
				vh.classImg.setVisibility(View.VISIBLE);
				// 得到缩略图
				BmobProFile.getInstance(mContext).submitThumnailTask(
						imgArray.getJSONObject(0).getString("imgName"), 1,
						new ThumbnailListener() {

							@Override
							public void onError(int arg0, String arg1) {
								// TODO Auto-generated method stub
								Log.d("content", "error");
							}

							@Override
							public void onSuccess(String thumbnailName,
									String thumbnailUrl) {
								ImageLoader.getInstance().displayImage(
										thumbnailUrl, vh.classImg);
							}
						});
			} else {
				vh.classImg.setVisibility(View.GONE);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		vh.btnShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 分享

			}
		});

		vh.btnCollect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CommonTools.createLoadingDialog(mContext).show();
					// 取消收藏
					final CollectionClass collection = mCollectedList.get(position);
					collection.delete(mContext, new DeleteListener() {

						@Override
						public void onSuccess() {
							CommonTools.cancleDialog();
							Toast.makeText(
									mContext,
									mContext.getResources().getString(
											R.string.cancelCollectSucceed), 1)
									.show();
							vh.btnCollect.setSelected(false);
							mCollectedList.remove(collection);
						}

						@Override
						public void onFailure(int arg0, String arg1) {
							CommonTools.cancleDialog();
							Toast.makeText(
									mContext,
									mContext.getResources().getString(
											R.string.cancelCollectFail), 1)
									.show();
						}
					});

			}
		});
		return convertView;
	}

	class ViewHolder {
		TextView className, time, corpName, address;
		ImageView classImg;
		TextView btnShare, btnCollect;
	}

}
