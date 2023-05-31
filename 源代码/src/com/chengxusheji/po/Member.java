package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Member {
    /*会员名*/
    @NotEmpty(message="会员名不能为空")
    private String memberUserName;
    public String getMemberUserName(){
        return memberUserName;
    }
    public void setMemberUserName(String memberUserName){
        this.memberUserName = memberUserName;
    }

    /*姓名*/
    @NotEmpty(message="姓名不能为空")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    @NotEmpty(message="性别不能为空")
    private String gender;
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    /*出生日期*/
    @NotEmpty(message="出生日期不能为空")
    private String birthDate;
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /*联系电话*/
    @NotEmpty(message="联系电话不能为空")
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*住宅地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*会员备注*/
    private String memberMemo;
    public String getMemberMemo() {
        return memberMemo;
    }
    public void setMemberMemo(String memberMemo) {
        this.memberMemo = memberMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonMember=new JSONObject(); 
		jsonMember.accumulate("memberUserName", this.getMemberUserName());
		jsonMember.accumulate("name", this.getName());
		jsonMember.accumulate("gender", this.getGender());
		jsonMember.accumulate("birthDate", this.getBirthDate().length()>19?this.getBirthDate().substring(0,19):this.getBirthDate());
		jsonMember.accumulate("telephone", this.getTelephone());
		jsonMember.accumulate("address", this.getAddress());
		jsonMember.accumulate("memberMemo", this.getMemberMemo());
		return jsonMember;
    }}