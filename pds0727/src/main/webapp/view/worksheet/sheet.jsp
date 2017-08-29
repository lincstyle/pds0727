<%@page import="com.ctdcn.pds.organization.model.User,java.util.Date,java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;
%>
<script type="text/javascript"  src="/static/js/jsp-js/worksheet/datagrid-groupview.js"></script>
<script type="text/javascript" src="/static/js/jsp-js/worksheet/sheet.js"></script>
<script type="text/javascript">
;app.worksheet_sheetManager=(function(){
	$('#sheet_datagrid').datagrid({
		url: "/sheetManager/sheetList",
		pagination: true,
		pageSize: 10,
		pageList: [10, 25, 100],
		fit: true,
		striped: true,
		fitColumns: false,
		nowrap: true,
		rownumbers: true,
		singleSelect: true,
		groupField:'sendWeek',  
	    view: groupview,  
	    groupFormatter:function(value, rows){  
	        return value;  
	    },
		onLoadSuccess: function(date){
			$(this).datagrid('doCellTip',{
				onlyShowInterrupt: false,     //是否只有在文字被截断时才显示tip，默认值为false  无效           
                position: 'bottom',   //tip的位置，可以为top,botom,right,left
                cls: { 'background-color': '#D1EEEE' },  //tip的样式
                delay: 500   //tip 响应时间
			});
		},
		columns:[[{
			checkbox:true,
			field: 'id',
			title: 'id',
			width: 50
		},{
			field: 'sendDept',
			title: '需求提出部门',
			width: 100
		},{
			field: 'sendName',
			title: '提出人',
			width: 50
		},{
			field: 'sendDate',
			title: '提出时间',
			width: 80		
		},{
			field: 'sendType',
			title: '类别',
			width: 80,
			formatter : function type_name(value){
				return findSType(value);
			}
		},{
			field: 'sendTheme',
			title: '项目名称',
			width: 200
		},{
			field: 'endDate',
			title: '时间要求',
			width: 80	
		},{
			field: 'handleName',
			title: '执行人',
			width: 50
		},{
			field: 'handleState',
			title: '状态',
			width: 50,
			formatter : function state_name(value){
				return findHState(value);
			}
		},{
			field: 'handleText',
			title: '反馈信息',
			width: 300
		},{
			field: 'manageName',
			title: '负责人',
			width: 50
		},{
			field: 'checkName',
			title: '发出人',
			width: 50
		},{
			field: 'opt',
			title: '操作',
			width: 100,
			formatter: function (value, row, index){
				var userId;
				var userName = null;
				var isChecker =false;
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
				<shiro:hasPermission name="sheet:sheet:checkWroksheet" >
					isChecker=true;
				</shiro:hasPermission>
				//待审核联络单按钮
				var opt=null;
				if(row.handleState == 0 ){
					 if(isChecker && row.sendId ==userId){
						 return "<button onclick=\"javascript: editWorksheet("+row.id+","+row.handleState+","+opt+")\">审核</button>"+
						 			"<button onclick=\"javascript: reviseWorksheet("+ row.id+","+row.handleState+","+opt+ ")\">修改</button>";
					 }else if((!isChecker) && row.sendId ==userId){
						 return "<button onclick=\"javascript: reviseWorksheet("+row.id+","+row.handleState+","+opt+")\">修改</button>"+
						 			"<button onclick=\"javascript: sheetMark(" + row.id + ")\">备注</button>";
					 }else if(isChecker && row.sendId !=userId){
						 return "<button onclick=\"javascript: editWorksheet("+row.id+","+row.handleState+","+opt+")\">审核</button>"+
						 			"<button onclick=\"javascript: sheetMark("+ row.id + ")\">备注</button>";
					 }
				 }
				 // 联络单相关人员
				 if(row.checkId == userId || row.sendId == userId || row.manageId == userId ||
						 row.handleId == userId || row.signId == userId){
					 //待分配联络单按钮
					 if(row.handleState==1 ){
						if(row.manageId == userId && row.checkId == userId){
							 return "<button onclick=\"javascript: editWorksheet("+ row.id+","+row.handleState+","+opt+")\">分配</button>"+
							 			"<button onclick=\"javascript: reviseWorksheet("+row.id+","+row.handleState+","+opt+")\">修改</button>";
						 }else  if(row.manageId == userId && row.checkId != userId){
							 return "<button onclick=\"javascript: editWorksheet("+ row.id+","+row.handleState+","+opt+")\">分配</button>"+
							 			"<button onclick=\"javascript: sheetMark(" + row.id + ")\">备注</button>";
						 }else if(row.manageId != userId && row.checkId == userId){
							 return "<button onclick=\"javascript: reviseWorksheet("+row.id+","+row.handleState+","+opt+")\">修改</button>"+
							 			"<button onclick=\"javascript: sheetMark(" + row.id + ")\">备注</button>";
						 }else{
							 return  "<button onclick=\"javascript:qallWorksheet("+row.id+")\">详细</button>"+
										"<button onclick=\"javascript: sheetMark(" + row.id + ")\">备注</button>";
						 }
					}
					 //待处理联络单按钮
					 else if(row.handleState==2 || row.handleState==3 || row.handleState ==4){
						 if(row.handleId == userId && row.manageId == userId){
							return 	"<button onclick=\"javascript: editWorksheet("+ row.id+","+row.handleState+","+opt+ ")\">处理</button>"+
						 				"<button onclick=\"javascript: reviseWorksheet("+row.id+","+row.handleState+ ","+opt+")\">修改</button>";
						}else if(row.handleId == userId && row.manageId != userId){
							return 	"<button onclick=\"javascript: editWorksheet("+ row.id+","+row.handleState+","+opt+ ")\">处理</button>"+
					 					"<button onclick=\"javascript: sheetMark(" + row.id + ")\">备注</button>";
						}else if(row.handleId != userId && row.manageId == userId){
							return 	"<button onclick=\"javascript: reviseWorksheet("+row.id+","+row.handleState+ ","+opt+ ")\">修改</button>"+
					 					"<button onclick=\"javascript: sheetMark(" + row.id + ")\">备注</button>";
						}else{
							return  "<button onclick=\"javascript:qallWorksheet("+row.id+")\">详细</button>"+
										"<button onclick=\"javascript: sheetMark(" + row.id + ")\">备注</button>";
						}
					}
					 //已完结联络单按钮
					 else if(row.handleState==5 ||  row.handleState ==6){
						if(row.handleId == userId){
							return 	"<button onclick=\"javascript: reviseWorksheet("+row.id+","+row.handleState+","+opt+ ")\">修改</button>"+
					 					"<button onclick=\"javascript: sheetMark(" + row.id + ")\">备注</button>";
						}else{
							return  "<button onclick=\"javascript:qallWorksheet("+row.id+")\">详细</button>"+
										"<button onclick=\"javascript: sheetMark(" + row.id + ")\">备注</button>";
						}
					}else{
					 		return  "<button onclick=\"javascript:qallWorksheet("+row.id+")\">详细</button>"+
										"<button onclick=\"javascript: sheetMark(" + row.id + ")\">备注</button>";
					}
				}
			}
		}]],
		toolbar : [{
			id: 'menu',
   			text : '我的联络单',
   			iconCls : 'combo-arrow',
   			handler : function() {
   				sheet_menu();
   			}
		},'-',{
   			text : '新建联络单',
   			iconCls : 'icon-add',
   			handler : function() {
   				addWorksheet();
   			}
   		},'-',<shiro:hasPermission name="sheet:sheet:checkSheet" >{
   			text : '审核联络单',
   			iconCls : 'icon-lock',
   			handler : function(){
   				var checkSheet = $("#sheet_datagrid").datagrid('getSelected');
   				if(checkSheet != null){
   						if(checkSheet.handleState==0){
   							editWorksheet(checkSheet.id,checkSheet.handleState,"admin");
   						}else{
   							$.messager.alert("提示信息","联络单不在本阶段，无法进行此操作！","info");
   						}
   					} else {
   						$.messager.alert("提示信息","请选择联络单","info");
   					}
   			}
   		},'-',</shiro:hasPermission> <shiro:hasPermission name="sheet:sheet:manageSheet" >{
   			text : '分配联络单',
   			iconCls : 'icon-man',
   			handler : function(){
   				var manageSheet = $("#sheet_datagrid").datagrid('getSelected');
   				if(manageSheet != null){
   						if(manageSheet.handleState==1){
   							editWorksheet(manageSheet.id,manageSheet.handleState,"admin");
   						}else{
   							$.messager.alert("提示信息","联络单不在本阶段，无法进行此操作！","info");
   						}
   					} else {
   						$.messager.alert("提示信息","请选择联络单","info");
   					}
   			}
   		},'-',</shiro:hasPermission> {
   			text : '转发联络单',
   			iconCls : 'icon-redo',
   			handler : function(){
   				var transferSheet = $("#sheet_datagrid").datagrid('getSelected');
   				if(transferSheet != null){
   						if(transferSheet.handleState==2 || transferSheet.handleState==3 || transferSheet.handleState==4){
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
   							if(transferSheet.handleId ==userId){
   								transferWorksheet(transferSheet.id);
   							}else{
   								$.messager.alert("提示信息","您不是本联络单执行人，无法进行此操作！","info");
   							}
   	   					}else{
   	   						$.messager.alert("提示信息","只能转发待处理联络单！","info");
   	   					}
   				} else {
   					$.messager.alert("提示信息","请选择联络单","info");
   				}
   			}
   		},'-',<shiro:hasPermission name="sheet:sheet:handleSheet" >{
   			text : '处理联络单',
   			iconCls : 'icon-edit',
   			handler : function(){
   				var handleSheet = $("#sheet_datagrid").datagrid('getSelected');
   				if(handleSheet != null){
   						if(handleSheet.handleState==2 || handleSheet.handleState==3 || handleSheet.handleState==4){
   							editWorksheet(handleSheet.id,handleSheet.handleState,"admin");
   	   					}else{
   	   						$.messager.alert("提示信息","联络单不在本阶段，无法进行此操作！","info");
   	   					}
   					} else {
   						$.messager.alert("提示信息","请选择联络单","info");
   					}
   			}
   		},'-',</shiro:hasPermission> <shiro:hasPermission name="sheet:sheet:editSheet" >{
   			text :'修改联络单',
   			iconCls : 'icon-undo',
   			handler : function(){
   				var reviseSheet = $("#sheet_datagrid").datagrid('getSelected');
   				if(reviseSheet !=null){
   					reviseWorksheet(reviseSheet.id,reviseSheet.handleState,"admin");
   				}else{
   					$.messager.alert("提示信息","请选择联络单","info");
   				}
   			}
   		},'-',</shiro:hasPermission> <shiro:hasPermission name="sheet:sheet:deleteSheet" >{
   			text : '删除联络单',
			iconCls : 'icon-cancel',
			handler : function(){
				var deleteSheet = $("#sheet_datagrid").datagrid('getSelected');
   				if(deleteSheet != null){
   						$.messager.confirm('提示信息' , '确认删除?' , function(r){
   							if(r){
   								deleteWorksheet(deleteSheet.id);
   							}else {
   							 	return ;
   							}
   						});
   					} else {
   						$.messager.alert("提示信息","请选择联络单","info");
   					}
			}
   		},'-',</shiro:hasPermission> <shiro:hasPermission name="sheet:sheet:querySheet" >{
   			text : '联络单详细',
   			iconCls : 'icon-large-smartart',
   			handler : function(){
   				var querySheet = $("#sheet_datagrid").datagrid('getSelected');
   				if(querySheet != null){
   					qallWorksheet(querySheet.id);
   				}else{
   					$.messager.alert("提示信息","请选择联络单","info");
   				}
   			}
   		},'-',</shiro:hasPermission> {
   			text : '联络单查询',
   			iconCls : 'icon-search',
   			handler : function(){
   				findWorksheet();
   			}
   		}<shiro:hasPermission name="sheet:sheet:expSheet" >,'-',{
   			text : '导出联络单',
   			iconCls : 'icon-redo',
   			handler : function(){
   				var exportSheet = $("#sheet_datagrid").datagrid('getSelected');
   				if(exportSheet != null){
   					expSheet(exportSheet.id);
   				}else{
   					$.messager.alert("提示信息","请选择联络单","info");
   				}
   			}
   		}</shiro:hasPermission>
   		]
	});
})();

