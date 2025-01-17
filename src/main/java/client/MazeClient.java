package client;

import utils.GameProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class MazeClient {
    private final GameUI gameUI;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public MazeClient(String host, int port, GameUI gameUI) {
        this.gameUI = gameUI;

        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(this::listenToServer).start();
        } catch (IOException e) {
            System.err.println("Failed to connect to server: " + e.getMessage());
            closeResources();
        }
    }

    private void listenToServer() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received from server: " + message); // Логирование
                processServerMessage(message);
            }
        } catch (IOException e) {
            System.err.println("Error while reading from server: " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    private void processServerMessage(String message) {
        if (message.startsWith("MAZE_START")) {
            StringBuilder mazeData = new StringBuilder();
            try {
                // Чтение данных лабиринта
                while (!(message = in.readLine()).equals("MAZE_END")) {
                    mazeData.append(message).append("\n");
                }
            } catch (IOException e) {
                System.err.println("Ошибка при чтении данных лабиринта: " + e.getMessage());
                return;  // Прерываем обработку в случае ошибки
            }

            // Парсинг строки в массив
            String[] lines = mazeData.toString().split("\n");

            // Проверяем на корректность данных лабиринта
            if (lines.length == 0 || lines[0].isEmpty()) {
                System.err.println("Получены пустые данные лабиринта.");
                return;
            }

            int[][] maze = new int[lines.length][lines[0].length()];
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].length() != lines[0].length()) {
                    System.err.println("Ошибка: строки лабиринта имеют разную длину.");
                    return;
                }
                for (int j = 0; j < lines[i].length(); j++) {
                    char cell = lines[i].charAt(j);
                    switch (cell) {
                        case '#':
                            maze[i][j] = 1; // Стена
                            break;
                        case '.':
                            maze[i][j] = 0; // Проход
                            break;
                        case 'M':
                            maze[i][j] = 2; // Монетка
                            break;
                        case 'T':
                            maze[i][j] = 3; // Ловушка
                            break;
                        case 'C':
                            maze[i][j] = 4; // Кубок
                            break;
                        default:
                            maze[i][j] = 0; // По умолчанию - проход
                            break;
                    }
                }
            }
            gameUI.setMaze(maze);  // Обновляем UI новым лабиринтом
        } else if (message.startsWith(GameProtocol.PLAYER_POSITION)) {
            // Обработка положения игрока
            try {
                String positionData = message.substring(GameProtocol.PLAYER_POSITION.length()).trim();
                if (positionData.isEmpty()) {
                    System.err.println("Position data is empty.");
                    return;
                }

                String[] parts = positionData.split("\\s+");
                if (parts.length != 2) {
                    System.err.println("Invalid format: Expected two values but found " + parts.length);
                    return;
                }

                int x = Integer.parseInt(parts[0].trim());
                int y = Integer.parseInt(parts[1].trim());

                gameUI.updatePlayerPosition(x, y);  // Обновление UI с позицией игрока
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format for position: " + e.getMessage());
            }
        } else if (message.startsWith(GameProtocol.CLEAR_CELL)) {
            // Обработка команды CLEAR_CELL
            try {
                String positionData = message.substring(GameProtocol.CLEAR_CELL.length()).trim();
                if (positionData.isEmpty()) {
                    System.err.println("Position data is empty.");
                    return;
                }

                String[] parts = positionData.split("\\s+");
                if (parts.length != 2) {
                    System.err.println("Invalid format: Expected two values but found " + parts.length);
                    return;
                }

                int clearX = Integer.parseInt(parts[0].trim());
                int clearY = Integer.parseInt(parts[1].trim());

                gameUI.clearCell(clearX, clearY); // Обновляем UI, очищая клетку
            } catch (NumberFormatException e) {
                System.err.println("Invalid number format in CLEAR_CELL: " + e.getMessage());
            }
        } else {
            System.err.println("Unknown command received: " + message);
        }
    }


    public void sendMoveCommand(String direction) {
        if (out != null) {
            out.println(GameProtocol.MOVE_PREFIX + direction); // Отправка команды MOVE с направлением
        } else {
            System.err.println("Connection to server is not established.");
        }
    }


    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
        } catch (IOException e) {
            System.err.println("Error while closing resources: " + e.getMessage());
        }
    }
}











