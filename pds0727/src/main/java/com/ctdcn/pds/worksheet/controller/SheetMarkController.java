package com.ctdcn.pds.worksheet.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctdcn.pds.authority.model.ReturnBean;
import com.ctdcn.pds.worksheet.model.SheetMark;
import com.ctdcn.pds.worksheet.service.SheetMarkService;

@Controller
@RequestMapping("/sheetMark")
public class SheetMarkController {
	@Autowired
	private SheetMarkService sheetMarkService;
	
	//查询备注
	@ResponseBody
	@RequestMapping("/queryMark")
	public List<SheetMark> queryMark(HttpServletRequest request){
		SheetMark sheetMark = new SheetMark();
		String id =request.getParameter("id");
		if(id !=null){
			sheetMark.setId( Integer.parseInt(id));
		}
		String sheetId = request.getParameter("sheetId");
		if(sheetId !=null){
			sheetMark.setSheetId(Integer.parseInt(sheetId));
		}
		return sheetMarkService.queryMark(sheetMark);
	}
	
	//新增备注
	@ResponseBody
	@RequestMapping("/addMark")
	public ReturnBean addMark(HttpServletRequest request){
		ReturnBean rb = new ReturnBean();
		try {
			SheetMark sheetMark =new SheetMark();
			Integer sheetId = Integer.parseInt(request.getParameter("sheetId"));
			Integer sendId = Integer.parseInt(request.getParameter("sendId"));
			String mark = request.getParameter("mark");
			Date date = new Date();
			sheetMark.setSheetId(sheetId);
			sheetMark.setSendId(sendId);
			sheetMark.setMark(mark);
			sheetMark.setSdate(date);
			sheetMarkService.addMark(sheetMark);
			rb.setState("ok");
			rb.setMessage("新增备注成功!");
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("新增备注失败!");
			e.printStackTrace();
		}
		return rb;
	}
	
	//修改备注
	@ResponseBody
	@RequestMapping("/updateMark")
	public ReturnBean updateMark(HttpServletRequest request,SheetMark sheetMark){
		ReturnBean rb = new ReturnBean();
		try {
			Date date = new Date();
			sheetMark.setSdate(date);
			sheetMarkService.updateMark(sheetMark);
			rb.setState("ok");
			rb.setMessage("修改备注成功!");
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("修改备注失败!");
			e.printStackTrace();
		}
		return rb;
	}
	
	//删除备注
	@ResponseBody
	@RequestMapping("/deleteMark")
	public ReturnBean deleteMark(HttpServletRequest request){
		ReturnBean rb = new ReturnBean();
		try {
			Integer id = Integer.parseInt(request.getParameter("markId"));
			SheetMark sheetMark = new SheetMark();
			sheetMark.setId(id);
			sheetMarkService.deleteMark(sheetMark);
			rb.setState("ok");
			rb.setMessage("删除备注成功!");
		} catch (Exception e) {
			rb.setState("error");
			rb.setMessage("删除备注失败!");
			e.printStackTrace();
		}
		return rb;
	}
}
