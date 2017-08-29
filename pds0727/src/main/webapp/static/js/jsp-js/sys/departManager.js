/**
 * 部门管理.
 * 这里写了一个例子，以如下例子
 * ; app.sys_departManger = function (){
 *
 * };
 * 以文件名路径下划线的形式命名变量，如这个文件路径为/sys/departManager.js,那么它的变量就应该命名为sys_departManager
 * 所有的代码都写到方法体内，私有参数请以var 起头声明，如果想外部访问就不加var声明
 * Created by 张靖
 *           2015-6-9 23:15.
 */
; app.sys_departManger = function (){
	
	/*
	$('#depart_table').treegrid({    
	    url:'/departmentManager/getDepartmentTree',    
	    idField:'id',    
	    treeField:'name',  
	    columns:[[    
	        {title:'部门名称',field:'name',width:180}
	    ]]    
	}); 
	*/

	$('#depart_table').tree({ 
		 url:'/departmentManager/getDepartmentTree',
//		 checkbox:true,
		 lines:true
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
						} else if(value == 2) {
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
					width : 100,
					hidden: true
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
				toolbar: [{
					text : '查询用户',
					iconCls: 'icon-search',
					handler: function(){
						findUser();
					}
				},'-',{
					text : '添加用户',
					iconCls: 'icon-add',
					handler: function(){
						addUser(node.id);
					}
				},'-',{
					text : '删除用户',
					iconCls: 'icon-cancel',
					handler: function(){
						delUser();
					}
				},'-',{
					text : '修改用户',
					iconCls: 'icon-edit',
					handler: function(){
						editUser();
					}
				}]
			});
		}
	});
	
	
	
};
app.sys_departManger();


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
						}
						$.messager.alert('提示信息',result.message,"info");
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
			}
			$.messager.alert('提示信息',result.message,"info");
		}
	});
}

/**
 * 模糊查询用户
 */
function findUser(){
	
	$("#find_departmentId").combobox({    
		url:'/departmentManager/getDepartmentList?selectId='+null,    
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
	//角色下拉框
//	$('#role').combobox({    
//		url:'/roleManager/comboRoleList',    
//		valueField:'id',    
//		textField:'text'
//	});
//	$("#departmentId_add").val(departmentId);
	
//	console.log(departmentId);
	
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
				$("#user_form_add").form('submit',{
					url:'/userManager/addUser',
				    onSubmit: function(){
				    	var name = $("#name_add").validatebox("isValid");//验证名字
				    	if(!name){
				    		$.messager.alert("提示信息","请输入姓名","info");
				    		return false;
				    	}
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
				    		var weixin = $("#weixin_add").validatebox("isValid");//验证微信
				    		if(!weixin){
				    			$.messager.alert("提示信息","请输入正确格式的微信号!","info");
				    			return false;
				    		}
				    	}
				    	var roleValue = $("#role_add").combobox('getValue');//验证角色
				    	if(roleValue == null || roleValue == ""){
				    		$.messager.alert("提示信息","请选择用户角色!","info");
				    		return false;
				    	}
				    	return true;
				    },    
				    success : function(result){
				        result = $.parseJSON(result);
				    	if(result.state == "ok"){
				    		$("#user_form_add").form('clear');
				    		$("#user_dialog_add").dialog('close');
				    		$('#user_datagrid').datagrid('reload');
				    	} 
				    	$.messager.alert('提示信息',result.message);
				    }
				});
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				$("#user_dialog_add").dialog('close');
				$("#user_form_add").form('clear');
			}
		}]
	});
}


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
			edit_user_dialog(editUser.id,editUser.departmentId);
		}
	} else {
		$.messager.alert("提示信息","请选择需要修改的用户","info");
	}
}

function edit_user_dialog(editUserId, departmentId){
	$("#departmentId_edit").combobox({    
		url:'/departmentManager/getDepartmentList?selectId='+departmentId,    
		valueField:'id',
		textField:'text'
	});
	
	$('#user_form_edit').form('load', '/userManager/getUserById?user_id='+editUserId);
	
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
				if(departmentId == 1){
		    		$.messager.alert("提示信息","必须选择一个部门","info");
		    		return false;
		    	}
//				var departmentName = $("#departmentId_edit").combobox('getText');
				$("#user_form_edit").form('submit',{
					url:'/userManager/editUser?departmentId='+departmentId,
				    onSubmit: function(){
				    	var name = $("#name_edit").validatebox("isValid");//验证名字
				    	if(!name){
				    		$.messager.alert("提示信息","请输入姓名","info");
				    		return false;
				    	}
				    	var accountValue = $("#account_edit").val();
				    	if(accountValue == ""){
				    		$.messager.alert("提示信息","请输入账号!","info");
				    		return false;
				    	}
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
				    		}
					    	var email = $("#email_edit").validatebox("isValid");//验证邮箱
					    	if(!email){
					    		$.messager.alert("提示信息","请输入正确格式的邮箱!","info");
					    		return false;
					    	}
				    		var weixin = $("#weixin_edit").validatebox("isValid");//验证微信
				    		if(!weixin){
				    			$.messager.alert("提示信息","请输入正确格式的微信号!","info");
				    			return false;
				    		}
				    	}
				    	var roleValue = $("#role_edit").combobox('getValue');//验证角色
				    	if(roleValue == null || roleValue == ""){
				    		$.messager.alert("提示信息","请选择用户角色!","info");
				    		return false;
				    	}
				    	return true;
				    },    
				    success : function(result){
				        result = $.parseJSON(result);
				    	if(result.state == "ok"){
				    		$("#user_form_edit").form('clear');
				    		$("#user_dialog_edit").dialog('close');
				    		$('#user_datagrid').datagrid('reload');
				    	} 
				    	$.messager.alert('提示信息',result.message);
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
}

/**
 * 删除用户
 */
function delUser(){
	var delUser = $("#user_datagrid").datagrid('getSelected');
//	console.log(delUser);
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
						}
						$.messager.alert('提示信息',result.returnBean.message,"info");
					}
				});
			}
		});
		
	} else {
		$.messager.alert("提示信息","请选择需要删除的用户","info");
	}
}

//扩展easyui表单的验证
$.extend($.fn.validatebox.defaults.rules, {
	//验证汉子
CHS: {
    validator: function (value) {
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
},
minLength: {   
		validator: function(value, param){ 
			return value.length >= param[0] && value.length <= param[1];    
		},   
		message: '微信号必须在4到20个字符之间!'  
} 
//国内邮编验证
//zipcode: {
//    validator: function (value) {
//        var reg = /^[1-9]\d{5}$/;
//        return reg.test(value);
//    },
//    message: '邮编必须是非0开始的6位数字.'
//},
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
