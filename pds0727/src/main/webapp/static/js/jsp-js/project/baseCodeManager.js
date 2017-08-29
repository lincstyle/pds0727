; app.basecode_mananger = (function (){

    var baseCodeTree = $.fn.zTree.init($('#baseCodeTree'), {
        async: {
            dataType:"json",
            enable: true,
            url: "/baseCodeManager/queryBaseCode",
            autoParam: ["typecode"]
        },
        data: {
            key: {
                name: "mc"
                 }
        },
        view:{
            showIcon:false,
            selectedMulti: false
        },
        callback:{
            onClick:onClick,
            onDrop:onDrop,
            onAsyncSuccess:onAsyncSuccess
        },
        edit:{
            drag:{
                prev: true,
                inner: false,
                next: true
            },
            enable: true,
            showRemoveBtn: false,
            showRenameBtn: false
        }
    });

    function onClick(event, treeId, treeNode,clickFlag){
        if(clickFlag != 1) return;
        var form = $("#baseCodeForm");
        form.form("load",treeNode);
        form.find("tr:eq(1)").show();
    }


    function onDrop(event, treeId, treeNodes, targetNode, moveType) {
        $.easyMask("show");

        if(!targetNode || !treeNodes || !treeNodes[0]){
            return;
        }

        var original = treeNodes[0];
        var target = targetNode;

        $.ajax({
            url:'/baseCodeManager/moveBaseCode',
            dataType:"json",
            method : "post",
            data:{
                original:original,
                target:target
            },
            success: function (info) {
                $.easyMask("hide");
                refresh();
            }
        });
    }

    function submitAdd(flag){
        var selects = baseCodeTree.getSelectedNodes();
        if(selects.length < 1){
            $.messager.alert("警告","请选择一个节点再操作");
            return ;
        }

        $('#baseCodeForm').form('submit',{
            url:"/baseCodeManager/addBaseCode/"+flag,
            success:function(data){
                    refresh();
            }
        });
    }
    function submitEdit(){
        var selects = baseCodeTree.getSelectedNodes();
        if(selects.length < 1){
            $.messager.alert("警告","请选择一个节点再操作");
            return ;
        }

        $('#baseCodeForm').form('submit',{
            url:"/baseCodeManager/editBaseCode",
            success:function(data){
                refresh();
            }
        })
    }
    function submitRemove(){
        var selects = baseCodeTree.getSelectedNodes();
        if(selects.length < 1){
            $.messager.alert("警告","请选择一个编码再操作");
            return ;
        }
        var selectNode = selects[0];

            $.messager.confirm("警告","该编码将会被删除，是否确定", function (t) {
                if(t){
                    remove();
                }
            });

    }

    function remove(){

        $.ajax({
            data:$('#baseCodeForm').serialize(),
            url:"/baseCodeManager/deleteBaseCode",
            success:function(data){
            	if(data.success){
            		refresh();
            	}
                $.messager.alert("提示信息" ,data.msg);
            }
        })
    }


    function refresh(parent){
        var parent = parent || baseCodeTree.getSelectedNodes()[0].getParentNode();
        baseCodeTree.reAsyncChildNodes(parent, "refresh");
    }


    function onAsyncSuccess(event, treeId, treeNode, msg){
        if(!msg.length){
            treeNode.isParent = false;
            baseCodeTree.updateNode(treeNode);
        }
    }

    return {
        submitAdd:submitAdd,
        submitEdit:submitEdit,
        submitRemove:submitRemove
}
})();

