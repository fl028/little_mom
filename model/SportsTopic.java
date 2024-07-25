package model;

import java.util.Random;

/**
 * Represents a sports topic with specific message generation logic.
 */
public class SportsTopic implements Topic {
    private String name;
    private Random random = new Random();

    public SportsTopic(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String generateMessageContent() {
        int score1 = random.nextInt(5);
        int score2 = random.nextInt(5);
        int minute = random.nextInt(91);
        return String.format("Current Score: %d - %d (Minute %d)", score1, score2, minute);
    }

    @Override
    public String toString() {
        return name;
    }
}
