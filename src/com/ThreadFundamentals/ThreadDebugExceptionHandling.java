package com.ThreadFundamentals;

public class ThreadDebugExceptionHandling {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                throw new RuntimeException("Creating Exception on Purpose");
            }
        });
        t1.setName("Misbehaving Thread.");
        t1.setPriority(Thread.NORM_PRIORITY);
        t1.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("A critical error in the thread happened in the thread"+t1.getName()+" the error is "+e.getMessage());
            }
        });
    }
}
