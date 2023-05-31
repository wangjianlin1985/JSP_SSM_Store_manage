package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.MemberService;
import com.chengxusheji.po.Member;

//Member管理控制层
@Controller
@RequestMapping("/Member")
public class MemberController extends BaseController {

    /*业务层对象*/
    @Resource MemberService memberService;

	@InitBinder("member")
	public void initBinderMember(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("member.");
	}
	/*跳转到添加Member视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Member());
		return "Member_add";
	}

	/*客户端ajax方式提交添加会员信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Member member, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		if(memberService.getMember(member.getMemberUserName()) != null) {
			message = "会员名已经存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
        memberService.addMember(member);
        message = "会员添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询会员信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String memberUserName,String name,String birthDate,String telephone,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (memberUserName == null) memberUserName = "";
		if (name == null) name = "";
		if (birthDate == null) birthDate = "";
		if (telephone == null) telephone = "";
		if(rows != 0)memberService.setRows(rows);
		List<Member> memberList = memberService.queryMember(memberUserName, name, birthDate, telephone, page);
	    /*计算总的页数和总的记录数*/
	    memberService.queryTotalPageAndRecordNumber(memberUserName, name, birthDate, telephone);
	    /*获取到总的页码数目*/
	    int totalPage = memberService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = memberService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Member member:memberList) {
			JSONObject jsonMember = member.getJsonObject();
			jsonArray.put(jsonMember);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询会员信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Member> memberList = memberService.queryAllMember();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Member member:memberList) {
			JSONObject jsonMember = new JSONObject();
			jsonMember.accumulate("memberUserName", member.getMemberUserName());
			jsonMember.accumulate("name", member.getName());
			jsonArray.put(jsonMember);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询会员信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String memberUserName,String name,String birthDate,String telephone,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (memberUserName == null) memberUserName = "";
		if (name == null) name = "";
		if (birthDate == null) birthDate = "";
		if (telephone == null) telephone = "";
		List<Member> memberList = memberService.queryMember(memberUserName, name, birthDate, telephone, currentPage);
	    /*计算总的页数和总的记录数*/
	    memberService.queryTotalPageAndRecordNumber(memberUserName, name, birthDate, telephone);
	    /*获取到总的页码数目*/
	    int totalPage = memberService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = memberService.getRecordNumber();
	    request.setAttribute("memberList",  memberList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("memberUserName", memberUserName);
	    request.setAttribute("name", name);
	    request.setAttribute("birthDate", birthDate);
	    request.setAttribute("telephone", telephone);
		return "Member/member_frontquery_result"; 
	}

     /*前台查询Member信息*/
	@RequestMapping(value="/{memberUserName}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable String memberUserName,Model model,HttpServletRequest request) throws Exception {
		/*根据主键memberUserName获取Member对象*/
        Member member = memberService.getMember(memberUserName);

        request.setAttribute("member",  member);
        return "Member/member_frontshow";
	}

	/*ajax方式显示会员修改jsp视图页*/
	@RequestMapping(value="/{memberUserName}/update",method=RequestMethod.GET)
	public void update(@PathVariable String memberUserName,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键memberUserName获取Member对象*/
        Member member = memberService.getMember(memberUserName);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonMember = member.getJsonObject();
		out.println(jsonMember.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新会员信息*/
	@RequestMapping(value = "/{memberUserName}/update", method = RequestMethod.POST)
	public void update(@Validated Member member, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			memberService.updateMember(member);
			message = "会员更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "会员更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除会员信息*/
	@RequestMapping(value="/{memberUserName}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String memberUserName,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  memberService.deleteMember(memberUserName);
	            request.setAttribute("message", "会员删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "会员删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条会员记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String memberUserNames,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = memberService.deleteMembers(memberUserNames);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出会员信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String memberUserName,String name,String birthDate,String telephone, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(memberUserName == null) memberUserName = "";
        if(name == null) name = "";
        if(birthDate == null) birthDate = "";
        if(telephone == null) telephone = "";
        List<Member> memberList = memberService.queryMember(memberUserName,name,birthDate,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Member信息记录"; 
        String[] headers = { "会员名","姓名","性别","出生日期","联系电话","会员备注"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<memberList.size();i++) {
        	Member member = memberList.get(i); 
        	dataset.add(new String[]{member.getMemberUserName(),member.getName(),member.getGender(),member.getBirthDate(),member.getTelephone(),member.getMemberMemo()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Member.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
