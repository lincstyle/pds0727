<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.shiro.subject.Subject" %>
<%@ page import="org.apache.shiro.SecurityUtils" %>
<%@ page import="com.ctdcn.pds.organization.model.User" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<link rel="stylesheet" href="/static/js/wysiwyg/style.css"/>
<script src="/static/js/wysiwyg/bootstrap-wysiwyg.min.js"></script>

<link rel="stylesheet" href="/static/js/tagsinput/tagsinput.css" type="text/css"/>
<script src="/static/js/tagsinput/tagsinput.js" type="text/javascript" charset="utf-8"></script>

<link rel="stylesheet" href="/static/js/jquery-autocomplete/styles.css" type="text/css"/>
<script src="/static/js/jquery-autocomplete/jquery.autocomplete.js" type="text/javascript" charset="utf-8"></script>

<script>
    $(function () {
        $('#editor').wysiwyg();
    })
</script>

<script>
    app.project_Log = (function () {

        var editLog = function (lid,pname) {
            debugger;
                $("#a").hide();
                $("#b").hide();
                $("#c").hide();
                $("#d").show();
                $('#log_pname_edit').html(pname);
                $.ajax({
                    type: "post",
                    url: '/projectLog/queryContent',
                    data: {
                        lid: lid
                    },
                    cache: false,
                    dataType: 'json',
                    success: function (r)
                    {
                        $('#editor').html(r.contents);
                    }
                });


                var editPrLog_Dialog = $('#addPr_Log').dialog({
                    title: '编辑日志',
                    width: 800,
                    height: 480,
                    closable: false,
                    resizable:true,
                    cache: false,
                    modal: true,
                    buttons: [{
                        iconCls: 'icon-ok',
                        text: '确定',
                        handler: function ()
                        {
                            if ($('#editor').html() == "") {
                                $.messager.alert('提示', "必须填写详细日志", 'info'); // 弹出提示信息
                                return false;
                            }
                            $.ajax({
                                type: "post",
                                url: '/projectLog/editPrLog',
                                data: {
                                    detail: $("#editor").text(),
                                    contents: $("#editor").html(),
                                    lid: lid
                                },
                                cache: false,
                                dataType: 'json',
                                success: function (r) {
                                    if (r.success) {
                                        editPrLog_Dialog.dialog('close');
                                        $.messager.alert("提示信息", r.msg);
                                        $('#pr_Log_datagrid').datagrid('load', {});
                                    } else {
                                        $.messager.alert("提示信息", r.msg);
                                    }

                                }
                            });

                        }
                    }, {
                        iconCls: 'icon-cancel',
                        text: '取消',
                        handler: function () {
                            editPrLog_Dialog.dialog('close');
                        }
                    }]
                });



        }

        var deleteLog = function (lid) {
                $.messager.confirm('确认', '是否删除该日报', function (r) {
                    if (r) {
                        deletePrLog(lid);
                        $('#pr_Log_datagrid').datagrid('load', {});
                    }
                });
        }

    	var tagInputAdd;
        var tagInputSearch;
        $('#pr_Log_datagrid').datagrid({
            url: '/projectLog/queryPrLog',
            pagination: true,
            pageSize: 10,
            pageList: [5, 10, 15, 20, 25, 30, 35],
            fit: true,//datagrid自适应宽高
            fitColumns: true,
            nowrap: false,
            singleSelect: true,
            rownumbers: true,
            onLoadSuccess:function(data){
                $(".datagrid-row [field=detail]").tooltip({
                    position: 'bottom',
                    content: '<div class="panel">双击显示详情</div>'
                });
            },
            columns: [[{
                //    field : 'bid',
                //    checkbox : true,
                //    sortable:true
                //}, {
                field: 'pname',
                title: '项目名',
                width: 80
            }, {
                field: 'person',
                title: '更新人',
                width: 80
            }, {
                field: 'detail',
                title: '日报简介',
                width: 200
            }, {
                field: 'pstage',
                title: '项目阶段',
                width: 50
            }, {
                field: 'sdate',
                title: '日报时间',
                width: 80
            }, {
                field: 'cdate',
                title: '提交时间',
                width: 80
            }, {
                field: 'isdelete',
                title: '是否删除',
                width: 100,
                hidden: true
            }, {
                field: 'lid',
                title: '日志id',
                width: 100,
                hidden: true
            }, {
                    field: 'opt',
                    title: '操作',
                    width: 50,
                    formatter: function (value, row, index) {
                        if(row.userId == <%=((User)SecurityUtils.getSubject().getSession().getAttribute(User.USER_SESSIN_KEY)).getId()%>){
                            return "<button onclick=\"javascript:app.project_Log.editLog("+row.lid+",'"+row.pname+"')\">修改</button>"+
                                    "<button onclick=\"javascript:app.project_Log.deleteLog(" + row.lid + ")\">删除</button>";
                        }
                    }
                }
            ]],
            toolbar: [{
                text: '查询',
                iconCls: 'icon-search',
                handler: function () {
                    $('#selectSDate_prLog').datebox();
                    $('#selectEDate_prLog').datebox();
                    $('#pname_prLog').val("")
                    $('#person_prLog').val("");
                    $('#selectSDate_prLog').val("");
                    $('#selectEDate_prLog').val("");

                    if (!tagInputSearch) {
                        tagInputSearch = new TagsInput($('#person_prLog'));
                        tagInputSearch.input.autocomplete({
                            serviceUrl: '/userManager/autoCompleteUser',
                            type: "POST",
                            dataType: "json",
                            paramName: "keywords",
                            transformResult: function (response) {
                                for (var i in response) {
                                    response[i].value = response[i].quanpin;
                                }
                                return {suggestions: response};
                            },
                            formatResult: function (suggestion, currentValue) {
                                return suggestion.name;
                            },
                            onSelect: function (suggestion) {
                                tagInputSearch.addTag(suggestion);
                            }
                        });
                    } else {
                        tagInputSearch.removeAll();
                    }

                    var queryPrLogDialog = $('#queryPrLog_Dialog').dialog({
                        title: '查询项目',
                        width: 400,
                        height: 300,
                        closable: false,
                        cache: false,
                        modal: true,
                        buttons: [{
                            iconCls: 'icon-ok',
                            text: '确定',
                            handler: function () {
                                if (dateCompare($('#selectSDate_prLog').datebox("getValue"), $('#selectEDate_prLog').datebox("getValue"))) {
                                    $.messager.alert('提示', "开始时间不能大于结束时间", 'info'); // 弹出提示信息
                                    return false;
                                }

                                var node = tagInputSearch.getValue();
                                var cnodes = '';
                                for(var i=0;i<node.length;i++){
                                    cnodes+=node[i]+',';
                                }
                                if(cnodes){
                                    cnodes = cnodes.substring(0,cnodes.length-1);
                                }

                                $('#pr_Log_datagrid').datagrid('load', {
                                    pname: $.trim($('#pname_prLog').val()),
                                    proRespId: cnodes,
                                    selectSDate: $('#selectSDate_prLog').datebox("getValue"),
                                    selectEDate: $('#selectEDate_prLog').datebox("getValue")
                                });
                                queryPrLogDialog.dialog('close');
                            }
                        }, {
                            iconCls: 'icon-cancel',
                            text: '取消',
                            handler: function () {
                                queryPrLogDialog.dialog('close');

                            }
                        }]
                    });

                }
            }
             <shiro:hasPermission name="project:Projectlog:addProjectLog" >, '-', {
                text: '新增',
                iconCls: 'icon-add',
                handler: function () {
                    $("#a").show();
                    $("#b").show();
                    $("#c").show();
                    $("#d").hide();

                    showPname($('#log_pname_add'));
                    $('#log_stage_add').combobox('setValue', "-1");
                    //当选择了项目  就会加载项目状态
                    $('#log_pname_add').combobox({
                        onSelect: function (record) {
                            showStage($('#log_stage_add'), record.id)
                        }
                    });
                    $('#log_sdate_add').datebox();
                    $('#log_sdate_add').datebox('setValue', formatterDate(new Date()));
                    $('#editor').empty();
                    $('#log_sdate_add').val("");
                    var addPr_Log = $('#addPr_Log').dialog({
                        title: '撰写日志',
                        width: 800,
                        height: '60%',
                        closable: false,
                        resizable:true,
                        cache: false,
                        modal: true,
                        buttons: [{
                            iconCls: 'icon-ok',
                            text: '确定',
                            handler: function () {
                                if ($('#log_pname_add').combobox("getValue") == -1) {
                                    $.messager.alert('提示', "必须选择项目名", 'info'); // 弹出提示信息
                                    return false;
                                }
                                if ($('#log_stage_add').combobox("getValue") == -1) {
                                    $.messager.alert('提示', "必须选择项目阶段", 'info'); // 弹出提示信息
                                    return false;
                                }
                                if ($('#editor').html() == "") {
                                    $.messager.alert('提示', "必须填写详细日志", 'info'); // 弹出提示信息
                                    return false;
                                }
                                if ($('#log_sdate_add').combobox("getValue") == "") {
                                    $.messager.alert('提示', "必须选择日报时间", 'info'); // 弹出提示信息
                                    return false;
                                }

                                if(!$.trim($("#editor").text())){
                                    $.messager.alert('提示', "日志内容不能为空", 'info'); // 弹出提示信息
                                    return false;
                                }

                                $.ajax({
                                    url: '/projectLog/addprLog',
                                    method: "POST",
                                    data: {
                                        pname: $("#log_pname_add").combobox("getText"),
                                        detail: $("#editor").text(),
                                        contents: $("#editor").html(),
                                        sdate: $('#log_sdate_add').datebox("getValue"),
                                        pflag: $("#log_stage_add").combobox("getValue"),
                                        pid: $("#log_pname_add").combobox("getValue")
                                    },
                                    cache: false,
                                    dataType: 'JSON',
                                    success: function (r) {
                                        if (r.success) {
                                            addPr_Log.dialog('close');
                                            $.messager.alert("提示信息", r.msg);
                                            $('#pr_Log_datagrid').datagrid('load', {});
                                            $('#log_stage_add').combobox("clear");
                                            $('#log_stage_add').combobox("loadData", [{
                                                "id": "-1",
                                                "text": "--请先选择项目"
                                            }]);
                                        } else {
                                            $.messager.alert("提示信息", r.msg);
                                        }

                                    }
                                });

                            }
                        }, {
                            iconCls: 'icon-cancel',
                            text: '取消',
                            handler: function () {
                                addPr_Log.dialog('close');
                                $('#log_stage_add').combobox("clear");
                                $('#log_stage_add').combobox("loadData", [{"id": "-1", "text": "--请先选择项目"}]);
                            }
                        }]
                    });
                }

            }</shiro:hasPermission>
             <shiro:hasPermission name="project:Projectlog:exportExcel" >, '-', {
                text: '导出excel',
                iconCls: 'icon-redo',
                handler: function () {
                    showPnameTree($('#export_pname'));
                    $('#export_pname').combotree('setValue',"");
                    $('#export_sdate').datebox();
                    $('#export_edate').datebox();
                    $('#export_pname').val("");
                    $('#export_sdate').val("");
                    $('#export_edate').val("");
                    $('#export_person').val("");
                    
                    if (!tagInputAdd) {
                        tagInputAdd = new TagsInput($('#export_person'));
                        tagInputAdd.input.autocomplete({
                            serviceUrl: '/userManager/autoCompleteUser',
                            type: "POST",
                            dataType: "json",
                            paramName: "keywords",
                            transformResult: function (response) {
                                for (var i in response) {
                                    response[i].value = response[i].quanpin;
                                }
                                return {suggestions: response};
                            },
                            formatResult: function (suggestion, currentValue) {
                                return suggestion.name;
                            },
                            onSelect: function (suggestion) {
                                tagInputAdd.addTag(suggestion);
                            }
                        });
                    } else {
                        tagInputAdd.removeAll();
                    }
                    
                    var export_Excel = $('#export_excel').dialog({
                        title: '导出excel',
                        width: 400,
                        height: 300,
                        closable: false,
                        cache: false,
                        modal: true,
                        buttons: [{
                            iconCls: 'icon-ok',
                            text: '确定',
                            handler: function () {
                            	var node = $('#export_pname').combotree('tree').tree('getChecked');
                            	var cnodes = '';
                            	for(var i=0;i<node.length;i++){
                            		cnodes+=node[i].id+',';
								}
                            	if(cnodes=="-1,"){
                            		cnodes="";
                            	}
                            	 
                            	 var form =  $('#export_excel_form').form({
            						url : '/export/createExcel',
            						onSubmit : function(param){
            							param.pids = cnodes;
            							param.updateIds = tagInputAdd.getValue();
            						}
            					});
            					form.submit(); 
            					export_Excel.dialog('close'); 
                            }
                        }, {
                            iconCls: 'icon-cancel',
                            text: '取消',
                            handler: function () {
                                export_Excel.dialog('close');
                            }
                        }]
                    });
                }

            }</shiro:hasPermission>]
        });

        $('#pr_Log_datagrid').datagrid({
            onDblClickCell: function (index, field, value) {
                $('#pr_Log_datagrid').datagrid('selectRecord', index);
                var rowdata = $('#pr_Log_datagrid').datagrid('getSelected');

                $.ajax({
                    type: "post",
                    url: '/projectLog/queryContent',
                    data: {
                        lid: rowdata.lid
                    },
                    cache: false,
                    dataType: 'json',
                    success: function (r) {
                        $('#contents_dialog').html(r.contents);
                        var contents_dialog = $('#contents_dialog').dialog({
                            title: '日志详细',
                            width: 600,
                            height: 300,
                            closable: false,
                            resizable:true,
                            cache: false,
                            modal: true,
                            buttons: [{
                                //iconCls: 'icon-ok',
                                text: '关闭',
                                handler: function () {
                                    contents_dialog.dialog('close');
                                }
                            }]
                        });

                    }
                });
            }
        });

        function deletePrLog(lid) {
            $("#pr_Log_datagrid").easyMask('show', {
                msg: "任务单删除中."
            });
            $.ajax({
                type: "post",
                url: '/projectLog/deleteprLog',
                data: {
                    lid: lid
                },
                cache: false,
                dataType: 'json',
                success: function (r) {
                    $("#pr_Log_datagrid").easyMask('hide');
                    $.messager.alert('提示', r.msg, 'info');
                }
            });
        }

        return {
            deleteLog:deleteLog,
            editLog:editLog
        }
    })();
