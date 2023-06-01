package com.xiaohai.mapper;

import com.xiaohai.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;

//部门管理
@Mapper
public interface DeptMapper {
    //查询全部部门数据
    @Select("select * from dept")
    List<Dept> list();

    //根据ID删除部门
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into dept(name, create_time, update_time) value (#{name},#{createTime},#{updateTime})")
    void insert(Dept dept);

    @Update("update dept set create_time = #{createTime} , " +
            "update_time = #{updateTime} where id = #{id} and name = #{name}")
    void update(Dept dept);
}
