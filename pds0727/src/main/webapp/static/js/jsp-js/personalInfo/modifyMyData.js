$(function(){
	
	$("#save").click(function(){
		saveData();
	});
	
	$("#editPwd").click(function(){
		editPwd();
	});

	$("#upload_img").click(function(){
		uploadImg();
	});
	
});

function uploadImg(){
	$('#upload_file').filebox({    
	    buttonText: '选择文件',
	    buttonAlign: 'right' 
	});
	$("#error").text("");
	$("#upload_img_div").show().dialog({
		title:'上传图像',
		width : 400,
		height : 150,
		closable : false,
		draggable : true,
		cache : false,
		modal : true,
		buttons : [ {
			iconCls : 'icon-ok',
			text : '确定',
			handler : function() {
				$('#upload_form').form('submit',{
					url:'/userManager/uploadImg',
	            	onSubmit:function(){
//	            		var uploadfile_value = $("#upload_form").form("validate");  
	            		var uploadfile = $(":file").val();
	            		console.log(uploadfile);
                        if(uploadfile == ""){
                        	$("#error").text("请选择上传文件!");
                        	return false;
                        }
                        //判断上传文件的后缀名  
                        var file_type = uploadfile.substr(uploadfile.lastIndexOf('.') + 1);
                        if (file_type != 'jpg' && file_type != 'jpeg' && file_type != 'bmp' && file_type != 'png') {
                            $("#error").text("请选择jpg、jpeg、bmp或png等格式的图片!");
                            return false;
                        }
	            	},
	                success:function(result){
	                	result = $.parseJSON(result);
	                    if(result.state == "ok"){
	                    	var user_id = $("#upload_Id").val();
	        				$('#upload_form').form('clear');//清空输入的数据
	        				$("#upload_Id").val(user_id);
	        				$('#upload_img_div').dialog('close');//关闭dialog
	        				$.messager.alert('提示信息',result.message,"info");
	        			} else {
	        				$("#error").text(result.message);
	        			}
	                }
	            });
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				var user_id = $("#upload_Id").val();
				$('#upload_form').form('clear');//清空输入的数据
				$("#upload_Id").val(user_id);
				$('#upload_img_div').dialog('close');//关闭dialog
			}
		} ]
	});
}

function saveData(){
	$("#bg").show();//显示遮罩层，防止用户连续点击保存
	var name = $("#modify_name").validatebox("isValid");//验证名字
	if(!name){
		$.messager.alert("提示信息","姓名不符合规范","info");
		return false;
	}
	var nameValue = $("#modify_name").val();
	var telValue = $("#modify_tel").val();
	var weixinValue = $("#modify_weixin").val();
	var emailValue = $("#modify_email").val();
	if(telValue == "" && weixinValue == "" && emailValue == ""){
		$.messager.alert("提示信息","请输入手机号或邮箱或微信号!","info");
		return false;
	} else {
		var tel = $("#modify_tel").validatebox("isValid");//验证手机号
		if(!tel){
			$.messager.alert("提示信息","请输入正确格式的手机号!","info");
			return false;
		}
		var email = $("#modify_email").validatebox("isValid");//验证邮箱
		if(!email){
			$.messager.alert("提示信息","请输入正确格式的邮箱!","info");
			return false;
		}
		var weixin = $("#modify_weixin").validatebox("isValid");//验证微信
		if(!weixin){
			$.messager.alert("提示信息","请输入正确格式的微信号!","info");
			return false;
		}
	}
	var userId = $("#modify_id").val();
	var departmentId = $("#modify_departmentId").val();
	var gender = $('input:radio[name="gender"]:checked').val();
	var account = $('#modify_account').html();
	var roleId = $('#modify_roleId').val();
	console.log(account);
	
	$.ajax({
		type: 'POST',
		url: '/userManager/modifyMyData',
		data: {
			userId:userId,
			departmentId:departmentId,
			username:nameValue,
			account:account,
			gender:gender,
			tel:telValue,
			email:emailValue,
			weixin:weixinValue,
			roleId:roleId
		},
		dataType: 'json',
		success: function(result){
			$("#bg").hide();
			if(result.state == "ok"){
				$("#user_name").html("用户："+nameValue);
			}
			//console.log(result.message);
			$.messager.alert('提示信息',result.message,"info");
		}
	});
}
	
