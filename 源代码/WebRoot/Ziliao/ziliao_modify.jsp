<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ziliao.css" />
<div id="ziliao_editDiv">
	<form id="ziliaoEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">资料id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ziliao_ziliaoId_edit" name="ziliao.ziliaoId" value="<%=request.getParameter("ziliaoId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">标题:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ziliao_title_edit" name="ziliao.title" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">描述:</span>
			<span class="inputControl">
				<script id="ziliao_content_edit" name="ziliao.content" type="text/plain"   style="width:750px;height:500px;"></script>

			</span>

		</div>
		<div>
			<span class="label">资料文件:</span>
			<span class="inputControl">
				<a id="ziliao_ziliaoFileA" style="color:red;margin-bottom:5px;">查看</a>&nbsp;&nbsp;
    			<input type="hidden" id="ziliao_ziliaoFile" name="ziliao.ziliaoFile"/>
				<input id="ziliaoFileFile" name="ziliaoFileFile" value="重新选择文件" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">添加时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ziliao_addTime_edit" name="ziliao.addTime" />

			</span>

		</div>
		<div class="operation">
			<a id="ziliaoModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/Ziliao/js/ziliao_modify.js"></script> 
