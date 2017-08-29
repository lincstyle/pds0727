/**
 *导出联络单 
 */
function expSheet(id){
	location.href='/sheetManager/expSheet?id='+id;
}

/**
 * 菜单
 */
function sheet_menu(){
	$('#sheet_menu').menu({ 
		onClick:function(item){
			var userId = null;
			 $.ajax({
		         type: "post",
		         url:"/sheetManager/getUser",
		         async:false, 
		         resultType: "User",
		         success: function(result){
		        	userId = result.id;
		         }
		     });
			 if(item.name=="all"){
				$('#sheet_datagrid').datagrid('load',{
			 	});
			 }else if(item.name=="mySheet"){
				$('#sheet_datagrid').datagrid('load',{
					userId : userId
				}); 
			 }else if(item.name=="myCheck"){
				$('#sheet_datagrid').datagrid('load',{
					 handleState : 0
				}); 
			 }else if(item.name=="myManage"){
				$('#sheet_datagrid').datagrid('load',{
					 manageId : userId,
					 handleState : 1
				}); 
			 }else if(item.name=="myHandle"){
				$('#sheet_datagrid').datagrid('load',{
					 handleId : userId,
					 handleState : 2
				}); 
			 }else if(item.name=="myCompleted"){
				$('#sheet_datagrid').datagrid('load',{
					 userId : userId,
					 handleState : 5
				}); 
			 }else{
			 }
		} 
	}); 
	$('#sheet_menu').menu('show', {
		  left: 180,
		  top: 60
	});
}

/**
 * 联络单详细
 */
function qallWorksheet(id){
	$('#no_qall').html("");
  	$('#sign_name_qall').html("");
  	$('#send_date_qall').html("");
  	$('#send_dept_qall').html("");
  	$('#send_name_qall').html("");
  	$('#handle_dept_qall').html("");
  	$('#notify_dept_qall').html("");
  	$('#send_theme_qall').html("");
  	$('#end_date_qall').html("");
  	$('#send_type_qall').html("");
  	$('#send_text_qall').val("");
  	$('#manage_name_qall').html("");
  	$('#handle_name_qall').html("");
  	$('#handle_text_qall').val("");
  	$('#handle_state_qall').html("");
	$.ajax({
		type: "post",
        url:"/sheetManager/querySheet",
        data: {"id": id },
        resultType: "Sheet",
        async:false, 
        success: function(result){
        	$('#no_qall').html(result.no);
          	$('#sign_name_qall').html(result.signName);
          	$('#send_date_qall').html(result.sendDate);
          	$('#send_dept_qall').html(result.sendDept);
          	$('#send_name_qall').html(result.sendName);
          	$('#handle_dept_qall').html(result.handleDept);
          	$('#notify_dept_qall').html(result.notifyDept);
          	$('#send_theme_qall').html(result.sendTheme);
          	$('#end_date_qall').html(result.endDate);
          	$('#send_type_qall').html(findSType(result.sendType));
          	$('#send_text_qall').val(result.sendText);
          	$('#manage_name_qall').html(result.manageName);
          	$('#handle_name_qall').html(result.handleName);
          	$(":radio[name=import_lv_qall]").each(function(){
    		 	if($(this).val() !=result.importLv){
    		 		$(this).attr("disabled",true);
    		 	}else{
    		 		$(this).attr("disabled",false);
    		 		$(this).attr("checked",true);
    		 	}
          	}); 
          	$(":radio[name=handle_lv_qall]").each(function(){
          		if($(this).val() !=result.handleLv){
          			$(this).attr("disabled",true);
    		 	}else{
    		 		$(this).attr("disabled",false);
    		 		$(this).attr("checked",true);
    		 	}
          	}); 
          	$('#handle_text_qall').val(result.handleText);
          	$('#handle_state_qall').html(findHState(result.handleState));
        }
	});
        
	$("#qall_sheet_dialog").show().dialog({
		title: '联络单详细',    
		width: 600,
		height : 650,
		closed: false,
		closable: true,
		cache: false,    
		modal: true,
		onClose: function(){
			$(':radio[name=import_lv_qall]').removeAttr("checked");
			$(':radio[name=handle_lv_qall]').removeAttr("checked");
			$("#qall_sheet_form").form('reset');
			$("#sheet_datagrid").datagrid('reload');
		},
		buttons : [{
			iconCls : 'icon-search',
			text : '查看备注',
			handler : function() {
				sheetMark(id);
			}
		},{
			iconCls : 'icon-back',
			text : '返回列表',
			handler : function() {
				$("#qall_sheet_dialog").dialog('close');
			}
		}]
	});
}

/**
 * 查找联络单
 */
function findWorksheet(){
	$('#find_sendSDate').datebox();
    $('#find_sendtEDate').datebox();
    $('#find_endSDate').datebox();
    $('#find_endtEDate').datebox();
    showDeptUser($('#find_send_dept'),$('#find_send_name'));
    showDeptUser($('#find_handle_dept'), $('#find_handle_name'));
   
    //页面初始化
    $('#find_sendSDate').val("");
    $('#find_sendtEDate').val("");
    $('#find_endSDate').val("");
    $('#find_endtEDate').val("");
    $('#find_handleState').val("");
    $('#find_send_name').combobox({
  		url:'/sheetManager/queryDeptUser',
  		width : '80',
  		valueField : 'id',
  		textField : 'text',
  		onLoadSuccess : function(data) {
  			$('#find_send_name').combobox('setValue', "-1");
  		}
  	});
    $('#find_handle_name').combobox({
   		url:'/sheetManager/queryDeptUser',
   		width : '80',
   		valueField : 'id',
   		textField : 'text',
   		onLoadSuccess : function(data) {
   			$('#find_handle_name').combobox('setValue', "-1");
   		}
   	});
   $('#find_sheet_dialog').dialog({
         title: '查询联络单',
         width: 500,
         height: 300,
         closable: false,
         cache: false,
         modal: true,
         buttons: [{
             iconCls: 'icon-ok',
             text: '确定',
             handler: function () {
                 if (dateCompare($('#find_sendSDate').datebox("getValue"), $('#find_sendtEDate').datebox("getValue"))) {
                     $.messager.alert('提示', "提出开始时间不能大于结束时间", 'info'); // 弹出提示信息
                     return false;
                 };
                 if (dateCompare($('#find_endSDate').datebox("getValue"), $('#find_endtEDate').datebox("getValue"))) {
                     $.messager.alert('提示', "要求开始时间不能大于结束时间", 'info'); // 弹出提示信息
                     return false;
                 };
                 $('#sheet_datagrid').datagrid('load', {
                     sendDept: $('#find_send_dept').combobox("getText").replace("--请选择",""),
                     handleDeptId: $('#find_handle_dept').combobox("getValue").replace("--请选择",""),
                     sendName:$('#find_send_name').combobox("getText").replace("--请选择",""),
                     handleId:$('#find_handle_name').combobox("getValue").replace(-1,""),
                     "sendDate_start": $('#find_sendSDate').datebox("getValue"),
                     "sendDate_end": $('#find_sendtEDate').datebox("getValue"),
                     "endDate_start": $('#find_endSDate').datebox("getValue"),
                     "endDate_end": $('#find_endtEDate').datebox("getValue"),
                     handleState:  $('#find_handleState').val()
                 });
                 $('#find_sheet_dialog').dialog('close');
             }
         }, {
             iconCls: 'icon-cancel',
             text: '取消',
             handler: function () {
            	 $('#find_sheet_dialog').dialog('close');
             }
         }]
     });
}


