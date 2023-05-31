<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.LogInfo" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    LogInfo logInfo = (LogInfo)request.getAttribute("logInfo");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改系统日志信息</TITLE>
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
  		<li class="active">系统日志信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="logInfoEditForm" id="logInfoEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="logInfo_logId_edit" class="col-md-3 text-right">日志id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="logInfo_logId_edit" name="logInfo.logId" class="form-control" placeholder="请输入日志id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="logInfo_logType_edit" class="col-md-3 text-right">日志类型:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="logInfo_logType_edit" name="logInfo.logType" class="form-control" placeholder="请输入日志类型">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="logInfo_logContent_edit" class="col-md-3 text-right">日志内容:</label>
		  	 <div class="col-md-9">
			    <textarea id="logInfo_logContent_edit" name="logInfo.logContent" rows="8" class="form-control" placeholder="请输入日志内容"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="logInfo_logTime_edit" class="col-md-3 text-right">日志时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date logInfo_logTime_edit col-md-12" data-link-field="logInfo_logTime_edit">
                    <input class="form-control" id="logInfo_logTime_edit" name="logInfo.logTime" size="16" type="text" value="" placeholder="请选择日志时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxLogInfoModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#logInfoEditForm .form-group {margin-bottom:5px;}  </style>
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
/*弹出修改系统日志界面并初始化数据*/
function logInfoEdit(logId) {
	$.ajax({
		url :  basePath + "LogInfo/" + logId + "/update",
		type : "get",
		dataType: "json",
		success : function (logInfo, response, status) {
			if (logInfo) {
				$("#logInfo_logId_edit").val(logInfo.logId);
				$("#logInfo_logType_edit").val(logInfo.logType);
				$("#logInfo_logContent_edit").val(logInfo.logContent);
				$("#logInfo_logTime_edit").val(logInfo.logTime);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交系统日志信息表单给服务器端修改*/
function ajaxLogInfoModify() {
	$.ajax({
		url :  basePath + "LogInfo/" + $("#logInfo_logId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#logInfoEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#logInfoQueryForm").submit();
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
    /*日志时间组件*/
    $('.logInfo_logTime_edit').datetimepicker({
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
    logInfoEdit("<%=request.getParameter("logId")%>");
 })
 </script> 
</body>
</html>

