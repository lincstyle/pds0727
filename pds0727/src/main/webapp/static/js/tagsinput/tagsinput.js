/**
 * @author 张靖
 * @description 完成taginput功能
 */
function TagsInput(element, options) {

    var defaults = {
        id: "id",
        text: "name",
        placeholder: "请输入",
        /**
         * 格式化方法.
         * 默认的取数据项的name
         * @param str
         * @returns  string
         */
        formatter: function (str) {
            return str;
        },
        /**
         * 获取值的时候的格式化方法.
         * @param item data中存放的数据
         * @returns object
         */
        valueFormatter: function (item) {
            return item.id;
        },
        /**
         * 当重复的时候执行的方法.
         * @param item 重复的内容
         * @returns boolean 返回false不会添加tag
         */
        onTagExists: function (item) {
            return;
        },
        /**
         * 比较将要添加的值是否存在.
         * 即标签是否已经添加.
         * @param itemNew 要添加的项
         * @param itemIt 已经存在的项
         * @returns {boolean}
         */
        compare: function (itemNew, itemIt) {
            return itemNew.id === itemIt.id
        },
        itemDataKey: "itemData"
    }

    options = $.extend({}, defaults, options);
    var $container = $('<div class="bootstrap-tagsinput"></div>');
    var $input = $('<input type="text" placeholder="' + options.placeholder + '"/>').appendTo($container);

    element.before($container);
    element.hide();

    $container.on('click', '[data-role=remove]', function () {
        $(this).parent().remove();
    });

    this.addTag = function (item) {
        //获得字符串值
        var tagStr = item[options.text];

        //判断元素是否存在
        if (options.onTagExists) {
            var existTag = $(".tag", $container).filter(function () {
                return options.compare(item, $(this).data(options.itemDataKey));
            });
            if (existTag.length > 0) {
                if (!options.onTagExists(item, existTag)) {
                    $input.val("")
                    return;
                }
            }
        }
        //插入tag
        var $tag = $('<span class="tag label label-info" style="white-space: normal" data-id="' + item[options.id] + '" > ' + options.formatter(tagStr) + '<span  data-role="remove"></span></span>');
        $tag.data(options.itemDataKey, item);
        $input.before($tag);
        $input.val("");
    };
    this.remove = function (item) {
        $container.find(".tag [data-id=" + item[this.options.id] + "]").remove();
    };
    this.removeAll = function () {
        $container.find(".tag").remove();
    };
    this.getValue = function () {
        var result = new Array();
        $container.find(".tag").each(function (i, v) {
            result.push(options.valueFormatter($(v).data(options.itemDataKey)));
        });
        return result
    }
    this.input = $input

};


