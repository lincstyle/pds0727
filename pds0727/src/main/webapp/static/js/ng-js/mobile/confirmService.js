/**
 * Created by Administrator on 2015/9/29.
 * 在选择项目参与人员的时候会用到此service
 */

angular.module('mobile').service('confirmer',["$window",'$rootScope', function ($window,$rootScope) {
    $rootScope.$on('$locationChangeSuccess', function () {
        if($("#bg").show()){
            confirmerDiv.slideUp("fast");//收起框
            $("#bg").hide();//隐藏遮罩层
            //解除禁止屏幕滑动事件
            document.removeEventListener("touchmove",disableTouch,false);
        }
    });

    //confirm控件，负责弹出框
    var defaults = {
        msg:"是否删除？"
    }

    var bg_div = '<div id="bg" class="panel-default navbar-fixed-bottom" style="display: none; position: fixed; top: 0%; left: 0%; width: 100%; height: 100%; background-color: black; z-index:1030; -moz-opacity: 0.4; opacity:0.4; filter: alpha(opacity=70);" ></div>';
    var divTmpl = '<div class="panel panel-default navbar-fixed-bottom" style="display:none; z-index:1031;margin-bottom:0px;"> <div class="panel-heading" > <h3 class="panel-title">提示</h3> </div> <div class="panel-body"> <div style="text-align: center"></div> <br/> <button  class="btn btn-success" style="width: 100%">确定</button> <br/><br/> <button class="btn btn-default" style="width: 100%">取消</button> </div> </div>';
    var disableTouch = function(e){
        e.preventDefault();
        e.stopPropagation();
    };
    var successBtn,cancelBtn,contentDiv,confirmerDiv,timestamp;
    /**
     *  弹出确认框
     *  @paran callback 回调方法,需要接收一个状态点击按钮的值
     *                  确认为1，取消为0
     */
    function openDialog(callback) {

        successBtn.unbind();
        cancelBtn.unbind();
        //点击确定
        successBtn.click(function(){
            callback(1);
            confirmerDiv.slideUp("fast");
            $("#bg").hide();
            document.removeEventListener("touchmove",disableTouch,false);
        });
        //点击取消
        cancelBtn.click(function(){
            callback(0);
            confirmerDiv.slideUp("fast");//收起框
            $("#bg").hide();//隐藏遮罩层
            //解除禁止屏幕滑动事件
            document.removeEventListener("touchmove",disableTouch,false);

        });
        //显示遮罩层
        $("#bg").show();
        //弹出框
        confirmerDiv.slideDown("fast");
        //禁止屏幕滚动
        document.addEventListener("touchmove",disableTouch,false);

    };

    /**
     *   设置弹出框显示的内容
     *   @param msg
     */
    function setMsg(msg){
        contentDiv.html(msg);
    }
    (function _init() {
        timestamp = Date.parse(new Date());
        var div = $(divTmpl);
        div.attr("id",timestamp);
        $($window.document.body).append(bg_div);//append遮罩层
        $($window.document.body).append(div);//append confirm
        var content = $("#"+timestamp).find(".panel-body");
        contentDiv = content.find("div:eq(0)");
        successBtn = content.find(".btn-success");
        cancelBtn = content.find(".btn-default");
        confirmerDiv = content.parent();
        contentDiv.html(defaults.msg);
    })();

    return {
        open: openDialog,
        setMsg : setMsg
    };
}]);