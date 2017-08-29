function sheetMark(id) {
	var editing = null; //判断用户是否处于编辑状态
	var flag;	  //判断新增和修改方法
	$('#sheet_mark_datagrid').datagrid({
		url: "/sheetMark/queryMark?sheetId="+id,
		fit: true,
		striped: true,
		fitColumns: true,
		nowrap: true,
		rownumbers: true,
		singleSelect: true,
		onLoadSuccess: function(date){
			$(this).datagrid('doCellTip',{
				onlyShowInterrupt: false,     //是否只有在文字被截断时才显示tip，默认值为false  无效           
                position: 'bottom',   //tip的位置，可以为top,botom,right,left
                cls: { 'background-color': '#D1EEEE' },  //tip的样式
                delay: 500   //tip 响应时间
			});
			$("#sheet_mark").show().dialog({
				title : '备注',
				width : 700,
				height : 400,
				closed : false,
				closable : true,
				cache : false,
				modal : true,
				buttons : [{
					iconCls : 'icon-ok',
					text : '确定',
					handler : function() {
					}
				},{
					iconCls : 'icon-cancel',
					text : '取消',
					handler : function() {
					}
				}]
			});
		},
		columns:[[{
			checkbox : true,
			field: 'id',
			title: 'id',
			width: 50
		},{
			field: 'sdate',
			title: '时间',
			width: 50
		},{
			field: 'sendName',
			title: '备注人',
			width: 20
		},{
			field: 'mark',
			title: '备注',
			width: 100,
			editor:{
				type:'validatebox' ,
				options:{
					required:true ,
					missingMessage:'必填项!'
				}
			}
		},{
			field: 'mopt',
			title: '操作',
			width: 35,
			formatter: function (value, row, index) {
				var userId;
				var userName = null;
				 $.ajax({
			         type: "post",
			         url:"/sheetManager/getUser",
			         async:false, 
			         resultType: "User",
			         success: function(result){
			        	userId = result.id;
			        	userName = result.name;
			         }
			     });
				 if(row.sendName == userName){
				return "<button id='bnt"+row.id+"' value= 'edit' onclick=\"javascript: var umark = updateMark("+index+")\">修改</button>"+
						   "<button onclick=\"javascript: delMark("+row.id+")\">删除</button>";
				 }
			}
		}]],
		toolbar : [{
   			text : '新增备注',
   			iconCls : 'icon-add',
   			handler : function() {
   					if(editing == null){
   						flag = 'add';
   						$('#sheet_mark_datagrid').datagrid('unselectAll');//1 先取消所有的选中状态
   						$('#sheet_mark_datagrid').datagrid('appendRow',{description:''});//2追加一行
   						editing = $('#sheet_mark_datagrid').datagrid('getRows').length -1;//3获取当前页的行号.
   						$('#sheet_mark_datagrid').datagrid('beginEdit', editing);//4开启编辑状态
   					} else {
   						editing = null;
   						$.messager.alert('提示信息','由于上次操作未完成，现已将数据初始化。请再次点击', 'info');
   					}
   			}
		},'-',{
   			text : '保存操作',
   			iconCls : 'icon-save',
   			handler : function() {
   			var selMark = $("#sheet_mark_datagrid").datagrid('getSelected');
   			var start = selMark.mark;
   			 $('#sheet_mark_datagrid').datagrid('acceptChanges');
   			var end = selMark.mark;
   			if(end.length()>=1500){
   				$.messager.alert('提示信息',"备注超出1500字！","info");
   			}else{
   				if(start !=end){
   					flag="edit";
   				}
   			}
				if(flag != null && flag != undefined){
					var mark;
   					var markValue;
					var userId;
					var url;
					 $.ajax({
				         type: "post",
				         url:"/sheetManager/getUser",
				         async:false, 
				         resultType: "User",
				         success: function(result){
				        	userId = result.id;
				         }
				     });
					if(flag == 'add'){
						url = '/sheetMark/addMark';
						mark= $('#sheet_mark_datagrid').datagrid('getEditor',{index:editing,field:'mark'});
						markValue= mark.target[0].value;
					} else {
						mark=$("#sheet_mark_datagrid").datagrid('getSelected');
						markValue=mark.mark;
						url = '/sheetMark/updateMark?markId='+mark.id;
					}
					if(markValue.length()>=1500){
						$.messager.alert('提示信息',"备注超出1500字！","info");
						}else{
					$.ajax({
						type:'POST',
						url:url,
						data:{
							mark : markValue,
							sendId : userId,
							sheetId : id
						},
						dataType:'json',
						success:function(r){
								$('#sheet_mark_datagrid').datagrid('endEdit', editing);
								editing = null;
								isFinish = true;
								$('#sheet_mark_datagrid').datagrid('reload');
						},
						error: function(result){
							$('#sheet_mark_datagrid').datagrid('endEdit', editing);
							editing = null;
							isFinish = true;
							$('#sheet_mark_datagrid').datagrid('reload');
						}
					});	
					}
				} else {
					$.messager.alert('提示信息',"请先执行操作！","info");
				}
   			}
		},'-',{
   			text : '取消操作',
   			iconCls : 'icon-back',
   			handler : function() {
   				flag = null;
   				$('#sheet_mark_datagrid').datagrid('rejectChanges');
   				editing = null;
   				isFinish = true;
   			}
		}]
	});
}


/**
 * 修改备注
 */
function updateMark(index) {
	// 根据行记录对象获取该行的索引位置
	editing = $('#sheet_mark_datagrid').datagrid('getRowIndex', index);
	// 开启编辑状态
	$('#sheet_mark_datagrid').datagrid('beginEdit', index);
}

/**
 * 删除备注
 */
function delMark(markId){
			$.messager.confirm('提示信息' , '确认删除?' , function(r){
				if(r){
					$.ajax({
						type : 'POST',
						url : "/sheetMark/deleteMark?markId="+markId,
						dataType: 'json',
						success: function(result){
							if(result.returnBean.state == "ok"){
								$('#sheet_mark_datagrid').datagrid('reload');
								$.messager.alert('提示信息', '删除备注成功!', 'info');
							} else {
								$.messager.alert('提示信息', '删除备注失败!', 'info');
							}
						},
					error:function(result){
						$('#sheet_mark_datagrid').datagrid('reload');
					}
					});
				} else {
					 return ;
				}
			});
}
