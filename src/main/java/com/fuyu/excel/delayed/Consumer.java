package com.fuyu.excel.delayed;

import java.util.concurrent.DelayQueue;

/**
 * @desc:
 * 延时队列消费者
 * @author: Richy
 * @date: 2020/12/20/0020
 */
public class Consumer implements Runnable{

    //延时队列 ,消费者从其中获取消息进行消费
    private DelayQueue<Message> queue;

    public Consumer(DelayQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message take = queue.take();
                System.out.println("消费消息id：" + take.getId() + " 消息体：" + take.getBody());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
