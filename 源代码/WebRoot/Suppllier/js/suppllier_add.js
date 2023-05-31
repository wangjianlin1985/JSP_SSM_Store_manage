$(function () {
	$("#suppllier_supplierName").validatebox({
		required : true, 
		missingMessage : '请输入供应商名称',
	});

	$("#suppllier_supplierLawyer").validatebox({
		required : true, 
		missingMessage : '请输入法人代表',
	});

	$("#suppllier_supplierTelephone").validatebox({
		required : true, 
		missingMessage : '请输入供应商电话',
	});

	$("#suppllier_supplierAddress").validatebox({
		required : true, 
		missingMessage : '请输入供应商地址',
	});

	//单击添加按钮
	$("#suppllierAddButton").click(function () {
		//验证表单 
		if(!$("#suppllierAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#suppllierAddForm").form({
			    url:"Suppllier/add",
			    onSubmit: function(){
					if($("#suppllierAddForm").form("validate"))  { 
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
                        $("#suppllierAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#suppllierAddForm").submit();
		}
	});

	//单击清空按钮
	$("#suppllierClearButton").click(function () { 
		$("#suppllierAddForm").form("clear"); 
	});
});
