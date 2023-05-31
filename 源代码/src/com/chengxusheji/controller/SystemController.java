package com.chengxusheji.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.json.JSONObject;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

  
import com.chengxusheji.po.Admin;
import com.chengxusheji.po.LogInfo;
import com.chengxusheji.service.AdminService;
import com.chengxusheji.service.EmployeeService;
import com.chengxusheji.service.LogInfoService;
import com.chengxusheji.service.ManagerService;
import com.chengxusheji.utils.UserException;

 
@Controller
@SessionAttributes("username")
public class SystemController { 
	
	@Resource AdminService adminService;  
	@Resource ManagerService managerService;
	@Resource EmployeeService employeeService;
	@Resource LogInfoService logInfoService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(Model model) {
		model.addAttribute(new Admin());
		return "login";
	}

	//前台用户登录
	@RequestMapping(value="/frontLogin",method=RequestMethod.POST)
	public void frontLogin(@RequestParam("userName")String userName,@RequestParam("password")String password,HttpServletResponse response,HttpSession session) throws Exception { 
		boolean success = true;
		String msg = ""; 

		 
		if (!employeeService.checkLogin(userName, password)) { 
			msg = employeeService.getErrMessage();
			success = false; 
		} 
		if(success) {
			session.setAttribute("user_name", userName); 
			//记录日志
			LogInfo logInfo = new LogInfo();
			logInfo.setLogType("系统登录");
			logInfo.setLogContent("员工" + userName + "登录系统");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			logInfo.setLogTime(sdf.format(new java.util.Date()));
			logInfoService.addLogInfo(logInfo);
		}
  
        response.setContentType("text/json;charset=UTF-8");  
        PrintWriter out = response.getWriter();  
        //将要被返回到客户端的对象   
        JSONObject json=new JSONObject();   
        json.accumulate("success", success);
        json.accumulate("msg", msg);
        out.println(json.toString());   
        out.flush();  
        out.close();  
	}


	@RequestMapping(value="/login",method=RequestMethod.POST)
	public void login(@Validated Admin admin,BindingResult br,Model model,HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception { 
		boolean success = true;
		String msg = ""; 
		if(br.hasErrors()) {
			msg = br.getAllErrors().toString();
			success = false;  
		} 
		
		if(admin.getIdentify().equals("admin")) {
			if (!adminService.checkLogin(admin)) { 
				msg = adminService.getErrMessage();
				success = false; 
			} 
			if(success) {
				session.setAttribute("username", admin.getUsername()); 
				//记录日志
				LogInfo logInfo = new LogInfo();
				logInfo.setLogType("系统登录");
				logInfo.setLogContent("超级管理员" + admin.getUsername() + "登录系统");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				logInfo.setLogTime(sdf.format(new java.util.Date()));
				logInfoService.addLogInfo(logInfo);
			}  
		} else {
			if (!managerService.checkLogin(admin)) { 
				msg = managerService.getErrMessage();
				success = false; 
			} 
			if(success) {
				session.setAttribute("manager", admin.getUsername()); 
				//记录日志
				LogInfo logInfo = new LogInfo();
				logInfo.setLogType("系统登录");
				logInfo.setLogContent("普通管理员" + admin.getUsername() + "登录系统");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				logInfo.setLogTime(sdf.format(new java.util.Date()));
				logInfoService.addLogInfo(logInfo);
			}  
		}
			
		
        response.setContentType("text/json;charset=UTF-8");  
        PrintWriter out = response.getWriter();  
        //将要被返回到客户端的对象   
        JSONObject json=new JSONObject();   
        json.accumulate("success", success);
        json.accumulate("msg", msg);
        out.println(json.toString());   
        out.flush();  
        out.close();  
	}
	
	
	
	@RequestMapping("/logout")
	public String logout(Model model,HttpSession session) {
		model.asMap().remove("username"); 
		session.invalidate();
		return "redirect:/login";
	}
	
	
	@RequestMapping(value="/changePassword",method=RequestMethod.POST)
	public String ChangePassword(String oldPassword,String newPassword,String newPassword2,HttpServletRequest request,HttpSession session) throws Exception { 
		if(oldPassword.equals("")) throw new UserException("请输入旧密码！");
		if(newPassword.equals("")) throw new UserException("请输入新密码！");
		if(!newPassword.equals(newPassword2)) throw new UserException("两次新密码输入不一致"); 
		
		String username = (String)session.getAttribute("username");
		if(username == null) throw new UserException("session会话超时，请重新登录系统!");
		 
		
		Admin admin = adminService.findAdminByUserName(username); 
		if(!admin.getPassword().equals(oldPassword)) throw new UserException("输入的旧密码不正确!");
		
		try { 
			adminService.changePassword(username,newPassword);
			request.setAttribute("message", java.net.URLEncoder.encode(
					"密码修改成功!", "GBK"));
			return "message";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", java.net.URLEncoder
					.encode("密码修改失败!","GBK"));
			return "error";
		}   
	}
	
	
	
	
}
