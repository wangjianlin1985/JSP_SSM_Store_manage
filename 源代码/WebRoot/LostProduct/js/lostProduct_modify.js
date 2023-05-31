$(function () {
	$.ajax({
		url : "LostProduct/" + $("#lostProduct_lostId_edit").val() + "/update",
		type : "get",
		data : {
			//lostId : $("#lostProduct_lostId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (lostProduct, response, status) {
			$.messager.progress("close");
			if (lostProduct) { 
				$("#lostProduct_lostId_edit").val(lostProduct.lostId);
				$("#lostProduct_lostId_edit").validatebox({
					required : true,
					missingMessage : "请输入记录id",
					editable: false
				});
				$("#lostProduct_productObj_productId_edit").combobox({
					url:"Product/listAll",
					valueField:"productId",
					textField:"productName",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#lostProduct_productObj_productId_edit").combobox("select", lostProduct.productObjPri);
						//var data = $("#lostProduct_productObj_productId_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#lostProduct_productObj_productId_edit").combobox("select", data[0].productId);
						//}
					}
				});
				$("#lostProduct_lostNumber_edit").val(lostProduct.lostNumber);
				$("#lostProduct_lostNumber_edit").validatebox({
					required : true,
					validType : "integer",
					missingMessage : "请输入丢失数量",
					invalidMessage : "丢失数量输入不对",
				});
				$("#lostProduct_lostTime_edit").datetimebox({
					value: lostProduct.lostTime,
					required: true,
					showSeconds: true,
				});
				$("#lostProduct_lostPlace_edit").val(lostProduct.lostPlace);
				$("#lostProduct_lostPlace_edit").validatebox({
					required : true,
					missingMessage : "请输入丢失地点",
				});
				$("#lostProduct_productMoney_edit").val(lostProduct.productMoney);
				$("#lostProduct_productMoney_edit").validatebox({
					required : true,
					validType : "number",
					missingMessage : "请输入总价值",
					invalidMessage : "总价值输入不对",
				});
				$("#lostProduct_employeeObj_employeeNo_edit").combobox({
					url:"Employee/listAll",
					valueField:"employeeNo",
					textField:"name",
					panelHeight: "auto",
					editable: false, //不允许手动输入 
					onLoadSuccess: function () { //数据加载完毕事件
						$("#lostProduct_employeeObj_employeeNo_edit").combobox("select", lostProduct.employeeObjPri);
						//var data = $("#lostProduct_employeeObj_employeeNo_edit").combobox("getData"); 
						//if (data.length > 0) {
							//$("#lostProduct_employeeObj_employeeNo_edit").combobox("select", data[0].employeeNo);
						//}
					}
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

	$("#lostProductModifyButton").click(function(){ 
		if ($("#lostProductEditForm").form("validate")) {
			$("#lostProductEditForm").form({
			    url:"LostProduct/" +  $("#lostProduct_lostId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#lostProductEditForm").form("validate"))  {
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
			$("#lostProductEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
