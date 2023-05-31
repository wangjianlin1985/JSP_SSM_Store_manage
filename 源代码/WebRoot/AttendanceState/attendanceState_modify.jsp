<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/attendanceState.css" />
<div id="attendanceState_editDiv">
	<form id="attendanceStateEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">考勤状态id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="attendanceState_stateId_edit" name="attendanceState.stateId" value="<%=request.getParameter("stateId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">考勤状态名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="attendanceState_stateName_edit" name="attendanceState.stateName" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="attendanceStateModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/AttendanceState/js/attendanceState_modify.js"></script> 
