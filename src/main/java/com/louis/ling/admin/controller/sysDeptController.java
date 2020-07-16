package com.louis.ling.admin.controller;

import com.louis.ling.admin.http.HttpResult;
import com.louis.ling.admin.model.sysDept;
import com.louis.ling.admin.service.sysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 机构控制器
 */
@RestController
@RequestMapping("dept")
public class sysDeptController {

    @Autowired
    private sysDeptService sysDeptService;

    @PostMapping(value="/save")
    public HttpResult save(@RequestBody sysDept record) {
        return HttpResult.ok(sysDeptService.save(record));
    }

    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<sysDept> records) {
        return HttpResult.ok(sysDeptService.delete(records));
    }

    @GetMapping(value="/findTree")
    public HttpResult findTree() {
        return HttpResult.ok(sysDeptService.findTree());
    }
}
