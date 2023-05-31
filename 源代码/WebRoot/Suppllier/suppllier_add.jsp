<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/suppllier.css" />
<div id="suppllierAddDiv">
	<form id="suppllierAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">供应商名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="suppllier_supplierName" name="suppllier.supplierName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">法人代表:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="suppllier_supplierLawyer" name="suppllier.supplierLawyer" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">供应商电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="suppllier_supplierTelephone" name="suppllier.supplierTelephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">供应商地址:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="suppllier_supplierAddress" name="suppllier.supplierAddress" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="suppllierAddButton" class="easyui-linkbutton">添加</a>
			<a id="suppllierClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Suppllier/js/suppllier_add.js"></script> 
