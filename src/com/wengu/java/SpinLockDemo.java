package com.wengu.java;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁demo
 * @author LoserJohn
 * @create 2020-11-12 14:28
 */
public class SpinLockDemo {
    AtomicReference<Thread> reference = new AtomicReference<>();

    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t 尝试获取锁");
        while (!reference.compareAndSet(null, thread)) {

        }
        System.out.println(Thread.currentThread().getName()+"\t获取锁成功");
    }

    public void myUnlock() {
        Thread thread = Thread.currentThread();
        reference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t 释放锁");
    }

    public static void main(String[] args) {
        SpinLockDemo lock = new SpinLockDemo();
        new Thread(() -> {
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.myUnlock();
        }, "t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            lock.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.myUnlock();
        }, "t2").start();

    }
}
