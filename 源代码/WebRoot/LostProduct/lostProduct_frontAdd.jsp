<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Employee" %>
<%@ page import="com.chengxusheji.po.Product" %>
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
<title>丢失物品添加</title>
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
			    	<li role="presentation" ><a href="<%=basePath %>LostProduct/frontlist">丢失物品列表</a></li>
			    	<li role="presentation" class="active"><a href="#lostProductAdd" aria-controls="lostProductAdd" role="tab" data-toggle="tab">添加丢失物品</a></li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane" id="lostProductList">
				    </div>
				    <div role="tabpanel" class="tab-pane active" id="lostProductAdd"> 
				      	<form class="form-horizontal" name="lostProductAddForm" id="lostProductAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
						  <div class="form-group">
						  	 <label for="lostProduct_productObj_productId" class="col-md-2 text-right">丢失物品:</label>
						  	 <div class="col-md-8">
							    <select id="lostProduct_productObj_productId" name="lostProduct.productObj.productId" class="form-control">
							    </select>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="lostProduct_lostNumber" class="col-md-2 text-right">丢失数量:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="lostProduct_lostNumber" name="lostProduct.lostNumber" class="form-control" placeholder="请输入丢失数量">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="lostProduct_lostTimeDiv" class="col-md-2 text-right">丢失时间:</label>
						  	 <div class="col-md-8">
				                <div id="lostProduct_lostTimeDiv" class="input-group date lostProduct_lostTime col-md-12" data-link-field="lostProduct_lostTime">
				                    <input class="form-control" id="lostProduct_lostTime" name="lostProduct.lostTime" size="16" type="text" value="" placeholder="请选择丢失时间" readonly>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="lostProduct_lostPlace" class="col-md-2 text-right">丢失地点:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="lostProduct_lostPlace" name="lostProduct.lostPlace" class="form-control" placeholder="请输入丢失地点">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="lostProduct_productMoney" class="col-md-2 text-right">总价值:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="lostProduct_productMoney" name="lostProduct.productMoney" class="form-control" placeholder="请输入总价值">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="lostProduct_employeeObj_employeeNo" class="col-md-2 text-right">操作员:</label>
						  	 <div class="col-md-8">
							    <select id="lostProduct_employeeObj_employeeNo" name="lostProduct.employeeObj.employeeNo" class="form-control">
							    </select>
						  	 </div>
						  </div>
				          <div class="form-group">
				             <span class="col-md-2""></span>
				             <span onclick="ajaxLostProductAdd();" class="btn btn-primary bottom5 top5">添加</span>
				          </div>
						</form> 
				        <style>#lostProductAddForm .form-group {margin:10px;}  </style>
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
	//提交添加丢失物品信息
	function ajaxLostProductAdd() { 
		//提交之前先验证表单
		$("#lostProductAddForm").data('bootstrapValidator').validate();
		if(!$("#lostProductAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "LostProduct/add",
			dataType : "json" , 
			data: new FormData($("#lostProductAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#lostProductAddForm").find("input").val("");
					$("#lostProductAddForm").find("textarea").val("");
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
	//验证丢失物品添加表单字段
	$('#lostProductAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"lostProduct.lostNumber": {
				validators: {
					notEmpty: {
						message: "丢失数量不能为空",
					},
					integer: {
						message: "丢失数量不正确"
					}
				}
			},
			"lostProduct.lostTime": {
				validators: {
					notEmpty: {
						message: "丢失时间不能为空",
					}
				}
			},
			"lostProduct.lostPlace": {
				validators: {
					notEmpty: {
						message: "丢失地点不能为空",
					}
				}
			},
			"lostProduct.productMoney": {
				validators: {
					notEmpty: {
						message: "总价值不能为空",
					},
					numeric: {
						message: "总价值不正确"
					}
				}
			},
		}
	}); 
	//初始化丢失物品下拉框值 
	$.ajax({
		url: basePath + "Product/listAll",
		type: "get",
		success: function(products,response,status) { 
			$("#lostProduct_productObj_productId").empty();
			var html="";
    		$(products).each(function(i,product){
    			html += "<option value='" + product.productId + "'>" + product.productName + "</option>";
    		});
    		$("#lostProduct_productObj_productId").html(html);
    	}
	});
	//初始化操作员下拉框值 
	$.ajax({
		url: basePath + "Employee/listAll",
		type: "get",
		success: function(employees,response,status) { 
			$("#lostProduct_employeeObj_employeeNo").empty();
			var html="";
    		$(employees).each(function(i,employee){
    			html += "<option value='" + employee.employeeNo + "'>" + employee.name + "</option>";
    		});
    		$("#lostProduct_employeeObj_employeeNo").html(html);
    	}
	});
	//丢失时间组件
	$('#lostProduct_lostTimeDiv').datetimepicker({
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
		$('#lostProductAddForm').data('bootstrapValidator').updateStatus('lostProduct.lostTime', 'NOT_VALIDATED',null).validateField('lostProduct.lostTime');
	});
})
</script>
</body>
</html>
