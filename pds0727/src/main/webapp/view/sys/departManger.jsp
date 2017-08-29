<%--
  User: zjblague
  Date: 2015/6/9
  Time: 22:50
  Description:
  部门管理
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%>
<!-- 
<script type="text/javascript" src="/static/js/jsp-js/sys/departManager.js" ></script>
 -->
<script type="text/javascript">
; app.sys_departManger = function (){

	var isFirst = true;

	$('#depart_table').tree({
		 url:'/departmentManager/getDepartmentTree',
		 lines:true,
		onLoadSuccess: function () {

			if(isFirst){
				$(this).find(".tree-title:eq(0)").click();
				isFirst = false;
			}
		}
	});



	$('#depart_table').tree({
		onClick: function(node){
			$("#user_datagrid").datagrid({
				url : "/userManager/getDepartmentUser?id="+node.id,
				queryParams:{},
				pagination : true,
				pageSize : 20,
				pageList : [10, 20, 30, 50],
				striped : true,
				fit : true,//datagrid自适应宽高
				nowrap:true,//设置为false时，当一行数据显示不下时，自动折行
				rownumbers:true,
				singleSelect:true,
				frozenColumns:[[{//冻结列特性 ,不要与fitColumns 特性一起使用 
					checkbox: true,
					field : 'id',
					title : 'ID',
					width : 10
				},{
					field : 'name',
					title : '姓名',
					align : 'center',
					width : 100
				}]],
				columns : [ [ {
					field : 'account',
					title : '账号',
					align : 'center',
					width : 150
				},{
					field : 'gender',
					title : '性别',
					align : 'center',
					width : 80,
					formatter:function(value,row,index){
						if(value == 1){
							return "男";
						} else if(value == 0) {
							return "女";
						} else {
							return "";
						}
					}
				},{
					field : 'tel',
					title : '手机号',
					align : 'center',
					width : 100
				},{
					field : 'email',
					title : '电子邮箱',
					width : 150,
					hidden: true
				},{
					field : 'weixin',
					title : '微信',
					width : 150,
					hidden: false
				}, {
					field : 'departmentId',
					title : '部门ID',
					width : 30,
					hidden: true
				},{
					field : 'departmentName',
					title : '部门名称',
					align : 'center',
					width : 150
				},{
					field : 'role',
					title : '角色ID',
					width : 100,
					hidden : true
				},{
					field : 'deviceId',
					title : '上一次登陆设备ID',
					width : 150,
					hidden: true
				},{
					field : 'lastpwddate',
					title : '最后修改密码时间',
					width : 120,
					hidden: true,
					formatter : function(value,row,index){
						if(value != null){
							var lastPwdDate = new Date(value);
							return lastPwdDate.format("yyyy-MM-dd");
						}
						return "";
					}
				},{
					field : 'lastlogdate',
					title : '最后登陆时间',
					width : 120,
					hidden: true,
					formatter : function(value,row,index){
						if(value != null){
							var lastLogDate = new Date(value);
							return lastLogDate.format("yyyy-MM-dd");
						}
						return "";
					}
				},{
					field : 'createDate',
					title : '创建时间',
					width : 100,
					hidden: true,
					align : 'center',
					formatter : function(value,row,index){
						var createDate = new Date(value);
						return createDate.format("yyyy-MM-dd");
					}
				},{
					field : 'isUsed',
					title : '是否使用',
					width : 50,
					align : 'center',
					hidden : true
				}] ],
				toolbar: [
				{
					text : '查询用户',
					iconCls: 'icon-search',
					handler: function(){
						findUser(node.id);
					}
				},'-',<shiro:hasPermission name="organization:depart:addUser" >{
					text : '添加用户',
					iconCls: 'icon-add',
					handler: function(){
						addUser(node.id);
					}
				},'-',</shiro:hasPermission> <shiro:hasPermission name="organization:depart:delUser" >{
					text : '删除用户',
					iconCls: 'icon-cancel',
					handler: function(){
						delUser();
					}
				},'-',</shiro:hasPermission> <shiro:hasPermission name="organization:depart:editUser" >{
					text : '修改用户',
					iconCls: 'icon-edit',
					handler: function(){
						editUser();
					}
				}</shiro:hasPermission> ]
			});

		}
	});
};
app.sys_departManger();

