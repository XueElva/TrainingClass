package com.xue.trainingclass.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

//����
public class Area extends BmobObject implements Serializable{
private String cityName; //ʡ/��������
private String cityId; //��Ӧid
private String level;  //�ȼ� level=1/2/3��
private String parentId; //��һ�ж�Ӧid

public String getCityName() {
	return cityName;
}
public void setCityName(String cityName) {
	this.cityName = cityName;
}
public String getCityId() {
	return cityId;
}
public void setCityId(String cityId) {
	this.cityId = cityId;
}
public String getLevel() {
	return level;
}
public void setLevel(String level) {
	this.level = level;
}
public String getParentId() {
	return parentId;
}
public void setParentId(String parentId) {
	this.parentId = parentId;
}


}
