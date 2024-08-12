package de.dhbw.cas.broker;

import de.dhbw.cas.client.Subscriber;
import de.dhbw.cas.model.Message;
import de.dhbw.cas.model.Topic;
import java.util.*;

/**
 * Manages the subscriptions and message delivery.
 */
public class MessageBroker {
    private final Map<Topic, List<Subscriber>> subscribers = new HashMap<>();
    private final List<Message> messageQueue = new LinkedList<>();
    private final Map<Topic, Integer> messageCountByTopic = new HashMap<>();

    private boolean running = true; 
    private final int maxMessages; // Maximum number of messages to process
    private int messageCount = 0; // Total message count

    /**
     * Constructs a MessageBroker with a maximum message count.
     * 
     * @param maxMessages the maximum number of messages to process
     */
    public MessageBroker(int maxMessages) {
        this.maxMessages = maxMessages;
    }

    /**
     * Publishes a message to the broker.
     * 
     * @param message the message to publish
     */
    public synchronized void publish(Message message) {
        messageQueue.add(message);
        messageCount++;
        messageCountByTopic.put(message.getTopic(), messageCountByTopic.getOrDefault(message.getTopic(), 0) + 1);
        System.out.println("Published: " + message);
        notifyAll();
    }

    /**
     * Subscribes a subscriber to multiple topics.
     * 
     * @param topics     the topics to subscribe to
     * @param subscriber the subscriber to add
     */
    public synchronized void subscribe(Topic[] topics, Subscriber subscriber) {
        for (Topic topic : topics) {
            subscribers.computeIfAbsent(topic, k -> new ArrayList<>()).add(subscriber);
        }
    }

    /**
     * Dispatches messages to subscribers.
     */
    public void dispatchMessages() {
        while (running) {
            synchronized (this) {
                // Wait if no messages
                while (messageQueue.isEmpty() && running) {
                    try {
                        wait(); // Wait for message
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                if (!running) {
                    break;
                }

                // Process the next message
                Message message = messageQueue.remove(0); // Retrieve and remove the first message
                if (message != null) {
                    List<Subscriber> subs = subscribers.get(message.getTopic());
                    if (subs != null) {
                        for (Subscriber subscriber : subs) {
                            subscriber.receive(message);
                        }
                    }
                }

                // Terminate dispatching if message limit is reached
                if (messageCount >= maxMessages) {
                    stop();
                }
            }
        }
    }

    /**
     * Stops the dispatching of messages.
     */
    public synchronized void stop() {
        running = false;
        notifyAll();
    }

    /**
     * Gets the total number of messages processed.
     * 
     * @return the total number of messages
     */
    public synchronized int getMessageCount() {
        return messageCount;
    }

    /**
     * Gets the count of messages by topic.
     * 
     * @return a map of topics to their respective message counts
     */
    public Map<Topic, Integer> getMessageCountByTopic() {
        synchronized (this) {
            return new HashMap<>(messageCountByTopic);
        }
    }

    /**
     * Gets the list of subscribers.
     * 
     * @return the list of subscribers
     */
    public List<Subscriber> getSubscribers() {
        synchronized (this) {
            return new ArrayList<>(subscribers.values().stream().flatMap(List::stream).distinct().toList());
        }
    }
}
