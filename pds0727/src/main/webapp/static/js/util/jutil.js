// ����ռ�?
var ns;
if(!ns){
	ns = {};
}

//��ʾ����ʱ��������
function initIdate(obj_1) {
	obj_1.combobox({
		url : '/ParameterManageController_showIdate.do',
		valueField : 'id',
		textField : 'text',
		panelHeight : 200,
		editable : false,
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}
	});
}

// ��ʾ����������
function initKs(obj_1) {
	obj_1.combobox({
		url : '/ParameterManageController_showKs.do',
		valueField : 'id',
		textField : 'text',
		panelHeight : 'auto',
		editable : false,
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}
	});
}

// ��ʾ����������
function initYh(obj_1) {
	obj_1.combobox({
		url : 'ParameterManageController_showYh.do',
		valueField : 'id',
		textField : 'text',
		panelHeight : 'auto',
		editable : false,
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}
	});
}

// ��ʾ�ͻ���������
function initPerson(obj_1, bz) {
	if (bz == 0) {
		obj_1.combobox({
			url : 'ParameterManageController_showShr.do',
			valueField : 'id',
			textField : 'text',
			panelHeight : '150',
			editable : false,
			onLoadSuccess : function(data) {
				obj_1.combobox('setValue', "-1");
			}
		});
	}
	if (bz == 1) {// ��������Ա
		obj_1.combobox({
			url : 'ParameterManageController_showRole.do',
			valueField : 'id',
			textField : 'text',
			panelHeight : '150',
			editable : false,
			onLoadSuccess : function(data) {
				obj_1.combobox('setValue', "-1");
			}
		});
	}
	if (bz == 2) {// ϵͳʹ����Ա
		obj_1.combobox({
			url : 'ParameterManageController_systemUser.do',
			valueField : 'id',
			textField : 'text',
			panelHeight : '150',
			editable : false,
			onLoadSuccess : function(data) {
				obj_1.combobox('setValue', "-1");
			}
		});
	}
}

function initPersonByRole(obj_1, roleId) {
	obj_1.combobox({
		url : 'initPersonByRole.do?roleId=' + roleId,
		valueField : 'id',
		textField : 'text',
		panelHeight : 'auto',
		editable : false,
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}
	});
}

// ��ʾ�����������?
function initCardType(obj_1) {
	obj_1.combobox({
		url : '/ParameterManageController_showCardType.do',
		valueField : 'id',
		textField : 'text',
		panelHeight : 'auto',
		editable : false,
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}
	});
}

// ��ʾ�Ա�������
function initXb(obj_1) {
	obj_1.combobox({
		url : '/ParameterManageController_showXb.do',
		valueField : 'id',
		textField : 'text',
		panelHeight : 'auto',
		editable : false,
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}
	});
}

// ��ʾ��κ�������?
function initPch(obj_1,flag) {
	obj_1.combobox({
		url : '/ParameterManageController_showPch.do?flag='+flag ,
		valueField : 'id',
		textField : 'text',
		panelHeight : '150',
		editable : false,
		multiple : false
	});
}




// ��ʾ����������
function initMz(obj_1) {
	obj_1.combobox({
		url : '/ParameterManageController_showMz.do',
		valueField : 'id',
		textField : 'text',
		panelHeight : 160,
		editable : false,
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}
	});
}

// ��ʾ��״̬������
function initCardFlag(obj_1, bz) {
	if (bz == 0) {
		obj_1.combobox({
			url : '/ParameterManageController_showSortCardFlag.do',
			valueField : 'id',
			textField : 'text',
			panelHeight : 'auto',
			editable : false,
			onLoadSuccess : function(data) {
				obj_1.combobox('setValue', "-1");
			}
		});
	}
	if (bz == 1) {
		obj_1.combobox({
			url : '/ParameterManageController_showSamplingCardFlag.do',
			valueField : 'id',
			textField : 'text',
			panelHeight : 'auto',
			editable : false,
			onLoadSuccess : function(data) {
				obj_1.combobox('setValue', "-1");
			}
		});
	}
	if (bz == 2) {
		obj_1.combobox({
			url : '/ParameterManageController_showProductInfoFlag.do',
			valueField : 'id',
			textField : 'text',
			panelHeight : '150',
			editable : false,
			onLoadSuccess : function(data) {
				obj_1.combobox('setValue', "-1");
			}
		});
	}
	if (bz == 3) {
		obj_1.combobox({
			url : '/ParameterManageController_showKeyReplaceFlag.do',
			valueField : 'id',
			textField : 'text',
			panelHeight : 'auto',
			editable : false,
			onLoadSuccess : function(data) {
				obj_1.combobox('setValue', "-1");
			}
		});
	}
}

