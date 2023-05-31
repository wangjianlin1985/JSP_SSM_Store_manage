package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.BuyInfo;

public interface BuyInfoMapper {
	/*添加商品进货信息*/
	public void addBuyInfo(BuyInfo buyInfo) throws Exception;

	/*按照查询条件分页查询商品进货记录*/
	public ArrayList<BuyInfo> queryBuyInfo(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有商品进货记录*/
	public ArrayList<BuyInfo> queryBuyInfoList(@Param("where") String where) throws Exception;

	/*按照查询条件的商品进货记录数*/
	public int queryBuyInfoCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条商品进货记录*/
	public BuyInfo getBuyInfo(int buyId) throws Exception;

	/*更新商品进货记录*/
	public void updateBuyInfo(BuyInfo buyInfo) throws Exception;

	/*删除商品进货记录*/
	public void deleteBuyInfo(int buyId) throws Exception;

}