/**
 * 新建联络单
 */
function addWorksheet(){
	var date = new Date();
	$('#send_date_add').html(myformatter(date));
	//设置要求时间当天之前日期不可选
    $('#end_date_add').datebox('calendar').calendar({
        validator: function(date){
            var now = new Date();
            var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return d1<=date;
        }
    });
    //获取处理部门下拉框
    $('#handle_dept_add').combobox({
		url:'/departmentManager/getDepartmentList?selectId='+null, 
		width : 439,
		valueField:'id',    
		textField:'text',
		editable : false,
		onLoadSuccess : function(data) {
			 $('#handle_dept_add').combobox('setValue', "--请选择");
		}
	});
    //获取抄送部门下拉框
    $('#notify_dept_add').combobox({    
		url:'/departmentManager/getDepartmentList?selectId='+null, 
		width : 439,
		valueField:'id',    
		textField:'text',
		editable : false,
		onLoadSuccess : function(data) {
			 $('#notify_dept_add').combobox('setValue', "--请选择");
		}
	});
    //获取联络单编号
    $.ajax({
    	type : 'POST',
		url : '/sheetManager/getNo',
		dateType : 'String',
		success : function(result){
			 $('#no_add').html(result);
		}
    });
    $.ajax({
        type: "post",
        url:"/sheetManager/getUser",
        resultType: "User",
        success: function(result){
        	//提出部门与提出人、签发二级联动
       	 	showDeptUser2($('#send_dept_add'),$('#send_name_add'),$('#sign_name_add'));
       	 	$('#sign_name_add').combobox({
     			url:'/sheetManager/queryDeptUser?departmentId='+result.departmentId,
     			width : '80',
     			valueField : 'id',
     			textField : 'text',
     			onLoadSuccess : function(data) {
     				$('#sign_name_add').combobox('setValue', "-1");
     			}
     		});
       	 	//初始化提出部门为当前登录用户部门
       		$('#send_dept_add').combobox({    
     			url:'/departmentManager/getDepartmentList?selectId='+null,    
     			valueField:'id',    
     			textField:'text',
     			onLoadSuccess : function(data) {
     			 	$('#send_dept_add').combobox('setValue', result.departmentId);
     			}
     		});
       		 //初始化提出人为当前登录用户
       	 	$("#send_name_add").combobox({
     			url:'/sheetManager/queryDeptUser?departmentId='+result.departmentId,
     			width : '80',	
     			valueField : 'id',
     			textField : 'text',
     			onLoadSuccess : function(data) {
     				 $("#send_name_add").combobox('setValue',result.id);
     			}
     		});
        }
    });
    $(':radio[name=import_lv_add][value=1]').attr('checked','true');
  	$(':radio[name=handle_lv_add][value=1]').attr('checked','true');
	$('#add_sheet_dialog').show().dialog({
		title: '新建联络单',    
		width: 600,
		closed: false,
		closable: true,
		cache: false,    
		modal: true,
		onClose : function(){
			$("#sheet_datagrid").datagrid('reload');
			$("#add_sheet_form").form('reset');
		},
		buttons : [{
			iconCls : 'icon-ok',
			text : '确定',
			handler : function() {
				var sendDeptId;
				var sendId;
				var signId;
				var sendDept =  $("#send_dept_add").combobox('getText').replace("--请选择","");
				var sendName=$('#send_name_add').combobox('getText').replace("--请选择","");
			 	var signName =$('#sign_name_add').combobox('getText').replace("--请选择","");
			 	//判断提出部门是否在数据库中存在，若不存在部门ID设置为-1
			 	if(sendDept != $("#send_dept_add").combobox('getValue')){
					sendDeptId = $("#send_dept_add").combobox('getValue');
			 	}else{
			 		sendDeptId = -1;
			 	};
			 	if(sendName != $('#send_name_add').combobox('getValue')){
			 		sendId=$('#send_name_add').combobox('getValue');
			 	}else{
			 		sendId=-1;
			 	};
				if(signName !=$('#sign_name_add').combobox('getValue')){
					signId=$('#sign_name_add').combobox('getValue');
				}else{
					signId = -1;
				};
			    var no = $('#no_add').html();
				var sendDate = myparser($('#send_date_add').html());
				var sendType = $('#send_type_add').val();
				var sendTheme=$('#send_theme_add').val();
				var sendText=$('#send_text_add').val();
				var importLv = $("input[name='import_lv_add']:checked").val();
				var handleLv = $("input[name='handle_lv_add']:checked").val();
				var endDate=$('#end_date_add').datebox('getValue');
				var handleDeptId=$('#handle_dept_add').combobox("getValue").replace("--请选择","");
				var notifyDeptId=$('#notify_dept_add').combobox("getValue").replace("--请选择","");
				var handleState=0;
				var message ="";
				if(no ==null || no ==""){
					message = message + "编号   	";
				}
				if(sendDate ==null || sendDate ==""){
					message = message + "日期    	";
				}
				if(sendDept ==null || sendDept ==""){
					message = message + "提出部门    	";
				}
				if(sendName ==null || sendName ==""){
					message = message + "提出人    	";
				}
				if(handleDeptId ==null || handleDeptId ==""){
					message = message + "处理部门    ";
				}
				if(sendTheme ==null || sendTheme ==""){
					message = message + "发文主题    ";
				}
				if(endDate ==null || endDate ==""){
					message = message + "时间要求    ";
				}
				if(sendType ==null || sendType ==""){
					message = message + "项目类型    ";
				}
				if(sendText ==null || sendText ==""){
					message = message + "问题描述    ";
				}
				if(importLv ==null || importLv ==""){
					message = message + "重要程度    ";
				}
				if(handleLv ==null || handleLv ==""){
					message = message + "处理缓急程度    ";
				}
				if(message !=null && message !=""){
					message = message+"不能为空 ! ";
				}
				if(message !=null && message !="" ){
					$.messager.alert('提示',message, 'info'); 
				}else if(sendText.length>=1500){
					$.messager.alert('提示', "问题描述不能超过1500字", 'info');
				}else{
					$.ajax({
						type : 'POST',
						url : '/sheetManager/addSheet',
						data:{
							no : no,
							sendDeptId : sendDeptId,
							sendDept : sendDept,
							sendId : sendId,
							sendName : sendName,
							signId : signId,
							signName : signName,
							sendDate : sendDate,
							sendType : sendType,
							sendTheme : sendTheme,
							sendText : sendText,
							importLv : importLv,
							handleLv : handleLv,
							endDate : endDate,
							handleDeptId : handleDeptId,
							notifyDeptId : notifyDeptId,
							handleState : handleState
						},
						dataType: 'json',
						success: function(r){
							if(r.state == "ok"){
								$("#add_sheet_dialog").dialog('close');
								$("#sheet_datagrid").datagrid('reload');
								$("#add_sheet_form").form('reset');
							}
							if(r.state == "error"){
								$.messager.alert('提示信息',r.message,"info");
								return false;
							}
						},
						error:function(result){
							$.messager.alert('提示信息',result.message,"info");
							return false;
						}
					});
				}
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				$("#add_sheet_dialog").dialog('close');
				$("#sheet_datagrid").datagrid('reload');
				$("#add_sheet_form").form('reset');
			}	
		}]
	});
};