</script>
<style type="text/css">
	.select{
	border:1px solid #ccc;
	line-height:90%;
	margin:-1px;
	padding:4px 3px;
	width:100%;
	*width:90%
	}
</style>
<table id="sheet_datagrid"></table>

<!-- 联络单查询 -->
<div id="find_sheet_dialog">
	<table align="center" style="padding-top: 10px;">
		<tr>
			<td><br />
			<td>
		</tr>
		<tr>
			<td align="right" >提出部门：</td>
			<td><input id="find_send_dept"  ></td>
			<td align="right" >提出人：</td>
			<td><input id="find_send_name"  ></td>
		</tr>
		<tr>
			<td><br />
			<td>
		</tr>
		<tr>
			<td align="right">处理部门：</td>
			<td><input id="find_handle_dept"  ></td>
			<td align="right" >执行人：</td>
			<td><input id="find_handle_name"  ></td>
		</tr>
		<tr>
			<td><br />
			<td>
		</tr>
		<tr>
			<td align="right">提出时间/ 开始：</td>
			<td><input id="find_sendSDate" /></td>
			<td align="right">结束：</td>
			<td><input id="find_sendtEDate" /></td>
		</tr>
			<tr>
			<td><br />
			<td>
		</tr>
		<tr>
			<td align="right">要求时间/ 开始：</td>
			<td><input id="find_endSDate" /></td>
			<td align="right">结束：</td>
			<td><input id="find_endtEDate" /></td>
		</tr>
		<tr>
			<td><br />
			<td>
		</tr>
		<tr>
			<td align="right">联络单状态：</td>
			<td>
				<select id="find_handleState"  class="select"  >
						<option value="" selected='ture'>-- 请选择 --</option>
						<option value="0">待审核</option>
						<option value="1">待分配</option>
						<option value="2">处理中</option>
						<option value="5">已完结</option>
				</select>
			</td>
		</tr>
	</table>
