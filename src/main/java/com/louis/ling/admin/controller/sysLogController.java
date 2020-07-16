package com.louis.ling.admin.controller;

import com.louis.ling.admin.http.HttpResult;
import com.louis.ling.admin.page.PageRequest;
import com.louis.ling.admin.service.sysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 日志控制器
 */
@RestController
@RequestMapping("log")
public class sysLogController {

    @Autowired
    private sysLogService sysLogService;

    @PostMapping(value = "/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysLogService.findPage(pageRequest));

    }
}
