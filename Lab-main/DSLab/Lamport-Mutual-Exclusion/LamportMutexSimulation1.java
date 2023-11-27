import java.util.PriorityQueue;
import java.util.Queue;

public class LamportMutexSimulation1 {

    public static void main(String[] args) {
        int numProcesses = 3;
        Process[] processes = new Process[numProcesses];

        for (int i = 0; i < numProcesses; i++) {
            processes[i] = new Process(i);
        }

        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numProcesses; j++) {
                if (i != j) {
                    processes[i].addMessageToQueue(new Message(j, 0));
                }
            }
        }

        Thread[] threads = new Thread[numProcesses];
        for (int i = 0; i < numProcesses; i++) {
            threads[i] = new Thread(processes[i]);
            threads[i].start();
        }
    }
}

class Process implements Runnable {
    private int processId;
    private int logicalClock;
    private Queue<Message> messageQueue;

    public Process(int processId) {
        this.processId = processId;
        this.logicalClock = 0;
        this.messageQueue = new PriorityQueue<>();
    }

    private void sendMessage(int toProcessId) {
        logicalClock++;
        Message message = new Message(processId, logicalClock);
        System.out.println("Process " + processId + " sends message to Process " + toProcessId + " at logical time " + logicalClock);
        // In a real distributed system, you would send this message to the destination process.
    }

    private void receiveMessage(Message message) {
        logicalClock = Math.max(logicalClock, message.getTimestamp()) + 1;
        System.out.println("Process " + processId + " receives message from Process " + message.getProcessId() + " at logical time " + logicalClock);
        // In a real distributed system, you would process the received message.
    }

    private void requestCriticalSection() {
        sendMessage(processId); // Broadcast request to all processes
        // Wait for replies from all other processes
        while (messageQueue.size() < 2) {
            Thread.yield(); // Simulate waiting
        }
        enterCriticalSection();
    }

    private void enterCriticalSection() {
        System.out.println("Process " + processId + " enters critical section.");
        // Simulate critical section work
        try {
            Thread.sleep((int) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Process " + processId + " exits critical section.");
    }

    public void run() {
        // Simulate some non-critical section work
        try {
            Thread.sleep((int) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        requestCriticalSection();
    }

    public void addMessageToQueue(Message message) {
        messageQueue.add(message);
    }
}

class Message implements Comparable<Message> {
    private int processId;
    private int timestamp;

    public Message(int processId, int timestamp) {
        this.processId = processId;
        this.timestamp = timestamp;
    }

    public int getProcessId() {
        return processId;
    }

    public int getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(Message other) {
        return this.timestamp - other.timestamp;
    }
}