/*
 * 同步部门和用户
 */
$("#syncUserDep").click(function(){
	$.ajax({
		type : 'POST',
		url : "/syncUserDep/userAndDeaprtment",
		dataType: 'json',
		success: function(result){
			if(result.success){
				$('#depart_table').tree('reload');
				$("#user_datagrid").datagrid('reload');
				$.messager.alert('提示信息',result.msg,"info");
			} else {
				$.messager.alert('错误信息',result.msg,"error");
			}
		},
		error : function(r){
			$.messager.alert('错误信息',r.msg,"error");
		}
	});
});

/**
 * 添加部门
 */
$("#add_depart").click(function(){
	var title = '添加部门';
	var url = '/departmentManager/addDepartment';
	//加载部门下拉框
	$("#parentId").combobox({    
		url:'/departmentManager/getDepartmentList?selectId='+null,    
		valueField:'id',    
		textField:'text'
	});
	add_edit_depart_dialog(title,url);
});

/**
 * 修改部门
 */
$("#edit_depart").click(function(){
	var department = $('#depart_table').tree('getSelected');
	if(department != null){
		$('#add_depart_form').form('load', '/departmentManager/getDepartment?id='+department.id);
		//加载部门下拉框
		$("#parentId").combobox({    
			url:'/departmentManager/getDepartmentList?selectId='+department.parentId,    
			valueField:'id',    
			textField:'text'
		});
		var title = '修改部门';      
		var url = '/departmentManager/editDepartment';
		add_edit_depart_dialog(title,url);
		
	} else {
		$.messager.alert("提示信息","请选择需要修改的部门","info");
	}
});

/**
 * 添加 修改 部门
 * @param title
 * @param url
 */
function add_edit_depart_dialog(title, url){
	$('#add_depart_dialog').show().dialog({
		title: title,    
		width: 400,    
		height: 300,    
		closed: false,
		closable: false,
		cache: false,    
		modal: true,
		buttons : [{
			iconCls : 'icon-ok',
			text : '确定',
			handler : function() {
				$('#add_depart_form').form('submit', {
					url : url,
					onSubmit : function() {
						var departmentName = $('#departmentName').val();
						if(departmentName == null || departmentName == "") {
							$.messager.alert("提示信息","请输入部门名称","info");
							return false;
						};
						// \:*?"<>｜
						if(departmentName.indexOf("\\") > 0
							|| departmentName.indexOf(":") > 0
							|| departmentName.indexOf("*") > 0
							|| departmentName.indexOf("?") > 0
							|| departmentName.indexOf("\"") > 0
							|| departmentName.indexOf("<") > 0
							|| departmentName.indexOf(">") > 0
							|| departmentName.indexOf("/") > 0
						){
							$.messager.alert("错误信息","部门名称不能包含特殊符号","error");
							return false;
						}
					},
					success : function(result) {
						result = $.parseJSON(result);
						if(result.state == "ok"){
							$("#add_depart_dialog").dialog('close');
							$("#add_depart_form").form('clear');
							$('#depart_table').tree('reload');
							//添加部门后,刷新部门树后  再打开树节点
//							$("#depart_table").tree({
//								onLoadSuccess : function(tree_node, data){
//									$('#depart_table').tree('expandAll');
//								}
//							});
							$("#user_datagrid").datagrid('reload');
							$.messager.alert('提示信息',result.message,"info");
						} else {
							$.messager.alert('错误信息',result.message,"error");
						}
					}
				});
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				$("#add_depart_dialog").dialog('close');
				$("#add_depart_form").form('clear');
			}
		}]
	});
}

/**
 * 删除部门
 */
