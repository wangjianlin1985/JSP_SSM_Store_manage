<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.LogInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<LogInfo> logInfoList = (List<LogInfo>)request.getAttribute("logInfoList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String logType = (String)request.getAttribute("logType"); //日志类型查询关键字
    String logTime = (String)request.getAttribute("logTime"); //日志时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>系统日志查询</title>
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
			    	<li role="presentation" class="active"><a href="#logInfoListPanel" aria-controls="logInfoListPanel" role="tab" data-toggle="tab">系统日志列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>LogInfo/logInfo_frontAdd.jsp" style="display:none;">添加系统日志</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="logInfoListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>日志id</td><td>日志类型</td><td>日志内容</td><td>日志时间</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<logInfoList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		LogInfo logInfo = logInfoList.get(i); //获取到系统日志对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=logInfo.getLogId() %></td>
 											<td><%=logInfo.getLogType() %></td>
 											<td><%=logInfo.getLogContent() %></td>
 											<td><%=logInfo.getLogTime() %></td>
 											<td>
 												<a href="<%=basePath  %>LogInfo/<%=logInfo.getLogId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="logInfoEdit('<%=logInfo.getLogId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="logInfoDelete('<%=logInfo.getLogId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
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
    		<h1>系统日志查询</h1>
		</div>
		<form name="logInfoQueryForm" id="logInfoQueryForm" action="<%=basePath %>LogInfo/frontlist" class="mar_t15">
			<div class="form-group">
				<label for="logType">日志类型:</label>
				<input type="text" id="logType" name="logType" value="<%=logType %>" class="form-control" placeholder="请输入日志类型">
			</div>






			<div class="form-group">
				<label for="logTime">日志时间:</label>
				<input type="text" id="logTime" name="logTime" class="form-control"  placeholder="请选择日志时间" value="<%=logTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="logInfoEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;系统日志信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
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
		</form> 
	    <style>#logInfoEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxLogInfoModify();">提交</button>
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
    document.logInfoQueryForm.currentPage.value = currentPage;
    document.logInfoQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.logInfoQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.logInfoQueryForm.currentPage.value = pageValue;
    documentlogInfoQueryForm.submit();
}

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
				$('#logInfoEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除系统日志信息*/
function logInfoDelete(logId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "LogInfo/deletes",
			data : {
				logIds : logId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#logInfoQueryForm").submit();
					//location.href= basePath + "LogInfo/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
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
})
</script>
</body>
</html>

