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
import com.chengxusheji.service.BuyInfoService;
import com.chengxusheji.service.LogInfoService;
import com.chengxusheji.po.BuyInfo;
import com.chengxusheji.po.LogInfo;
import com.chengxusheji.service.EmployeeService;
import com.chengxusheji.po.Employee;
import com.chengxusheji.service.ProductService;
import com.chengxusheji.po.Product;
import com.chengxusheji.service.SuppllierService;
import com.chengxusheji.po.Suppllier;

//BuyInfo管理控制层
@Controller
@RequestMapping("/BuyInfo")
public class BuyInfoController extends BaseController {

    /*业务层对象*/
    @Resource BuyInfoService buyInfoService;
    @Resource LogInfoService logInfoService;

    @Resource EmployeeService employeeService;
    @Resource ProductService productService;
    @Resource SuppllierService suppllierService;
	@InitBinder("productObj")
	public void initBinderproductObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("productObj.");
	}
	@InitBinder("supplierObj")
	public void initBindersupplierObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("supplierObj.");
	}
	@InitBinder("employeeObj")
	public void initBinderemployeeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("employeeObj.");
	}
	@InitBinder("buyInfo")
	public void initBinderBuyInfo(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("buyInfo.");
	}
	/*跳转到添加BuyInfo视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new BuyInfo());
		/*查询所有的Employee信息*/
		List<Employee> employeeList = employeeService.queryAllEmployee();
		request.setAttribute("employeeList", employeeList);
		/*查询所有的Product信息*/
		List<Product> productList = productService.queryAllProduct();
		request.setAttribute("productList", productList);
		/*查询所有的Suppllier信息*/
		List<Suppllier> suppllierList = suppllierService.queryAllSuppllier();
		request.setAttribute("suppllierList", suppllierList);
		return "BuyInfo_add";
	}

	/*客户端ajax方式提交添加商品进货信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated BuyInfo buyInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        buyInfoService.addBuyInfo(buyInfo);
        
        Product product = productService.getProduct(buyInfo.getProductObj().getProductId());
        //记录日志
		LogInfo logInfo = new LogInfo();
		logInfo.setLogType("商品进货");
		logInfo.setLogContent("员工" + buyInfo.getEmployeeObj().getEmployeeNo() + "操作商品" + buyInfo.getProductObj().getProductName() + "进货数量：" + buyInfo.getNumber() + "登记成功");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logInfo.setLogTime(sdf.format(new java.util.Date()));
		logInfoService.addLogInfo(logInfo);
		
		
        message = "商品进货添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加商品进货信息*/
	@RequestMapping(value = "/empAdd", method = RequestMethod.POST)
	public void empAdd(BuyInfo buyInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		/*if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}*/
		//计算机总价
        buyInfo.setTotalPrice(buyInfo.getPrice() * buyInfo.getNumber());
        Employee employee = new Employee();
        employee.setEmployeeNo(session.getAttribute("user_name").toString());
        buyInfo.setEmployeeObj(employee);
        
        int productId = buyInfo.getProductObj().getProductId();
        Product product = productService.getProduct(productId);
        product.setProductCount(product.getProductCount() + buyInfo.getNumber());
        productService.updateProduct(product);  //更新商品库存
        
        
		buyInfoService.addBuyInfo(buyInfo);
		
		 //记录日志
		LogInfo logInfo = new LogInfo();
		logInfo.setLogType("商品进货");
		logInfo.setLogContent("员工" + buyInfo.getEmployeeObj().getEmployeeNo() + "操作商品" + product.getProductName() + "进货数量：" + buyInfo.getNumber() + "登记成功");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logInfo.setLogTime(sdf.format(new java.util.Date()));
		logInfoService.addLogInfo(logInfo);
			
			
        message = "商品进货添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*ajax方式按照查询条件分页查询商品进货信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("supplierObj") Suppllier supplierObj,String buyDate,@ModelAttribute("employeeObj") Employee employeeObj,@ModelAttribute("productObj") Product productObj,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (buyDate == null) buyDate = "";
		if(rows != 0)buyInfoService.setRows(rows);
		List<BuyInfo> buyInfoList = buyInfoService.queryBuyInfo(supplierObj, buyDate, employeeObj, productObj, page);
	    /*计算总的页数和总的记录数*/
	    buyInfoService.queryTotalPageAndRecordNumber(supplierObj, buyDate, employeeObj, productObj);
	    /*获取到总的页码数目*/
	    int totalPage = buyInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = buyInfoService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(BuyInfo buyInfo:buyInfoList) {
			JSONObject jsonBuyInfo = buyInfo.getJsonObject();
			jsonArray.put(jsonBuyInfo);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询商品进货信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<BuyInfo> buyInfoList = buyInfoService.queryAllBuyInfo();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(BuyInfo buyInfo:buyInfoList) {
			JSONObject jsonBuyInfo = new JSONObject();
			jsonBuyInfo.accumulate("buyId", buyInfo.getBuyId());
			jsonArray.put(jsonBuyInfo);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询商品进货信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("supplierObj") Suppllier supplierObj,String buyDate,@ModelAttribute("employeeObj") Employee employeeObj,@ModelAttribute("productObj") Product productObj,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (buyDate == null) buyDate = "";
		List<BuyInfo> buyInfoList = buyInfoService.queryBuyInfo(supplierObj, buyDate, employeeObj, productObj, currentPage);
	    /*计算总的页数和总的记录数*/
	    buyInfoService.queryTotalPageAndRecordNumber(supplierObj, buyDate, employeeObj, productObj);
	    /*获取到总的页码数目*/
	    int totalPage = buyInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = buyInfoService.getRecordNumber();
	    request.setAttribute("buyInfoList",  buyInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("supplierObj", supplierObj);
	    request.setAttribute("buyDate", buyDate);
	    request.setAttribute("employeeObj", employeeObj);
	    request.setAttribute("productObj", productObj);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
	    List<Product> productList = productService.queryAllProduct();
	    request.setAttribute("productList", productList);
	    List<Suppllier> suppllierList = suppllierService.queryAllSuppllier();
	    request.setAttribute("suppllierList", suppllierList);
		return "BuyInfo/buyInfo_frontquery_result"; 
	}
	
	
	
	/*前台按照查询条件分页查询商品进货信息*/
	@RequestMapping(value = { "/empFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String empFrontlist(@ModelAttribute("supplierObj") Suppllier supplierObj,String buyDate,@ModelAttribute("employeeObj") Employee employeeObj,@ModelAttribute("productObj") Product productObj,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (buyDate == null) buyDate = "";
		employeeObj = new Employee();
		employeeObj.setEmployeeNo(session.getAttribute("user_name").toString());
		
		List<BuyInfo> buyInfoList = buyInfoService.queryBuyInfo(supplierObj, buyDate, employeeObj, productObj, currentPage);
	    /*计算总的页数和总的记录数*/
	    buyInfoService.queryTotalPageAndRecordNumber(supplierObj, buyDate, employeeObj, productObj);
	    /*获取到总的页码数目*/
	    int totalPage = buyInfoService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = buyInfoService.getRecordNumber();
	    request.setAttribute("buyInfoList",  buyInfoList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("supplierObj", supplierObj);
	    request.setAttribute("buyDate", buyDate);
	    request.setAttribute("employeeObj", employeeObj);
	    request.setAttribute("productObj", productObj);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
	    List<Product> productList = productService.queryAllProduct();
	    request.setAttribute("productList", productList);
	    List<Suppllier> suppllierList = suppllierService.queryAllSuppllier();
	    request.setAttribute("suppllierList", suppllierList);
		return "BuyInfo/buyInfo_empFrontquery_result"; 
	}
	

     /*前台查询BuyInfo信息*/
	@RequestMapping(value="/{buyId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer buyId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键buyId获取BuyInfo对象*/
        BuyInfo buyInfo = buyInfoService.getBuyInfo(buyId);

        List<Employee> employeeList = employeeService.queryAllEmployee();
        request.setAttribute("employeeList", employeeList);
        List<Product> productList = productService.queryAllProduct();
        request.setAttribute("productList", productList);
        List<Suppllier> suppllierList = suppllierService.queryAllSuppllier();
        request.setAttribute("suppllierList", suppllierList);
        request.setAttribute("buyInfo",  buyInfo);
        return "BuyInfo/buyInfo_frontshow";
	}

	/*ajax方式显示商品进货修改jsp视图页*/
	@RequestMapping(value="/{buyId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer buyId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键buyId获取BuyInfo对象*/
        BuyInfo buyInfo = buyInfoService.getBuyInfo(buyId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonBuyInfo = buyInfo.getJsonObject();
		out.println(jsonBuyInfo.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新商品进货信息*/
	@RequestMapping(value = "/{buyId}/update", method = RequestMethod.POST)
	public void update(@Validated BuyInfo buyInfo, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			buyInfoService.updateBuyInfo(buyInfo);
			message = "商品进货更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品进货更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除商品进货信息*/
	@RequestMapping(value="/{buyId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer buyId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  buyInfoService.deleteBuyInfo(buyId);
	            request.setAttribute("message", "商品进货删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "商品进货删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条商品进货记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String buyIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = buyInfoService.deleteBuyInfos(buyIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出商品进货信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("supplierObj") Suppllier supplierObj,String buyDate,@ModelAttribute("employeeObj") Employee employeeObj,@ModelAttribute("productObj") Product productObj, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(buyDate == null) buyDate = "";
        List<BuyInfo> buyInfoList = buyInfoService.queryBuyInfo(supplierObj,buyDate,employeeObj,productObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "BuyInfo信息记录"; 
        String[] headers = { "进货id","进货商品","供应商","进货单价","进货数量","总价格","进货日期","进货员工"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<buyInfoList.size();i++) {
        	BuyInfo buyInfo = buyInfoList.get(i); 
        	dataset.add(new String[]{buyInfo.getBuyId() + "",buyInfo.getProductObj().getProductName(),buyInfo.getSupplierObj().getSupplierName(),buyInfo.getPrice() + "",buyInfo.getNumber() + "",buyInfo.getTotalPrice() + "",buyInfo.getBuyDate(),buyInfo.getEmployeeObj().getName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"BuyInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
