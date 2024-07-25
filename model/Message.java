package model;

/**
 * Represents a message with a topic and content.
 */
public class Message {
    private Topic topic;
    private String content;

    public Message(Topic topic, String content) {
        this.topic = topic;
        this.content = content;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Topic: " + topic + ", Content: " + content;
    }
}
