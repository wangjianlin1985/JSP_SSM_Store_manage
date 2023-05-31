var suppllier_manage_tool = null; 
$(function () { 
	initSuppllierManageTool(); //建立Suppllier管理对象
	suppllier_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#suppllier_manage").datagrid({
		url : 'Suppllier/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "supplierId",
		sortOrder : "desc",
		toolbar : "#suppllier_manage_tool",
		columns : [[
			{
				field : "supplierId",
				title : "供应商id",
				width : 70,
			},
			{
				field : "supplierName",
				title : "供应商名称",
				width : 140,
			},
			{
				field : "supplierLawyer",
				title : "法人代表",
				width : 140,
			},
			{
				field : "supplierTelephone",
				title : "供应商电话",
				width : 140,
			},
			{
				field : "supplierAddress",
				title : "供应商地址",
				width : 140,
			},
		]],
	});

	$("#suppllierEditDiv").dialog({
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
				if ($("#suppllierEditForm").form("validate")) {
					//验证表单 
					if(!$("#suppllierEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#suppllierEditForm").form({
						    url:"Suppllier/" + $("#suppllier_supplierId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#suppllierEditForm").form("validate"))  {
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
			                        $("#suppllierEditDiv").dialog("close");
			                        suppllier_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#suppllierEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#suppllierEditDiv").dialog("close");
				$("#suppllierEditForm").form("reset"); 
			},
		}],
	});
});

function initSuppllierManageTool() {
	suppllier_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#suppllier_manage").datagrid("reload");
		},
		redo : function () {
			$("#suppllier_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#suppllier_manage").datagrid("options").queryParams;
			queryParams["supplierName"] = $("#supplierName").val();
			queryParams["supplierLawyer"] = $("#supplierLawyer").val();
			queryParams["supplierTelephone"] = $("#supplierTelephone").val();
			$("#suppllier_manage").datagrid("options").queryParams=queryParams; 
			$("#suppllier_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#suppllierQueryForm").form({
			    url:"Suppllier/OutToExcel",
			});
			//提交表单
			$("#suppllierQueryForm").submit();
		},
		remove : function () {
			var rows = $("#suppllier_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var supplierIds = [];
						for (var i = 0; i < rows.length; i ++) {
							supplierIds.push(rows[i].supplierId);
						}
						$.ajax({
							type : "POST",
							url : "Suppllier/deletes",
							data : {
								supplierIds : supplierIds.join(","),
							},
							beforeSend : function () {
								$("#suppllier_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#suppllier_manage").datagrid("loaded");
									$("#suppllier_manage").datagrid("load");
									$("#suppllier_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#suppllier_manage").datagrid("loaded");
									$("#suppllier_manage").datagrid("load");
									$("#suppllier_manage").datagrid("unselectAll");
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
			var rows = $("#suppllier_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Suppllier/" + rows[0].supplierId +  "/update",
					type : "get",
					data : {
						//supplierId : rows[0].supplierId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (suppllier, response, status) {
						$.messager.progress("close");
						if (suppllier) { 
							$("#suppllierEditDiv").dialog("open");
							$("#suppllier_supplierId_edit").val(suppllier.supplierId);
							$("#suppllier_supplierId_edit").validatebox({
								required : true,
								missingMessage : "请输入供应商id",
								editable: false
							});
							$("#suppllier_supplierName_edit").val(suppllier.supplierName);
							$("#suppllier_supplierName_edit").validatebox({
								required : true,
								missingMessage : "请输入供应商名称",
							});
							$("#suppllier_supplierLawyer_edit").val(suppllier.supplierLawyer);
							$("#suppllier_supplierLawyer_edit").validatebox({
								required : true,
								missingMessage : "请输入法人代表",
							});
							$("#suppllier_supplierTelephone_edit").val(suppllier.supplierTelephone);
							$("#suppllier_supplierTelephone_edit").validatebox({
								required : true,
								missingMessage : "请输入供应商电话",
							});
							$("#suppllier_supplierAddress_edit").val(suppllier.supplierAddress);
							$("#suppllier_supplierAddress_edit").validatebox({
								required : true,
								missingMessage : "请输入供应商地址",
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
