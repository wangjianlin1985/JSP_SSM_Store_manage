﻿package com.chengxusheji.controller;

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
import com.chengxusheji.service.SuppllierService;
import com.chengxusheji.po.Suppllier;

//Suppllier管理控制层
@Controller
@RequestMapping("/Suppllier")
public class SuppllierController extends BaseController {

    /*业务层对象*/
    @Resource SuppllierService suppllierService;

	@InitBinder("suppllier")
	public void initBinderSuppllier(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("suppllier.");
	}
	/*跳转到添加Suppllier视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Suppllier());
		return "Suppllier_add";
	}

	/*客户端ajax方式提交添加供应商信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Suppllier suppllier, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        suppllierService.addSuppllier(suppllier);
        message = "供应商添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询供应商信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String supplierName,String supplierLawyer,String supplierTelephone,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (supplierName == null) supplierName = "";
		if (supplierLawyer == null) supplierLawyer = "";
		if (supplierTelephone == null) supplierTelephone = "";
		if(rows != 0)suppllierService.setRows(rows);
		List<Suppllier> suppllierList = suppllierService.querySuppllier(supplierName, supplierLawyer, supplierTelephone, page);
	    /*计算总的页数和总的记录数*/
	    suppllierService.queryTotalPageAndRecordNumber(supplierName, supplierLawyer, supplierTelephone);
	    /*获取到总的页码数目*/
	    int totalPage = suppllierService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = suppllierService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Suppllier suppllier:suppllierList) {
			JSONObject jsonSuppllier = suppllier.getJsonObject();
			jsonArray.put(jsonSuppllier);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询供应商信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Suppllier> suppllierList = suppllierService.queryAllSuppllier();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Suppllier suppllier:suppllierList) {
			JSONObject jsonSuppllier = new JSONObject();
			jsonSuppllier.accumulate("supplierId", suppllier.getSupplierId());
			jsonSuppllier.accumulate("supplierName", suppllier.getSupplierName());
			jsonArray.put(jsonSuppllier);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询供应商信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String supplierName,String supplierLawyer,String supplierTelephone,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (supplierName == null) supplierName = "";
		if (supplierLawyer == null) supplierLawyer = "";
		if (supplierTelephone == null) supplierTelephone = "";
		List<Suppllier> suppllierList = suppllierService.querySuppllier(supplierName, supplierLawyer, supplierTelephone, currentPage);
	    /*计算总的页数和总的记录数*/
	    suppllierService.queryTotalPageAndRecordNumber(supplierName, supplierLawyer, supplierTelephone);
	    /*获取到总的页码数目*/
	    int totalPage = suppllierService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = suppllierService.getRecordNumber();
	    request.setAttribute("suppllierList",  suppllierList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("supplierName", supplierName);
	    request.setAttribute("supplierLawyer", supplierLawyer);
	    request.setAttribute("supplierTelephone", supplierTelephone);
		return "Suppllier/suppllier_frontquery_result"; 
	}

     /*前台查询Suppllier信息*/
	@RequestMapping(value="/{supplierId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer supplierId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键supplierId获取Suppllier对象*/
        Suppllier suppllier = suppllierService.getSuppllier(supplierId);

        request.setAttribute("suppllier",  suppllier);
        return "Suppllier/suppllier_frontshow";
	}

	/*ajax方式显示供应商修改jsp视图页*/
	@RequestMapping(value="/{supplierId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer supplierId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键supplierId获取Suppllier对象*/
        Suppllier suppllier = suppllierService.getSuppllier(supplierId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonSuppllier = suppllier.getJsonObject();
		out.println(jsonSuppllier.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新供应商信息*/
	@RequestMapping(value = "/{supplierId}/update", method = RequestMethod.POST)
	public void update(@Validated Suppllier suppllier, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			suppllierService.updateSuppllier(suppllier);
			message = "供应商更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "供应商更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除供应商信息*/
	@RequestMapping(value="/{supplierId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer supplierId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  suppllierService.deleteSuppllier(supplierId);
	            request.setAttribute("message", "供应商删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "供应商删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条供应商记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String supplierIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = suppllierService.deleteSupplliers(supplierIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出供应商信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String supplierName,String supplierLawyer,String supplierTelephone, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(supplierName == null) supplierName = "";
        if(supplierLawyer == null) supplierLawyer = "";
        if(supplierTelephone == null) supplierTelephone = "";
        List<Suppllier> suppllierList = suppllierService.querySuppllier(supplierName,supplierLawyer,supplierTelephone);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Suppllier信息记录"; 
        String[] headers = { "供应商id","供应商名称","法人代表","供应商电话","供应商地址"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<suppllierList.size();i++) {
        	Suppllier suppllier = suppllierList.get(i); 
        	dataset.add(new String[]{suppllier.getSupplierId() + "",suppllier.getSupplierName(),suppllier.getSupplierLawyer(),suppllier.getSupplierTelephone(),suppllier.getSupplierAddress()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Suppllier.xls");//filename是下载的xls的名，建议最好用英文 
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
