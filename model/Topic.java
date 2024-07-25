package model;

/**
 * Interface for different topics in the message broker system.
 */
public interface Topic {
    String getName();

    String generateMessageContent();
}