$("#del_depart").click(function(){
	var delDepartment = $('#depart_table').tree('getSelected');
	if(delDepartment != null){
		var delDepartmentId = delDepartment.id;
		var delDepartParentId = delDepartment.parentId;
		var isleaf = false;
		var isLeaf = $('#depart_table').tree('isLeaf',delDepartment.target);
		if(!isLeaf){
			$.messager.confirm('确认对话框', '该部门下还有子部门,确定要一起删除吗?', function(r){
				if (r){
					isleaf = true;
					del_department(delDepartmentId, isleaf,delDepartParentId);
				}
			});
		} else {
			$.messager.confirm('确认对话框', '确定删除所选部门吗?', function(r){
				if (r){
					del_department(delDepartmentId, isleaf,delDepartParentId);
				}
			});
		};
	} else {
		$.messager.alert("提示信息","请选择需要删除的部门","info");
	};
});
function del_department(delDepartmentId, isleaf,delDepartParentId){
	$.ajax({
		type : 'POST',
		url : "/departmentManager/delDepartment?id="+delDepartmentId+"&isleaf="+isleaf+"&parentId="+delDepartParentId,
		dataType: 'json',
		success: function(result){
			if(result.state == "ok"){
				$('#depart_table').tree('reload');
				$.messager.alert('提示信息',result.message,"info");
			} else {
				$.messager.alert('错误信息',result.message,"error");
			}
		}
	});
}

/**
 * 模糊查询用户
 */
function findUser(node_id){
	$("#find_departmentId").combobox({    
		url:'/departmentManager/getDepartmentList?selectId='+node_id,    
		valueField:'id',    
		textField:'text'
	});
	
	$("#find_user_dialog").show().dialog({
		title: '查询用户',    
		width: 320,
		height: 400,    
		closed: false,
		closable: false,
		cache: false,    
		modal: true,
		buttons : [{
			iconCls : 'icon-ok',
			text : '确定',
			handler : function() {
				var find_user = serializeObject($('#find_user_form'));
				$('#user_datagrid').datagrid('load',find_user);
				$('#find_user_form').form('clear');
				$('#find_user_dialog').dialog('close');
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				$("#find_user_form").form('clear');
				$("#find_user_dialog").dialog('close');
			}
		}]
	});
}

/**
 * 添加用户
 */
function addUser(departmentId){
	$("#departmentId_add").combobox({    
		url:'/departmentManager/getDepartmentList?selectId='+departmentId,    
		valueField:'id',
		textField:'text'
	});
	
	$("#user_dialog_add").show().dialog({
		title: '添加用户',    
		width: 350,
		height: 520,    
		closed: false,
		closable: false,
		cache: false,    
		modal: true,
		buttons : [{
			iconCls : 'icon-ok',
			text : '确定',
			handler : function() {
				var name = $("#name_add").validatebox("isValid");//验证名字
				if(!name){
		    		$.messager.alert("提示信息","请输入姓名","info");
		    		return false;
		    	}
				var userName = $("#name_add").val();
				var addGender = $("input[name='add_gender']:checked").val();
				var accountValue = $("#account_add").val();
		    	if(accountValue == ""){
		    		$.messager.alert("提示信息","请输入账号!","info");
		    		return false;
		    	}
		    	var pwd = $("#pwd_add").val();
		    	if(pwd == ""){
		    		$.messager.alert("提示信息","请输入密码!","info");
		    		return false;
		    	} else {
	    			var pwd_2 = $("#pwd_2_add").val();
	    			if(pwd_2 == ""){
	    				$.messager.alert("提示信息","请输入确认密码!","info");
	    				return false;
	    			} else {
	    				if (pwd != pwd_2){
	    					$.messager.alert("提示信息","两次输入的密码不一致,请重新输入!","info");
	    					return false;
	    				}
	    			}
		    	}
		    	var telValue = $("#tel_add").val();
		    	var weixinValue = $("#weixin_add").val();
		    	var emailValue = $("#email_add").val();
		    	if(telValue == "" && weixinValue == "" && emailValue == ""){
		    		$.messager.alert("提示信息","请输入手机号或邮箱或微信号!","info");
		    		return false;
		    	} else {
		    		var tel = $("#tel_add").validatebox("isValid");//验证手机号
		    		if(!tel){
		    			$.messager.alert("提示信息","请输入正确格式的手机号!","info");
		    			return false;
		    		}
			    	var email = $("#email_add").validatebox("isValid");//验证邮箱
			    	if(!email){
			    		$.messager.alert("提示信息","请输入正确格式的邮箱!","info");
			    		return false;
			    	}
		    	}
		    	var roleValue = $("#role_add").combobox('getValue');//验证角色
		    	if(roleValue == null || roleValue == ""){
		    		$.messager.alert("提示信息","请选择用户角色!","info");
		    		return false;
		    	}
		    	var departmentId = $("#departmentId_add").combobox('getValue');
		    	var departmentName = "";
		    	if(departmentId != 1){
		    		departmentName = $("#departmentId_add").combobox('getText');
		    	}
		    	$.ajax({
					type : 'POST',
					url : "/userManager/addUser",
					dataType: 'json',
					data:{
						name : userName,
						account : accountValue,
						pwd : pwd,
						gender : addGender,
						tel : telValue,
						email : emailValue,
						weixin : weixinValue,
						departmentId : departmentId,
						departmentName : departmentName,
						role : roleValue
					},
					success: function(result){
						if(result.state == "ok"){
				    		//$("#user_form_add").form('clear');
				    		resetForm("user_form_add");
				    		$("#user_dialog_add").dialog('close');
				    		$('#user_datagrid').datagrid('reload');
				    		$.messager.alert('提示信息',result.message,'info');
				    	} else {
				    		$.messager.alert('错误信息',result.message,'error');
				    	}
					}
				}); 
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				$("#user_dialog_add").dialog('close');
				resetForm("user_form_add");				
			} 
		}]
	});
};