function showErrCard(obj_1, bz) {
	if (bz == 0) {
		obj_1.combobox({
			url : '/ParameterManageController_showErrCardFlag.do',
			valueField : 'id',
			textField : 'text',
			panelHeight : 'auto',
			editable : false,
			onLoadSuccess : function(data) {
				obj_1.combobox('setValue', "-1");
			}
		});
	}
	if (bz == 1) {
		obj_1.combobox({
			url : '/ParameterManageController_showErrCardType.do',
			valueField : 'id',
			textField : 'text',
			panelHeight : 'auto',
			editable : false,
			onLoadSuccess : function(data) {
				obj_1.combobox('setValue', "-1");
			}
		});
	}
}

function showPsamCardFlag(obj_1) {
	obj_1.combobox({
		url : '/ParameterManageController_showPsamCard.do',
		valueField : 'id',
		textField : 'text',
		panelHeight : 'auto',
		editable : false,
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}
	});
}

function showInorderFlag(obj_1) {
	obj_1.combobox({
		url : '/ParameterManageController_showInorderFlag.do',
		valueField : 'id',
		textField : 'text',
		panelHeight : 'auto',
		editable : false,
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}
	});
}

function showOutorderFlag(obj_1) {
	obj_1.combobox({
		url : '/ParameterManageController_showOutorderFlag.do',
		valueField : 'id',
		textField : 'text',
		panelHeight : 'auto',
		editable : false,
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}
	});
}

function setOperateUser(obj, role) {
	obj.combobox({
		url : 'PMC_queryForUser.do?role=' + role,
		valueField : 'id',
		textField : 'text',
		editable : false,
		panelHeight : 'auto'
	});
}

// ���ɫ�������?
function setRoleValue(obj) {
	obj.combobox({
		url : 'UserManageController_queryRoleInfo.do',
		valueField : 'id',
		textField : 'text',
		editable : false,
		panelHeight : 'auto',
		onLoadSuccess : function(data) {
			obj.combobox('setValue', "-1");
		}
	});
}

function showProductFlag(obj_1) {
	obj_1.combobox({
		url : '/ParameterManageController_showProductType.do',
		valueField : 'id',
		textField : 'text',
		panelHeight : 'auto',
		editable : false,
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}
	});
}

function showOperationType(obj_1) {
	obj_1.combobox({
		url : '/ParameterManageController_showOperationType.do',
		valueField : 'id',
		textField : 'text',
		panelHeight : '242',
		editable : false,
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}
	});
}

$.extend($.fn.validatebox.defaults.rules, {
	checkID : {
		validator : function(v, p) {
			var reg = /^[a-zA-Z]$/;
			if (!reg.test(v.substr(0, 1))) {
				return false;
			}
			return v.length >= p[0];
		},
		message : '��ĸ��ͷ���Ҳ�������{0}���ַ�'
	},
	minLength : {
		validator : function(v, p) {
			return v.length >= p[0];
		},
		message : '��������{0}λ��'
	},
	eqPwd : {
		validator : function(v, p) {
			return v == $(p[0]).val();
		},
		message : "�������벻һ�£�"
	}
});

serializeObject = function(form) {
	var o = {};
	$.each(form.serializeArray(), function(index, n) {
		if (o[this['name']]) {
			o[this['name']] = o[this['name']] + "," + this['value'];
		} else {
			o[this['name']] = this['value'];
		}
	});
	return o;
};

function disable(obj) {
	obj.attr("disabled", "disabled");
}
function enable(obj) {
	obj.removeAttr("disabled");
}
function readOnly(obj) {
	obj.attr("readOnly", "readOnly");
}
function canRead(obj) {
	obj.removeAttr("readOnly");
}
function required(obj) {
	obj.attr("required", "required");
}
function cancelRequired(obj) {
	obj.removeAttr("required");
}

function resetForm(id) {
	document.getElementById(id).reset();
}

