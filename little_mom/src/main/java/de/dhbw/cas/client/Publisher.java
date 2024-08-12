package de.dhbw.cas.client;

import de.dhbw.cas.broker.MessageBroker;
import de.dhbw.cas.model.Message;
import de.dhbw.cas.model.Topic;

/**
 * A Publisher generates and publishes messages to a specified topic on a
 * {@link MessageBroker}.
 * This class implements {@link Runnable} to allow instances to be run in
 * separate threads.
 */
public class Publisher implements Runnable {
    /** Default speed (in milliseconds) for message publication. */
    private static int speed = 3000;

    /** The {@link MessageBroker} to which messages are published. */
    private MessageBroker broker;

    /** The {@link Topic} to which this publisher will generate messages. */
    private Topic topic;

    /**
     * Constructs a Publisher with the specified {@link MessageBroker} and
     * {@link Topic}.
     * 
     * @param broker the {@link MessageBroker} to publish messages to
     * @param topic  the {@link Topic} for which messages are generated
     */
    public Publisher(MessageBroker broker, Topic topic) {
        this.broker = broker;
        this.topic = topic;
    }

    /**
     * Sets the speed (in milliseconds) at which messages are generated and
     * published.
     * 
     * @param speed the new speed (in milliseconds) for message publication
     */
    public static void setSpeed(int speed) {
        Publisher.speed = speed;
    }

    /**
     * Continuously generates and publishes messages to the specified {@link Topic}
     * at intervals
     * determined by the speed parameter. The method runs in a separate thread.
     */
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                int sleepDuration = (int) (Math.random() * speed);
                Thread.sleep(sleepDuration);

                // Generate a message and publish it
                String content = topic.generateMessageContent();
                Message message = new Message(topic, content);

                // Publish the message using synchronized block
                synchronized (broker) {
                    broker.publish(message);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
