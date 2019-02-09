package com.wei.controller;

import com.wei.entity.MiaoShaUser;
import com.wei.redis.GoodsKey;
import com.wei.redis.RedisService;
import com.wei.result.Result;
import com.wei.service.GoodsService;
import com.wei.service.MiaoShaUserService;
import com.wei.vo.GoodsDetailVo;
import com.wei.vo.MiaoShaGoogsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品页面控制
 *
 * @author 为为
 * @create 1/25
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private MiaoShaUserService miaoShaUserService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisService redisService;
    /**
     * 渲染页面解析器
     */
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 去商品列表页
     *
     * @param user
     * @param model
     * @return html
     * <p>
     * 没用页面缓存之前
     * QPS: 420
     * 3000 * 5
     * 使用页面缓存
     * QPS：1170
     * 3000 * 5
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model, MiaoShaUser user) {
        model.addAttribute("user", user);
        //取缓存
        String html = redisService.get(GoodsKey.GoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        List<MiaoShaGoogsVo> goodsList = goodsService.getList();
        model.addAttribute("goodsList", goodsList);
        //手动渲染
        IWebContext ctx = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if (!StringUtils.isEmpty(html)) {
            //设置缓存
            redisService.set(GoodsKey.GoodsList, "", html);
        }
        return html;
    }

    /**
     * 去商品详情页（使用页面缓存）
     *
     * @param goodsId
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/to_detail2/{goodsId}", produces = "text/html")
    @ResponseBody
    public String toDetail2(HttpServletRequest request, HttpServletResponse response, @PathVariable("goodsId") String goodsId, MiaoShaUser user, Model model) {
        model.addAttribute("user", user);
        //取缓存
        String html = redisService.get(GoodsKey.GoodsDetail, goodsId, String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        //查找商品
        MiaoShaGoogsVo goods = goodsService.getMiaoShaVoById(goodsId);
        model.addAttribute("goods", goods);
        //秒杀开始时间
        long startTime = goods.getStartDate().getTime();
        //秒杀结束时间
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int miaoshaStatus;
        long remainSeconds = (startTime - now) / 1000;
        //秒杀未开始
        if (remainSeconds < 0) {
            miaoshaStatus = 0;

        }
        //秒杀结束
        else if (endTime < now) {
            miaoshaStatus = 2;
        } else {
            miaoshaStatus = 1;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        //手动渲染页面
        IWebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if (!StringUtils.isEmpty(html)) {
            //设置缓存
            redisService.set(GoodsKey.GoodsDetail, goodsId, html);
        }
        return html;
    }

    /**
     * 去商品详情页（使用页面静态化）
     *
     * @param goodsId
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/to_detail/{goodsId}")
    @ResponseBody
    public Result<GoodsDetailVo> toDetail(HttpServletRequest request, HttpServletResponse response, @PathVariable("goodsId") String goodsId, MiaoShaUser user, Model model) {

        //查找商品
        MiaoShaGoogsVo goods = goodsService.getMiaoShaVoById(goodsId);
        //秒杀开始时间
        long startTime = goods.getStartDate().getTime();

        //秒杀结束时间
        long endTime = goods.getEndDate().getTime();

        long now = System.currentTimeMillis();

        long remainSeconds = 0;
        //秒杀还没开始，倒计时
        if (now < startTime) {
            remainSeconds = (int) ((startTime - now) / 1000);

        } //秒杀已经结束
        else if (now > endTime) {
            remainSeconds = -1;
        }//秒杀进行中
        else {
            remainSeconds = 0;
        }

        GoodsDetailVo vo = new GoodsDetailVo();
        vo.setGoods(goods);
        vo.setRemainSeconds(remainSeconds);
        vo.setUser(user);
        return Result.success(vo);
    }

}
