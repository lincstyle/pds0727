app.project_Aintenance=function(){
    $('#project_datagrid').datagrid( {
        url : '/projectManage/queryProject',
        pagination : true,
        pageSize : 10,
        pageList : [ 5, 10, 15, 20, 25, 30, 35],
        fit : true,//datagrid自适应宽高
        fitColumns:true,
        nowrap:false,
        singleSelect : true,
        rownumbers:true,
        columns : [ [ {
        //    field : 'bid',
        //    checkbox : true,
        //    sortable:true
        //}, {
            field : 'pname',
            title : '项目名',
            width : 50
        },{
            field : 'pflag',
            title : '项目阶段',
            width : 50,
            hidden:true
        }, {
            field : 'pstage',
            title : '项目阶段',
            width : 50
        },
        {
            field : 'pintro',
            title : '基本介绍',
            width : 120
        },{
            field : 'fuzeperson',
            title : '项目负责人',
            width : 50
        }, {
            field : 'person',
            title : '立项人',
            width : 50
        },{
            field : 'pdate',
            title : '立项时间',
            width : 50
        },{
            field : 'lastPerson',
            title : '最后更新人',
            width : 50
        },{
            field : 'lastDate',
            title : '最后更新时间',
            width : 50
        },{
                field : 'opt',
                title : '操作',
                width:   50,
                formatter : function(value, row, index)
                {
                    return "<button onclick=projectCode('"+row.pid+"')>项目阶段维护</button>";
                }
            }
            , {
            field : 'isdelete',
            title : '是否删除',
            width : 100,
            hidden:true
        },{
            field : 'pid',
            title : '工程ID',
            width : 100,
            hidden:true
        }] ],
        toolbar: [
        {
            text : '查询',
            iconCls: 'icon-search',
            handler: function()
            {
                $('#selectSDate_project').datebox();
                $('#selectEDate_project').datebox();
                showPname($('#pname_project'));
                showUsername($('#person_project'));
                $('#pname_project').val("");
                $('#person_project').val("");
                $('#selectSDate_project').val("");
                $('#selectEDate_project').val("");
                var queryProjectDialog= $('#queryProject_Dialog').dialog({
                    title : '查询项目',
                    width: 300,
                    height: 300,
                    closable: false,
                    cache: false,
                    modal: true,
                    buttons:[{
                        iconCls: 'icon-ok',
                        text:'确定',
                        handler:function()
                        {
                            if(dateCompare($('#selectSDate_project').datebox("getValue"),$('#selectEDate_project').datebox("getValue")))
                            {
                                $.messager.alert('提示', "开始时间不能大于结束时间", 'info'); // 弹出提示信息
                                return false;
                            }
                                $('#project_datagrid').datagrid('load',{
                                    pid: $('#pname_project').combobox("getValue"),
                                    person: $('#person_project').combobox("getText"),
                                    selectSDate:$('#selectSDate_project').datebox("getValue"),
                                    selectEDate:$('#selectEDate_project').datebox("getValue")
                                });
                                queryProjectDialog.dialog('close');
                        }
                    },{
                        iconCls: 'icon-cancel',
                        text:'取消',
                        handler:function(){
                            queryProjectDialog.dialog('close');
                        }
                    }]});

            }
        },'-',{
            text : '编辑',
            iconCls : 'icon-edit',
            handler: function()
            {
                var rowData = $('#project_datagrid').datagrid('getSelected');
                if(rowData != null){
                    $('#pname_edit').val(rowData.pname);
           //         $('#pname_edit').attr('readonly', true);
                    $('#person_edit').val(rowData.person);
                    showStage($('#pstage_edit'),rowData.pid);
                    $('#fuzeperson_edit').val(rowData.fuzeperson);
                    $('#pintro_edit').val(rowData.pintro);

                    var editProjectDialog= $('#editProject').dialog({
                        title : '编辑项目',
                        width: 300,
                        height: 300,
                        closable: false,
                        cache: false,
                        modal: true,
                        buttons:[{
                            iconCls: 'icon-ok',
                            text:'确定',
                            handler:function()
                            {
                                if ($('#pname_edit').val() == "") {
                                    $.messager.alert('提示', "项目名不能为空", 'info'); // 弹出提示信息
                                    return false;
                                }
                                if ($('#person_edit').val() == "") {
                                $.messager.alert('提示', "立项人人不能为空", 'info'); // 弹出提示信息
                                return false;
                                }
                                if ($('#fuzeperson_edit').val() == "") {
                                    $.messager.alert('提示', "负责人不能为空", 'info'); // 弹出提示信息
                                    return false;
                                }

                                $.ajax({
                                    type:"post",
                                    url :'/projectManage/editproject',
                                    data:{
                                        pname:$('#pname_edit').val(),
                                        person:$('#person_edit').val(),
                                        fuzeperson:$('#fuzeperson_edit').val(),
                                        pflag:$('#pstage_edit').combobox("getValue"),
                                        pintro:$('#pintro_edit').val(),
                                        oldpname:rowData.pname
                                    },
                                    cache :false,
                                    dataType :'json',
                                    success : function(r)
                                    {
                                        if(r.success)
                                        {
                                            editProjectDialog.dialog('close');
                                            $.messager.alert("提示信息" ,r.msg);
                                            $('#project_datagrid').datagrid('load',{});
                                        }else
                                        {
                                            $.messager.alert("提示信息" ,r.msg);
                                        }
                                    }
                                });

                            }
                        },{
                            iconCls: 'icon-cancel',
                            text:'取消',
                            handler:function(){
                                editProjectDialog.dialog('close');
                            }
                        }]});

                }else{
                    $.messager.alert('提示','请先选中一行记录！','info');
                    return false;
                }


            }

        },'-',{
            text : '新增',
            iconCls : 'icon-add',
            handler : function ()
            {
                $('#pname_add').val("");
                $('#pintro_add').val("");
                $('#fuzeperson_add').val("");

                var addProjectDialog= $('#addProject').dialog({
                    title : '添加工程',
                    width: 300,
                    height: 300,
                    closable: false,
                    cache: false,
                    modal: true,
                    buttons:[{
                        iconCls: 'icon-ok',
                        text:'确定',
                        handler:function()
                        {
                            if ($('#pname_add').val() == "") {
                                $.messager.alert('提示', "项目名不能为空", 'info'); // 弹出提示信息
                                return false;
                            }
                            if ($('#pintro_add').val() == "") {
                                $.messager.alert('提示', "项目介绍不能为空", 'info'); // 弹出提示信息
                                return false;
                            }
                            if ($('#fuzeperson_add').val() == "") {
                                $.messager.alert('提示', "项目负责人不能为空", 'info'); // 弹出提示信息
                                return false;
                            }
                            $.ajax({
                                type:"post",
                                url :'/projectManage/addproject',
                                data:{
                                    pname:$('#pname_add').val(),
                                    pintro:$('#pintro_add').val(),
                                    fuzeperson:$('#fuzeperson_add').val()
                                },
                                cache :false,
                                dataType :'json',
                                success : function(r)
                                {
                                    if(r.success)
                                    {
                                        addProjectDialog.dialog('close');
                                        $.messager.alert("提示信息" ,r.msg);
                                        $('#project_datagrid').datagrid('load',{});
                                    }else
                                    {
                                        $.messager.alert("提示信息" ,r.msg);
                                    }

                                }
                            });

                        }
                    },{
                        iconCls: 'icon-cancel',
                        text:'取消',
                        handler:function(){
                            addProjectDialog.dialog('close');
                        }
                    }]});
            }

        },'-',{
            text : '删除',
            iconCls : 'icon-cancel',
            handler : function ()
            {
                var rowData = $('#project_datagrid').datagrid('getSelected');
                if(rowData != null)
                {
                    $.messager.confirm('确认', '是否删除该项目', function (r) {
                        if (r)
                        {
                            deleteProject(rowData.pname);
                            $('#project_datagrid').datagrid('load',{});
                        }
                    });
                    return false;
                }else
                {
                    $.messager.alert('提示','请先选中一个项目！','info');
                    return false;
                }
            }

        },'-',{
            text : '参与项目人员',
            iconCls : 'icon-man',
            handler : function ()
            {
                var rowData = $('#project_datagrid').datagrid('getSelected');
                if(rowData != null)
                {
                    projectuser(rowData.pid,rowData.pname);
                }else
                {
                    $.messager.alert('提示','请先选中一个项目！','info');
                    return false;
                }

            }

        }]
    });
};
app.project_Aintenance();

