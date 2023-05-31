$(function () {
	$.ajax({
		url : "AttendanceState/" + $("#attendanceState_stateId_edit").val() + "/update",
		type : "get",
		data : {
			//stateId : $("#attendanceState_stateId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (attendanceState, response, status) {
			$.messager.progress("close");
			if (attendanceState) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#attendanceStateModifyButton").click(function(){ 
		if ($("#attendanceStateEditForm").form("validate")) {
			$("#attendanceStateEditForm").form({
			    url:"AttendanceState/" +  $("#attendanceState_stateId_edit").val() + "/update",
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
			$("#attendanceStateEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
