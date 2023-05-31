<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/employee.css" />
<div id="employeeAddDiv">
	<form id="employeeAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">雇员编号:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_employeeNo" name="employee.employeeNo" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">登录密码:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_password" name="employee.password" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">姓名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_name" name="employee.name" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">性别:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_gender" name="employee.gender" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">出生日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_birthDate" name="employee.birthDate" />

			</span>

		</div>
		<div>
			<span class="label">雇员照片:</span>
			<span class="inputControl">
				<input id="employeePhotoFile" name="employeePhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">联系电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_telephone" name="employee.telephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">邮箱:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_email" name="employee.email" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">家庭地址:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_address" name="employee.address" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">注册时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="employee_regTime" name="employee.regTime" />

			</span>

		</div>
		<div class="operation">
			<a id="employeeAddButton" class="easyui-linkbutton">添加</a>
			<a id="employeeClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Employee/js/employee_add.js"></script> 
