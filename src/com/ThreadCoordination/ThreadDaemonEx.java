package com.ThreadCoordination;

public class ThreadDaemonEx {
    public static void main(String[] args) {
        Thread t = new Thread(new BlockingTask());
        t.setDaemon(true);  // Setting thread as Daemon will terminate the thread when main thread ends.
    }
    public static class BlockingTask implements Runnable{
        @Override
        public void run() {
            try {
                Thread.sleep(500000);
            }catch(InterruptedException ex){
                System.out.println("Exiting blocking Thread!");
            }
        }
    }
}
