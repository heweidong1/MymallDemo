package com.macro.mall.tiny.component;

import com.macro.mall.tiny.dto.QueueEnum;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 取消订单消息的发出者
 * Created by macro on 2018/9/14.
 * 交换机及队列说明
 *
 * mall.order.direct（取消订单消息队列所绑定的交换机）:
 * 绑定的队列为mall.order.cancel，一旦有消息以mall.order.cancel为路由键发过来，会发送到此队列。
 * mall.order.direct.ttl（订单延迟消息队列所绑定的交换机）:
 * 绑定的队列为mall.order.cancel.ttl，一旦有消息以mall.order.cancel.ttl为路由键发送过来，会转发到此队列，并在此队列保存一定时间，等到超时后会自动将消息发送到mall.order.cancel（取消订单消息消费队列）
 *
 */
@Component
public class CancelOrderSender
{

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(Long orderId,final long delayTimes)
    {
        amqpTemplate.convertAndSend(QueueEnum.QUEUE_TTL_ORDER_CANCEL.getExchange(),QueueEnum.QUEUE_TTL_ORDER_CANCEL.getRouteKey(), orderId, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒数
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            }
        });
    }





















}
