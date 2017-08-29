package com.ctdcn.pds.shiro;

import com.ctdcn.pds.authority.model.Role;
import com.ctdcn.pds.authority.service.ResourceService;
import com.ctdcn.pds.authority.service.RoleService;
import com.ctdcn.pds.organization.model.User;
import com.ctdcn.pds.organization.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 张靖.
 *         2015-06-06 0:41
 */
public class UserRealm extends AuthorizingRealm {

    private static Logger log = LogManager.getLogger(UserRealm.class.getName());

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String account = (String) principals.getPrimaryPrincipal();
        User user = userService.queryUserByAccount(account);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        Role role = roleService.getRoleById(user.getRole());
        Set<String> roleSet = new HashSet<String>();
        roleSet.add(role.getRole());
        simpleAuthorizationInfo.setRoles(roleSet);

        Set<String> permissionsSet = new HashSet<String>(resourceService.querySpliceAuthority(role.getRoleId(), 3));
        simpleAuthorizationInfo.setStringPermissions(permissionsSet);
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        String account = upToken.getUsername().trim();
        String password = "";

        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }

        User user =null;
        try{
            user =  userService.queryUserByAccount(account);
        }catch (Exception e){
            e.printStackTrace();
        }


        if (user == null){
            //这个地方让我觉得最逗比的就是，如果你在这里返回了错误信息，并且使用默认的FormauthenicationFilter的话，那么错误信息是传递不到更上级的。
            throw new UnknownAccountException();
        }
        if(!validatePassWord(user,password)){
            throw new AuthenticationException();
        }

        user.setRoleOb(roleService.getRoleById(user.getRole()));
        SecurityUtils.getSubject().getSession().setAttribute(User.USER_SESSIN_KEY,user);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getAccount(), password.toCharArray(), getName());

        return info;
    }

    /**
     * 检查是否匹配的用户.
     * @param user
     * @param password
     * @return
     */
    public boolean validatePassWord(User user,String password){
        //TODO 先期版本不考虑加密
        return user.getPwd().equals(password);
    }
}
