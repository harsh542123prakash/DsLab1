import java.util.Scanner;
public class UnsynchronizedCounterTest1 {
    static class Counter {
        int count;
        void inc() {
            count = count + 1;
        }
        int getCount() {
            return count;
        }
    }
    static Counter counter;
    static int numberofincrements;
    static class IncrementerThread extends Thread {
        public void run() {
            for (int i = 0; i < numberofincrements; i++) {
                counter.inc();
            }
        }
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("how many threads do you want to run");
            int numberofthreads = in .nextInt();
            if (numberofthreads <= 0)
                break;
            do {
                System.out.println();
                System.out.println("How many times should each thread increment the counter");
                numberofincrements = in .nextInt();
                if (numberofthreads <= 0) {
                    System.out.println("No of increments should be positive");
                }
            } while (numberofincrements <= 0);
            System.out.println();
            System.out.println("using" + numberofthreads + "threads");
            System.out.println("each thread increment the counter" + numberofincrements + "times");

            System.out.println();
            System.out.println("working");
            System.out.println();
            IncrementerThread[] workers = new IncrementerThread[numberofthreads];
            counter = new Counter();
            for (int i = 0; i < numberofthreads; i++)
                workers[i] = new IncrementerThread();
            for (int i = 0; i < numberofthreads; i++)
                workers[i].start();

            for (int i = 0; i < numberofthreads; i++) {
                try {
                    workers[i].join();
                } catch (InterruptedException e) {}
            }
            System.out.println("the final value of the counter should be" + (numberofincrements * numberofthreads));
            System.out.println("actual final value of counter is:" + counter.getCount());
            System.out.println();
            System.out.println();
        }
    }
}