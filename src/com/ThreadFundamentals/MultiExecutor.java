package com.ThreadFundamentals;

import java.util.ArrayList;
import java.util.List;


public class MultiExecutor {
    List<Runnable> tasks;
    public void MultiExecutor(List<Runnable> tasks){
        this.tasks = tasks;
    }

    public  void executeAll() {
        List<Thread> threads = new ArrayList<>(tasks.size());
        for(Runnable task: tasks){
            Thread t = new Thread(task);
            threads.add(t);
        }
        for(Thread thread: threads){
            thread.start();
        }
    }
    public static void main(String[]args){

    }
}