</script>


<%----------------------------------------------------------------------------------%>

<table id="pr_Log_datagrid"></table>

<div id="queryPrLog_Dialog">
    <table align="center" style="padding-top: 10px;">
        <tr>
            <td align="right" style="height: 40px">项目名称：</td>
            <td><input id="pname_prLog"/></td>
        </tr>
        <tr>
            <td align="right" style="height: 40px">更新人：</td>
            <td><input id="person_prLog"/></td>
        </tr>
        <tr>
            <td align="right" style="height: 40px">开始时间：</td>
            <td><input id="selectSDate_prLog"/></td>
        </tr>
        <tr>
            <td align="right" style="height: 40px">终止时间：</td>
            <td><input id="selectEDate_prLog"/></td>
        </tr>
    </table>
</div>

<%--<div id="editPr_Log">--%>
<%--<table align="center" style="padding-top: 10px;">--%>
<%--<tr>--%>
<%--<td align="right">项目名称：</td><td><div id="log_pname_edit"  /></td>--%>
<%--</tr>--%>
<%--<tr>--%>
<%--<td align="right">日报内容：</td><td><textarea id="log_detail_edit" rows="10" cols="28"/> </td>--%>
<%--</tr>--%>
<%--</table>--%>
<%--</div>--%>

<div id="addPr_Log">
    <table align="left" style="padding-top: 10px;padding-left: 72px;">
        <tr><td><br/><td></tr>
        <tr id="a">
            <td align="right">项目名称：</td>
            <!-- <td><input id="log_pname_add" style="width: 150px;"/></td> -->
            <td><select name="log_pname_add" id="log_pname_add"
						class="easyui-combobox" style="width: 150px;"></select>
        </tr>
        <tr id="b">
            <td align="right">项目阶段：</td>
            <td><input style="width: 150px;" class="easyui-combobox" id='log_stage_add' data-options="
		valueField : 'id',
		textField : 'text',
		panelHeight : 'auto',
		editable : false,
		data: [{
			id: '-1',
			text: '--请先选择项目--'
		}
		]"/></td>
        </tr>
        <tr id="c">
            <td align="right">日报时间:</td>
            <td><input id="log_sdate_add"/></td>
        </tr>
        <tr id="d">
            <td align="right">项目名称：</td>
            <td>
                <div id="log_pname_edit"/>
            </td>
        </tr>
        <tr><td><br/><td></tr>
        <tr>
            <td align="right">日报内容：</td>
            <td>
                <div class="btn-toolbar" data-role="editor-toolbar"
                     data-target="#editor">
                    <div class="btn-group">
                        <a class="btn btn-default dropdown-toggle" data-toggle="dropdown" title="字体大小"><i
                                class="fa fa-text-height"></i>&nbsp;<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a data-edit="fontSize 5"><font size="5">大</font></a></li>
                            <li><a data-edit="fontSize 3"><font size="3">正常</font></a></li>
                            <li><a data-edit="fontSize 1"><font size="1">小</font></a></li>
                        </ul>
                    </div>
                    <div class="btn-group">
                        <a class="btn btn-default" data-edit="bold" title="加粗(Ctrl/Cmd+B)"><i class="fa fa-bold"></i></a>
                        <a class="btn btn-default" data-edit="italic" title="斜体 (Ctrl/Cmd+I)"><i
                                class="fa fa-italic"></i></a>
                        <a class="btn btn-default" data-edit="strikethrough" title="删除线"><i
                                class="fa fa-strikethrough"></i></a>
                        <a class="btn btn-default" data-edit="underline" title="下划线 (Ctrl/Cmd+U)"><i
                                class="fa fa-underline"></i></a>
                    </div>
                    <div class="btn-group">
                        <a class="btn btn-default" data-edit="insertunorderedlist" title="符号列表"><i
                                class="fa fa-list-ul"></i></a>
                        <a class="btn btn-default" data-edit="insertorderedlist" title="编号列表"><i
                                class="fa fa-list-ol"></i></a>
                        <a class="btn btn-default" data-edit="outdent" title="减少缩进 (Shift+Tab)"><i
                                class="fa fa-outdent"></i></a>
                        <a class="btn btn-default" data-edit="indent" title="缩进 (Tab)"><i class="fa fa-indent"></i></a>
                    </div>
                    <div class="btn-group">
                        <a class="btn btn-default" data-edit="justifyleft" title="左对齐 (Ctrl/Cmd+L)"><i
                                class="fa fa-align-left"></i></a>
                        <a class="btn btn-default" data-edit="justifycenter" title="居中 (Ctrl/Cmd+E)"><i
                                class="fa fa-align-center"></i></a>
                        <a class="btn btn-default" data-edit="justifyright" title="右对齐 (Ctrl/Cmd+R)"><i
                                class="fa fa-align-right"></i></a>
                        <a class="btn btn-default" data-edit="justifyfull" title="平铺 (Ctrl/Cmd+J)"><i
                                class="fa fa-align-justify"></i></a>
                    </div>
                    <div class="btn-group">
                        <a class="btn btn-default" data-edit="undo" title="撤销 (Ctrl/Cmd+Z)"><i class="fa fa-undo"></i></a>
                        <a class="btn btn-default" data-edit="redo" title="还原 (Ctrl/Cmd+Y)"><i
                                class="fa fa-repeat"></i></a>
                    </div>
                </div>
                <div id="editor" class="lead" placeholder="" style="width: 600px;"></div>
            </td>
        </tr>
    </table>
</div>

<div id="export_excel">
	<form id="export_excel_form" method="post">
	    <table align="center" style="padding-top: 10px;">
	        <tr>
	            <td align="right" style="height: 40px">项目名称：</td>
	            <td><input id="export_pname" name="export_pname" style="width: 156px;"/></td>
	            <!-- <td><select name="export_pname" id="export_pname"
						class="easyui-combobox" style="width: 156px;"></select></td> -->
	        </tr>
	        <tr>
	            <td align="right" style="height: 40px">更新人：</td>
	            <td><input id="export_person" name="export_person" style="width: 156px;"/></td>
	        </tr>
	        <tr>
	            <td align="right" style="height: 40px">日报时间：</td>
	            <td><input id="export_sdate" name="export_sdate" style="width: 145px;"/>-
	            <input id="export_edate" name="export_edate" style="width: 145px;"/></td>
	        </tr>
	        <!-- <tr>
	            <td align="right" style="height: 40px">终止时间：</td>
	            <td><input id="export_edate" name="export_edate" style="width: 156px;"/></td>
	        </tr> -->
	    </table>
    </form>
</div>


<div id="contents_dialog" style="width: 600px; height: 300px;">
</div>



