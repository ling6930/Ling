package com.louis.ling.admin.controller;

import com.louis.ling.admin.constants.sysConstants;
import com.louis.ling.admin.dao.sysRoleMapper;
import com.louis.ling.admin.http.HttpResult;
import com.louis.ling.admin.model.sysRole;
import com.louis.ling.admin.model.sysRoleMenu;
import com.louis.ling.admin.page.PageRequest;
import com.louis.ling.admin.service.sysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色控制器
 * @author Louis
 * @date Oct 29, 2018
 */
@RestController
@RequestMapping("role")
public class sysRoleController {

    @Autowired
    private sysRoleService sysRoleService;
    @Autowired
    private sysRoleMapper sysRoleMapper;

    @PostMapping(value="/save")
    public HttpResult save(@RequestBody sysRole record) {
        sysRole role = sysRoleService.findById(record.getId());
        if(role != null) {
            if(sysConstants.ADMIN.equalsIgnoreCase(role.getName())) {
                return HttpResult.error("超级管理员不允许修改!");
            }
        }
        // 新增角色
        if((record.getId() == null || record.getId() ==0) && !sysRoleService.findByName(record.getName()).isEmpty()) {
            return HttpResult.error("角色名已存在!");
        }
        return HttpResult.ok(sysRoleService.save(record));
    }

    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<sysRole> records) {
        return HttpResult.ok(sysRoleService.delete(records));
    }

    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysRoleService.findPage(pageRequest));
    }

    @GetMapping(value="/findAll")
    public HttpResult findAll() {
        return HttpResult.ok(sysRoleService.findAll());
    }

    @GetMapping(value="/findRoleMenus")
    public HttpResult findRoleMenus(@RequestParam Long roleId) {
        return HttpResult.ok(sysRoleService.findRoleMenus(roleId));
    }

    @PostMapping(value="/saveRoleMenus")
    public HttpResult saveRoleMenus(@RequestBody List<sysRoleMenu> records) {
        for(sysRoleMenu record:records) {
            sysRole sysRole = sysRoleMapper.selectByPrimaryKey(record.getRoleId());
            if(sysConstants.ADMIN.equalsIgnoreCase(sysRole.getName())) {
                // 如果是超级管理员，不允许修改
                return HttpResult.error("超级管理员拥有所有菜单权限，不允许修改！");
            }
        }
        return HttpResult.ok(sysRoleService.saveRoleMenus(records));
    }
}
