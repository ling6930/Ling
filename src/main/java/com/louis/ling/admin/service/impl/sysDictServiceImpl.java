package com.louis.ling.admin.service.impl;

import com.louis.ling.admin.dao.sysDictMapper;
import com.louis.ling.admin.model.sysDict;
import com.louis.ling.admin.page.ColumnFilter;
import com.louis.ling.admin.page.MybatisPageHelper;
import com.louis.ling.admin.page.PageRequest;
import com.louis.ling.admin.page.PageResult;
import com.louis.ling.admin.service.sysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class sysDictServiceImpl implements sysDictService {

    @Autowired
    private sysDictMapper sysDictMapper;

    @Override
    public List<sysDict> findByLable(String lable) {
        return sysDictMapper.findByLable(lable);
    }

    @Override
    public int save(sysDict record) {
        if(record.getId() == null || record.getId() == 0) {
            return sysDictMapper.insertSelective(record);
        }
        return sysDictMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int update(sysDict record) {
        return 0;
    }

    @Override
    public int delete(sysDict record) {
        return sysDictMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<sysDict> records) {
        for(sysDict record:records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public sysDict findById(Long id) {
        return sysDictMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        ColumnFilter columnFilter = pageRequest.getColumnFilter("label");
        if(columnFilter != null) {
            return MybatisPageHelper.findPage(pageRequest, sysDictMapper, "findPageByLabel", columnFilter.getValue());
        }
        return MybatisPageHelper.findPage(pageRequest, sysDictMapper);
    }
}
