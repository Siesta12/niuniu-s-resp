package com.nlt.myapplication.mapper;

import com.nlt.myapplication.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username} and password = #{password}")
    User login(User user);

    @Insert("insert into user(username,password) values(#{username},#{password})")
    void register(User user);

}
