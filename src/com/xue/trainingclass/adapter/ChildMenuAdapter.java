package com.xue.trainingclass.adapter;

import java.util.List;

import com.xue.trainingclass.activity.R;
import com.xue.trainingclass.bean.Class;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChildMenuAdapter extends BaseAdapter {
	List<Class> mChildList;
	Context context;
	
	public ChildMenuAdapter(List<Class> list,Context context) {
		this.mChildList=list;
		this.context=context;
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mChildList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mChildList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh=null;
		if(convertView==null){
			convertView=View.inflate(context, R.layout.item_childmenu, null);
			vh=new ViewHolder();
			vh.classname=(TextView) convertView.findViewById(R.id.classname);
			convertView.setTag(vh);
		}else{
			vh=(ViewHolder) convertView.getTag();
		}
		
		vh.classname.setText(mChildList.get(position).getClassname());
		return convertView;
	}

	class ViewHolder{
		TextView classname;
	}
}
