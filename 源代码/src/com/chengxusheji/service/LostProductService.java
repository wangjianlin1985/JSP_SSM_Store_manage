package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Product;
import com.chengxusheji.po.Employee;
import com.chengxusheji.po.LostProduct;

import com.chengxusheji.mapper.LostProductMapper;
@Service
public class LostProductService {

	@Resource LostProductMapper lostProductMapper;
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

    /*添加丢失物品记录*/
    public void addLostProduct(LostProduct lostProduct) throws Exception {
    	lostProductMapper.addLostProduct(lostProduct);
    }

    /*按照查询条件分页查询丢失物品记录*/
    public ArrayList<LostProduct> queryLostProduct(Product productObj,String lostTime,Employee employeeObj,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != productObj && productObj.getProductId()!= null && productObj.getProductId()!= 0)  where += " and t_lostProduct.productObj=" + productObj.getProductId();
    	if(!lostTime.equals("")) where = where + " and t_lostProduct.lostTime like '%" + lostTime + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null  && !employeeObj.getEmployeeNo().equals(""))  where += " and t_lostProduct.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	int startIndex = (currentPage-1) * this.rows;
    	return lostProductMapper.queryLostProduct(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<LostProduct> queryLostProduct(Product productObj,String lostTime,Employee employeeObj) throws Exception  { 
     	String where = "where 1=1";
    	if(null != productObj && productObj.getProductId()!= null && productObj.getProductId()!= 0)  where += " and t_lostProduct.productObj=" + productObj.getProductId();
    	if(!lostTime.equals("")) where = where + " and t_lostProduct.lostTime like '%" + lostTime + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_lostProduct.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	return lostProductMapper.queryLostProductList(where);
    }

    /*查询所有丢失物品记录*/
    public ArrayList<LostProduct> queryAllLostProduct()  throws Exception {
        return lostProductMapper.queryLostProductList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Product productObj,String lostTime,Employee employeeObj) throws Exception {
     	String where = "where 1=1";
    	if(null != productObj && productObj.getProductId()!= null && productObj.getProductId()!= 0)  where += " and t_lostProduct.productObj=" + productObj.getProductId();
    	if(!lostTime.equals("")) where = where + " and t_lostProduct.lostTime like '%" + lostTime + "%'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_lostProduct.employeeObj='" + employeeObj.getEmployeeNo() + "'";
        recordNumber = lostProductMapper.queryLostProductCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取丢失物品记录*/
    public LostProduct getLostProduct(int lostId) throws Exception  {
        LostProduct lostProduct = lostProductMapper.getLostProduct(lostId);
        return lostProduct;
    }

    /*更新丢失物品记录*/
    public void updateLostProduct(LostProduct lostProduct) throws Exception {
        lostProductMapper.updateLostProduct(lostProduct);
    }

    /*删除一条丢失物品记录*/
    public void deleteLostProduct (int lostId) throws Exception {
        lostProductMapper.deleteLostProduct(lostId);
    }

    /*删除多条丢失物品信息*/
    public int deleteLostProducts (String lostIds) throws Exception {
    	String _lostIds[] = lostIds.split(",");
    	for(String _lostId: _lostIds) {
    		lostProductMapper.deleteLostProduct(Integer.parseInt(_lostId));
    	}
    	return _lostIds.length;
    }
}