function deleteProject(pname)
{
    $("#project_datagrid").easyMask('show', {
        msg : "项目删除中"
    });
    $.ajax({
        type : "post",
        url : '/projectManage/deleteproject',
        data : {
            pname : pname
        },
        cache : false,
        dataType : 'json',
        success : function(r) {
            $("#project_datagrid").easyMask('hide');
            $.messager.alert('提示', r.msg, 'info');
        }
    });
}


function projectuser(pid,pname)
{
    var Project_user_datagrid=$("#prUser_datagrid").datagrid({
        url : '/projectManage/queryPrUser',
        pagination : true,
        pageSize : 10,
        pageList : [ 5, 10, 15, 20, 25, 30, 35],
        fit : true,//datagrid自适应宽高
        fitColumns:true,
        nowrap:false,
        rownumbers:true,
        columns : [ [ {
            //    field : 'bid',
            //    checkbox : true,
            //    sortable:true
            //}, {
            field : 'pname',
            title : '项目名',
            width : 80,
            hidden:true
        },{
            field : 'username',
            title : '人员名',
            width : 80
        },{
            field : 'pid',
            title : '工程id',
            width : 80,
            hidden:true
        },{
            field : 'userid',
            title : '人员id',
            width : 80,
            hidden:true
        },{
            field:'puid',
            title:'记录',
            width:80,
            hidden:true

        }

        ]],
        toolbar:[{
            text : '查询',
            iconCls: 'icon-search',
            handler: function()
            {
                $('#person_project_user').val("");
                var queryPrUser_Dialog= $('#queryPruser_Dialog').dialog({
                    title : '查询条件',
                    width: 300,
                    height: 150,
                    closable: false,
                    cache: false,
                    modal: true,
                    buttons:[{
                        iconCls: 'icon-ok',
                        text:'确定',
                        handler:function()
                        {
                            Project_user_datagrid.datagrid('load',{
                                user: $('#person_project_user').val(),
                                pid:pid
                            });
                            queryPrUser_Dialog.dialog('close');
                        }
                    },{
                        iconCls: 'icon-cancel',
                        text:'取消',
                        handler:function(){
                            queryPrUser_Dialog.dialog('close');
                        }
                    }]});
            }
        },{
            text : '添加',
            iconCls: 'icon-add',
            handler: function()
            {
                showUsername($('#add_pruser'));
                $('#add_pruser').val("");
                var addPrUser_Dialog= $('#addPruser_Dialog').dialog({
                    title : '添加项目',
                    width: 300,
                    height: 150,
                    closable: false,
                    cache: false,
                    modal: true,
                    buttons:[{
                        iconCls: 'icon-ok',
                        text:'确定',
                        handler:function()
                        {
                            if ($('#add_pruser').combobox("getValue") == -1) {
                                $.messager.alert('提示', "请选择人员", 'info'); // 弹出提示信息
                                return false;
                            }

                            $.ajax({
                                type:"post",
                                url :'/projectManage/addprUser',
                                data:{
                                    pname:pname,
                                    pid:pid,
                                    userid:$('#add_pruser').combobox("getValue"),
                                    username:$('#add_pruser').combobox("getText")
                                },
                                cache :false,
                                dataType :'json',
                                success : function(r)
                                {
                                    if(r.success)
                                    {
                                        addPrUser_Dialog.dialog('close');
                                        $.messager.alert("提示信息" ,r.msg);
                                        Project_user_datagrid.datagrid('load',{
                                            pid: pid
                                        });;
                                    }else
                                    {
                                        $.messager.alert("提示信息" ,r.msg);
                                    }

                                }
                            });
                        }
                    },{
                        iconCls: 'icon-cancel',
                        text:'取消',
                        handler:function()
                        {
                            addPrUser_Dialog.dialog('close');
                        }
                    }]});
            }
        },{
            text : '删除',
            iconCls: 'icon-cancel',
            handler: function()
            {

                var rowDatas = $('#prUser_datagrid ').datagrid('getSelections');
                if(rowDatas.length >0)
                {
                    var i = 0;
                    var puid_array=new Array;
                    for( i=0 ;i<rowDatas.length;i++)
                    {
                        puid_array.push(rowDatas[i].puid);

                    }

                    $.messager.confirm('确认', '是否删除该记录', function (r) {
                        if (r)
                        {
                            deleteProjectUser(puid_array);
                            Project_user_datagrid.datagrid('load',{
                                pid: pid
                            });
                        }
                    });
                    return false;
                }else
                {
                    $.messager.alert('提示','请先选中至少一条记录！','info');
                    return false;
                }

            }
        }]

    });

    Project_user_datagrid.datagrid('load',{
        pid: pid
    });
    var prUser_dialog = $('#prUser_dialog').show().dialog(
        {
        title : '项目人员关系',
        width : 350,
        height : 400,
        closable : true,
        cache : false,
        modal : true,
        buttons : [  {
            iconCls : 'icon-cancel',
            text : '取消',
            handler : function()
            {
                prUser_dialog.dialog("close");
            }
        } ]
    });
}