</div>

<!-- 新建联络单 -->
<div id="add_sheet_dialog" style="display: none;" align="center" >
	<form id="add_sheet_form" method="post">
		<table id="head_add" align="center" width="540" >
			<tr>
				<td colspan="6" align="center" height="50">
					<b>楚天龙实业有限公司工作联络单</b>
				</td>
			</tr>
			<tr height="30">
				<td width="30">编号:</td>
				<td width="180" id="no_add" align="left">
				</td>
				<td width="30">签发:</td>
				<td width="100" Valign="middle">
					<input  id="sign_name_add"  type="text" style="width:100%; height:95%; border:0;">
				</td>
				<td width="30">日期:</td>
				<td width="70" id="send_date_add" align="left">
				</td>
			</tr>
		</table>
		<table id="mid_add" align="center" style="padding-top: 10px;"
					width="540" height="500" border='1' cellspacing="0" cellpadding="0">
			<tr>
				<td width="100" height="30" align="center">
					提出单位/部门<font color="red">*</font>
					<input id="send_dept_id_add" type="text"   style="display:none;"/>
				</td>
				<td width="300" align="center"  Valign="middle">
					<input  id="send_dept_add"  type="text"  
					style="width:100%; *width:95%; height:90%; border:0;" >
				</td>
				<td width="60" align="center">
					提出人<font color="red">*</font>
					<input id="send_id_add" type="text"   style="display:none;">
				<td width="80" align="center"  Valign="middle">
					<input  id="send_name_add"  type="text" 
					style="width:100%; *width:99%;height:90%; border:0;" >
				</td>
			</tr>
			<tr>
				<td height="30" align="center">处理单位/部门<font color="red">*</font></td>
				<td  colspan="3" align="center"  Valign="middle">
					<input id="handle_dept_add"  type="text" 
								style="width:100%; *width:99%; height:90%; border:0;" >
				</td>
			</tr>
			<tr>
				<td height="30" align="center">抄送单位/部门</td>
				<td  colspan="3" align="center"  Valign="middle">
					<input id="notify_dept_add"  type="text" 
								style="width:100%; *width:95%; height:90%; border:0;">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">发文主题<font color="red">*</font></td>
				<td colspan="3">
					<input id="send_theme_add"  type="text" 
						style="width:100%; height:97%; border:0;" >
				</td>
			</tr>
			<tr>
				<td height="30" align="center">时间要求<font color="red">*</font></td>
				<td align="center"  Valign="middle">
					<input id="end_date_add"  type="text"  style="width:100%; *width:99%; height:90%; border:0;"  
							class="easyui-datebox" >
				</td>
				<td height="30" align="center">项目类型<font color="red">*</font></td>
				<td align="center"  Valign="middle">
					<select id="send_type_add"  class="select"   >
						<option value="" selected='ture'>-- 请选择 --</option>
						<option value="1">数据采集</option>
						<option value="2">电子档案</option>
						<option value="3">卡管系统</option>
						<option value="4">其他类型</option>
					</select>
				</td>
			</tr>
			<tr>
				<td rowSpan="2" height="30" align="center">问题描述<font color="red">*</font></td>
				<td rowSpan="2" colspan="3" align="center"  Valign="middle">
					<textarea id="send_text_add"  cols="50" rows="10" 
					style="width:100%; height:95%; border:0;resize:none;" />
				</td>
			</tr>
			<tr></tr>
			<tr>
				<td height="30" align="center">重要程度<font color="red">*</font></td>
				<td colspan="3" align="center">
					<div style="width:33%; float:left;text-align:right">
					<input type="radio"name="import_lv_add" value="1"  style="width : 50"/>
					一般
					</div>
					<div style="width:33%; float:left;text-align:center">
					<input type="radio" name="import_lv_add" value="2" style="width : 50"/>
					重要
					</div>
					<div style="width:33%; float:left;text-align:left">
					<input type="radio" name="import_lv_add" value="3" style="width : 50"/>
					很重要
					</div>
				</td>
			</tr>
			<tr>
				<td height="30" align="center">处理缓急程度<font color="red">*</font></td>
				<td colspan="3" align="center">
					<div style="width:33%; float:left;text-align:right">
					<input type="radio" name="handle_lv_add" value="1"  />
					一般
					</div>
					<div style="width:33%; float:left;text-align:center">
					<input type="radio" name="handle_lv_add" value="2" />
					急&nbsp;&nbsp;&nbsp;
					</div>
					<div style="width:33%; float:left;text-align:left">
					<input type="radio" name="handle_lv_add" value="3" />
					紧急
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>

