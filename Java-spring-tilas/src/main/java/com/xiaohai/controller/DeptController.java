package com.xiaohai.controller;


import com.xiaohai.pojo.Dept;
import com.xiaohai.pojo.Result;
import com.xiaohai.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//部门管理
@Slf4j
@RestController
@RequestMapping("/depts")
public class DeptController {

    @Autowired
    private DeptService deptService;

    //private static Logger log = LoggerFactory.getLogger(DeptController.class);

    @GetMapping
    public Result list() {
        log.info("查询全部部门数据");
        List<Dept> deptList = deptService.list();
        return Result.success(deptList);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("根据id删除部门:{}", id);
        deptService.delect(id);
        return Result.success();
    }

    @PostMapping
    public Result add(@RequestBody Dept dept) {
        log.info("新增部门:{}", dept);
        //新增部门
        deptService.add(dept);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody Dept dept) {
        log.info("修改部门:{}", dept);
        deptService.update(dept);
        return Result.success();
    }
}
