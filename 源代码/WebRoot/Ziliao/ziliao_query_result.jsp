<%@ page language="java"  contentType="text/html;charset=UTF-8"%>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ziliao.css" /> 

<div id="ziliao_manage"></div>
<div id="ziliao_manage_tool" style="padding:5px;">
	<div style="margin-bottom:5px;">
		<a href="#" class="easyui-linkbutton" iconCls="icon-edit-new" plain="true" onclick="ziliao_manage_tool.edit();">修改</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-delete-new" plain="true" onclick="ziliao_manage_tool.remove();">删除</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-reload" plain="true"  onclick="ziliao_manage_tool.reload();">刷新</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="ziliao_manage_tool.redo();">取消选择</a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="ziliao_manage_tool.exportExcel();">导出到excel</a>
	</div>
	<div style="padding:0 0 0 7px;color:#333;">
		<form id="ziliaoQueryForm" method="post">
			标题：<input type="text" class="textbox" id="title" name="title" style="width:110px" />
			添加时间：<input type="text" id="addTime" name="addTime" class="easyui-datebox" editable="false" style="width:100px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ziliao_manage_tool.search();">查询</a>
		</form>	
	</div>
</div>

<div id="ziliaoEditDiv">
	<form id="ziliaoEditForm" enctype="multipart/form-data"  method="post">
		<div>
			<span class="label">资料id:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ziliao_ziliaoId_edit" name="ziliao.ziliaoId" style="width:200px" />
			</span>
		</div>
		<div>
			<span class="label">标题:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ziliao_title_edit" name="ziliao.title" style="width:200px" />

			</span>

		</div>
		<div>
			<span class="label">描述:</span>
			<span class="inputControl">
				<script name="ziliao.content" id="ziliao_content_edit" type="text/plain"   style="width:100%;height:500px;"></script>

			</span>

		</div>
		<div>
			<span class="label">资料文件:</span>
			<span class="inputControl">
				<a id="ziliao_ziliaoFileA" style="color:red;margin-bottom:5px;">查看</a>&nbsp;&nbsp;
    			<input type="hidden" id="ziliao_ziliaoFile" name="ziliao.ziliaoFile"/>
				<input id="ziliaoFileFile" name="ziliaoFileFile" value="重新选择文件" type="file" size="50" />
			</span>
		</div>
		<div>
			<span class="label">添加时间:</span>
			<span class="inputControl">
				<input class="textbox" type="text" id="ziliao_addTime_edit" name="ziliao.addTime" />

			</span>

		</div>
	</form>
</div>
<script>
//实例化编辑器
//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
var ziliao_content_editor = UE.getEditor('ziliao_content_edit'); //描述编辑器
</script>
<script type="text/javascript" src="Ziliao/js/ziliao_manage.js"></script> 
