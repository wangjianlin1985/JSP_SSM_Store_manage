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
import com.chengxusheji.service.SellCartService;
import com.chengxusheji.po.SellCart;
import com.chengxusheji.service.EmployeeService;
import com.chengxusheji.po.Employee;
import com.chengxusheji.service.ProductService;
import com.chengxusheji.po.Product;

//SellCart管理控制层
@Controller
@RequestMapping("/SellCart")
public class SellCartController extends BaseController {

    /*业务层对象*/
    @Resource SellCartService sellCartService;

    @Resource EmployeeService employeeService;
    @Resource ProductService productService;
	@InitBinder("employeeObj")
	public void initBinderemployeeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("employeeObj.");
	}
	@InitBinder("productObj")
	public void initBinderproductObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("productObj.");
	}
	@InitBinder("sellCart")
	public void initBinderSellCart(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("sellCart.");
	}
	/*跳转到添加SellCart视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new SellCart());
		/*查询所有的Employee信息*/
		List<Employee> employeeList = employeeService.queryAllEmployee();
		request.setAttribute("employeeList", employeeList);
		/*查询所有的Product信息*/
		List<Product> productList = productService.queryAllProduct();
		request.setAttribute("productList", productList);
		return "SellCart_add";
	}

	/*客户端ajax方式提交添加销售购物车信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated SellCart sellCart, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        sellCartService.addSellCart(sellCart);
        message = "销售购物车添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	/*客户端ajax方式提交添加销售购物车信息*/
	@RequestMapping(value = "/empAdd", method = RequestMethod.POST)
	public void empAdd(SellCart sellCart, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		
		Employee employee = new Employee();
		employee.setEmployeeNo(session.getAttribute("user_name").toString());
		sellCart.setEmployeeObj(employee); 
		
		ArrayList<SellCart> cartList = sellCartService.querySellCart(employee, sellCart.getProductObj());
		if(cartList.size() > 0) {
			SellCart cart = cartList.get(0); 
			cart.setProductCount(cart.getProductCount() + sellCart.getProductCount());
			sellCartService.updateSellCart(cart);
		} else {
			sellCartService.addSellCart(sellCart);
		}
		 
        message = "商品销售登记成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	
	/*ajax方式按照查询条件分页查询销售购物车信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("employeeObj") Employee employeeObj,@ModelAttribute("productObj") Product productObj,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if(rows != 0)sellCartService.setRows(rows);
		List<SellCart> sellCartList = sellCartService.querySellCart(employeeObj, productObj, page);
	    /*计算总的页数和总的记录数*/
	    sellCartService.queryTotalPageAndRecordNumber(employeeObj, productObj);
	    /*获取到总的页码数目*/
	    int totalPage = sellCartService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = sellCartService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(SellCart sellCart:sellCartList) {
			JSONObject jsonSellCart = sellCart.getJsonObject();
			jsonArray.put(jsonSellCart);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询销售购物车信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<SellCart> sellCartList = sellCartService.queryAllSellCart();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(SellCart sellCart:sellCartList) {
			JSONObject jsonSellCart = new JSONObject();
			jsonSellCart.accumulate("sellCartId", sellCart.getSellCartId());
			jsonArray.put(jsonSellCart);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询销售购物车信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("employeeObj") Employee employeeObj,@ModelAttribute("productObj") Product productObj,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		List<SellCart> sellCartList = sellCartService.querySellCart(employeeObj, productObj, currentPage);
	    /*计算总的页数和总的记录数*/
	    sellCartService.queryTotalPageAndRecordNumber(employeeObj, productObj);
	    /*获取到总的页码数目*/
	    int totalPage = sellCartService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = sellCartService.getRecordNumber();
	    request.setAttribute("sellCartList",  sellCartList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("employeeObj", employeeObj);
	    request.setAttribute("productObj", productObj);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
	    List<Product> productList = productService.queryAllProduct();
	    request.setAttribute("productList", productList);
		return "SellCart/sellCart_frontquery_result"; 
	}
	
	
	/*前台按照查询条件分页查询销售购物车信息*/
	@RequestMapping(value = { "/empFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String empFrontlist(@ModelAttribute("employeeObj") Employee employeeObj,@ModelAttribute("productObj") Product productObj,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		employeeObj = new Employee();
		employeeObj.setEmployeeNo(session.getAttribute("user_name").toString());
		
		List<SellCart> sellCartList = sellCartService.querySellCart(employeeObj, productObj, currentPage);
	    /*计算总的页数和总的记录数*/
	    sellCartService.queryTotalPageAndRecordNumber(employeeObj, productObj);
	    /*获取到总的页码数目*/
	    int totalPage = sellCartService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = sellCartService.getRecordNumber();
	    request.setAttribute("sellCartList",  sellCartList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("employeeObj", employeeObj);
	    request.setAttribute("productObj", productObj);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
	    List<Product> productList = productService.queryAllProduct();
	    request.setAttribute("productList", productList);
		return "SellCart/sellCart_empFrontquery_result"; 
	}
	

     /*前台查询SellCart信息*/
	@RequestMapping(value="/{sellCartId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer sellCartId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键sellCartId获取SellCart对象*/
        SellCart sellCart = sellCartService.getSellCart(sellCartId);

        List<Employee> employeeList = employeeService.queryAllEmployee();
        request.setAttribute("employeeList", employeeList);
        List<Product> productList = productService.queryAllProduct();
        request.setAttribute("productList", productList);
        request.setAttribute("sellCart",  sellCart);
        return "SellCart/sellCart_frontshow";
	}

	/*ajax方式显示销售购物车修改jsp视图页*/
	@RequestMapping(value="/{sellCartId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer sellCartId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键sellCartId获取SellCart对象*/
        SellCart sellCart = sellCartService.getSellCart(sellCartId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonSellCart = sellCart.getJsonObject();
		out.println(jsonSellCart.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新销售购物车信息*/
	@RequestMapping(value = "/{sellCartId}/update", method = RequestMethod.POST)
	public void update(@Validated SellCart sellCart, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			sellCartService.updateSellCart(sellCart);
			message = "销售购物车更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "销售购物车更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除销售购物车信息*/
	@RequestMapping(value="/{sellCartId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer sellCartId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  sellCartService.deleteSellCart(sellCartId);
	            request.setAttribute("message", "销售购物车删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "销售购物车删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条销售购物车记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String sellCartIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = sellCartService.deleteSellCarts(sellCartIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出销售购物车信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("employeeObj") Employee employeeObj,@ModelAttribute("productObj") Product productObj, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        List<SellCart> sellCartList = sellCartService.querySellCart(employeeObj,productObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "SellCart信息记录"; 
        String[] headers = { "购物车id","员工","商品","商品数量"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<sellCartList.size();i++) {
        	SellCart sellCart = sellCartList.get(i); 
        	dataset.add(new String[]{sellCart.getSellCartId() + "",sellCart.getEmployeeObj().getName(),sellCart.getProductObj().getProductName(),sellCart.getProductCount() + ""});
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
			response.setHeader("Content-disposition","attachment; filename="+"SellCart.xls");//filename是下载的xls的名，建议最好用英文 
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
