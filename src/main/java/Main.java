import client.MazeClient;
import client.MazeGame;
import server.MazeServer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Run the server in a new thread or process
        Thread serverThread = new Thread(() -> {
            try {
                System.out.println("Starting the Maze Server...");
                MazeServer.main(new String[]{});  // Run the server's main method
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Run the server thread
        serverThread.start();

        // Give the server some time to start (optional, you may want to wait until server is fully ready)
        try {
            Thread.sleep(2000);  // Wait 2 seconds for server to initialize
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Run the client (MazeGame)
        System.out.println("Starting the Maze Game client...");
        MazeGame.main(new String[]{});  // Run the client's main method
    }
}


