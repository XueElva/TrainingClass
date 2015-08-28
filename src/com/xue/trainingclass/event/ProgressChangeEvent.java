package com.xue.trainingclass.event;

import android.R.integer;

public class ProgressChangeEvent {

	public String imgName;
	public int progress;
	public  ProgressChangeEvent(String imgName,int progress){
		this.progress=progress;
		this.imgName=imgName;
	};
}
