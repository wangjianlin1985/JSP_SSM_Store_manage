<%@ page language="java"  contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/> 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/suppllier.css" /> 

<div id="suppllier_manage"></div>
<div id="suppllier_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="suppllier_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="suppllier_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="suppllier_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="suppllier_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="suppllier_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="suppllierQueryForm" method="post">
			供应商名称：<input type="text" class="textbox" id="supplierName" name="supplierName" style="width:110px" />
			法人代表：<input type="text" class="textbox" id="supplierLawyer" name="supplierLawyer" style="width:110px" />
			供应商电话：<input type="text" class="textbox" id="supplierTelephone" name="supplierTelephone" style="width:110px" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="suppllier_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="suppllierEditDiv">
	<form id="suppllierEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">供应商id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="suppllier_supplierId_edit" name="suppllier.supplierId" style="width:200px" />
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
	</form>
</div>
<script type="text/javascript" src="Suppllier/js/suppllier_manage.js"></script> 
