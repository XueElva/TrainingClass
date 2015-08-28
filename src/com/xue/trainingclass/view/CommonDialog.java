package com.xue.trainingclass.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.xue.trainingclass.activity.R;

public class CommonDialog {
	Context context;
	Dialog dialog;

	public CommonDialog(Context context) {
		this.context = context;
		dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	public void Init( String content,
			final onComDialogBtnClick clickListener) {
		View view = View.inflate(context, R.layout.dialog_common, null);
		TextView tv_content = (TextView) view
				.findViewById(R.id.tv_comdialog_content);
		tv_content.setText(content);
		Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				clickListener.onCancelClick();
			}
		});

		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				clickListener.onConfirmClick();
			}
		});
		dialog.setContentView(view, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		dialog.show();

	}

	public void show() {
		dialog.show();
	}

	public void disMiss() {
		dialog.dismiss();
	}

	public interface onComDialogBtnClick {
		void onCancelClick();

		void onConfirmClick();
	}
}