/**
 * 修改新建联络单
 */
function reviseNewSheet(id){
	var infoStart;
	$.ajax({
        type: "post",
        url:"/sheetManager/querySheet",
        data:{'id':id},
        resultType: "Sheet",
        success: function(result){
        	infoStart = result;
        	showDeptUser2($('#send_dept_add'),$('#send_name_add'),$('#sign_name_add'));
       		$('#send_dept_add').combobox({    
     			url:'/departmentManager/getDepartmentList?selectId='+null,    
     			valueField:'id',    
     			textField:'text',
     			onLoadSuccess : function(data) {
     				if(result.sendDeptId !=-1){
     					$('#send_dept_add').combobox('setValue', result.sendDeptId);
     				}else{
     					$('#send_dept_add').combobox('setValue', result.sendDept);
     				}
     			}
       		});
       		 
   			$("#send_name_add").combobox({
      			url:'/sheetManager/queryDeptUser?departmentId='+result.sendDeptId,
      			width : '80',	
      			valueField : 'id',
      			textField : 'text',
      			onLoadSuccess : function(data) {
      				if(result.sendId != -1){
      					$('#send_name_add').combobox('setValue', result.sendId);
      				}else{
      					$('#send_name_add').combobox('setValue', result.sendName);
      				}
        		}
        	});
 			
 			$("#sign_name_add").combobox({
 	         		url:'/sheetManager/queryDeptUser?departmentId='+result.sendDeptId,
 	         		width : '80',
 	         		valueField : 'id',
 	         		textField : 'text',
 	         		onLoadSuccess : function(data) {
 	         			if(result.signId !=-1){
 	         				$('#sign_name_add').combobox('setValue', result.signId);	
 	         			}else{
 	         				$('#sign_name_add').combobox('setValue', result.signName);
 	         			}
 	         		}
 	         });
 			
 			
        	$("#no_add").html(result.no);
        	$('#send_date_add').html(result.sendDate);
        	$('#end_date_add').datebox('setValue',result.endDate).datebox('calendar').calendar({
                validator: function(date){
                    var now = new Date();
                    var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return d1<=date;
                }
            });
        	$("#send_dept_add").val(result.sendDept);
           	$("#send_dept_id_add").val(result.sendDeptId);
       		$("#send_name_add").val(result.sendName);
       		$("#send_id_add").val(result.sendId);
       		$('#handle_dept_add').combobox({    
       			url:'/departmentManager/getDepartmentList?selectId='+null,   
       			width : 439,
       			valueField:'id',    
       			textField:'text',
       			editable : false,
       			onLoadSuccess : function(data) {
       				 $('#handle_dept_add').combobox('setValue', result.handleDeptId);
       			}
       		});
       	 	$('#notify_dept_add').combobox({    
     			url:'/departmentManager/getDepartmentList?selectId='+null, 
     			width : 439,
     			valueField:'id',    
     			textField:'text',
     			editable : false,
     			onLoadSuccess : function(data) {
     				 $('#notify_dept_add').combobox('setValue', result.notifyDeptId);
     			}
     		});
       	 	$("#send_theme_add").val(result.sendTheme);
       	 	$("#send_type_add").val(result.sendType);
       	 	$("#send_text_add").val(result.sendText);
       	 	$(':radio[name=import_lv_add][value='+result.importLv+']').attr('checked','true');
       	 	$(':radio[name=handle_lv_add][value='+result.handleLv+']').attr('checked','true');
        }
    });
	$('#add_sheet_dialog').show().dialog({
		title: '新建联络单',    
		width: 600,
		closed: false,
		closable: true,
		cache: false,    
		modal: true,
		onClose: function(){
			$(':radio[name=import_lv_add]').removeAttr("checked");
			$(':radio[name=handle_lv_add]').removeAttr("checked");
			$("#sheet_datagrid").datagrid('reload');
			$("#add_sheet_form").form('reset');
		},
		buttons : [{
			iconCls : 'icon-ok',
			text : '确定',
			handler : function(){
				//判断联络单信息是否已修改
				var isSame=true;
				$.ajax({
			        type: "post",
			        url:"/sheetManager/querySheet",
			        data:{'id':id},
			        async:false, 
			        resultType: "Sheet",
			        success: function(result){
			        	isSame = equalObj(infoStart,result);
			        }
				}); 
				if(!isSame){
					$.messager.alert("提示信息","联络单信息已更新，请刷新后操作！","info");
					return false;
				}
				
				var sendDeptId;
				var sendId;
				var signId;
				var sendDept =  $("#send_dept_add").combobox('getText').replace("--请选择","");
				var sendName=$('#send_name_add').combobox('getText').replace("--请选择","");
			 	var signName =$('#sign_name_add').combobox('getText').replace("--请选择","");
			 	if(sendDept != $("#send_dept_add").combobox('getValue')){
					sendDeptId = $("#send_dept_add").combobox('getValue');
			 	}else{
			 		sendDeptId = -1;
			 	};
			 	if(sendName != $('#send_name_add').combobox('getValue')){
			 		sendId=$('#send_name_add').combobox('getValue');
			 	}else{
			 		sendId= -1;
			 	};
				if(signName !=$('#sign_name_add').combobox('getValue')){
					signId=$('#sign_name_add').combobox('getValue');
				}else{
					signId = -1;
				};
				var sendDate = myparser($('#send_date_add').html());
				var sendType = $('#send_type_add').val();
				var sendTheme=$('#send_theme_add').val();
				var sendText=$('#send_text_add').val();
				var importLv = $("input[name='import_lv_add']:checked").val();
				var handleLv = $("input[name='handle_lv_add']:checked").val();
				var endDate=$('#end_date_add').datebox('getValue');
				var handleDeptId=$('#handle_dept_add').combobox("getValue").replace("--请选择","");
				var notifyDeptId=$('#notify_dept_add').combobox("getValue").replace("--请选择","");
				var handleState=0;
				var message ="";
				if(sendDept ==null || sendDept ==""){
					message = message + "提出部门  ";
				}
				if(sendName ==null || sendName ==""){
					message = message + "提出人  ";
				}
				if(handleDeptId ==null || handleDeptId ==""){
					message = message + "处理部门  ";
				}
				if(sendTheme ==null || sendTheme ==""){
					message = message + "发文主题  ";
				}
				if(endDate ==null || endDate ==""){
					message = message + "时间要求  ";
				}
				if(sendType ==null || sendType ==""){
					message = message + "项目类型  ";
				}
				if(sendText ==null || sendText ==""){
					message = message + "问题描述  ";
				}
				if(importLv ==null || importLv ==""){
					message = message + "重要程度  ";
				}
				if(handleLv ==null || handleLv ==""){
					message = message + "处理缓急程度  ";
				}
				if(message !=null && message !=""){
					message = message+"不能为空 ! ";
				}
				if(message !=null && message !=""){
					$.messager.alert('提示', message, 'info'); 
				}else if(sendText.length>=1500){
					$.messager.alert('提示', "问题描述不能超过1500字", 'info');
				}else{
					$.ajax({
						type : 'POST',
						url : '/sheetManager/updateSheet',
						data:{
							id : id,
							sendDeptId : sendDeptId,
							sendDept : sendDept,
							sendId : sendId,
							sendName : sendName,
							signId : signId,
							signName : signName,
							sendDate : sendDate,
							sendType : sendType,
							sendTheme : sendTheme,
							sendText : sendText,
							importLv : importLv,
							handleLv : handleLv,
							endDate : endDate,
							handleDeptId : handleDeptId,
							notifyDeptId : notifyDeptId
						},
						dataType: 'json',
						success: function(r){
							if(r.state == "ok"){
								$(':radio[name=import_lv_add]').removeAttr("checked");
								$(':radio[name=handle_lv_add]').removeAttr("checked");
								$("#add_sheet_dialog").dialog('close');
								$("#sheet_datagrid").datagrid('reload');
								$("#add_sheet_form").form('reset');
							}
							if(r.state == "error"){
								$.messager.alert('提示信息',r.message,"info");
								return false;
							}
						},
						error:function(result){
							$.messager.alert('提示信息',result.message,"info");
							return false;
						}
					});
				}
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				$(':radio[name=import_lv_add]').removeAttr("checked");
				$(':radio[name=handle_lv_add]').removeAttr("checked");
				$("#add_sheet_dialog").dialog('close');
				$("#sheet_datagrid").datagrid('reload');
				$("#add_sheet_form").form('reset');
			}
		}]
	});
}

