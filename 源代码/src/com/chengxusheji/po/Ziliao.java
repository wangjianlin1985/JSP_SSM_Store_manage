package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Ziliao {
    /*资料id*/
    private Integer ziliaoId;
    public Integer getZiliaoId(){
        return ziliaoId;
    }
    public void setZiliaoId(Integer ziliaoId){
        this.ziliaoId = ziliaoId;
    }

    /*标题*/
    @NotEmpty(message="标题不能为空")
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*描述*/
    @NotEmpty(message="描述不能为空")
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*资料文件*/
    private String ziliaoFile;
    public String getZiliaoFile() {
        return ziliaoFile;
    }
    public void setZiliaoFile(String ziliaoFile) {
        this.ziliaoFile = ziliaoFile;
    }

    /*添加时间*/
    @NotEmpty(message="添加时间不能为空")
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonZiliao=new JSONObject(); 
		jsonZiliao.accumulate("ziliaoId", this.getZiliaoId());
		jsonZiliao.accumulate("title", this.getTitle());
		jsonZiliao.accumulate("content", this.getContent());
		jsonZiliao.accumulate("ziliaoFile", this.getZiliaoFile());
		jsonZiliao.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonZiliao;
    }}