$.ajaxSetup({
	contentType : "application/x-www-form-urlencoded;charset=uft-8",
	complete : function(XMLHttpRequest, textStatus) {
		var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
		if (sessionstatus == "timeout") {
			window.location.replace("sessionFail.jsp");
		}
	}
});

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

function postOpen(url,params){
	var form = document.createElement("form");
	form.action = url+"?" + Math.random();
	form.method = "post";
	form.target = "_blank";

	for(var key in params){
		var input = document.createElement("input");
		input.name= key;
		input.value = params[key];
		form.appendChild(input);
	}

	form.style.display = "none";
	document.body.appendChild(form);
	form.submit();

}

function valSingleCombobox(obj, data) {
	obj.combobox({
		editable : false,
		multiple : false,
		panelHeight : 'auto',
		valueField : 'id',
		textField : 'text',
		data : data
	});
}


/**
 * ��չ��������
 */
$.extend($.fn.datagrid.methods, {
	/**
	 * ������ʾ����
	 * @param {} jq
	 * @param {} params ��ʾ��Ϣ������?
	 * @return {}
	 */
	doCellTip: function(jq, params){
		function showTip(data, td, e){
			if ($(td).text() == "")
				return;
			data.tooltip.text($(td).text()).css({
				top: (e.pageY + 10) + 'px',
				left: (e.pageX + 20) + 'px',
				'z-index': $.fn.window.defaults.zIndex,
				display: 'block'
			});
		};

		return jq.each(function(){
			var grid = $(this);
			var options = $(this).data('datagrid');
			if (!options.tooltip) {
				var panel = grid.datagrid('getPanel').panel('panel');
				var defaultCls = {
					'border': '1px solid #333',
					'padding': '2px',
					'color': '#333',
					'background': '#f7f5d1',
					'position': 'absolute',
					'max-width': '800px',
					'border-radius' : '4px',
					'-moz-border-radius' : '4px',
					'-webkit-border-radius' : '4px',
					'display': 'none'
				};
				var tooltip = $("<div id='celltip'></div>").appendTo('body');
				tooltip.css($.extend({}, defaultCls, params.cls));
				options.tooltip = tooltip;
				panel.find('.datagrid-body').each(function(){
					var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;
					$(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').delegate('td', {
						'mouseover': function(e){
							if (params.delay) {
								if (options.tipDelayTime)
									clearTimeout(options.tipDelayTime);
								var that = this;
								options.tipDelayTime = setTimeout(function(){
									showTip(options, that, e);
								}, params.delay);
							}
							else {
								showTip(options, this, e);
							}

						},
						'mouseout': function(e){
							if (options.tipDelayTime)
								clearTimeout(options.tipDelayTime);
							options.tooltip.css({
								'display': 'none'
							});
						},
						'mousemove': function(e){
							var that = this;
							if (options.tipDelayTime)
								clearTimeout(options.tipDelayTime);
							//showTip(options, this, e);
							options.tipDelayTime = setTimeout(function(){
								showTip(options, that, e);
							}, params.delay);
						}
					});
				});

			}

		});
	},
	/**
	 * �ر���Ϣ��ʾ����
	 *
	 * @param {}
	 *            jq
	 * @return {}
	 */
	cancelCellTip: function(jq){
		return jq.each(function(){
			var data = $(this).data('datagrid');
			if (data.tooltip) {
				data.tooltip.remove();
				data.tooltip = null;
				var panel = $(this).datagrid('getPanel').panel('panel');
				panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove');
			}
			if (data.tipDelayTime) {
				clearTimeout(data.tipDelayTime);
				data.tipDelayTime = null;
			}
		});
	}
});


/**
 * ��ȡ״̬ ����dict_type
 */
function getTdictState(dict_type){
	var tdict = tDicState;
	var json=new Array();
	for(var i=0;i<tdict.length;i++){
		if(tdict[i].dict_type==dict_type||tdict[i].dict_type=='choose'){
			json.push(tdict[i]);
		}
	}
	return json;
}

/**
 * ����������״ֵ̬:dataΪgetTdictState����ֵ
 * @param obj
 * @param data
 */
function setTdictState(obj, data) {
	obj.combobox({
		editable : false,
		multiple : false,
		panelHeight : 'auto',
		valueField : 'dict_name',
		textField : 'dict_val',
		data :data
	});
}

