package com.CriticalsectionSynchronization;

/**
 * This is an example for DataRace. You will get "Data Race detected" o/p as the -
 * Compilers and Cpu may execute the instructions out of order in order to optimize performance and utilization - they
 * do so in away that logical correctness of the code is mantained. Out of order execution is important for the speed.
 * CPU rearranges instructions for better
 * Branch Prediction(optimised loops, if statements), Vectorizations, Prefetching instructions for better cache performance.
 *
 */
public class RaceConditionIssue {
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
        private int x=0;
        private int y=0;

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
