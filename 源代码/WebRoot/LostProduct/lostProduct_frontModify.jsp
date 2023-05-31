<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.LostProduct" %>
<%@ page import="com.chengxusheji.po.Employee" %>
<%@ page import="com.chengxusheji.po.Product" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的employeeObj信息
    List<Employee> employeeList = (List<Employee>)request.getAttribute("employeeList");
    //获取所有的productObj信息
    List<Product> productList = (List<Product>)request.getAttribute("productList");
    LostProduct lostProduct = (LostProduct)request.getAttribute("lostProduct");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改丢失物品信息</TITLE>
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
  		<li class="active">丢失物品信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="lostProductEditForm" id="lostProductEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="lostProduct_lostId_edit" class="col-md-3 text-right">记录id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="lostProduct_lostId_edit" name="lostProduct.lostId" class="form-control" placeholder="请输入记录id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="lostProduct_productObj_productId_edit" class="col-md-3 text-right">丢失物品:</label>
		  	 <div class="col-md-9">
			    <select id="lostProduct_productObj_productId_edit" name="lostProduct.productObj.productId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="lostProduct_lostNumber_edit" class="col-md-3 text-right">丢失数量:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="lostProduct_lostNumber_edit" name="lostProduct.lostNumber" class="form-control" placeholder="请输入丢失数量">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="lostProduct_lostTime_edit" class="col-md-3 text-right">丢失时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date lostProduct_lostTime_edit col-md-12" data-link-field="lostProduct_lostTime_edit">
                    <input class="form-control" id="lostProduct_lostTime_edit" name="lostProduct.lostTime" size="16" type="text" value="" placeholder="请选择丢失时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="lostProduct_lostPlace_edit" class="col-md-3 text-right">丢失地点:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="lostProduct_lostPlace_edit" name="lostProduct.lostPlace" class="form-control" placeholder="请输入丢失地点">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="lostProduct_productMoney_edit" class="col-md-3 text-right">总价值:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="lostProduct_productMoney_edit" name="lostProduct.productMoney" class="form-control" placeholder="请输入总价值">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="lostProduct_employeeObj_employeeNo_edit" class="col-md-3 text-right">操作员:</label>
		  	 <div class="col-md-9">
			    <select id="lostProduct_employeeObj_employeeNo_edit" name="lostProduct.employeeObj.employeeNo" class="form-control">
			    </select>
		  	 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxLostProductModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#lostProductEditForm .form-group {margin-bottom:5px;}  </style>
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
/*弹出修改丢失物品界面并初始化数据*/
function lostProductEdit(lostId) {
	$.ajax({
		url :  basePath + "LostProduct/" + lostId + "/update",
		type : "get",
		dataType: "json",
		success : function (lostProduct, response, status) {
			if (lostProduct) {
				$("#lostProduct_lostId_edit").val(lostProduct.lostId);
				$.ajax({
					url: basePath + "Product/listAll",
					type: "get",
					success: function(products,response,status) { 
						$("#lostProduct_productObj_productId_edit").empty();
						var html="";
		        		$(products).each(function(i,product){
		        			html += "<option value='" + product.productId + "'>" + product.productName + "</option>";
		        		});
		        		$("#lostProduct_productObj_productId_edit").html(html);
		        		$("#lostProduct_productObj_productId_edit").val(lostProduct.productObjPri);
					}
				});
				$("#lostProduct_lostNumber_edit").val(lostProduct.lostNumber);
				$("#lostProduct_lostTime_edit").val(lostProduct.lostTime);
				$("#lostProduct_lostPlace_edit").val(lostProduct.lostPlace);
				$("#lostProduct_productMoney_edit").val(lostProduct.productMoney);
				$.ajax({
					url: basePath + "Employee/listAll",
					type: "get",
					success: function(employees,response,status) { 
						$("#lostProduct_employeeObj_employeeNo_edit").empty();
						var html="";
		        		$(employees).each(function(i,employee){
		        			html += "<option value='" + employee.employeeNo + "'>" + employee.name + "</option>";
		        		});
		        		$("#lostProduct_employeeObj_employeeNo_edit").html(html);
		        		$("#lostProduct_employeeObj_employeeNo_edit").val(lostProduct.employeeObjPri);
					}
				});
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交丢失物品信息表单给服务器端修改*/
function ajaxLostProductModify() {
	$.ajax({
		url :  basePath + "LostProduct/" + $("#lostProduct_lostId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#lostProductEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#lostProductQueryForm").submit();
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
    /*丢失时间组件*/
    $('.lostProduct_lostTime_edit').datetimepicker({
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
    lostProductEdit("<%=request.getParameter("lostId")%>");
 })
 </script> 
</body>
</html>

