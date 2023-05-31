package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Member;

public interface MemberMapper {
	/*添加会员信息*/
	public void addMember(Member member) throws Exception;

	/*按照查询条件分页查询会员记录*/
	public ArrayList<Member> queryMember(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有会员记录*/
	public ArrayList<Member> queryMemberList(@Param("where") String where) throws Exception;

	/*按照查询条件的会员记录数*/
	public int queryMemberCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条会员记录*/
	public Member getMember(String memberUserName) throws Exception;

	/*更新会员记录*/
	public void updateMember(Member member) throws Exception;

	/*删除会员记录*/
	public void deleteMember(String memberUserName) throws Exception;

}
