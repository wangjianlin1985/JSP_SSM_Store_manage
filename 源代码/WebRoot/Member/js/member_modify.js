$(function () {
	$.ajax({
		url : "Member/" + $("#member_memberUserName_edit").val() + "/update",
		type : "get",
		data : {
			//memberUserName : $("#member_memberUserName_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (member, response, status) {
			$.messager.progress("close");
			if (member) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#memberModifyButton").click(function(){ 
		if ($("#memberEditForm").form("validate")) {
			$("#memberEditForm").form({
			    url:"Member/" +  $("#member_memberUserName_edit").val() + "/update",
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
			$("#memberEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
