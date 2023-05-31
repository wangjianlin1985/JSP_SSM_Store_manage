<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/member.css" />
<div id="member_editDiv">
	<form id="memberEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">会员名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="member_memberUserName_edit" name="member.memberUserName" value="<%=request.getParameter("memberUserName") %>" style="width:200px" />
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
		<div class="operation">
			<a id="memberModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/Member/js/member_modify.js"></script> 
