var logInfo_manage_tool = null; 
$(function () { 
	initLogInfoManageTool(); //建立LogInfo管理对象
	logInfo_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#logInfo_manage").datagrid({
		url : 'LogInfo/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "logId",
		sortOrder : "desc",
		toolbar : "#logInfo_manage_tool",
		columns : [[
			{
				field : "logId",
				title : "日志id",
				width : 70,
			},
			{
				field : "logType",
				title : "日志类型",
				width : 140,
			},
			{
				field : "logContent",
				title : "日志内容",
				width : 140,
			},
			{
				field : "logTime",
				title : "日志时间",
				width : 140,
			},
		]],
	});

	$("#logInfoEditDiv").dialog({
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
				if ($("#logInfoEditForm").form("validate")) {
					//验证表单 
					if(!$("#logInfoEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#logInfoEditForm").form({
						    url:"LogInfo/" + $("#logInfo_logId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#logInfoEditForm").form("validate"))  {
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
			                        $("#logInfoEditDiv").dialog("close");
			                        logInfo_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#logInfoEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#logInfoEditDiv").dialog("close");
				$("#logInfoEditForm").form("reset"); 
			},
		}],
	});
});

function initLogInfoManageTool() {
	logInfo_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#logInfo_manage").datagrid("reload");
		},
		redo : function () {
			$("#logInfo_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#logInfo_manage").datagrid("options").queryParams;
			queryParams["logType"] = $("#logType").val();
			queryParams["logTime"] = $("#logTime").datebox("getValue"); 
			$("#logInfo_manage").datagrid("options").queryParams=queryParams; 
			$("#logInfo_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#logInfoQueryForm").form({
			    url:"LogInfo/OutToExcel",
			});
			//提交表单
			$("#logInfoQueryForm").submit();
		},
		remove : function () {
			var rows = $("#logInfo_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var logIds = [];
						for (var i = 0; i < rows.length; i ++) {
							logIds.push(rows[i].logId);
						}
						$.ajax({
							type : "POST",
							url : "LogInfo/deletes",
							data : {
								logIds : logIds.join(","),
							},
							beforeSend : function () {
								$("#logInfo_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#logInfo_manage").datagrid("loaded");
									$("#logInfo_manage").datagrid("load");
									$("#logInfo_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#logInfo_manage").datagrid("loaded");
									$("#logInfo_manage").datagrid("load");
									$("#logInfo_manage").datagrid("unselectAll");
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
			var rows = $("#logInfo_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "LogInfo/" + rows[0].logId +  "/update",
					type : "get",
					data : {
						//logId : rows[0].logId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (logInfo, response, status) {
						$.messager.progress("close");
						if (logInfo) { 
							$("#logInfoEditDiv").dialog("open");
							$("#logInfo_logId_edit").val(logInfo.logId);
							$("#logInfo_logId_edit").validatebox({
								required : true,
								missingMessage : "请输入日志id",
								editable: false
							});
							$("#logInfo_logType_edit").val(logInfo.logType);
							$("#logInfo_logType_edit").validatebox({
								required : true,
								missingMessage : "请输入日志类型",
							});
							$("#logInfo_logContent_edit").val(logInfo.logContent);
							$("#logInfo_logContent_edit").validatebox({
								required : true,
								missingMessage : "请输入日志内容",
							});
							$("#logInfo_logTime_edit").datetimebox({
								value: logInfo.logTime,
							    required: true,
							    showSeconds: true,
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
