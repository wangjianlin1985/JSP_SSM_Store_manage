package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Employee;

public interface EmployeeMapper {
	/*添加雇员信息*/
	public void addEmployee(Employee employee) throws Exception;

	/*按照查询条件分页查询雇员记录*/
	public ArrayList<Employee> queryEmployee(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有雇员记录*/
	public ArrayList<Employee> queryEmployeeList(@Param("where") String where) throws Exception;

	/*按照查询条件的雇员记录数*/
	public int queryEmployeeCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条雇员记录*/
	public Employee getEmployee(String employeeNo) throws Exception;

	/*更新雇员记录*/
	public void updateEmployee(Employee employee) throws Exception;

	/*删除雇员记录*/
	public void deleteEmployee(String employeeNo) throws Exception;

}
