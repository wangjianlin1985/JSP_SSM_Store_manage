package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class LostProduct {
    /*记录id*/
    private Integer lostId;
    public Integer getLostId(){
        return lostId;
    }
    public void setLostId(Integer lostId){
        this.lostId = lostId;
    }

    /*丢失物品*/
    private Product productObj;
    public Product getProductObj() {
        return productObj;
    }
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }

    /*丢失数量*/
    @NotNull(message="必须输入丢失数量")
    private Integer lostNumber;
    public Integer getLostNumber() {
        return lostNumber;
    }
    public void setLostNumber(Integer lostNumber) {
        this.lostNumber = lostNumber;
    }

    /*丢失时间*/
    @NotEmpty(message="丢失时间不能为空")
    private String lostTime;
    public String getLostTime() {
        return lostTime;
    }
    public void setLostTime(String lostTime) {
        this.lostTime = lostTime;
    }

    /*丢失地点*/
    @NotEmpty(message="丢失地点不能为空")
    private String lostPlace;
    public String getLostPlace() {
        return lostPlace;
    }
    public void setLostPlace(String lostPlace) {
        this.lostPlace = lostPlace;
    }

    /*总价值*/
    @NotNull(message="必须输入总价值")
    private Float productMoney;
    public Float getProductMoney() {
        return productMoney;
    }
    public void setProductMoney(Float productMoney) {
        this.productMoney = productMoney;
    }

    /*操作员*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonLostProduct=new JSONObject(); 
		jsonLostProduct.accumulate("lostId", this.getLostId());
		jsonLostProduct.accumulate("productObj", this.getProductObj().getProductName());
		jsonLostProduct.accumulate("productObjPri", this.getProductObj().getProductId());
		jsonLostProduct.accumulate("lostNumber", this.getLostNumber());
		jsonLostProduct.accumulate("lostTime", this.getLostTime().length()>19?this.getLostTime().substring(0,19):this.getLostTime());
		jsonLostProduct.accumulate("lostPlace", this.getLostPlace());
		jsonLostProduct.accumulate("productMoney", this.getProductMoney());
		jsonLostProduct.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonLostProduct.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		return jsonLostProduct;
    }}