/**
 * 审核联络单
 */
function checkWorksheet(id){
	$('#no_check').html("");
	 $('#sign_name_check').html("");
	 $('#send_date_check').html("");
	 $('#send_dept_check').html("");
	 $('#send_name_check').html("");
	 $('#handle_dept_check').html("");
	 $('#notify_dept_check').html("");
	 $('#send_theme_check').html("");
	 $('#end_date_check').html("");
	 $('#send_type_check').html("");
	 $('#send_text_check').val("");
	var infoStart;
	$.ajax({
         type: "post",
         url:"/sheetManager/querySheet",
         data: {"id": id },
         resultType: "Sheet",
         success: function(result){
        	 infoStart = result;
        	 $('#no_check').html(result.no);
        	 $('#sign_name_check').html(result.signName);
        	 $('#send_date_check').html(result.sendDate);
        	 $('#send_dept_check').html(result.sendDept);
        	 $('#send_name_check').html(result.sendName);
        	 $('#handle_dept_check').html(result.handleDept);
        	 $('#notify_dept_check').html(result.notifyDept);
        	 $('#send_theme_check').html(result.sendTheme);
        	 $('#end_date_check').html(result.endDate);
        	 $('#send_type_check').html(findSType(result.sendType));
        	 $('#send_text_check').val(result.sendText);
        	 $(":radio[name=import_lv_check]").each(function(){
        		 	if($(this).val() !=result.importLv){
              			$(this).attr("disabled",true);
        		 	}else{
        		 		$(this).attr("disabled",false);
        		 		$(this).attr("checked",true);
        		 	}  
        	 }); 
        	 $(":radio[name=handle_lv_check]").each(function(){
        		 if($(this).val() !=result.handleLv){
           			$(this).attr("disabled",true);
     		 	}else{
     		 		$(this).attr("disabled",false);
     		 		$(this).attr("checked",true);
     		 	}  
     		}); 
        	 var id =result.manageId;
        	 if(id==null){
        		 id = -1;
        	 }
        	 showUser($('#manage_name_check'),result.handleDeptId,id);
        }
    });
	$.ajax({
             type: "post",
             url:"/sheetManager/getUser",
             resultType: "User",
             success: function(result){
            	$("#check_name_check").html(result.name);
            	$("#check_id_check").val(result.id);
             }
    });
	$("#check_sheet_dialog").show().dialog({
		title : '联络单审核',
		width : 600,
		closed : false,
		closable : true,
		cache : false,
		modal : true,
		onClose: function(){
			$(':radio[name=import_lv_check]').removeAttr("checked");
			$(':radio[name=handle_lv_check]').removeAttr("checked");
			$("#check_sheet_form").form('reset');
			$("#sheet_datagrid").datagrid('reload');
		},
		buttons : [{
			iconCls : 'icon-ok',
			text : '确定',
			handler : function() {
				//判断联络单信息是否已经改变
				var isSame=true;
				$.ajax({
			        type: "post",
			        url:"/sheetManager/querySheet",
			        data:{'id':id},
			        async:false, 
			        resultType: "Sheet",
			        success: function(result){
			        	isSame = equalObj(infoStart,result);
			        }
				}); 
				if(!isSame){
					$.messager.alert("提示信息","联络单信息已更新，请刷新后操作！","info");
					return false;
				}
				
				var checkId = $('#check_id_check').val();	
				var manageId = $('#manage_name_check').combobox('getValue');
				var handleState = 1;
				var message ="";
				if(checkId ==-1){
					message = message + "审核人  ";
				}
				if(manageId ==-1){
					message = message + "负责人  ";
				}
				if(message !=null && message !=""){
					message = message +"不能为空 ! ";
				}
				if(message !=null && message !=""){
					$.messager.alert('提示', message, 'info'); 
				}else{
					$.ajax({
						type : 'POST',
						url : '/sheetManager/updateSheet',
						data : {
							id : id,
							checkId : checkId,
							manageId : manageId,
							handleState : handleState
						},
						dataType : 'json',
						success: function(r){
							if(r.state == "ok"){
								$(':radio[name=import_lv_check]').removeAttr("checked");
								$(':radio[name=handle_lv_check]').removeAttr("checked");
								$("#check_sheet_dialog").dialog('close');
								$("#sheet_datagrid").datagrid('reload');
								$("#check_sheet_form").form('reset');
							}
							if(r.state == "error"){
								$.messager.alert('提示信息',r.message,"info");
								return false;
							}
						},
						error:function(result){
							$.messager.alert('提示信息',result.message,"info");
							return false;
						}
					});
				}
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
			    $(':radio[name=import_lv_check]').removeAttr("checked");
				$(':radio[name=handle_lv_check]').removeAttr("checked");
				$("#check_sheet_dialog").dialog('close');
				$("#sheet_datagrid").datagrid('reload');
				$("#check_sheet_form").form('reset');
			}
		}]
	});
};


/**
 * 分配联络单
 */
