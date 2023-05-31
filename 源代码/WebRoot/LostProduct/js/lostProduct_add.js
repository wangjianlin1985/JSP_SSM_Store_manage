$(function () {
	$("#lostProduct_productObj_productId").combobox({
	    url:'Product/listAll',
	    valueField: "productId",
	    textField: "productName",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#lostProduct_productObj_productId").combobox("getData"); 
            if (data.length > 0) {
                $("#lostProduct_productObj_productId").combobox("select", data[0].productId);
            }
        }
	});
	$("#lostProduct_lostNumber").validatebox({
		required : true,
		validType : "integer",
		missingMessage : '请输入丢失数量',
		invalidMessage : '丢失数量输入不对',
	});

	$("#lostProduct_lostTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	$("#lostProduct_lostPlace").validatebox({
		required : true, 
		missingMessage : '请输入丢失地点',
	});

	$("#lostProduct_productMoney").validatebox({
		required : true,
		validType : "number",
		missingMessage : '请输入总价值',
		invalidMessage : '总价值输入不对',
	});

	$("#lostProduct_employeeObj_employeeNo").combobox({
	    url:'Employee/listAll',
	    valueField: "employeeNo",
	    textField: "name",
	    panelHeight: "auto",
        editable: false, //不允许手动输入
        required : true,
        onLoadSuccess: function () { //数据加载完毕事件
            var data = $("#lostProduct_employeeObj_employeeNo").combobox("getData"); 
            if (data.length > 0) {
                $("#lostProduct_employeeObj_employeeNo").combobox("select", data[0].employeeNo);
            }
        }
	});
	//单击添加按钮
	$("#lostProductAddButton").click(function () {
		//验证表单 
		if(!$("#lostProductAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#lostProductAddForm").form({
			    url:"LostProduct/add",
			    onSubmit: function(){
					if($("#lostProductAddForm").form("validate"))  { 
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
                        $("#lostProductAddForm").form("clear");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#lostProductAddForm").submit();
		}
	});

	//单击清空按钮
	$("#lostProductClearButton").click(function () { 
		$("#lostProductAddForm").form("clear"); 
	});
});
