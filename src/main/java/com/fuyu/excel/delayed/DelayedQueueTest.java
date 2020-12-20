package com.fuyu.excel.delayed;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @desc:
 * 测试延时队列
 * @author: Richy
 * @date: 2020/12/20/0020
 */
public class DelayedQueueTest {

    public static void main(String[] args) {
        //创建延时队列
        DelayQueue<Message> queue = new DelayQueue<>();
        //添加延时消息,延时3秒
        Message msg1 = new Message(1,"world",3000);
        //添加延时消息,延时10秒
        Message msg2 = new Message(2,"hello",10000);
        //将延时消息放入到延时队列中
        queue.add(msg1);
        queue.add(msg2);
        //启动消费线程，消费添加到延时队列的消息，前提是任务到了延期时间
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Consumer(queue));
        executorService.shutdown();
    }
}