<!-- 审核联络单 -->
<div id="check_sheet_dialog" style="display: none;" align="center" >
	<form id="check_sheet_form" method="post">
		<table id="head_check" align="center" width="540">
			<tr>
				<td colspan="6" align="center" height="50">
					<b>楚天龙实业有限公司工作联络单</b>
				</td>
			</tr>
			<tr>
				<td width="30">编号:</td>
				<td width="180"  id="no_check" align="left">
				</td>
				<td width="30">签发:</td>
				<td width="100" id="sign_name_check" align="left">
				</td>
				<td width="30">日期:</td>
				<td width="70" id="send_date_check" align="left">
				</td>
			</tr>
		</table>
		<table id="mid_check" align="center" style="padding-top: 10px;"
					width="540" height="500" border='1' cellspacing="0" cellpadding="0">
			<tr>
				<td width="100" height="30" align="center" id="send_dept_id_check" >
				提出单位/部门
				</td>
				<td width="300"  id="send_dept_check"  align="left">
				</td>
				<td width="60" align="center"  id="send_id_check" >
				提出人
				</td>
				<td width="80" align="left"  id="send_name_check" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">处理单位/部门</td>
				<td  colspan="3"  id="handle_dept_check"  align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">抄送单位/部门</td>
				<td  colspan="3"  id="notify_dept_check" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">发文主题</td>
				<td colspan="3"  id="send_theme_check" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">时间要求</td>
				<td id="end_date_check" align="left">
				</td>
				<td height="30" align="center">项目类型</td>
				<td align="left"   id="send_type_check" >
				</td>
			</tr>
			<tr>
				<td rowSpan="2" height="30" align="center">问题描述</td>
				<td rowSpan="2" colspan="3"  align="center"  Valign="middle">
					<textarea  id="send_text_check"  cols="50" rows="10" 
					style="width:100%; height:95%; border:0; resize:none;" readonly="readonly" />
				</td>
			</tr>
			<tr></tr>
			<tr>
				<td height="30" align="center">重要程度</td>
				<td colspan="3" align="center">
					<div style="width:33%; float:left;text-align:right">
					<input type="radio"name="import_lv_check" value="1" />
					一般
					</div>
					<div style="width:33%; float:left;text-align:center">
					<input type="radio" name="import_lv_check" value="2" />
					重要
					</div>
					<div style="width:33%; float:left;text-align:left">
					<input type="radio" name="import_lv_check" value="3" />
					很重要
					</div>
				</td>
			</tr>
			<tr>
				<td height="30" align="center">处理缓急程度</td>
				<td colspan="3" align="center">
					<div style="width:33%; float:left;text-align:right">
					<input type="radio" name="handle_lv_check" value="1"  />
					一般
					</div>
					<div style="width:33%; float:left;text-align:center">
					<input type="radio" name="handle_lv_check" value="2" />
					急&nbsp;&nbsp;&nbsp;
					</div>
					<div style="width:33%; float:left;text-align:left">
					<input type="radio" name="handle_lv_check" value="3" />
					紧急
					</div>
				</td>
			</tr>
			<tr>
				<td height="30" align="center">审核人
					<input id="check_id_check" type="text"   style="display:none;">
				</td>
				<td colspan="3"  id="check_name_check"  align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">负责人<font color="red">*</font>
				</td>
				<td colspan="3"  >
					<input id="manage_name_check"  type="text" 
					style="width:100%; *width:99%; height:90%; border:0;">
				</td>
			</tr>
		</table>	
	</form>
