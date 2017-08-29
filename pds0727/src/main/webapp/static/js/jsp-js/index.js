/*
 * 首页js
 */

// 加载系统菜单
var g_index = 0;
var center_tabs;
var tDicState;

//加载系统菜单
function loadMenu(){
    var tab = $('#center_tabs');
    $('#systemMenu').tree({
        url: "/admin/showMenu",
        onClick: function(node){
            addTabs(tab,node);
        }
    });
    //tab关闭时remove所有id
    tab.tabs({
        onBeforeClose:function(title,index){//关闭面板前把此功能的资源释放
            var tab =  $(this).tabs('getTab',index);
            if(tab.divs != undefined){
                //准备删除的DIV内容
                $('#center_tabs').attr("rmdiv",tab.divs);
            }
        },
        onClose : function(){//删除被关闭tab中用到的DOM对象
            var rmdiv = $('#center_tabs').attr("rmdiv");
            if(rmdiv != undefined){
                var divs = rmdiv.split(",");
                for(var i=0;i<divs.length;i++){
                    var divTarget = $('#'+divs[i]);
                    if(divTarget){
                        divTarget.remove();
                    }
                }
            }
        }
    });
    
}


// 点击系统菜单，中间区域显示相应页面内容
function addTabs(center_tabs, node) {
	if (center_tabs.tabs('getTab', node.text) != null) {
		// 已经存在的tab，则不需要再次新增
		center_tabs.tabs('select', node.text);
	} else {
		var url = node.url;
        if(!url) return;
        if(url.match(/\.jsp$/i)){
            url = '/view'+url;
        };
        center_tabs.tabs('add', {
            title : node.text,
            href : url,
            closable : true,
            // 新增记录每个tab对应jsp里的多有id
            border : false,
            id : node.text,
            extractor : function(data) {
                // 抽取body中的内容
                var pattern = /<body[^>]*>((.|[\n\r])*)<\/body>/im;
                var matches = pattern.exec(data);
                if (matches) {
                    data = matches[1];
                }
                var tmp = $('<div/>').html(data);
                var divs = $(tmp).find('[id]');
                var ids = [];
                for ( var i = 0; i < divs.length; i++) {
                    ids.push(divs[i].getAttribute("id"));
                }
                // 记录本tab中的所有带ID的DIV
                center_tabs.tabs('getTab', node.text).divs = ids;
                return data;
            }
        });
	}
}

// 关闭当前标签页
function closeTab() {
    var tab = center_tabs.tabs('getSelected');
    if (tab) {
    	center_tabs.tabs('close', center_tabs.tabs('getTabIndex', tab));
    }
}

//关闭所有标签页
function closeAllTab() {
    $(".tabs li").each(function (i, n) {
        var title = $(n).text();
        center_tabs.tabs('close', title);
    });
}

//关闭其它标签页
function closeOtherTab() {
    var curTab = center_tabs.tabs('getSelected');
    var curTitle = curTab.panel('options').title;
    $(".tabs li").each(function (i, n) {
        var title = $(n).text();
        if (curTitle != title) {
        	center_tabs.tabs('close', title);
        }
    });
}

var reportTime = new Date().getTime();
/*
function modifyMyData(){
	var tab = $('#center_tabs');
	addTabs(tab,{url:'/userManager/getCurrentUser',text:"修改个人资料"});
}
*/
$.ajaxSetup({
    dataType:"json",
    contentType : "application/x-www-form-urlencoded;charset=uft-8",
    headers:{
        "X-Requested-With":"XMLHttpRequest"
    },
    statusCode:{
        401 :function (){
            window.location.replace("/login");
        },
        500 : function () {

        }
    },
    error: function ( jqXHR,textStatus,errorThrown ){
        $.messager.alert("错误",errorThrown);
    }
});

