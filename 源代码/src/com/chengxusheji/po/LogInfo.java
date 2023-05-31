package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class LogInfo {
    /*日志id*/
    private Integer logId;
    public Integer getLogId(){
        return logId;
    }
    public void setLogId(Integer logId){
        this.logId = logId;
    }

    /*日志类型*/
    @NotEmpty(message="日志类型不能为空")
    private String logType;
    public String getLogType() {
        return logType;
    }
    public void setLogType(String logType) {
        this.logType = logType;
    }

    /*日志内容*/
    @NotEmpty(message="日志内容不能为空")
    private String logContent;
    public String getLogContent() {
        return logContent;
    }
    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    /*日志时间*/
    @NotEmpty(message="日志时间不能为空")
    private String logTime;
    public String getLogTime() {
        return logTime;
    }
    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonLogInfo=new JSONObject(); 
		jsonLogInfo.accumulate("logId", this.getLogId());
		jsonLogInfo.accumulate("logType", this.getLogType());
		jsonLogInfo.accumulate("logContent", this.getLogContent());
		jsonLogInfo.accumulate("logTime", this.getLogTime().length()>19?this.getLogTime().substring(0,19):this.getLogTime());
		return jsonLogInfo;
    }}