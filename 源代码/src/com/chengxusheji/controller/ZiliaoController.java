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
import com.chengxusheji.service.ZiliaoService;
import com.chengxusheji.po.Ziliao;

//Ziliao管理控制层
@Controller
@RequestMapping("/Ziliao")
public class ZiliaoController extends BaseController {

    /*业务层对象*/
    @Resource ZiliaoService ziliaoService;

	@InitBinder("ziliao")
	public void initBinderZiliao(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("ziliao.");
	}
	/*跳转到添加Ziliao视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Ziliao());
		return "Ziliao_add";
	}

	/*客户端ajax方式提交添加资料文件信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Ziliao ziliao, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		ziliao.setZiliaoFile(this.handleFileUpload(request, "ziliaoFileFile"));
        ziliaoService.addZiliao(ziliao);
        message = "资料文件添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询资料文件信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String title,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (title == null) title = "";
		if (addTime == null) addTime = "";
		if(rows != 0)ziliaoService.setRows(rows);
		List<Ziliao> ziliaoList = ziliaoService.queryZiliao(title, addTime, page);
	    /*计算总的页数和总的记录数*/
	    ziliaoService.queryTotalPageAndRecordNumber(title, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = ziliaoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = ziliaoService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Ziliao ziliao:ziliaoList) {
			JSONObject jsonZiliao = ziliao.getJsonObject();
			jsonArray.put(jsonZiliao);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询资料文件信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Ziliao> ziliaoList = ziliaoService.queryAllZiliao();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Ziliao ziliao:ziliaoList) {
			JSONObject jsonZiliao = new JSONObject();
			jsonZiliao.accumulate("ziliaoId", ziliao.getZiliaoId());
			jsonZiliao.accumulate("title", ziliao.getTitle());
			jsonArray.put(jsonZiliao);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询资料文件信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String title,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (addTime == null) addTime = "";
		List<Ziliao> ziliaoList = ziliaoService.queryZiliao(title, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    ziliaoService.queryTotalPageAndRecordNumber(title, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = ziliaoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = ziliaoService.getRecordNumber();
	    request.setAttribute("ziliaoList",  ziliaoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("title", title);
	    request.setAttribute("addTime", addTime);
		return "Ziliao/ziliao_frontquery_result"; 
	}

     /*前台查询Ziliao信息*/
	@RequestMapping(value="/{ziliaoId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer ziliaoId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键ziliaoId获取Ziliao对象*/
        Ziliao ziliao = ziliaoService.getZiliao(ziliaoId);

        request.setAttribute("ziliao",  ziliao);
        return "Ziliao/ziliao_frontshow";
	}

	/*ajax方式显示资料文件修改jsp视图页*/
	@RequestMapping(value="/{ziliaoId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer ziliaoId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键ziliaoId获取Ziliao对象*/
        Ziliao ziliao = ziliaoService.getZiliao(ziliaoId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonZiliao = ziliao.getJsonObject();
		out.println(jsonZiliao.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新资料文件信息*/
	@RequestMapping(value = "/{ziliaoId}/update", method = RequestMethod.POST)
	public void update(@Validated Ziliao ziliao, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String ziliaoFileFileName = this.handleFileUpload(request, "ziliaoFileFile");
		if(!ziliaoFileFileName.equals(""))ziliao.setZiliaoFile(ziliaoFileFileName);
		try {
			ziliaoService.updateZiliao(ziliao);
			message = "资料文件更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "资料文件更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除资料文件信息*/
	@RequestMapping(value="/{ziliaoId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer ziliaoId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  ziliaoService.deleteZiliao(ziliaoId);
	            request.setAttribute("message", "资料文件删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "资料文件删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条资料文件记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String ziliaoIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = ziliaoService.deleteZiliaos(ziliaoIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出资料文件信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String title,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(title == null) title = "";
        if(addTime == null) addTime = "";
        List<Ziliao> ziliaoList = ziliaoService.queryZiliao(title,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Ziliao信息记录"; 
        String[] headers = { "资料id","标题","添加时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<ziliaoList.size();i++) {
        	Ziliao ziliao = ziliaoList.get(i); 
        	dataset.add(new String[]{ziliao.getZiliaoId() + "",ziliao.getTitle(),ziliao.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Ziliao.xls");//filename是下载的xls的名，建议最好用英文 
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
