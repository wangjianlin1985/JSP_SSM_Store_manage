<%@ page language="java"  contentType="text/html;charset=UTF-8"%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lostProduct.css" /> 

<div id="lostProduct_manage"></div>
<div id="lostProduct_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="lostProduct_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="lostProduct_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="lostProduct_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="lostProduct_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="lostProduct_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="lostProductQueryForm" method="post">
			丢失物品：<input class="textbox" type="text" id="productObj_productId_query" name="productObj.productId" style="width: auto"/>
			丢失时间：<input type="text" id="lostTime" name="lostTime" class="easyui-datebox" editable="false" style="width:100px">
			操作员：<input class="textbox" type="text" id="employeeObj_employeeNo_query" name="employeeObj.employeeNo" style="width: auto"/>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="lostProduct_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="lostProductEditDiv">
	<form id="lostProductEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">记录id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="lostProduct_lostId_edit" name="lostProduct.lostId" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">丢失物品:</span>
			<span class="inputControl">
				<input class="textbox"  id="lostProduct_productObj_productId_edit" name="lostProduct.productObj.productId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">丢失数量:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="lostProduct_lostNumber_edit" name="lostProduct.lostNumber" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">丢失时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="lostProduct_lostTime_edit" name="lostProduct.lostTime" />

			</span>

		</div>
		<div>
			<span class="label">丢失地点:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="lostProduct_lostPlace_edit" name="lostProduct.lostPlace" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">总价值:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="lostProduct_productMoney_edit" name="lostProduct.productMoney" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">操作员:</span>
			<span class="inputControl">
				<input class="textbox"  id="lostProduct_employeeObj_employeeNo_edit" name="lostProduct.employeeObj.employeeNo" style="width: auto"/>
			</span>
		</div>
	</form>
</div>
<script type="text/javascript" src="LostProduct/js/lostProduct_manage.js"></script> 
