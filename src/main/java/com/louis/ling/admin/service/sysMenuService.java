package com.louis.ling.admin.service;

import com.louis.ling.admin.model.sysMenu;
import com.louis.ling.admin.page.PageRequest;
import com.louis.ling.admin.page.PageResult;

import java.util.List;

public interface sysMenuService extends curdService<sysMenu> {
    /**
     * 查询菜单树,用户ID和用户名为空则查询全部
     * @param userId
     * @return
     */
    List<sysMenu> findTree(String userName, int menuType);

    /**
     * 根据用户名查找菜单列表
     * @param userName
     * @return
     */
    List<sysMenu> findByUser(String userName);
    /**
     * 分页查询接口
     * 这里统一封装了分页请求和结果，避免直接引入具体框架的分页对象, 如MyBatis或JPA的分页对象
     * 从而避免因为替换ORM框架而导致服务层、控制层的分页接口也需要变动的情况，替换ORM框架也不会
     * 影响服务层以上的分页接口，起到了解耦的作用
     * @param pageRequest 自定义，统一分页查询请求
     * @return PageResult 自定义，统一分页查询结果
     */
    PageResult findPage(PageRequest pageRequest);
}
