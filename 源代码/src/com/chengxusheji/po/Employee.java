package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Employee {
    /*雇员编号*/
    @NotEmpty(message="雇员编号不能为空")
    private String employeeNo;
    public String getEmployeeNo(){
        return employeeNo;
    }
    public void setEmployeeNo(String employeeNo){
        this.employeeNo = employeeNo;
    }

    /*登录密码*/
    @NotEmpty(message="登录密码不能为空")
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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

    /*雇员照片*/
    private String employeePhoto;
    public String getEmployeePhoto() {
        return employeePhoto;
    }
    public void setEmployeePhoto(String employeePhoto) {
        this.employeePhoto = employeePhoto;
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

    /*邮箱*/
    @NotEmpty(message="邮箱不能为空")
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    /*家庭地址*/
    private String address;
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /*注册时间*/
    private String regTime;
    public String getRegTime() {
        return regTime;
    }
    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonEmployee=new JSONObject(); 
		jsonEmployee.accumulate("employeeNo", this.getEmployeeNo());
		jsonEmployee.accumulate("password", this.getPassword());
		jsonEmployee.accumulate("name", this.getName());
		jsonEmployee.accumulate("gender", this.getGender());
		jsonEmployee.accumulate("birthDate", this.getBirthDate().length()>19?this.getBirthDate().substring(0,19):this.getBirthDate());
		jsonEmployee.accumulate("employeePhoto", this.getEmployeePhoto());
		jsonEmployee.accumulate("telephone", this.getTelephone());
		jsonEmployee.accumulate("email", this.getEmail());
		jsonEmployee.accumulate("address", this.getAddress());
		jsonEmployee.accumulate("regTime", this.getRegTime().length()>19?this.getRegTime().substring(0,19):this.getRegTime());
		return jsonEmployee;
    }}