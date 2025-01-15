package client;

import javax.swing.*;
import java.awt.*;

public class GameUI extends JPanel {
    private int[][] maze;
    private int playerX, playerY;

    public GameUI() {
        this.maze = null;
        this.playerX = 1;  // Начальная позиция игрока по X
        this.playerY = 1;  // Начальная позиция игрока по Y
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
        repaint();  // Перерисовать панель при изменении лабиринта
    }

    public void updatePlayerPosition(int x, int y) {
        this.playerX = x;
        this.playerY = y;
        repaint();  // Перерисовать панель при изменении позиции игрока
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (maze != null) {
            int cellSize = 30; // Размер клетки
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j] == 1) { // Стены
                        g.setColor(Color.BLACK);
                    } else { // Проходы
                        g.setColor(Color.WHITE);
                    }
                    g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);  // Отрисовка клетки
                    g.setColor(Color.GRAY);
                    g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);  // Рамка клетки
                }
            }

            // Отрисовка игрока
            g.setColor(Color.RED);  // Цвет игрока
            g.fillOval(playerX * cellSize + 5, playerY * cellSize + 5, cellSize - 10, cellSize - 10);  // Отрисовка круга игрока
        }
    }
}





