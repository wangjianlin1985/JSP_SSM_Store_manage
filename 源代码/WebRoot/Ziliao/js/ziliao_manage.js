﻿var ziliao_manage_tool = null; 
$(function () { 
	initZiliaoManageTool(); //建立Ziliao管理对象
	ziliao_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#ziliao_manage").datagrid({
		url : 'Ziliao/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "ziliaoId",
		sortOrder : "desc",
		toolbar : "#ziliao_manage_tool",
		columns : [[
			{
				field : "ziliaoId",
				title : "资料id",
				width : 70,
			},
			{
				field : "title",
				title : "标题",
				width : 140,
			},
			{
				field : "ziliaoFile",
				title : "资料文件",
				width : "350px",
				formatter: function(val,row) {
 					if(val == "") return "暂无文件";
					return "<a href='" + val + "' target='_blank' style='color:red;'>" + val + "</a>";
				}
 			},
			{
				field : "addTime",
				title : "添加时间",
				width : 140,
			},
		]],
	});

	$("#ziliaoEditDiv").dialog({
		title : "修改管理",
		top: "10px",
		width : 1000,
		height : 600,
		modal : true,
		closed : true,
		iconCls : "icon-edit-new",
		buttons : [{
			text : "提交",
			iconCls : "icon-edit-new",
			handler : function () {
				if ($("#ziliaoEditForm").form("validate")) {
					//验证表单 
					if(!$("#ziliaoEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#ziliaoEditForm").form({
						    url:"Ziliao/" + $("#ziliao_ziliaoId_edit").val() + "/update",
						    onSubmit: function(){
								if($("#ziliaoEditForm").form("validate"))  {
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
			                        $("#ziliaoEditDiv").dialog("close");
			                        ziliao_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#ziliaoEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#ziliaoEditDiv").dialog("close");
				$("#ziliaoEditForm").form("reset"); 
			},
		}],
	});
});

function initZiliaoManageTool() {
	ziliao_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#ziliao_manage").datagrid("reload");
		},
		redo : function () {
			$("#ziliao_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#ziliao_manage").datagrid("options").queryParams;
			queryParams["title"] = $("#title").val();
			queryParams["addTime"] = $("#addTime").datebox("getValue"); 
			$("#ziliao_manage").datagrid("options").queryParams=queryParams; 
			$("#ziliao_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#ziliaoQueryForm").form({
			    url:"Ziliao/OutToExcel",
			});
			//提交表单
			$("#ziliaoQueryForm").submit();
		},
		remove : function () {
			var rows = $("#ziliao_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var ziliaoIds = [];
						for (var i = 0; i < rows.length; i ++) {
							ziliaoIds.push(rows[i].ziliaoId);
						}
						$.ajax({
							type : "POST",
							url : "Ziliao/deletes",
							data : {
								ziliaoIds : ziliaoIds.join(","),
							},
							beforeSend : function () {
								$("#ziliao_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#ziliao_manage").datagrid("loaded");
									$("#ziliao_manage").datagrid("load");
									$("#ziliao_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#ziliao_manage").datagrid("loaded");
									$("#ziliao_manage").datagrid("load");
									$("#ziliao_manage").datagrid("unselectAll");
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
			var rows = $("#ziliao_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Ziliao/" + rows[0].ziliaoId +  "/update",
					type : "get",
					data : {
						//ziliaoId : rows[0].ziliaoId,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (ziliao, response, status) {
						$.messager.progress("close");
						if (ziliao) { 
							$("#ziliaoEditDiv").dialog("open");
							$("#ziliao_ziliaoId_edit").val(ziliao.ziliaoId);
							$("#ziliao_ziliaoId_edit").validatebox({
								required : true,
								missingMessage : "请输入资料id",
								editable: false
							});
							$("#ziliao_title_edit").val(ziliao.title);
							$("#ziliao_title_edit").validatebox({
								required : true,
								missingMessage : "请输入标题",
							});
							ziliao_content_editor.setContent(ziliao.content, false);
							$("#ziliao_ziliaoFile").val(ziliao.ziliaoFile);
							if(ziliao.ziliaoFile == "") $("#ziliao_ziliaoFileA").html("暂无文件");
							else $("#ziliao_ziliaoFileA").html(ziliao.ziliaoFile);
							$("#ziliao_ziliaoFileA").attr("href", ziliao.ziliaoFile);
							$("#ziliao_addTime_edit").datetimebox({
								value: ziliao.addTime,
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
