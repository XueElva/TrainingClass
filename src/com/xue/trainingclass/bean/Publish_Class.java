package com.xue.trainingclass.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

//课程
public class Publish_Class extends BmobObject implements Serializable{
private String authorId; //作者id
private String classTypeId; //课程对应id
private String title; //标题
private String toward; //面向对象
private String beginTime; //开课时间
private String cycle; //课程周期
private Boolean isSummerClass; //是否是暑期班
private Boolean isWeekendClass; //是否是周末班
private String price; //价格
private String imgList; //图片名称列，json，最多三张[{"imgName":""},{"imgName":""},{"imgName":""}]
private String address; //地址
private String cityId; //区域id
private String connectInfo; //联系人信息，json，最多三张[{"name":"","phone":""},{"name":"","phone":""},{"name":"","phone":""}]
private String storeBriefIntroduction; //企业简介
private String classDescription; //课程简介
public String getAuthorId() {
	return authorId;
}
public void setAuthorId(String authorId) {
	this.authorId = authorId;
}
public String getClassTypeId() {
	return classTypeId;
}
public void setClassTypeId(String classTypeId) {
	this.classTypeId = classTypeId;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getToward() {
	return toward;
}
public void setToward(String toward) {
	this.toward = toward;
}
public String getBeginTime() {
	return beginTime;
}
public void setBeginTime(String beginTime) {
	this.beginTime = beginTime;
}
public String getCycle() {
	return cycle;
}
public void setCycle(String cycle) {
	this.cycle = cycle;
}
public Boolean getIsSummerClass() {
	return isSummerClass;
}
public void setIsSummerClass(Boolean isSummerClass) {
	this.isSummerClass = isSummerClass;
}
public Boolean getIsWeekendClass() {
	return isWeekendClass;
}
public void setIsWeekendClass(Boolean isWeekendClass) {
	this.isWeekendClass = isWeekendClass;
}
public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
}
public String getImgList() {
	return imgList;
}
public void setImgList(String imgList) {
	this.imgList = imgList;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getCityId() {
	return cityId;
}
public void setCityId(String cityId) {
	this.cityId = cityId;
}
public String getConnectInfo() {
	return connectInfo;
}
public void setConnectInfo(String connectInfo) {
	this.connectInfo = connectInfo;
}
public String getStoreBriefIntroduction() {
	return storeBriefIntroduction;
}
public void setStoreBriefIntroduction(String storeBriefIntroduction) {
	this.storeBriefIntroduction = storeBriefIntroduction;
}
public String getClassDescription() {
	return classDescription;
}
public void setClassDescription(String classDescription) {
	this.classDescription = classDescription;
}


}
