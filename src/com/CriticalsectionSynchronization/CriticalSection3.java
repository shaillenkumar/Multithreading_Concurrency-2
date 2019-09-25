package com.CriticalsectionSynchronization;

/**
 * Synchronized for code blocks-
 * Locks access to the critical code Section.
 * We have to create Object lock to apply synchronized for critical/atomic code blocks
 * Since the increment and decrement blocks are locked down by two different lock objects
 * increment and decrement can be accessed by two different threads at a time.
 * If the increment and decrement blocks were locked down by a single lock object
 * then both the methods would've been locked down for a single thread at a given point of time.
 */
public class CriticalSection3 {

    public static void main(String[] args)throws InterruptedException {
        InventoryCounter inventoryCounter = new InventoryCounter();
        DecrementingThread decrementingThread = new DecrementingThread(inventoryCounter);
        IncrementingThread incrementingThread = new IncrementingThread(inventoryCounter);

        incrementingThread.start();
        decrementingThread.start();

        incrementingThread.join();
        decrementingThread.join();

        System.out.println("We currently have "+inventoryCounter.getItems()+" items");

    }

    public static class DecrementingThread extends Thread{
        private InventoryCounter inventoryCounter;
        public DecrementingThread(InventoryCounter inventoryCounter){
            this.inventoryCounter=inventoryCounter;
        }
        @Override
        public void run() {
            for(int i=0; i< 10000; i++){
                inventoryCounter.decrement();
            }
        }
    }

    public static class IncrementingThread extends Thread{
        InventoryCounter inventoryCounter;
        public IncrementingThread(InventoryCounter inventoryCounter){
            this.inventoryCounter = inventoryCounter;
        }
        @Override
        public void run() {
            for(int i=0; i< 10000; i++){
                inventoryCounter.increment();
            }
        }
    }


    /**
     * Here we are using two different lock objects.
     * So here ThreadA can execute code in increment method and increment method will be locked and at the same time
     * another thread ThreadB can execute code in decrement method and the method will be locked.
     * This is unlike CriticalSection1 where - all doors are locked.
     */
    private static class InventoryCounter{
        private int items=0;
        private Object lock1 = new Object(); // Creating object1 for Object Lock
        private Object lock2 = new Object(); // Creating object2 for Object Lock

        public  void increment(){
            synchronized (lock1) {
                this.items++;
            }
        }
        public  void decrement(){
            synchronized (lock2) {
                this.items--;
            }
        }

        public synchronized int getItems(){

            return items;
        }

    }

}
