package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Suppllier {
    /*供应商id*/
    private Integer supplierId;
    public Integer getSupplierId(){
        return supplierId;
    }
    public void setSupplierId(Integer supplierId){
        this.supplierId = supplierId;
    }

    /*供应商名称*/
    @NotEmpty(message="供应商名称不能为空")
    private String supplierName;
    public String getSupplierName() {
        return supplierName;
    }
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /*法人代表*/
    @NotEmpty(message="法人代表不能为空")
    private String supplierLawyer;
    public String getSupplierLawyer() {
        return supplierLawyer;
    }
    public void setSupplierLawyer(String supplierLawyer) {
        this.supplierLawyer = supplierLawyer;
    }

    /*供应商电话*/
    @NotEmpty(message="供应商电话不能为空")
    private String supplierTelephone;
    public String getSupplierTelephone() {
        return supplierTelephone;
    }
    public void setSupplierTelephone(String supplierTelephone) {
        this.supplierTelephone = supplierTelephone;
    }

    /*供应商地址*/
    @NotEmpty(message="供应商地址不能为空")
    private String supplierAddress;
    public String getSupplierAddress() {
        return supplierAddress;
    }
    public void setSupplierAddress(String supplierAddress) {
        this.supplierAddress = supplierAddress;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonSuppllier=new JSONObject(); 
		jsonSuppllier.accumulate("supplierId", this.getSupplierId());
		jsonSuppllier.accumulate("supplierName", this.getSupplierName());
		jsonSuppllier.accumulate("supplierLawyer", this.getSupplierLawyer());
		jsonSuppllier.accumulate("supplierTelephone", this.getSupplierTelephone());
		jsonSuppllier.accumulate("supplierAddress", this.getSupplierAddress());
		return jsonSuppllier;
    }}