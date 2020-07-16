package com.louis.ling.admin.service;

import com.louis.ling.admin.model.sysDept;
import com.louis.ling.admin.service.curdService;

import java.util.List;

/**
 * 机构管理
 * @author Louis
 * @date Oct 29, 2018
 */
public interface sysDeptService extends curdService<sysDept> {

        /**
         * 查询机构树
         * @return
         */
        List<sysDept> findTree();
}
