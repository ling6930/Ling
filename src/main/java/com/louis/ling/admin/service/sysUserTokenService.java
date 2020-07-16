package com.louis.ling.admin.service;

import com.louis.ling.admin.model.sysUserToken;

/**
 * 用户Token
 * @author Louis
 * @date Aug 21, 2018
 */
public interface sysUserTokenService extends curdService<sysUserToken>{

    /**
     * 根据用户id查找
     * @param userId
     * @return
     */
    sysUserToken findByUserId(Long userId);

    /**
     * 根据token查找
     * @param token
     * @return
     */
    sysUserToken findByToken(String token);

    /**
     * 生成token
     * @param userId
     * @return
     */
    sysUserToken createToken(long userId);
}
