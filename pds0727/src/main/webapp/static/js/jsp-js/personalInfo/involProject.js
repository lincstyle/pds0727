; app.personalInfo_personalInfoManager = (function (){
	$('#involProject_datagrid').datagrid({
		url:'/personalInfoManager/pcShowProject',
		pagination : true,
		pageSize : 10,
		pageList : [10, 20, 30],
		fit : true,
		striped : true,
		fitColumns : true,
		rownumbers : true,
		singleSelect : true,
		columns : [[{
				field : 'pid',
				title : '工程id',
				width : 80,
				hidden : true
			 },{
			    field : 'pname',
		        title : '项目名',
		        width : 250
		     },{
		        field : 'username',
		        title : '人员名',
		        width : 80,
		        hidden : true
		     },{
		        field : 'userid',
		        title : '人员id',
		        width : 80,
		        hidden : true
		     },{
		        field:'puid',
		        title:'记录',
		        width:80,
		        hidden : true
		     },{
		    	field : 'opt',
		        title : '接收消息',
		        width:   50,
		        formatter : function(value, row, index){
		        	var checkedAttr = "";
		        	if(row.isreceive == 1){
		        		checkedAttr ='checked="checked"';
		        	} 		        		
		        	return "<input id='isreceive"+row.pid+"' type='checkbox' "+checkedAttr+" onclick = isReceive('"+row.pid+"')  />";
		        }
		     }
		]]
	});
})();
	
function isReceive (proId){
	var isReceive = $("#isreceive"+proId)[0].checked;
	var receive = 0;
	if(isReceive){
		receive = 1;
	}
	$.ajax({
		type: 'POST',
		url: '/personalInfoManager/editIsReceive',
		data: {
			proId:proId,
			isReceive:receive
		},
		dataType: 'json',
		error : function(result){
			$.messager.alert('错误信息','修改失败,出现未知错误,请联系超级管理员!','error');
			$("#isreceive").prop('checked',false);
		}
	});
}






