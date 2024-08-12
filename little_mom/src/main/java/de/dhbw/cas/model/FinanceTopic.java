package de.dhbw.cas.model;

import java.util.Random;

/**
 * Represents a finance topic with specific message generation logic.
 */
public class FinanceTopic implements Topic {
    private String name;
    private Random random = new Random();

    public FinanceTopic(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String generateMessageContent() {
        double price = 100 + (random.nextDouble() * 50);
        return String.format("%.2f$", price) + " (Apple)";
    }

    @Override
    public String toString() {
        return name;
    }
}
