package com.ThreadFundamentals;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VaultExample {
    public static int MAX_PASSWORD=9999;

    public static void main(String[] args) {
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));
        System.out.println("Password Set is "+vault.getPassword());
        List<Thread> threadList = new ArrayList();
        threadList.add(new AsccendingHackerThread(vault));
        threadList.add(new DescendingHackerThread(vault));
        threadList.add(new PoliceThread());
        for(Thread thread: threadList){
            thread.start();
        }
    }

    public static class Vault {
        private int password;      //in this example we will take password to be numeric
        public int getPassword() {
            return password;
        }
        public Vault(int password){
            this.password = password;
        }
        public boolean isCorrectPassword(int guess){
            try{
                Thread.sleep(5);
            }catch(InterruptedException ex){
                ex.printStackTrace();
            }
            return this.password == guess;
        }
    }
    public static abstract class HackerThread extends Thread{
        protected Vault vault;
        public HackerThread(Vault vault){
            this.vault= vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting Thread : "+this.getName());
            super.start();
        }
    }
    public static class AsccendingHackerThread extends HackerThread {
        public AsccendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = 0; guess < MAX_PASSWORD; guess++) {
                //System.out.println(this.getName()+ "hacking password.");
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " won. guessed password is " + guess +". Game Over!!");
                    System.exit(0);
                }
            }
        }
    }
    public static class DescendingHackerThread extends HackerThread{
        public DescendingHackerThread(Vault vault){
            super(vault);
        }

        @Override
        public void run() {
            for(int guess=MAX_PASSWORD; guess>=0; guess--){
                //System.out.println(this.getName()+ "hacking password.");
                if(vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " won. guessed password is " + guess +". Game Over!!");
                    System.exit(0);
                }
            }
        }
    }

    public static class PoliceThread extends Thread{
        @Override
        public void run() {
            for(int i=10; i>0; i--){
                try {
                    Thread.sleep(1000); /// pause 1 sec b/n each count.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Police Arriving in "+i+" second.");
            }
            System.out.println("Police Arrived! Game Over Hackers!!");
            System.exit(0);
        }
    }
}
