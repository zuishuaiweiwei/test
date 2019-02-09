package com.wei.dao;

import com.wei.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 为为
 * @create 11/23
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id} ")
    User getUserById(@Param("id") int id);
}
