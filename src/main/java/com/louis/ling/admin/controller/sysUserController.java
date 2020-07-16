package com.louis.ling.admin.controller;

import com.louis.ling.admin.constants.sysConstants;
import com.louis.ling.admin.http.HttpResult;
import com.louis.ling.admin.model.sysUser;
import com.louis.ling.admin.page.PageRequest;
import com.louis.ling.admin.service.sysUserService;
import com.louis.ling.admin.util.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class sysUserController {

    @Autowired
    private sysUserService sysUserService;

    @GetMapping(value="/findByUserId")
    public Object findByUserId(@RequestParam Long userId) {

        return sysUserService.findByUserId(userId);
    }

    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<sysUser> records) {
        for(sysUser record:records) {
            sysUser sysUser = sysUserService.findById(record.getId());
            if(sysUser != null && sysConstants.ADMIN.equalsIgnoreCase(sysUser.getName())) {
                return HttpResult.error("超级管理员不允许删除!");
            }
        }
        return HttpResult.ok(sysUserService.delete(records));
    }

    @GetMapping(value="/findAll")
    public Object findAll() {

        return sysUserService.findAll();
    }

    /**
     * 获取权限
     * @param name
     * @return
     */
    @GetMapping(value="/findPermissions")
    public HttpResult findPermissions(@RequestParam String name) {
        return HttpResult.ok(sysUserService.findPermissions(name));
    }

    /**
     * 获取分页数据
     * @param pageRequest
     * @return
     */
    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysUserService.findPage(pageRequest));
    }

    /**
     * 保存用户数据
     * @param record
     * @return
     */
    @PostMapping(value="/save")
    public HttpResult save(@RequestBody sysUser record) {
        sysUser user = sysUserService.findById(record.getId());
        if(user != null) {
            if(sysConstants.ADMIN.equalsIgnoreCase(user.getName())) {
                return HttpResult.error("超级管理员不允许修改!");
            }
        }
        if(record.getPassword() != null) {
            String salt = PasswordUtils.getSalt();
            if(user == null) {
                // 新增用户
                if(sysUserService.findByName(record.getName()) != null) {
                    return HttpResult.error("用户名已存在!");
                }
                String password = PasswordUtils.encrypte(record.getPassword(), salt);
                record.setSalt(salt);
                record.setPassword(password);
            } else {
                // 修改用户, 且修改了密码
                if(!record.getPassword().equals(user.getPassword())) {
                    String password = PasswordUtils.encrypte(record.getPassword(), salt);
                    record.setSalt(salt);
                    record.setPassword(password);
                }
            }
        }
        return HttpResult.ok(sysUserService.save(record));
    }
}
