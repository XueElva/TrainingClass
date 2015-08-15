package com.xue.trainingclass.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

//�γ�
public class Publish_Class extends BmobObject implements Serializable{
private String authorId; //����id
private String classTypeId; //�γ̶�Ӧid
private String title; //����
private String toward; //�������
private String beginTime; //����ʱ��
private String cycle; //�γ�����
private Boolean isSummerClass; //�Ƿ������ڰ�
private Boolean isWeekendClass; //�Ƿ�����ĩ��
private String price; //�۸�
private String imgList; //ͼƬ�����У�json���������[{"imgName":""},{"imgName":""},{"imgName":""}]
private String address; //��ַ
private String cityId; //����id
private String connectInfo; //��ϵ����Ϣ��json���������[{"name":"","phone":""},{"name":"","phone":""},{"name":"","phone":""}]
private String storeBriefIntroduction; //��ҵ���
private String classDescription; //�γ̼��
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
