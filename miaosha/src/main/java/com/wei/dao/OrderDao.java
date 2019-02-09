package com.wei.dao;


import com.wei.entity.MiaoshaOrder;
import com.wei.entity.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author 为为
 * @create 1/31
 */
@Mapper
public interface OrderDao {

    /**
     * 判断有没有重复秒杀
     *
     * @param userId
     * @param goodsId
     * @return
     */
    @Select("select * from miaosha_order where user_id=#{userId} and goods_Id = #{goodsId}")
    MiaoshaOrder getOrderByUserIdGoodsId(@Param("userId") String userId, @Param("goodsId") String goodsId);

    @Insert("insert into order_info(user_id,goods_id,goods_name,goods_count,goods_price,order_channel,status,create_date) values (" +
            "#{userId},#{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = Long.class, before = false, statement = "select last_insert_id()")
    long createOrder(OrderInfo orderInfo);

    @Insert("insert into miaosha_order (user_id,goods_id,order_id) values (#{userId},#{goodsId},#{orderId})")
    void createMiaoShaOrder(MiaoshaOrder miaoshaOrder);

    @Select("select * from order_info where id = #{infoId}")
    OrderInfo getOrderById(String infoId);
}
