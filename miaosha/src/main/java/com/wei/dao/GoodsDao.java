package com.wei.dao;

import com.wei.entity.MiaoshaGoods;
import com.wei.vo.MiaoShaGoogsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 为为
 * @create 1/27
 */
@Mapper
public interface GoodsDao {
    /**
     * 秒杀列表
     * @return
     */
    @Select("SELECT g.*,m.miaosha_price,m.stock_count,m.start_date,m.end_date FROM goods g LEFT JOIN miaosha_goods m ON g.id = m.goods_id")
    List<MiaoShaGoogsVo> getList();

    /**
     * 根据Id获取秒杀商品
     * @param id
     * @return
     */
    @Select("SELECT g.id,g.goods_name,g.goods_title,g.goods_img,g.goods_detail,g.goods_price,m.start_date,m.end_date,m.stock_count,m.miaosha_price " +
            "FROM miaosha_goods m,goods g WHERE g.id = #{id} AND m.goods_id = g.id")
    MiaoShaGoogsVo getMiaoShaVoById(@Param("id") String id);

    /**
     * 减少库存
     * @param goods
     */
    @Update("update miaosha_goods set stock_count = stock_count -1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(MiaoshaGoods goods);
}
