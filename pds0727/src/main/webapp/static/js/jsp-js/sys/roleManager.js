var editing ; //判断用户是否处于编辑状态
var isFinish = true;

; app.sys_roleManager = (function (){
	var flag;	  //判断新增和修改方法
	$('#role_datagrid').datagrid({
		url : "/roleManager/getRoleList",
		pagination : true,
		pageSize : 10,
		pageList : [10, 20],
		fit : true,
		striped : true,
		fitColumns : true,
		nowrap : true,
		rownumbers : true,
		singleSelect : true,
		columns : [[{
			checkbox : true,
			field : 'roleId',
			title : '角色ID',
			width : 100
		},{ 
			field : 'roleName',
			title : '角色名',
			width : 200,
			editor:{
				type:'validatebox' ,
				options:{
					required:true , 
					missingMessage:'角色名必填!'
				}
			}
		},{
			field : 'description',
			title : '说明',
			width : 400,
			editor:{
				type:'validatebox' ,
				options:{
					required:true , 
					missingMessage:'角色说明必填!'
				}
			}
		},{
			field : 'role',
			title : '角色的值',
			width : 200,
			editor:{
				type:'validatebox' ,
				options:{
					required:true , 
					missingMessage:'角色值必填!'
				}
			}
		}]],
		toolbar : [{
			text : '添加角色',
			iconCls : 'icon-add',
			handler : function() {
				if(isFinish){
					if(editing == null){
						flag = 'add';
						$('#role_datagrid').datagrid('unselectAll');//1 先取消所有的选中状态
						$('#role_datagrid').datagrid('appendRow',{description:''});//2追加一行
						editing = $('#role_datagrid').datagrid('getRows').length -1;//3获取当前页的行号.
						$('#role_datagrid').datagrid('beginEdit', editing);//4开启编辑状态
						isFinish = false;
					}
				} else {
					$.messager.alert('提示信息',"编辑操作未保存，请先保存或取消再进行其它操作！","info");
				}
			}
		},{
			text : '删除角色',
			iconCls : 'icon-cancel',
			handler : function() {
				if(isFinish){
					var delRole = $('#role_datagrid').datagrid('getSelected');
					if(delRole == null){
						$.messager.alert('提示', '请选择需要删除角色!', 'info');
					} else {
						$.messager.confirm('提示信息' , '确认删除?' , function(r){
							if(r){
								var role_id = delRole.roleId;
								$.ajax({
									type : 'POST',
									url : "/roleManager/delRole?role_id="+role_id,
									dataType: 'json',
									success: function(result){
										if(result.returnBean.state == "ok"){
											$('#role_datagrid').datagrid('reload');
											$.messager.alert('提示信息',result.returnBean.message, 'info');
										} else {
											$.messager.alert('提示信息',result.returnBean.message, 'info');
										}
									}
								});
							} else {
								 return ;
							}
						});
					}
				} else {
					$.messager.alert('提示信息',"编辑操作未保存，请先保存或取消再进行其它操作！","info");
				}
			}
		},{
			text : '修改角色',
			iconCls : 'icon-edit',
			handler : function() {
				if(isFinish){
					var editRole = $('#role_datagrid').datagrid('getSelected');
					if(editRole == null){
						$.messager.alert('提示', '请选择需要修改的角色!', 'info');
					} else {
						editing = null;
						if(editing == null){
							flag = 'edit';
							//根据行记录对象获取该行的索引位置
							editing = $('#role_datagrid').datagrid('getRowIndex', editRole);
							//开启编辑状态
							$('#role_datagrid').datagrid('beginEdit',editing);
						}
						isFinish = false;
					}
				} else {
					$.messager.alert('提示信息',"编辑操作未保存，请先保存或取消再进行其它操作！","info");
				}
			}
		},{
			text:'保存操作',
			iconCls:'icon-save' , 
			handler:function(){
				saveRole();
			}
		},{
			text:'取消操作',
			iconCls:'icon-back' , 
			handler:function(){
				cancelRole();
			}
		},{
			text:'资源授权',
			iconCls:'icon-filter' , 
			handler:function(){
				if(isFinish){	
					var editRole = $('#role_datagrid').datagrid('getSelected');
					if(editRole == null){
						$.messager.alert('提示', '请选择需要授权的角色!', 'info');
					} else {
						var roleId = editRole.roleId;
						var resourceTree = $.fn.zTree.init($('#resourceAuthorityTree'), {
							async: {
								enable: true,
								url: "/roleResourceManager/resourceTree",
								otherParam:{
									roleId:roleId
								}
							},
							data: {
								key: {
									name: "text"
								},
								simpleData:{
									enable: true,
									pIdKey:"parentId",
									rootPId:"0"
								}
							},
							check:{
								chkboxType: { "Y" : "ps", "N" : "s" },
								chkStyle: "checkbox",
								enable: true
							}
						});
	
						var resourceTreeDialog = $('#resourceTree_dialog').dialog({
						    title: '授权列表',
						    width: 400,    
						    height: 500,    
						    closed: false,    
						    cache: false,    
						    modal: true,
							buttons:[{
								text:"保存",
								handler: function () {
									var changes = resourceTree.getChangeCheckedNodes();
									var addArray = new Array();
									var deleteArray = new Array();
									for(var i = 0,length = changes.length;i < length ;i++){
										var item = changes[i];
										if(item.checked){
											addArray.push(item.id);
										}else{
											deleteArray.push(item.id);
										}
									}
									$(".load_mask").easyMask('show');
									$.ajax({
										url:"/roleResourceManager/authorize",
										method:"post",
										dataType:"json",
										data:{
											add:addArray,
											remove:deleteArray,
											roleId:roleId
										},
										complete: function () {
											$(".load_mask").easyMask('hide');
										},
										success: function (data) {
											$.messager.alert("提示","修改成功", 'info');
											resourceTreeDialog.dialog("close");
										}
									})
								}
							},{
								text:"关闭",
								handler: function () {
									resourceTreeDialog.dialog("close");
								}
							}]
						});    
					}
				} else {
					$.messager.alert('提示信息',"编辑操作未保存，请先保存或取消再进行其它操作！","info");
				}		
			}
		}],
		onAfterEdit:function(index,data,record){
			var url = (flag == "add"?"/roleManager/addRole":"/roleManager/updateRole");
			$.ajax({
				type: 'POST',
				url: url,
				data: data,
				dataType: 'json',
				success: function(result){
					if(result.returnBean.state == "ok"){
						$('#role_datagrid').datagrid('reload');
					}
					$.messager.alert('提示信息',result.returnBean.message,"info");
				}
			});
		}
	});

/*				
	$(document).bind('mousedown',function(e) {
		if (editing > 0 && isButton == false) {
			var v = {} ;
			var editor = $('#role_datagrid').datagrid('getEditors', editing);
			for(var i = 0 ; i < editor.length;i++){
				v = $(e.target).closest(editor[i].target);
				if(v.length) break;
			}
			if (v.length){
				//焦点是修改器
				console.log("焦点还在");
			} else {
				console.log("失去焦点");
				if(state){
					console.log("state--1--  "+state);
				} else {
//				state = true;
					console.log("state--2--  "+state);
					$('#confirm_dialog').show().dialog({
						title:'确认对话框',
						width:350,    
						height:120,
						closable:false,
						modal:true,
						collapsible:false,
						buttons:[{
							text:'保存数据',
							iconCls:'icon-save',
							handler:function(){
								saveRole();
							}
						},{
							text:'取消保存',
							iconCls:'icon-cancel',
							handler:function(){
								cancelRole();
							}
						},{
							text:'返回编辑',
							iconCls:'icon-back',
							handler:function(){
								backRole();
							}
						}]

					}); 
				}
				console.log("state--3--  "+state);
//				var result = $('#role_datagrid').datagrid('validateRow',editing);
				v =  $(e.target).closest();
			}
		}
	});
 */				
	
})();

