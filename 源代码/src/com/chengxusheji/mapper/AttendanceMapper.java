﻿package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Attendance;

public interface AttendanceMapper {
	/*添加考勤信息*/
	public void addAttendance(Attendance attendance) throws Exception;

	/*按照查询条件分页查询考勤记录*/
	public ArrayList<Attendance> queryAttendance(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有考勤记录*/
	public ArrayList<Attendance> queryAttendanceList(@Param("where") String where) throws Exception;

	/*按照查询条件的考勤记录数*/
	public int queryAttendanceCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条考勤记录*/
	public Attendance getAttendance(int attendanceId) throws Exception;

	/*更新考勤记录*/
	public void updateAttendance(Attendance attendance) throws Exception;

	/*删除考勤记录*/
	public void deleteAttendance(int attendanceId) throws Exception;

}