function manageWorksheet(id) {
	$('#no_manage').html("");
   	$('#sign_name_manage').html("");
   	$('#send_date_manage').html("");
   	$('#send_dept_manage').html("");
   	$('#send_name_manage').html("");
   	$('#handle_dept_manage').html("");
   	$('#notify_dept_manage').html("");
   	$('#send_theme_manage').html("");
   	$('#end_date_manage').html("");
   	$('#send_type_manage').html("");
   	$('#send_text_manage').val("");
	var infoStart;
	 $.ajax({
        type: "post",
        url:"/sheetManager/getUser",
        async:false, 
        resultType: "User",
        success: function(result){
       	$('#manage_id_manage').val(result.id);
       	$('#manage_name_manage').html(result.name);
        }
    });
	$.ajax({
        type: "post",
        url:"/sheetManager/querySheet",
        data: {"id": id },
        resultType: "Sheet",
        success: function(result){
	        infoStart = result;	
	       	$('#no_manage').html(result.no);
	       	$('#sign_name_manage').html(result.signName);
	       	$('#send_date_manage').html(result.sendDate);
	       	$('#send_dept_manage').html(result.sendDept);
	       	$('#send_name_manage').html(result.sendName);
	       	$('#handle_dept_manage').html(result.handleDept);
	       	$('#notify_dept_manage').html(result.notifyDept);
	       	$('#send_theme_manage').html(result.sendTheme);
	       	$('#end_date_manage').html(result.endDate);
	       	$('#send_type_manage').html(findSType(result.sendType));
	       	$('#send_text_manage').val(result.sendText);
	       	$(":radio[name=import_lv_manage]").each(function(){
	       		if($(this).val() !=result.importLv){
           			$(this).attr("disabled",true);
     		 	}else{
     		 		$(this).attr("disabled",false);
     		 		$(this).attr("checked",true);
     		 	}
    		}); 
	       	$(":radio[name=handle_lv_manage]").each(function(){  
	       		if($(this).val() !=result.handleLv){
           			$(this).attr("disabled",true);
     		 	}else{
     		 		$(this).attr("disabled",false);
     		 		$(this).attr("checked",true);
     		 	}
	       	}); 
	       	var id =result.handleId;
	       	if(id==null){
       		 id = -1;
	       	}
	       	showUser($('#handle_name_manage'),result.handleDeptId,id);
        }
        });
		 $("#manage_sheet_dialog").show().dialog({
		title : '分配联络单',
		width : 600,
		closed : false,
		closable : true,
		cache : false,
		modal : true,
		onClose: function(){
			$(':radio[name=import_lv_manage]').removeAttr("checked");
			$(':radio[name=handle_lv_manage]').removeAttr("checked");
			$("#sheet_datagrid").datagrid('reload');
			$("#manage_sheet_form").form('reset');
		},
		buttons : [{
			iconCls : 'icon-ok',
			text : '确定',
			handler : function() {
				//判断联络单信息是否已经改变
				var isSame=true;
				$.ajax({
			        type: "post",
			        url:"/sheetManager/querySheet",
			        data:{'id':id},
			        async:false, 
			        resultType: "Sheet",
			        success: function(result){
			        	isSame = equalObj(infoStart,result);
			        }
				}); 
				if(!isSame){
					$.messager.alert("提示信息","联络单信息已更新，请刷新后操作！","info");
					return false;
				}
				
				var manageId =$('#manage_id_manage').val();
				var handleId = $('#handle_name_manage').combobox('getValue');
				var handleState = 2;
				var message ="";
				if(handleId ==-1){
					message = message + "执行人  ";
				}
				if(message !=null && message !=""){
					message = message+"不能为空 ! ";
				}
				if(message !=null && message !=""){
					$.messager.alert('提示', message, 'info'); 
				}else{
					$.ajax({
						type : 'POST',
						url : '/sheetManager/updateSheet',
						data : {
							id : id,
							manageId : manageId,
							handleId : handleId,
							handleState : handleState
						},
						dataType : 'json',
						success: function(r){
							if(r.state == "ok"){
								$(':radio[name=import_lv_manage]').removeAttr("checked");
								$(':radio[name=handle_lv_manage]').removeAttr("checked");
								$("#manage_sheet_dialog").dialog('close');
								$("#sheet_datagrid").datagrid('reload');
								$("#manage_sheet_form").form('reset');
							}
							if(r.state == "error"){
								$.messager.alert('提示信息',r.message,"info");
								return false;
							}
						},
						error:function(result){
							$.messager.alert('提示信息',result.message,"info");
							return false;
						}
					});
				}
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				$(':radio[name=import_lv_manage]').removeAttr("checked");
				$(':radio[name=handle_lv_manage]').removeAttr("checked");
				$("#manage_sheet_dialog").dialog('close');
				$("#sheet_datagrid").datagrid('reload');
				$("#manage_sheet_form").form('reset');
			}
		}]
	});
}


/**
 * 转发联络单
 */
 function 	transferWorksheet(id){
	 $('#no_transfer').html("");
	$('#sign_name_transfer').html("");
	$('#send_date_transfer').html("");
	$('#send_dept_transfer').html("");
	$('#send_name_transfer').html("");
	$('#handle_dept_transfer').html("");
	$('#notify_dept_transfer').html("");
	$('#send_theme_transfer').html("");
	$('#end_date_transfer').html("");
	$('#send_type_transfer').html("");
	$('#send_text_transfer').val("");
	$('#transfer_name_transfer').html("");
	 var infoStart;
	 $.ajax({
	        type: "post",
	        url:"/sheetManager/querySheet",
	        data: {"id": id },
	        resultType: "Sheet",
	        success: function(result){
	        	infoStart = result;
		       	$('#no_transfer').html(result.no);
		       	$('#sign_name_transfer').html(result.signName);
		       	$('#send_date_transfer').html(result.sendDate);
		       	$('#send_dept_transfer').html(result.sendDept);
		       	$('#send_name_transfer').html(result.sendName);
		       	$('#handle_dept_transfer').html(result.handleDept);
		       	$('#notify_dept_transfer').html(result.notifyDept);
		       	$('#send_theme_transfer').html(result.sendTheme);
		       	$('#end_date_transfer').html(result.endDate);
		       	$('#send_type_transfer').html(findSType(result.sendType));
		       	$('#send_text_transfer').val(result.sendText);
		       	$('#transfer_name_transfer').html(result.handleName);
		       	$(":radio[name=import_lv_transfer]").each(function(){
		       		if($(this).val() !=result.importLv){
	           			$(this).attr("disabled",true);
	     		 	}else{
	     		 		$(this).attr("disabled",false);
	     		 		$(this).attr("checked",true);
	     		 	}
	    		}); 
		       	$(":radio[name=handle_lv_transfer]").each(function(){  
		       		if($(this).val() !=result.handleLv){
	           			$(this).attr("disabled",true);
	     		 	}else{
	     		 		$(this).attr("disabled",false);
	     		 		$(this).attr("checked",true);
	     		 	}
		       	});  
		       	showUser($('#handle_name_transfer'),result.handleDeptId,-1);
	        }
	        });
	$("#transfer_sheet_dialog").show().dialog({
		title : '转发联络单',
		width : 600,
		closed : false,
		closable : true,
		cache : false,
		modal : true,
		onClose :  function() {
			$(':radio[name=import_lv_transfer]').removeAttr("checked");
			$(':radio[name=handle_lv_transfer]').removeAttr("checked");
			$("#sheet_datagrid").datagrid('reload');
			$("#transfer_sheet_form").form('reset');
		},
		buttons : [{
			iconCls : 'icon-ok',
			text : '确定',
			handler : function() {
				//判断联络单信息是否已经改变
				var isSame=true;
				$.ajax({
			        type: "post",
			        url:"/sheetManager/querySheet",
			        data:{'id':id},
			        async:false, 
			        resultType: "Sheet",
			        success: function(result){
			        	isSame = equalObj(infoStart,result);
			        }
				}); 
				if(!isSame){
					$.messager.alert("提示信息","联络单信息已更新，请刷新后操作！","info");
					return false;
				}
				
				var handleId = $('#handle_name_transfer').combobox('getValue');
				var message ="";
				if(handleId ==-1){
					message = message + "新执行人  ";
				}
				if(message !=null && message !=""){
					message = message+"不能为空 !";
				}
				if(message !=null && message !=""){
					$.messager.alert('提示', message, 'info'); 
				}else{
					$.ajax({
						type : 'post',
						url : '/sheetManager/updateSheet',
						data :{
							id :id,
							handleId : handleId
						},
						resultType : 'json',
						success : function(r){
							if(r.state == "ok"){
								$(':radio[name=import_lv_transfer]').removeAttr("checked");
								$(':radio[name=handle_lv_transfer]').removeAttr("checked");
								$("#transfer_sheet_dialog").dialog('close');
								$("#sheet_datagrid").datagrid('reload');
								$("#transfer_sheet_form").form('reset');
							}
							if(r.state == "error"){
								$.messager.alert('提示信息',r.message,"info");
								return false;
							}
						},
						error:function(result){
							$.messager.alert('提示信息',result.message,"info");
							return false;
						}
					});
				} 
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				$(':radio[name=import_lv_transfer]').removeAttr("checked");
				$(':radio[name=handle_lv_transfer]').removeAttr("checked");
				$("#transfer_sheet_dialog").dialog('close');
				$("#sheet_datagrid").datagrid('reload');
				$("#transfer_sheet_form").form('reset'); 
			}
		}]
	});
}
 

 /**
 * 处理联络单
 */
