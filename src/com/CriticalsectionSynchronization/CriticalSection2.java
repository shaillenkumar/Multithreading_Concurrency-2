package com.CriticalsectionSynchronization;

/**
 * Synchronized for code blocks-
 * Locks access to the critical code Section when a thread is executing the block.
 * We have to create Object lock to apply synchronized for critical/atomic code blocks
 * Since the increment and decrement blocks are locked down by single lock object
 * increment and decrement can be accessed by only one thread at a given time.
 * both the methods are locked for a single thread at a given point of time.
 */
public class CriticalSection2 {

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
     * Here we are using one lock objects.
     * So here the Critical/Atomic code section in increment method and decrement method will be locked and at the same time
     * - all doors are locked.
     */
    private static class InventoryCounter{
        private int items=0;
        private Object lock1 = new Object(); // Creating object1 for Object Lock


        public  void increment(){
            synchronized (lock1) {
                this.items++;
            }
        }
        public  void decrement(){
            synchronized (lock1) {
                this.items--;
            }
        }

        public synchronized int getItems(){

            return items;
        }

    }

}
