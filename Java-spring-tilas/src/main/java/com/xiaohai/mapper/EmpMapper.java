package com.xiaohai.mapper;

import com.xiaohai.pojo.Emp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

//员工管理
@Mapper
public interface EmpMapper {

    //查询总记录数
    /*@Select("select count(*) from emp")
    public Integer count();

    //分页查询,获取列表数据
    @Select("select * from emp limit #{start},#{pageSize}")
    public List<Emp> page(Integer start, Integer pageSize);*/

    //员工信息查询
    //@Select("select * from emp ")
    public List<Emp> list(String name, Short gender, LocalDate begin, LocalDate end);


    void delete(List<Integer> ids);

    void insert(Emp emp);

    @Select("select * from emp where id= #{id}")
    Emp getById(Integer id);

    void update(Emp emp);
}