</div>

<!-- 分配联络单 -->
<div id="manage_sheet_dialog" style="display: none;" align="center" >
	<form id="manage_sheet_form" method="post">
		<table id="head_manage" align="center" width="540">
			<tr>
				<td colspan="6" align="center" height="50">
					<b>楚天龙实业有限公司工作联络单</b>
				</td>
			</tr>
			<tr>
				<td width="30">编号:</td>
				<td width="180" id="no_manage" align="left">
				</td>
				<td width="30">签发:</td>
				<td width="100" id="sign_name_manage"  align="left">
				</td>
				<td width="30">日期:</td>
				<td width="70" id="send_date_manage"  align="left">
				</td>
			</tr>
		</table>
		<table id="mid_manage" align="center" style="padding-top: 10px;"
					width="540" height="500" border='1' cellspacing="0" cellpadding="0">
			<tr>
				<td width="100" height="30" align="center" >
				提出单位/部门
				</td>
				<td width="300" id="send_dept_manage"  align="left">
				</td>
				<td width="60" align="center"  id="send_id_manage" >
				提出人
				</td>
				<td width="80"  id="send_name_manage" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">处理单位/部门</td>
				<td  colspan="3" id="handle_dept_manage" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">抄送单位/部门</td>
				<td  colspan="3" id="notify_dept_manage" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">发文主题</td>
				<td colspan="3"  id="send_theme_manage" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">时间要求</td>
				<td  id="end_date_manage" align="left">
				</td>
				<td height="30" align="center">项目类型</td>
				<td  id="send_type_manage" align="left">
				</td>
			</tr>
			<tr>
				<td rowSpan="2" height="30" align="center">问题描述</td>
				<td rowSpan="2" colspan="3" align="center"  Valign="middle">
					<textarea  id="send_text_manage"   cols="50" rows="10" 
					style="width:100%; height:95%; border:0; resize:none;" readonly="readonly" />
				</td>
			</tr>
			<tr></tr>
			<tr>
				<td height="30" align="center">重要程度</td>
				<td colspan="3" align="center">
					<div style="width:33%; float:left;text-align:right">
					<input type="radio"name="import_lv_manage" value="1"  />
					一般
					</div>
					<div style="width:33%; float:left;text-align:center">
					<input type="radio" name="import_lv_manage" value="2" />
					重要
					</div>
					<div style="width:33%; float:left;text-align:left">
					<input type="radio" name="import_lv_manage" value="3" />
					很重要
					</div>
				</td>
			</tr>
			<tr>
				<td height="30" align="center">处理缓急程度</td>
				<td colspan="3" align="center">
					<div style="width:33%; float:left;text-align:right">
					<input type="radio" name="handle_lv_manage" value="1"  />
					一般
					</div>
					<div style="width:33%; float:left;text-align:center">
					<input type="radio" name="handle_lv_manage" value="2" />
					急&nbsp;&nbsp;&nbsp;
					</div>
					<div style="width:33%; float:left;text-align:left">
					<input type="radio" name="handle_lv_manage" value="3" />
					紧急
					</div>
				</td>
			</tr>
			<tr>
				<td height="30" align="center">负责人
					<input id="manage_id_manage" type="text"   style="display:none;">
				</td>
				<td colspan="3" id="manage_name_manage" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">执行人<font color="red">*</font>
					<input id="handle_id_manage"   type="text"   style="display:none;"/>
				</td>
				<td colspan="3">
					<input id="handle_name_manage"  type="text" 
					style="width:100%; *width:99%; height:90%; border:0;"> 
				</td>
			</tr>
		</table>	
	</form>
