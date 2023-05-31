<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/logInfo.css" />
<div id="logInfoAddDiv">
	<form id="logInfoAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">日志类型:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="logInfo_logType" name="logInfo.logType" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">日志内容:</span>
			<span class="inputControl">
				<textarea id="logInfo_logContent" name="logInfo.logContent" rows="6" cols="80"></textarea>

			</span>

		</div>
		<div>
			<span class="label">日志时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="logInfo_logTime" name="logInfo.logTime" />

			</span>

		</div>
		<div class="operation">
			<a id="logInfoAddButton" class="easyui-linkbutton">添加</a>
			<a id="logInfoClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/LogInfo/js/logInfo_add.js"></script> 
