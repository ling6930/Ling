package com.louis.ling.admin.service;

import java.util.List;
import java.util.Set;

import com.louis.ling.admin.model.sysUser;
import com.louis.ling.admin.model.sysUserRole;

public interface sysUserService extends curdService<sysUser>{
    /**
     * 根据用户ID查找用户
     * @param userId
     * @return
     */
    sysUser findByUserId(Long userId);

    /**
     * 查找所有用户
     * @return
     */
    List<sysUser> findAll();

    sysUser findByName(String username);

    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    Set<String> findPermissions(String userName);

    /**
     * 查找用户的角色集合
     * @param userId
     * @return
     */
    List<sysUserRole> findUserRoles(Long userId);
}