function handleWorksheet(id){
	$('#no_handle').html("");
 	$('#sign_name_handle').html("");
 	$('#send_date_handle').html("");
 	$('#send_dept_handle').html("");
 	$('#send_name_handle').html("");
 	$('#handle_dept_handle').html("");
 	$('#notify_dept_handle').html("");
 	$('#send_theme_handle').html("");
 	$('#end_date_handle').html("");
 	$('#send_type_handle').html("");
 	$('#send_text_handle').val("");
 	$('#manage_name_handle').html("");
 	$('#handle_name_handle').html("");
 	
	var infoStart;
	$.ajax({
        type: "post",
        url:"/sheetManager/getUser",
        async:false, 
        resultType: "User",
        success: function(result){
        	$('#handle_id_handle').val(result.id);
        	$('#handle_name_handle').html(result.name);
        }
    });
	$.ajax({
        type: "post",
        url:"/sheetManager/querySheet",
        data: {"id": id },
        resultType: "Sheet",
        success: function(result){
        	infoStart = result;
       	 	$('#no_handle').html(result.no);
       	 	$('#sign_name_handle').html(result.signName);
       	 	$('#send_date_handle').html(result.sendDate);
       	 	$('#send_dept_handle').html(result.sendDept);
       	 	$('#send_name_handle').html(result.sendName);
       	 	$('#handle_dept_handle').html(result.handleDept);
       	 	$('#notify_dept_handle').html(result.notifyDept);
       	 	$('#send_theme_handle').html(result.sendTheme);
       	 	$('#end_date_handle').html(result.endDate);
       	 	$('#send_type_handle').html(findSType(result.sendType));
       	 	$('#send_text_handle').val(result.sendText);
       	 	$('#manage_name_handle').html(result.manageName);
       	 	$('#handle_name_handle').html(result.handleName);
       	 	$(":radio[name=import_lv_handle]").each(function(){
	       		if($(this).val() !=result.importLv){
           			$(this).attr("disabled",true);
     		 	}else{
     		 		$(this).attr("disabled",false);
     		 		$(this).attr("checked",true);
     		 	}
       	 	}); 
	       	$(":radio[name=handle_lv_handle]").each(function(){  
	       		if($(this).val() !=result.handleLv){
           			$(this).attr("disabled",true);
     		 	}else{
     		 		$(this).attr("disabled",false);
     		 		$(this).attr("checked",true);
     		 	}
	       	});  
	       	 if(result.handleText !="" && result.handleText !=null){
	       		$('#handle_text_handle').val(result.handleText);
	       		//$('#handle_state_handle').val(result.handleState);
	       	 }
	        }
	        });
	$("#handle_sheet_dialog").show().dialog({
		title : '联络单处理',
		width : 600,
		height : 650,
		closed : false,
		closable : true,
		cache : false,
		modal : true,
		onClose: function() {
			$(':radio[name=import_lv_handle]').removeAttr("checked");
			$(':radio[name=handle_lv_handle]').removeAttr("checked");
			$("#sheet_datagrid").datagrid('reload');
			$("#handle_sheet_form").form('reset');
		},
		buttons : [{
			iconCls : 'icon-ok',
			text : '确定',
			handler : function() {
				//判断联络单信息是否已经改变
				var isSame=true;
				$.ajax({
			        type: "post",
			        url:"/sheetManager/querySheet",
			        data:{'id':id},
			        async:false, 
			        resultType: "Sheet",
			        success: function(result){
			        	isSame = equalObj(infoStart,result);
			        }
				}); 
				if(!isSame){
					$.messager.alert("提示信息","联络单信息已更新，请刷新后操作！","info");
					return false;
				}
				
				var handleText =$('#handle_text_handle').val();
				var handleState =$('#handle_state_handle').val();
				var handleId =$('#handle_id_handle').val();
				var message ="";
				if((handleText ==null || handleText =="") && handleState >=5){
					message = message + "反馈信息  ";
				}
				if(handleState ==null || handleState ==""){
					message = message + "联络单状态  ";
				}
				if(message !=null && message !=""){
					$.messager.alert('提示', message+"不能为空 !", 'info'); 
				}else{
					$.ajax({
						type : 'POST',
						url : '/sheetManager/updateSheet',
						data : {
							id : id,
							handleId : handleId,
							handleText : handleText,
							handleState : handleState
						},
						dataType : 'json',
						success: function(r){
							if(r.state == "ok"){
								$(':radio[name=import_lv_handle]').removeAttr("checked");
								$(':radio[name=handle_lv_handle]').removeAttr("checked");
								$("#handle_sheet_dialog").dialog('close');
								$("#handle_sheet_form").form('reset');
								$("#sheet_datagrid").datagrid('reload');
							}
							if(r.state == "error"){
								$.messager.alert('提示信息',r.message,"info");
								return false;
							}
						},
						error:function(result){
							$.messager.alert('提示信息',result.message,"info");
							return false;
						}
					});
				}
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				$(':radio[name=import_lv_handle]').removeAttr("checked");
				$(':radio[name=handle_lv_handle]').removeAttr("checked");
				$("#handle_sheet_dialog").dialog('close');
				$("#sheet_datagrid").datagrid('reload');
				$("#handle_sheet_form").form('reset');
			}
		}]
	});
}

/**
 * 删除联络单
 */
function deleteWorksheet(id){
	$.ajax({
		type : 'POST',
		url : "/sheetManager/deleteSheet",
		data:{ id : id},
		dataType: 'json',
		success: function(r){
			if(r.state == "ok"){
				$("#sheet_datagrid").datagrid('reload');
			}
			if(r.state == "error"){
				$.messager.alert('提示信息',r.message,"info");
				return false;
			}
		},
		error:function(result){
			$.messager.alert('提示信息',result.message,"info");
			return false;
		}
	});
}


