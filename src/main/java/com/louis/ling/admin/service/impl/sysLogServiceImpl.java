package com.louis.ling.admin.service.impl;

import com.louis.ling.admin.dao.sysLogMapper;
import com.louis.ling.admin.model.sysLog;
import com.louis.ling.admin.page.ColumnFilter;
import com.louis.ling.admin.page.MybatisPageHelper;
import com.louis.ling.admin.page.PageRequest;
import com.louis.ling.admin.page.PageResult;
import com.louis.ling.admin.service.sysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class sysLogServiceImpl implements sysLogService {

    @Autowired
    private sysLogMapper sysLogMapper;

    @Override
    public int save(sysLog record) {
        if(record.getId() == null || record.getId() == 0) {
            return sysLogMapper.insertSelective(record);
        }
        return sysLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int update(sysLog record) {
        return 0;
    }

    @Override
    public int delete(sysLog record) {
        return sysLogMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<sysLog> records) {
        for(sysLog record:records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public sysLog findById(Long id) {
        return sysLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        ColumnFilter columnFilter = pageRequest.getColumnFilter("userName");
        if(columnFilter != null) {
            return MybatisPageHelper.findPage(pageRequest, sysLogMapper, "findPageByUserName", columnFilter.getValue());
        }
        return MybatisPageHelper.findPage(pageRequest, sysLogMapper);
    }

}
