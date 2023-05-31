<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/logInfo.css" />
<div id="logInfo_editDiv">
	<form id="logInfoEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">日志id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="logInfo_logId_edit" name="logInfo.logId" value="<%=request.getParameter("logId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">日志类型:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="logInfo_logType_edit" name="logInfo.logType" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">日志内容:</span>
			<span class="inputControl">
				<textarea id="logInfo_logContent_edit" name="logInfo.logContent" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div>
			<span class="label">日志时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="logInfo_logTime_edit" name="logInfo.logTime" />

			</span>

		</div>
		<div class="operation">
			<a id="logInfoModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/LogInfo/js/logInfo_modify.js"></script> 
