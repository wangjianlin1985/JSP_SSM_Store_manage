var member_manage_tool = null; 
$(function () { 
	initMemberManageTool(); //建立Member管理对象
	member_manage_tool.init(); //如果需要通过下拉框查询，首先初始化下拉框的值
	$("#member_manage").datagrid({
		url : 'Member/list',
		fit : true,
		fitColumns : true,
		striped : true,
		rownumbers : true,
		border : false,
		pagination : true,
		pageSize : 5,
		pageList : [5, 10, 15, 20, 25],
		pageNumber : 1,
		sortName : "memberUserName",
		sortOrder : "desc",
		toolbar : "#member_manage_tool",
		columns : [[
			{
				field : "memberUserName",
				title : "会员名",
				width : 140,
			},
			{
				field : "name",
				title : "姓名",
				width : 140,
			},
			{
				field : "gender",
				title : "性别",
				width : 140,
			},
			{
				field : "birthDate",
				title : "出生日期",
				width : 140,
			},
			{
				field : "telephone",
				title : "联系电话",
				width : 140,
			},
			{
				field : "memberMemo",
				title : "会员备注",
				width : 140,
			},
		]],
	});

	$("#memberEditDiv").dialog({
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
				if ($("#memberEditForm").form("validate")) {
					//验证表单 
					if(!$("#memberEditForm").form("validate")) {
						$.messager.alert("错误提示","你输入的信息还有错误！","warning");
					} else {
						$("#memberEditForm").form({
						    url:"Member/" + $("#member_memberUserName_edit").val() + "/update",
						    onSubmit: function(){
								if($("#memberEditForm").form("validate"))  {
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
			                        $("#memberEditDiv").dialog("close");
			                        member_manage_tool.reload();
			                    }else{
			                        $.messager.alert("消息",obj.message);
			                    } 
						    }
						});
						//提交表单
						$("#memberEditForm").submit();
					}
				}
			},
		},{
			text : "取消",
			iconCls : "icon-redo",
			handler : function () {
				$("#memberEditDiv").dialog("close");
				$("#memberEditForm").form("reset"); 
			},
		}],
	});
});

function initMemberManageTool() {
	member_manage_tool = {
		init: function() {
		},
		reload : function () {
			$("#member_manage").datagrid("reload");
		},
		redo : function () {
			$("#member_manage").datagrid("unselectAll");
		},
		search: function() {
			var queryParams = $("#member_manage").datagrid("options").queryParams;
			queryParams["memberUserName"] = $("#memberUserName").val();
			queryParams["name"] = $("#name").val();
			queryParams["birthDate"] = $("#birthDate").datebox("getValue"); 
			queryParams["telephone"] = $("#telephone").val();
			$("#member_manage").datagrid("options").queryParams=queryParams; 
			$("#member_manage").datagrid("load");
		},
		exportExcel: function() {
			$("#memberQueryForm").form({
			    url:"Member/OutToExcel",
			});
			//提交表单
			$("#memberQueryForm").submit();
		},
		remove : function () {
			var rows = $("#member_manage").datagrid("getSelections");
			if (rows.length > 0) {
				$.messager.confirm("确定操作", "您正在要删除所选的记录吗？", function (flag) {
					if (flag) {
						var memberUserNames = [];
						for (var i = 0; i < rows.length; i ++) {
							memberUserNames.push(rows[i].memberUserName);
						}
						$.ajax({
							type : "POST",
							url : "Member/deletes",
							data : {
								memberUserNames : memberUserNames.join(","),
							},
							beforeSend : function () {
								$("#member_manage").datagrid("loading");
							},
							success : function (data) {
								if (data.success) {
									$("#member_manage").datagrid("loaded");
									$("#member_manage").datagrid("load");
									$("#member_manage").datagrid("unselectAll");
									$.messager.show({
										title : "提示",
										msg : data.message
									});
								} else {
									$("#member_manage").datagrid("loaded");
									$("#member_manage").datagrid("load");
									$("#member_manage").datagrid("unselectAll");
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
			var rows = $("#member_manage").datagrid("getSelections");
			if (rows.length > 1) {
				$.messager.alert("警告操作！", "编辑记录只能选定一条数据！", "warning");
			} else if (rows.length == 1) {
				$.ajax({
					url : "Member/" + rows[0].memberUserName +  "/update",
					type : "get",
					data : {
						//memberUserName : rows[0].memberUserName,
					},
					beforeSend : function () {
						$.messager.progress({
							text : "正在获取中...",
						});
					},
					success : function (member, response, status) {
						$.messager.progress("close");
						if (member) { 
							$("#memberEditDiv").dialog("open");
							$("#member_memberUserName_edit").val(member.memberUserName);
							$("#member_memberUserName_edit").validatebox({
								required : true,
								missingMessage : "请输入会员名",
								editable: false
							});
							$("#member_name_edit").val(member.name);
							$("#member_name_edit").validatebox({
								required : true,
								missingMessage : "请输入姓名",
							});
							$("#member_gender_edit").val(member.gender);
							$("#member_gender_edit").validatebox({
								required : true,
								missingMessage : "请输入性别",
							});
							$("#member_birthDate_edit").datebox({
								value: member.birthDate,
							    required: true,
							    showSeconds: true,
							});
							$("#member_telephone_edit").val(member.telephone);
							$("#member_telephone_edit").validatebox({
								required : true,
								missingMessage : "请输入联系电话",
							});
							$("#member_address_edit").val(member.address);
							$("#member_memberMemo_edit").val(member.memberMemo);
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
