package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Sell {
    /*记录id*/
    private Integer sellId;
    public Integer getSellId(){
        return sellId;
    }
    public void setSellId(Integer sellId){
        this.sellId = sellId;
    }

    /*订单号*/
    @NotEmpty(message="订单号不能为空")
    private String sellNo;
    public String getSellNo() {
        return sellNo;
    }
    public void setSellNo(String sellNo) {
        this.sellNo = sellNo;
    }

    /*销售商品*/
    private Product productObj;
    public Product getProductObj() {
        return productObj;
    }
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }

    /*销售价格*/
    @NotNull(message="必须输入销售价格")
    private Float price;
    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    /*销售数量*/
    @NotNull(message="必须输入销售数量")
    private Integer number;
    public Integer getNumber() {
        return number;
    }
    public void setNumber(Integer number) {
        this.number = number;
    }

    /*销售总价*/
    @NotNull(message="必须输入销售总价")
    private Float totalPrice;
    public Float getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /*购买会员*/
    private Member memberObj;
    public Member getMemberObj() {
        return memberObj;
    }
    public void setMemberObj(Member memberObj) {
        this.memberObj = memberObj;
    }

    /*销售员工*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    /*销售时间*/
    @NotEmpty(message="销售时间不能为空")
    private String sellTime;
    public String getSellTime() {
        return sellTime;
    }
    public void setSellTime(String sellTime) {
        this.sellTime = sellTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonSell=new JSONObject(); 
		jsonSell.accumulate("sellId", this.getSellId());
		jsonSell.accumulate("sellNo", this.getSellNo());
		jsonSell.accumulate("productObj", this.getProductObj().getProductName());
		jsonSell.accumulate("productObjPri", this.getProductObj().getProductId());
		jsonSell.accumulate("price", this.getPrice());
		jsonSell.accumulate("number", this.getNumber());
		jsonSell.accumulate("totalPrice", this.getTotalPrice());
		jsonSell.accumulate("memberObj", this.getMemberObj().getName());
		jsonSell.accumulate("memberObjPri", this.getMemberObj().getMemberUserName());
		jsonSell.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonSell.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		jsonSell.accumulate("sellTime", this.getSellTime().length()>19?this.getSellTime().substring(0,19):this.getSellTime());
		return jsonSell;
    }}