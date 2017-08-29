package com.ctdcn.pds.worksheet.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ctdcn.pds.authority.model.ReturnBean;
import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.worksheet.model.Sheet;
import com.ctdcn.pds.worksheet.model.SheetMark;
import com.ctdcn.pds.worksheet.service.SheetMarkService;
import com.ctdcn.pds.worksheet.service.SheetNo;
import com.ctdcn.pds.worksheet.service.SheetService;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Controller
@RequestMapping("/sheetManager")
public class SheetController {
	
	@Autowired
	private SheetNo sheetNo;
	
	@Autowired
	private SheetService sheetService;
	
	@Autowired
	private SheetMarkService sheetMarkService;
	
	/**
	 * 联络单跟踪列表
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sheetList")
	public Map<String,Object> sheetList(HttpServletRequest request){
		int page = Integer.parseInt(request.getParameter("page"));
        int rows = Integer.parseInt(request.getParameter("rows"));
		
		String uId = request.getParameter("userId");
		Integer userId = null;
		if(uId !=null){
			userId = Integer.parseInt(uId);
		}
		
		Sheet sheet = new Sheet();
		String handleState = request.getParameter("handleState");
		String sendId = request.getParameter("sendId");
		String sendName = request.getParameter("sendName");
		String checkId = request.getParameter("checkId");
		String manageId = request.getParameter("manageId");
		String handleId = request.getParameter("handleId");
		String sendDeptId =request.getParameter("sendDeptId");
		String sendDept = request.getParameter("sendDept");
		String handleDeptId =request.getParameter("handleDeptId");
		String sendDate_start =request.getParameter("sendDate_start");
		String sendDate_end =request.getParameter("sendDate_end");
		String endDate_start =request.getParameter("endDate_start");
		String endDate_end =request.getParameter("endDate_end");
		if(handleState !=null && !"".equals(handleState)){
			sheet.setHandleState(Integer.parseInt(handleState));
		}
		if(sendId !=null && !"".equals(sendId)){
			sheet.setSendId(Integer.parseInt(sendId));
		}
		if(sendName !=null && !"".equals(sendName)){
			sheet.setSendName(sendName);
		}
		if(checkId !=null && !"".equals(checkId)){
			sheet.setCheckId(Integer.parseInt(checkId));
		}
		if(manageId !=null && !"".equals(manageId)){
			sheet.setManageId(Integer.parseInt(manageId));
		}
		if(handleId !=null && !"".equals(handleId)){
			sheet.setHandleId(Integer.parseInt(handleId));
		}
		if(sendDeptId !=null && !"".equals(sendDeptId)){
			sheet.setSendDeptId(Integer.parseInt(sendDeptId));
		}
		if(sendDept !=null && !"".equals(sendDept)){
			sheet.setSendDept(sendDept);
		}
		if(handleDeptId !=null && !"".equals(handleDeptId)){
			sheet.setHandleDeptId(Integer.parseInt(handleDeptId));
		}
		if(sendDate_start !=null && !"".equals(sendDate_start)){
			sheet.setSendDate_start(sendDate_start);
		}
		if(sendDate_end !=null && !"".equals(sendDate_end)){
			sheet.setSendDate_end(sendDate_end);
		}
		if(endDate_start !=null && !"".equals(endDate_start)){
			sheet.setEndDate_start(endDate_start);
		}
		if(endDate_end !=null && !"".equals(endDate_end)){
			sheet.setEndDate_end(endDate_end);
		}
		
		
		Integer total = sheetService.countSheet(sheet,userId);
		 
		List<Sheet> sheetList = sheetService.sheetList(sheet,userId,page, rows);
		Map sheetMap =new HashMap();
		sheetMap.put("total",total);
		sheetMap.put("rows",sheetList);
        return sheetMap;
	}
	
	/**
	 * 联络单详情查询
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/querySheet")
	public  Sheet querySheet(HttpServletRequest request,Integer id){
		Sheet sheet=sheetService.querySheet(id);
		return sheet;
	}
	
	/**
	 * 新增联络单
	 * @param request
	 * @param sheet
	 */
	@ResponseBody
	@RequestMapping("/addSheet")
	public ReturnBean addSheet(HttpServletRequest request,Sheet sheet){
		ReturnBean rb = new ReturnBean();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c =Calendar.getInstance();
			c.setTime(sheet.getSendDate());
			Integer w = c.get(Calendar.WEEK_OF_YEAR);
			Integer y = c.get(Calendar.YEAR);
			c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
			String Mdate = sdf.format(c.getTime());
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			String Fdate = sdf.format(c.getTime());
			String sendWeek= (y+"年，第"+w+"周("+Mdate+"至"+Fdate+")");
			sheet.setSendWeek(sendWeek);
			sheetService.addSheet(sheet);
			rb.setState("ok");
			rb.setMessage("新增联络单成功!");
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("新增联络单失败!");
			e.printStackTrace();
		}
		return rb;
	}

	/**
	 * 联络单核查、分发、处理、转交
	 * @param request
	 * @param sheet
	 */
	@ResponseBody
	@RequestMapping("/updateSheet")
	public ReturnBean updateSheet(HttpServletRequest request, Sheet sheet){
		ReturnBean rb = new ReturnBean();
		try {
			if(sheet.getSendDate() !=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c =Calendar.getInstance();
			c.setTime(sheet.getSendDate());
			Integer w = c.get(Calendar.WEEK_OF_YEAR);
			Integer y = c.get(Calendar.YEAR);
			c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
			String Mdate = sdf.format(c.getTime());
			c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
			String Fdate = sdf.format(c.getTime());
			String sendWeek= (y+"年，第"+w+"周("+Mdate+"至"+Fdate+")");
			sheet.setSendWeek(sendWeek);
			}
			sheetService.updateSheet(sheet);
			rb.setState("ok");
			rb.setMessage("操作成功!");
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("操作失败!");
			e.printStackTrace();
		}
		return rb;
	}
	
	/**
	 * 删除联络单
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/deleteSheet")
	public ReturnBean deleteSheet(HttpServletRequest request){
		ReturnBean rb = new ReturnBean();
		try {
			String sheetId=request.getParameter("id");
			Integer id = Integer.parseInt(sheetId);
			sheetService.deleteSheet(id);
			SheetMark sheetMark = new SheetMark();
			sheetMark.setSheetId(id);
			sheetMarkService.deleteMark(sheetMark);
			rb.setState("ok");
			rb.setMessage("删除成功!");
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("删除失败!");
			e.printStackTrace();
		}
		return rb;
	}
	
	/**
	 * 获取联络单号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getNo")
	public String getNo(HttpServletRequest request){
		String no = sheetNo.getNo();
		String sno = sheetNo.nextNo(no);
		sheetNo.setNo(sno);
		return sno;
	}
	/**
	 * 获取当前用户信息
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getUser")
	public User getUser(HttpServletRequest request){
		User user=(User)request.getSession().getAttribute(User.USER_SESSIN_KEY);
		return user;
	}
	
	/**
	 * 获取某部门的全部人员
	 * @param departmentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/queryDeptUser")
	public List queryDeptUser(Integer departmentId)
	{
		return sheetService.queryDeptUser(departmentId);
	}
	
	/**
	 * 获取某联络单数量
	 * @param request
	 * @param sheet
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/countSheet")
	public Integer countSheet(HttpServletRequest request,Sheet sheet,Integer userId){
		return sheetService.countSheet(sheet, userId);
	}
	
	/**
	 * 导出联络单
	 * @param request
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/expSheet")
	@SuppressWarnings("unchecked")
	public  void expSheet(HttpServletRequest request,HttpServletResponse response,Integer id) throws Exception{
		try{
			Sheet sheet = sheetService.querySheet(id);
			String path = request.getSession().getServletContext().getRealPath("/")+ "/view/worksheet/";
			String fileName = "工作联络单_" + sheet.getNo() + "_"+ sheet.getSendTheme() + ".doc";

			// 联络单数据
			Map dataMap = new HashMap();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String sendDate = sdf.format(sheet.getSendDate());
			if (sheet.getNo() != null) {
				dataMap.put("no", sheet.getNo());
			} else {
				dataMap.put("no", "");
			}
			if (sheet.getSignName() != null) {
				dataMap.put("signName", sheet.getSignName());
			} else {
				dataMap.put("signName", "");
			}
			if (sendDate != null) {
				dataMap.put("sendDate", sendDate);
			} else {
				dataMap.put("sendDate", "");
			}
			if (sheet.getSendDept() != null) {
				dataMap.put("sendDept", sheet.getSendDept());
			} else {
				dataMap.put("sendDept", "");
			}
			if (sheet.getSendName() != null) {
				dataMap.put("sendName", sheet.getSendName());
			} else {
				dataMap.put("sendName", "");
			}
			if (sheet.getHandleDept() != null) {
				dataMap.put("handleDept", sheet.getHandleDept());
			} else {
				dataMap.put("handleDept", "");
			}
			if (sheet.getNotifyDept() != null) {
				dataMap.put("notifyDept", sheet.getNotifyDept());
			} else {
				dataMap.put("notifyDept", "");
			}
			if (sheet.getSendTheme() != null) {
				dataMap.put("sendTheme", sheet.getSendTheme());
			} else {
				dataMap.put("sendTheme", "");
			}
			if (sheet.getSendText() != null) {
				dataMap.put("sendText","<w:p></w:p>"+sheet.getSendText().replace("\n", "<w:p></w:p>").replace("  ", " "));
			} else {
				dataMap.put("sendText", "");
			}
			if (sheet.getHandleText() != null) {
				dataMap.put("handelText","<w:p></w:p>"+sheet.getHandleText().replace("\n", "<w:p></w:p>").replace("  ", " "));
			} else {
				dataMap.put("handelText", "");
			}
			switch (sheet.getImportLv()) {
			case 1:
				dataMap.put("importLv1", "■");
				dataMap.put("importLv2", "□");
				dataMap.put("importLv3", "□");
				break;
			case 2:
				dataMap.put("importLv1", "□");
				dataMap.put("importLv2", "■");
				dataMap.put("importLv3", "□");
				break;
			case 3:
				dataMap.put("importLv1", "□");
				dataMap.put("importLv2", "□");
				dataMap.put("importLv3", "■");
				break;
			}
			switch (sheet.getHandleLv()) {
			case 1:
				dataMap.put("handleLv1", "■");
				dataMap.put("handleLv2", "□");
				dataMap.put("handleLv3", "□");
				break;
			case 2:
				dataMap.put("handleLv1", "□");
				dataMap.put("handleLv2", "■");
				dataMap.put("handleLv3", "□");
				break;
			case 3:
				dataMap.put("handleLv1", "□");
				dataMap.put("handleLv2", "□");
				dataMap.put("handleLv3", "■");
				break;
			}

			// 生成word
			Configuration configuration = new Configuration();
			configuration.setDefaultEncoding("utf-8");
			configuration.setDirectoryForTemplateLoading(new File(path));
			File outFile = new File(path + fileName);
			Template t = configuration.getTemplate("worksheetModel.ftl","utf-8");
			Writer outdata = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"), 10240);
			t.process(dataMap, outdata);
			outdata.close();
			
			// 下载word
			File file = new File(path + fileName);
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(file));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header，此处要对中文进行编码处理。
			response.addHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
			if (file.isFile() && file.exists()) {
				file.delete();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

