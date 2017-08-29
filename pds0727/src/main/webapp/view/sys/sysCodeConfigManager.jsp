<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<script type="text/javascript">
; app.sys_roleManager = (function (){
	
	$('#code_datagrid').datagrid({
		url : "/sysCodeConfigManager/findSysCode",
		pagination : true,
		pageSize : 10,
		pageList : [10, 20, 30, 50],
		fit : true,
		striped : true,
		fitColumns : true,
		nowrap : true,
		rownumbers : true,
		singleSelect : true,
		onLoadSuccess:function(data){
			$(this).datagrid('doCellTip',{
				onlyShowInterrupt: false,     //是否只有在文字被截断时才显示tip，默认值为false  无效           
                position: 'bottom',   //tip的位置，可以为top,botom,right,left
                cls: { 'background-color': '#D1EEEE' },  //tip的样式
                delay: 500   //tip 响应时间
			});
		},
		columns : [[{
			checkbox : true,
			field : 'id',
			title : 'id',
			width : 50
		},{ 
			field : 'typecode',
			title : '类别编码',
			width : 50
		},{ 
			field : 'bm',
			title : '字典编码',
			width : 50
		},{
			field : 'mc',
			title : '字典名称',
			width : 80
		},{
			field : 'value',
			title : '字典值',
			width : 200
		},{
			field : 'bz',
			title : '字典备注',
			width : 200
		}]],
		toolbar : [{
   			text : '查询字典',
   			iconCls : 'icon-search',
   			handler : function() {
   				find_sysCode();
   			}
   		},'-',<shiro:hasPermission name="sys:sysCode:addSysCode" >{
   			text : '添加字典',
   			iconCls : 'icon-add',
   			handler : function() {
   				add_sysCode();
   			}
   		},'-',</shiro:hasPermission> <shiro:hasPermission name="sys:sysCode:editSysCode" >{
   			text : '修改字典',
			iconCls : 'icon-edit',
			handler : function() {
				edit_sysCode();
			}
   		},'-',</shiro:hasPermission> <shiro:hasPermission name="sys:sysCode:delSysCode" >{
   			text : '删除字典',
			iconCls : 'icon-cancel',
			handler : function() {
				del_sysCode();
			}
   		}</shiro:hasPermission>]
	});
})();

/**
 * 查询
 */
function find_sysCode(){
	$("#syscode_dialog").show().dialog({
		title: '查询数据字典',    
		width: 400,
		height: 350,    
		closed: false,
		closable: false,
		cache: false,    
		modal: true,
		buttons : [{
			iconCls : 'icon-ok',
			text : '确定',
			handler : function() {
				var find_code = serializeObject($('#syscode_form'));
				$('#code_datagrid').datagrid('load',find_code);
				$('#syscode_form').form('clear');
				$('#syscode_dialog').dialog('close'); 
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				$("#syscode_form").form('clear');
				$("#syscode_dialog").dialog('close');
			}
		}]
	});
	
}

/*
 * 添加数据字典
 */
function add_sysCode(){
	submitForm('添加数据字典','/sysCodeConfigManager/addSysCode');
}

/*
 * 修改数据字典
 */
function edit_sysCode(){
	var editCode = $("#code_datagrid").datagrid('getSelected');
	if(editCode != null){
		if(editCode.id != null){
			$('#edit_syscode_form').form('load', '/sysCodeConfigManager/getSysCodeById?syscode_id='+editCode.id);
			submitForm_edit(editCode.id);
		};
	} else {
		$.messager.alert("提示信息","请选择需要修改的数据","info");
	};
}

/*
 * 修改
 */
function submitForm_edit(id){
	$("#edit_syscode_dialog").show().dialog({
		title: '修改数据字典',    
		width: 400,
		height: 350,    
		closed: false,
		closable: false,
		cache: false,    
		modal: true,
		buttons : [{
			iconCls : 'icon-ok',
			text : '确定',
			handler : function() {
				$('#edit_syscode_form').form('submit', {  
				    url:'/sysCodeConfigManager/editSysCode?syscode_id='+id,
				    onSubmit:function(){
				    	var isValid = $('#edit_syscode_form').form('validate');
						if(!isValid){
							$.messager.alert('提示信息','验证失败，请按要求输入!','info');
							return false;
						}
				    },
					success : function(result){
				        result = $.parseJSON(result);
				    	if(result.state == "ok"){
				    		$('#edit_syscode_form').form('clear');
							$('#edit_syscode_dialog').dialog('close'); 
							$('#code_datagrid').datagrid('reload');
				    	} 
				    	$.messager.alert('提示信息',result.message,"info");
				    }
				});
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				$("#edit_syscode_form").form('clear');
				$("#edit_syscode_dialog").dialog('close');
			}
		}]
	});
}


/*
 * 增、查的 表单封装
 */
