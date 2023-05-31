<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/lostProduct.css" />
<div id="lostProductAddDiv">
	<form id="lostProductAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">丢失物品:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="lostProduct_productObj_productId" name="lostProduct.productObj.productId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">丢失数量:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="lostProduct_lostNumber" name="lostProduct.lostNumber" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">丢失时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="lostProduct_lostTime" name="lostProduct.lostTime" />

			</span>

		</div>
		<div>
			<span class="label">丢失地点:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="lostProduct_lostPlace" name="lostProduct.lostPlace" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">总价值:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="lostProduct_productMoney" name="lostProduct.productMoney" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">操作员:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="lostProduct_employeeObj_employeeNo" name="lostProduct.employeeObj.employeeNo" style="width: auto"/>
			</span>
		</div>
		<div class="operation">
			<a id="lostProductAddButton" class="easyui-linkbutton">添加</a>
			<a id="lostProductClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/LostProduct/js/lostProduct_add.js"></script> 
