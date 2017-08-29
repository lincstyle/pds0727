package com.ctdcn.pds.sys.controller;

import com.ctdcn.pds.authority.service.ResourceService;
import com.ctdcn.pds.authority.service.RoleResourceService;
import com.ctdcn.pds.authority.service.RoleService;
import com.ctdcn.pds.organization.model.User;
import com.ctdcn.utils.WebUtils;
import com.ctdcn.utils.javascript.model.TreeNode;
import com.ctdcn.utils.spring.web.bind.CurrentUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 首页action.
 * @author 张靖
 *         2015-06-08 14:26.
 */
@Controller
@RequestMapping("/admin")
public class IndexController {

    private static Logger logger = LogManager.getLogger(IndexController.class.getName());
    
    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleResourceService roleResourceService;

    @RequestMapping("/index")
    public String index(HttpServletRequest request, ModelMap model,@CurrentUser User user){
        logger.debug("当前登录的用户为" + user.getAccount());
        model.put("user", user);
        model.put("roleName", user.getRoleOb().getRoleName());

        if(WebUtils.isMobile(request)){
            return "../mobile/index";
        }

        return "index";
    }

    @ResponseBody
    @RequestMapping("/showMenu")
    public List<TreeNode> showMenu(@CurrentUser User user,Integer id){
        id = id == null ? 0: id;
        List<TreeNode> menu = roleResourceService.getRoleResourceTree(user.getRole(),id,Boolean.TRUE);
        for (int i = 0 ,length = menu.size(); i < length ; i ++){
            TreeNode treeNode = menu.get(i);
            if (id == 0 ){
                treeNode.setState("closed");
            }
        }
        return menu;
    }



}
