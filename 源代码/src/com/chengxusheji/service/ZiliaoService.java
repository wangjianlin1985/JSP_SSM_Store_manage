package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Ziliao;

import com.chengxusheji.mapper.ZiliaoMapper;
@Service
public class ZiliaoService {

	@Resource ZiliaoMapper ziliaoMapper;
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

    /*添加资料文件记录*/
    public void addZiliao(Ziliao ziliao) throws Exception {
    	ziliaoMapper.addZiliao(ziliao);
    }

    /*按照查询条件分页查询资料文件记录*/
    public ArrayList<Ziliao> queryZiliao(String title,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_ziliao.title like '%" + title + "%'";
    	if(!addTime.equals("")) where = where + " and t_ziliao.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return ziliaoMapper.queryZiliao(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Ziliao> queryZiliao(String title,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_ziliao.title like '%" + title + "%'";
    	if(!addTime.equals("")) where = where + " and t_ziliao.addTime like '%" + addTime + "%'";
    	return ziliaoMapper.queryZiliaoList(where);
    }

    /*查询所有资料文件记录*/
    public ArrayList<Ziliao> queryAllZiliao()  throws Exception {
        return ziliaoMapper.queryZiliaoList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String title,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_ziliao.title like '%" + title + "%'";
    	if(!addTime.equals("")) where = where + " and t_ziliao.addTime like '%" + addTime + "%'";
        recordNumber = ziliaoMapper.queryZiliaoCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取资料文件记录*/
    public Ziliao getZiliao(int ziliaoId) throws Exception  {
        Ziliao ziliao = ziliaoMapper.getZiliao(ziliaoId);
        return ziliao;
    }

    /*更新资料文件记录*/
    public void updateZiliao(Ziliao ziliao) throws Exception {
        ziliaoMapper.updateZiliao(ziliao);
    }

    /*删除一条资料文件记录*/
    public void deleteZiliao (int ziliaoId) throws Exception {
        ziliaoMapper.deleteZiliao(ziliaoId);
    }

    /*删除多条资料文件信息*/
    public int deleteZiliaos (String ziliaoIds) throws Exception {
    	String _ziliaoIds[] = ziliaoIds.split(",");
    	for(String _ziliaoId: _ziliaoIds) {
    		ziliaoMapper.deleteZiliao(Integer.parseInt(_ziliaoId));
    	}
    	return _ziliaoIds.length;
    }
}
