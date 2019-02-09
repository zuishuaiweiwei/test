package com.wei.service;
import com.wei.dao.GoodsDao;
import com.wei.entity.MiaoshaGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



        import com.wei.dao.GoodsDao;
        import com.wei.dao.UserDao;
        import com.wei.entity.MiaoshaGoods;
        import com.wei.entity.User;
        import com.wei.vo.MiaoShaGoogsVo;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;

/**
 * @author 为为
 * @create 11/23
 */
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;


    public List<MiaoShaGoogsVo> getList() {
        return goodsDao.getList();
    }

    public MiaoShaGoogsVo getMiaoShaVoById(String goodsId) {
        return goodsDao.getMiaoShaVoById(goodsId);
    }

    public boolean reduceStock(MiaoShaGoogsVo goods) {
        MiaoshaGoods miaoshaGoods = new MiaoshaGoods();
        miaoshaGoods.setGoodsId(goods.getId());
        int ret = goodsDao.reduceStock(miaoshaGoods);
        return ret > 0;
    }
}
