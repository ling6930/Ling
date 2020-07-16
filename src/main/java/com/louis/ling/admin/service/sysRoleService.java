package com.louis.ling.admin.service;

import com.louis.ling.admin.model.sysMenu;
import com.louis.ling.admin.model.sysRole;
import com.louis.ling.admin.model.sysRoleMenu;
import com.louis.ling.admin.service.curdService;

import java.util.List;

/**
 * 角色管理
 * @author Louis
 * @date Oct 29, 2018
 */
public interface sysRoleService extends curdService<sysRole> {

    /**
     * 查询全部
     * @return
     */
    List<sysRole> findAll();

    /**
     * 查询角色菜单集合
     * @return
     */
    List<sysMenu> findRoleMenus(Long roleId);

    /**
     * 保存角色菜单
     * @param records
     * @return
     */
    int saveRoleMenus(List<sysRoleMenu> records);

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    List<sysRole> findByName(String name);

}
