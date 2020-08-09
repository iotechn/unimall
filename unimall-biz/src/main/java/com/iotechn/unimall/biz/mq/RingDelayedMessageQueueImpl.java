package com.iotechn.unimall.biz.mq;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 单机时使用的延迟消息队列实现
 * （1）环形队列，例如可以创建一个包含3600个Set的环形队列（本质是个数组）
 *
 * （2）任务集合，环上每一个Set是都是一群任务集合
 *
 * 同时，启动一个timer，这个timer每隔1s，在上述环形队列中移动一格，有一个Current Index指针来标识正在检测的Set。
 *
 * Task结构中有两个很重要的属性：
 *
 * （1）Cycle-Num：当Current Index第几圈扫描到这个Set时，执行任务
 *
 * （2）task：需要执行的任务指针
 */

@Component
public class RingDelayedMessageQueueImpl implements DelayedMessageQueue {

    /**
     * 每秒轮询线程
     */
    private ScheduledExecutorService schedule;

    /**
     * 执行Slot中的任务
     */
    private ExecutorService executor;

    /**
     * 指针索引
     */
    private AtomicInteger currentIndex;

    private ArrayList<HashSet<Slot>> ring;

    {
        ring = new ArrayList<>();
        for (int i = 0; i < 3600; i++) {
            ring.add(new HashSet<Slot>());
        }

        schedule = Executors.newScheduledThreadPool(1);
        executor = Executors.newFixedThreadPool(2);
        currentIndex = new AtomicInteger(0);
        // 1. 开启定时任务
        schedule.scheduleAtFixedRate(() -> {
            if(!this.currentIndex.compareAndSet(3599, 0)){
                this.currentIndex.incrementAndGet();
            }
            HashSet<Slot> hashSet = ring.get(this.currentIndex.get());
            if (!CollectionUtils.isEmpty(hashSet)) {
                // 因为可能在执行当前任务时有可能，被添加元素
                synchronized (hashSet) {
                    Iterator<Slot> iterator = hashSet.iterator();
                    while (iterator.hasNext()){
                        Slot item = iterator.next();
                        if (item.cycleNum > 0) {
                            item.cycleNum--;
                            continue;
                        }

                        // 2. 将需要执行的slot任务放入线程
                        executor.submit(item.task);
                        iterator.remove();
                    }
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    /**
     *
     * @param task
     * @param delay second
     * @return
     */
    @Override
    public Boolean publishTask(Callable task, Integer delay) {
        Slot slot = new Slot();
        slot.setTask(task);
        if(delay <= 0){
            delay = 1;
        }
        int temp = this.currentIndex.get();
        temp += delay;
        slot.setCycleNum(temp / 3600);
        HashSet<Slot> slots = this.ring.get(temp % 3600);

        synchronized (slots){
            slots.add(slot);
        }
        return true;
    }

    @Override
    public Boolean publishTask(Integer code, String value, Integer delay) {
        throw new RuntimeException();
    }

    @Override
    public Boolean deleteTask(Integer code, String value) {
        throw new RuntimeException();
    }

    @Override
    public Long getTaskTime(Integer code, String value) {
        throw new RuntimeException();
    }

    public static class Slot {

        /**
         * 当索引指针(currentIndex)到达对应Set时，为0时执行任务，然后移除任务，不能0则减一
         */
        private int cycleNum;

        /**
         * 实现了的执行任务
         */
        private Callable task;

        public void setCycleNum(int cycleNum) {
            this.cycleNum = cycleNum;
        }

        public void setTask(Callable task) {
            this.task = task;
        }

        public int getCycleNum() {
            return cycleNum;
        }

        public Callable getTask() {
            return task;
        }
    }
}
