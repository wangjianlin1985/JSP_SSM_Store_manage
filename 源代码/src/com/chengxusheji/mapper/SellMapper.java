package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Sell;

public interface SellMapper {
	/*添加商品销售信息*/
	public void addSell(Sell sell) throws Exception;

	/*按照查询条件分页查询商品销售记录*/
	public ArrayList<Sell> querySell(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有商品销售记录*/
	public ArrayList<Sell> querySellList(@Param("where") String where) throws Exception;

	/*按照查询条件的商品销售记录数*/
	public int querySellCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条商品销售记录*/
	public Sell getSell(int sellId) throws Exception;

	/*更新商品销售记录*/
	public void updateSell(Sell sell) throws Exception;

	/*删除商品销售记录*/
	public void deleteSell(int sellId) throws Exception;

}
