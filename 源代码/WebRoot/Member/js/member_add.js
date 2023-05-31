$(function () {
	$("#member_memberUserName").validatebox({
		required : true, 
		missingMessage : '请输入会员名',
	});

	$("#member_name").validatebox({
		required : true, 
		missingMessage : '请输入姓名',
	});

	$("#member_gender").validatebox({
		required : true, 
		missingMessage : '请输入性别',
	});

	$("#member_birthDate").datebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	$("#member_telephone").validatebox({
		required : true, 
		missingMessage : '请输入联系电话',
	});

	//单击添加按钮
	$("#memberAddButton").click(function () {
		//验证表单 
		if(!$("#memberAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#memberAddForm").form({
			    url:"Member/add",
			    onSubmit: function(){
					if($("#memberAddForm").form("validate"))  { 
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
                    //此处data={"Success":true}是字符串
                	var obj = jQuery.parseJSON(data); 
                    if(obj.success){ 
                        $.messager.alert("消息","保存成功！");
                        $(".messager-window").css("z-index",10000);
                        $("#memberAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#memberAddForm").submit();
		}
	});

	//单击清空按钮
	$("#memberClearButton").click(function () { 
		$("#memberAddForm").form("clear"); 
	});
});
