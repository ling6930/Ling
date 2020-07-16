package com.louis.ling.admin.service;

import com.louis.ling.admin.model.sysDict;
import com.louis.ling.admin.service.curdService;

import java.util.List;

/**
 * 字典管理
 * @author Louis
 * @date Oct 29, 2018
 */
public interface sysDictService extends curdService<sysDict> {

    /**
     * 根据名称查询
     * @param lable
     * @return
     */
    List<sysDict> findByLable(String lable);
}
