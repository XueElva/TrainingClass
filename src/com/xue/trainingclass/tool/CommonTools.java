package com.xue.trainingclass.tool;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xue.trainingclass.activity.LoginActivity;
import com.xue.trainingclass.activity.R;
import com.xue.trainingclass.event.FinishEvent;

import de.greenrobot.event.EventBus;

public class CommonTools {
	/**
	 * 提示请先登录
	 * 
	 * @param context
	 */
	public static void loginFirst(final Context context) {
		AlertDialog dialog;
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(R.drawable.ic_launcher);
		builder.setTitle(context.getResources().getString(R.string.prompt));
		builder.setMessage(context.getResources()
				.getString(R.string.loginFirst));
		builder.setPositiveButton(
				context.getResources().getString(R.string.login),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						context.startActivity(new Intent(context,
								LoginActivity.class));
						EventBus.getDefault().post(new FinishEvent());
					}
				});

		builder.setNegativeButton(
				context.getResources().getString(R.string.cancel),
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
		dialog = builder.create();
		dialog.show();
	}

	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	public static Dialog LoadingDialog;

	public static Dialog createLoadingDialog(Context context) {

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(context.getResources().getString(R.string.loading));// 设置加载信息

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

		loadingDialog.setCancelable(true);// 不可以用“返回键”取�?
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
		LoadingDialog = loadingDialog;
		return loadingDialog;
	}

	public static void cancleDialog() {
		try {
			LoadingDialog.cancel();
		} catch (Exception e) {
		}
	}

	/**
	 * 缩小图片到指定大小
	 * 
	 * @param bitmap
	 * @return
	 */
	private Bitmap scaleBitmap(Bitmap bitmap, int size) {
		Bitmap scaledBitmap;
		float scaledHeightPercent = size / bitmap.getHeight();
		float scaledWidthPercent = size / bitmap.getWidth();
		Matrix matrix = new Matrix();
		matrix.postScale(scaledWidthPercent, scaledHeightPercent);

		scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, true);
		return scaledBitmap;
	}

	/**
	 * 生成图片名称的json [{"imgName":""},{"imgName":""},{"imgName":""}]
	 * 
	 * @param imgList
	 * @return
	 */
	public static  String getImgListJSON(ArrayList<String> imgList) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for (int i = 0; i < imgList.size(); i++) {
			sb.append("{\"imgName\":\"" + imgList.get(i) + "\"}");
			if (i != (imgList.size() - 1)) {
				sb.append(",");
			}
		}
		sb.append("]");

		return sb.toString();
	}

	/**
	 * 得到联系人列表 JSON串
	 * [{"name":"","phone":""},{"name":"","phone":""},{"name":"","phone":""}]
	 * 
	 * @return
	 */
	public static  String getConnectInfoJSON(
			ArrayList<HashMap<String, String>> connectInfoList) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");

		for (int i = 0; i < connectInfoList.size(); i++) {
			sb.append("{\"name\":\"" + connectInfoList.get(i).get("name")
					+ "\",\"phone\":\"" + connectInfoList.get(i).get("phone") + "\"}");
			if (i != (connectInfoList.size() - 1)) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
