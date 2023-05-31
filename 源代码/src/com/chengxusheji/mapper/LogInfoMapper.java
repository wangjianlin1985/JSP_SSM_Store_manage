package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.LogInfo;

public interface LogInfoMapper {
	/*添加系统日志信息*/
	public void addLogInfo(LogInfo logInfo) throws Exception;

	/*按照查询条件分页查询系统日志记录*/
	public ArrayList<LogInfo> queryLogInfo(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有系统日志记录*/
	public ArrayList<LogInfo> queryLogInfoList(@Param("where") String where) throws Exception;

	/*按照查询条件的系统日志记录数*/
	public int queryLogInfoCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条系统日志记录*/
	public LogInfo getLogInfo(int logId) throws Exception;

	/*更新系统日志记录*/
	public void updateLogInfo(LogInfo logInfo) throws Exception;

	/*删除系统日志记录*/
	public void deleteLogInfo(int logId) throws Exception;

}
