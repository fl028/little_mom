package de.dhbw.cas;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.dhbw.cas.broker.MessageBroker;
import de.dhbw.cas.client.Publisher;
import de.dhbw.cas.client.Subscriber;
import de.dhbw.cas.model.*;

/**
 * The {@code MOMSimulation} class represents a simple simulation of a MOM (message oriented middleware).
 * <p>
 * This class contains the main method for starting the simulation.
 * </p>
 *
 * @author Nils Hepp, Florian Böhm
 * @version 1.0
 * @since 2024-08-12
 */
public class MOMSimulation {
    private static final long SIMULATION_DURATION_MS = 5000;
    private static final int MAX_MESSAGES = 10;

    public static void main(String[] args) {
        MessageBroker broker = new MessageBroker(MAX_MESSAGES);

        // Initialize topics
        Topic sports = new SportsTopic("Sports");
        Topic finance = new FinanceTopic("Finance");
        Topic weather = new WeatherTopic("Weather");

        // Initialize publishers
        List<Publisher> publishers = new ArrayList<>();
        publishers.add(new Publisher(broker, sports));
        publishers.add(new Publisher(broker, finance));
        publishers.add(new Publisher(broker, weather));

        // Initialize subscribers
        Subscriber subscriber1 = new Subscriber("Subscriber1");
        Subscriber subscriber2 = new Subscriber("Subscriber2");
        Subscriber subscriber3 = new Subscriber("Subscriber3");

        // Subscribe subscribers to topics
        broker.subscribe(new Topic[] { sports, finance }, subscriber1);
        broker.subscribe(new Topic[] { weather }, subscriber2);
        broker.subscribe(new Topic[] { weather, finance }, subscriber3);

        // Create and start threads for publishers and subscribers
        List<Thread> threads = new ArrayList<>();
        for (Publisher publisher : publishers) {
            Thread pubThread = new Thread(publisher);
            pubThread.start();
            threads.add(pubThread);
        }

        Thread subThread1 = new Thread(subscriber1);
        Thread subThread2 = new Thread(subscriber2);
        Thread subThread3 = new Thread(subscriber3);
        subThread1.start();
        subThread2.start();
        subThread3.start();
        threads.add(subThread1);
        threads.add(subThread2);
        threads.add(subThread3);

        // Start dispatching messages
        Thread dispatchThread = new Thread(broker::dispatchMessages);
        dispatchThread.start();
        threads.add(dispatchThread);

        try {
            // Wait
            Thread.sleep(SIMULATION_DURATION_MS);
        } catch (InterruptedException e) {
            System.out.println("Simulation was interrupted.");
            Thread.currentThread().interrupt();
        }

        // Interrupt the publisher threads
        for (Thread thread : threads) {
            thread.interrupt();
        }

        // Stop the dispatching thread
        broker.stop();

        // Ensure all threads have completed
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(thread.getName() + " thread was interrupted while waiting for it to finish.");
                Thread.currentThread().interrupt();
            }
        }

        // Summary
        System.out.println();
        System.out.println("###################################################");
        System.out.println();
        System.out.println("Simulation ended. Reason: " +
                (broker.getMessageCount() >= MAX_MESSAGES ? "Maximum message count reached"
                        : "Simulation duration reached"));
        System.out.println("Total number of messages published: " + broker.getMessageCount());

        Map<Topic, Integer> messageCountByTopic = broker.getMessageCountByTopic();
        System.out.println("Messages published by topic:");
        for (Map.Entry<Topic, Integer> entry : messageCountByTopic.entrySet()) {
            System.out.println(entry.getKey().getName() + ": " + entry.getValue());
        }

        System.out.println("Number of active publishers: " + publishers.size());
        System.out.println("Number of active subscribers: " + broker.getSubscribers().size());
    }
}
