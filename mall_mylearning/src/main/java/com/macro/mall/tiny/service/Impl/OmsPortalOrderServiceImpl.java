package com.macro.mall.tiny.service.Impl;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.component.CancelOrderSender;
import com.macro.mall.tiny.dto.OrderParam;
import com.macro.mall.tiny.service.OmsPortalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 前台订单管理Service
 * Created by macro on 2018/8/30.
 */

@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {

    @Autowired
    private CancelOrderSender cancelOrderSender;

    @Override
    public CommonResult generateOrder(OrderParam orderParam) {
        //TODO 执行一系列下单操作，具体参考mall项目
        //下单完成后开启一个延迟消息，用于当用户没有付款时取消订单（orderId应该在下单后生成）
        sendDelayMessageCancelOrder(11L);
        return CommonResult.success(null,"下单成功");
    }

    @Override
    public void cancelOrder(Long orderId) {
        //TODO 执行一系列取消订单操作，具体参考mall项目
        //取消订单一定会通过id查询出订单在判断用户是否支付 如果没有支付就取消订单
        System.out.println(orderId);
    }
    private void sendDelayMessageCancelOrder(Long orderId) {
        //获取订单超时时间，假设为60分钟(测试用的30秒)
        long delayTimes = 30 * 1000;
        //发送延迟消息
        cancelOrderSender.sendMessage(orderId, delayTimes);
    }
}
