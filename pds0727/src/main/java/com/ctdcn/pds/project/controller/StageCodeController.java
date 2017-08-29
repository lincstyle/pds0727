package com.ctdcn.pds.project.controller;

import com.ctdcn.pds.project.model.StageCode;
import com.ctdcn.pds.project.service.StageCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Triumphant on 2015/6/15.
 */
@Controller
@RequestMapping("/stagecode")
public class StageCodeController {

    @Autowired
    private StageCodeService stageCodeService;

    /**
     * 根据项目id查询此项目的项目阶段
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/querystage")
    public List createExcel(HttpServletRequest request,HttpServletResponse response) {
        String pid = request.getParameter("pid");
        return stageCodeService.queryStageByType(Integer.parseInt(pid));
    }

    @ResponseBody
    @RequestMapping("/queryAllStage")
    public Map queryAllStage(HttpServletRequest request,HttpServletResponse response) {
        String pid =request.getParameter("pid");
        Integer projectid =Integer.parseInt(pid);
        return stageCodeService.queryAllStage(projectid);
    }

    /**
     * 项目阶段维护
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/projectStage")
    public List<StageCode> projectStage(HttpServletRequest request){
        String pid = request.getParameter("pid");
        return stageCodeService.projectStage(Integer.parseInt(pid));
    }

    /**
     * 修改项目阶段
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/getProStageById")
    public List<StageCode> getProStageById(HttpServletRequest request){
        String pid = request.getParameter("pid");
        return stageCodeService.getProStageById(Integer.parseInt(pid));
    }

}
