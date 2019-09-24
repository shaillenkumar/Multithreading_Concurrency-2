package com.ThreadFundamentals;
/*
    1. Thread Creation with Java Runnable
    2. Thread class capabilities
    3. Thread Debugging
 */
public class ThreadCreation {
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                System.out.println("We are now in a Thread"+Thread.currentThread().getName());
            }
        });
        System.out.println("Before Start of New Thread"+Thread.currentThread().getName());

        t1.setName("New Worker Thread");
        t1.setPriority(Thread.NORM_PRIORITY); //MaxPriority=10 NormPriority=5 MinPriority=1
        t1.start();

        Thread.sleep(2000);

        System.out.println("End of Thread"+Thread.currentThread().getName());
    }
}
