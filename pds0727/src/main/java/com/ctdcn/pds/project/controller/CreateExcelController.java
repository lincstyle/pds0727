package com.ctdcn.pds.project.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.project.model.ProjectLog;
import com.ctdcn.pds.project.service.CreateExcelService;
import com.ctdcn.utils.FileUtils;
import com.ctdcn.utils.spring.web.bind.CurrentUser;


/**
 * Created by Triumphant on 2015/6/14.
 */
@Controller
@RequestMapping("/export")
public class CreateExcelController
{

    /**
     * 根据选择条件生产excel文件
     */

    @Autowired private CreateExcelService createExcelService;

    @RequestMapping("/createExcel")
    public void createExcel(HttpServletRequest request,HttpServletResponse response,@CurrentUser User user)//throws IOException
    {
        String updateIds=request.getParameter("updateIds");
        String pids=request.getParameter("pids");
        ProjectLog queryprLog =new ProjectLog();
        queryprLog.setSelectSDate(request.getParameter("export_sdate"));
        queryprLog.setSelectEDate(request.getParameter("export_edate"));
        queryprLog.setIsdelete(0);
        if (!SecurityUtils.getSubject().isPermitted("project:Projectlog:queryAllPrLog")) {
        	queryprLog.setUserId(user.getId());
        }
        InputStream is = null;
        OutputStream out = null;
        File file = null;
        String filename = "";
        try {
            Map excelDataMap=createExcelService.getExcelData(pids,updateIds,queryprLog);
            file = createExcelService.createExcel(excelDataMap);
            //String fileName = SysCodeVo.getCode(Sys.FILE_NAME, Sys.WEEKLY_FILE_NAME).getValue();
           // File file =new File(fileName);
            filename ="项目周报"+ new DateTime().toString("yyyyMMddmmhhss")+".xls";
            response.reset();
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes("GBK"), "iso8859-1"));
            response.setContentType("application/octet-stream");
            is = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(response.getOutputStream());
            byte[] buf = new byte[1024];
            int count = 0;
            while ((count = is.read(buf)) != -1) {
                out.write(buf, 0, count);
            }
            out.flush();
        }catch (Exception e )
        {
            e.printStackTrace();
            response.setContentType("text/html;charset=GBK");
            try {
            	String message = e.getMessage().replaceAll("\\\\", "\\\\\\\\");
				response.getWriter().print("<script>alert('"+message+"')</script>");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            return;
        }finally {
        	if(is != null){
        		try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
            if(out != null){
            	try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
			if (file != null) {
				try {
					FileUtils.deleteFile(file);						
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
            
        }

    }


}
