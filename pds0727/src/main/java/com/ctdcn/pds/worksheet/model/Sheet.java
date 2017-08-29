package com.ctdcn.pds.worksheet.model;


import java.util.Date;



import com.alibaba.fastjson.annotation.JSONField;

/**
 * 联络单
 * @author Administrator
 *
 */
public class Sheet {
	private Integer id;					//联络单ID
	private String no;					//编号
	private Integer signId;				//签发人ID
	private String signName;			//签发人
	private Integer sendDeptId;		//发文部门ID
	private String sendDept;			//发文部门
	private Integer sendId;				//拟稿人ID
	private String sendName;			//拟稿人
	@JSONField (format="yyyy-MM-dd")
	private Date sendDate;				//发文时间
	private Integer sendType;			//项目类别		(1.数据采集, 2.电子档案, 3.卡管系统,4.其他类型)
	private String sendTheme;		//发文主题
	private String sendText;			//发文内容
	private Integer importLv;			//重要程度		(1.一般, 2.重要, 3.很重要)
	private Integer handleLv;			//处理缓急程度		(1.一般, 2.急, 3.紧急)
	private String endDate;			//处理时间要求
	private Integer checkId;			//发出人ID
	private String checkName;		//发出人
	private Integer notifyDeptId;		//抄送部门ID
	private String notifyDept;			//抄送部门
	private Integer handleDeptId;	//主送部门(处理部门)ID
	private String handleDept;		//主送部门(处理部门)
	private Integer manageId;		//负责人ID
	private String manageName;	//负责人
	private Integer handleId;			//执行人ID
	private String handleName;		//执行人
	private String handleText;		//反馈信息(备注信息)
	private Integer handleState;		//项目状态		(0.待审核，1.待分配，2.未开始，3.进行中，4.延后，5.已完成，6.取消)
	private String sendWeek;			//联络单建立周
	
	private String sendDate_start; 	//浏览器选择的联络单发送开始时间
	private String sendDate_end;		//浏览器选择的联络单发送结束时间
	private String endDate_start;		//浏览器选择的联络单要求开始时间
	private String endDate_end;		//浏览器选择的联络单要求结束时间
	
	
	public String getSendWeek() {
		return sendWeek;
	}
	public void setSendWeek(String sendWeek) {
		this.sendWeek = sendWeek;
	}
	public String getSendDate_start() {
		return sendDate_start;
	}
	public void setSendDate_start(String sendDate_start) {
		this.sendDate_start = sendDate_start;
	}
	public String getSendDate_end() {
		return sendDate_end;
	}
	public void setSendDate_end(String sendDate_end) {
		this.sendDate_end = sendDate_end;
	}
	public String getEndDate_start() {
		return endDate_start;
	}
	public void setEndDate_start(String endDate_start) {
		this.endDate_start = endDate_start;
	}
	public String getEndDate_end() {
		return endDate_end;
	}
	public void setEndDate_end(String endDate_end) {
		this.endDate_end = endDate_end;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public Integer getSignId() {
		return signId;
	}
	public void setSignId(Integer signId) {
		this.signId = signId;
	}
	public String getSignName() {
		return signName;
	}
	public void setSignName(String signName) {
		this.signName = signName;
	}
	public Integer getSendDeptId() {
		return sendDeptId;
	}
	public void setSendDeptId(Integer sendDeptId) {
		this.sendDeptId = sendDeptId;
	}
	public String getSendDept() {
		return sendDept;
	}
	public void setSendDept(String sendDept) {
		this.sendDept = sendDept;
	}
	public Integer getSendId() {
		return sendId;
	}
	public void setSendId(Integer sendId) {
		this.sendId = sendId;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}
	public Integer getSendType() {
		return sendType;
	}
	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}
	public String getSendTheme() {
		return sendTheme;
	}
	public void setSendTheme(String sendTheme) {
		this.sendTheme = sendTheme;
	}
	public String getSendText() {
		return sendText;
	}
	public void setSendText(String sendText) {
		this.sendText = sendText;
	}
	public Integer getImportLv() {
		return importLv;
	}
	public void setImportLv(Integer importLv) {
		this.importLv = importLv;
	}
	public Integer getHandleLv() {
		return handleLv;
	}
	public void setHandleLv(Integer handleLv) {
		this.handleLv = handleLv;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Integer getCheckId() {
		return checkId;
	}
	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}
	public String getCheckName() {
		return checkName;
	}
	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}
	public Integer getNotifyDeptId() {
		return notifyDeptId;
	}
	public void setNotifyDeptId(Integer notifyDeptId) {
		this.notifyDeptId = notifyDeptId;
	}
	public String getNotifyDept() {
		return notifyDept;
	}
	public void setNotifyDept(String notifyDept) {
		this.notifyDept = notifyDept;
	}
	public Integer getHandleDeptId() {
		return handleDeptId;
	}
	public void setHandleDeptId(Integer handleDeptId) {
		this.handleDeptId = handleDeptId;
	}
	public String getHandleDept() {
		return handleDept;
	}
	public void setHandleDept(String handleDept) {
		this.handleDept = handleDept;
	}
	public Integer getManageId() {
		return manageId;
	}
	public void setManageId(Integer manageId) {
		this.manageId = manageId;
	}
	public String getManageName() {
		return manageName;
	}
	public void setManageName(String manageName) {
		this.manageName = manageName;
	}
	public Integer getHandleId() {
		return handleId;
	}
	public void setHandleId(Integer handleId) {
		this.handleId = handleId;
	}
	public String getHandleName() {
		return handleName;
	}
	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}
	public String getHandleText() {
		return handleText;
	}
	public void setHandleText(String handleText) {
		this.handleText = handleText;
	}
	public Integer getHandleState() {
		return handleState;
	}
	public void setHandleState(Integer handleState) {
		this.handleState = handleState;
	}
}
