package client;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;


public class GameUI extends JPanel {
    private int[][] maze;
    private int playerX, playerY;
    private Image[] walkingRightFrames;
    private Timer animationTimer; // Таймер для анимации
    private Image[] currentFrames; // Текущий массив спрайтов
    private int currentFrame = 0;  // Текущий кадр анимации


    private Image resizeImage(Image originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();
        if (originalImage  == null) {
            throw new IllegalArgumentException("Image not found or failed to load");
        }
        return resizedImage;
    }

    private final Image wallImage;
    private final Image pathImage;
    private final Image coinImage;
    private final Image trapImage;
    private final Image trophyImage;
    public GameUI() {
        this.maze = null;
        this.playerX = 1;
        this.playerY = 1;

        // Попробуем загрузить изображения
        try {
            wallImage = resizeImage(new ImageIcon(Objects.requireNonNull(
                    getClass().getResource("/image/wall.png"))).getImage(), 30, 30);
            pathImage = resizeImage(new ImageIcon(Objects.requireNonNull(
                    getClass().getResource("/image/path.png"))).getImage(), 30, 30);
            coinImage = resizeImage(new ImageIcon(Objects.requireNonNull(
                    getClass().getResource("/image/coin.png"))).getImage(), 30, 30);
            trapImage = resizeImage(new ImageIcon(Objects.requireNonNull(
                    getClass().getResource("/image/trap.png"))).getImage(), 30, 30);
            trophyImage = resizeImage(new ImageIcon(Objects.requireNonNull(
                    getClass().getResource("/image/trophy.png"))).getImage(), 30, 30);

            loadWalkingSprites();
            currentFrames = walkingRightFrames; // Загружаем спрайты для всех направлений
        } catch (Exception e) {
            throw new RuntimeException("Failed to load images.", e);
        }

        // Инициализация таймера для анимации
        animationTimer = new Timer(150, e -> {
            currentFrame = (currentFrame + 1) % currentFrames.length; // Меняем кадр
            repaint(); // Перерисовываем игрока
        });
        animationTimer.start(); // Запускаем таймер
    }

    public void setMaze(int[][] maze) {
        this.maze = maze;
        repaint();  // Перерисовать панель при изменении лабиринта
    }

    public void updatePlayerPosition(int x, int y) {
        this.playerX = x;
        this.playerY = y;
        animationTimer.restart();
        repaint();  // Перерисовать панель при изменении позиции игрока
    }
    public void clearCell(int x, int y) {
        // Обновление визуального отображения клетки
        if (maze != null && x >= 0 && y >= 0) {
            System.out.println("Clearing cell at: (" + x + ", " + y + ")");
            maze[y][x] = 0; // Устанавливаем как пустую клетку
            repaint(); // Перерисовываем интерфейс
        } else {
            System.err.println("Invalid cell coordinates for clearing: (" + x + ", " + y + ")");
        }
    }
    private void loadWalkingSprites() {
        walkingRightFrames = new Image[4];
        for (int i = 0; i < walkingRightFrames.length; i++) {
            walkingRightFrames[i] = resizeImage(new ImageIcon(Objects.requireNonNull(
                    getClass().getResource("/image/walking_frame" + i + ".png"))).getImage(), 30, 80);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (maze != null) {
            int cellSize = 30; // Размер клетки
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j] == 1) {
                        g.drawImage(wallImage, j * cellSize, i * cellSize, cellSize, cellSize, this); // Стены
                    } else if (maze[i][j] == 0) {
                        g.drawImage(pathImage, j * cellSize, i * cellSize, cellSize, cellSize, this); // Путь
                    } else if (maze[i][j] == 2) {
                        g.drawImage(coinImage, j * cellSize, i * cellSize, cellSize, cellSize, this); // Монеты
                    } else if (maze[i][j] == 3) {
                        g.drawImage(trapImage, j * cellSize, i * cellSize, cellSize, cellSize, this); // Ловушки
                    } else if (maze[i][j] == 4) {
                        g.drawImage(trophyImage, j * cellSize, i * cellSize, cellSize, cellSize, this); // Трофеи
                    }
                }
            }

            // Отрисовка игрока
            g.drawImage(walkingRightFrames[currentFrame], playerX * cellSize, playerY * cellSize, cellSize, cellSize, this);
        }
    }
}

