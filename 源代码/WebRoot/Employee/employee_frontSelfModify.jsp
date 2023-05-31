<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Employee" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Employee employee = (Employee)request.getAttribute("employee");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改雇员信息</TITLE>
  <link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
  <link href="<%=basePath %>plugins/animate.css" rel="stylesheet"> 
</head>
<body style="margin-top:70px;"> 
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
	<ul class="breadcrumb">
  		<li><a href="<%=basePath %>index.jsp">首页</a></li>
  		<li class="active">雇员信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="employeeEditForm" id="employeeEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="employee_employeeNo_edit" class="col-md-3 text-right">雇员编号:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="employee_employeeNo_edit" name="employee.employeeNo" class="form-control" placeholder="请输入雇员编号" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="employee_password_edit" class="col-md-3 text-right">登录密码:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="employee_password_edit" name="employee.password" class="form-control" placeholder="请输入登录密码">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="employee_name_edit" class="col-md-3 text-right">姓名:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="employee_name_edit" name="employee.name" class="form-control" placeholder="请输入姓名">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="employee_gender_edit" class="col-md-3 text-right">性别:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="employee_gender_edit" name="employee.gender" class="form-control" placeholder="请输入性别">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="employee_birthDate_edit" class="col-md-3 text-right">出生日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date employee_birthDate_edit col-md-12" data-link-field="employee_birthDate_edit" data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="employee_birthDate_edit" name="employee.birthDate" size="16" type="text" value="" placeholder="请选择出生日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="employee_employeePhoto_edit" class="col-md-3 text-right">雇员照片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="employee_employeePhotoImg" border="0px"/><br/>
			    <input type="hidden" id="employee_employeePhoto" name="employee.employeePhoto"/>
			    <input id="employeePhotoFile" name="employeePhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="employee_telephone_edit" class="col-md-3 text-right">联系电话:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="employee_telephone_edit" name="employee.telephone" class="form-control" placeholder="请输入联系电话">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="employee_email_edit" class="col-md-3 text-right">邮箱:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="employee_email_edit" name="employee.email" class="form-control" placeholder="请输入邮箱">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="employee_address_edit" class="col-md-3 text-right">家庭地址:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="employee_address_edit" name="employee.address" class="form-control" placeholder="请输入家庭地址">
			 </div>
		  </div>
		  <div class="form-group" style="display:none;">
		  	 <label for="employee_regTime_edit" class="col-md-3 text-right">注册时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date employee_regTime_edit col-md-12" data-link-field="employee_regTime_edit">
                    <input class="form-control" id="employee_regTime_edit" name="employee.regTime" size="16" type="text" value="" placeholder="请选择注册时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxEmployeeModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#employeeEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
   </div>
</div>


<jsp:include page="../footer.jsp"></jsp:include>
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*弹出修改雇员界面并初始化数据*/
function employeeEdit(employeeNo) {
	$.ajax({
		url :  basePath + "Employee/" + employeeNo + "/update",
		type : "get",
		dataType: "json",
		success : function (employee, response, status) {
			if (employee) {
				$("#employee_employeeNo_edit").val(employee.employeeNo);
				$("#employee_password_edit").val(employee.password);
				$("#employee_name_edit").val(employee.name);
				$("#employee_gender_edit").val(employee.gender);
				$("#employee_birthDate_edit").val(employee.birthDate);
				$("#employee_employeePhoto").val(employee.employeePhoto);
				$("#employee_employeePhotoImg").attr("src", basePath +　employee.employeePhoto);
				$("#employee_telephone_edit").val(employee.telephone);
				$("#employee_email_edit").val(employee.email);
				$("#employee_address_edit").val(employee.address);
				$("#employee_regTime_edit").val(employee.regTime);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交雇员信息表单给服务器端修改*/
function ajaxEmployeeModify() {
	$.ajax({
		url :  basePath + "Employee/" + $("#employee_employeeNo_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#employeeEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#employeeQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
        /*小屏幕导航点击关闭菜单*/
        $('.navbar-collapse a').click(function(){
            $('.navbar-collapse').collapse('hide');
        });
        new WOW().init();
    /*出生日期组件*/
    $('.employee_birthDate_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd',
    	minView: 2,
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
    /*注册时间组件*/
    $('.employee_regTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
    employeeEdit("<%=session.getAttribute("user_name")%>");
 })
 </script> 
</body>
</html>