/**
 * 修改用户
 * @param url
 * @param departmentId
 * @param departmentName
 */ 
function editUser(){
	var editUser = $("#user_datagrid").datagrid('getSelected');
	if(editUser != null){
		if(editUser.id != null){

			$("#departmentId_edit").combobox({
				url: '/departmentManager/getDepartmentList?selectId=' + editUser.departmentId,
				valueField: 'id',
				textField: 'text',
				onLoadSuccess: function () {
					edit_user_dialog();
				}
			})

			$.ajax({
				type : 'POST',
				url : "/userManager/getUserById",
				data:{
					user_id : editUser.id
				},
				dataType: 'json',
				success: function(result){
					$('#user_form_edit').form('load', result);
				}
			});
		};
	} else {
		$.messager.alert("提示信息","请选择需要修改的用户","info");
	};
};

function edit_user_dialog(){
	$("#user_dialog_edit").show().dialog({
		title: '修改用户',
		width: 350,
		height: 520,
		closed: false,
		closable: false,
		cache: false,
		modal: true,
		buttons : [{
			iconCls : 'icon-ok',
			text : '确定',
			handler : function() {
				var departmentId = $("#departmentId_edit").combobox('getValue');
				if(!departmentId){
					$.messager.alert("提示信息","必须选择一个部门","info");
					return false;
				};
//				var departmentName = $("#departmentId_edit").combobox('getText');
				$("#user_form_edit").form('submit',{
					url:'/userManager/editUser?departmentId='+departmentId,
					onSubmit: function(){
						var name = $("#name_edit").validatebox("isValid");//验证名字
						if(!name){
							$.messager.alert("提示信息","请输入姓名","info");
							return false;
						};
						var accountValue = $("#account_edit").val();
						if(accountValue == ""){
							$.messager.alert("提示信息","请输入账号!","info");
							return false;
						};
						var telValue = $("#tel_edit").val();
						var weixinValue = $("#weixin_edit").val();
						var emailValue = $("#email_edit").val();
						if(telValue == "" && weixinValue == "" && emailValue == ""){
							$.messager.alert("提示信息","请输入手机号或邮箱或微信号!","info");
							return false;
						} else {
							var tel = $("#tel_edit").validatebox("isValid");//验证手机号
							if(!tel){
								$.messager.alert("提示信息","请输入正确格式的手机号!","info");
								return false;
							};
							var email = $("#email_edit").validatebox("isValid");//验证邮箱
							if(!email){
								$.messager.alert("提示信息","请输入正确格式的邮箱!","info");
								return false;
							};
						};
						var roleValue = $("#role_edit").combobox('getValue');//验证角色
						if(roleValue == null || roleValue == ""){
							$.messager.alert("提示信息","请选择用户角色!","info");
							return false;
						};
						return true;
					},
					success : function(result){
						result = $.parseJSON(result);
						if(result.state == "ok"){
							$("#user_form_edit").form('clear');
							$("#user_dialog_edit").dialog('close');
							$('#user_datagrid').datagrid('reload');
							$.messager.alert('提示信息',result.message,'info');
						} else {
							$.messager.alert('错误信息',result.message,'error');
						};
					}
				});
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				$("#user_dialog_edit").dialog('close');
				$("#user_form_edit").form('clear');
			}
		}]
	});
};


