package com.louis.ling.admin.controller;

import com.louis.ling.admin.http.HttpResult;
import com.louis.ling.admin.model.sysMenu;
import com.louis.ling.admin.page.PageRequest;
import com.louis.ling.admin.service.sysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("menu")
public class sysMenuController {

    @Autowired
    private sysMenuService sysMenuService;

    @PostMapping(value="/save")
    public HttpResult save(@RequestBody sysMenu record) {
        return HttpResult.ok(sysMenuService.save(record));
    }

    @PostMapping(value="/delete")
    public HttpResult delete(@RequestBody List<sysMenu> records) {
        return HttpResult.ok(sysMenuService.delete(records));
    }

    @PostMapping(value="/findPage")
    public Object findPage(@RequestBody PageRequest pageQuery) {

        return sysMenuService.findPage(pageQuery);
    }

    @GetMapping(value="/findNavTree")
    public HttpResult findNavTree(@RequestParam String userName) {
        return HttpResult.ok(sysMenuService.findTree(userName, 1));
    }

    @GetMapping(value="/findMenuTree")
    public HttpResult findMenuTree() {
        return HttpResult.ok(sysMenuService.findTree(null, 0));
    }
}
