package com.louis.ling.admin.service.impl;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.louis.ling.admin.dao.sysUserTokenMapper;
import com.louis.ling.admin.model.sysUserToken;
import com.louis.ling.admin.service.sysUserTokenService;
import com.louis.ling.admin.util.TokenGenerator;
import com.louis.ling.admin.page.MybatisPageHelper;
import com.louis.ling.admin.page.PageRequest;
import com.louis.ling.admin.page.PageResult;

import javax.annotation.Resource;

@Service
public class SysUserTokenServiceImpl implements sysUserTokenService {

    @Resource
    private sysUserTokenMapper sysUserTokenMapper;
    // 12小时后过期
    private final static int EXPIRE = 3600 * 12;

    @Override
    public sysUserToken findByUserId(Long userId) {
        return null;
    }

    @Override
    public sysUserToken findByToken(String token) {
        return null;
    }

    @Override
    public int save(sysUserToken record) {
        return sysUserTokenMapper.insertSelective(record);
    }

    @Override
    public int update(sysUserToken record) {
        return sysUserTokenMapper.updateByPrimaryKey(record);
    }

    @Override
    public int delete(sysUserToken record) {
        return sysUserTokenMapper.deleteByPrimaryKey(record.getId());
    }

    @Override
    public int delete(List<sysUserToken> records) {
        for(sysUserToken record:records) {
            delete(record);
        }
        return 1;
    }

    @Override
    public sysUserToken findById(Long id) {
        return sysUserTokenMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageResult findPage(PageRequest pageRequest) {
        return MybatisPageHelper.findPage(pageRequest, sysUserTokenMapper);
    }

    @Override
    public sysUserToken createToken(long userId) {
        // 生成一个token
        String token = TokenGenerator.generateToken();
        // 当前时间
        Date now = new Date();
        // 过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);
        // 判断是否生成过token
        sysUserToken sysUserToken = findByUserId(userId);
        if(sysUserToken == null){
            sysUserToken = new sysUserToken();
            sysUserToken.setUserId(userId);
            sysUserToken.setToken(token);
            sysUserToken.setLastUpdateTime(now);
            sysUserToken.setExpireTime(expireTime);
            // 保存token，这里选择保存到数据库，也可以放到Redis或Session之类可存储的地方
            save(sysUserToken);
        } else{
            sysUserToken.setToken(token);
            sysUserToken.setLastUpdateTime(now);
            sysUserToken.setExpireTime(expireTime);
            // 如果token已经生成，则更新token的过期时间
            update(sysUserToken);
        }
        return sysUserToken;
    }
}
