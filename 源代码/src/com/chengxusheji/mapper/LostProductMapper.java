package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.LostProduct;

public interface LostProductMapper {
	/*添加丢失物品信息*/
	public void addLostProduct(LostProduct lostProduct) throws Exception;

	/*按照查询条件分页查询丢失物品记录*/
	public ArrayList<LostProduct> queryLostProduct(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有丢失物品记录*/
	public ArrayList<LostProduct> queryLostProductList(@Param("where") String where) throws Exception;

	/*按照查询条件的丢失物品记录数*/
	public int queryLostProductCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条丢失物品记录*/
	public LostProduct getLostProduct(int lostId) throws Exception;

	/*更新丢失物品记录*/
	public void updateLostProduct(LostProduct lostProduct) throws Exception;

	/*删除丢失物品记录*/
	public void deleteLostProduct(int lostId) throws Exception;

}
