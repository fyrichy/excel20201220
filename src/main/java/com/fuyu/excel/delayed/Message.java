package com.fuyu.excel.delayed;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @desc:
 * 定义延时队列消息体
 * @author: Richy
 * @date: 2020/12/20/0020
 */
public class Message implements Delayed{

    private int id;
    /**
     * 消息内容
     */
    private String body;

    /**
     * 延迟时长，这个是必须的属性，要根据这个来判断时长
     */
    private long executeTime;

    public Message(){}

    /**
     * 3600分钟 转换成 小时 是多少
     * TimeUnit.HOURS.convert(3600, TimeUnit.MINUTES)
     *
     * 3600分钟 转换成 天 是多少
     * TimeUnit.DAYS.convert(3600, TimeUnit.MINUTES)
     *
     * NANOSECONDS:纳秒
     * MILLISECONDS:毫秒
     * SECONDS:秒
     * 1秒=1000毫秒=1000000微妙=1000000000纳秒
     *
     * //当前时间折合成纳秒
     * System.nanoTime()
     * @param id
     * @param body
     * @param delayTime
     */
    public Message(int id,String body,long delayTime){
        this.id = id;
        this.body = body;
        this.executeTime = TimeUnit.NANOSECONDS.convert(delayTime, TimeUnit.MILLISECONDS) + System.nanoTime();
    }

    /**
     * 延时队列是否到时就按照这个方法来判断
     * 如果返回的是负数，则说明到期
     * 否则，还没到期
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.executeTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    /**
     * 自定义实现比较方法
     * 1,0,-1
     * @param delayed
     * @return
     */
    @Override
    public int compareTo(Delayed delayed) {
        Message message = (Message) delayed;
        return Integer.valueOf(this.id) > Integer.valueOf(message.id)?1:(Integer.valueOf(this.id) < Integer.valueOf(message.id)?-1:0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }
}
