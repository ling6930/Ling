package com.louis.ling.admin.service.impl;

import com.louis.ling.admin.dao.sysRoleMapper;
import com.louis.ling.admin.dao.sysUserMapper;
import com.louis.ling.admin.dao.sysUserRoleMapper;
import com.louis.ling.admin.model.sysMenu;
import com.louis.ling.admin.model.sysRole;
import com.louis.ling.admin.model.sysUser;
import com.louis.ling.admin.model.sysUserRole;
import com.louis.ling.admin.page.ColumnFilter;
import com.louis.ling.admin.page.MybatisPageHelper;
import com.louis.ling.admin.page.PageRequest;
import com.louis.ling.admin.page.PageResult;
import com.louis.ling.admin.service.sysMenuService;
import com.louis.ling.admin.service.sysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class sysUserServiceImpl implements sysUserService {

    @Autowired
    private sysUserMapper sysUserMapper;
    @Autowired
    private sysMenuService sysMenuService;
    @Autowired
    private sysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private sysRoleMapper sysRoleMapper;

    @Override
    public sysUser findByUserId(Long userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public List<sysUser> findAll(){
        return sysUserMapper.selectAll();
    }

    @Override
    public sysUser findByName(String name) {
        return sysUserMapper.findByName(name);
    }

    @Override
    public Set<String> findPermissions(String userName) {

        Set<String> perms = new HashSet<>();
        List<sysMenu> sysMenus = sysMenuService.findByUser(userName);
        for(sysMenu sysMenu:sysMenus) {
            perms.add(sysMenu.getPerms());
        }
        return perms;
    }

    @Override
    public int save(sysUser record) {
        Long id = null;
        if(record.getId() == null || record.getId() == 0) {
            // 新增用户
            sysUserMapper.insertSelective(record);
            id = record.getId();
        } else {
            // 更新用户信息
            sysUserMapper.updateByPrimaryKeySelective(record);
        }
        // 更新用户角色
        if(id != null && id == 0) {
            return 1;
        }
        if(id != null) {
            for(sysUserRole sysUserRole:record.getUserRoles()) {
                sysUserRole.setUserId(id);
            }
        } else {
            sysUserRoleMapper.deleteByUserId(record.getId());
        }
        for(sysUserRole sysUserRole:record.getUserRoles()) {
            sysUserRoleMapper.insertSelective(sysUserRole);
        }
        return 1;
    }

    @Override
    public int update(sysUser record) {
        return 0;
    }

    @Override
    public int delete(sysUser record) {

        return sysUserMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<sysUser> records) {

        for(sysUser record:records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public sysUser findById(Long id) {
        return null;
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {

        PageResult pageResult = null;
        String name = getColumnFilterValue(pageRequest, "name");
        String email = getColumnFilterValue(pageRequest, "email");
        if(name != null) {
            if(email != null) {
                pageResult = MybatisPageHelper.findPage(pageRequest, sysUserMapper, "findPageByNameAndEmail", name, email);
            } else {
                pageResult = MybatisPageHelper.findPage(pageRequest, sysUserMapper, "findPageByName", name);
            }
        } else {
            pageResult = MybatisPageHelper.findPage(pageRequest, sysUserMapper);
        }
        // 加载用户角色信息
        findUserRoles(pageResult);
        return pageResult;
    }

    /**
     * 加载用户角色
     * @param pageResult
     */
    private void findUserRoles(PageResult pageResult) {
        List<?> content = pageResult.getContent();
        for(Object object:content) {
            sysUser sysUser = (sysUser) object;
            List<sysUserRole> userRoles = findUserRoles(sysUser.getId());
            sysUser.setUserRoles(userRoles);
            sysUser.setRoleNames(getRoleNames(userRoles));
        }
    }

    private String getRoleNames(List<sysUserRole> userRoles) {
        StringBuilder sb = new StringBuilder();
        for(Iterator<sysUserRole> iter = userRoles.iterator(); iter.hasNext();) {
            sysUserRole userRole = iter.next();
            sysRole sysRole = sysRoleMapper.selectByPrimaryKey(userRole.getRoleId());
            if(sysRole == null) {
                continue ;
            }
            sb.append(sysRole.getRemark());
            if(iter.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    private String getColumnFilterValue(PageRequest pageRequest, String filterName) {
        String value = null;
        ColumnFilter columnFilter = pageRequest.getColumnFilter(filterName);
        if(columnFilter != null) {
            value = columnFilter.getValue();
        }
        return value;
    }

    @Override
    public List<sysUserRole> findUserRoles(Long userId) {
        return sysUserRoleMapper.findUserRoles(userId);
    }
}
