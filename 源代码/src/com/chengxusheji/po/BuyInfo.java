package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class BuyInfo {
    /*进货id*/
    private Integer buyId;
    public Integer getBuyId(){
        return buyId;
    }
    public void setBuyId(Integer buyId){
        this.buyId = buyId;
    }

    /*进货商品*/
    private Product productObj;
    public Product getProductObj() {
        return productObj;
    }
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }

    /*供应商*/
    private Suppllier supplierObj;
    public Suppllier getSupplierObj() {
        return supplierObj;
    }
    public void setSupplierObj(Suppllier supplierObj) {
        this.supplierObj = supplierObj;
    }

    /*进货单价*/
    @NotNull(message="必须输入进货单价")
    private Float price;
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    /*进货数量*/
    @NotNull(message="必须输入进货数量")
    private Integer number;
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }

    /*总价格*/
    @NotNull(message="必须输入总价格")
    private Float totalPrice;
    public Float getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /*进货日期*/
    @NotEmpty(message="进货日期不能为空")
    private String buyDate;
    public String getBuyDate() {
        return buyDate;
    }
    public void setBuyDate(String buyDate) {
        this.buyDate = buyDate;
    }

    /*进货备注*/
    private String buyMemo;
    public String getBuyMemo() {
        return buyMemo;
    }
    public void setBuyMemo(String buyMemo) {
        this.buyMemo = buyMemo;
    }

    /*进货员工*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonBuyInfo=new JSONObject(); 
		jsonBuyInfo.accumulate("buyId", this.getBuyId());
		jsonBuyInfo.accumulate("productObj", this.getProductObj().getProductName());
		jsonBuyInfo.accumulate("productObjPri", this.getProductObj().getProductId());
		jsonBuyInfo.accumulate("supplierObj", this.getSupplierObj().getSupplierName());
		jsonBuyInfo.accumulate("supplierObjPri", this.getSupplierObj().getSupplierId());
		jsonBuyInfo.accumulate("price", this.getPrice());
		jsonBuyInfo.accumulate("number", this.getNumber());
		jsonBuyInfo.accumulate("totalPrice", this.getTotalPrice());
		jsonBuyInfo.accumulate("buyDate", this.getBuyDate().length()>19?this.getBuyDate().substring(0,19):this.getBuyDate());
		jsonBuyInfo.accumulate("buyMemo", this.getBuyMemo());
		jsonBuyInfo.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonBuyInfo.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		return jsonBuyInfo;
    }}