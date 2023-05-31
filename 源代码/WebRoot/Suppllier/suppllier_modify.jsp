<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/suppllier.css" />
<div id="suppllier_editDiv">
	<form id="suppllierEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">供应商id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="suppllier_supplierId_edit" name="suppllier.supplierId" value="<%=request.getParameter("supplierId") %>" style="width:200px" />
			</span>
		</div>

		<div>
			<span class="label">供应商名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="suppllier_supplierName_edit" name="suppllier.supplierName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">法人代表:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="suppllier_supplierLawyer_edit" name="suppllier.supplierLawyer" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">供应商电话:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="suppllier_supplierTelephone_edit" name="suppllier.supplierTelephone" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">供应商地址:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="suppllier_supplierAddress_edit" name="suppllier.supplierAddress" style="width:200px" />

			</span>

		</div>
		<div class="operation">
			<a id="suppllierModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/Suppllier/js/suppllier_modify.js"></script> 
