package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;

import com.chengxusheji.po.Admin;
import com.chengxusheji.po.Manager;

import com.chengxusheji.mapper.ManagerMapper;
@Service
public class ManagerService {

	@Resource ManagerMapper managerMapper;
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

    /*添加管理员记录*/
    public void addManager(Manager manager) throws Exception {
    	managerMapper.addManager(manager);
    }

    /*按照查询条件分页查询管理员记录*/
    public ArrayList<Manager> queryManager(String managerUserName,String name,String birthday,String telephone,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!managerUserName.equals("")) where = where + " and t_manager.managerUserName like '%" + managerUserName + "%'";
    	if(!name.equals("")) where = where + " and t_manager.name like '%" + name + "%'";
    	if(!birthday.equals("")) where = where + " and t_manager.birthday like '%" + birthday + "%'";
    	if(!telephone.equals("")) where = where + " and t_manager.telephone like '%" + telephone + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return managerMapper.queryManager(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Manager> queryManager(String managerUserName,String name,String birthday,String telephone) throws Exception  { 
     	String where = "where 1=1";
    	if(!managerUserName.equals("")) where = where + " and t_manager.managerUserName like '%" + managerUserName + "%'";
    	if(!name.equals("")) where = where + " and t_manager.name like '%" + name + "%'";
    	if(!birthday.equals("")) where = where + " and t_manager.birthday like '%" + birthday + "%'";
    	if(!telephone.equals("")) where = where + " and t_manager.telephone like '%" + telephone + "%'";
    	return managerMapper.queryManagerList(where);
    }

    /*查询所有管理员记录*/
    public ArrayList<Manager> queryAllManager()  throws Exception {
        return managerMapper.queryManagerList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String managerUserName,String name,String birthday,String telephone) throws Exception {
     	String where = "where 1=1";
    	if(!managerUserName.equals("")) where = where + " and t_manager.managerUserName like '%" + managerUserName + "%'";
    	if(!name.equals("")) where = where + " and t_manager.name like '%" + name + "%'";
    	if(!birthday.equals("")) where = where + " and t_manager.birthday like '%" + birthday + "%'";
    	if(!telephone.equals("")) where = where + " and t_manager.telephone like '%" + telephone + "%'";
        recordNumber = managerMapper.queryManagerCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取管理员记录*/
    public Manager getManager(String managerUserName) throws Exception  {
        Manager manager = managerMapper.getManager(managerUserName);
        return manager;
    }

    /*更新管理员记录*/
    public void updateManager(Manager manager) throws Exception {
        managerMapper.updateManager(manager);
    }

    /*删除一条管理员记录*/
    public void deleteManager (String managerUserName) throws Exception {
        managerMapper.deleteManager(managerUserName);
    }

    /*删除多条管理员信息*/
    public int deleteManagers (String managerUserNames) throws Exception {
    	String _managerUserNames[] = managerUserNames.split(",");
    	for(String _managerUserName: _managerUserNames) {
    		managerMapper.deleteManager(_managerUserName);
    	}
    	return _managerUserNames.length;
    }
    
    
	/*保存业务逻辑错误信息字段*/
	private String errMessage;
	public String getErrMessage() { return this.errMessage; }
	
	/*验证用户登录*/
	public boolean checkLogin(Admin admin) throws Exception { 
		Manager db_manager = (Manager) managerMapper.getManager(admin.getUsername());
		if(db_manager == null) { 
			this.errMessage = " 账号不存在 ";
			System.out.print(this.errMessage);
			return false;
		} else if( !db_manager.getPassword().equals(admin.getPassword())) {
			this.errMessage = " 密码不正确! ";
			System.out.print(this.errMessage);
			return false;
		}
		
		return true;
	}
}
