<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/member.css" />
<div id="memberAddDiv">
	<form id="memberAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">会员名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="member_memberUserName" name="member.memberUserName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">姓名:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="member_name" name="member.name" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">性别:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="member_gender" name="member.gender" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">出生日期:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="member_birthDate" name="member.birthDate" />

			</span>

		</div>
		<div>
			<span class="label">联系电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="member_telephone" name="member.telephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">住宅地址:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="member_address" name="member.address" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">会员备注:</span>
			<span class="inputControl">
				<textarea id="member_memberMemo" name="member.memberMemo" rows="6" cols="80"></textarea>

			</span>

		</div>
		<div class="operation">
			<a id="memberAddButton" class="easyui-linkbutton">添加</a>
			<a id="memberClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Member/js/member_add.js"></script> 
