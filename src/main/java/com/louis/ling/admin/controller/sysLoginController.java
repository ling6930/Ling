package com.louis.ling.admin.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.louis.ling.admin.model.sysUser;
import com.louis.ling.admin.model.sysUserToken;
import com.louis.ling.admin.service.sysUserService;
import com.louis.ling.admin.service.sysUserTokenService;
import com.louis.ling.admin.util.PasswordUtils;
import com.louis.ling.admin.vo.LoginBean;
import com.louis.ling.admin.http.HttpResult;

@RestController
public class sysLoginController {

    @Autowired
    private sysUserService sysUserService;
    @Resource
    private sysUserTokenService sysUserTokenService;

    @PostMapping(value = "/sys/login")
    public HttpResult login(@RequestBody LoginBean loginBean){

        String username = loginBean.getUsername();
        String password = loginBean.getPassword();

        // 用户信息
        sysUser user = sysUserService.findByName(username);

        // 账号不存在、密码错误
        if (user == null) {
            return HttpResult.error("账号不存在");
        }

        if (!match(user, password)) {
            return HttpResult.error("密码不正确");
        }

        // 账号锁定
        if (user.getStatus() == 0) {
            return HttpResult.error("账号已被锁定,请联系管理员");
        }

        // 生成token，并保存到数据库
        sysUserToken data = sysUserTokenService.createToken(user.getId());
        return HttpResult.ok(data);
    }
        /**
         * 验证用户密码
         * @param user
         * @param password
         * @return
         */
        public boolean match(sysUser user, String password) {
            return user.getPassword().equals(PasswordUtils.encrypte(password, user.getSalt()));
    }
}
