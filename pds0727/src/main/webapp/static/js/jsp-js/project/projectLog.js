app.project_Log=function(){
    $('#pr_Log_datagrid').datagrid( {
        url : '/projectLog/queryPrLog',
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
            width : 80
        },{
            field : 'person',
            title : '撰写人',
            width : 80
        },{
            field : 'detail',
            title : '日报简介',
            width : 200
        },{
            field : 'contents',
            title : '日报内容',
            width : 200,
            hidden:true
        },
            {
            field : 'pstage',
            title : '项目阶段',
            width : 50
        }, {
            field : 'sdate',
            title : '日报时间',
            width : 50
        }, {
            field : 'cdate',
            title : '提交时间',
            width : 50
        },{
            field : 'isdelete',
            title : '是否删除',
            width : 100,
            hidden:true
        },{
            field : 'lid',
            title : '日志id',
            width : 100,
            hidden:true
        } ] ],
        toolbar: [{
            text : '查询',
            iconCls: 'icon-search',
            handler: function()
            {
                $('#selectSDate_prLog').datebox();
                $('#selectEDate_prLog').datebox();
                $('#pname_prLog').val("");
                $('#person_prLog').val("");
                $('#selectSDate_prLog').val("");
                $('#selectEDate_prLog').val("");
                var queryPrLogDialog= $('#queryPrLog_Dialog').dialog({
                    title : '查询项目',
                    width: 300,
                    height: 250,
                    closable: false,
                    cache: false,
                    modal: true,
                    buttons:[{
                        iconCls: 'icon-ok',
                        text:'确定',
                        handler:function()
                        {
                            if(dateCompare($('#selectSDate_prLog').datebox("getValue"),$('#selectEDate_prLog').datebox("getValue")))
                            {
                                $.messager.alert('提示', "开始时间不能大于结束时间", 'info'); // 弹出提示信息
                                return false;
                            }
                            $('#pr_Log_datagrid').datagrid('load',{
                                pname: $.trim($('#pname_prLog').val()),
                                person: $.trim($('#person_prLog').val()),
                                selectSDate:$('#selectSDate_prLog').datebox("getValue"),
                                selectEDate:$('#selectEDate_prLog').datebox("getValue")
                            });
                            queryPrLogDialog.dialog('close');
                        }
                    },{
                        iconCls: 'icon-cancel',
                        text:'取消',
                        handler:function(){
                            queryPrLogDialog.dialog('close');

                        }
                    }]});

            }
        },'-',{
            text : '编辑',
            iconCls : 'icon-edit',
            handler: function()
            {
                var rowData = $('#pr_Log_datagrid').datagrid('getSelected');
                if(rowData != null){
                    $("#a").hide();
                    $("#b").hide();
                    $("#c").hide();
                    $("#d").show();
                    $('#log_pname_edit').html(rowData.pname);
                    $('#editor').html(rowData.contents);

                    var editPrLog_Dialog= $('#addPr_Log').dialog({
                        title : '编辑日志',
                        width: 800,
                        height: 480,
                        closable: false,
                        cache: false,
                        modal: true,
                        buttons:[{
                            iconCls: 'icon-ok',
                            text:'确定',
                            handler:function()
                            {

                                $.ajax({
                                    type:"post",
                                    url :'/projectLog/editPrLog',
                                    data:{
                                        detail:$("#editor").text(),
                                        contents:$("#editor").html(),
                                        lid:rowData.lid
                                    },
                                    cache :false,
                                    dataType :'json',
                                    success : function(r)
                                    {
                                        if(r.success)
                                        {
                                            editPrLog_Dialog.dialog('close');
                                            $.messager.alert("提示信息" ,r.msg);
                                            $('#pr_Log_datagrid').datagrid('load',{});
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
                                editPrLog_Dialog.dialog('close');
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
                $("#a").show();
                $("#b").show();
                $("#c").show();
                $("#d").hide();

                showPname($('#log_pname_add'));
                $('#log_stage_add').combobox('setValue', "-1");
                //当选择了项目  就会加载项目状态
                $('#log_pname_add').combobox({
                    onSelect: function(record){
                        showStage($('#log_stage_add'),record.id)
                    }
                });
                $('#log_sdate_add').datebox();
                $('#log_sdate_add').datebox('setValue', formatterDate(new Date()));
                $('#editor').empty();
                $('#log_sdate_add').val("");
                var addPr_Log= $('#addPr_Log').dialog({
                    title : '撰写日志啊',
                    width: 800,
                    height: '20%' ,
                    closable: false,
                    cache: false,
                    modal: true,
                    buttons:[{
                        iconCls: 'icon-ok',
                        text:'确定',
                        handler:function()
                        {
                            if($('#log_pname_add').combobox("getValue")==-1)
                            {
                                $.messager.alert('提示', "必须选择项目名", 'info'); // 弹出提示信息
                                return false;
                            }
                            if($('#log_stage_add').combobox("getValue")==-1)
                            {
                                $.messager.alert('提示', "必须选择项目状态", 'info'); // 弹出提示信息
                                return false;
                            }
                            if($('#log_detail_add').val()=="")
                            {
                                $.messager.alert('提示', "必须填写详细日志", 'info'); // 弹出提示信息
                                return false;
                            }
                            if($('#log_sdate_add').combobox("getValue")=="")
                            {
                                $.messager.alert('提示', "必须选择日报时间", 'info'); // 弹出提示信息
                                return false;
                            }

                            console.log($("#editor").text());
                            console.log($("#editor").html());
                            console.log("1-----------2");

                            $.ajax({
                                type:"post",
                                url :'/projectLog/addprLog',
                                data:{
                                    pname:$("#log_pname_add").combobox("getText"),
                                    detail:$("#editor").text(),
                                    contents:$("#editor").html(),
                                    sdate:$('#log_sdate_add').datebox("getValue"),
                                    pflag:$("#log_stage_add").combobox("getValue"),
                                    pid:$("#log_pname_add").combobox("getValue")
                                },
                                cache :false,
                                dataType :'json',
                                success : function(r)
                                {
                                    if(r.success)
                                    {
                                        addPr_Log.dialog('close');
                                        $.messager.alert("提示信息" ,r.msg);
                                        $('#pr_Log_datagrid').datagrid('load',{});
                                        $('#log_stage_add').combobox("clear");
                                        $('#log_stage_add').combobox("loadData",[{"id":"-1","text":"--请先选择项目"}]);

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
                            addPr_Log.dialog('close');
                            $('#log_stage_add').combobox("clear");
                            $('#log_stage_add').combobox("loadData",[{"id":"-1","text":"--请先选择项目"}]);
                        }
                    }]});
            }

        },'-',{
            text : '删除',
            iconCls : 'icon-cancel',
            handler : function ()
            {

                var rowData = $('#pr_Log_datagrid').datagrid('getSelected');
                if(rowData != null)
                {
                    $.messager.confirm('确认', '是否删除该日报', function (r) {
                        if (r)
                        {
                            deletePrLog(rowData.lid);
                            $('#pr_Log_datagrid').datagrid('load',{});
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
            text : '导出excel',
            iconCls : 'icon-redo',
            handler : function ()
            {
                showPname($('#export_pname'))
                $('#export_sdate').datebox();
                $('#export_edate').datebox();
                $('#export_pname').val("");
                $('#export_sdate').val("");
                $('#export_edate').val("");
                $('#export_person').val("");
                var export_Excel= $('#export_excel').dialog({
                    title : '导出excel',
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
                            postOpen("/export/createExcel",
                                {
                                    pname:$("#export_pname").combobox("getText"),
                                    person:$('#export_person').val(),
                                    selectSDate:$('#export_sdate').datebox("getValue"),
                                    selectEDate:$('#export_edate').datebox("getValue")
                                });
                        }
                    },{
                        iconCls: 'icon-cancel',
                        text:'取消',
                        handler:function(){
                            export_Excel.dialog('close');
                        }
                    }]});
            }

        }]
    });
};
app.project_Log();


$('#pr_Log_datagrid').datagrid({
    onDblClickCell: function(index,field,value){
        $('#pr_Log_datagrid').datagrid('selectRecord',index);
        var rowdata=$('#pr_Log_datagrid').datagrid('getSelected');
        $('#contents_dialog').html(rowdata.contents);
        var contents_dialog= $('#contents_dialog').dialog({
            title : '日志详细',
            width: 300,
            height: 300,
            closable: false,
            cache: false,
            modal: true,
            buttons:[
                {
                iconCls: 'icon-ok',
                text:'确定',
                handler:function(){
                    contents_dialog.dialog('close');
                }
            }]});
    }
});


function deletePrLog(lid)
{
    $("#pr_Log_datagrid").easyMask('show', {
        msg : "任务单删除中."
    });
    $.ajax({
        type : "post",
        url : '/projectLog/deleteprLog',
        data : {
            lid : lid
        },
        cache : false,
        dataType : 'json',
        success : function(r) {
            $("#pr_Log_datagrid").easyMask('hide');
            $.messager.alert('提示', r.msg, 'info');
        }
    });
}





