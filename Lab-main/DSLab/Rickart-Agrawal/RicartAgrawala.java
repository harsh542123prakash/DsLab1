import java.util.PriorityQueue;
import java.util.Queue;

public class RicartAgrawalaMutexSimulation {

    public static void main(String[] args) {
        int numProcesses = 3;
        Process[] processes = new Process[numProcesses];

        for (int i = 0; i < numProcesses; i++) {
            processes[i] = new Process(i, numProcesses);
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
    private int numProcesses;
    private int logicalClock;
    private boolean requestingCS;
    private Queue<Message> messageQueue;

    public Process(int processId, int numProcesses) {
        this.processId = processId;
        this.numProcesses = numProcesses;
        this.logicalClock = 0;
        this.requestingCS = false;
        this.messageQueue = new PriorityQueue<>();
    }

    private void sendMessage(int toProcessId, MessageType messageType) {
        logicalClock++;
        Message message = new Message(processId, logicalClock, messageType);
        System.out.println("Process " + processId + " sends " + messageType + " message to Process " + toProcessId + " at logical time " + logicalClock);
        // In a real distributed system, you would send this message to the destination process.
    }

    private void receiveMessage(Message message) {
        logicalClock = Math.max(logicalClock, message.getTimestamp()) + 1;
        System.out.println("Process " + processId + " receives " + message.getType() + " message from Process " + message.getProcessId() + " at logical time " + logicalClock);

        switch (message.getType()) {
            case REQUEST:
                handleRequest(message);
                break;
            case REPLY:
                handleReply(message);
                break;
        }
    }

    private void handleRequest(Message request) {
        if (requestingCS && (request.getTimestamp() < logicalClock || (request.getTimestamp() == logicalClock && request.getProcessId() < processId))) {
            sendMessage(request.getProcessId(), MessageType.REPLY);
        } else {
            messageQueue.add(request);
        }
    }

    private void handleReply(Message reply) {
        messageQueue.add(reply);
    }

    private void requestCriticalSection() {
        requestingCS = true;
        sendMessage(processId, MessageType.REQUEST);

        // Wait for replies from all other processes
        while (messageQueue.size() < numProcesses - 1) {
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

        requestingCS = false;
        // Send deferred replies
        while (!messageQueue.isEmpty()) {
            Message deferredReply = messageQueue.poll();
            sendMessage(deferredReply.getProcessId(), MessageType.REPLY);
        }
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
}

enum MessageType {
    REQUEST,
    REPLY
}

class Message implements Comparable<Message> {
    private int processId;
    private int timestamp;
    private MessageType type;

    public Message(int processId, int timestamp, MessageType type) {
        this.processId = processId;
        this.timestamp = timestamp;
        this.type = type;
    }

    public int getProcessId() {
        return processId;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public MessageType getType() {
        return type;
    }

    @Override
    public int compareTo(Message other) {
        return this.timestamp - other.timestamp;
    }
}
