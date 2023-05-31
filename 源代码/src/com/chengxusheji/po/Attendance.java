package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Attendance {
    /*考勤id*/
    private Integer attendanceId;
    public Integer getAttendanceId(){
        return attendanceId;
    }
    public void setAttendanceId(Integer attendanceId){
        this.attendanceId = attendanceId;
    }

    /*考勤日期*/
    @NotEmpty(message="考勤日期不能为空")
    private String attendanceDate;
    public String getAttendanceDate() {
        return attendanceDate;
    }
    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    /*考勤员工*/
    private Employee employeeObj;
    public Employee getEmployeeObj() {
        return employeeObj;
    }
    public void setEmployeeObj(Employee employeeObj) {
        this.employeeObj = employeeObj;
    }

    /*考勤结果*/
    private AttendanceState attendanceStateObj;
    public AttendanceState getAttendanceStateObj() {
        return attendanceStateObj;
    }
    public void setAttendanceStateObj(AttendanceState attendanceStateObj) {
        this.attendanceStateObj = attendanceStateObj;
    }

    /*考勤备注*/
    private String attendanceMemo;
    public String getAttendanceMemo() {
        return attendanceMemo;
    }
    public void setAttendanceMemo(String attendanceMemo) {
        this.attendanceMemo = attendanceMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonAttendance=new JSONObject(); 
		jsonAttendance.accumulate("attendanceId", this.getAttendanceId());
		jsonAttendance.accumulate("attendanceDate", this.getAttendanceDate().length()>19?this.getAttendanceDate().substring(0,19):this.getAttendanceDate());
		jsonAttendance.accumulate("employeeObj", this.getEmployeeObj().getName());
		jsonAttendance.accumulate("employeeObjPri", this.getEmployeeObj().getEmployeeNo());
		jsonAttendance.accumulate("attendanceStateObj", this.getAttendanceStateObj().getStateName());
		jsonAttendance.accumulate("attendanceStateObjPri", this.getAttendanceStateObj().getStateId());
		jsonAttendance.accumulate("attendanceMemo", this.getAttendanceMemo());
		return jsonAttendance;
    }}