$(function () {
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.delEditor('ziliao_content');
	var ziliao_content_editor = UE.getEditor('ziliao_content'); //描述编辑框
	$("#ziliao_title").validatebox({
		required : true, 
		missingMessage : '请输入标题',
	});

	$("#ziliao_addTime").datetimebox({
	    required : true, 
	    showSeconds: true,
	    editable: false
	});

	//单击添加按钮
	$("#ziliaoAddButton").click(function () {
		if(ziliao_content_editor.getContent() == "") {
			alert("请输入描述");
			return;
		}
		//验证表单 
		if(!$("#ziliaoAddForm").form("validate")) {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		} else {
			$("#ziliaoAddForm").form({
			    url:"Ziliao/add",
			    onSubmit: function(){
					if($("#ziliaoAddForm").form("validate"))  { 
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
                        $("#ziliaoAddForm").form("clear");
                        ziliao_content_editor.setContent("");
                    }else{
                        $.messager.alert("消息",obj.message);
                        $(".messager-window").css("z-index",10000);
                    }
			    }
			});
			//提交表单
			$("#ziliaoAddForm").submit();
		}
	});

	//单击清空按钮
	$("#ziliaoClearButton").click(function () { 
		$("#ziliaoAddForm").form("clear"); 
	});
});
