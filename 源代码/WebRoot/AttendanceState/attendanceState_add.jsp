<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/attendanceState.css" />
<div id="attendanceStateAddDiv">
	<form id="attendanceStateAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">考勤状态名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="attendanceState_stateName" name="attendanceState.stateName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="attendanceStateAddButton" class="easyui-linkbutton">添加</a>
			<a id="attendanceStateClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/AttendanceState/js/attendanceState_add.js"></script> 
