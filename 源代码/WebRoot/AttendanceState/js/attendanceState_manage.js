var attendanceState_manage_tool = null; 
$(function () { 
	initAttendanceStateManageTool(); //建立AttendanceState管理对象
	attendanceState_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#attendanceState_manage").datagrid({
		url : 'AttendanceState/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "stateId",
		sortOrder : "desc",
		toolbar : "#attendanceState_manage_tool",
		columns : [[
			{
				field : "stateId",
				title : "考勤状态id",
				width : 70,
			},
			{
				field : "stateName",
				title : "考勤状态名称",
				width : 140,
			},
		]],
	});

	$("#attendanceStateEditDiv").dialog({
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
				if ($("#attendanceStateEditForm").form("validate")) {
					//验证表单 
					if(!$("#attendanceStateEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#attendanceStateEditForm").form({
						    url:"AttendanceState/" + $("#attendanceState_stateId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#attendanceStateEditForm").form("validate"))  {
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
			                        $("#attendanceStateEditDiv").dialog("close");
			                        attendanceState_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#attendanceStateEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#attendanceStateEditDiv").dialog("close");
				$("#attendanceStateEditForm").form("reset"); 
			},
		}],
	});
});

function initAttendanceStateManageTool() {
	attendanceState_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#attendanceState_manage").datagrid("reload");
		},
		redo : function () {
			$("#attendanceState_manage").datagrid("unselectAll");
		},
		search: function() {
			$("#attendanceState_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#attendanceStateQueryForm").form({
			    url:"AttendanceState/OutToExcel",
			});
			//提交表单
			$("#attendanceStateQueryForm").submit();
		},
		remove : function () {
			var rows = $("#attendanceState_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var stateIds = [];
						for (var i = 0; i < rows.length; i ++) {
							stateIds.push(rows[i].stateId);
						}
						$.ajax({
							type : "POST",
							url : "AttendanceState/deletes",
							data : {
								stateIds : stateIds.join(","),
							},
							beforeSend : function () {
								$("#attendanceState_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#attendanceState_manage").datagrid("loaded");
									$("#attendanceState_manage").datagrid("load");
									$("#attendanceState_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#attendanceState_manage").datagrid("loaded");
									$("#attendanceState_manage").datagrid("load");
									$("#attendanceState_manage").datagrid("unselectAll");
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
			var rows = $("#attendanceState_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "AttendanceState/" + rows[0].stateId +  "/update",
					type : "get",
					data : {
						//stateId : rows[0].stateId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (attendanceState, response, status) {
						$.messager.progress("close");
						if (attendanceState) { 
							$("#attendanceStateEditDiv").dialog("open");
							$("#attendanceState_stateId_edit").val(attendanceState.stateId);
							$("#attendanceState_stateId_edit").validatebox({
								required : true,
								missingMessage : "请输入考勤状态id",
								editable: false
							});
							$("#attendanceState_stateName_edit").val(attendanceState.stateName);
							$("#attendanceState_stateName_edit").validatebox({
								required : true,
								missingMessage : "请输入考勤状态名称",
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
