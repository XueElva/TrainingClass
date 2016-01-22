package com.xue.trainingclass.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.xu.view.ExpandedGridView;
import com.xu.view.ExpandedListView;
import com.xue.trainingclass.adapter.PublishConnectPersonListAdapter;
import com.xue.trainingclass.adapter.PublishImgListAdapter;
import com.xue.trainingclass.bean.Area;
import com.xue.trainingclass.bean.Constant;
import com.xue.trainingclass.bean.Publish_Class;
import com.xue.trainingclass.bean.User;
import com.xue.trainingclass.event.ProgressChangeEvent;
import com.xue.trainingclass.event.UploadSuccess;
import com.xue.trainingclass.tool.CommonTools;
import com.xue.trainingclass.view.CommonDialog;
import com.xue.trainingclass.view.CommonDialog.onComDialogBtnClick;
import com.xue.trainingclass.view.GalleryActivity;

import de.greenrobot.event.EventBus;

public class PublishActivity extends Activity {

	private static final int REQUEST_PICK_PHOTO = 111;
	private TextView mBack, mClassType, mStoreIntroduction;
	private EditText mTitle, mToward, mClassBeginTime, mCycle, mPrice,
			mClassDescription, mAddr;
	private CheckBox mSummerClass, mWeekendClass;
	private TextView mBtnAddImg, mBtnAddConnectPerson;
	private ExpandedGridView mImgGridView;
	private ArrayList<String> mImgList = new ArrayList<String>(); //图片本地路径
	private ArrayList<HashMap<String, String>> mImgCloudList=new ArrayList<HashMap<String, String>>(); //图片名称和云路径
	private PublishImgListAdapter mImgListAdapter;

	private ExpandedListView mPersonLV;
	private PublishConnectPersonListAdapter mPersonListAdapter;
	private ArrayList<HashMap<String, String>> mConnectInfoList = new ArrayList<HashMap<String, String>>();