/**
 * 保存
 */
function saveRole(){
	//保存之前进行数据的校验 , 然后结束编辑并回显编辑状态字段 
	var roleName = $('#role_datagrid').datagrid('getEditor',{index:editing,field:'roleName'});
	if(roleName == null || roleName == ""){
		$.messager.alert("提示信息","角色名不能为空!","info");
		return false;
	} else {
		//验证角色名在数据库是不是已存在
		var roleNameValue = roleName.target[0].value.replace(/^\s+|\s+$/g,"");
		$.ajax({
			type:'POST',
			url:'/roleManager/changeRole',
			data:{
				roleName:roleNameValue,
			},
			dataType:'json',
			success:function(result){
				if(result.state == "error"){
					$.messager.alert('提示信息',result.message,"info");
					return false;
				}
				if(result.state == "ok"){
					var description = $('#role_datagrid').datagrid('getEditor',{index:editing,field:'description'});
					description = description.target[0].value.replace(/^\s+|\s+$/g,"");
					if(description == null || description == ""){
						$.messager.alert("提示信息","角色说明不能为空!","info");
						return false;
					}
					var role = $('#role_datagrid').datagrid('getEditor',{index:editing,field:'role'});
					var reg = /^[a-z]*$/;
//					var roleValue = role.target[0].value.replace(/^\s+|\s+$/g,"");//拿到值后去前后空格
					if(!reg.test(roleValue)){
						$.messager.alert("提示信息","输入的角色值必须为小写字母!","info");
						return false;
					} else {
						//验证角色名在数据库是不是已存在
						var roleValue = role.target[0].value.replace(/\s+/g,"");//拿到值后去所有空格
						$.ajax({
							type:'POST',
							url:'/roleManager/changeRole',
							data:{
								role:roleValue,
							},
							dataType:'json',
							success:function(r){
								if(r.state == "ok"){
									$('#role_datagrid').datagrid('endEdit', editing);
									editing = null;
									isFinish = true;
									$('#role_datagrid').datagrid('reload');
								}
								if(r.state == "error"){
									$.messager.alert('提示信息',r.message,"info");
									return false;
								}
							},
							error: function(r){
								$.messager.alert('警告',r.message,"error");
								return false;
							}
						});
					}
				}
			},
			error: function(result){
				$.messager.alert('警告',result.message,"error");
				return false;
			}
		});
	}
}

/**
 * 取消操作(不保存)
 */
function cancelRole(){
	$('#role_datagrid').datagrid('rejectChanges');
	editing = null;
	isFinish = true;
}

