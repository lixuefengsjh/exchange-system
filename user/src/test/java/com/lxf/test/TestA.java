package com.lxf.test;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lxf
 * @create: 2020-05-13 21:27
 * @description: 测试用例
 */
@Slf4j
public class TestA {
    private static Integer count = 0;
    private static final Integer FULL = 10;
    private static String LOCK = "lock";
    private  class producter implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<10;i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK){
                    while (count==0){
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count++;
                    System.out.println(Thread.currentThread().getName() + "生产者生产，目前总共有" + count);
                    LOCK.notifyAll();

                }
            };
        }
    }
    private class consumer implements Runnable{

        @Override
        public void run() {
            for(int i=0;i<10;i++){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (LOCK){
                    while (count==0){
                        try {
                            LOCK.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    count--;
                    System.out.println(Thread.currentThread().getName() + "消费者消费，目前总共有" + count);
                    LOCK.notifyAll();

                }
            };
        }
    }

    public static void main(String[] args) {
        String s=(String)null;
        System.out.println(s+"iiii");
        TestA t=new  TestA();
        new Thread(t.new consumer()).start();
        new Thread(t.new producter()).start();
    }
}



