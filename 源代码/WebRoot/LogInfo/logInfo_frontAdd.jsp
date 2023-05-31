<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
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
<title>系统日志添加</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<jsp:include page="../header.jsp"></jsp:include>
<div class="container">
	<div class="row">
		<div class="col-md-12 wow fadeInUp" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li role="presentation" ><a href="<%=basePath %>LogInfo/frontlist">系统日志列表</a></li>
			    	<li role="presentation" class="active"><a href="#logInfoAdd" aria-controls="logInfoAdd" role="tab" data-toggle="tab">添加系统日志</a></li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane" id="logInfoList">
				    </div>
				    <div role="tabpanel" class="tab-pane active" id="logInfoAdd"> 
				      	<form class="form-horizontal" name="logInfoAddForm" id="logInfoAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
						  <div class="form-group">
						  	 <label for="logInfo_logType" class="col-md-2 text-right">日志类型:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="logInfo_logType" name="logInfo.logType" class="form-control" placeholder="请输入日志类型">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="logInfo_logContent" class="col-md-2 text-right">日志内容:</label>
						  	 <div class="col-md-8">
							    <textarea id="logInfo_logContent" name="logInfo.logContent" rows="8" class="form-control" placeholder="请输入日志内容"></textarea>
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="logInfo_logTimeDiv" class="col-md-2 text-right">日志时间:</label>
						  	 <div class="col-md-8">
				                <div id="logInfo_logTimeDiv" class="input-group date logInfo_logTime col-md-12" data-link-field="logInfo_logTime">
				                    <input class="form-control" id="logInfo_logTime" name="logInfo.logTime" size="16" type="text" value="" placeholder="请选择日志时间" readonly>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
						  	 </div>
						  </div>
				          <div class="form-group">
				             <span class="col-md-2""></span>
				             <span onclick="ajaxLogInfoAdd();" class="btn btn-primary bottom5 top5">添加</span>
				          </div>
						</form> 
				        <style>#logInfoAddForm .form-group {margin:10px;}  </style>
					</div>
				</div>
			</div>
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
	//提交添加系统日志信息
	function ajaxLogInfoAdd() { 
		//提交之前先验证表单
		$("#logInfoAddForm").data('bootstrapValidator').validate();
		if(!$("#logInfoAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "LogInfo/add",
			dataType : "json" , 
			data: new FormData($("#logInfoAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#logInfoAddForm").find("input").val("");
					$("#logInfoAddForm").find("textarea").val("");
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
	//验证系统日志添加表单字段
	$('#logInfoAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"logInfo.logType": {
				validators: {
					notEmpty: {
						message: "日志类型不能为空",
					}
				}
			},
			"logInfo.logContent": {
				validators: {
					notEmpty: {
						message: "日志内容不能为空",
					}
				}
			},
			"logInfo.logTime": {
				validators: {
					notEmpty: {
						message: "日志时间不能为空",
					}
				}
			},
		}
	}); 
	//日志时间组件
	$('#logInfo_logTimeDiv').datetimepicker({
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
		$('#logInfoAddForm').data('bootstrapValidator').updateStatus('logInfo.logTime', 'NOT_VALIDATED',null).validateField('logInfo.logTime');
	});
})
</script>
</body>
</html>
