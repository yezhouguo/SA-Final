package com.example.convert;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.stereotype.Component;


@Component
public class TaskQueue {


    final int size = 3;

    // 队列，里面是任务。
    private BlockingQueue<String> mTaskQueue;
    // 好多执行器。
    private TaskExecutor[] mTaskExecutors;

    // new队列的时候，要指定执行器数量。这可以确定你开的多个线程是否需要等待。
    public TaskQueue() {
        mTaskQueue = new LinkedBlockingQueue<>();
        mTaskExecutors = new TaskExecutor[size];
        start();;
    }

    // 开启队列。
    public void start() {
        stop();
        // 开启队列。
        for (int i = 0; i < mTaskExecutors.length; i++) {
            mTaskExecutors[i] = new TaskExecutor(mTaskQueue);
            mTaskExecutors[i].start();
        }
    }

    // 关闭队列。
    public void stop() {
        if (mTaskExecutors != null)
            for (TaskExecutor taskExecutor : mTaskExecutors) {
                if (taskExecutor != null) taskExecutor.quit();
            }
    }

    //添加任务到队列。
    public <T extends String> int add(T task) {
        if (!mTaskQueue.contains(task)) {
            mTaskQueue.add(task);
        }
        // 返回队列中的任务数
        return mTaskQueue.size();
    }

    public int getQueueSize()
    {
        return mTaskQueue.size();
    }

    public void runTasks() {
        // 开启队列。
        for (int i = 0; i < mTaskExecutors.length; i++) {
            if(mTaskExecutors[i].convert.isDone()==true)
            {
                mTaskExecutors[i] = new TaskExecutor(mTaskQueue);
                mTaskExecutors[i].start();
            }

        }
    }
    

}

