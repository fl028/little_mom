package model;

import java.util.Random;

/**
 * Represents a weather topic with specific message generation logic.
 */
public class WeatherTopic implements Topic {
    private String name;
    private Random random = new Random();

    public WeatherTopic(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String generateMessageContent() {
        int temperature = 20 + random.nextInt(15);
        return temperature + "*C (Location: Stuttgart)";
    }

    @Override
    public String toString() {
        return name;
    }
}
