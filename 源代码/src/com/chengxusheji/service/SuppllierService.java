package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Suppllier;

import com.chengxusheji.mapper.SuppllierMapper;
@Service
public class SuppllierService {

	@Resource SuppllierMapper suppllierMapper;
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

    /*添加供应商记录*/
    public void addSuppllier(Suppllier suppllier) throws Exception {
    	suppllierMapper.addSuppllier(suppllier);
    }

    /*按照查询条件分页查询供应商记录*/
    public ArrayList<Suppllier> querySuppllier(String supplierName,String supplierLawyer,String supplierTelephone,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!supplierName.equals("")) where = where + " and t_suppllier.supplierName like '%" + supplierName + "%'";
    	if(!supplierLawyer.equals("")) where = where + " and t_suppllier.supplierLawyer like '%" + supplierLawyer + "%'";
    	if(!supplierTelephone.equals("")) where = where + " and t_suppllier.supplierTelephone like '%" + supplierTelephone + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return suppllierMapper.querySuppllier(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Suppllier> querySuppllier(String supplierName,String supplierLawyer,String supplierTelephone) throws Exception  { 
     	String where = "where 1=1";
    	if(!supplierName.equals("")) where = where + " and t_suppllier.supplierName like '%" + supplierName + "%'";
    	if(!supplierLawyer.equals("")) where = where + " and t_suppllier.supplierLawyer like '%" + supplierLawyer + "%'";
    	if(!supplierTelephone.equals("")) where = where + " and t_suppllier.supplierTelephone like '%" + supplierTelephone + "%'";
    	return suppllierMapper.querySuppllierList(where);
    }

    /*查询所有供应商记录*/
    public ArrayList<Suppllier> queryAllSuppllier()  throws Exception {
        return suppllierMapper.querySuppllierList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String supplierName,String supplierLawyer,String supplierTelephone) throws Exception {
     	String where = "where 1=1";
    	if(!supplierName.equals("")) where = where + " and t_suppllier.supplierName like '%" + supplierName + "%'";
    	if(!supplierLawyer.equals("")) where = where + " and t_suppllier.supplierLawyer like '%" + supplierLawyer + "%'";
    	if(!supplierTelephone.equals("")) where = where + " and t_suppllier.supplierTelephone like '%" + supplierTelephone + "%'";
        recordNumber = suppllierMapper.querySuppllierCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取供应商记录*/
    public Suppllier getSuppllier(int supplierId) throws Exception  {
        Suppllier suppllier = suppllierMapper.getSuppllier(supplierId);
        return suppllier;
    }

    /*更新供应商记录*/
    public void updateSuppllier(Suppllier suppllier) throws Exception {
        suppllierMapper.updateSuppllier(suppllier);
    }

    /*删除一条供应商记录*/
    public void deleteSuppllier (int supplierId) throws Exception {
        suppllierMapper.deleteSuppllier(supplierId);
    }

    /*删除多条供应商信息*/
    public int deleteSupplliers (String supplierIds) throws Exception {
    	String _supplierIds[] = supplierIds.split(",");
    	for(String _supplierId: _supplierIds) {
    		suppllierMapper.deleteSuppllier(Integer.parseInt(_supplierId));
    	}
    	return _supplierIds.length;
    }
}
