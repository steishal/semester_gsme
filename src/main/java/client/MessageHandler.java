package client;

@FunctionalInterface
public interface MessageHandler {
    void handle(String message);
}

