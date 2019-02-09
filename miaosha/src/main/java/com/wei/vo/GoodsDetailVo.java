package com.wei.vo;
import com.wei.entity.MiaoShaUser;



        import com.wei.entity.MiaoShaUser;

/**
 * @author 为为
 * @create 2/5
 */
public class GoodsDetailVo {
    private long remainSeconds;
    private long  miaoshaStatus;
    private MiaoShaUser user;
    private MiaoShaGoogsVo goods;

    public long getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(long remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public long getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(long miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public MiaoShaUser getUser() {
        return user;
    }

    public void setUser(MiaoShaUser user) {
        this.user = user;
    }

    public MiaoShaGoogsVo getGoods() {
        return goods;
    }

    public void setGoods(MiaoShaGoogsVo goods) {
        this.goods = goods;
    }
}
