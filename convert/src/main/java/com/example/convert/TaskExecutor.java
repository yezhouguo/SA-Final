package com.example.convert;

import java.util.concurrent.BlockingQueue;

public class TaskExecutor extends Thread {

    // 任务队列，里面是要执行的任务。
    private BlockingQueue<String> taskQueue;

    // 任务队列是否在执行任务
    private boolean isRunning = true;

    Convert convert;

    public TaskExecutor(BlockingQueue<String> taskQueue) {
        this.taskQueue = taskQueue;
    }

    // 退出。
    public void quit() {
        isRunning = false;
        interrupt();
    }

    public boolean isrunning() {
        return isRunning;
    }

    @Override
    public void run() {
        while (isRunning) { // 如果是执行状态就待着。
            String fileName;
            try {
                System.out.println("taskQueueSize:"+taskQueue.size());
                fileName = taskQueue.take(); // 下一个任务，没有就等着。
            } catch (InterruptedException e) {
                if (!isRunning) {
                    // 发生意外了，是退出状态的话就把窗口关闭。
                    interrupt();
                    break; // 如果执行到break，后面的代码就无效了。
                }
                // 发生意外了，不是退出状态，那么窗口继续等待。
                continue;
            }

            // 执行任务。
            convert = new Convert(fileName);
            convert.process();
        }
    }

}

