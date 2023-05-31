package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.LogInfo;

import com.chengxusheji.mapper.LogInfoMapper;
@Service
public class LogInfoService {

	@Resource LogInfoMapper logInfoMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加系统日志记录*/
    public void addLogInfo(LogInfo logInfo) throws Exception {
    	logInfoMapper.addLogInfo(logInfo);
    }

    /*按照查询条件分页查询系统日志记录*/
    public ArrayList<LogInfo> queryLogInfo(String logType,String logTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!logType.equals("")) where = where + " and t_logInfo.logType like '%" + logType + "%'";
    	if(!logTime.equals("")) where = where + " and t_logInfo.logTime like '%" + logTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return logInfoMapper.queryLogInfo(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<LogInfo> queryLogInfo(String logType,String logTime) throws Exception  { 
     	String where = "where 1=1";
    	if(!logType.equals("")) where = where + " and t_logInfo.logType like '%" + logType + "%'";
    	if(!logTime.equals("")) where = where + " and t_logInfo.logTime like '%" + logTime + "%'";
    	return logInfoMapper.queryLogInfoList(where);
    }

    /*查询所有系统日志记录*/
    public ArrayList<LogInfo> queryAllLogInfo()  throws Exception {
        return logInfoMapper.queryLogInfoList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String logType,String logTime) throws Exception {
     	String where = "where 1=1";
    	if(!logType.equals("")) where = where + " and t_logInfo.logType like '%" + logType + "%'";
    	if(!logTime.equals("")) where = where + " and t_logInfo.logTime like '%" + logTime + "%'";
        recordNumber = logInfoMapper.queryLogInfoCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取系统日志记录*/
    public LogInfo getLogInfo(int logId) throws Exception  {
        LogInfo logInfo = logInfoMapper.getLogInfo(logId);
        return logInfo;
    }

    /*更新系统日志记录*/
    public void updateLogInfo(LogInfo logInfo) throws Exception {
        logInfoMapper.updateLogInfo(logInfo);
    }

    /*删除一条系统日志记录*/
    public void deleteLogInfo (int logId) throws Exception {
        logInfoMapper.deleteLogInfo(logId);
    }

    /*删除多条系统日志信息*/
    public int deleteLogInfos (String logIds) throws Exception {
    	String _logIds[] = logIds.split(",");
    	for(String _logId: _logIds) {
    		logInfoMapper.deleteLogInfo(Integer.parseInt(_logId));
    	}
    	return _logIds.length;
    }
}
