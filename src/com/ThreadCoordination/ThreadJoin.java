package com.ThreadCoordination;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreadJoin {
    public static void main(String[] args) throws InterruptedException{
        List<Long> inputList = Arrays.asList(0L, 34L, 23235L, 23L, 9867L);
        // we want to calculate factorial of !0L, !345L, !2345L, !23L, !5567L
        List<FactorialThread> threads = new ArrayList<>();
        for(long input: inputList) {
            threads.add(new FactorialThread(input));
        }
        for(FactorialThread thread: threads){
            thread.start();
        }
        for(FactorialThread thread: threads){
            // the Main thread will wait for all thread to complete.
            thread.join(); // if this line commented not all the results will be computed before Main() thread ends.
           // thread.join(2000) ; // the join(ms) accepts time on millisec to wait
        }
        for(int i =0 ; i<inputList.size(); i++){
            FactorialThread factorialThread = threads.get(i);
            if(factorialThread.isFinished()) {
                System.out.println("Factorial of " + inputList.get(i) + " is " + factorialThread.getResult());
            }else{
                System.out.println("Calculation for "+inputList.get(i) + "is still in progress.");
            }
        }
    }

    public static class FactorialThread extends Thread{
        private long inputNumber;
        private BigInteger result = BigInteger.ZERO;
        private boolean finished = false;

        @Override
        public void run() {
            this.result = findFactorial(inputNumber);
            this.finished = true;
        }

        public FactorialThread(long inputNumber){
            this.inputNumber=inputNumber;
        }
        public BigInteger findFactorial(long n){
            BigInteger tempResult = BigInteger.ONE;
            for(long i=n; i>0; i--){
                tempResult = tempResult.multiply(new BigInteger((Long.toString(i))));
            }
            return tempResult;
        }

        public boolean isFinished() {
            return finished;
        }

        public BigInteger getResult() {
            return result;
        }
    }
}
