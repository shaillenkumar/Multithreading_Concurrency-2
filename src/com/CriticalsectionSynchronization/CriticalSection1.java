package com.CriticalsectionSynchronization;

/**
 * Synchronized for methods-
 * Locks access to the critical method or code Section
 * Only single thread can access the critical section at any one time.
 * Here both the increment and decrement item methods are locked at a time
 * when a thread enters the Monitor - all door are locked.
 *
 */
public class CriticalSection1 {

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
     * Here both the methods are locked at a time when a thread enter the Monitor - all door are locked.
     */
    private static class InventoryCounter{
        private int items=0;

        public synchronized void increment(){
            items++;
        }
        public synchronized void decrement(){
            items--;
        }
        public synchronized int getItems(){
            return items;
        }

    }

}
