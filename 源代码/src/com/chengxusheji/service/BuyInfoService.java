package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Product;
import com.chengxusheji.po.Suppllier;
import com.chengxusheji.po.Employee;
import com.chengxusheji.po.BuyInfo;

import com.chengxusheji.mapper.BuyInfoMapper;
@Service
public class BuyInfoService {

	@Resource BuyInfoMapper buyInfoMapper;
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

    /*添加商品进货记录*/
    public void addBuyInfo(BuyInfo buyInfo) throws Exception {
    	buyInfoMapper.addBuyInfo(buyInfo);
    }

    /*按照查询条件分页查询商品进货记录*/
    public ArrayList<BuyInfo> queryBuyInfo(Suppllier supplierObj,String buyDate,Employee employeeObj,Product productObj,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != supplierObj && supplierObj.getSupplierId()!= null && supplierObj.getSupplierId()!= 0)  where += " and t_buyInfo.supplierObj=" + supplierObj.getSupplierId();
    	if(!buyDate.equals("")) where = where + " and t_buyInfo.buyDate like '%" + buyDate + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null  && !employeeObj.getEmployeeNo().equals(""))  where += " and t_buyInfo.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(null != productObj && productObj.getProductId()!= null && productObj.getProductId()!= 0)  where += " and t_buyInfo.productObj=" + productObj.getProductId();
    	int startIndex = (currentPage-1) * this.rows;
    	return buyInfoMapper.queryBuyInfo(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<BuyInfo> queryBuyInfo(Suppllier supplierObj,String buyDate,Employee employeeObj,Product productObj) throws Exception  { 
     	String where = "where 1=1";
    	if(null != supplierObj && supplierObj.getSupplierId()!= null && supplierObj.getSupplierId()!= 0)  where += " and t_buyInfo.supplierObj=" + supplierObj.getSupplierId();
    	if(!buyDate.equals("")) where = where + " and t_buyInfo.buyDate like '%" + buyDate + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_buyInfo.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(null != productObj && productObj.getProductId()!= null && productObj.getProductId()!= 0)  where += " and t_buyInfo.productObj=" + productObj.getProductId();
    	return buyInfoMapper.queryBuyInfoList(where);
    }

    /*查询所有商品进货记录*/
    public ArrayList<BuyInfo> queryAllBuyInfo()  throws Exception {
        return buyInfoMapper.queryBuyInfoList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Suppllier supplierObj,String buyDate,Employee employeeObj,Product productObj) throws Exception {
     	String where = "where 1=1";
    	if(null != supplierObj && supplierObj.getSupplierId()!= null && supplierObj.getSupplierId()!= 0)  where += " and t_buyInfo.supplierObj=" + supplierObj.getSupplierId();
    	if(!buyDate.equals("")) where = where + " and t_buyInfo.buyDate like '%" + buyDate + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_buyInfo.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(null != productObj && productObj.getProductId()!= null && productObj.getProductId()!= 0)  where += " and t_buyInfo.productObj=" + productObj.getProductId();
        recordNumber = buyInfoMapper.queryBuyInfoCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取商品进货记录*/
    public BuyInfo getBuyInfo(int buyId) throws Exception  {
        BuyInfo buyInfo = buyInfoMapper.getBuyInfo(buyId);
        return buyInfo;
    }

    /*更新商品进货记录*/
    public void updateBuyInfo(BuyInfo buyInfo) throws Exception {
        buyInfoMapper.updateBuyInfo(buyInfo);
    }

    /*删除一条商品进货记录*/
    public void deleteBuyInfo (int buyId) throws Exception {
        buyInfoMapper.deleteBuyInfo(buyId);
    }

    /*删除多条商品进货信息*/
    public int deleteBuyInfos (String buyIds) throws Exception {
    	String _buyIds[] = buyIds.split(",");
    	for(String _buyId: _buyIds) {
    		buyInfoMapper.deleteBuyInfo(Integer.parseInt(_buyId));
    	}
    	return _buyIds.length;
    }
}
