package com.ThreadLocking;

import java.util.Random;
//Uncomment Line 63 and 66 to create circular dependency for deadlock situation

public class DeadlockIssue {

    public static class TrainA implements  Runnable{
        private Intersection intersection = new Intersection();
        private Random random = new Random();
        public TrainA(Intersection intersection){
            this.intersection = intersection;
        }
        @Override
        public void run() {
            while(true){
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                }catch (InterruptedException ie){
                }
                intersection.takeRoadA();
            }
        }
    }
    public static class TrainB implements  Runnable{
        private Intersection intersection = new Intersection();
        private Random random = new Random();
        public TrainB(Intersection intersection){
            this.intersection = intersection;
        }
        @Override
        public void run() {
            while(true){
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                }catch (InterruptedException ie){
                }
                intersection.takeRoadB();
            }
        }
    }
    public static class Intersection {
        private Object roadA = new Object();
        private Object roadB = new Object();

        // Once a Thread(train) enters RoadA it locks down RoadB(Thread.sleep)
        public void takeRoadA() {
            synchronized (roadA) {
                System.out.println("Road A is locked by the Thread " + Thread.currentThread().getName());
                synchronized (roadB) {
                    System.out.println("Train is passing through Road A");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void takeRoadB() {
            //synchronized (roadB) { //Uncomment Line 63 and 66 to create circular dependency for deadlock situation
            synchronized (roadA) {
                System.out.println("Road B is locked by the Thread " + Thread.currentThread().getName());
                //synchronized (roadA) { //Uncomment Line 63 and 66 to create circular dependency for deadlock situation
                synchronized (roadB) {
                    System.out.println("Train is passing through Road B");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        Intersection intersection = new Intersection();
        Thread trainAThread = new Thread(new TrainA(intersection));
        Thread trainBThread = new Thread(new TrainB(intersection));

        trainAThread.start();
        trainBThread.start();
    }
}
