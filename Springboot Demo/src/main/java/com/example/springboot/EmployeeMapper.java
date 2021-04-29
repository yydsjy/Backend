package com.example.springboot;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface EmployeeMapper {
    @Select("select * from employee where eno=#{eno}")
    public Employee findEmployeeById(Integer eno);
}
