package com.CriticalsectionSynchronization;

/**
 * This is an example for DataRace.
 * Compilers and Cpu may execute the instructions out of order in order to optimize performance and utilization.
 * By declaring the varible x, y as volatile the instructions before the x,y access and after x,y access are executed in order.
 *
 */
public class RaceConditionIssueSoln {
    public static void main(String[] args) {
        SharedClass sharedClass = new SharedClass();
        Thread thread1 = new Thread(()->{
            for(int i=0; i<Integer.MAX_VALUE; i++){
                sharedClass.increment();
            }
        });
        Thread thread2 = new Thread(()-> {
            for(int i=0;i<Integer.MAX_VALUE; i++){
                sharedClass.checkForDataRace();
            }
        });
    }
    public static class SharedClass{
        volatile private int x=0;
        volatile private int y=0;

        public void increment(){
            x++;
            y++;
        }
        public void checkForDataRace(){
            if(y>x){
                System.out.println("y >x Data Race Detected");
            }
        }
    }
}
