package com.wei.rabbitmq;
import com.wei.entity.MiaoShaUser;


        import com.wei.entity.MiaoShaUser;

/**
 * @author 为为
 * @create 2/8
 */
public class MiaoshaMessage {

    private MiaoShaUser user;
    private long goodsId;

    public MiaoshaMessage() {
    }

    public MiaoShaUser getUser() {
        return user;
    }

    public void setUser(MiaoShaUser user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public MiaoshaMessage(MiaoShaUser user, long goodsId) {
        this.user = user;
        this.goodsId = goodsId;
    }
}
