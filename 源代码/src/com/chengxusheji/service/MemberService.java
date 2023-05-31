package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Member;

import com.chengxusheji.mapper.MemberMapper;
@Service
public class MemberService {

	@Resource MemberMapper memberMapper;
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

    /*添加会员记录*/
    public void addMember(Member member) throws Exception {
    	memberMapper.addMember(member);
    }

    /*按照查询条件分页查询会员记录*/
    public ArrayList<Member> queryMember(String memberUserName,String name,String birthDate,String telephone,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!memberUserName.equals("")) where = where + " and t_member.memberUserName like '%" + memberUserName + "%'";
    	if(!name.equals("")) where = where + " and t_member.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_member.birthDate like '%" + birthDate + "%'";
    	if(!telephone.equals("")) where = where + " and t_member.telephone like '%" + telephone + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return memberMapper.queryMember(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Member> queryMember(String memberUserName,String name,String birthDate,String telephone) throws Exception  { 
     	String where = "where 1=1";
    	if(!memberUserName.equals("")) where = where + " and t_member.memberUserName like '%" + memberUserName + "%'";
    	if(!name.equals("")) where = where + " and t_member.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_member.birthDate like '%" + birthDate + "%'";
    	if(!telephone.equals("")) where = where + " and t_member.telephone like '%" + telephone + "%'";
    	return memberMapper.queryMemberList(where);
    }

    /*查询所有会员记录*/
    public ArrayList<Member> queryAllMember()  throws Exception {
        return memberMapper.queryMemberList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String memberUserName,String name,String birthDate,String telephone) throws Exception {
     	String where = "where 1=1";
    	if(!memberUserName.equals("")) where = where + " and t_member.memberUserName like '%" + memberUserName + "%'";
    	if(!name.equals("")) where = where + " and t_member.name like '%" + name + "%'";
    	if(!birthDate.equals("")) where = where + " and t_member.birthDate like '%" + birthDate + "%'";
    	if(!telephone.equals("")) where = where + " and t_member.telephone like '%" + telephone + "%'";
        recordNumber = memberMapper.queryMemberCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取会员记录*/
    public Member getMember(String memberUserName) throws Exception  {
        Member member = memberMapper.getMember(memberUserName);
        return member;
    }

    /*更新会员记录*/
    public void updateMember(Member member) throws Exception {
        memberMapper.updateMember(member);
    }

    /*删除一条会员记录*/
    public void deleteMember (String memberUserName) throws Exception {
        memberMapper.deleteMember(memberUserName);
    }

    /*删除多条会员信息*/
    public int deleteMembers (String memberUserNames) throws Exception {
    	String _memberUserNames[] = memberUserNames.split(",");
    	for(String _memberUserName: _memberUserNames) {
    		memberMapper.deleteMember(_memberUserName);
    	}
    	return _memberUserNames.length;
    }
}
