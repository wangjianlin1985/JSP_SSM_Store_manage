package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.SellCart;

public interface SellCartMapper {
	/*添加销售购物车信息*/
	public void addSellCart(SellCart sellCart) throws Exception;

	/*按照查询条件分页查询销售购物车记录*/
	public ArrayList<SellCart> querySellCart(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有销售购物车记录*/
	public ArrayList<SellCart> querySellCartList(@Param("where") String where) throws Exception;

	/*按照查询条件的销售购物车记录数*/
	public int querySellCartCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条销售购物车记录*/
	public SellCart getSellCart(int sellCartId) throws Exception;

	/*更新销售购物车记录*/
	public void updateSellCart(SellCart sellCart) throws Exception;

	/*删除销售购物车记录*/
	public void deleteSellCart(int sellCartId) throws Exception;

}
