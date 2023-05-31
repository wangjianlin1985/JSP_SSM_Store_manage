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
import com.chengxusheji.service.LostProductService;
import com.chengxusheji.po.LostProduct;
import com.chengxusheji.service.EmployeeService;
import com.chengxusheji.po.Employee;
import com.chengxusheji.service.ProductService;
import com.chengxusheji.po.Product;

//LostProduct管理控制层
@Controller
@RequestMapping("/LostProduct")
public class LostProductController extends BaseController {

    /*业务层对象*/
    @Resource LostProductService lostProductService;

    @Resource EmployeeService employeeService;
    @Resource ProductService productService;
	@InitBinder("productObj")
	public void initBinderproductObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("productObj.");
	}
	@InitBinder("employeeObj")
	public void initBinderemployeeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("employeeObj.");
	}
	@InitBinder("lostProduct")
	public void initBinderLostProduct(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("lostProduct.");
	}
	/*跳转到添加LostProduct视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new LostProduct());
		/*查询所有的Employee信息*/
		List<Employee> employeeList = employeeService.queryAllEmployee();
		request.setAttribute("employeeList", employeeList);
		/*查询所有的Product信息*/
		List<Product> productList = productService.queryAllProduct();
		request.setAttribute("productList", productList);
		return "LostProduct_add";
	}
	
	 
	

	/*客户端ajax方式提交添加丢失物品信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated LostProduct lostProduct, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        lostProductService.addLostProduct(lostProduct);
        message = "丢失物品添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	

	

	/*客户端ajax方式提交添加丢失物品信息*/
	@RequestMapping(value = "/empAdd", method = RequestMethod.POST)
	public void empAdd(LostProduct lostProduct, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		 
		String employeeNo = session.getAttribute("user_name").toString();
		Employee employeeObj = new Employee();
		employeeObj.setEmployeeNo(employeeNo);
		lostProduct.setEmployeeObj(employeeObj);
		
        lostProductService.addLostProduct(lostProduct);
        message = "丢失物品添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*ajax方式按照查询条件分页查询丢失物品信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("productObj") Product productObj,String lostTime,@ModelAttribute("employeeObj") Employee employeeObj,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (lostTime == null) lostTime = "";
		if(rows != 0)lostProductService.setRows(rows);
		List<LostProduct> lostProductList = lostProductService.queryLostProduct(productObj, lostTime, employeeObj, page);
	    /*计算总的页数和总的记录数*/
	    lostProductService.queryTotalPageAndRecordNumber(productObj, lostTime, employeeObj);
	    /*获取到总的页码数目*/
	    int totalPage = lostProductService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = lostProductService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(LostProduct lostProduct:lostProductList) {
			JSONObject jsonLostProduct = lostProduct.getJsonObject();
			jsonArray.put(jsonLostProduct);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询丢失物品信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<LostProduct> lostProductList = lostProductService.queryAllLostProduct();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(LostProduct lostProduct:lostProductList) {
			JSONObject jsonLostProduct = new JSONObject();
			jsonLostProduct.accumulate("lostId", lostProduct.getLostId());
			jsonArray.put(jsonLostProduct);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询丢失物品信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("productObj") Product productObj,String lostTime,@ModelAttribute("employeeObj") Employee employeeObj,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (lostTime == null) lostTime = "";
		List<LostProduct> lostProductList = lostProductService.queryLostProduct(productObj, lostTime, employeeObj, currentPage);
	    /*计算总的页数和总的记录数*/
	    lostProductService.queryTotalPageAndRecordNumber(productObj, lostTime, employeeObj);
	    /*获取到总的页码数目*/
	    int totalPage = lostProductService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = lostProductService.getRecordNumber();
	    request.setAttribute("lostProductList",  lostProductList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("productObj", productObj);
	    request.setAttribute("lostTime", lostTime);
	    request.setAttribute("employeeObj", employeeObj);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
	    List<Product> productList = productService.queryAllProduct();
	    request.setAttribute("productList", productList);
		return "LostProduct/lostProduct_frontquery_result"; 
	}

     /*前台查询LostProduct信息*/
	@RequestMapping(value="/{lostId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer lostId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键lostId获取LostProduct对象*/
        LostProduct lostProduct = lostProductService.getLostProduct(lostId);

        List<Employee> employeeList = employeeService.queryAllEmployee();
        request.setAttribute("employeeList", employeeList);
        List<Product> productList = productService.queryAllProduct();
        request.setAttribute("productList", productList);
        request.setAttribute("lostProduct",  lostProduct);
        return "LostProduct/lostProduct_frontshow";
	}

	/*ajax方式显示丢失物品修改jsp视图页*/
	@RequestMapping(value="/{lostId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer lostId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键lostId获取LostProduct对象*/
        LostProduct lostProduct = lostProductService.getLostProduct(lostId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonLostProduct = lostProduct.getJsonObject();
		out.println(jsonLostProduct.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新丢失物品信息*/
	@RequestMapping(value = "/{lostId}/update", method = RequestMethod.POST)
	public void update(@Validated LostProduct lostProduct, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			lostProductService.updateLostProduct(lostProduct);
			message = "丢失物品更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "丢失物品更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除丢失物品信息*/
	@RequestMapping(value="/{lostId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer lostId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  lostProductService.deleteLostProduct(lostId);
	            request.setAttribute("message", "丢失物品删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "丢失物品删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条丢失物品记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String lostIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = lostProductService.deleteLostProducts(lostIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出丢失物品信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("productObj") Product productObj,String lostTime,@ModelAttribute("employeeObj") Employee employeeObj, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(lostTime == null) lostTime = "";
        List<LostProduct> lostProductList = lostProductService.queryLostProduct(productObj,lostTime,employeeObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "LostProduct信息记录"; 
        String[] headers = { "记录id","丢失物品","丢失数量","丢失时间","丢失地点","总价值","操作员"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<lostProductList.size();i++) {
        	LostProduct lostProduct = lostProductList.get(i); 
        	dataset.add(new String[]{lostProduct.getLostId() + "",lostProduct.getProductObj().getProductName(),lostProduct.getLostNumber() + "",lostProduct.getLostTime(),lostProduct.getLostPlace(),lostProduct.getProductMoney() + "",lostProduct.getEmployeeObj().getName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"LostProduct.xls");//filename是下载的xls的名，建议最好用英文 
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