function deleteProjectUser(puarray)
{
    $("#prUser_datagrid").easyMask('show', {
        msg : "记录删除中"
    });
    $.ajax({
        type : "post",
        url : '/projectManage/deleteprUser',
        data : {
            puid_array : puarray
        },
        cache : false,
        dataType : 'json',
        success : function(r) {
            $("#prUser_datagrid").easyMask('hide');
            $.messager.alert('提示', r.msg, 'info');
        }
    });
}

function deleteProjectCode(cidarray)
{
    $("#prcode_datagrid").easyMask('show', {
        msg : "记录删除中"
    });
    $.ajax({
        type : "post",
        url : '/codeManager/deleteprCode',
        data : {
            cid_array : cidarray
        },
        cache : false,
        dataType : 'json',
        success : function(r) {
            $("#prcode_datagrid").easyMask('hide');
            $.messager.alert('提示', r.msg, 'info');
        }
    });
}


function projectCode(pid)
{
    var Project_code_datagrid=$("#prcode_datagrid").datagrid({
        url : '/codeManager/querycodeStage',
        pagination : true,
        pageSize : 10,
        pageList : [ 5, 10, 15, 20, 25, 30, 35],
        fit : true,//datagrid自适应宽高
        fitColumns:true,
        nowrap:false,
        rownumbers:true,
        columns : [ [ {
            //    field : 'bid',
            //    checkbox : true,
            //    sortable:true
            //}, {
            field : 'typeCode',
            title : '阶段',
            width : 10,
            hidden:true
        },{
            field : 'mc',
            title : '阶段名',
            width : 80
        },{
            field : 'pid',
            title : '工程id',
            width : 80,
            hidden:true
        },{
            field : 'type',
            title : '类型',
            width : 80,
            hidden:true
        },{
            field : 'cid',
            title : '编码id',
            width : 80,
            hidden:true
        }
        ]],
        toolbar:[
            {
            text : '添加',
            iconCls: 'icon-add',
            handler: function()
            {

                $.ajax
                ({
                    type : "post",// 不写此参数默认为get方式提交
                    url : '/stagecode/queryAllStage',
                    data : {
                        pid : pid
                    },
                    cache : false,
                    dataType : 'json',
                    success : function(r)
                    {
                        if(document.getElementById('add_code')==undefined)
                        {
                            $('#addcode_Dialog').append("<table id='ms2table'> <tr><td> <select name='add_code' id='add_code' multiple='multiple' >"+ r.option+"</select></td></tr></table>");
                            $('#add_code').multiselect2side({});
                            $('.ms2side__updown').remove();
                        }
                        var addPrcodeDialog = $('#addcode_Dialog').dialog( {
                            title : '请选择需要添加的状态',
                            width: 420,
                            height: 330,
                            cache: false,
                            closable : false,
                            modal: true,
                            buttons:[{
                                iconCls: 'icon-ok',
                                text:'确定',
                                handler:function()
                                {
                                    if($("#add_codems2side__dx option").length==0)
                                    {
                                        $.messager.alert("提示", "请至少选择一个项目状态", "info");
                                        return false;
                                    }
                                    add_code(pid);
                                }
                            },{
                                iconCls: 'icon-cancel',
                                text:'取消',
                                handler:function(){
                                    $("#add_codems2side__dx").find("option").remove();
                                    $("#ms2table").remove();
                                    addPrcodeDialog.dialog('close');
                                }
                            }]
                        });
                    }
                });


            }
        },{
            text : '删除',
            iconCls: 'icon-cancel',
            handler: function()
            {

                var rowDatas = $('#prcode_datagrid ').datagrid('getSelections');
                if(rowDatas.length >0)
                {
                    var i = 0;
                    var cid_array=new Array;
                    for( i=0 ;i<rowDatas.length;i++)
                    {
                        cid_array.push(rowDatas[i].cid);

                    }

                    $.messager.confirm('确认', '是否删除该记录', function (r) {
                        if (r)
                        {
                            deleteProjectCode(cid_array);
                            Project_code_datagrid.datagrid('load',{
                                pid: pid
                            });
                        }
                    });
                    return false;
                }else
                {
                    $.messager.alert('提示','请先选中至少一条记录！','info');
                    return false;
                }
            }
        }]

    });

    Project_code_datagrid.datagrid('load',{
        pid: pid
    });
    var prCode_dialog = $('#prcode_dialog').show().dialog(
        {
            title : '阶段编码维护',
            width : 300,
            height : 400,
            closable : true,
            cache : false,
            modal : true,
            buttons : [  {
                iconCls : 'icon-ok',
                text : '确定',
                handler : function()
                {
                    prCode_dialog.dialog("close");
                }
            } ]
        });
}


function add_code(pid)
{
    var selected=$("#add_codems2side__dx option");
    var p = new Array();

    for(var i=0,len=selected.length;i<len;i++){
        p.push(selected[i].value);
    }
    $("#add_codems2side__dx").children().appendTo($("#add_codems2side__sx"));
    $("#add_codems2side__dx").children().remove();
    $("#add_code").find('option').removeAttr("selected");
    $(".add_code_mask").easyMask('show', {
            msg : "补卡任务单生成中，请稍候......"
        });
        $.ajax( {
            type:"post",
            url : '/codeManager/addProjectCode',
            data : {
                stage : p,
                pid:pid
            },
            cache : false,
            dataType : 'json',
            success : function(r) {
                $(".add_code_mask").easyMask('hide');
                $.messager.alert('提示',r.msg,'info');//弹出提示信息
                $("#add_codems2side__dx").find("option").remove();
                $("#ms2table").remove();
                $("#addcode_Dialog").dialog('close');
                $('#prcode_datagrid').datagrid('load',
                    {
                    pid: pid
                    });//最后重新load数据,将最新的信息显示到页面上
            }
    });

}


