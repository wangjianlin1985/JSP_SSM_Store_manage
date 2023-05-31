<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/manager.css" />
<div id="manager_editDiv">
	<form id="managerEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">管理用户名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="manager_managerUserName_edit" name="manager.managerUserName" value="<%=request.getParameter("managerUserName") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">登录密码:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="manager_password_edit" name="manager.password" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">姓名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="manager_name_edit" name="manager.name" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">性别:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="manager_sex_edit" name="manager.sex" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">出生日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="manager_birthday_edit" name="manager.birthday" />

			</span>

		</div>
		<div>
			<span class="label">联系电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="manager_telephone_edit" name="manager.telephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">管理备注:</span>
			<span class="inputControl">
				<textarea id="manager_managerMemo_edit" name="manager.managerMemo" rows="8" cols="60"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="managerModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/Manager/js/manager_modify.js"></script> 
