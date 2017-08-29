var gulp = require('gulp');
var RevAll = require('gulp-rev-all');
var uglify = require('gulp-uglify');
var filter = require('gulp-filter');
var csso = require('gulp-csso');
var useref = require('gulp-useref');

var Path = require('path');
var Tool = require('gulp-rev-all/tool.js');


Date.prototype.format = function(format) {
    var o = {
        "M+" : this.getMonth() + 1, // month
        "d+" : this.getDate(), // day
        "h+" : this.getHours(), // hour
        "m+" : this.getMinutes(), // minute
        "s+" : this.getSeconds(), // second
        "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
        "S" : this.getMilliseconds()
    }

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    }

    for ( var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
}


gulp.task('default', function () {

    var jsFilter = filter(["**/**.js"],{restore: true});
    var cssFilter = filter(["**/**.css"],{restore: true});
    var htmlFilter = filter(["**/**.jsp"],{restore: true});



/*    var assets = useref.assets({
        searchPath:[
            'src/main/webapp'
        ]
    });*/

    var nonFileNameChar = '[^a-zA-Z0-9\\.\\-\\_\/]';

    var revAll = new RevAll({

        //生成版本文件名字
        fileNameManifest:"rev-manifest"+new Date().format("yyyy-MM-dd-hhmmss")+".json",

        //不重命名文件
        dontRenameFile: ['.jsp','.jpg','.png'] ,

        //无需关联处理文件
        dontGlobal: [ /^\/favicon.ico$/ ,'.bat','.txt','.less','.html','.xml'],


        referenceToRegexs:function (reference) {

            var escapedRefPathBase = Tool.path_without_ext(reference.path).replace(/([^0-9a-z])/ig, '\\$1');
            var escapedRefPathExt = Path.extname(reference.path).replace(/([^0-9a-z])/ig, '\\$1');

            for(var i = 0,length = this.dontRenameFile.length ; i < length ; i++){
                if(escapedRefPathExt.lastIndexOf(this.dontRenameFile[i]) != -1){
                    return [];
                }
            }

            var regExp, regExps = [];

            // Expect left and right sides of the reference to be a non-filename type character, escape special regex chars
            regExp = '('+ nonFileNameChar +')(' + escapedRefPathBase + ')(' +  escapedRefPathExt + ')('+ nonFileNameChar + '|$)';
            regExps.push(new RegExp(regExp, 'g'));
            return regExps;
        }
    });

    return gulp.src([
        'src/main/webapp/**'
        ]
    )

        //合并html里面的js/css
        .pipe(htmlFilter)
        .pipe(useref())
        .pipe(htmlFilter.restore)

        //压缩js
        .pipe(jsFilter)
        .pipe(uglify())
        .pipe(jsFilter.restore)

        //压缩css
        .pipe(cssFilter)
        .pipe(csso())
        .pipe(cssFilter.restore)

        //加MD5后缀
        .pipe(revAll.revision())

        //输出
        .pipe(gulp.dest('target/buildWebApp'))
         //生成映射json文件
         .pipe(revAll.manifestFile())
         .pipe(gulp.dest('target/buildWebApp'));
});



