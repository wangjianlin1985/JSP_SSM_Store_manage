package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class SellCart {
    /*购物车id*/
    private Integer sellCartId;
    public Integer getSellCartId(){
        return sellCartId;
    }
    public void setSellCartId(Integer sellCartId){
        this.sellCartId = sellCartId;
    }

    /*员工*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    /*商品*/
    private Product productObj;
    public Product getProductObj() {
        return productObj;
    }
    public void setProductObj(Product productObj) {
        this.productObj = productObj;
    }

    /*商品数量*/
    @NotNull(message="必须输入商品数量")
    private Integer productCount;
    public Integer getProductCount() {
        return productCount;
    }
    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonSellCart=new JSONObject(); 
		jsonSellCart.accumulate("sellCartId", this.getSellCartId());
		jsonSellCart.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonSellCart.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		jsonSellCart.accumulate("productObj", this.getProductObj().getProductName());
		jsonSellCart.accumulate("productObjPri", this.getProductObj().getProductId());
		jsonSellCart.accumulate("productCount", this.getProductCount());
		return jsonSellCart;
    }}