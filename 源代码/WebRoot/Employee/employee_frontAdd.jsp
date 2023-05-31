﻿<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>雇员添加</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-12 wow fadeInLeft">
		<ul class="breadcrumb">
  			<li><a href="<%=basePath %>index.jsp">首页</a></li>
  			<li><a href="<%=basePath %>Employee/frontlist">雇员管理</a></li>
  			<li class="active">添加雇员</li>
		</ul>
		<div class="row">
			<div class="col-md-10">
		      	<form class="form-horizontal" name="employeeAddForm" id="employeeAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
				  <div class="form-group">
					 <label for="employee_employeeNo" class="col-md-2 text-right">雇员编号:</label>
					 <div class="col-md-8"> 
					 	<input type="text" id="employee_employeeNo" name="employee.employeeNo" class="form-control" placeholder="请输入雇员编号">
					 </div>
				  </div> 
				  <div class="form-group">
				  	 <label for="employee_password" class="col-md-2 text-right">登录密码:</label>
				  	 <div class="col-md-8">
					    <input type="text" id="employee_password" name="employee.password" class="form-control" placeholder="请输入登录密码">
					 </div>
				  </div>
				  <div class="form-group">
				  	 <label for="employee_name" class="col-md-2 text-right">姓名:</label>
				  	 <div class="col-md-8">
					    <input type="text" id="employee_name" name="employee.name" class="form-control" placeholder="请输入姓名">
					 </div>
				  </div>
				  <div class="form-group">
				  	 <label for="employee_gender" class="col-md-2 text-right">性别:</label>
				  	 <div class="col-md-8">
					    <input type="text" id="employee_gender" name="employee.gender" class="form-control" placeholder="请输入性别">
					 </div>
				  </div>
				  <div class="form-group">
				  	 <label for="employee_birthDateDiv" class="col-md-2 text-right">出生日期:</label>
				  	 <div class="col-md-8">
		                <div id="employee_birthDateDiv" class="input-group date employee_birthDate col-md-12" data-link-field="employee_birthDate" data-link-format="yyyy-mm-dd">
		                    <input class="form-control" id="employee_birthDate" name="employee.birthDate" size="16" type="text" value="" placeholder="请选择出生日期" readonly>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                </div>
				  	 </div>
				  </div>
				  <div class="form-group">
				  	 <label for="employee_employeePhoto" class="col-md-2 text-right">雇员照片:</label>
				  	 <div class="col-md-8">
					    <img  class="img-responsive" id="employee_employeePhotoImg" border="0px"/><br/>
					    <input type="hidden" id="employee_employeePhoto" name="employee.employeePhoto"/>
					    <input id="employeePhotoFile" name="employeePhotoFile" type="file" size="50" />
				  	 </div>
				  </div>
				  <div class="form-group">
				  	 <label for="employee_telephone" class="col-md-2 text-right">联系电话:</label>
				  	 <div class="col-md-8">
					    <input type="text" id="employee_telephone" name="employee.telephone" class="form-control" placeholder="请输入联系电话">
					 </div>
				  </div>
				  <div class="form-group">
				  	 <label for="employee_email" class="col-md-2 text-right">邮箱:</label>
				  	 <div class="col-md-8">
					    <input type="text" id="employee_email" name="employee.email" class="form-control" placeholder="请输入邮箱">
					 </div>
				  </div>
				  <div class="form-group">
				  	 <label for="employee_address" class="col-md-2 text-right">家庭地址:</label>
				  	 <div class="col-md-8">
					    <input type="text" id="employee_address" name="employee.address" class="form-control" placeholder="请输入家庭地址">
					 </div>
				  </div>
				  <div class="form-group" style="display:none;">
				  	 <label for="employee_regTimeDiv" class="col-md-2 text-right">注册时间:</label>
				  	 <div class="col-md-8">
		                <div id="employee_regTimeDiv" class="input-group date employee_regTime col-md-12" data-link-field="employee_regTime">
		                    <input class="form-control" id="employee_regTime" name="employee.regTime" size="16" type="text" value="" placeholder="请选择注册时间" readonly>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
		                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
		                </div>
				  	 </div>
				  </div>
		          <div class="form-group">
		             <span class="col-md-2""></span>
		             <span onclick="ajaxEmployeeAdd();" class="btn btn-primary bottom5 top5">用户注册</span>
		          </div> 
		          <style>#employeeAddForm .form-group {margin:5px;}  </style>  
				</form> 
			</div>
			<div class="col-md-2"></div> 
	    </div>
	</div>
</div>
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrapvalidator/js/bootstrapValidator.min.js"></script>
<script type="text/javascript" src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script>
var basePath = "<%=basePath%>";
	//提交添加雇员信息
	function ajaxEmployeeAdd() { 
		//提交之前先验证表单
		$("#employeeAddForm").data('bootstrapValidator').validate();
		if(!$("#employeeAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "Employee/add",
			dataType : "json" , 
			data: new FormData($("#employeeAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#employeeAddForm").find("input").val("");
					$("#employeeAddForm").find("textarea").val("");
				} else {
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
	//验证雇员添加表单字段
	$('#employeeAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"employee.employeeNo": {
				validators: {
					notEmpty: {
						message: "雇员编号不能为空",
					}
				}
			},
			"employee.password": {
				validators: {
					notEmpty: {
						message: "登录密码不能为空",
					}
				}
			},
			"employee.name": {
				validators: {
					notEmpty: {
						message: "姓名不能为空",
					}
				}
			},
			"employee.gender": {
				validators: {
					notEmpty: {
						message: "性别不能为空",
					}
				}
			},
			"employee.birthDate": {
				validators: {
					notEmpty: {
						message: "出生日期不能为空",
					}
				}
			},
			"employee.telephone": {
				validators: {
					notEmpty: {
						message: "联系电话不能为空",
					}
				}
			},
			"employee.email": {
				validators: {
					notEmpty: {
						message: "邮箱不能为空",
					}
				}
			},
			 
		}
	}); 
	//出生日期组件
	$('#employee_birthDateDiv').datetimepicker({
		language:  'zh-CN',  //显示语言
		format: 'yyyy-mm-dd',
		minView: 2,
		weekStart: 1,
		todayBtn:  1,
		autoclose: 1,
		minuteStep: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0
	}).on('hide',function(e) {
		//下面这行代码解决日期组件改变日期后不验证的问题
		$('#employeeAddForm').data('bootstrapValidator').updateStatus('employee.birthDate', 'NOT_VALIDATED',null).validateField('employee.birthDate');
	});
	//注册时间组件
	$('#employee_regTimeDiv').datetimepicker({
		language:  'zh-CN',  //显示语言
		format: 'yyyy-mm-dd hh:ii:ss',
		weekStart: 1,
		todayBtn:  1,
		autoclose: 1,
		minuteStep: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0
	}).on('hide',function(e) {
		//下面这行代码解决日期组件改变日期后不验证的问题
		$('#employeeAddForm').data('bootstrapValidator').updateStatus('employee.regTime', 'NOT_VALIDATED',null).validateField('employee.regTime');
	});
})
</script>
</body>
</html>