function submitForm(title,url){
	$("#syscode_dialog").show().dialog({
		title: title,    
		width: 400,
		height: 350,    
		closed: false,
		closable: false,
		cache: false,    
		modal: true,
		buttons : [{
			iconCls : 'icon-ok',
			text : '确定',
			handler : function() {
				$('#syscode_form').form('submit', {  
				    url:url,
				    onSubmit:function(){
				    	var isValid = $('#syscode_form').form('validate');
						if(!isValid){
							$.messager.alert('提示信息','验证失败，请按要求输入!','info');
							return false;
						}
				    },
					success : function(result){
				        result = $.parseJSON(result);
				    	if(result.state == "ok"){
				    		$('#syscode_form').form('clear');
							$('#syscode_dialog').dialog('close'); 
							$('#code_datagrid').datagrid('reload');
				    	} 
				    	$.messager.alert('提示信息',result.message,"info");
				    } 
				});
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				$("#syscode_form").form('clear');
				$("#syscode_dialog").dialog('close');
			}
		}]
	});
}

/**
 * 删除数据字典
 */
function del_sysCode(){
	var del_sysCode = $('#code_datagrid').datagrid('getSelected');
	if(del_sysCode != null){
		var del_sysCode_id = del_sysCode.id;
		if(del_sysCode_id != null){
			$.messager.confirm('确认对话框', '确定要删除该数据字典吗?', function(r){
				if (r){
					$.ajax({
						type : 'POST',
						url : "/sysCodeConfigManager/delSysCode?delId="+del_sysCode_id+"&typecode="+del_sysCode.typecode+"&bm="+del_sysCode.bm,
						dataType: "json",
						success: function(result){
							if(result.state == "ok"){
								$('#code_datagrid').datagrid('reload');
							}
							$.messager.alert('提示信息',result.message,"info"); 
						},
						error: function(){
							$.messager.alert('错误信息',result.message,"error"); 
						}
					});
				}
			});
		} else {
			$.messager.alert('错误信息','数据异常，请联系管理员',"error"); 
		};
	} else {
		$.messager.alert("提示信息","请选择需要删除的数据","info");
	};
}


//转换表单
function serializeObject(form) {
	var o = {};
	$.each(form.serializeArray(), function(index, n) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

//扩展easyui表单的验证
$.extend($.fn.validatebox.defaults.rules, {
ZHIMU_SHUZHI_FUHAO: {
    validator: function (value) {
    var zhimu_shuzhi_fuhao_reg = /^(((?=[\x21-\x7e]+)[^A-Za-z0-9])+)|([A-Za-z0-9]+)|(((?=[\x21-\x7e]+)[^A-Za-z0-9])+)$/;
        return zhimu_shuzhi_fuhao_reg.test(value);
    },
    message: '必须是字母、数字或符号!'
}
});
</script>

<table id="code_datagrid"></table>

<div id="syscode_dialog" style="display: none;" align="center">
	<form id="syscode_form" method="post" >
		<input id="id" name="id" type="hidden" />
		<table style="margin: 15px auto;">
			<tr height="40px;">
				<td align="right">类别编码：</td>
				<td>
					<input id="typecode" name="typecode" class="easyui-textbox easyui-validatebox" prompt="输入必须是字母、数字或符号" 
					data-options="required:true,validType:'ZHIMU_SHUZHI_FUHAO'" style="width:200px;">
				</td>
			</tr> 
			<tr height="40px;">
				<td align="right">字典编码：</td>
				<td>
					<input id="bm" name="bm" class="easyui-numberbox" prompt="输入必须是数字" required='true' style="width:200px;">
				</td>
			</tr> 
			<tr height="40px;">
				<td align="right">字典名称：</td>
				<td>
					<input id="mc" name="mc" class="easyui-textbox" prompt="请输入字典名称" style="width:200px;" >
				</td>
			</tr> 
			<tr height="40px;">
				<td align="right">字典备注：</td>
				<td>
					<input id="bz" name="bz" class="easyui-textbox" prompt="请输入字典备注"  style="width:200px;" >
				</td>
			</tr> 
			<tr height="40px;">
				<td align="right">字典值：</td>
				<td>
					<input id="value" name="value" class="easyui-textbox" prompt="请输入字典值" style="width:200px;" >
				</td>
			</tr> 
		</table>
	</form>
</div>


<div id="edit_syscode_dialog" style="display: none;" align="center">
	<form id="edit_syscode_form" method="post" >
		<input id="edit_id" name="id" type="hidden" />
		<table style="margin: 15px auto;">
			<tr height="40px;">
				<td align="right">类别编码：</td>
				<td>
					<input id="edit_typecode" name="typecode" class="easyui-textbox" style="width:200px;" editable=false >
				</td>
			</tr> 
			<tr height="40px;">
				<td align="right">字典编码：</td>
				<td>
					<input id="edit_bm" name="bm" class="easyui-textbox"  style="width:200px;" editable=false >
				</td>
			</tr> 
			<tr height="40px;">
				<td align="right">字典名称：</td>
				<td>
					<input id="edit_mc" name="mc" class="easyui-textbox" prompt="请输入字典名称" required=true style="width:200px;" >
				</td>
			</tr> 
			<tr height="40px;">
				<td align="right">字典备注：</td>
				<td>
					<input id="edit_bz" name="bz" class="easyui-textbox" prompt="请输入字典备注"  style="width:200px;" >
				</td>
			</tr> 
			<tr height="40px;">
				<td align="right">字典值：</td>
				<td>
					<input id="edit_value" name="value" class="easyui-textbox" prompt="请输入字典值" style="width:200px;" >
				</td>
			</tr> 
		</table>
	</form>
</div>




