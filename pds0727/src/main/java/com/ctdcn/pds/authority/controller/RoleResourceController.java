package com.ctdcn.pds.authority.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ctdcn.utils.javascript.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ctdcn.pds.authority.model.RoleResource;
import com.ctdcn.pds.authority.service.RoleResourceService;

@Controller
@RequestMapping("/roleResourceManager")
public class RoleResourceController {
	
	@Autowired
	private RoleResourceService roleResourceService;
	
	/**
	 * 得到所有的资源
	 *
	 */
	@ResponseBody
	@RequestMapping("/roleResourceList")
	public List<RoleResource> getRoleResourceList(){
		List<RoleResource> roleResourceList = roleResourceService.getRoleResourceList();
		return roleResourceList;
	}

    /**
     * 根据指定的角色ID得到此角色的资源
     * @param request
     * @return
     */
	@ResponseBody
	@RequestMapping("/getRoleResourceById")
	public List<RoleResource> getRoleResourceById(HttpServletRequest request){
		int roleId = Integer.parseInt(request.getParameter("role_id"));
		List<RoleResource> roleResourceList = roleResourceService.getRoleResourceById(roleId);
		return roleResourceList;
	}

	@ResponseBody
	@RequestMapping("/resourceTree")
	public List<TreeNode> showResourceTree(Integer roleId){

		List<TreeNode> root = roleResourceService.getRoleResourceTree(roleId, null,null);

		return root;
	}

	@ResponseBody
	@RequestMapping("/authorize")
	public Boolean authorize(@RequestParam(value = "add[]",required = false) Integer[] add,@RequestParam(value = "remove[]",required = false) Integer[] remove,Integer roleId){
		List<RoleResource> addList = new ArrayList<RoleResource>();
		List<RoleResource> removeList = new ArrayList<RoleResource>();

		if (add != null){
			for(Integer rid : add){
				addList.add(new RoleResource(roleId,rid));
			}
		}

		if (remove != null){
			for(Integer rid : remove){
				removeList.add(new RoleResource(roleId,rid));
			}
		}

		if(addList.size() > 0)
			roleResourceService.addRoleResource(addList.toArray(new RoleResource[addList.size()]));
		if(removeList.size() > 0)
			roleResourceService.delRoleResource(removeList.toArray(new RoleResource[removeList.size()]));

		return true;
	}
    
}
