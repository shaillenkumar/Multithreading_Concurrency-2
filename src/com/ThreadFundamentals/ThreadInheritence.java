package com.ThreadFundamentals;

public class ThreadInheritence {
    public static void main(String[] args) {
        NewThread t1 = new NewThread();
        t1.start();
    }

    //another way to create Thread is by extending the Thread Class
    private static class NewThread extends Thread{
        @Override
        public void run() {
            this.setName("New Worker Thread");  // you get accesss to Thread Methods with this. expression.
            this.setPriority(5);
            System.out.println(" Hello from "+Thread.currentThread().getName());
        }
    }
}
