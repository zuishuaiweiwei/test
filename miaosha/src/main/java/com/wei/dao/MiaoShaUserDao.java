package com.wei.dao;

import com.wei.entity.MiaoShaUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author 为为
 * @create 1/25
 */
@Mapper
public interface MiaoShaUserDao {
    /**
     * 登陆
     * @param id
     * @return
     */
    @Select("select * from miaosha where id = #{id} ")
    MiaoShaUser getById(@Param("id")String id);
    @Update("update miaosha set password = #{password} where id = #{id}")
    void update(MiaoShaUser userNew);
}
