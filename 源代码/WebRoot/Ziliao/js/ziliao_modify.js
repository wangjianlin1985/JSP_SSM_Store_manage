$(function () {
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	UE.delEditor('ziliao_content_edit');
	var ziliao_content_edit = UE.getEditor('ziliao_content_edit'); //描述编辑器
	ziliao_content_edit.addListener("ready", function () {
		 // editor准备好之后才可以使用 
		 ajaxModifyQuery();
	}); 
  function ajaxModifyQuery() {	
	$.ajax({
		url : "Ziliao/" + $("#ziliao_ziliaoId_edit").val() + "/update",
		type : "get",
		data : {
			//ziliaoId : $("#ziliao_ziliaoId_edit").val(),
		},
		beforeSend : function () {
			$.messager.progress({
				text : "正在获取中...",
			});
		},
		success : function (ziliao, response, status) {
			$.messager.progress("close");
			if (ziliao) { 
				$("#ziliao_ziliaoId_edit").val(ziliao.ziliaoId);
				$("#ziliao_ziliaoId_edit").validatebox({
					required : true,
					missingMessage : "请输入资料id",
					editable: false
				});
				$("#ziliao_title_edit").val(ziliao.title);
				$("#ziliao_title_edit").validatebox({
					required : true,
					missingMessage : "请输入标题",
				});
				ziliao_content_edit.setContent(ziliao.content);
				$("#ziliao_ziliaoFile").val(ziliao.ziliaoFile);
				if(ziliao.ziliaoFile == "") $("#ziliao_ziliaoFileA").html("暂无文件");
				$("#ziliao_ziliaoFileA").attr("href", ziliao.ziliaoFile);
				$("#ziliao_addTime_edit").datetimebox({
					value: ziliao.addTime,
					required: true,
					showSeconds: true,
				});
			} else {
				$.messager.alert("获取失败！", "未知错误导致失败，请重试！", "warning");
				$(".messager-window").css("z-index",10000);
			}
		}
	});

  }

	$("#ziliaoModifyButton").click(function(){ 
		if ($("#ziliaoEditForm").form("validate")) {
			$("#ziliaoEditForm").form({
			    url:"Ziliao/" +  $("#ziliao_ziliaoId_edit").val() + "/update",
			    onSubmit: function(){
					if($("#ziliaoEditForm").form("validate"))  {
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
			$("#ziliaoEditForm").submit();
		} else {
			$.messager.alert("错误提示","你输入的信息还有错误！","warning");
			$(".messager-window").css("z-index",10000);
		}
	});
});
