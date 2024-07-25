package client;

import model.Message;
import model.Topic;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a subscriber that receives messages from the broker.
 */
public class Subscriber implements Runnable {
    private String name;
    private Map<Topic, Integer> messageCountByTopic = new HashMap<>();

    public Subscriber(String name) {
        this.name = name;
    }

    /**
     * Receives a message and increments the count for the associated topic.
     * 
     * @param message the message to receive
     */
    public synchronized void receive(Message message) {
        Topic topic = message.getTopic();
        messageCountByTopic.put(topic, messageCountByTopic.getOrDefault(topic, 0) + 1);
        System.out.println(name + " received: " + message);
    }

    /**
     * Gets the message count by topic.
     * 
     * @return a map of topics to their respective message counts
     */
    public synchronized Map<Topic, Integer> getMessageCountByTopic() {
        return new HashMap<>(messageCountByTopic);
    }

    @Override
    public void run() {
        // The MessageBroker class is responsible for managing the message queue and dispatching messages to subscribers
    }
}
