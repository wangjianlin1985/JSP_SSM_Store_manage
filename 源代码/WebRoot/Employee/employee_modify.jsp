<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/employee.css" />
<div id="employee_editDiv">
	<form id="employeeEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">雇员编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_employeeNo_edit" name="employee.employeeNo" value="<%=request.getParameter("employeeNo") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">登录密码:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_password_edit" name="employee.password" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">姓名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_name_edit" name="employee.name" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">性别:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_gender_edit" name="employee.gender" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">出生日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_birthDate_edit" name="employee.birthDate" />

			</span>

		</div>
		<div>
			<span class="label">雇员照片:</span>
			<span class="inputControl">
				<img id="employee_employeePhotoImg" width="200px" border="0px"/><br/>
    			<input type="hidden" id="employee_employeePhoto" name="employee.employeePhoto"/>
				<input id="employeePhotoFile" name="employeePhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">联系电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_telephone_edit" name="employee.telephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">邮箱:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_email_edit" name="employee.email" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">家庭地址:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_address_edit" name="employee.address" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">注册时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_regTime_edit" name="employee.regTime" />

			</span>

		</div>
		<div class="operation">
			<a id="employeeModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/Employee/js/employee_modify.js"></script> 
