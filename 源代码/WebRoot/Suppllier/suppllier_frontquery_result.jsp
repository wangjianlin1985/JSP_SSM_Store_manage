﻿<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Suppllier" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Suppllier> suppllierList = (List<Suppllier>)request.getAttribute("suppllierList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String supplierName = (String)request.getAttribute("supplierName"); //供应商名称查询关键字
    String supplierLawyer = (String)request.getAttribute("supplierLawyer"); //法人代表查询关键字
    String supplierTelephone = (String)request.getAttribute("supplierTelephone"); //供应商电话查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>供应商查询</title>
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
			    	<li role="presentation" class="active"><a href="#suppllierListPanel" aria-controls="suppllierListPanel" role="tab" data-toggle="tab">供应商列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>Suppllier/suppllier_frontAdd.jsp" style="display:none;">添加供应商</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="suppllierListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>供应商id</td><td>供应商名称</td><td>法人代表</td><td>供应商电话</td><td>供应商地址</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<suppllierList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		Suppllier suppllier = suppllierList.get(i); //获取到供应商对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=suppllier.getSupplierId() %></td>
 											<td><%=suppllier.getSupplierName() %></td>
 											<td><%=suppllier.getSupplierLawyer() %></td>
 											<td><%=suppllier.getSupplierTelephone() %></td>
 											<td><%=suppllier.getSupplierAddress() %></td>
 											<td>
 												<a href="<%=basePath  %>Suppllier/<%=suppllier.getSupplierId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="suppllierEdit('<%=suppllier.getSupplierId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="suppllierDelete('<%=suppllier.getSupplierId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
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
    		<h1>供应商查询</h1>
		</div>
		<form name="suppllierQueryForm" id="suppllierQueryForm" action="<%=basePath %>Suppllier/frontlist" class="mar_t15">
			<div class="form-group">
				<label for="supplierName">供应商名称:</label>
				<input type="text" id="supplierName" name="supplierName" value="<%=supplierName %>" class="form-control" placeholder="请输入供应商名称">
			</div>






			<div class="form-group">
				<label for="supplierLawyer">法人代表:</label>
				<input type="text" id="supplierLawyer" name="supplierLawyer" value="<%=supplierLawyer %>" class="form-control" placeholder="请输入法人代表">
			</div>






			<div class="form-group">
				<label for="supplierTelephone">供应商电话:</label>
				<input type="text" id="supplierTelephone" name="supplierTelephone" value="<%=supplierTelephone %>" class="form-control" placeholder="请输入供应商电话">
			</div>






            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="suppllierEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;供应商信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="suppllierEditForm" id="suppllierEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="suppllier_supplierId_edit" class="col-md-3 text-right">供应商id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="suppllier_supplierId_edit" name="suppllier.supplierId" class="form-control" placeholder="请输入供应商id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="suppllier_supplierName_edit" class="col-md-3 text-right">供应商名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="suppllier_supplierName_edit" name="suppllier.supplierName" class="form-control" placeholder="请输入供应商名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="suppllier_supplierLawyer_edit" class="col-md-3 text-right">法人代表:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="suppllier_supplierLawyer_edit" name="suppllier.supplierLawyer" class="form-control" placeholder="请输入法人代表">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="suppllier_supplierTelephone_edit" class="col-md-3 text-right">供应商电话:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="suppllier_supplierTelephone_edit" name="suppllier.supplierTelephone" class="form-control" placeholder="请输入供应商电话">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="suppllier_supplierAddress_edit" class="col-md-3 text-right">供应商地址:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="suppllier_supplierAddress_edit" name="suppllier.supplierAddress" class="form-control" placeholder="请输入供应商地址">
			 </div>
		  </div>
		</form> 
	    <style>#suppllierEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxSuppllierModify();">提交</button>
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
    document.suppllierQueryForm.currentPage.value = currentPage;
    document.suppllierQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.suppllierQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.suppllierQueryForm.currentPage.value = pageValue;
    documentsuppllierQueryForm.submit();
}

/*弹出修改供应商界面并初始化数据*/
function suppllierEdit(supplierId) {
	$.ajax({
		url :  basePath + "Suppllier/" + supplierId + "/update",
		type : "get",
		dataType: "json",
		success : function (suppllier, response, status) {
			if (suppllier) {
				$("#suppllier_supplierId_edit").val(suppllier.supplierId);
				$("#suppllier_supplierName_edit").val(suppllier.supplierName);
				$("#suppllier_supplierLawyer_edit").val(suppllier.supplierLawyer);
				$("#suppllier_supplierTelephone_edit").val(suppllier.supplierTelephone);
				$("#suppllier_supplierAddress_edit").val(suppllier.supplierAddress);
				$('#suppllierEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除供应商信息*/
function suppllierDelete(supplierId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Suppllier/deletes",
			data : {
				supplierIds : supplierId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#suppllierQueryForm").submit();
					//location.href= basePath + "Suppllier/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交供应商信息表单给服务器端修改*/
function ajaxSuppllierModify() {
	$.ajax({
		url :  basePath + "Suppllier/" + $("#suppllier_supplierId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#suppllierEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#suppllierQueryForm").submit();
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

})
</script>
</body>
</html>

