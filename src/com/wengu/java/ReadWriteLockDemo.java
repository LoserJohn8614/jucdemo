package com.wengu.java;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author LoserJohn
 * @create 2020-11-12 15:07
 */


//未加读写锁的资源类

class MyCache1 {
    Map<String, Object> map = new HashMap<>();

    public void set(String key, Object value) {
        System.out.println(Thread.currentThread().getName() + "正在写入" + key);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + "写入完成");
    }

    public void get(String key) {
        System.out.println(Thread.currentThread().getName() + "正在读取" + key);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object result = map.get(key);
        System.out.println(Thread.currentThread().getName() + "读取结果:" + result);
    }
}


//加了读写锁的资源类

class MyCache {
    Map<String, Object> map = new HashMap<>();
    ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void set(String key, Object value) {
        try {
            rwLock.writeLock().lock();
            System.out.println(Thread.currentThread().getName() + "正在写入" + key);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "写入完成");
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void get(String key) {
        try {
            rwLock.readLock().lock();
            System.out.println(Thread.currentThread().getName() + "正在读取" + key);
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName() + "读取结果:" + result);
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
//测试类
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();
        for (int i = 1; i <= 5; i++) {
            final int it = i;
            new Thread(() -> {
                myCache.set(it + "key", it + "value");
            }, String.valueOf(i)).start();
            new Thread(() -> {
                myCache.get(it + "key");
            }, String.valueOf(i)).start();
        }
    }
}
