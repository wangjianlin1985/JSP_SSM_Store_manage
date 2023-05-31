<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Member" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Member member = (Member)request.getAttribute("member");

%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
  <TITLE>修改会员信息</TITLE>
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
  		<li class="active">会员信息修改</li>
	</ul>
		<div class="row"> 
      	<form class="form-horizontal" name="memberEditForm" id="memberEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="member_memberUserName_edit" class="col-md-3 text-right">会员名:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="member_memberUserName_edit" name="member.memberUserName" class="form-control" placeholder="请输入会员名" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="member_name_edit" class="col-md-3 text-right">姓名:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="member_name_edit" name="member.name" class="form-control" placeholder="请输入姓名">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="member_gender_edit" class="col-md-3 text-right">性别:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="member_gender_edit" name="member.gender" class="form-control" placeholder="请输入性别">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="member_birthDate_edit" class="col-md-3 text-right">出生日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date member_birthDate_edit col-md-12" data-link-field="member_birthDate_edit" data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="member_birthDate_edit" name="member.birthDate" size="16" type="text" value="" placeholder="请选择出生日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="member_telephone_edit" class="col-md-3 text-right">联系电话:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="member_telephone_edit" name="member.telephone" class="form-control" placeholder="请输入联系电话">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="member_address_edit" class="col-md-3 text-right">住宅地址:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="member_address_edit" name="member.address" class="form-control" placeholder="请输入住宅地址">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="member_memberMemo_edit" class="col-md-3 text-right">会员备注:</label>
		  	 <div class="col-md-9">
			    <textarea id="member_memberMemo_edit" name="member.memberMemo" rows="8" class="form-control" placeholder="请输入会员备注"></textarea>
			 </div>
		  </div>
			  <div class="form-group">
			  	<span class="col-md-3""></span>
			  	<span onclick="ajaxMemberModify();" class="btn btn-primary bottom5 top5">修改</span>
			  </div>
		</form> 
	    <style>#memberEditForm .form-group {margin-bottom:5px;}  </style>
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
/*弹出修改会员界面并初始化数据*/
function memberEdit(memberUserName) {
	$.ajax({
		url :  basePath + "Member/" + memberUserName + "/update",
		type : "get",
		dataType: "json",
		success : function (member, response, status) {
			if (member) {
				$("#member_memberUserName_edit").val(member.memberUserName);
				$("#member_name_edit").val(member.name);
				$("#member_gender_edit").val(member.gender);
				$("#member_birthDate_edit").val(member.birthDate);
				$("#member_telephone_edit").val(member.telephone);
				$("#member_address_edit").val(member.address);
				$("#member_memberMemo_edit").val(member.memberMemo);
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*ajax方式提交会员信息表单给服务器端修改*/
function ajaxMemberModify() {
	$.ajax({
		url :  basePath + "Member/" + $("#member_memberUserName_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#memberEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                location.reload(true);
                $("#memberQueryForm").submit();
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
    /*出生日期组件*/
    $('.member_birthDate_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd',
    	minView: 2,
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
    memberEdit("<%=request.getParameter("memberUserName")%>");
 })
 </script> 
</body>
</html>

