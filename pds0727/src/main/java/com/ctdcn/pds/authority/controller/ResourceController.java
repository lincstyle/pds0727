package com.ctdcn.pds.authority.controller;

import com.ctdcn.pds.authority.model.Resource;
import com.ctdcn.pds.authority.service.ResourceService;
import com.ctdcn.utils.javascript.model.TreeNode;
import com.google.common.collect.Collections2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/resourceManager")
public class ResourceController {


	@Autowired
    private ResourceService resourceService;

    /**
     * 查询资源
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/showResource")
    public List<Resource> showResource(Integer id){
        List<Resource> resourceList = resourceService.queryResourceWith(id);
        return resourceList;
    }

    /**
     * 添加资源
     * @param resource
     * @param flag
     * @return
     */
    @ResponseBody
    @RequestMapping("/add/{flag}")
    public Boolean add(Resource resource,@PathVariable(value="flag") Integer flag){
        if (flag == 1 && resource.getWidth() == 3){
            throw  new RuntimeException("不能为最后一级添加子节点");
        }
        resource.setWidth(resource.getWidth()+flag);
        Integer sort = null;
        if(flag == 1){
            sort = resourceService.getMax(resource.getId());
            resource.setParentId(resource.getId());
        }else{
            sort = resourceService.getMax(resource.getParentId());
        }
        if(sort != null ){
            sort++;
        }else{
            sort = 1;
        }
        resource.setSort(sort);

        resourceService.addResource(resource);
        return true;
    }

    /**
     * 修改
     * @param resource
     * @return
     */
    @ResponseBody
    @RequestMapping("/edit")
    public Boolean edit(Resource resource){
        Resource updater = new Resource();
        updater.setUrl(resource.getUrl());
        updater.setIdentity(resource.getIdentity());
        updater.setName(resource.getName());
        updater.setIsShow(resource.getIsShow());

        Resource cond = new Resource();
        cond.setId(resource.getId());

        resourceService.updateResource(updater, cond);

        return true;
    }

    /**
     * 删除
     * @param resource
     * @return
     */
    @ResponseBody
    @RequestMapping("/remove")
    public Boolean remove(Resource resource){
        Integer id = resource.getId();
        List<Resource> children = resourceService.queryChildren(id);
        resourceService.batchDelete(children);
        resourceService.move(resource.getSort(), null, resource.getParentId());
        return true;
    }

    /**
     * 移动 更改顺序
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/move")
    public Boolean move(HttpServletRequest request){
        Integer originalId = Integer.valueOf(request.getParameter("original[id]"));
        Integer originalSort = Integer.valueOf(request.getParameter("original[sort]"));
        Integer originalParentId = Integer.valueOf(request.getParameter("original[parentId]"));
        Integer targetSort = Integer.valueOf(request.getParameter("target[sort]"));


        resourceService.move(originalSort, targetSort, originalParentId);

        Resource org = new Resource();
        Resource cond = new Resource();
        cond.setId(originalId);
        org.setSort(targetSort);
        resourceService.updateResource(org,cond);

        return true;
    }




}
