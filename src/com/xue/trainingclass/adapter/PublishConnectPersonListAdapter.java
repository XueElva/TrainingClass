package com.xue.trainingclass.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xue.trainingclass.activity.R;
import com.xue.trainingclass.view.CommonDialog;
import com.xue.trainingclass.view.CommonDialog.onComDialogBtnClick;

public class PublishConnectPersonListAdapter extends BaseAdapter {
	private ArrayList<HashMap<String, String>> mConnectInfoList;
	private Context mContext;

	public PublishConnectPersonListAdapter(
			ArrayList<HashMap<String, String>> list, Context context) {
		this.mConnectInfoList = list;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mConnectInfoList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mConnectInfoList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(mContext, R.layout.item_connectperson, null);
		TextView name = (TextView) convertView.findViewById(R.id.name);
		TextView phone = (TextView) convertView.findViewById(R.id.phone);
		ImageView delete = (ImageView) convertView.findViewById(R.id.delete);

		name.setText(mConnectInfoList.get(position).get("name"));
		phone.setText(mConnectInfoList.get(position).get("phone"));
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new CommonDialog(mContext).Init(mContext.getResources()
						.getString(R.string.deleteConnectPerson),
						new onComDialogBtnClick() {

							@Override
							public void onCancelClick() {
								// TODO Auto-generated method stub

							}

							@Override
							public void onConfirmClick() {
								// TODO Auto-generated method stub
								mConnectInfoList.remove(position);
								notifyDataSetChanged();
							}

						});
			}
		});
		return convertView;
	}

}
