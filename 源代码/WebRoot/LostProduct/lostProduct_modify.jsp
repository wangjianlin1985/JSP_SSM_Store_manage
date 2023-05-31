<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<jsp:include page="../check_logstate.jsp"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lostProduct.css" />
<div id="lostProduct_editDiv">
	<form id="lostProductEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">记录id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="lostProduct_lostId_edit" name="lostProduct.lostId" value="<%=request.getParameter("lostId") %>" style="width:200px" />
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
		<div class="operation">
			<a id="lostProductModifyButton" class="easyui-linkbutton">更新</a> 
		</div>
	</form>
</div>
<script src="${pageContext.request.contextPath}/LostProduct/js/lostProduct_modify.js"></script> 
