package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Suppllier;

public interface SuppllierMapper {
	/*添加供应商信息*/
	public void addSuppllier(Suppllier suppllier) throws Exception;

	/*按照查询条件分页查询供应商记录*/
	public ArrayList<Suppllier> querySuppllier(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有供应商记录*/
	public ArrayList<Suppllier> querySuppllierList(@Param("where") String where) throws Exception;

	/*按照查询条件的供应商记录数*/
	public int querySuppllierCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条供应商记录*/
	public Suppllier getSuppllier(int supplierId) throws Exception;

	/*更新供应商记录*/
	public void updateSuppllier(Suppllier suppllier) throws Exception;

	/*删除供应商记录*/
	public void deleteSuppllier(int supplierId) throws Exception;

}
