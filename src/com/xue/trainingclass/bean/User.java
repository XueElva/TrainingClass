package com.xue.trainingclass.bean;

import java.util.Date;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser{
private String sex;
private String storeName; //��������
private String city;      //�������У�ʡ�ݱ�ţ����б�ţ�
private Boolean isVIP;    //�Ƿ���vip
private Boolean isSeller;  //�Ƿ����̼�
private Date vipDeadLine;  //vip��ֹ����
private String avatar;  //ͷ��
private String storeIntroduction; //�̼Ҽ��



public String getStoreIntroduction() {
	return storeIntroduction;
}
public void setStoreIntroduction(String storeIntroduction) {
	this.storeIntroduction = storeIntroduction;
}
public String getAvatar() {
	return avatar;
}
public void setAvatar(String avatar) {
	this.avatar = avatar;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getStoreName() {
	return storeName;
}
public void setStoreName(String storeName) {
	this.storeName = storeName;
}
public String getCity() {
	return city;
}
public void setCity(String city) {
	this.city = city;
}
public Boolean getIsVIP() {
	return isVIP;
}
public void setIsVIP(Boolean isVIP) {
	this.isVIP = isVIP;
}
public Boolean getIsSeller() {
	return isSeller;
}
public void setIsSeller(Boolean isSeller) {
	this.isSeller = isSeller;
}
public Date getVipDeadLine() {
	return vipDeadLine;
}
public void setVipDeadLine(Date vipDeadLine) {
	this.vipDeadLine = vipDeadLine;
}
}
