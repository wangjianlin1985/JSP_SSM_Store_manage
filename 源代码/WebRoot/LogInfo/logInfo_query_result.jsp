<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/logInfo.css" /> 

<div id="logInfo_manage"></div>
<div id="logInfo_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="logInfo_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="logInfo_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="logInfo_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="logInfo_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="logInfo_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="logInfoQueryForm" method="post">
			日志类型：<input type="text" class="textbox" id="logType" name="logType" style="width:110px" />
			日志时间：<input type="text" id="logTime" name="logTime" class="easyui-datebox" editable="false" style="width:100px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="logInfo_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="logInfoEditDiv">
	<form id="logInfoEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">日志id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="logInfo_logId_edit" name="logInfo.logId" style="width:200px" />
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
	</form>
</div>
<script type="text/javascript" src="LogInfo/js/logInfo_manage.js"></script> 
