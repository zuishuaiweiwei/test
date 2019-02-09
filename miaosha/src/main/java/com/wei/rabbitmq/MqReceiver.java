package com.wei.rabbitmq;
import com.wei.entity.MiaoShaUser;
import com.wei.entity.MiaoshaOrder;
import com.wei.rabbitmq.MiaoshaMessage;
import com.wei.rabbitmq.MqConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



        import com.wei.entity.MiaoShaUser;
        import com.wei.entity.MiaoshaOrder;
        import com.wei.redis.RedisService;
        import com.wei.result.CodeMsg;
        import com.wei.result.Result;
        import com.wei.service.GoodsService;
        import com.wei.service.MiaoShaService;
        import com.wei.service.OrderService;
        import com.wei.vo.MiaoShaGoogsVo;
        import org.springframework.amqp.rabbit.annotation.RabbitListener;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

/**
 * @author 为为
 * @create 2/7
 */
@Service
public class MqReceiver {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    RedisService redisService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MiaoShaService miaoShaService;

    @RabbitListener(queues = MqConfig.MIAOSHA_QUEUE)
    public void reciver(String msg) {
        MiaoshaMessage mm = redisService.stringToBean(msg, MiaoshaMessage.class);
        MiaoShaUser user = mm.getUser();
        long goodsId = mm.getGoodsId();
        MiaoShaGoogsVo goods = goodsService.getMiaoShaVoById(goodsId+"");
        if (goods.getStockCount() <= 0) {
            return;
        }
        MiaoshaOrder order = orderService.getOrderByUserIdGoodsId(user.getId(), ""+goodsId);
        if(order != null){
            return;
        }
        miaoShaService.miaosha(user,goods);

    }
//    @RabbitListener(queues = MqConfig.QUEUE)
//    public void reciver(String msg){
//        System.out.println(msg);
//    }
//
//    /**
//     * fanout
//     * @param msg
//     */
//    @RabbitListener(queues = MqConfig.FANOUT_QUEUE1)
//    public void fontReciver1(String msg){
//        System.out.println("receiver fanout1 :"+msg);
//    }
//    @RabbitListener(queues = MqConfig.FANOUT_QUEUE2)
//    public void fontReciver2(String msg){
//        System.out.println("receiver fanout2 :"+msg);
//    }
//
//    /**
//     * topic
//     * @param msg
//     */
//    @RabbitListener(queues = MqConfig.TOPIC_QUEUE1)
//    public void topicReciver1(String msg){
//        System.out.println("receiver topic1 :"+msg);
//    }
//    @RabbitListener(queues = MqConfig.TOPIC_QUEUE2)
//    public void topicReciver2(String msg){
//        System.out.println("receiver topic2 :"+msg);
//    }
//
//    @RabbitListener(queues = MqConfig.HEADERS_QUEUE1)
//    public void headersReciver1(byte[] msg){
//        System.out.println("receiver headers 1 :"+new String(msg));
//    }
}
