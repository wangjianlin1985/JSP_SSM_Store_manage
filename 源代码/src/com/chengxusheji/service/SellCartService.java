package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Employee;
import com.chengxusheji.po.Product;
import com.chengxusheji.po.SellCart;

import com.chengxusheji.mapper.SellCartMapper;
@Service
public class SellCartService {

	@Resource SellCartMapper sellCartMapper;
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

    /*添加销售购物车记录*/
    public void addSellCart(SellCart sellCart) throws Exception {
    	sellCartMapper.addSellCart(sellCart);
    }

    /*按照查询条件分页查询销售购物车记录*/
    public ArrayList<SellCart> querySellCart(Employee employeeObj,Product productObj,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null  && !employeeObj.getEmployeeNo().equals(""))  where += " and t_sellCart.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(null != productObj && productObj.getProductId()!= null && productObj.getProductId()!= 0)  where += " and t_sellCart.productObj=" + productObj.getProductId();
    	int startIndex = (currentPage-1) * this.rows;
    	return sellCartMapper.querySellCart(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<SellCart> querySellCart(Employee employeeObj,Product productObj) throws Exception  { 
     	String where = "where 1=1";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_sellCart.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(null != productObj && productObj.getProductId()!= null && productObj.getProductId()!= 0)  where += " and t_sellCart.productObj=" + productObj.getProductId();
    	return sellCartMapper.querySellCartList(where);
    }

    /*查询所有销售购物车记录*/
    public ArrayList<SellCart> queryAllSellCart()  throws Exception {
        return sellCartMapper.querySellCartList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Employee employeeObj,Product productObj) throws Exception {
     	String where = "where 1=1";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_sellCart.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(null != productObj && productObj.getProductId()!= null && productObj.getProductId()!= 0)  where += " and t_sellCart.productObj=" + productObj.getProductId();
        recordNumber = sellCartMapper.querySellCartCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取销售购物车记录*/
    public SellCart getSellCart(int sellCartId) throws Exception  {
        SellCart sellCart = sellCartMapper.getSellCart(sellCartId);
        return sellCart;
    }

    /*更新销售购物车记录*/
    public void updateSellCart(SellCart sellCart) throws Exception {
        sellCartMapper.updateSellCart(sellCart);
    }

    /*删除一条销售购物车记录*/
    public void deleteSellCart (int sellCartId) throws Exception {
        sellCartMapper.deleteSellCart(sellCartId);
    }

    /*删除多条销售购物车信息*/
    public int deleteSellCarts (String sellCartIds) throws Exception {
    	String _sellCartIds[] = sellCartIds.split(",");
    	for(String _sellCartId: _sellCartIds) {
    		sellCartMapper.deleteSellCart(Integer.parseInt(_sellCartId));
    	}
    	return _sellCartIds.length;
    }
}
