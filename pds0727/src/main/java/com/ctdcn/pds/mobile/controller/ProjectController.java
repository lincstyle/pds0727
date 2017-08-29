package com.ctdcn.pds.mobile.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 张靖
 *         2015-08-25 10:50.
 */
@Controller
public class ProjectController {

    private static Logger log = LogManager.getLogger(ProjectController.class.getName());

    @RequestMapping("/mobile/project/{code}/detail/{id}")
    public String redirectDetail(@PathVariable(value="id") String id){
        log.debug("登录后详细信息转发");
        return "redirect:/mobile/project/#/detail/"+id;
    }
}