	private Spinner mProvince, mCity1, mCity2;
	private ArrayAdapter<String> mProvinceAdapter, mCity1Adapter,
			mCity2Adapter;
	private String[] mProvinces, mCitys1, mCitys2; // 用于显示
	private ArrayList<Area> mProvinceList, mCity1List, mCity2List;
	private Button mPublish;
	LinearLayout.LayoutParams listviewLP;
	// 最终要保存的数据
	private Publish_Class mClassInfo;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_publish);

		mClassInfo = new Publish_Class();
		Intent intent = getIntent();
		mClassInfo.setClassTypeId(intent.getStringExtra("classId"));
		initView();
		mClassType.setText("类别：" + intent.getStringExtra("className"));
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
		mImgGridView = (ExpandedGridView) findViewById(R.id.imgGV);
		mAddr = (EditText) findViewById(R.id.address);
		mProvince = (Spinner) findViewById(R.id.Spin_province);
		mCity1 = (Spinner) findViewById(R.id.Spin_city);
		mCity2 = (Spinner) findViewById(R.id.Spin_city2);
		mPersonLV = (ExpandedListView) findViewById(R.id.connectPersonLV);
		mPublish = (Button) findViewById(R.id.publish);

		User currentUser=BmobUser.getCurrentUser(PublishActivity.this, User.class);
		if(currentUser.getStoreIntroduction()!=null && !currentUser.getStoreIntroduction().equals("")){
			mStoreIntroduction.setText(currentUser.getStoreIntroduction());
		}else{
			mStoreIntroduction.setHint("您暂时还没有商家简介，请在用户详情中完善");
		}
		mPersonListAdapter = new PublishConnectPersonListAdapter(
				mConnectInfoList, PublishActivity.this);
		mPersonLV.setAdapter(mPersonListAdapter);

		mImgListAdapter = new PublishImgListAdapter(mImgList,
				PublishActivity.this);
		mImgGridView.setAdapter(mImgListAdapter);
		mImgGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				new CommonDialog(PublishActivity.this).Init(getResources()
						.getString(R.string.deleteImg),
						new onComDialogBtnClick() {

							@Override
							public void onCancelClick() {
								// TODO Auto-generated method stub

							}

							@Override
							public void onConfirmClick() {
								// TODO Auto-generated method stub
								mImgList.remove(position);
								mImgListAdapter.notifyDataSetChanged();
							}

						});
				return false;
			}
		});
		mPublish.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				publish();

			}
		});

		getAreas(1, null); // 获取省份
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

		// 添加图片
		mBtnAddImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mImgList.size() == 3) {
					Toast.makeText(PublishActivity.this,
							getResources().getString(R.string.imgIsFull), 1)
							.show();
				} else {
					Intent intent = new Intent(PublishActivity.this,
							GalleryActivity.class);
					// 最多选取图片数
					intent.putExtra(Constant.EXTRA_PHOTO_LIMIT,
							3 - mImgList.size());
					startActivityForResult(intent, REQUEST_PICK_PHOTO);
				}

			}
		});

		// 添加联系人
		mBtnAddConnectPerson.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PublishActivity.this,
						AddConnectPersonDialog.class);
				startActivityForResult(intent, 5);
			}
		});

	}

	/**
	 * 发布
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
		if (title.equals("")) {
			Toast.makeText(PublishActivity.this,
					getResources().getString(R.string.inputTitle), 1).show();
		} else if (beginTime.equals("")) {
			Toast.makeText(PublishActivity.this,
					getResources().getString(R.string.inputClassBeginTime), 1)
					.show();
		} else if (cycle.equals("")) {
			Toast.makeText(PublishActivity.this,
					getResources().getString(R.string.inputCycle), 1).show();
		} else if (address.equals("")) {
			Toast.makeText(PublishActivity.this,
					getResources().getString(R.string.inputAddress), 1).show();
		} else if (mClassInfo.getCityId() == null) {
			Toast.makeText(PublishActivity.this,
					getResources().getString(R.string.selectCity), 1).show();
		} else if (mConnectInfoList.size() == 0) {
			Toast.makeText(PublishActivity.this,
					getResources().getString(R.string.addConnectPerson), 1)
					.show();
		} else {
			mClassInfo.setTitle(title);
			mClassInfo.setToward(toward);
			mClassInfo.setBeginTime(beginTime);
			mClassInfo.setCycle(cycle);
			mClassInfo.setIsSummerClass(mSummerClass.isChecked());
			mClassInfo.setIsWeekendClass(mWeekendClass.isChecked());
			mClassInfo.setPrice(price);
			mClassInfo.setClassDescription(classDescription);
			mClassInfo.setAddress(address);
			mClassInfo.setConnectInfo(CommonTools
					.getConnectInfoJSON(mConnectInfoList));

			CommonTools.createLoadingDialog(PublishActivity.this).show();
			if(mImgList.size()>0){
				uploadImgs();
			}else{
				//发布
				doPublish();
			}

		

		}
	}

	private void doPublish(){
		User currentUser=BmobUser.getCurrentUser(PublishActivity.this, User.class);
		mClassInfo.setAuthorId(currentUser.getObjectId());
		mClassInfo.setStoreName(currentUser.getStoreName());
		mClassInfo.setImgList(CommonTools.getImgListJSON(mImgCloudList));
		mClassInfo.save(PublishActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				CommonTools.cancleDialog();
				Toast.makeText(PublishActivity.this,
						getResources().getString(R.string.publishSucceed),
						1).show();

				finish();
			}

			@Override
			public void onFailure(int arg0, String arg1) {
				CommonTools.cancleDialog();
				Toast.makeText(PublishActivity.this,
						getResources().getString(R.string.publishFail), 1)
						.show();
			}
		});
	}
	private void uploadImgs(){
		Intent intent=new Intent(PublishActivity.this,ProgressDialog.class);
		startActivity(intent);
		BmobProFile.getInstance(PublishActivity.this).upload(mImgList.get(0), new UploadListener() {
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onSuccess(String fileName, String url, BmobFile file) {
				// TODO Auto-generated method stub
				HashMap<String, String> img=new HashMap<String, String>();
				img.put("imgName", fileName);
				img.put("imgUrl", file.getUrl());
				mImgCloudList.add(img);
				mImgList.remove(0);
				EventBus.getDefault().post(new UploadSuccess());
				if(mImgList.size()>0){
					uploadImgs();
				}else{
					//发布
					doPublish();
				}
			}
			
			@Override
			public void onProgress(int arg0) {
				EventBus.getDefault().post(new ProgressChangeEvent(getImgName(mImgList.get(0)),arg0));
				
			}
		});
	}
	
	private String getImgName(String path){
		String name;
		String[] names=path.split("/");
		name=names[names.length-1];
		return name;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_PICK_PHOTO) {
			ArrayList<String> imgs = data
					.getStringArrayListExtra(Constant.EXTRA_PHOTO_PATHS);
			if (imgs != null && imgs.size() > 0) {
				mImgList.addAll(imgs);
				mImgListAdapter.notifyDataSetChanged();
			}
		} else if (resultCode == com.xue.trainingclass.bean.Constant.ADD_CONNECTPERSON
				&& data != null) {
			HashMap<String, String> person = new HashMap<String, String>();
			person.put("name", data.getStringExtra("name"));
			person.put("phone", data.getStringExtra("phone"));
			mConnectInfoList.add(person);

			mPersonListAdapter.notifyDataSetChanged();
			listviewLP = new LayoutParams(LayoutParams.FILL_PARENT,
					mConnectInfoList.size() * 100);
			mPersonLV.setLayoutParams(listviewLP);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 获取对应列的地区列表
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

		// 执行查询方法
		query.findObjects(PublishActivity.this, new FindListener<Area>() {
			@Override
			public void onSuccess(List<Area> object) {
				// TODO Auto-generated method stub
				switch (level) {
				case 1: // 省
					mProvinceList = (ArrayList<Area>) object;
					mProvinces = getNameList(mProvinceList);
					// if (mProvinceAdapter == null) {
					mProvinceAdapter = new ArrayAdapter<String>(
							PublishActivity.this, R.layout.item_spinner,
							mProvinces);
					mProvince.setAdapter(mProvinceAdapter);
					// } else {
					// mProvinceAdapter.notifyDataSetChanged();
					// }

					break;
				case 2: // city1
					mCity1List = (ArrayList<Area>) object;
					mCitys1 = getNameList(mCity1List);
					// if (mCity1Adapter == null) {
					mCity1Adapter = new ArrayAdapter<String>(
							PublishActivity.this, R.layout.item_spinner,
							mCitys1);
					mCity1.setAdapter(mCity1Adapter);
					// } else {
					// mCity1Adapter.notifyDataSetChanged();
					// }

					break;
				case 3: // city2
					mCity2List = (ArrayList<Area>) object;
					mCitys2 = getNameList(mCity2List);
					// if (mCity2Adapter == null) {
					mCity2Adapter = new ArrayAdapter<String>(
							PublishActivity.this, R.layout.item_spinner,
							mCitys2);
					mCity2.setAdapter(mCity2Adapter);
					// } else {
					// mCity2Adapter.notifyDataSetChanged();
					// }

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

	// 得到要显示的区域名称
	private String[] getNameList(ArrayList<Area> list) {
		String[] names = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			names[i] = list.get(i).getCityName();
		}
		return names;
	}
}
