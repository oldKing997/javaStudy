package com.wwq.socket;

/**
 * @author wwq
 * @date 2019年6月26日 11:25:49
 * @deprecated
 */

import java.util.LinkedList;

public class ThreadPool {

    // 线程池大小
    private int threadPoolSize;

    // 任务容器
    LinkedList<Runnable> tasks = new LinkedList<Runnable>();

    // 试图消费任务的线程

    public ThreadPool(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;

        // 启动10个任务消费者线程
        synchronized (tasks) {
            for (int i = 0; i < threadPoolSize; i++) {
                new TaskConsumeThread("任务消费者线程 " + i).start();
            }
        }
    }

    public void add(Runnable r) {
        synchronized (tasks) {
            tasks.add(r);
            // 唤醒等待的任务消费者线程
            tasks.notifyAll();
        }
    }

    class TaskConsumeThread extends Thread {
        public TaskConsumeThread(String name) {
            super(name);
        }

        Runnable task;

        public void run() {
//            System.out.println("启动： " + this.getName());
            while (true) {
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    task = tasks.removeLast();
                    // 允许添加任务的线程可以继续添加任务
                    tasks.notifyAll();

                }
//                System.out.println(this.getName() + " 获取到任务，并执行");
                task.run();
            }
        }
    }

}