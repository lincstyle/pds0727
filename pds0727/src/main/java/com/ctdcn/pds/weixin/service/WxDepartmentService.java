package com.ctdcn.pds.weixin.service;

import com.ctdcn.pds.organization.model.Department;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.cp.api.WxCpConfigStorage;
import me.chanjar.weixin.cp.api.WxCpServiceImpl;
import me.chanjar.weixin.cp.bean.WxCpDepart;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 微信操作部门类.
 * @author 张靖
 *         2015-06-17 11:35.
 */
@Service
public class WxDepartmentService {

    @Resource
    WxCpConfigStorage wxCpConfigStorage;

    @Resource
    WxCpServiceImpl wxCpService;

    /**
     * 增加部门.
     * @param department
     * @return Integer 新增的部门Id
     * @throws WxErrorException
     */
    public Integer addDepart(Department department) throws WxErrorException {
        return  wxCpService.departCreate(toWx(department));
    }

    /**
     * 删除部门.
     * @param departId
     * @throws WxErrorException
     */
    public void removeDepart(Integer departId) throws WxErrorException {
        wxCpService.departDelete(departId);
    }

    /**
     * 修改部门.
     * @param department
     * @throws WxErrorException
     */
    public void editDepart(Department department) throws WxErrorException {
        wxCpService.departUpdate(toWx(department));
    }

    public static WxCpDepart toWx(Department department){
        WxCpDepart wxCpDepart = new WxCpDepart();
        wxCpDepart.setId(department.getDepartmentId());
        wxCpDepart.setName(department.getDepartmentName());
        wxCpDepart.setParentId(department.getParentId());
        wxCpDepart.setOrder(department.getDepartmentId());
        return wxCpDepart;
    }

    public static Department toWx(WxCpDepart wxCpDepart)
    {
        Department department =new Department();
        department.setDepartmentId(wxCpDepart.getId());
        department.setDepartmentName(wxCpDepart.getName());
        department.setParentId(wxCpDepart.getParentId());
        department.setSort(wxCpDepart.getOrder());
        return  department;
    }


}
