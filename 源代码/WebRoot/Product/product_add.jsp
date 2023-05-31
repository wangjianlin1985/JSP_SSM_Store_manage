﻿<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%>


<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/product.css" />
<div id="productAddDiv">
	<form id="productAddForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">商品类别:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="product_productClassObj_classId" name="product.productClassObj.classId" style="width: auto"/>
			</span>
		</div>
		<div>
			<span class="label">商品名称:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="product_productName" name="product.productName" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">商品主图:</span>
			<span class="inputControl">
				<input id="mainPhotoFile" name="mainPhotoFile" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">商品单价:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="product_productPrice" name="product.productPrice" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">商品库存:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="product_productCount" name="product.productCount" style="width:80px" />

			</span>

		</div>
		<div>
			<span class="label">商品描述:</span>
			<span class="inputControl">
				<script name="product.productDesc" id="product_productDesc" type="text/plain"   style="width:750px;height:500px;"></script>
			</span>

		</div>
		<div>
			<span class="label">发布时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="product_addTime" name="product.addTime" />

			</span>

		</div>
		<div class="operation">
			<a id="productAddButton" class="easyui-linkbutton">添加</a>
			<a id="productClearButton" class="easyui-linkbutton">重填</a>
		</div> 
	</form>
</div>
<script src="${pageContext.request.contextPath}/Product/js/product_add.js"></script> 