/**
 * 操作联络单
 */
 function editWorksheet(id,handleState,opt){
	 var sendId;
	 var checkId;
	 var manageId;
	 var handleId;
	 var userId;
	 var flag=true;
	 $.ajax({
			type: "post",
	        url:"/sheetManager/querySheet",
	        async:false, 
	        data: {"id": id },
	        resultType: "Sheet",
	        success:function(result){
	        	 sendId  = result.sendId;
	          	 checkId = result.checkId;
	          	 manageId = result.manageId;
	          	 handleId = result.handleId;
	          	 if(handleState !=result.handleState){
	          		$.messager.alert("提示信息","联络单信息已更新，请刷新后操作！","info");
					 flag =false;
	          	 }
	        }
	 });
	 $.ajax({
         type: "post",
         url:"/sheetManager/getUser",
         async:false, 
         resultType: "User",
         success: function(result){
        	 userId = result.id;
         }
     });
	 if(!flag){
		 $("#sheet_datagrid").datagrid('reload');
		 return false;
	 }else if(handleState == 0){
		 checkWorksheet(id);
	 }else if(handleState == 1){
		 if(opt != null || manageId == userId){
			 manageWorksheet(id);
		 }else{
			 $.messager.alert("提示信息","联络单信息已更新，请刷新后操作！","info");
			 $("#sheet_datagrid").datagrid('reload');
		 }
	 }else if(handleState >= 2 && handleState <5){
		 if(opt != null || handleId == userId){
			 handleWorksheet(id);
		 }else{
			 $.messager.alert("提示信息","联络单信息已更新，请刷新后操作！","info");
			 $("#sheet_datagrid").datagrid('reload');
		 }
	 }else if(handleState >=5){
		 $.messager.alert("提示信息","联络单已处理完结！","info");
	 }else{
		 $.messager.alert("提示信息","操作异常！","info");
	 }
}
 

 /**
 * 修改联络单
 */
function reviseWorksheet(id,handleState,opt){
	var sendId;
	var checkId;
	var manageId;
	var handleId;
	var userId;
	var flag =true; //页面信息是否为最新状态
	$.ajax({
		type: "post",
        url:"/sheetManager/querySheet",
        async:false, 
        data: {"id": id },
        resultType: "Sheet",
        success: function(result){
       	 	sendId  = result.sendId;
       	 	checkId = result.checkId;
       	 	manageId = result.manageId;
       		handleId = result.handleId;
       		if(handleState !=result.handleState){
       			flag = false;
      			$.messager.alert("提示信息","联络单信息已更新，请刷新后操作！","info");
      		}
        }
	});
	 $.ajax({
         type: "post",
         url:"/sheetManager/getUser",
         async:false, 
         resultType: "User",
         success: function(result){
        	 userId = result.id;
         }
     });
	if(!flag){
		$("#sheet_datagrid").datagrid('reload');
		return false;
	}else if(handleState == 0){
		if(opt != null || sendId == userId){
			reviseNewSheet(id);
		 }else{
			 $.messager.alert("提示信息","联络单信息已更新，请刷新后操作！","info");
			 $("#sheet_datagrid").datagrid('reload');
		 }
	}else if(handleState == 1){
		if(opt != null || checkId == userId){
			checkWorksheet(id);
		 }else{
			 $.messager.alert("提示信息","联络单信息已更新，请刷新后操作！","info");
			 $("#sheet_datagrid").datagrid('reload');
		 }
	}else if(handleState >= 2 && handleState <5){
		if(opt != null || manageId == userId){
			manageWorksheet(id);
		 }else{
			 $.messager.alert("提示信息","联络单信息已更新，请刷新后操作！","info");
			 $("#sheet_datagrid").datagrid('reload');
		 }
	}else if(handleState >=5){
		if(opt != null || handleId == userId){
			handleWorksheet(id);
		 }else{
			 $.messager.alert("提示信息","联络单信息已更新，请刷新后操作！","info");
			 $("#sheet_datagrid").datagrid('reload');
		 }
	}else{
		$.messager.alert("提示信息","操作异常！","info");
	}
}


/**
 * 联络单备注
 */
