<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.LostProduct" %>
<%@ page import="com.chengxusheji.po.Employee" %>
<%@ page import="com.chengxusheji.po.Product" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<LostProduct> lostProductList = (List<LostProduct>)request.getAttribute("lostProductList");
    //获取所有的employeeObj信息
    List<Employee> employeeList = (List<Employee>)request.getAttribute("employeeList");
    //获取所有的productObj信息
    List<Product> productList = (List<Product>)request.getAttribute("productList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    Product productObj = (Product)request.getAttribute("productObj");
    String lostTime = (String)request.getAttribute("lostTime"); //丢失时间查询关键字
    Employee employeeObj = (Employee)request.getAttribute("employeeObj");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>丢失物品查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#lostProductListPanel" aria-controls="lostProductListPanel" role="tab" data-toggle="tab">丢失物品列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>LostProduct/lostProduct_frontAdd.jsp" style="display:none;">添加丢失物品</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="lostProductListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>记录id</td><td>丢失物品</td><td>丢失数量</td><td>丢失时间</td><td>丢失地点</td><td>总价值</td><td>操作员</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<lostProductList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		LostProduct lostProduct = lostProductList.get(i); //获取到丢失物品对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=lostProduct.getLostId() %></td>
 											<td><%=lostProduct.getProductObj().getProductName() %></td>
 											<td><%=lostProduct.getLostNumber() %></td>
 											<td><%=lostProduct.getLostTime() %></td>
 											<td><%=lostProduct.getLostPlace() %></td>
 											<td><%=lostProduct.getProductMoney() %></td>
 											<td><%=lostProduct.getEmployeeObj().getName() %></td>
 											<td>
 												<a href="<%=basePath  %>LostProduct/<%=lostProduct.getLostId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="lostProductEdit('<%=lostProduct.getLostId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="lostProductDelete('<%=lostProduct.getLostId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>丢失物品查询</h1>
		</div>
		<form name="lostProductQueryForm" id="lostProductQueryForm" action="<%=basePath %>LostProduct/frontlist" class="mar_t15">
            <div class="form-group">
            	<label for="productObj_productId">丢失物品：</label>
                <select id="productObj_productId" name="productObj.productId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(Product productTemp:productList) {
	 					String selected = "";
 					if(productObj!=null && productObj.getProductId()!=null && productObj.getProductId().intValue()==productTemp.getProductId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=productTemp.getProductId() %>" <%=selected %>><%=productTemp.getProductName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="lostTime">丢失时间:</label>
				<input type="text" id="lostTime" name="lostTime" class="form-control"  placeholder="请选择丢失时间" value="<%=lostTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <div class="form-group">
            	<label for="employeeObj_employeeNo">操作员：</label>
                <select id="employeeObj_employeeNo" name="employeeObj.employeeNo" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(Employee employeeTemp:employeeList) {
	 					String selected = "";
 					if(employeeObj!=null && employeeObj.getEmployeeNo()!=null && employeeObj.getEmployeeNo().equals(employeeTemp.getEmployeeNo()))
 						selected = "selected";
	 				%>
 				 <option value="<%=employeeTemp.getEmployeeNo() %>" <%=selected %>><%=employeeTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="lostProductEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;丢失物品信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
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
		</form> 
	    <style>#lostProductEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxLostProductModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.lostProductQueryForm.currentPage.value = currentPage;
    document.lostProductQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.lostProductQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.lostProductQueryForm.currentPage.value = pageValue;
    documentlostProductQueryForm.submit();
}

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
				$('#lostProductEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除丢失物品信息*/
function lostProductDelete(lostId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "LostProduct/deletes",
			data : {
				lostIds : lostId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#lostProductQueryForm").submit();
					//location.href= basePath + "LostProduct/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
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
})
</script>
</body>
</html>

