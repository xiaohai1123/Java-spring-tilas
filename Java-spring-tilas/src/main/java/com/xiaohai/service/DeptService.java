package com.xiaohai.service;

import com.xiaohai.pojo.Dept;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DeptService {

    //查询全部部门数据
    @Select("select * from dept")
    List<Dept> list();


    //删除部门
    void delect(Integer id);

    //新增部门
    void add(Dept dept);

    //修改部门
    void update(Dept dept);
}
