<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ziliao.css" />
<div id="ziliaoAddDiv">
	<form id="ziliaoAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">标题:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ziliao_title" name="ziliao.title" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">描述:</span>
			<span class="inputControl">
				<script name="ziliao.content" id="ziliao_content" type="text/plain"   style="width:750px;height:500px;"></script>
			</span>

		</div>
		<div>
			<span class="label">资料文件:</span>
			<span class="inputControl">
				<input id="ziliaoFileFile" name="ziliaoFileFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">添加时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ziliao_addTime" name="ziliao.addTime" />

			</span>

		</div>
		<div class="operation">
			<a id="ziliaoAddButton" class="easyui-linkbutton">添加</a>
			<a id="ziliaoClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Ziliao/js/ziliao_add.js"></script> 
