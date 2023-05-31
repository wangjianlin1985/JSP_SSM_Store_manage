package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.AttendanceState;

import com.chengxusheji.mapper.AttendanceStateMapper;
@Service
public class AttendanceStateService {

	@Resource AttendanceStateMapper attendanceStateMapper;
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

    /*添加考勤状态记录*/
    public void addAttendanceState(AttendanceState attendanceState) throws Exception {
    	attendanceStateMapper.addAttendanceState(attendanceState);
    }

    /*按照查询条件分页查询考勤状态记录*/
    public ArrayList<AttendanceState> queryAttendanceState(int currentPage) throws Exception { 
     	String where = "where 1=1";
    	int startIndex = (currentPage-1) * this.rows;
    	return attendanceStateMapper.queryAttendanceState(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<AttendanceState> queryAttendanceState() throws Exception  { 
     	String where = "where 1=1";
    	return attendanceStateMapper.queryAttendanceStateList(where);
    }

    /*查询所有考勤状态记录*/
    public ArrayList<AttendanceState> queryAllAttendanceState()  throws Exception {
        return attendanceStateMapper.queryAttendanceStateList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber() throws Exception {
     	String where = "where 1=1";
        recordNumber = attendanceStateMapper.queryAttendanceStateCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取考勤状态记录*/
    public AttendanceState getAttendanceState(int stateId) throws Exception  {
        AttendanceState attendanceState = attendanceStateMapper.getAttendanceState(stateId);
        return attendanceState;
    }

    /*更新考勤状态记录*/
    public void updateAttendanceState(AttendanceState attendanceState) throws Exception {
        attendanceStateMapper.updateAttendanceState(attendanceState);
    }

    /*删除一条考勤状态记录*/
    public void deleteAttendanceState (int stateId) throws Exception {
        attendanceStateMapper.deleteAttendanceState(stateId);
    }

    /*删除多条考勤状态信息*/
    public int deleteAttendanceStates (String stateIds) throws Exception {
    	String _stateIds[] = stateIds.split(",");
    	for(String _stateId: _stateIds) {
    		attendanceStateMapper.deleteAttendanceState(Integer.parseInt(_stateId));
    	}
    	return _stateIds.length;
    }
}
