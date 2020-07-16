package com.louis.ling.admin.service.impl;

import com.louis.ling.admin.dao.sysDeptMapper;
import com.louis.ling.admin.model.sysDept;
import com.louis.ling.admin.page.MybatisPageHelper;
import com.louis.ling.admin.page.PageRequest;
import com.louis.ling.admin.page.PageResult;
import com.louis.ling.admin.service.sysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class sysDeptServiceImpl implements sysDeptService {

    @Autowired
    private sysDeptMapper sysDeptMapper;

    @Override
    public List<sysDept> findTree() {
        List<sysDept> sysDepts = new ArrayList<>();
        List<sysDept> depts = sysDeptMapper.findAll();
        for (sysDept dept : depts) {
            if (dept.getParentId() == null || dept.getParentId() == 0) {
                dept.setLevel(0);
                sysDepts.add(dept);
            }
        }
        findChildren(sysDepts, depts);
        return sysDepts;
    }

    @Override
    public int save(sysDept record) {
        if(record.getId() == null || record.getId() == 0) {
            return sysDeptMapper.insertSelective(record);
        }
        return 1;
    }

    @Override
    public int update(sysDept record) {
        return 0;
    }

    @Override
    public int delete(sysDept record) {

        return sysDeptMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<sysDept> records) {
        for(sysDept record:records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public sysDept findById(Long id) {

        return sysDeptMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MybatisPageHelper.findPage(pageRequest, sysDeptMapper);
    }

    private void findChildren(List<sysDept> sysDepts, List<sysDept> depts) {
        for (sysDept sysDept : sysDepts) {
            List<sysDept> children = new ArrayList<>();
            for (sysDept dept : depts) {
                if (sysDept.getId() != null && sysDept.getId().equals(dept.getParentId())) {
                    dept.setParentName(dept.getName());
                    dept.setLevel(sysDept.getLevel() + 1);
                    children.add(dept);
                }
            }
            sysDept.setChildren(children);
            findChildren(children, depts);
        }
    }

}
