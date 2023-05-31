package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Product;
import com.chengxusheji.po.Member;
import com.chengxusheji.po.Employee;
import com.chengxusheji.po.Sell;

import com.chengxusheji.mapper.SellMapper;
@Service
public class SellService {

	@Resource SellMapper sellMapper;
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

    /*添加商品销售记录*/
    public void addSell(Sell sell) throws Exception {
    	sellMapper.addSell(sell);
    }

    /*按照查询条件分页查询商品销售记录*/
    public ArrayList<Sell> querySell(String sellNo,Product productObj,Member memberObj,Employee employeeObj,String sellTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!sellNo.equals("")) where = where + " and t_sell.sellNo like '%" + sellNo + "%'";
    	if(null != productObj && productObj.getProductId()!= null && productObj.getProductId()!= 0)  where += " and t_sell.productObj=" + productObj.getProductId();
    	if(null != memberObj &&  memberObj.getMemberUserName() != null  && !memberObj.getMemberUserName().equals(""))  where += " and t_sell.memberObj='" + memberObj.getMemberUserName() + "'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null  && !employeeObj.getEmployeeNo().equals(""))  where += " and t_sell.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!sellTime.equals("")) where = where + " and t_sell.sellTime like '%" + sellTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return sellMapper.querySell(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Sell> querySell(String sellNo,Product productObj,Member memberObj,Employee employeeObj,String sellTime) throws Exception  { 
     	String where = "where 1=1";
    	if(!sellNo.equals("")) where = where + " and t_sell.sellNo like '%" + sellNo + "%'";
    	if(null != productObj && productObj.getProductId()!= null && productObj.getProductId()!= 0)  where += " and t_sell.productObj=" + productObj.getProductId();
    	if(null != memberObj &&  memberObj.getMemberUserName() != null && !memberObj.getMemberUserName().equals(""))  where += " and t_sell.memberObj='" + memberObj.getMemberUserName() + "'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_sell.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!sellTime.equals("")) where = where + " and t_sell.sellTime like '%" + sellTime + "%'";
    	return sellMapper.querySellList(where);
    }

    /*查询所有商品销售记录*/
    public ArrayList<Sell> queryAllSell()  throws Exception {
        return sellMapper.querySellList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String sellNo,Product productObj,Member memberObj,Employee employeeObj,String sellTime) throws Exception {
     	String where = "where 1=1";
    	if(!sellNo.equals("")) where = where + " and t_sell.sellNo like '%" + sellNo + "%'";
    	if(null != productObj && productObj.getProductId()!= null && productObj.getProductId()!= 0)  where += " and t_sell.productObj=" + productObj.getProductId();
    	if(null != memberObj &&  memberObj.getMemberUserName() != null && !memberObj.getMemberUserName().equals(""))  where += " and t_sell.memberObj='" + memberObj.getMemberUserName() + "'";
    	if(null != employeeObj &&  employeeObj.getEmployeeNo() != null && !employeeObj.getEmployeeNo().equals(""))  where += " and t_sell.employeeObj='" + employeeObj.getEmployeeNo() + "'";
    	if(!sellTime.equals("")) where = where + " and t_sell.sellTime like '%" + sellTime + "%'";
        recordNumber = sellMapper.querySellCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取商品销售记录*/
    public Sell getSell(int sellId) throws Exception  {
        Sell sell = sellMapper.getSell(sellId);
        return sell;
    }

    /*更新商品销售记录*/
    public void updateSell(Sell sell) throws Exception {
        sellMapper.updateSell(sell);
    }

    /*删除一条商品销售记录*/
    public void deleteSell (int sellId) throws Exception {
        sellMapper.deleteSell(sellId);
    }

    /*删除多条商品销售信息*/
    public int deleteSells (String sellIds) throws Exception {
    	String _sellIds[] = sellIds.split(",");
    	for(String _sellId: _sellIds) {
    		sellMapper.deleteSell(Integer.parseInt(_sellId));
    	}
    	return _sellIds.length;
    }
}
