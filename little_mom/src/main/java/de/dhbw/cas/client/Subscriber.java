package de.dhbw.cas.client;

import de.dhbw.cas.model.Message;
import de.dhbw.cas.model.Topic;

import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a subscriber that receives messages from the broker.
 */
public class Subscriber implements Runnable {
    private final String name;
    private final Map<Topic, Integer> messageCountByTopic = new HashMap<>();
    private final List<Message> messageQueue = new LinkedList<>();

    // Synchronization lock object
    private final Object lock = new Object();

    public Subscriber(String name) {
        this.name = name;
    }

    /**
     * Receives a message and adds it to the queue for processing.
     * 
     * @param message the message to receive
     */
    public void receive(Message message) {
        synchronized (lock) {
            messageQueue.add(message);
            lock.notify(); // Notify the waiting thread
        }
    }

    /**
     * Processes messages from the queue.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Message message;
            synchronized (lock) {
                // Wait for messages if the queue is empty
                while (messageQueue.isEmpty()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                // Process the next message
                message = messageQueue.remove(0);
            }

            // Process the message outside of synchronized block
            if (message != null) {
                Topic topic = message.getTopic();
                synchronized (this) {
                    messageCountByTopic.put(topic, messageCountByTopic.getOrDefault(topic, 0) + 1);
                }
                System.out.println(name + " received: " + message);
            }
        }
    }

    /**
     * Gets the message count by topic.
     * 
     * @return a map of topics to their respective message counts
     */
    public synchronized Map<Topic, Integer> getMessageCountByTopic() {
        return new HashMap<>(messageCountByTopic);
    }
}
