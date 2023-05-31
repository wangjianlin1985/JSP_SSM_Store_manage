﻿<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/manager.css" /> 

<div id="manager_manage"></div>
<div id="manager_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="manager_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="manager_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="manager_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="manager_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="manager_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="managerQueryForm" method="post">
			管理用户名：<input type="text" class="textbox" id="managerUserName" name="managerUserName" style="width:110px" />
			姓名：<input type="text" class="textbox" id="name" name="name" style="width:110px" />
			出生日期：<input type="text" id="birthday" name="birthday" class="easyui-datebox" editable="false" style="width:100px">
			联系电话：<input type="text" class="textbox" id="telephone" name="telephone" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="manager_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="managerEditDiv">
	<form id="managerEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">管理用户名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="manager_managerUserName_edit" name="manager.managerUserName" style="width:200px" />
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
	</form>
</div>
<script type="text/javascript" src="Manager/js/manager_manage.js"></script> 
