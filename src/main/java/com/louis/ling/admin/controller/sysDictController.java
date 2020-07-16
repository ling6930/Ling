package com.louis.ling.admin.controller;

import com.louis.ling.admin.http.HttpResult;
import com.louis.ling.admin.model.sysDict;
import com.louis.ling.admin.page.PageRequest;
import com.louis.ling.admin.service.sysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典控制器
 */
@RestController
@RequestMapping("dict")
public class sysDictController {

    @Autowired
    private sysDictService sysDictService;

    @PostMapping(value="/save")
    public HttpResult save(@RequestBody sysDict record) {
        return HttpResult.ok(sysDictService.save(record));
    }

    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<sysDict> records) {
        return HttpResult.ok(sysDictService.delete(records));
    }

    @PostMapping(value="/findPage")
    public HttpResult findPage(@RequestBody PageRequest pageRequest) {
        return HttpResult.ok(sysDictService.findPage(pageRequest));
    }


    @GetMapping(value="/findByLable")
    public HttpResult findByLable(@RequestParam String lable) {
        return HttpResult.ok(sysDictService.findByLable(lable));
    }
}
