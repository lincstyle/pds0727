/**
 * @author 张靖
 *          2015-06-14 22:04.
 */
; app.authority_resource = (function (){

    var resourceTree = $.fn.zTree.init($('#resourceTree'), {
        async: {
            dataType:"json",
            enable: true,
            url: "/resourceManager/showResource",
            autoParam: ["id"]
        },
        data: {
            key: {
                name: "name"
            },
            simpleData:{
                enable: true,
                pIdKey:"parentId",
                rootPId:"0"
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
                prev: drop,
                inner: false,
                next: drop
            },
            enable: true,
            showRemoveBtn: false,
            showRenameBtn: false
        }
    });

    function onClick(event, treeId, treeNode,clickFlag){
        if(clickFlag != 1) return;
        var form = $("#resourceForm");
        form.form("load",treeNode);

        var node,identityStr;
        for(var i = treeNode.level ; i >= 0 ; i -- ){
            if(i == treeNode.level){
                node = treeNode;
            }else{
                node = node.getParentNode();
            }
            identityStr = (i == 0 ?"":":")+node.identity + (identityStr || "");
        }
        $("#resourceFormIdentity").html(identityStr);

        if(treeNode.level != 1){
            form.find("tr:eq(1)").hide();
        }else{
            form.find("tr:eq(1)").show();
        }
    }

    function drop(treeId, nodes, targetNode) {
        if(nodes[0].getParentNode() != targetNode.getParentNode()){
            return false;
        }
        return true;
    }

    function onDrop(event, treeId, treeNodes, targetNode, moveType) {
        $.easyMask("show");

        if(!targetNode || !treeNodes || !treeNodes[0]){
            return;
        }

        var original = treeNodes[0];
        var target = targetNode;

        if(original.children){
            delete original.children;
        }

        if(target.children){
            delete target.children;
        }

        $.ajax({
           url:'/resourceManager/move',
            dataType:"json",
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
        $.messager.confirm("提示","是否添加资源？",function (t){
            if(t){
                var selects = resourceTree.getSelectedNodes();
                if(selects.length < 1){
                    $.messager.alert("警告","请选择一个节点再操作");
                    return ;
                }
                var selectNode = selects[0];
                if(flag == 1 && selectNode.level == 2 ){
                    $.messager.alert("警告","已经是最小一级，不能再新增子节点");
                    return ;
                }

                //如果不是新增菜单,把url重置为空
                if(selectNode.level - flag != 1){
                    $("#resourceForm :input[name='url']").val("");
                }

                $('#resourceForm').form('submit',{
                    url:"/resourceManager/add/"+flag,
                    success:function(data){
                        $.messager.alert("提示","操作成功");
                        if(flag == 1){
                            refresh(resourceTree.getSelectedNodes()[0]);
                        }else{
                            refresh();
                        }
                    }
                });
            }
        })
    }

    function submitEdit(){
        $.messager.confirm("提示","是否修改资源？",function (t) {
            if(t){
                var selects = resourceTree.getSelectedNodes();
                if (selects.length < 1) {
                    $.messager.alert("警告", "请选择一个节点再操作");
                    return;
                }
                $('#resourceForm').form('submit', {
                    url: "/resourceManager/edit",
                    success: function (data) {
                        $.messager.alert("提示","操作成功");
                        refresh();
                    }
                })
            }
        });
    }

    function submitRemove(){
        $.messager.confirm("提示","是否删除资源？",function (t) {
            if(t){
        var selects = resourceTree.getSelectedNodes();
        if(selects.length < 1){
            $.messager.alert("警告","请选择一个节点再操作");
            return ;
        }
        var selectNode = selects[0];
        if(selectNode.level != 2 ){
            $.messager.confirm("警告","该节点是父节点，子节点讲被一同删除", function (t) {
                if(t){
                    remove();
                }
            });
        }else{
            remove();
        }
            }});
    }

    function remove(){

        $.ajax({
            data:$('#resourceForm').serialize(),
            url:"/resourceManager/remove",
            success:function(data){
                $.messager.alert("提示","操作成功");
                refresh();
            }
        })

    }


    function refresh(parent){
        if(parent != null){
            parent.isParent = true;
        }
        resourceTree.reAsyncChildNodes(parent, "refresh");
    }


    function onAsyncSuccess(event, treeId, treeNode, msg){
        if(!msg.length){
            treeNode.isParent = false;
            resourceTree.updateNode(treeNode);
        }
    }

    return {
        submitAdd:submitAdd,
        submitEdit:submitEdit,
        submitRemove:submitRemove
    }
})();