function setTdictStateScroll(obj, data) {
	obj.combobox({
		editable : false,
		multiple : false,
		panelHeight : 168,
		valueField : 'dict_name',
		textField : 'dict_val',
		data :data
	});
}

function setRegionSelf(obj,obj1) {
	console.log(obj);
	console.log(obj1.val());
	obj.combobox({
		url : 'PreCardStoreController_queryRegion.do',
		editable : false,
		panelHeight : 168,
		valueField : 'id',
		textField : 'text',
		onLoadSuccess: function (data) {
			if (data) {
				obj.combobox('setValue',obj1.val());
			}
		}
	});
}

function getToday() {
	var d = new Date();
	var y = d.getFullYear();
	var m = d.getMonth() + 1;
	var dd = d.getDate();
	if(m < 10){
		m = "0" + m;
	}
	if(dd < 10){
		dd = "0" + dd;
	}
	return y + "-" + m + "-" + dd;
}

/**
 * �й�����������
 * @param obj
 * @param url
 * @param xml_id
 */
function initSingleComboboxScroll(obj, url, xml_id) {
	obj.combobox({
		url : url + '?xml_id='+xml_id,
		editable : false,
		panelHeight : 168,
		valueField : 'id',
		textField : 'text'
	});
}


function setSubRegion(obj, obj1) {
	obj.combobox({
		url : 'PreCardStoreController_querySubRegion.do',
		editable : false,
		panelHeight : 168,
		valueField : 'id',
		textField : 'text',
	});
}

/**
 * �շ���true
 * @param arg
 * @returns {Boolean}
 */
function checkNull(arg) {
	if (typeof(arg) == "undefined" || arg == null || arg == "") {
		return true;
	} else {
		return false;
	}
}

/**
 * ��ʼ��������
 */
function initSingleCombobox(obj, url, xml_id) {
	obj.combobox({
		url : url + '?xml_id='+xml_id,
		editable : false,
		panelHeight : 'auto',
		valueField : 'id',
		textField : 'text'
	});
}

/**
 * �й�����������
 * @param obj
 * @param url
 * @param xml_id
 */
function initSingleComboboxScrollE(obj, url, xml_id) {
	obj.combobox({
		url : url + '?xml_id='+xml_id,
		editable : true,
		panelHeight : 168,
		valueField : 'id',
		textField : 'text'
	});
}

/**
 *������
 * @param obj
 * @param url
 * @param xml_id
 * @param pram
 */
function initSingleComboboxScrollPram(obj, url, xml_id, pram) {
	obj.combobox({
		url : url + '?xml_id='+xml_id+'&pram='+pram,
		editable : false,
		panelHeight : 'auto',
		valueField : 'id',
		textField : 'text'
	});
}

function $alert(msg) {
	$.messager.alert("��ʾ��Ϣ", msg, "info");
}
function alertZhuXiao() {
	$.messager.alert("��ʾ��Ϣ", '��ѡ��Ҫע��ļ��?', "info");
}
function alertEdit() {
	$.messager.alert("��ʾ��Ϣ", '��ѡ��Ҫ�༭�ļ�¼', "info");
}
function alertChoose() {
	$.messager.alert("��ʾ��Ϣ", '��ѡ��һ����¼', "info");
}
function alertDelete() {
	$.messager.alert("��ʾ��Ϣ", '��ѡ��Ҫɾ��ļ��?', "info");
}

function alertShow() {
	$.messager.alert("��ʾ��Ϣ", '��ѡ��Ҫ�鿴�ļ�¼', "info");
}
function alertSszf(){
	$.messager.alert("��ʾ��Ϣ",'��ѡ��Ҫ��ֹ/���ϵļ�¼',"info");
}
function alertImp(){
	$.messager.alert("��ʾ��Ϣ", '��ѡ��Ҫ����ļ��?', "info");
}
function alertTask(){
	$.messager.alert("��ʾ��Ϣ", '��ѡ��Ҫ������񵥵ļ��?', "info");
}
function alertBadCard() {
	$.messager.alert("��ʾ��Ϣ", '��ѡ��Ҫ���͵ķϿ����?', "info");
}
function alertKZXRuKu() {
	$.messager.alert("��ʾ��Ϣ", '��ѡ��Ҫ���ķϿ����?', "info");
}
function alertKZXDes() {
	$.messager.alert("��ʾ��Ϣ", '��ѡ��Ҫ��ٵķϿ����', "info");
}
function alertDownload() {
	$.messager.alert("��ʾ��Ϣ", '��ѡ��Ҫ���ص�������Ϣ', "info");
}
function alertDownloadBatch() {
	$.messager.alert("��ʾ��Ϣ", '��ѡ��Ҫ���ص����?', "info");
}
function alertPcExp() {
	$.messager.alert("��ʾ��Ϣ", '��ѡ��Ҫ�����ĳ��ⵥ��¼', "info");
}
function alertExp(){
	$.messager.alert("��ʾ��Ϣ", '��ѡ�����?', "info");
}

