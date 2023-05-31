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
<title>资料文件添加</title>
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
			    	<li role="presentation" ><a href="<%=basePath %>Ziliao/frontlist">资料文件列表</a></li>
			    	<li role="presentation" class="active"><a href="#ziliaoAdd" aria-controls="ziliaoAdd" role="tab" data-toggle="tab">添加资料文件</a></li>
				</ul>
				<!-- Tab panes -->
				<div class="tab-content">
				    <div role="tabpanel" class="tab-pane" id="ziliaoList">
				    </div>
				    <div role="tabpanel" class="tab-pane active" id="ziliaoAdd"> 
				      	<form class="form-horizontal" name="ziliaoAddForm" id="ziliaoAddForm" enctype="multipart/form-data" method="post"  class="mar_t15">
						  <div class="form-group">
						  	 <label for="ziliao_title" class="col-md-2 text-right">标题:</label>
						  	 <div class="col-md-8">
							    <input type="text" id="ziliao_title" name="ziliao.title" class="form-control" placeholder="请输入标题">
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="ziliao_content" class="col-md-2 text-right">描述:</label>
						  	 <div class="col-md-8">
							    <textarea name="ziliao.content" id="ziliao_content" style="width:100%;height:500px;"></textarea>
							 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="ziliao_ziliaoFile" class="col-md-2 text-right">资料文件:</label>
						  	 <div class="col-md-8">
							    <a id="ziliao_ziliaoFileImg" border="0px"></a><br/>
							    <input type="hidden" id="ziliao_ziliaoFile" name="ziliao.ziliaoFile"/>
							    <input id="ziliaoFileFile" name="ziliaoFileFile" type="file" size="50" />
						  	 </div>
						  </div>
						  <div class="form-group">
						  	 <label for="ziliao_addTimeDiv" class="col-md-2 text-right">添加时间:</label>
						  	 <div class="col-md-8">
				                <div id="ziliao_addTimeDiv" class="input-group date ziliao_addTime col-md-12" data-link-field="ziliao_addTime">
				                    <input class="form-control" id="ziliao_addTime" name="ziliao.addTime" size="16" type="text" value="" placeholder="请选择添加时间" readonly>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div>
						  	 </div>
						  </div>
				          <div class="form-group">
				             <span class="col-md-2""></span>
				             <span onclick="ajaxZiliaoAdd();" class="btn btn-primary bottom5 top5">添加</span>
				          </div>
						</form> 
				        <style>#ziliaoAddForm .form-group {margin:10px;}  </style>
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
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
//实例化编辑器
var ziliao_content_editor = UE.getEditor('ziliao_content'); //描述编辑器
var basePath = "<%=basePath%>";
	//提交添加资料文件信息
	function ajaxZiliaoAdd() { 
		//提交之前先验证表单
		$("#ziliaoAddForm").data('bootstrapValidator').validate();
		if(!$("#ziliaoAddForm").data('bootstrapValidator').isValid()){
			return;
		}
		if(ziliao_content_editor.getContent() == "") {
			alert('描述不能为空');
			return;
		}
		jQuery.ajax({
			type : "post",
			url : basePath + "Ziliao/add",
			dataType : "json" , 
			data: new FormData($("#ziliaoAddForm")[0]),
			success : function(obj) {
				if(obj.success){ 
					alert("保存成功！");
					$("#ziliaoAddForm").find("input").val("");
					$("#ziliaoAddForm").find("textarea").val("");
					ziliao_content_editor.setContent("");
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
	//验证资料文件添加表单字段
	$('#ziliaoAddForm').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			"ziliao.title": {
				validators: {
					notEmpty: {
						message: "标题不能为空",
					}
				}
			},
			"ziliao.addTime": {
				validators: {
					notEmpty: {
						message: "添加时间不能为空",
					}
				}
			},
		}
	}); 
	//添加时间组件
	$('#ziliao_addTimeDiv').datetimepicker({
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
		$('#ziliaoAddForm').data('bootstrapValidator').updateStatus('ziliao.addTime', 'NOT_VALIDATED',null).validateField('ziliao.addTime');
	});
})
</script>
</body>
</html>