/**
 * 删除用户
 */
function delUser(){
	var delUser = $("#user_datagrid").datagrid('getSelected');
	if(delUser != null){
		$.messager.confirm("提示信息","确定要删除此用户?",function(r){
			if(r){
				$.ajax({
					type : 'POST',
					url : "/userManager/delUser",
					data:{
						user_id : delUser.id,
						account : delUser.account
					},
					dataType: 'json',
					success: function(result){
						if(result.returnBean.state == "ok"){
							$('#user_datagrid').datagrid('reload');
						};
						$.messager.alert('提示信息',result.returnBean.message,"info");
					}
				});
			};
		});
		
	} else {
		$.messager.alert("提示信息","请选择需要删除的用户","info");
	};
};

//扩展easyui表单的验证
$.extend($.fn.validatebox.defaults.rules, {
	//验证汉子
CHS: {
    validator: function (value) {
//		if(value.length < 2 && value.length > 4){
//			return false;
//		}
        return /^[\u0391-\uFFE5]+$/.test(value);
    },
    message: '请输入正确的汉字!'
},
//移动手机号码验证
tel: {  //value值为文本框中的值
    validator: function (value) {
        var reg = /^1[3|4|5|6|8|9]\d{9}$/;
        return reg.test(value);
    },
    message: '输入手机号码格式不准确!'
}
/* minLength: {   
		validator: function(value, param){ 
			return value.length >= param[0] && value.length <= param[1];    
		},   
		message: '微信号必须在4到20个字符之间!'  
}  */
//用户账号验证(只能包括 _ 数字 字母) 
//account: {//param的值为[]中值
//    validator: function (value, param) {
//        if (value.length < param[0] || value.length > param[1]) {
//            $.fn.validatebox.defaults.rules.account.message = '用户名长度必须在' + param[0] + '至' + param[1] + '范围';
//            return false;
//        } else {
//            if (!/^[\w]+$/.test(value)) {
//                $.fn.validatebox.defaults.rules.account.message = '用户名只能数字、字母、下划线组成.';
//                return false;
//            } else {
//                return true;
//            }
//        }
//    }, message: ''
//}
});

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
</script>
 
<div class="easyui-layout" data-options="fit:true">
	<!-- <table id="depart_table" ></table>   -->
	<div data-options="region:'north'" style="padding:5px;">
        <shiro:hasPermission name="organization:depart:addDepartment" >
        	<a href="#" id="add_depart" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加部门</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="organization:depart:delDepartment" >
        	<a href="#" id="del_depart" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">删除部门</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="organization:depart:editDepartment" >
        	<a href="#" id="edit_depart" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改部门</a>
        </shiro:hasPermission>
        <shiro:hasPermission name="organization:depart:syncDepUser" >
        	<a href="#" id="syncUserDep" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">同步部门和用户数据</a>
        </shiro:hasPermission>
	</div>
	<div data-options="region:'west'" style="width: 200px; padding: 10px">
		<ul id="depart_table" class="easyui-tree" >
		</ul>
	</div>
	<div data-options="region:'center'" style="padding: 10px">
		<table id="user_datagrid"></table>
	</div>
</div>

