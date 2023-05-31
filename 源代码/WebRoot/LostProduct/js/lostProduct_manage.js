var lostProduct_manage_tool = null; 
$(function () { 
	initLostProductManageTool(); //建立LostProduct管理对象
	lostProduct_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#lostProduct_manage").datagrid({
		url : 'LostProduct/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "lostId",
		sortOrder : "desc",
		toolbar : "#lostProduct_manage_tool",
		columns : [[
			{
				field : "lostId",
				title : "记录id",
				width : 70,
			},
			{
				field : "productObj",
				title : "丢失物品",
				width : 140,
			},
			{
				field : "lostNumber",
				title : "丢失数量",
				width : 70,
			},
			{
				field : "lostTime",
				title : "丢失时间",
				width : 140,
			},
			{
				field : "lostPlace",
				title : "丢失地点",
				width : 140,
			},
			{
				field : "productMoney",
				title : "总价值",
				width : 70,
			},
			{
				field : "employeeObj",
				title : "操作员",
				width : 140,
			},
		]],
	});

	$("#lostProductEditDiv").dialog({
		title : "修改管理",
		top: "50px",
		width : 700,
		height : 515,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#lostProductEditForm").form("validate")) {
					//验证表单 
					if(!$("#lostProductEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#lostProductEditForm").form({
						    url:"LostProduct/" + $("#lostProduct_lostId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#lostProductEditForm").form("validate"))  {
				                	$.messager.progress({
										text : "正在提交数据中...",
									});
				                	return true;
				                } else { 
				                    return false; 
				                }
						    },
						    success:function(data){
						    	$.messager.progress("close");
						    	console.log(data);
			                	var obj = jQuery.parseJSON(data);
			                    if(obj.success){
			                        $.messager.alert("消息","信息修改成功！");
			                        $("#lostProductEditDiv").dialog("close");
			                        lostProduct_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#lostProductEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#lostProductEditDiv").dialog("close");
				$("#lostProductEditForm").form("reset"); 
			},
		}],
	});
});

function initLostProductManageTool() {
	lostProduct_manage_tool = {
		init: function() {
			$.ajax({
				url : "Product/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#productObj_productId_query").combobox({ 
					    valueField:"productId",
					    textField:"productName",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{productId:0,productName:"不限制"});
					$("#productObj_productId_query").combobox("loadData",data); 
				}
			});
			$.ajax({
				url : "Employee/listAll",
				type : "post",
				success : function (data, response, status) {
					$("#employeeObj_employeeNo_query").combobox({ 
					    valueField:"employeeNo",
					    textField:"name",
					    panelHeight: "200px",
				        editable: false, //不允许手动输入 
					});
					data.splice(0,0,{employeeNo:"",name:"不限制"});
					$("#employeeObj_employeeNo_query").combobox("loadData",data); 
				}
			});
		},
		reload : function () {
			$("#lostProduct_manage").datagrid("reload");
		},
		redo : function () {
			$("#lostProduct_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#lostProduct_manage").datagrid("options").queryParams;
			queryParams["productObj.productId"] = $("#productObj_productId_query").combobox("getValue");
			queryParams["lostTime"] = $("#lostTime").datebox("getValue"); 
			queryParams["employeeObj.employeeNo"] = $("#employeeObj_employeeNo_query").combobox("getValue");
			$("#lostProduct_manage").datagrid("options").queryParams=queryParams; 
			$("#lostProduct_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#lostProductQueryForm").form({
			    url:"LostProduct/OutToExcel",
			});
			//提交表单
			$("#lostProductQueryForm").submit();
		},
		remove : function () {
			var rows = $("#lostProduct_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var lostIds = [];
						for (var i = 0; i < rows.length; i ++) {
							lostIds.push(rows[i].lostId);
						}
						$.ajax({
							type : "POST",
							url : "LostProduct/deletes",
							data : {
								lostIds : lostIds.join(","),
							},
							beforeSend : function () {
								$("#lostProduct_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#lostProduct_manage").datagrid("loaded");
									$("#lostProduct_manage").datagrid("load");
									$("#lostProduct_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#lostProduct_manage").datagrid("loaded");
									$("#lostProduct_manage").datagrid("load");
									$("#lostProduct_manage").datagrid("unselectAll");
									$.messager.alert("消息",data.message);
								}
							},
						});
					}
				});
			} else {
				$.messager.alert("提示", "请选择要删除的记录！", "info");
			}
		},
		edit : function () {
			var rows = $("#lostProduct_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "LostProduct/" + rows[0].lostId +  "/update",
					type : "get",
					data : {
						//lostId : rows[0].lostId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (lostProduct, response, status) {
						$.messager.progress("close");
						if (lostProduct) { 
							$("#lostProductEditDiv").dialog("open");
							$("#lostProduct_lostId_edit").val(lostProduct.lostId);
							$("#lostProduct_lostId_edit").validatebox({
								required : true,
								missingMessage : "请输入记录id",
								editable: false
							});
							$("#lostProduct_productObj_productId_edit").combobox({
								url:"Product/listAll",
							    valueField:"productId",
							    textField:"productName",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#lostProduct_productObj_productId_edit").combobox("select", lostProduct.productObjPri);
									//var data = $("#lostProduct_productObj_productId_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#lostProduct_productObj_productId_edit").combobox("select", data[0].productId);
						            //}
								}
							});
							$("#lostProduct_lostNumber_edit").val(lostProduct.lostNumber);
							$("#lostProduct_lostNumber_edit").validatebox({
								required : true,
								validType : "integer",
								missingMessage : "请输入丢失数量",
								invalidMessage : "丢失数量输入不对",
							});
							$("#lostProduct_lostTime_edit").datetimebox({
								value: lostProduct.lostTime,
							    required: true,
							    showSeconds: true,
							});
							$("#lostProduct_lostPlace_edit").val(lostProduct.lostPlace);
							$("#lostProduct_lostPlace_edit").validatebox({
								required : true,
								missingMessage : "请输入丢失地点",
							});
							$("#lostProduct_productMoney_edit").val(lostProduct.productMoney);
							$("#lostProduct_productMoney_edit").validatebox({
								required : true,
								validType : "number",
								missingMessage : "请输入总价值",
								invalidMessage : "总价值输入不对",
							});
							$("#lostProduct_employeeObj_employeeNo_edit").combobox({
								url:"Employee/listAll",
							    valueField:"employeeNo",
							    textField:"name",
							    panelHeight: "auto",
						        editable: false, //不允许手动输入 
						        onLoadSuccess: function () { //数据加载完毕事件
									$("#lostProduct_employeeObj_employeeNo_edit").combobox("select", lostProduct.employeeObjPri);
									//var data = $("#lostProduct_employeeObj_employeeNo_edit").combobox("getData"); 
						            //if (data.length > 0) {
						                //$("#lostProduct_employeeObj_employeeNo_edit").combobox("select", data[0].employeeNo);
						            //}
								}
							});
						} else {
							$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
						}
					}
				});
			} else if (rows.length == 0) {
				$.messager.alert("警告操作！", "编辑记录至少选定一条数据！", "warning");
			}
		},
	};
}
