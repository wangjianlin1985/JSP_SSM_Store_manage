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
import com.chengxusheji.service.LogInfoService;
import com.chengxusheji.po.LogInfo;

//LogInfo管理控制层
@Controller
@RequestMapping("/LogInfo")
public class LogInfoController extends BaseController {

    /*业务层对象*/
    @Resource LogInfoService logInfoService;

	@InitBinder("logInfo")
	public void initBinderLogInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("logInfo.");
	}
	/*跳转到添加LogInfo视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new LogInfo());
		return "LogInfo_add";
	}

	/*客户端ajax方式提交添加系统日志信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated LogInfo logInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        logInfoService.addLogInfo(logInfo);
        message = "系统日志添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询系统日志信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String logType,String logTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (logType == null) logType = "";
		if (logTime == null) logTime = "";
		if(rows != 0)logInfoService.setRows(rows);
		List<LogInfo> logInfoList = logInfoService.queryLogInfo(logType, logTime, page);
	    /*计算总的页数和总的记录数*/
	    logInfoService.queryTotalPageAndRecordNumber(logType, logTime);
	    /*获取到总的页码数目*/
	    int totalPage = logInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = logInfoService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(LogInfo logInfo:logInfoList) {
			JSONObject jsonLogInfo = logInfo.getJsonObject();
			jsonArray.put(jsonLogInfo);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询系统日志信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<LogInfo> logInfoList = logInfoService.queryAllLogInfo();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(LogInfo logInfo:logInfoList) {
			JSONObject jsonLogInfo = new JSONObject();
			jsonLogInfo.accumulate("logId", logInfo.getLogId());
			jsonLogInfo.accumulate("logContent", logInfo.getLogContent());
			jsonArray.put(jsonLogInfo);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询系统日志信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String logType,String logTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (logType == null) logType = "";
		if (logTime == null) logTime = "";
		List<LogInfo> logInfoList = logInfoService.queryLogInfo(logType, logTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    logInfoService.queryTotalPageAndRecordNumber(logType, logTime);
	    /*获取到总的页码数目*/
	    int totalPage = logInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = logInfoService.getRecordNumber();
	    request.setAttribute("logInfoList",  logInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("logType", logType);
	    request.setAttribute("logTime", logTime);
		return "LogInfo/logInfo_frontquery_result"; 
	}

     /*前台查询LogInfo信息*/
	@RequestMapping(value="/{logId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer logId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键logId获取LogInfo对象*/
        LogInfo logInfo = logInfoService.getLogInfo(logId);

        request.setAttribute("logInfo",  logInfo);
        return "LogInfo/logInfo_frontshow";
	}

	/*ajax方式显示系统日志修改jsp视图页*/
	@RequestMapping(value="/{logId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer logId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键logId获取LogInfo对象*/
        LogInfo logInfo = logInfoService.getLogInfo(logId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonLogInfo = logInfo.getJsonObject();
		out.println(jsonLogInfo.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新系统日志信息*/
	@RequestMapping(value = "/{logId}/update", method = RequestMethod.POST)
	public void update(@Validated LogInfo logInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			logInfoService.updateLogInfo(logInfo);
			message = "系统日志更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "系统日志更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除系统日志信息*/
	@RequestMapping(value="/{logId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer logId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  logInfoService.deleteLogInfo(logId);
	            request.setAttribute("message", "系统日志删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "系统日志删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条系统日志记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String logIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = logInfoService.deleteLogInfos(logIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出系统日志信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String logType,String logTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(logType == null) logType = "";
        if(logTime == null) logTime = "";
        List<LogInfo> logInfoList = logInfoService.queryLogInfo(logType,logTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "LogInfo信息记录"; 
        String[] headers = { "日志id","日志类型","日志内容","日志时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<logInfoList.size();i++) {
        	LogInfo logInfo = logInfoList.get(i); 
        	dataset.add(new String[]{logInfo.getLogId() + "",logInfo.getLogType(),logInfo.getLogContent(),logInfo.getLogTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"LogInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
