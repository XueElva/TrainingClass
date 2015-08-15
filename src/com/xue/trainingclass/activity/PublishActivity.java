package com.xue.trainingclass.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.ns.mutiphotochoser.constant.Constant;
import com.xue.trainingclass.adapter.ChildMenuAdapter;
import com.xue.trainingclass.adapter.PublishImgListAdapter;
import com.xue.trainingclass.bean.Area;
import com.xue.trainingclass.bean.Class;
import com.xue.trainingclass.bean.Publish_Class;

public class PublishActivity extends Activity {

	private static final int REQUEST_PICK_PHOTO = 111;
	private TextView mBack, mClassType, mStoreIntroduction;
	private EditText mTitle, mToward, mClassBeginTime, mCycle, mPrice,
			mClassDescription, mAddr;
	private CheckBox mSummerClass, mWeekendClass;
	private TextView mBtnAddImg, mBtnAddConnectPerson;
	private GridView mImgGridView;
	private ArrayList<String> mImgList;
	private PublishImgListAdapter mImgListAdapter;
	
	private ListView mPersonLV;
	private Spinner mProvince, mCity1, mCity2;
	private ArrayAdapter<String> mProvinceAdapter, mCity1Adapter,
			mCity2Adapter;
	private String[] mProvinces, mCitys1, mCitys2; // ������ʾ
	private ArrayList<Area> mProvinceList, mCity1List, mCity2List;
	private Button mPublish;

	// ����Ҫ���������
	private Publish_Class mClassInfo;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_publish);

		mClassInfo = new Publish_Class();
		Intent intent = getIntent();
		mClassInfo.setClassTypeId(intent.getStringExtra("classId"));
		initView();
		mClassType.setText("���" + intent.getStringExtra("className"));
	}

	private void initView() {
		mBack = (TextView) findViewById(R.id.back);
		mClassType = (TextView) findViewById(R.id.classType);
		mTitle = (EditText) findViewById(R.id.title);
		mToward = (EditText) findViewById(R.id.toward);
		mClassBeginTime = (EditText) findViewById(R.id.classBeginTime);
		mStoreIntroduction = (TextView) findViewById(R.id.storeIntroduction);
		mCycle = (EditText) findViewById(R.id.cycle);
		mSummerClass = (CheckBox) findViewById(R.id.isSummerClass);
		mWeekendClass = (CheckBox) findViewById(R.id.isWeekendClass);
		mPrice = (EditText) findViewById(R.id.price);
		mClassDescription = (EditText) findViewById(R.id.classDescription);
		mBtnAddImg = (TextView) findViewById(R.id.addImg);
		mBtnAddConnectPerson = (TextView) findViewById(R.id.addConnectPerson);
		mImgGridView = (GridView) findViewById(R.id.imgGV);
		mAddr = (EditText) findViewById(R.id.address);
		mProvince = (Spinner) findViewById(R.id.Spin_province);
		mCity1 = (Spinner) findViewById(R.id.Spin_city);
		mCity2 = (Spinner) findViewById(R.id.Spin_city2);
		mPersonLV = (ListView) findViewById(R.id.connectPersonLV);
		mPublish = (Button) findViewById(R.id.publish);

		mImgListAdapter=new PublishImgListAdapter(mImgList, PublishActivity.this);
		mImgGridView.setAdapter(mImgListAdapter);
		
		mPublish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publish();

			}
		});

		getAreas(1, null); // ��ȡʡ��
		mProvince.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				mClassInfo.setCityId(mProvinceList.get(position).getCityId());
				getAreas(2, mClassInfo.getCityId());

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		mCity1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				mClassInfo.setCityId(mCity1List.get(position).getCityId());
				getAreas(3, mClassInfo.getCityId());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		mCity2.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				mClassInfo.setCityId(mCity2List.get(position).getCityId());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		mBtnAddImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mImgList.size()==3){
					Toast.makeText(PublishActivity.this, getResources().getString(R.string.imgIsFull), 1).show();
				}else{
					Intent intent = new Intent(
							"com.ns.mutiphotochoser.sample.action.CHOSE_PHOTOS");
					// ���ѡȡͼƬ��
					intent.putExtra(Constant.EXTRA_PHOTO_LIMIT, 3-mImgList.size());
					startActivityForResult(intent, REQUEST_PICK_PHOTO);
				}
				
			}
		});

	}

	/**
	 * ����
	 */
	private void publish() {
		String title = mTitle.getText().toString();
		String toward = mToward.getText().toString();
		String beginTime = mClassBeginTime.getText().toString();
		String cycle = mCycle.getText().toString();
		String price = mPrice.getText().toString();
		String classDescription = mClassDescription.getText().toString();
		String address = mAddr.getText().toString();
		String storeIntroduction = mStoreIntroduction.getText().toString();
		

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_PICK_PHOTO) {
			ArrayList<String> imgs = data.getStringArrayListExtra(Constant.EXTRA_PHOTO_PATHS);
			if (imgs != null && imgs.size() > 0) {
				mImgList=imgs;
				mImgListAdapter.notifyDataSetChanged();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * ��ȡ��Ӧ�еĵ����б�
	 * 
	 * @param level
	 * @param parentId
	 */
	private void getAreas(final int level, String parentId) {
		BmobQuery<Area> query = new BmobQuery<Area>();
		query.addWhereEqualTo("level", level);
		if (parentId != null) {
			query.addWhereEqualTo("parentId", parentId);
		}
		query.setLimit(50);

		// ִ�в�ѯ����
		query.findObjects(PublishActivity.this, new FindListener<Area>() {
			@Override
			public void onSuccess(List<Area> object) {
				// TODO Auto-generated method stub
				switch (level) {
				case 1: // ʡ
					mProvinceList = (ArrayList<Area>) object;
					mProvinces = getNameList(mProvinceList);
					if (mProvinceAdapter == null) {
						mProvinceAdapter = new ArrayAdapter<String>(
								PublishActivity.this, R.layout.item_spinner,
								mProvinces);
					}
					mProvinceAdapter.notifyDataSetChanged();
					break;
				case 2: // city1
					mCity1List = (ArrayList<Area>) object;
					mCitys1 = getNameList(mCity1List);
					if (mCity1Adapter == null) {
						mCity1Adapter = new ArrayAdapter<String>(
								PublishActivity.this, R.layout.item_spinner,
								mCitys1);
					}
					mCity1Adapter.notifyDataSetChanged();
					break;
				case 3: // city2
					mCity2List = (ArrayList<Area>) object;
					mCitys2 = getNameList(mCity2List);
					if (mCity2Adapter == null) {
						mCity2Adapter = new ArrayAdapter<String>(
								PublishActivity.this, R.layout.item_spinner,
								mCitys2);
					}
					mCity2Adapter.notifyDataSetChanged();
					break;

				default:
					break;
				}
			}

			@Override
			public void onError(int code, String msg) {

			}
		});
	}

	// �õ�Ҫ��ʾ����������
	private String[] getNameList(ArrayList<Area> list) {
		String[] names = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			names[i] = list.get(i).getCityName();
		}
		return names;
	}
}
