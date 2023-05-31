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
<title>供应商添加</title>
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
			    	<li role="presentation" ><a href="<%=basePath %>Suppllier/frontlist">供应商列表</a></li>
			    	<li role="presentation" class="active"><a href="#suppllierAdd" aria-controls="suppllierAdd" role="tab" data-toggle="tab">添加供应商</a></li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane" id="suppllierList">
				    </div>
				    <div role="tabpanel" class="tab-pane active" id="suppllierAdd"> 
				      	<form class="form-horizontal" name="suppllierAddForm" id="suppllierAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
						  <div class="form-group">
						  	 <label for="suppllier_supplierName" class="col-md-2 text-right">供应商名称:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="suppllier_supplierName" name="suppllier.supplierName" class="form-control" placeholder="请输入供应商名称">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="suppllier_supplierLawyer" class="col-md-2 text-right">法人代表:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="suppllier_supplierLawyer" name="suppllier.supplierLawyer" class="form-control" placeholder="请输入法人代表">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="suppllier_supplierTelephone" class="col-md-2 text-right">供应商电话:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="suppllier_supplierTelephone" name="suppllier.supplierTelephone" class="form-control" placeholder="请输入供应商电话">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="suppllier_supplierAddress" class="col-md-2 text-right">供应商地址:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="suppllier_supplierAddress" name="suppllier.supplierAddress" class="form-control" placeholder="请输入供应商地址">
							 </div>
						  </div>
				          <div class="form-group">
				             <span class="col-md-2""></span>
				             <span onclick="ajaxSuppllierAdd();" class="btn btn-primary bottom5 top5">添加</span>
				          </div>
						</form> 
				        <style>#suppllierAddForm .form-group {margin:10px;}  </style>
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
	//提交添加供应商信息
	function ajaxSuppllierAdd() { 
		//提交之前先验证表单
		$("#suppllierAddForm").data('bootstrapValidator').validate();
		if(!$("#suppllierAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "Suppllier/add",
			dataType : "json" , 
			data: new FormData($("#suppllierAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#suppllierAddForm").find("input").val("");
					$("#suppllierAddForm").find("textarea").val("");
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
	//验证供应商添加表单字段
	$('#suppllierAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"suppllier.supplierName": {
				validators: {
					notEmpty: {
						message: "供应商名称不能为空",
					}
				}
			},
			"suppllier.supplierLawyer": {
				validators: {
					notEmpty: {
						message: "法人代表不能为空",
					}
				}
			},
			"suppllier.supplierTelephone": {
				validators: {
					notEmpty: {
						message: "供应商电话不能为空",
					}
				}
			},
			"suppllier.supplierAddress": {
				validators: {
					notEmpty: {
						message: "供应商地址不能为空",
					}
				}
			},
		}
	}); 
})
</script>
</body>
</html>
