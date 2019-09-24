    package com.ThreadCoordination;

    /**
     * Thread Termination - why and When
     * 1. Threads consume resources - Memory and Kernel even when not running. And Consume CPU and cache Memory when running.
     * 2. When thread has finished its work and if the application is running the resorces used by the Thread has to be cleaned up.
     * 3. If thread is misbehaving and has to be stopped.

     * Interrupting a Thread
     * threadB.interrupt() called From threadA.
     * ThreadB gets interrupted if the method that is executing in ThreadB  throws an InterruptedException.
     *
     * Setting a Thread as Daemon will end execution of the thread once the main thread ends- even if you don't handle interrupt
     */
    import java.math.BigInteger;


    public class ThreadTermination {
        public static void main(String[] args) {
            //Example-1  - Test Blocking Thread
            Thread t1 = new Thread(new BlockingTask());
            t1.start();
            t1.interrupt(); //Without this interrupt the thread will be Blocked. Try running UN-Commenting interrupt line.
            //Example-2 - Test Long Computational Thread
            Thread t2 = new Thread(new LongComputationTask(new BigInteger("2") , new BigInteger("4")));
            t2.start();
            t2.interrupt();
        }
        //Example-1 lets take a blocking thread
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

        //Example lets take a long Computational Thread
        public static class LongComputationTask implements Runnable{
            private BigInteger base;
            private BigInteger power;

            public LongComputationTask(BigInteger base, BigInteger power) {
                this.base = base;
                this.power = power;
            }

            @Override
            public void run() {
                System.out.println("base *** power = "+ pow(base,power));
            }
            private BigInteger pow(BigInteger base, BigInteger power){
                BigInteger result = BigInteger.ONE;
                // In the long computational code find the hot spot causing delay in program &
                // Insert the condition to handle the interrupt.
                if(Thread.currentThread().isInterrupted()){
                    System.out.println("Prematurely interrupted");
                    return BigInteger.ZERO;
                }
                for(BigInteger i = BigInteger.ZERO; i.compareTo(power)!=0; i.add(BigInteger.ONE)){
                    result = result.multiply(base);
                }
                return result;
            }
        }
    }
