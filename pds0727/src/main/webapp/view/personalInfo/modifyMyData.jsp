<%--修改个人资料 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="/static/js/jsp-js/personalInfo/modifyMyData.js" ></script>

<div id="modify_user" align="center" >
	<form id="modify_user_form" method="post" name="modifyMyDataForm" >
		<input id="modify_id" name="id" type="hidden" value="${id }">
		<input id="modify_departmentId" name="departmentId" type="hidden" value="${departmentId }">
		<input id="modify_roleId" type="hidden" value="${roleId }"> 
		<table style="margin: 15px auto;width:550px;height: 300px;" border="0px" >
			<tr height="30px;">
				<td>姓名：</td>
				<td>
					<input id="modify_name" name="name" class="easyui-validatebox" required=true validtype='CHS' value="${name }">
				</td>
				<td>账号：</td>
				<td>
					<label id="modify_account">${account }</label>
				</td>	
			</tr>
			<tr height="30px;">
				<td>性别：</td>
				<td colspan="3">
					<c:if test="${gender == 1}">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						男 <input name="gender" type="radio" value="1" checked="checked" >
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						女 <input name="gender" type="radio" value="0" >
					</c:if>
					<c:if test="${gender == 0}">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						男 <input name="gender" type="radio" value="1"  >
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						女 <input name="gender" type="radio" value="0" checked="checked">
					</c:if>
				</td>	
			</tr> 
			<tr height="30px;" align="center">
				<td colspan="4">
					<font color="#7F7F7F">身份验证信息
						<font color="red" size="1px;">（以下三种信息用于绑定微信,不可同时为空.）</font>
					</font>
				</td>
			</tr>
			<tr height="30px;">
				<td>手机号：</td>
				<td><input id="modify_tel" name="tel" class="easyui-validatebox" validtype='tel' value="${tel }"></td>	
				<td>电子邮箱：</td>
				<td><input id="modify_email" name="email" class="easyui-validatebox" data-options="required:false,validType:'email'" value="${email }"></td>
			</tr>	
			<tr height="30px;">
				<td>微信号：</td>
				<td><input id="modify_weixin" name="weixin" class="easyui-validatebox" data-options="required:false,validType:'minLength[2,20]'" value="${weixin }"></td>
			</tr>
			<tr height="30px;">
				<td colspan="4"></td>
			</tr>
			<tr height="30px;">
				<td>部门名称：</td>
				<td>
					<label name="departmentName">${departmentName }</label>
				</td>
				<td>角色：</td>
				<td>
					<label name="role">${role }</label>
				</td>
			</tr>
			<tr height="30px;" align="center">
				<td colspan="4">
					<button id="save" type="button" >保存</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button id="reset" type="reset" >重置</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button id="editPwd" type="button">修改密码</button>
					 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<button id="upload_img" type="button">上传照片</button>
					
				</td>
			</tr>
		</table>
	</form>
</div>

 
<div id="upload_img_div" style="display: none; margin-top: 10px;" align="center" >
   <form id="upload_form" method="post" enctype="multipart/form-data">
       <input id="upload_Id" name="upload_Id" type="hidden" value="${id }"> 
       <table>
            <tr>
                <td>请选择上传照片：</td>
                <td>
	            	<input id="uploadfile" name="uploadfile" class="easyui-filebox" />
                </td>
            </tr>
            <tr>
            	<td colspan="2" align="center" style="padding-top: 10px;">
            		<font id="error" color="red"></font>
            	</td>
            </tr>
            <!-- 
            <tr>
                <td></td>
                <td><input type="submit" value="确定"></input></td>
       		</tr>
			-->
       </table>
    </form>
</div>

 
<div id="old_password_div" style="padding-top:20px; display: none;" align="center">
	<%-- <input id="modify_id" name="id" type="hidden" value="${id }"> --%>
	请输入旧密码：<input id="old_password" type="password" size="15px" >
</div>
<div id="new_password_div" style="padding-top:20px; display: none;" align="center">
	<form id="new_password_form">
		<table>
			<tr>
				<td align="right">
					请输入新密码：
				</td>
				<td>
					<input id="new_password_1" type="password" size="15px" >
				</td>
			</tr>
				<td align="right">
					请确认输入新密码：
				</td>
				<td>
					<input id="new_password_2" type="password" size="15px" >
				</td>
			</tr>
		</table>
	</form>
</div>

<div id="bg" class="panel-default navbar-fixed-bottom"
	 style="display: none; position: fixed; top: 0%; left: 0%; width: 100%; height: 100%; background-color: black; z-index:1030;
	  -moz-opacity: 0.4; opacity:0.4; filter: alpha(opacity=70);" ></div>