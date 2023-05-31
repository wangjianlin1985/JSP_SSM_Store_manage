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
import com.chengxusheji.service.ManagerService;
import com.chengxusheji.po.Manager;

//Manager管理控制层
@Controller
@RequestMapping("/Manager")
public class ManagerController extends BaseController {

    /*业务层对象*/
    @Resource ManagerService managerService;

	@InitBinder("manager")
	public void initBinderManager(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("manager.");
	}
	/*跳转到添加Manager视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Manager());
		return "Manager_add";
	}

	/*客户端ajax方式提交添加管理员信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Manager manager, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		if(managerService.getManager(manager.getManagerUserName()) != null) {
			message = "管理用户名已经存在！";
			writeJsonResponse(response, success, message);
			return ;
		}
        managerService.addManager(manager);
        message = "管理员添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询管理员信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String managerUserName,String name,String birthday,String telephone,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (managerUserName == null) managerUserName = "";
		if (name == null) name = "";
		if (birthday == null) birthday = "";
		if (telephone == null) telephone = "";
		if(rows != 0)managerService.setRows(rows);
		List<Manager> managerList = managerService.queryManager(managerUserName, name, birthday, telephone, page);
	    /*计算总的页数和总的记录数*/
	    managerService.queryTotalPageAndRecordNumber(managerUserName, name, birthday, telephone);
	    /*获取到总的页码数目*/
	    int totalPage = managerService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = managerService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Manager manager:managerList) {
			JSONObject jsonManager = manager.getJsonObject();
			jsonArray.put(jsonManager);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询管理员信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Manager> managerList = managerService.queryAllManager();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Manager manager:managerList) {
			JSONObject jsonManager = new JSONObject();
			jsonManager.accumulate("managerUserName", manager.getManagerUserName());
			jsonManager.accumulate("name", manager.getName());
			jsonArray.put(jsonManager);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询管理员信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String managerUserName,String name,String birthday,String telephone,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (managerUserName == null) managerUserName = "";
		if (name == null) name = "";
		if (birthday == null) birthday = "";
		if (telephone == null) telephone = "";
		List<Manager> managerList = managerService.queryManager(managerUserName, name, birthday, telephone, currentPage);
	    /*计算总的页数和总的记录数*/
	    managerService.queryTotalPageAndRecordNumber(managerUserName, name, birthday, telephone);
	    /*获取到总的页码数目*/
	    int totalPage = managerService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = managerService.getRecordNumber();
	    request.setAttribute("managerList",  managerList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("managerUserName", managerUserName);
	    request.setAttribute("name", name);
	    request.setAttribute("birthday", birthday);
	    request.setAttribute("telephone", telephone);
		return "Manager/manager_frontquery_result"; 
	}

     /*前台查询Manager信息*/
	@RequestMapping(value="/{managerUserName}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable String managerUserName,Model model,HttpServletRequest request) throws Exception {
		/*根据主键managerUserName获取Manager对象*/
        Manager manager = managerService.getManager(managerUserName);

        request.setAttribute("manager",  manager);
        return "Manager/manager_frontshow";
	}

	/*ajax方式显示管理员修改jsp视图页*/
	@RequestMapping(value="/{managerUserName}/update",method=RequestMethod.GET)
	public void update(@PathVariable String managerUserName,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键managerUserName获取Manager对象*/
        Manager manager = managerService.getManager(managerUserName);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonManager = manager.getJsonObject();
		out.println(jsonManager.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新管理员信息*/
	@RequestMapping(value = "/{managerUserName}/update", method = RequestMethod.POST)
	public void update(@Validated Manager manager, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			managerService.updateManager(manager);
			message = "管理员更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "管理员更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除管理员信息*/
	@RequestMapping(value="/{managerUserName}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable String managerUserName,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  managerService.deleteManager(managerUserName);
	            request.setAttribute("message", "管理员删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "管理员删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条管理员记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String managerUserNames,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = managerService.deleteManagers(managerUserNames);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出管理员信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String managerUserName,String name,String birthday,String telephone, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(managerUserName == null) managerUserName = "";
        if(name == null) name = "";
        if(birthday == null) birthday = "";
        if(telephone == null) telephone = "";
        List<Manager> managerList = managerService.queryManager(managerUserName,name,birthday,telephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Manager信息记录"; 
        String[] headers = { "管理用户名","姓名","性别","出生日期","联系电话","管理备注"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<managerList.size();i++) {
        	Manager manager = managerList.get(i); 
        	dataset.add(new String[]{manager.getManagerUserName(),manager.getName(),manager.getSex(),manager.getBirthday(),manager.getTelephone(),manager.getManagerMemo()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Manager.xls");//filename是下载的xls的名，建议最好用英文 
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
