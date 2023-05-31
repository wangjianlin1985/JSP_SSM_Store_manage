$(function () {
	$.ajax({
		url : "LogInfo/" + $("#logInfo_logId_edit").val() + "/update",
		type : "get",
		data : {
			//logId : $("#logInfo_logId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (logInfo, response, status) {
			$.messager.progress("close");
			if (logInfo) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#logInfoModifyButton").click(function(){ 
		if ($("#logInfoEditForm").form("validate")) {
			$("#logInfoEditForm").form({
			    url:"LogInfo/" +  $("#logInfo_logId_edit").val() + "/update",
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
                	var obj = jQuery.parseJSON(data);
                    if(obj.success){
                        $.messager.alert("消息","信息修改成功！");
                        $(".messager-window").css("z-index",10000);
                        //location.href="frontlist";
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    } 
			    }
			});
			//提交表单
			$("#logInfoEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
