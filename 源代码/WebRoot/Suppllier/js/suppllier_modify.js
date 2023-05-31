$(function () {
	$.ajax({
		url : "Suppllier/" + $("#suppllier_supplierId_edit").val() + "/update",
		type : "get",
		data : {
			//supplierId : $("#suppllier_supplierId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (suppllier, response, status) {
			$.messager.progress("close");
			if (suppllier) { 
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
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#suppllierModifyButton").click(function(){ 
		if ($("#suppllierEditForm").form("validate")) {
			$("#suppllierEditForm").form({
			    url:"Suppllier/" +  $("#suppllier_supplierId_edit").val() + "/update",
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
			$("#suppllierEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
