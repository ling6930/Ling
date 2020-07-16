package com.louis.ling.admin.service.impl;

import com.louis.ling.admin.constants.sysConstants;
import com.louis.ling.admin.dao.sysMenuMapper;
import com.louis.ling.admin.dao.sysRoleMapper;
import com.louis.ling.admin.dao.sysRoleMenuMapper;
import com.louis.ling.admin.model.sysMenu;
import com.louis.ling.admin.model.sysRole;
import com.louis.ling.admin.model.sysRoleMenu;
import com.louis.ling.admin.page.ColumnFilter;
import com.louis.ling.admin.page.MybatisPageHelper;
import com.louis.ling.admin.page.PageRequest;
import com.louis.ling.admin.page.PageResult;
import com.louis.ling.admin.service.sysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class sysRoleServiceImpl implements sysRoleService {

    @Autowired
    private sysRoleMapper sysRoleMapper;
    @Autowired
    private sysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private sysMenuMapper sysMenuMapper;

    @Override
    public List<sysRole> findAll() {

        return sysRoleMapper.findAll();
    }

    @Override
    public List<sysMenu> findRoleMenus(Long roleId) {
        sysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        if(sysConstants.ADMIN.equalsIgnoreCase(sysRole.getName())) {
            // 如果是超级管理员，返回全部
            return sysMenuMapper.findAll();
        }
        return sysMenuMapper.findRoleMenus(roleId);
    }

    @Transactional
    @Override
    public int saveRoleMenus(List<sysRoleMenu> records) {

        if(records == null || records.isEmpty()) {
            return 1;
        }
        Long roleId = records.get(0).getRoleId();
        sysRoleMenuMapper.deleteByRoleId(roleId);
        for(sysRoleMenu record:records) {
            sysRoleMenuMapper.insertSelective(record);
        }
        return 1;
    }

    @Override
    public List<sysRole> findByName(String name) {

        return sysRoleMapper.findByName(name);
    }

    @Override
    public int save(sysRole record) {

        if(record.getId() == null || record.getId() == 0) {
            return sysRoleMapper.insertSelective(record);
        }
        return sysRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int update(sysRole record) {
        return 0;
    }

    @Override
    public int delete(sysRole record) {

        return sysRoleMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<sysRole> records) {

        for(sysRole record:records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public sysRole findById(Long id) {

        return sysRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {

        ColumnFilter columnFilter = pageRequest.getColumnFilter("name");
        if(columnFilter != null && columnFilter.getValue() != null) {
            return MybatisPageHelper.findPage(pageRequest, sysRoleMapper, "findPageByName", columnFilter.getValue());
        }
        return MybatisPageHelper.findPage(pageRequest, sysRoleMapper);
    }
}
