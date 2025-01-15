package server;

import client.GameProtocol;
import client.Player;
import shared.Maze;
import java.io.*;
import java.net.Socket;
import java.util.concurrent.ConcurrentMap;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ConcurrentMap<String, Player> players;
    private final Maze maze;
    private PrintWriter out;
    private BufferedReader in;
    private Player player;

    public ClientHandler(Socket clientSocket, ConcurrentMap<String, Player> players, Maze maze) {
        this.clientSocket = clientSocket;
        this.players = players;
        this.maze = maze;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Регистрация игрока
            registerPlayer();

            // Чтение и обработка команд
            String message;
            while ((message = in.readLine()) != null) {
                processMessage(message);
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            disconnectPlayer();
            closeResources();
        }
    }

    private void sendMazeToClient() throws IOException {
        out.println("MAZE_START");
        out.println(maze.toString()); // Отправка сгенерированного лабиринта
        out.println("MAZE_END");
        out.flush(); // Гарантия отправки
    }

    private String getPlayerName() throws IOException {
        out.println("Enter your name:");
        out.flush();
        String name = in.readLine();
        if (name == null || name.isEmpty()) {
            name = "Player" + clientSocket.getPort();
        }
        return name;
    }

    private Player createAndRegisterPlayer(String name) {
        int startX = maze.getStartX();
        int startY = maze.getStartY();
        Player player = new Player(name, startX, startY);
        players.put(name, player);
        return player;
    }

    private void sendPlayerStartPositionToClient(Player player) throws IOException {
        out.println(GameProtocol.playerPositionResponse(player.getX(), player.getY())); // Начальная позиция игрока
        out.flush(); // Гарантия отправки
    }

    private void registerPlayer() throws IOException {
        sendMazeToClient();
        String name = "PLAYER";
        player = createAndRegisterPlayer(name);
        sendPlayerStartPositionToClient(player);
        System.out.println("Player connected: ");
    }

    private void processMessage(String message) {
        if (message.startsWith(GameProtocol.MOVE_PREFIX)) {
            handleMove(message.substring(GameProtocol.MOVE_PREFIX.length()));
        } else if (message.startsWith(GameProtocol.PICKUP_PREFIX)) {
            handlePickup();
        } else {
            out.println("Unknown command.");
            out.flush();
        }
    }

    private void handleMove(String direction) {
        System.out.println("MOVE: " + direction);
        int newX = player.getX();
        int newY = player.getY();
        System.out.println("Старый Х и Y: " + newX + ", " + newY);
        switch (direction) {
            case "UP":
                newY--; // Движение вверх
                break;
            case "DOWN":
                newY++; // Движение вниз
                break;
            case "LEFT":
                newX--; // Движение влево
                break;
            case "RIGHT":
                newX++; // Движение вправо
                break;
            default:
                out.println("Invalid direction.");
                out.flush();
                return;
        }
        System.out.println("Новые Х и Y: " + newX + ", " + newY);

        if (maze.isWalkable(newX, newY)) {
            player.setPosition(newX, newY);
            out.println(GameProtocol.playerPositionResponse(newX, newY));
            out.flush();
            System.out.println("Player moved to: " + direction + "," + newX + ", " + newY);
        } else {
            out.println("Move blocked. You hit a wall.");
            out.flush();
            System.out.println("Player " + player.getName() + " tried to move to: " + direction  + "," + newX + ", " + newY + " but hit a wall.");
        }
    }


    private void handlePickup() {
        // Заглушка: можно расширить для обработки предметов.
        out.println("You picked up an item!");
        out.flush();
    }

    private void disconnectPlayer() {
        if (player != null) {
            players.remove(player.getName());
            System.out.println("Player disconnected: " + player.getName());
        }
    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}
