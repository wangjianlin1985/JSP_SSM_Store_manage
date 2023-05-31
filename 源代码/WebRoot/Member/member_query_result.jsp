<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/member.css" /> 

<div id="member_manage"></div>
<div id="member_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="member_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="member_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="member_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="member_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="member_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="memberQueryForm" method="post">
			会员名：<input type="text" class="textbox" id="memberUserName" name="memberUserName" style="width:110px" />
			姓名：<input type="text" class="textbox" id="name" name="name" style="width:110px" />
			出生日期：<input type="text" id="birthDate" name="birthDate" class="easyui-datebox" editable="false" style="width:100px">
			联系电话：<input type="text" class="textbox" id="telephone" name="telephone" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="member_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="memberEditDiv">
	<form id="memberEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">会员名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="member_memberUserName_edit" name="member.memberUserName" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">姓名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="member_name_edit" name="member.name" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">性别:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="member_gender_edit" name="member.gender" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">出生日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="member_birthDate_edit" name="member.birthDate" />

			</span>

		</div>
		<div>
			<span class="label">联系电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="member_telephone_edit" name="member.telephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">住宅地址:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="member_address_edit" name="member.address" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">会员备注:</span>
			<span class="inputControl">
				<textarea id="member_memberMemo_edit" name="member.memberMemo" rows="8" cols="60"></textarea>

			</span>

		</div>
	</form>
</div>
<script type="text/javascript" src="Member/js/member_manage.js"></script> 
