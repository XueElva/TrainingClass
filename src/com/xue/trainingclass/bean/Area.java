package com.xue.trainingclass.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

//地区
public class Area extends BmobObject implements Serializable{
private String cityName; //省/城市名称
private String cityId; //对应id
private String level;  //等级 level=1/2/3列
private String parentId; //上一列对应id

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