/**
 * 修改密码
 */	
function editPwd(){
	$('#old_password_div').show().dialog({
		title:'修改密码',
		width : 400,
		height : 150,
		closable : false,
		draggable : true,
		cache : false,
		modal : true,
		buttons : [ {
			iconCls : 'icon-ok',
			text : '确定',
			handler : function() {
				var old_password = $.trim($('#old_password').val());
				
				if(old_password != null && old_password != ""){
					var userId = $("#modify_id").val();
					$.ajax({
						url : '/userManager/changeOldPassword',
						type : "post",
						data : {
							user_id : userId,
							password : old_password
						},
						async : false,
						cache : false, 
						dataType : 'json',
						success : function(r) {
//							var r = $.parseJSON(r);
							//如果旧密码是对的
							if(r.state == "yes"){
								$('#old_password').val('');//清空输入的数据
								$('#old_password_div').dialog('close');//关闭输入旧密码的dialog
								
								//2、显示输入新密码的dialog
								$('#new_password_div').show().dialog({
									title:'修改密码',
									width : 400,
									height : 180,
									closable : false,
									draggable : true,
									cache : false,
									modal : true,
									buttons : [ {
										iconCls : 'icon-ok',
										text : '确定',
										handler : function() {
											var new_password_1 = $.trim($('#new_password_1').val());
											var new_password_2 = $.trim($('#new_password_2').val());
											if(new_password_1 == new_password_2){
												$.ajax({
													url : '/userManager/updatePassword',
													type : "post",
													data : {
														user_id : userId,
														new_password_1 : new_password_1
													},
													async : false,
													cache : false, 
													dataType : 'json',
													success : function(r) {
//														var r = $.parseJSON(result);
														if(r.state == "ok"){
															$('#new_password_form').form('clear');//清空输入的数据    方式一
															$('#new_password_div').dialog('close');//关闭dialog
															$.messager.alert("提示","修改密码成功!","info");
														} else {
															$.messager.alert("提示","修改密码失败!","info");
														}
													},
													error : function(r){
														$.messager.alert("错误","修改密码失败,出现未知错误,请联系管理员!","error");
													}
												});
											} else {
												$.messager.alert("提示","两次输入的密码不一致，请重新输入!","info");
											}
										}
									},{
										iconCls : 'icon-back',
										text : '返回',
										handler : function() {
											$('#new_password_1').val('');
											$('#new_password_2').val('');
											$('#new_password_div').dialog('close');
										}
									}]
								});
							}
							if(r.state == "no") {
								$('#old_password').val('');//清空输入的数据
								//如果是错的就提示不正确
								$.messager.alert("提示",r.message,"info");
							}
						},
						error: function(r){
							$.messager.alert("错误","未知错误！","error");
						}
					});
				} else {
					$.messager.alert("提示","请输入旧密码！","info");
				}
			}
		},{
			iconCls : 'icon-cancel',
			text : '取消',
			handler : function() {
				$('#old_password').val('');//清空输入的数据
				$('#old_password_div').dialog('close');//关闭dialog
			}
		}]
	});
	
}	

//扩展easyui表单的验证
$.extend($.fn.validatebox.defaults.rules, {
	//验证汉子
	CHS: {
	  validator: function (value) {
	      return /^[\u0391-\uFFE5]+$/.test(value);
	  },
	  message: '请输入正确的汉字!'
	},
	//移动手机号码验证
	tel: {  //value值为文本框中的值
	  validator: function (value) {
	      var reg = /^1[3|4|5|6|8|9]\d{9}$/;
	      return reg.test(value);
	  },
	  message: '输入手机号码格式不准确!'
	},
	minLength: {   
			validator: function(value, param){ 
				return value.length >= param[0] && value.length <= param[1];    
			},   
			message: '微信号必须在4到20个字符之间!'  
	} 
});

