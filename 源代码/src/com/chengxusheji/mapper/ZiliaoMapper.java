package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Ziliao;

public interface ZiliaoMapper {
	/*添加资料文件信息*/
	public void addZiliao(Ziliao ziliao) throws Exception;

	/*按照查询条件分页查询资料文件记录*/
	public ArrayList<Ziliao> queryZiliao(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有资料文件记录*/
	public ArrayList<Ziliao> queryZiliaoList(@Param("where") String where) throws Exception;

	/*按照查询条件的资料文件记录数*/
	public int queryZiliaoCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条资料文件记录*/
	public Ziliao getZiliao(int ziliaoId) throws Exception;

	/*更新资料文件记录*/
	public void updateZiliao(Ziliao ziliao) throws Exception;

	/*删除资料文件记录*/
	public void deleteZiliao(int ziliaoId) throws Exception;

}