function dateCompare(startdate, enddate) {
	var arr = startdate.split("-");
	var starttime = new Date(arr[0], arr[1], arr[2]);
	var starttimes = starttime.getTime();

	var arrs = enddate.split("-");
	var lktime = new Date(arrs[0], arrs[1], arrs[2]);
	var lktimes = lktime.getTime();

	if (starttimes > lktimes) {
		return true;
	}
	else
		return false;
}

function showPname(obj_1){
	obj_1.combobox({
		url:'/projectManage/queryPnameByuser',
		valueField : 'id',
		textField : 'text',
		panelHeight : '200',
		editable : true,
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}

	});
}

function showPnameTree(obj_1){
	obj_1.combotree({
		url:'/projectManage/queryPnameByuser',
		valueField : 'id',
		textField : 'text',
		panelHeight : '200',
		editable : false,
		multiple : true,
		onLoadSuccess : function(data) {
			obj_1.combotree('setValue', "-1");
		},
		onShowPanel : function() {
			var node = obj_1.combotree('tree').tree('getChecked');
			var cnodes = "";
			for(var i=0;i<node.length;i++){
				if(node[i].id=='-1'){
					continue;
				}
        		cnodes+=node[i].id+',';
			}
			obj_1.combotree('setValues', cnodes.split(","));
		},
		onHidePanel : function() {
			var node = $('#export_pname').combotree('tree').tree('getChecked');
			if((node.length==1 && node[0].id=='-1') || node.length==0){
				obj_1.combotree('setValue', "-1");
			}else if(node.length>1){
				var cnodes = "";
				for(var i=0;i<node.length;i++){
					if(node[i].id=='-1'){
						continue;
					}
	        		cnodes+=node[i].id+',';
				}
				obj_1.combotree('setValues', cnodes.split(","));
			}
			
		}
	});
}

function showStage(obj_1,pid){
	obj_1.combobox({
		url:'/stagecode/querystage?pid='+pid,
			valueField : 'id',
			textField : 'text',
			panelHeight : 'auto',
			editable : false,
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}
	});
}

function showStage2(obj_1,pid,pflag){
	obj_1.combobox({
		url:'/stagecode/querystage?pid='+pid,
		valueField : 'id',
		textField : 'text',
		panelHeight : 'auto',
		editable : false,
		onLoadSuccess : function(data) {
			if(pflag==null)
			{
				obj_1.combobox('setValue', "-1");
			}

			obj_1.combobox('setValue', pflag);
		}
	});
}

function showUsername(obj_1,pid){
	obj_1.combobox({
		url:'/userManager/queryAllUser?parameter='+pid,
		valueField : 'id',
		textField : 'text',
		onLoadSuccess : function(data) {
			obj_1.combobox('setValue', "-1");
		}
	});
}



//show
function showFuZeRen(obj_1,pid){
	obj_1.combobox({
		url:'/userManager/queryAllUser?parameter='+pid,
		valueField : 'id',
		textField : 'text',
		panelHeight : 'auto',
		multiple:true,
		editable : false,
		onLoadSuccess : function(data)
		{
		}
	});
}

function showIsFuZeRen(pid,callback){

	$.ajax({
		type:"post",
		url :'/userManager/queryIsFuZeRen',
		data:{
			pid:pid
		},
		cache :false,
		dataType :'json',
		success : function(r)
		{
			if(r.success)
			{
				$.post('/projectManage/queryPMUsers?pid='+pid,callback);
			}else
			{
				$.messager.alert("��ʾ��Ϣ" ,r.msg);
			}
		}
	});




}





formatterDate = function(date) {
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
	+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day;
};













