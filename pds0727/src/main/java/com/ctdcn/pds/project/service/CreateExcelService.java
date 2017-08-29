package com.ctdcn.pds.project.service;

import com.ctdcn.pds.project.model.ProjectLog;
import com.ctdcn.pds.sys.model.SysCodeVo;
import com.ctdcn.utils.StringUtils;
import com.ctdcn.utils.consts.Sys;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/6/14.
 */
@Service
public class CreateExcelService
{

    /**
     * 获得excel 文件的数据源
     * @param Pname
     */

    @Autowired
    ProjectService projectService;
    @Autowired
    ProjectLogService projectLogService;
    public Map getExcelData(String pids,String updateIds,ProjectLog queryprLog)
    {
        //excel  数据源Map
        Map excelDataMap =new HashMap();
        //保存项目名的List
        List<String> pnameList=new ArrayList<String>();
        List<String> pidsList=new ArrayList<String>();
        List<String> updateIdsList=new ArrayList<String>();
        //查询条件Map
        Map searchMap = new HashMap();
        Map pSearchMap = new HashMap();
        //如果选择了项目名即查询项目名 如未选择即查询所有
        if(pids !=null && !"".equals(pids))
        {
        	pidsList = Arrays.asList(pids.split(","));
        	searchMap.put("pids", pidsList);
        }
        if(updateIds !=null && !"".equals(updateIds) )
        {
        	updateIdsList = Arrays.asList(updateIds.split(","));
        	searchMap.put("updateIds", updateIdsList);
        }
        searchMap.put("prLog", queryprLog);
        //List<ProjectLog> queryprLogList =projectLogService.queryPnameByTime(queryprLog);
        List<ProjectLog> queryprLogList =projectLogService.queryPidsByTime(searchMap);
        if(queryprLogList.isEmpty()){
        	throw new RuntimeException("没有符合条件的可导出日志");
        }
        for(ProjectLog p :queryprLogList)
        {
            //设置项目日志查询条件
        	queryprLog.setPid(p.getPid());
            if(updateIds !=null && !"".equals(updateIds) )
            {
            	updateIdsList = Arrays.asList(updateIds.split(","));
            	pSearchMap.put("updateIds", updateIdsList);
            }
            pSearchMap.put("prLog", queryprLog);
            //List<ProjectLog> prLogList=projectLogService.queryPrLog(queryPrLog);
            List<ProjectLog> prLogList=projectLogService.queryPrLog(pSearchMap);
            //遍历所有的符合条件的项目  向list里面添加项目名
            pnameList.add(p.getPname());
            //以项目名为key 该项目的   满足条件的  日志内容
            excelDataMap.put(p.getPname(),prLogList);
            pSearchMap.clear();
        }
        excelDataMap.put("pnameList",pnameList);
        return excelDataMap;
    }




    public File createExcel(Map<String,List> excelDataMap) throws Exception
    {

        List<String> pnameList=excelDataMap.get("pnameList");

            // 第一步，创建一个webbook，对应一个Excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
            //每个项目成为一个单独的 sheet
            String fileName ="";
            for(String pname:pnameList)
            {
                HSSFSheet sheet = wb.createSheet(pname);

                //设置  列宽
                sheet.setColumnWidth(0, 3500);
                sheet.setColumnWidth(1, 3500);
                sheet.setColumnWidth(2, 15000);
                sheet.setColumnWidth(3, 3500);
                sheet.setColumnWidth(4, 3500);

                // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
                HSSFRow row = sheet.createRow((int) 0);
                // 第四步，创建单元格，并设置值表头 设置表头居中
                HSSFCellStyle style = wb.createCellStyle();
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
                style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
                style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
                style.setWrapText(true);//设置自动换行
                HSSFFont font = wb.createFont();
                font.setFontHeightInPoints((short) 12);
                // 把字体应用到当前的样式
                style.setFont(font);
                //第一行
                HSSFCell cell = row.createCell((short) 0);
                cell.setCellValue("项目名称");
                cell.setCellStyle(style);



                CellRangeAddress region1 = new CellRangeAddress(0, 0, (short) 1, (short) 4);
                setRegionStyle(sheet,region1,style);
                sheet.addMergedRegion(region1);
                cell = row.createCell((short) 1);
                cell.setCellValue(pname);
                cell.setCellStyle(style);

                //第二行
                row = sheet.createRow((int) 1);

                cell = row.createCell((short) 0);
                cell.setCellValue("日报时间");
                cell.setCellStyle(style);
                cell = row.createCell((short) 1);
                cell.setCellValue("项目状态");
                cell.setCellStyle(style);
                cell = row.createCell((short) 2);
                cell.setCellValue("项目动态及解决办法");
                cell.setCellStyle(style);
                cell = row.createCell((short) 3);
                cell.setCellValue("更新人");
                cell.setCellStyle(style);
                cell = row.createCell((short) 4);
                cell.setCellValue("提交时间");
                cell.setCellStyle(style);

                // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
                List<ProjectLog> prLogList =excelDataMap.get(pname);
                for (int i = 0; i < prLogList.size(); i++)
                {
                    ProjectLog projectLog =prLogList.get(i);
                    row = sheet.createRow((int) i + 2);
                    // 第四步，创建单元格，并设置值
                    cell=row.createCell((short) 0);
                    cell.setCellValue(
                        new SimpleDateFormat("yyyy-MM-dd").format(projectLog.getSdate()));
                    cell.setCellStyle(style);
                    cell=row.createCell((short) 1);
                    cell.setCellValue(
                            projectLog.getPstage());
                    cell.setCellStyle(style);
                    cell=row.createCell((short) 2);
                    cell.setCellValue(
                            projectLog.getDetail());
                    cell.setCellStyle(style);
                    cell=row.createCell((short) 3);
                    cell.setCellValue(projectLog.getPerson());
                    cell.setCellStyle(style);
                    cell=row.createCell((short) 4);
                    cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(projectLog.getCdate()));
                    cell.setCellStyle(style);
                }
            }


            // 第六步，将文件存到指定位置
            FileOutputStream fout = null;
            //try
            //{
            	String folderName = SysCodeVo.getCode(Sys.FILE_NAME, Sys.WEEKLY_FOLDER_NAME).getValue();
            	File folder = new File(folderName);
            	if(!folder.exists()){
                	folder.mkdirs();
                }
            	fileName = folder + "/项目周报" + StringUtils.getRandomChars() + ".xls";
            	//fout = new FileOutputStream(folder+"/项目周报.xls");
            	fout = new FileOutputStream(fileName);
                wb.write(fout);
                fout.close();
                return new File(fileName);
            /*} catch (Exception e){
                e.printStackTrace();
            }*/
           
            
    }


    private void setRegionStyle(HSSFSheet sheet, CellRangeAddress region , HSSFCellStyle cs) {

        for (int i = region.getFirstRow(); i <=  region.getLastRow(); i ++) {
            HSSFRow row = sheet.getRow(i);
            if(region.getFirstColumn()!=region.getLastColumn()){
                for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
                    HSSFCell cell = row.createCell((short)j);
                    cell.setCellStyle(cs);
                }
            }
        }
    }


}