<div id="add_depart_dialog" align="center" style="display: none">
	<form id="add_depart_form" method="post" >
		<input id="sort" name="sort" type="hidden">
		<table style="margin:50px auto" >
			<tr>
				<td colspan="2"><input id="departmentId" name="departmentId" type="hidden"></td>
			</tr>
			<tr>
				<td colspan="2"><input id="hasChildren" name="hasChildren" type="hidden"></td>
			</tr>
			<tr>
				<td colspan="2"><input id="width" name="width" type="hidden"></td>
			</tr>
			<tr >
				<td>部门名称：</td>
				<td>
					<input id="departmentName" name="departmentName" class="easyui-validatebox" required=true  maxlength="64">
				</td>
			</tr>
			<tr>
				<td>上级部门：</td>
				<td>
					<input id="parentId" name="parentId" class="easyui-combobox easyui-validatebox" style="width:100%;" editable=false > 
					<!-- 
					<input id="parentId" name="parentId" class="easyui-combobox" required=true style="width:100%;" value=""
					data-options="valueField:'id',textField:'text',url:'/departmentManager/getDepartmentList'" />
					--> 
				</td>
			</tr>
		</table>
	</form>
</div>

<div id="user_dialog_add" style="display: none;" align="center">
	<form id="user_form_add" method="post" >
		<table style="margin: 15px auto;">
			<tr height="30px;">
				<td align="right">姓名：</td>
				<td>
					<input id="name_add" name="name" class="easyui-validatebox" required=true validtype='CHS' maxlength="4">
				</td>	
			</tr>
			<tr height="30px;">
				<td align="right">账号：</td>
				<td><input id="account_add" name="account" class="easyui-validatebox" required=true maxlength="64" ></td>
			</tr>
			<tr height="30px;">
				<td align="right">密码：</td>
				<td><input id="pwd_add" name="pwd" class="easyui-validatebox" required=true type="password" ></td>
			</tr>
			<tr height="30px;">
				<td align="right">确认密码：</td>
				<td><input id="pwd_2_add" name="pwd_2" class="easyui-validatebox" required=true type="password" ></td>	
			</tr> 
			<tr height="30px;">
				<td align="right">性别：</td>
				<td>
				男 <input name="add_gender" type="radio" value="1" checked="checked">
				女 <input name="add_gender" type="radio" value="0">
				</td>	
			</tr>
			<tr>
				<td colspan="2">
					<hr style="">
					<font color="#7F7F7F">身份验证信息<br>
						<font color="red" size="1px;">（以下三种信息用于绑定微信,不可同时为空.）</font>
					</font>
				</td>
			</tr>
			<tr height="30px;">
				<td align="right">手机号：</td>
				<td><input id="tel_add" name="tel" class="easyui-validatebox" validtype='tel'></td>	
			</tr>	
			<tr height="30px;">
				<td align="right">电子邮箱：</td>
				<td><input id="email_add" name="email" class="easyui-validatebox" data-options="required:false,validType:'email'"></td>
			</tr>
			<tr height="30px;">
				<td align="right">微信号：</td>
				<td><input id="weixin_add" name="weixin" class="easyui-validatebox" maxlength="64" ></td>
			</tr>
			<tr>
				<td colspan="2">
					<%--<hr style="border-style:1px solid #8B7D7B;">--%>
				</td>
			</tr>
			<tr height="30px;">
				<td align="right">部门名称：</td>
				<td>
				<%-- 
				<input id="departmentId_add" name="departmentId" class="easyui-combobox" style="width:100%;" 
				data-options="valueField:'id',textField:'text',url:'/departmentManager/getDepartmentList'" />  
				 --%>
				<input id="departmentId_add" name="departmentId" class="easyui-combobox" style="width:100%;" required=true editable=false >
				</td>
			</tr>
			<tr height="30px;">
				<td align="right">角色：</td>
				<td>
					<!-- <input id="role" name="role" class="easyui-validatebox" required=true style="width:95%;"> -->
					<input id="role_add" name="role" class="easyui-combobox" required=true style="width:100%;" editable=false
					data-options="valueField:'id',textField:'text',url:'/roleManager/comboRoleList'" />
				</td>
			</tr> 
		</table>
	</form>
</div>