function sheetMark(id) {
	var editing = null; //判断用户是否处于编辑状态
	var isFinish = true;
	var flag;
	
	$('#sheet_mark_datagrid').datagrid({
		url: "/sheetMark/queryMark?sheetId="+id,
		fit: true,
		striped: true,
		fitColumns: true,
		nowrap: true,
		rownumbers: true,
		singleSelect: true,
		onLoadSuccess: function(date){
			$(".datagrid-row [field=mark]").tooltip({
                position: 'bottom',
                content: '<div class="panel">双击显示详情</div>'
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
					iconCls : 'icon-back',
					text : '返回',
					handler : function() {
						$("#sheet_mark").dialog('close');
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
				var userId =null;
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
				 if(row.sendId== userId){
				return "<button onclick=\"javascript: updateMark("+row.id+")\">修改</button>"+
						   "<button onclick=\"javascript: delMark("+row.id+")\">删除</button>";
				 }
			}
		}]],
		toolbar : [{
   			text : '新增备注',
   			iconCls : 'icon-add',
   			handler : function() {
   				if(isFinish){
					if(editing == null){
						flag = 'add';
   						$('#sheet_mark_datagrid').datagrid('unselectAll');//1 先取消所有的选中状态
   						$('#sheet_mark_datagrid').datagrid('appendRow',{description:''});//2追加一行
   						editing = $('#sheet_mark_datagrid').datagrid('getRows').length -1;//3获取当前页的行号.
   						$('#sheet_mark_datagrid').datagrid('beginEdit', editing);//4开启编辑状态
   						isFinish = false;
					} else {
						editing = null;
						$.messager.alert('提示信息','由于上次操作进行中，现已将数据初始化。请再次点击', 'info');
					}
				} else {
					$.messager.alert('提示信息',"编辑操作未保存，请先保存或取消再进行其它操作！","info");
				};
			}
		},'-',{
   			text : '保存操作',
   			iconCls : 'icon-save',
   			handler : function() {
   				if(flag != null && flag != undefined){
   					var userId;
   				 $.ajax({
   				         type: "post",
   				         url:"/sheetManager/getUser",
   				         async:false, 
   				         resultType: "User",
   				         success: function(result){
   				        	userId = result.id;
   				         }
   				     });
   						var mark= $('#sheet_mark_datagrid').datagrid('getEditor',{index:editing,field:'mark'});
   						var markValue= mark.target[0].value;
   					if(markValue.length>=1500){
   						$.messager.alert('提示信息',"备注不能超过1500字！","info");
   						return false;
   					}
   					$.ajax({
   						type:'POST',
   						url: '/sheetMark/addMark',
   						data:{
   							mark : markValue,
   							sendId : userId,
   							sheetId : id
   						},
   						dataType:'json',
   						success: function(r){
   							if(r.state == "ok"){
   								$('#sheet_mark_datagrid').datagrid('endEdit', editing);
   								editing = null;
   								isFinish = true;
   								flag=null;
   								$('#sheet_mark_datagrid').datagrid('load',{});
   							}
   							if(r.state == "error"){
   								$.messager.alert('提示信息',r.message,"info");
   								return false;
   							}
   						},
   						error:function(result){
   							$.messager.alert('提示信息',result.message,"info");
   							return false;
   						}
   					});	
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
	
	/**
	 * 双击查看备注详细
	 */
	$('#sheet_mark_datagrid').datagrid({
	    onDblClickCell: function (index, field, value) {
	        $('#sheet_mark_datagrid').datagrid('selectRecord', index);
	        var rowdata = $('#sheet_mark_datagrid').datagrid('getSelected');
	        $.ajax({
	            type: "post",
	            url: '/sheetMark/queryMark',
	            data: {
	                id: rowdata.id
	            },
	            cache: false,
	            dataType: 'json',
	            success: function (result) {
	                $('#mark_text').html(result[0].mark);
	                var mark_dialog = $('#mark_dialog').dialog({
	                    title: '备注详细',
	                    width: 600,
	                    height: 300,
	                    closable: false,
	                    resizable:true,
	                    cache: false,
	                    modal: true,
	                    buttons: [{
	                        text: '关闭',
	                        handler: function () {
	                            mark_dialog.dialog('close');
	                        }
	                    }]
	                });
	            }
	        });
	    }
	});
}


/**
 * 修改备注
 */
function updateMark(id) {
	var smark;
	 $.ajax({
         type: "post",
         url: '/sheetMark/queryMark',
         data: {
             id: id
         },
         cache: false,
         dataType: 'json',
         success: function (result) {
        	 smark = result[0].mark;
             $('#mark_text').html(smark);
             var mark_dialog = $('#mark_dialog').dialog({
                 title: '修改备注',
                 width: 600,
                 height: 300,
                 closable: false,
                 resizable:true,
                 cache: false,
                 modal: true,
                 buttons: [{
                	 iconCls : 'icon-ok',
         			text : '保存',
         			handler : function() {
         				if(smark==$('#mark_text').val()){
         					mark_dialog.dialog('close');
							$.messager.alert('提示信息',"未进行任何修改","info");
         					return false;
         				}
         				$.ajax({
    						type:'POST',
    						url: '/sheetMark/updateMark',
    						data:{
    							id : id,
    							mark : $('#mark_text').val()
    						},
    						dataType:'json',
    						success: function(r){
    							if(r.state == "ok"){
    								mark_dialog.dialog('close');
    								$('#sheet_mark_datagrid').datagrid('load',{});
    							}
    							if(r.state == "error"){
    								$.messager.alert('提示信息',r.message,"info");
    								return false;
    							}
    						},
    						error:function(result){
    							$.messager.alert('提示信息',result.message,"info");
    							return false;
    						}
    					});	
         				
         			}
                 },{
         			iconCls : 'icon-cancel',
        			text : '取消',
        			handler : function() {
        				 mark_dialog.dialog('close');
        			}
        		}]
             });
         }
     });
}


/**
 * 删除备注
 */
function delMark(markId){
		$.messager.confirm('提示信息', '确认删除?', function(r) {
		if (r) {
			$.ajax({
				type : 'POST',
				url : "/sheetMark/deleteMark?markId=" + markId,
				dataType : 'json',
				success : function(r) {
					if (r.state == "ok") {
						$('#sheet_mark_datagrid').datagrid('reload');
					}
					if (r.state == "error") {
						$.messager.alert('提示信息', r.message, "info");
						return false;
					}
				},
				error : function(result) {
					$.messager.alert('提示信息', result.message, "info");
					return false;
				}
			});
		} else {
			return;
		}
	});
}


/**
 * 设置日期格式
 */
function myformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}


/**
 *将页面中字符串转换为日期 
 */
function myparser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}


/**
 * 获取某部门的全部人员
 */
function showUser(obj_1,departmentId,id){
	obj_1.combobox({
		url:'/sheetManager/queryDeptUser?departmentId='+departmentId,
		valueField : 'id',
		textField : 'text',
		editable : false, 
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', id);
		}
	});
}


/**
 * 部门和员工下拉框二级联动
 */
function showDeptUser(obj_1,obj_2){
	obj_1.combobox({
		url:'/departmentManager/getDepartmentList?selectId='+null, 
		valueField : 'id',
		textField : 'text',
		onSelect : function(rec){
			obj_2.combobox({
				url : '/sheetManager/queryDeptUser?departmentId='+rec.id,
				width : "80",
				valueField : 'id',
				textField : 'text',
				onLoadSuccess: function (data) {
					if (data) {	    	   
						obj_2.combobox('setValue',-1);
		            }
		         }
			});
		},
		onLoadSuccess: function (data) {
			if (data) {	    	   
				obj_1.combobox('setValue',"--请选择");
            }
         }
	});
}


/**
 * 部门和两个员工下拉框二级联动
 */
function showDeptUser2(obj_1,obj_2,obj_3){
	obj_1.combobox({
		url:'/departmentManager/getDepartmentList?selectId='+null, 
		valueField : 'id',
		textField : 'text',
		onSelect : function(rec){
			obj_2.combobox({
				url : '/sheetManager/queryDeptUser?departmentId='+rec.id,
				width : "80",
				valueField : 'id',
				textField : 'text',
				onLoadSuccess: function (data) {
					if (data) {	    	   
						obj_2.combobox('setValue',-1);
		            }
		         }
			});
			obj_3.combobox({
				url : '/sheetManager/queryDeptUser?departmentId='+rec.id,
				width : "80",
				valueField : 'id',
				textField : 'text',
				onLoadSuccess: function (data) {
					if (data) {	    	   
						obj_3.combobox('setValue',-1);
		            }
		         }
			});
		},
		onLoadSuccess: function (data) {
			if (data) {	    	   
				obj_1.combobox('setValue',"--请选择");
            }
         }
	});
}


/**
 * 查找联络单状态
 */
function findHState(val){
	if(val == 0){
		return "待审核";
	}else if(val == 1){
		return "待分配";
	}else if(val == 2){
		return "未开始";
	}else if(val == 3){
		return "进行中";
	}else if(val == 4){
		return "延后";
	}else if(val == 5){
		return "已完成";
	}else if(val == 6){
		return "取消";
	}else{
		return "";
	}
}


/**
 * 查找联络单类型
 */
function findSType(val){
	if(val == 1){
		return "数据采集";
	}else if(val == 2){
		return "电子档案";
	}else if(val == 3){
		return "卡管系统";
	}else if(val == 4){
		return "其他类型";
	}else{
		return "";
	}
}

/**
 * 判断对象是否相等
 */
function equalObj(objA, objB){
	var result = true;
    if (arguments[0] instanceof Object && arguments[1] instanceof Object){
        var al = 0, bl = 0; //两对象中的属性个数
        for (var o in arguments[0]){
            //判断两个对象的同名属性是否相同（数字或字符串）
            if (typeof arguments[0][o] == 'number' || typeof arguments[0][o] == 'string'){
            	result = result && eval("arguments[0]['" + o + "'] == arguments[1]['" + o + "']");
            }else {
                if (!arguments.callee(eval("arguments[0]['" + o + "']"), eval("arguments[1]['" + o + "']"))){
                    result = result && false;
                }
            }
            al++;
        }
        for (var o in arguments[1]) {
            bl++;
        }
        //如果两个对象的属性数目不等，则两个对象也不等
        if (al != bl){
        	result = false;
        }
    }else{
    	result =false;
    }
    return result;
}
