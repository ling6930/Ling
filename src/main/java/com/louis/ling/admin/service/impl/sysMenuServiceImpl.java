package com.louis.ling.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.louis.ling.admin.constants.sysConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.louis.ling.admin.dao.sysMenuMapper;
import com.louis.ling.admin.model.sysMenu;
import com.louis.ling.admin.page.PageRequest;
import com.louis.ling.admin.page.PageResult;
import com.louis.ling.admin.page.PageUtils;
import com.louis.ling.admin.service.sysMenuService;

@Service
public class sysMenuServiceImpl implements sysMenuService {

    @Autowired
    private sysMenuMapper sysMenuMapper;

    @Override
    public int save(sysMenu record) {

        if(record.getId() == null || record.getId() == 0) {
            return sysMenuMapper.insertSelective(record);
        }
        if(record.getParentId() == null) {
            record.setParentId(0L);
        }
        return sysMenuMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int update(sysMenu record) {
        return 0;
    }

    @Override
    public int delete(sysMenu record) {

        return sysMenuMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<sysMenu> records) {

        for(sysMenu record:records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public sysMenu findById(Long id) {

        return sysMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return PageUtils.getPageResult(pageRequest, getPageInfo(pageRequest));
    }

    /**
     * 调用分页插件完成分页
     * @param pageQuery
     * @return
     */
    private PageInfo<sysMenu> getPageInfo(PageRequest pageRequest) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<sysMenu> sysMenus = sysMenuMapper.findAll();
        return new PageInfo<sysMenu>(sysMenus);
    }

    @Override
    public List<sysMenu> findTree(String userName, int menuType) {
        List<sysMenu> sysMenus = new ArrayList<>();
        List<sysMenu> menus = findByUser(userName);
        for (sysMenu menu : menus) {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                menu.setLevel(0);
                sysMenus.add(menu);
            }
        }
        sysMenus.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
        findChildren(sysMenus, menus, menuType);
        return sysMenus;
    }

    @Override
    public List<sysMenu> findByUser(String userName) {

        if(userName == null || "".equals(userName) || sysConstants.ADMIN.equalsIgnoreCase(userName)) {
            return sysMenuMapper.findAll();
        }
        return sysMenuMapper.findByUserName(userName);
    }

    private void findChildren(List<sysMenu> sysMenus, List<sysMenu> menus, int menuType) {
        for (sysMenu sysMenu : sysMenus) {
            List<sysMenu> children = new ArrayList<>();
            for (sysMenu menu : menus) {
                if(menuType == 1 && menu.getType() == 2) {
                    // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                    continue ;
                }
                if (sysMenu.getId() != null && sysMenu.getId().equals(menu.getParentId())) {
                    menu.setParentName(sysMenu.getName());
                    menu.setLevel(sysMenu.getLevel() + 1);
                    children.add(menu);
                }
            }
            sysMenu.setChildren(children);
            children.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
            findChildren(children, menus, menuType);
        }
    }

}