</div>

<!-- 转发联络单 -->
<div id="transfer_sheet_dialog" style="display: none;" align="center" >
	<form id="transfer_sheet_form" method="post">
		<table id="head_transfer" align="center" width="540">
			<tr>
				<td colspan="6" align="center" height="50">
					<b>楚天龙实业有限公司工作联络单</b>
				</td>
			</tr>
			<tr>
				<td width="30">编号:</td>
				<td width="180" id="no_transfer"  align="left">
				</td>
				<td width="30">签发:</td>
				<td width="100"  id="sign_name_transfer" align="left">
				</td>
				<td width="30">日期:</td>
				<td width="70" id="send_date_transfer" align="left">
				</td>
			</tr>
		</table>
		<table id="mid_transfer" align="center" style="padding-top: 10px;"
					width="540" height="500" border='1' cellspacing="0" cellpadding="0">
			<tr>
				<td width="100" height="30" align="center">
				提出单位/部门<input id="send_dept_id_transfer" type="text"   style="display:none;"/>
				</td>
				<td width="300" id="send_dept_transfer" align="left">
				</td>
				<td width="60" align="center">
				提出人<input id="send_id_transfer" type="text"  style="display:none;"/>
				</td>
				<td width="80" id="send_name_transfer" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">处理单位/部门</td>
				<td  colspan="3"  id="handle_dept_transfer"  align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">抄送单位/部门</td>
				<td  colspan="3"  id="notify_dept_transfer" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">发文主题</td>
				<td colspan="3"  id="send_theme_transfer" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">时间要求</td>
				<td id="end_date_transfer" align="left">
				</td>
				<td height="30" align="center">项目类型</td>
				<td id="send_type_transfer"  align="left">
				</td>
			</tr>
			<tr>
				<td rowSpan="2" height="30" align="center">问题描述</td>
				<td rowSpan="2" colspan="3"  align="center"  Valign="middle">
					<textarea  id="send_text_transfer"  cols="50" rows="10" 
					style="width:100%; height:95%; border:0; resize:none;" readonly="readonly"  />
				</td>
			</tr>
			<tr></tr>
			<tr>
				<td height="30" align="center">重要程度</td>
				<td colspan="3" align="center">
					<div style="width:33%; float:left;text-align: right">
					<input type="radio"name="import_lv_transfer" value="1"  />
					一般
					</div>
					<div style="width:33%; float:left;text-align: center">
					<input type="radio" name="import_lv_transfer" value="2" />
					重要
					</div>
					<div style="width:33%; float:left;text-align: left">
					<input type="radio" name="import_lv_transfer" value="3" />
					很重要
					</div>
				</td>
			</tr>
			<tr>
				<td height="30" align="center">处理缓急程度</td>
				<td colspan="3" align="center">
					<div style="width:33%; float:left;text-align: right">
					<input type="radio" name="handle_lv_transfer" value="1"  />
					一般
					</div>
					<div style="width:33%; float:left;text-align: center">
					<input type="radio" name="handle_lv_transfer" value="2" />
					急&nbsp;&nbsp;&nbsp;
					</div>
					<div style="width:33%; float:left;text-align: left">
					<input type="radio" name="handle_lv_transfer" value="3" />
					紧急
					</div>
				</td>
			</tr>
			<tr>
				<td height="30" align="center">原执行人
					<input id="transfer_id_transfer" type="text"   style="display:none;">
				</td>
				<td colspan="3" id="transfer_name_transfer"  align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">新执行人<font color="red">*</font>
					<input id="handle_id_transfer"   type="text"   style="display:none;"/>
				</td>
				<td colspan="3">
					<input id="handle_name_transfer"  type="text" 
					style="width:100%; *width:99%; height:90%; border:0;">
				</td>
			</tr>
		</table>	
	</form>