<div id="user_dialog_edit" style="display: none;" align="center">
	<form id="user_form_edit" method="post" >
		<input id="id_edit" name="id" type="hidden">
		<table style="margin: 15px auto;">
			<tr height="30px;">
				<td align="right">姓名：</td>
				<td>
					<input id="name_edit" name="name" class="easyui-validatebox" required=true validtype='CHS' maxlength="4">
				</td>	
			</tr>
			<tr height="30px;">
				<td align="right">账号：</td>
				<td><input id="account_edit" name="account" class="easyui-validatebox" required=true readonly="readonly"></td>	
			</tr>
			<tr height="30px;">
				<td align="right">性别：</td>
				<td>
				男 <input name="gender" type="radio" value="1" checked="checked">
				女 <input name="gender" type="radio" value="0">
				</td>	
			</tr>
			<tr>
				<td colspan="2">
					<%--<hr style="border-left-style:1px solid #C4C4C4;">--%>
					<font color="#7F7F7F">身份验证信息<br>
						<font color="red" size="1px;">（以下三种信息用于绑定微信,不可同时为空.）</font>
					</font>
				</td>
			</tr>
			<tr height="30px;">
				<td align="right">手机号：</td>
				<td><input id="tel_edit" name="tel" class="easyui-validatebox" validtype='tel'></td>	
			</tr>	
			<tr height="30px;">
				<td align="right">电子邮箱：</td>
				<td><input id="email_edit" name="email" class="easyui-validatebox" data-options="required:false,validType:'email'"></td>
			</tr>
			<tr height="30px;">
				<td align="right">微信号：</td>
				<td><input id="weixin_edit" name="weixin" class="easyui-validatebox" maxlength="64"></td>
			</tr>
			<tr>
				<%--<td colspan="2"><hr style="border-left-style:1px solid #C4C4C4;"></td>--%>
			</tr>
			<tr height="30px;">
				<td align="right">部门名称：</td>
				<td>
				<input id="departmentId_edit" name="departmentId" editable="false" style="width: 100%">
				</td>
			</tr>
			<tr height="30px;">
				<td align="right">角色：</td>
				<td>
					<input id="role_edit" name="role" class="easyui-combobox" required=true style="width:100%;" editable=false
					data-options="valueField:'id',textField:'text',url:'/roleManager/comboRoleList'" />
					<!-- 
					<input id="role" name="role" type="text" style="width:95%;" readonly="readonly" > 
					-->
				</td>
			</tr> 
		</table>
	</form>
</div>


<div id="find_user_dialog" style="display: none;" align="center">
	<form id="find_user_form" method="post" >
		<table style="margin: 15px auto;">
			<tr height="30px;">
				<td align="right">姓名:</td>
				<td>
					<input id="find_name" name="name" >
				</td>	
			</tr>
			<tr height="30px;">
				<td align="right">账号:</td>
				<td><input id="find_account" name="account" ></td>	
			</tr>
			<tr height="30px;">
				<td align="right">性别:</td>
				<td>
				男 <input name="gender" type="radio" value="1">
				女 <input name="gender" type="radio" value="0">
				</td>	
			</tr>
			<tr height="30px;">
				<td align="right">手机号:</td>
				<td><input id="find_tel" name="tel" ></td>	
			</tr>	
			<tr height="30px;">
				<td align="right">电子邮箱:</td>
				<td><input id="find_email" name="email" ></td>
			</tr>
			<tr height="30px;">
				<td align="right">微信号:</td>
				<td><input id="find_weixin" name="weixin" ></td>
			</tr>
			<tr height="30px;">
				<td align="right">部门名称:</td>
				<td>
				<input id="find_departmentId" name="departmentId" class="easyui-combobox" style="width:100%;" editable=false >
				<%-- 
				<input id="find_departmentId" name="departmentId" class="easyui-combobox" style="width:100%;" 
				data-options="valueField:'id',textField:'text',url:'/departmentManager/getDepartmentList?type=add'" />  
				 --%>
				</td>
			</tr>
			<tr height="30px;">
				<td align="right">角色:</td>
				<td>
					<!-- 
					<input id="find_role" name="role" style="width:100%;"> 
					-->
					<input id="find_role" name="role" class="easyui-combobox" style="width:100%;" editable=false
					data-options="valueField:'id',textField:'text',url:'/roleManager/comboRoleList'" />
				</td>
			</tr> 
		</table>
	</form>
</div>

