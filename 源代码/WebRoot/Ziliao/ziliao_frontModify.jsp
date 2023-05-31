<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Ziliao" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Ziliao ziliao = (Ziliao)request.getAttribute("ziliao");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改资料文件信息</TITLE>
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
  		<li class="active">资料文件信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="ziliaoEditForm" id="ziliaoEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="ziliao_ziliaoId_edit" class="col-md-3 text-right">资料id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="ziliao_ziliaoId_edit" name="ziliao.ziliaoId" class="form-control" placeholder="请输入资料id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="ziliao_title_edit" class="col-md-3 text-right">标题:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="ziliao_title_edit" name="ziliao.title" class="form-control" placeholder="请输入标题">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="ziliao_content_edit" class="col-md-3 text-right">描述:</label>
		  	 <div class="col-md-9">
			    <script name="ziliao.content" id="ziliao_content_edit" type="text/plain"   style="width:100%;height:500px;"></script>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="ziliao_ziliaoFile_edit" class="col-md-3 text-right">资料文件:</label>
		  	 <div class="col-md-9">
			    <a id="ziliao_ziliaoFileImg" width="200px" border="0px"></a><br/>
			    <input type="hidden" id="ziliao_ziliaoFile" name="ziliao.ziliaoFile"/>
			    <input id="ziliaoFileFile" name="ziliaoFileFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="ziliao_addTime_edit" class="col-md-3 text-right">添加时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date ziliao_addTime_edit col-md-12" data-link-field="ziliao_addTime_edit">
                    <input class="form-control" id="ziliao_addTime_edit" name="ziliao.addTime" size="16" type="text" value="" placeholder="请选择添加时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxZiliaoModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#ziliaoEditForm .form-group {margin-bottom:5px;}  </style>
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
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
var ziliao_content_editor = UE.getEditor('ziliao_content_edit'); //描述编辑框
var basePath = "<%=basePath%>";
/*弹出修改资料文件界面并初始化数据*/
function ziliaoEdit(ziliaoId) {
  ziliao_content_editor.addListener("ready", function () {
    // editor准备好之后才可以使用 
    ajaxModifyQuery(ziliaoId);
  });
}
 function ajaxModifyQuery(ziliaoId) {
	$.ajax({
		url :  basePath + "Ziliao/" + ziliaoId + "/update",
		type : "get",
		dataType: "json",
		success : function (ziliao, response, status) {
			if (ziliao) {
				$("#ziliao_ziliaoId_edit").val(ziliao.ziliaoId);
				$("#ziliao_title_edit").val(ziliao.title);
				ziliao_content_editor.setContent(ziliao.content, false);
				$("#ziliao_ziliaoFileA").val(ziliao.ziliaoFile);
				$("#ziliao_ziliaoFileA").attr("href", basePath +　ziliao.ziliaoFile);
				$("#ziliao_addTime_edit").val(ziliao.addTime);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交资料文件信息表单给服务器端修改*/
function ajaxZiliaoModify() {
	$.ajax({
		url :  basePath + "Ziliao/" + $("#ziliao_ziliaoId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#ziliaoEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#ziliaoQueryForm").submit();
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
    /*添加时间组件*/
    $('.ziliao_addTime_edit').datetimepicker({
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
    ziliaoEdit("<%=request.getParameter("ziliaoId")%>");
 })
 </script> 
</body>
</html>