</div>

<!-- 处理联络单 -->
<div id="handle_sheet_dialog" style="display: none;" align="center" >
	<form id="handle_sheet_form" method="post">
		<table id="head_handle" align="center" width="540">
			<tr>
				<td colspan="6" align="center" height="50">
					<b>楚天龙实业有限公司工作联络单</b>
				</td>
			</tr>
			<tr>
				<td width="30">编号:</td>
				<td width="180" id="no_handle" align="left">
				</td>
				<td width="30">签发:</td>
				<td width="100"  id="sign_name_handle" align="left">
				</td>
				<td width="30">日期:</td>
				<td width="70"  id="send_date_handle" align="left">
				</td>
			</tr>
		</table>
		<table id="mid_handle" align="center" style="padding-top: 10px;"
					width="540" height="500" border='1' cellspacing="0" cellpadding="0">
			<tr>
				<td width="100" height="30" align="center">
				提出单位/部门<input id="send_dept_id_handle" type="text"   style="display:none;"/>
				</td>
				<td width="300"  id="send_dept_handle" align="left">
				</td>
				<td width="60" align="center">
				提出人<input id="send_id_handle" type="text"  style="display:none;"/>
				</td>
				<td width="80" id="send_name_handle" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">处理单位/部门</td>
				<td  colspan="3" id="handle_dept_handle"  align="left"> 
				</td>
			</tr>
			<tr>
				<td height="30" align="center">抄送单位/部门</td>
				<td  colspan="3" id="notify_dept_handle" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">发文主题</td>
				<td colspan="3"  id="send_theme_handle" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center" >时间要求</td>
				<td  id="end_date_handle" align="left">
				</td>
				<td height="30" align="center">项目类型</td>
				<td id="send_type_handle" align="left">
				</td>
			</tr>
			<tr>
				<td rowSpan="2" height="30" align="center">问题描述</td>
				<td rowSpan="2" colspan="3" align="center"  Valign="middle">
					<textarea id="send_text_handle"  cols="50" rows="10" 
					style="width:100%; height:95%; border:0;resize:none;" readonly="readonly"  />
				</td>
			</tr>
			<tr></tr>
			<tr>
				<td height="30" align="center">重要程度</td>
				<td align="center">
					<div style="width:33%; float:left;text-align: right">
					<input type="radio"name="import_lv_handle" value="1" />
					一般
					</div>
					<div style="width:33%; float:left;text-align: center">
					<input type="radio" name="import_lv_handle" value="2" />
					重要
					</div>
					<div style="width:33%; float:left;text-align: left">
					<input type="radio" name="import_lv_handle" value="3" />
					很重要
					</div>
				</td>
				<td height="30" align="center">负责人
					<input id="manage_id_handle" type="text"   style="display:none;"  >
				</td>
				<td  id="manage_name_handle" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">处理缓急程度</td>
				<td  align="center">
					<div style="width:33%; float:left;text-align: right">
					<input type="radio" name="handle_lv_handle" value="1" />
					一般
					</div>
					<div style="width:33%; float:left;text-align: center">
					<input type="radio" name="handle_lv_handle" value="2" />
					急&nbsp;&nbsp;&nbsp;
					</div>
					<div style="width:33%; float:left;text-align: left">
					<input type="radio" name="handle_lv_handle" value="3" />
					紧急
					</div>
				</td>
				<td height="30" align="center">执行人
					<input id="handle_id_handle"   type="text"   style="display:none;" >
				</td>
				<td  id="handle_name_handle" align="left">
				</td>
			</tr>
			<tr>
				<td rowSpan="2" height="30" align="center">反馈信息<font color="red">*</font></td>
				<td rowSpan="2" colspan="3"  align="center"  Valign="middle">
					<textarea id="handle_text_handle"  cols="50" rows="10" 
						style="width:100%; height:95%; border:0;resize:none;" />
				</td>
			</tr>
			<tr></tr>
			<tr>
				<td height="30" align="center">联络单状态<font color="red">*</font></td>
				<td align="center"  colspan="3">
					<select id="handle_state_handle"  class="select"  >
						<option value="">--请选择</option>
						<option value="3">进行中</option>
						<option value="4">延后</option>
						<option value="5">已完成</option>
						<option value="6">取消</option>
					</select>
				</td>
			</tr>
		</table>	
	</form>
</div>

