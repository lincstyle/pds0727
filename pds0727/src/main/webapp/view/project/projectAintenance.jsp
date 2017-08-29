<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<link rel="stylesheet" href="<%=basePath%>static/js/jquery.multiselect2side/css/jquery.multiselect2side.css"
      type="text/css"/>
<script src="<%=basePath%>static/js/jquery.multiselect2side/js/jquery.multiselect2side.js" type="text/javascript"
        charset="utf-8"></script>

<link rel="stylesheet" href="/static/js/jquery-autocomplete/styles.css" type="text/css"/>
<script src="/static/js/jquery-autocomplete/jquery.autocomplete.js" type="text/javascript" charset="utf-8"></script>

<link rel="stylesheet" href="/static/js/tagsinput/tagsinput.css" type="text/css"/>
<script src="/static/js/tagsinput/tagsinput.js" type="text/javascript" charset="utf-8"></script>

<script>
    app.project_Aintenance = function () {

        var tagInputAdd, tagInputEdit,tagInputFind;

        $('#project_datagrid').datagrid({
            url: '/projectManage/queryProject',
            pagination: true,
            pageSize: 10,
            pageList: [5, 10, 15, 20, 25, 30, 35],
            fit: true,//datagrid自适应宽高
            fitColumns: true,
            nowrap: false,
            singleSelect: true,
            rownumbers: true,
            columns: [[{
                field: 'pname',
                title: '项目名',
                width: 50
            }, {
                field: 'pflag',
                title: '项目阶段',
                width: 50,
                hidden: true
            }, {
                field: 'pstage',
                title: '项目阶段',
                width: 50
            }, {
                field: 'pintro',
                title: '基本介绍',
                width: 120
            }, {
                field: 'person',
                title: '立项人',
                width: 50
            }, {
                field: 'pdate',
                title: '立项时间',
                width: 50
            }, {
                field: 'lastPerson',
                title: '最后更新人',
                width: 50
            }, {
                field: 'lastDate',
                title: '最后更新时间',
                width: 50
            }
            <shiro:hasPermission name="project:ProjectAintenance:projectCode" >, {
                field: 'opt',
                title: '操作',
                width: 50,
                formatter: function (value, row, index) {
                    return "<button onclick=projectCode('" + row.pid + "')>项目阶段维护</button> ";
                }
            }</shiro:hasPermission>
            , {
                field: 'isdelete',
                title: '是否删除',
                width: 100,
                hidden: true
            }, {
                field: 'pid',
                title: '项目工程ID',
                width: 100,
                hidden: true
            }]],
            toolbar: [{
                text: '查询',
                iconCls: 'icon-search',
                handler: function () {
                    $('#selectSDate_project').datebox();
                    $('#selectEDate_project').datebox();
                    showPname($('#pname_project'));
                    showUsername($('#person_project'), 0);
                    $('#pname_project').val("");
                    $('#person_project').val("");
                    $('#selectSDate_project').val("");
                    $('#selectEDate_project').val("");

                    var queryProjectDialog = $('#queryProject_Dialog').dialog({
                        title: '查询项目',
                        width: 500,
                        height: 300,
                        closable: false,
                        cache: false,
                        modal: true,
                        buttons: [{
                            iconCls: 'icon-ok',
                            text: '确定',
                            handler: function () {
                                if (dateCompare($('#selectSDate_project').datebox("getValue"), $('#selectEDate_project').datebox("getValue"))) {
                                    $.messager.alert('提示', "开始时间不能大于结束时间", 'info'); // 弹出提示信息
                                    return false;
                                }
                                $('#project_datagrid').datagrid('load', {
                                    pname: $('#pname_project').combobox("getText").replace("--请选择",""),
                                    person: $('#person_project').combobox("getText").replace("--请选择",""),
                                    "pdate_start": $('#selectSDate_project').datebox("getValue"),
                                    "pdate_end": $('#selectEDate_project').datebox("getValue")
                                });
                                queryProjectDialog.dialog('close');
                            }
                        }, {
                            iconCls: 'icon-cancel',
                            text: '取消',
                            handler: function () {
                                queryProjectDialog.dialog('close');
                            }
                        }]
                    });

                }
            }<shiro:hasPermission name="project:ProjectAintenance:editProject" >, '-', {
                text: '编辑',
                iconCls: 'icon-edit',
                handler: function () {
                    var rowData = $('#project_datagrid').datagrid('getSelected');
                    if (rowData != null) {
                        $('#pname_edit').val(rowData.pname);
                        $('#person_edit').html(rowData.person);
                        showStage2($('#pstage_edit'), rowData.pid, rowData.pflag);
                        $('#pintro_edit').val(rowData.pintro);


                        if (!tagInputEdit) {
                            tagInputEdit = new TagsInput($('#fuzeperson_edit'));
                            tagInputEdit.input.autocomplete({
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
                                    tagInputEdit.addTag(suggestion);
                                }
                            });
                        } else {
                            tagInputEdit.removeAll();
                        }

                        showIsFuZeRen(rowData.pid, function (data) {
                            $.each(data, function (i, v) {
                                tagInputEdit.addTag({
                                    id: v.userid,
                                    name: v.username
                                });
                            });
                        });

                        var editProjectDialog = $('#editProject').dialog({
                            title: '编辑项目',
                            width: 500,
                            height: 400,
                            closable: false,
                            cache: false,
                            modal: true,
                            buttons: [{
                                iconCls: 'icon-ok',
                                text: '确定',
                                handler: function () {
                                    if ($('#pname_edit').val() == "") {
                                        $.messager.alert('提示', "项目名不能为空", 'info'); // 弹出提示信息
                                        return false;
                                    }
                                    if (tagInputEdit.getValue().length < 1) {
                                        $.messager.alert('提示', "项目负责人不能为空", 'info'); // 弹出提示信息
                                        return false;
                                    }
                                    $.ajax({
                                        type: "post",
                                        url: '/projectManage/editproject',
                                        data: {
                                            pname: $('#pname_edit').val(),
                                            person: $('#person_edit').val(),
                                            pflag: $('#pstage_edit').combobox("getValue"),
                                            pintro: $('#pintro_edit').val(),
                                            oldpname: rowData.pname,
                                            fuzeId: tagInputEdit.getValue(),
                                            pid: rowData.pid
                                        },
                                        cache: false,
                                        dataType: 'json',
                                        success: function (r) {
                                            if (r.success) {
                                                editProjectDialog.dialog('close');
                                                $.messager.alert("提示信息", r.msg);
                                                $('#project_datagrid').datagrid('load', {});
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
                                    editProjectDialog.dialog('close');
                                }
                            }]
                        });

                    } else {
                        $.messager.alert('提示', '请先选中一行记录！', 'info');
                        return false;
                    }
                }
            }</shiro:hasPermission>
                <shiro:hasPermission name="project:ProjectAintenance:addProject" >, '-', {
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        $('#pname_add').val("");
                        $('#pintro_add').val("");
                        $('#fuzeperson_add').val("");

                        if (!tagInputAdd) {
                            tagInputAdd = new TagsInput($('#fuzeperson_add'));
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

                        var addProjectDialog = $('#addProject').dialog({
                            title: '添加项目',
                            width: 500,
                            height: 400,
                            closable: false,
                            cache: false,
                            modal: true,
                            buttons: [{
                                iconCls: 'icon-ok',
                                text: '确定',
                                handler: function () {
                                    if ($('#pname_add').val() == "") {
                                        $.messager.alert('提示', "项目名不能为空", 'info'); // 弹出提示信息
                                        return false;
                                    }
                                    if ($('#pintro_add').val() == "") {
                                        $.messager.alert('提示', "项目介绍不能为空", 'info'); // 弹出提示信息
                                        return false;
                                    }
                                    if (tagInputAdd.getValue().length < 1) {
                                        $.messager.alert('提示', "项目负责人不能为空", 'info'); // 弹出提示信息
                                        return false;
                                    }
                                    $.ajax({
                                        type: "post",
                                        url: '/projectManage/addproject',
                                        data: {
                                            pname: $('#pname_add').val(),
                                            pintro: $('#pintro_add').val(),
                                            fuzeId: tagInputAdd.getValue()
                                        },
                                        cache: false,
                                        dataType: 'json',
                                        success: function (r) {
                                            if (r.success) {
                                                addProjectDialog.dialog('close');
                                                $.messager.alert("提示信息", r.msg);
                                                $('#project_datagrid').datagrid('load', {});
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
                                    $("input").val("");
                                    addProjectDialog.dialog('close');
                                }
                            }]
                        });
                    }

                }</shiro:hasPermission>
                <shiro:hasPermission name="project:ProjectAintenance:removeProject" >, '-', {
                    text: '删除',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        var rowData = $('#project_datagrid').datagrid('getSelected');
                        if (rowData != null) {
                            $.messager.confirm('确认', '是否删除该项目', function (r) {
                                if (r) {
                                    deleteProject(rowData.pid);
                                    $('#project_datagrid').datagrid('load', {});
                                }
                            });
                            return false;
                        } else {
                            $.messager.alert('提示', '请先选中一个项目！', 'info');
                            return false;
                        }
                    }

                }</shiro:hasPermission>
                <shiro:hasPermission name="project:ProjectAintenance:projectStaff" >, '-', {
                    text: '参与项目人员',
                    iconCls: 'icon-man',
                    handler: function () {
                        var rowData = $('#project_datagrid').datagrid('getSelected');
                        if (rowData != null) {
                            projectuser(rowData.pid, rowData.pname);
                        } else {
                            $.messager.alert('提示', '请先选中一个项目！', 'info');
                            return false;
                        }

                    }

                }</shiro:hasPermission>]
        });
    };
    app.project_Aintenance();

    function deleteProject(pid) {
        $("#project_datagrid").easyMask('show', {
            msg: "项目删除中"
        });
        $.ajax({
            type: "post",
            url: '/projectManage/deleteproject',
            data: {
                pid: pid
            },
            cache: false,
            dataType: 'json',
            success: function (r) {
                $("#project_datagrid").easyMask('hide');
                $.messager.alert('提示', r.msg, 'info');
            }
        });
    }


    function projectuser(pid, pname) {
        var Project_user_datagrid = $("#prUser_datagrid").datagrid({
            url: '/projectManage/queryPrUser',
            queryParams:{pid:pid},
            pagination: true,
            pageSize: 10,
            pageList: [5, 10, 15, 20, 25, 30, 35],
            fit: true,//datagrid自适应宽高
            fitColumns: true,
            nowrap: false,
            rownumbers: true,
            columns: [[{
                //    field : 'bid',
                //    checkbox : true,
                //    sortable:true
                //}, {
                field: 'pname',
                title: '项目名',
                width: 80,
                hidden: true
            }, {
                field: 'username',
                title: '人员名',
                width: 80
            }, {
                field: 'pid',
                title: '项目Id',
                width: 80,
                hidden: true
            }, {
                field: 'userid',
                title: '人员id',
                width: 80,
                hidden: true
            }, {
                field: 'puid',
                title: '记录',
                width: 80,
                hidden: true
            }, {
                field: 'isResponse',
                title: '是否为负责人',
                width: 80,
                formatter: function (value, row, index) {
                    var checkedAttr = "";
                    if (row.isResponse == 1) {
                        checkedAttr = 'checked="checked"';
                    }
                    return "<input id='isResponse" + row.puid + "' type='checkbox' " + checkedAttr + " onclick = isResponse('" + row.puid + "','" + row.pid + "')  />";
                }
            }

            ]],
            toolbar: [{
                text: '查询',
                iconCls: 'icon-search',
                handler: function () {
                    $('#person_project_user').val("");
                    var queryPrUser_Dialog = $('#queryPruser_Dialog').dialog({
                        title: '查询条件',
                        width: 300,
                        height: 150,
                        closable: false,
                        cache: false,
                        modal: true,
                        buttons: [{
                            iconCls: 'icon-ok',
                            text: '确定',
                            handler: function () {
                                Project_user_datagrid.datagrid('load', {
                                    user: $('#person_project_user').val(),
                                    pid: pid
                                });
                                queryPrUser_Dialog.dialog('close');
                            }
                        }, {
                            iconCls: 'icon-cancel',
                            text: '取消',
                            handler: function () {
                                queryPrUser_Dialog.dialog('close');
                            }
                        }]
                    });
                }
            }, {
                text: '添加',
                iconCls: 'icon-add',
                handler: function () {
                    showUsername($('#add_pruser'), pid);
                    $('#add_pruser').val("");
                    var addPrUser_Dialog = $('#addPruser_Dialog').dialog({
                        title: '添加项目人员',
                        width: 300,
                        height: 150,
                        closable: false,
                        cache: false,
                        modal: true,
                        buttons: [{
                            iconCls: 'icon-ok',
                            text: '确定',
                            handler: function () {
                                var addUser = $('#add_pruser').combobox("getValue");
                                var addUserName =  $('#add_pruser').combobox("getText");
                                if (!addUser || addUser == "-1") {
                                    $.messager.alert('提示', "请选择人员", 'info'); // 弹出提示信息
                                    return false;
                                }
                                if (addUser == addUserName) {
                                    $.messager.alert('提示', "请选择下拉列表中存在的项，不要自己输入", 'info'); // 弹出提示信息
                                    return false;
                                }

                                $.ajax({
                                    type: "post",
                                    url: '/projectManage/addprUser',
                                    data: {
                                        pname: pname,
                                        pid: pid,
                                        userid: addUser,
                                        username: $('#add_pruser').combobox("getText")
                                    },
                                    cache: false,
                                    dataType: 'json',
                                    success: function (r) {
                                        if (r.success) {
                                            addPrUser_Dialog.dialog('close');
                                            $.messager.alert("提示信息", r.msg);
                                            Project_user_datagrid.datagrid('load', {
                                                pid: pid
                                            });
                                            ;
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
                                addPrUser_Dialog.dialog('close');
                            }
                        }]
                    });
                }
            }, {
                text: '删除',
                iconCls: 'icon-cancel',
                handler: function () {

                    var rowDatas = $('#prUser_datagrid ').datagrid('getSelections');
                    if (rowDatas.length > 0) {
                        var i = 0;
                        var puid_array = new Array;
                        for (i = 0; i < rowDatas.length; i++) {
                            puid_array.push(rowDatas[i].puid);

                        }

                        $.messager.confirm('确认', '是否删除该记录', function (r) {
                            if (r) {
                                deleteProjectUser(puid_array);
                                Project_user_datagrid.datagrid('load', {
                                    pid: pid
                                });
                            }
                        });
                        return false;
                    } else {
                        $.messager.alert('提示', '请先选中至少一条记录！', 'info');
                        return false;
                    }

                }
            }]

        });

        var prUser_dialog = $('#prUser_dialog').show().dialog(
                {
                    title: '项目参与人员列表',
                    width: 350,
                    height: 400,
                    closable: true,
                    cache: false,
                    modal: true,
                    buttons: [{
                        iconCls: 'icon-cancel',
                        text: '取消',
                        handler: function () {
                            prUser_dialog.dialog("close");
                        }
                    }]
                });
    }

    function isResponse(puid, pid) {
        var puidArr = new Array();
        var isResponse = $("#isResponse" + puid).prop('checked');
        puidArr[0] = puid;

        $.ajax({
            type: 'POST',
            url: '/personalInfoManager/editIsResponse',
            data: {
                puid: puidArr,
                response: isResponse
            },
            dataType: 'json',
            success: function (r) {
                $("#prUser_datagrid").datagrid('load', {
                    pid: pid
                });
            },
            error: function (result) {
                $.messager.alert('错误信息', '修改失败,出现未知错误,请联系超级管理员!', 'error');
                $("#isResponse").prop('checked', false);
            }
        });
    }


    function deleteProjectUser(puarray) {
        $("#prUser_datagrid").easyMask('show', {
            msg: "记录删除中"
        });
        $.ajax({
            type: "post",
            url: '/projectManage/deleteprUser',
            data: {
                puid_array: puarray
            },
            cache: false,
            dataType: 'json',
            success: function (r) {
                $("#prUser_datagrid").easyMask('hide');
                $.messager.alert('提示', r.msg, 'info');
            }
        });
    }

    function deleteProjectCode(cidarray) {
        $("#prcode_datagrid").easyMask('show', {
            msg: "记录删除中"
        });
        $.ajax({
            type: "post",
            url: '/codeManager/deleteprCode',
            data: {
                cid_array: cidarray
            },
            cache: false,
            dataType: 'json',
            success: function (r) {
                $("#prcode_datagrid").easyMask('hide');
                $.messager.alert('提示', r.msg, 'info');
            }
        });
    }


    function projectCode(pid) {
        $.ajax
        ({
            type: "post",// 不写此参数默认为get方式提交
            url: '/stagecode/queryAllStage',
            data: {
                pid: pid
            },
            cache: false,
            dataType: 'json',
            success: function (r) {
                if (document.getElementById('add_code') == undefined) {
                    $('#addcode_Dialog').append("<table id='ms2table'> <tr><td> <select name='add_code' id='add_code' multiple='multiple' >" + r.option + "</select></td></tr></table>");
                    $('#add_code').multiselect2side({});
                    $('.ms2side__updown').remove();
                }

                var addPrcodeDialog = $('#addcode_Dialog').dialog({
                    title: '请选择需要添加的阶段',
                    width: 420,
                    height: 330,
                    cache: false,
                    closable: false,
                    modal: true,
                    buttons: [{
                        iconCls: 'icon-ok',
                        text: '确定',
                        handler: function () {
                            if ($("#add_codems2side__dx option").length == 0) {
                                $.messager.alert("提示", "请至少选择一个项目阶段", "info");
                                return false;
                            }
                            add_code(pid);
                        }
                    }, {
                        iconCls: 'icon-cancel',
                        text: '取消',
                        handler: function () {
                            $("#add_codems2side__dx").find("option").remove();
                            $("#ms2table").remove();
                            addPrcodeDialog.dialog('close');
                        }
                    }]
                });
            }
        });

    }


    function add_code(pid) {
        var selected = $("#add_codems2side__dx option");
        var p = new Array();

        for (var i = 0, len = selected.length; i < len; i++) {
            p.push(selected[i].value);//项目最终的阶段数组
        }

        /*
            检查此项目所处的阶段是否被删除, 如有则禁止删除  只有两个参数: 项目ID、项目最终阶段的ID集合
            1.如果项目有阶段状态, 则必须在数组里面有此状态,否则提示用户不能删除
            2.如果此项目日志有此状态的日志,则必须也在数组里面,否则提示用户不能删除
         */
        $.ajax({
            url: '/projectManage/changeProjectPflag',
            data: {
                stage: p,
                pid: pid
            },
            type: "post",
            cache: false,
            async: false,
            dataType: 'json',
            success: function (result) {
                if(!result.success){
                    $.messager.alert('错误提示', result.msg, 'error');//弹出提示信息
                    return false;
                } else {
                    $("#add_codems2side__dx").children().appendTo($("#add_codems2side__sx"));
                    $("#add_codems2side__dx").children().remove();
                    $("#add_code").find('option').removeAttr("selected");
                    $(".add_code_mask").easyMask('show', {
                        msg: "该项目阶段提交中，请稍候......"
                    });
                    $.ajax({
                        type: "post",
                        url: '/codeManager/addProjectCode',
                        data: {
                            stage: p,
                            pid: pid
                        },
                        cache: false,
                        dataType: 'json',
                        success: function (r) {
                            $(".add_code_mask").easyMask('hide');
                            $.messager.alert('提示', r.msg, 'info');//弹出提示信息
                            $("#add_codems2side__dx").find("option").remove();
                            $("#ms2table").remove();
                            $("#addcode_Dialog").dialog('close');
                        }
                    });
                }
            }
        });

    }

</script>

<%----------------------------------------------------------------------------%>

<table id="project_datagrid"></table>

<div id="queryProject_Dialog">
    <table align="center" style="padding-top: 10px;">
        <tr>
            <td><br/>
            <td>
        </tr>
        <tr>
            <td align="right">项目名称：</td>
            <td><input id="pname_project"/></td>
        </tr>
        <tr>
            <td><br/>
            <td>
        </tr>
        <tr>
            <td align="right">立项人：</td>
            <td><input id="person_project"/></td>
        </tr>
        <tr>
            <td><br/>
            <td>
        </tr>
        <tr>
            <td align="right">开始时间：</td>
            <td><input id="selectSDate_project"/></td>
        </tr>
        <tr>
            <td><br/>
            <td>
        </tr>
        <tr>
            <td align="right">终止时间：</td>
            <td><input id="selectEDate_project"/></td>
        </tr>
    </table>
</div>

<div id="editProject">
    <table align="center" style="padding-top: 10px;">
        <tr>
            <td><br/>
            <td>
        </tr>
        <tr>
            <td align="right">项目名称：</td>
            <td><input id="pname_edit"/></td>
        </tr>
        <tr>
            <td><br/>
            <td>
        </tr>
        <tr>
            <td align="right">立项人：</td>
            <td>
                <div id="person_edit"/>
            </td>
        </tr>
        <tr>
            <td><br/>
            <td>
        </tr>
        <tr>
            <td align="right">项目阶段:</td>
            <td><input id="pstage_edit"/></td>
        </tr>
        <tr>
            <td><br/>
            <td>
        </tr>
        <tr>
            <td align="right">项目负责人:</td>
            <td><input id="fuzeperson_edit"/></td>
        </tr>
        <tr>
            <td><br/>
            <td>
        </tr>
        <tr>
            <td align="right">项目介绍:</td>
            <td><textarea id="pintro_edit" cols="20" rows="3"/></td>
        </tr>
    </table>
</div>

<div id="addProject">
    <table align="center" style="padding-top: 10px;">
        <tr>
            <td><br/>
            <td>
        </tr>
        <tr>
            <td align="right">项目名称：</td>
            <td><input id="pname_add" class="easyui-validatebox" data-options="required:true" type='text'/><span
                    id="valierr1" style="color:#FF9966">*</span></td>
        </tr>
        <tr>
            <td><br/>
            <td>
        </tr>
        <tr>
            <td align="right">项目简介：</td>
            <td><textarea id="pintro_add" cols="20" rows="3" class="easyui-validatebox" data-options="required:true"
                          type='text'/><span id="valierr2" style="color:#FF9966">*</span></td>
        </tr>
        <tr>
            <td><br/>
            <td>
        </tr>
        <tr>
            <td align="right">项目负责人：</td>
            <td>
                <input id="fuzeperson_add"/>
                <span id="valierr3" style="color:#FF9966">*</span>
            </td>
        </tr>
    </table>
</div>


<%--//人员项目关系表格--%>
<div id="prUser_dialog">
    <table id="prUser_datagrid"></table>
</div>

<%--//查询人员项目关系--%>
<div id="queryPruser_Dialog">
    <table align="center" style="padding-top: 10px;">
        <tr>
            <td align="right">人员：</td>
            <td><input id="person_project_user"/></td>
        </tr>
    </table>
</div>

<%--// 添加人员项目关系--%>
<div id="addPruser_Dialog">
    <table align="center" style="padding-top: 10px;">
        <tr>
            <td align="right">人员：</td>
            <td><input id="add_pruser"/></td>
        </tr>
    </table>
</div>


<div id="prcode_dialog">
    <table id="prcode_datagrid"></table>
</div>


<%--//添加项目阶段编码--%>
<div id="addcode_Dialog" style="padding-top: 10px;" class="add_code_mask">
    <!-- 入库单列表 -->
</div>






