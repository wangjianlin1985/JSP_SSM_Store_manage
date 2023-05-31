package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.chengxusheji.service.SellCartService;
import com.chengxusheji.service.SellService;
import com.chengxusheji.po.LogInfo;
import com.chengxusheji.po.Sell;
import com.chengxusheji.po.SellCart;
import com.chengxusheji.service.EmployeeService;
import com.chengxusheji.po.Employee;
import com.chengxusheji.service.MemberService;
import com.chengxusheji.po.Member;
import com.chengxusheji.service.ProductService;
import com.chengxusheji.po.Product;

//Sell管理控制层
@Controller
@RequestMapping("/Sell")
public class SellController extends BaseController {

    /*业务层对象*/
    @Resource SellService sellService;
    @Resource LogInfoService logInfoService;

    @Resource EmployeeService employeeService;
    @Resource MemberService memberService;
    @Resource ProductService productService;
    @Resource SellCartService sellCartService;
    
	@InitBinder("productObj")
	public void initBinderproductObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("productObj.");
	}
	@InitBinder("memberObj")
	public void initBindermemberObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("memberObj.");
	}
	@InitBinder("employeeObj")
	public void initBinderemployeeObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("employeeObj.");
	}
	@InitBinder("sell")
	public void initBinderSell(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("sell.");
	}
	/*跳转到添加Sell视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Sell());
		/*查询所有的Employee信息*/
		List<Employee> employeeList = employeeService.queryAllEmployee();
		request.setAttribute("employeeList", employeeList);
		/*查询所有的Member信息*/
		List<Member> memberList = memberService.queryAllMember();
		request.setAttribute("memberList", memberList);
		/*查询所有的Product信息*/
		List<Product> productList = productService.queryAllProduct();
		request.setAttribute("productList", productList);
		return "Sell_add";
	}

	/*客户端ajax方式提交添加商品销售信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Sell sell, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        sellService.addSell(sell);
        message = "商品销售添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加商品销售信息*/
	@RequestMapping(value = "/empAdd", method = RequestMethod.POST)
	public void empAdd(Sell sell, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false; 
		 
		String employeeNo = session.getAttribute("user_name").toString();
		Member member = sell.getMemberObj();  //购买会员
		
		Employee employeeObj = new Employee();
		employeeObj.setEmployeeNo(employeeNo);
		ArrayList<SellCart> cartList = sellCartService.querySellCart(employeeObj, null);
		if(cartList.size() == 0) {
			message = "一个商品都没有，请先选商品！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		//生成订单号
		java.util.Date nowDate = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String sellNo = employeeNo + sdf.format(nowDate);
		
		//判断是否有商品库存不足销售
		for(SellCart cart: cartList) {
			if(cart.getProductObj().getProductCount() < cart.getProductCount()) {
				message = cart.getProductObj().getProductId() + " : " +  cart.getProductObj().getProductName()  + " 库存不够出售！";
				writeJsonResponse(response, success, message);
				return ;
			}
		}
		
		for(SellCart cart: cartList) {
			Sell sellObj = new Sell();
			sellObj.setEmployeeObj(employeeObj);
			sellObj.setMemberObj(member);
			sellObj.setNumber(cart.getProductCount());
			sellObj.setPrice(cart.getProductObj().getProductPrice());
			sellObj.setProductObj(cart.getProductObj());
			sellObj.setSellNo(sellNo);
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sellObj.setSellTime(sdf.format(nowDate));
			sellObj.setTotalPrice(sellObj.getPrice() * sellObj.getNumber());
			sellService.addSell(sellObj);
			
			Product product = cart.getProductObj();
			product.setProductCount(product.getProductCount() - sellObj.getNumber()); //减少商品库存
			
			//记录日志
			LogInfo logInfo = new LogInfo();
			logInfo.setLogType("商品销售");
			logInfo.setLogContent("员工操作" + product.getProductName() + "销售数量：" + cart.getProductCount() + "登记成功");
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logInfo.setLogTime(sdf.format(new java.util.Date()));
			logInfoService.addLogInfo(logInfo);
			
			sellCartService.deleteSellCart(cart.getSellCartId());
			
			
		}
		 
        
        message = "商品销售添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*ajax方式按照查询条件分页查询商品销售信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String sellNo,@ModelAttribute("productObj") Product productObj,@ModelAttribute("memberObj") Member memberObj,@ModelAttribute("employeeObj") Employee employeeObj,String sellTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (sellNo == null) sellNo = "";
		if (sellTime == null) sellTime = "";
		if(rows != 0)sellService.setRows(rows);
		List<Sell> sellList = sellService.querySell(sellNo, productObj, memberObj, employeeObj, sellTime, page);
	    /*计算总的页数和总的记录数*/
	    sellService.queryTotalPageAndRecordNumber(sellNo, productObj, memberObj, employeeObj, sellTime);
	    /*获取到总的页码数目*/
	    int totalPage = sellService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = sellService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Sell sell:sellList) {
			JSONObject jsonSell = sell.getJsonObject();
			jsonArray.put(jsonSell);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}
	
	
	
	/*客户端ajax方式查询统计图信息 */
	@RequestMapping(value = "/statistic", method = RequestMethod.POST)
	public void statistic(HttpServletRequest request,HttpServletResponse response) throws Exception {
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		JSONObject jsonObj = new JSONObject();
    	JSONArray xData = new JSONArray();
    	JSONArray yData = new JSONArray();
    	
//    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
//    	Calendar calendar = Calendar.getInstance();
//    	String dayString = year + "-" + (month<10?("0" + month):month+"") + "-01";
//    	calendar.setTime(sdf.parse(dayString));  
//       int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); //获取某个月份一共多少天
//        
        Calendar   cal   =   Calendar.getInstance();   
        cal.set(Calendar.YEAR,year);   
        cal.set(Calendar.MONTH,month-1);//7月   
        int days = cal.getActualMaximum(Calendar.DATE); 
        
        
    	for(int i=1;i<=days;i++) {
    		float dayMoney = 0.0f;
    		String dayString = year  + "-" + (month<10?("0" + month):month+"") + "-" + (i<10?("0" + i):i+"");
    		ArrayList<Sell> sellList = sellService.querySell("", null, null, null, dayString);
    		for(Sell sell: sellList) {
    			dayMoney += sell.getTotalPrice();
    		}
    		//xData.put((month<10?("0" + month):month+"")+ "-" + (i<10?("0" + i):i+""));
    		xData.put((i<10?("0" + i):i+""));
    		yData.put(dayMoney);
    	}
    	
    	//将要被返回到客户端的对象 
		JSONObject json=new JSONObject();
		json.accumulate("xData", xData);
		json.accumulate("yData", yData); 
		
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter(); 
		out.println(json.toString());
		out.flush(); 
		out.close();
	}
	
	

	/*ajax方式按照查询条件分页查询商品销售信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Sell> sellList = sellService.queryAllSell();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Sell sell:sellList) {
			JSONObject jsonSell = new JSONObject();
			jsonSell.accumulate("sellId", sell.getSellId());
			jsonSell.accumulate("sellNo", sell.getSellNo());
			jsonArray.put(jsonSell);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询商品销售信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String sellNo,@ModelAttribute("productObj") Product productObj,@ModelAttribute("memberObj") Member memberObj,@ModelAttribute("employeeObj") Employee employeeObj,String sellTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (sellNo == null) sellNo = "";
		if (sellTime == null) sellTime = "";
		List<Sell> sellList = sellService.querySell(sellNo, productObj, memberObj, employeeObj, sellTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    sellService.queryTotalPageAndRecordNumber(sellNo, productObj, memberObj, employeeObj, sellTime);
	    /*获取到总的页码数目*/
	    int totalPage = sellService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = sellService.getRecordNumber();
	    request.setAttribute("sellList",  sellList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("sellNo", sellNo);
	    request.setAttribute("productObj", productObj);
	    request.setAttribute("memberObj", memberObj);
	    request.setAttribute("employeeObj", employeeObj);
	    request.setAttribute("sellTime", sellTime);
	    List<Employee> employeeList = employeeService.queryAllEmployee();
	    request.setAttribute("employeeList", employeeList);
	    List<Member> memberList = memberService.queryAllMember();
	    request.setAttribute("memberList", memberList);
	    List<Product> productList = productService.queryAllProduct();
	    request.setAttribute("productList", productList);
		return "Sell/sell_frontquery_result"; 
	}

     /*前台查询Sell信息*/
	@RequestMapping(value="/{sellId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer sellId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键sellId获取Sell对象*/
        Sell sell = sellService.getSell(sellId);

        List<Employee> employeeList = employeeService.queryAllEmployee();
        request.setAttribute("employeeList", employeeList);
        List<Member> memberList = memberService.queryAllMember();
        request.setAttribute("memberList", memberList);
        List<Product> productList = productService.queryAllProduct();
        request.setAttribute("productList", productList);
        request.setAttribute("sell",  sell);
        return "Sell/sell_frontshow";
	}

	/*ajax方式显示商品销售修改jsp视图页*/
	@RequestMapping(value="/{sellId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer sellId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键sellId获取Sell对象*/
        Sell sell = sellService.getSell(sellId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonSell = sell.getJsonObject();
		out.println(jsonSell.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新商品销售信息*/
	@RequestMapping(value = "/{sellId}/update", method = RequestMethod.POST)
	public void update(@Validated Sell sell, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			sellService.updateSell(sell);
			message = "商品销售更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "商品销售更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除商品销售信息*/
	@RequestMapping(value="/{sellId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer sellId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  sellService.deleteSell(sellId);
	            request.setAttribute("message", "商品销售删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "商品销售删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条商品销售记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String sellIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = sellService.deleteSells(sellIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出商品销售信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String sellNo,@ModelAttribute("productObj") Product productObj,@ModelAttribute("memberObj") Member memberObj,@ModelAttribute("employeeObj") Employee employeeObj,String sellTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(sellNo == null) sellNo = "";
        if(sellTime == null) sellTime = "";
        List<Sell> sellList = sellService.querySell(sellNo,productObj,memberObj,employeeObj,sellTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Sell信息记录"; 
        String[] headers = { "记录id","订单号","销售商品","销售价格","销售数量","销售总价","购买会员","销售员工","销售时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<sellList.size();i++) {
        	Sell sell = sellList.get(i); 
        	dataset.add(new String[]{sell.getSellId() + "",sell.getSellNo(),sell.getProductObj().getProductName(),sell.getPrice() + "",sell.getNumber() + "",sell.getTotalPrice() + "",sell.getMemberObj().getName(),sell.getEmployeeObj().getName(),sell.getSellTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Sell.xls");//filename是下载的xls的名，建议最好用英文 
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