<!-- 联络单详细 -->
<div id="qall_sheet_dialog" style="display: none;" align="center" >
	<form id="qall_sheet_form" method="post">
		<table id="head_qall" align="center" width="540">
			<tr>
				<td colspan="6" align="center" height="50">
					<b>楚天龙实业有限公司工作联络单</b>
				</td>
			</tr>
			<tr>
				<td width="30">编号:</td>
				<td width="180"  id="no_qall"  align="left">
				</td>
				<td width="30">签发:</td>
				<td width="100" id="sign_name_qall" align="left">
				</td>
				<td width="30" >日期:</td>
				<td width="70"  id="send_date_qall" align="left">
				</td>
			</tr>
		</table>
		<table id="mid_qall" align="center" style="padding-top: 10px;"
					width="540" height="500" border='1' cellspacing="0" cellpadding="0">
			<tr>
				<td width="100" height="30" align="center"  id="send_dept_id_qall">
				提出单位/部门
				</td>
				<td width="300"  id="send_dept_qall" align="left">
				</td>
				<td width="60" align="center" id="send_id_qall" >
				提出人
				</td>
				<td width="80"  id="send_name_qall" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center"  id="handle_dept_id_qall">处理单位/部门</td>
				<td  colspan="3" id="handle_dept_qall" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">抄送单位/部门</td>
				<td  colspan="3"  id="notify_dept_qall" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">发文主题</td>
				<td colspan="3" id="send_theme_qall" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">时间要求</td>
				<td id="end_date_qall" align="left">
				</td>
				<td height="30" align="center">项目类型</td>
				<td  id="send_type_qall" align="left">
				</td>
			</tr>
			<tr>
				<td rowSpan="2" height="30" align="center">问题描述</td>
				<td rowSpan="2" colspan="3"  align="center"  Valign="middle">
					<textarea id="send_text_qall" cols="50" rows="10" 
					style="width:100%; height:95%; border:0; resize:none;" readonly="readonly"  />
				</td>
			</tr>
			<tr></tr>
			<tr>
				<td height="30" align="center">重要程度</td>
				<td align="center">
					<div style="width:33%; float:left;text-align: right">
					<input type="radio"name="import_lv_qall" value="1"  />
					一般
					</div>
					<div style="width:33%; float:left;text-align: center">
					<input type="radio" name="import_lv_qall" value="2" />
					重要
					</div>
					<div style="width:33%; float:left;text-align: left">
					<input type="radio" name="import_lv_qall" value="3" />
					很重要
					</div>
				</td>
				<td height="30" align="center" id="manage_id_qall">负责人
				</td>
				<td   id="manage_name_qall" align="left">
				</td>
			</tr>
			<tr>
				<td height="30" align="center">处理缓急程度</td>
				<td  align="center">
					<div style="width:33%; float:left;text-align: right">
					<input type="radio" name="handle_lv_qall" value="1" />
					一般
					</div>
					<div style="width:33%; float:left;text-align: center">
					<input type="radio" name="handle_lv_qall" value="2" />
					急&nbsp;&nbsp;&nbsp;
					</div>
					<div style="width:33%; float:left;text-align: left">
					<input type="radio" name="handle_lv_qall" value="3" />
					紧急
					</div>
				</td>
				<td height="30" align="center"  id="handle_id_qall" >执行人
				</td>
				<td id="handle_name_qall" align="left">
				</td>
			</tr>
			<tr>
				<td rowSpan="2" height="30" align="center">反馈信息</td>
				<td rowSpan="2" colspan="3"  align="center"  Valign="middle">
					<textarea id="handle_text_qall" cols="50" rows="10" 
					style="width:100%; height:95%; border:0; resize:none;" readonly="readonly"  />
				</td>
			</tr>
			<tr></tr>
			<tr>
				<td height="30" align="center">联络单状态</td>
				<td colspan="3"  id="handle_state_qall"  align="left">
				</td>
			</tr>
		</table>	
	</form>
</div>

<!-- 联络单菜单 -->
<div id="sheet_menu" class="easyui-menu" style="width:120px;">
		 <div data-options="name:'all'">全部联络单</div>
		<div>
			<span>我的联络单</span>
			<div style="width:150px;">
				<div data-options="name:'mySheet'">与我相关联络单</div>
				<shiro:hasPermission name="sheet:sheet:checkWroksheet" >
				<div data-options="name:'myCheck'">待审核联络单</div>
				</shiro:hasPermission>
				<div data-options="name:'myManage'">待分配联络单</div>
				<div data-options="name:'myHandle'">待处理联络单</div>
				<div data-options="name:'myCompleted'">已处理联络单</div>
			</div>
		</div>
	</div>
	
<!-- 联络单备注 -->
<div id="sheet_mark" >
	<table id ="sheet_mark_datagrid" ></table>
	<div id="mark_dialog" style="width: 600px; height: 300px;"><textarea  id="mark_text" style="width: 97%; height: 97%; resize:none;"/></div>
</div>
