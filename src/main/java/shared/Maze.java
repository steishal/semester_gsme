package shared;

import java.util.Random;

import java.util.Random;
import java.util.Stack;

public class Maze {
    private final int[][] maze;
    private final int rows;
    private final int cols;
    private final Random random;

    public Maze(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.maze = new int[rows][cols];
        this.random = new Random();
        generateMaze();
    }

    private void generateMaze() {
        // Инициализация стен
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                maze[i][j] = 1; // 1 = стена
            }
        }

        // Создание путей
        Random random = new Random();
        carvePath(1, 1, random);
        placeItems(2, 20); // 20 монет
        placeItems(3, 10);
        placeItems(4, 1); // Один кубок
    }

    private void carvePath(int x, int y, Random random) {
        maze[x][y] = 0; // 0 = проход
        int[] directions = {0, 1, 2, 3};
        shuffleArray(directions, random); // Перемешиваем направления

        for (int direction : directions) {
            // Вычисляем координаты следующей клетки на 2 шага вперёд
            int nx = x, ny = y;
            switch (direction) {
                case 0:
                    nx = x - 2;
                    break; // вверх
                case 1:
                    ny = y + 2;
                    break; // вправо
                case 2:
                    nx = x + 2;
                    break; // вниз
                case 3:
                    ny = y - 2;
                    break; // влево
            }

            // Проверяем, можно ли пробить стену
            if (isInBounds(nx, ny) && maze[nx][ny] == 1) {
                // Пробиваем стену между текущей клеткой и целевой клеткой
                maze[x + (nx - x) / 2][y + (ny - y) / 2] = 0;
                // Рекурсивно вызываем функцию для новой клетки
                carvePath(nx, ny, random);
            }
        }
    }

    public boolean placeTrap(int x, int y) {
        if (maze[x][y] == 0) { // Проверяем, что клетка пустая
            maze[x][y] = 3; // Устанавливаем ловушку
            return true;
        }
        return false; // Ловушку нельзя установить
    }

    public boolean isCoin(int x, int y) {
        return maze[y][x] == 2;
    }

    public boolean isTrap(int x, int y) {
        return maze[y][x] == 3;
    }

    public boolean isTrophy(int x, int y) {
        return maze[y][x] == 4;
    }

    public void clearCell(int x, int y) {
        maze[y][x] = 0;
    }

    private void shuffleArray(int[] array, Random random) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }

    public boolean isWalkable(int x, int y) {
        return isInBounds(y, x) && maze[y][x] == 0 || maze[y][x] == 2 || maze[y][x] == 3 || maze[y][x] == 4; // Ячейка проходима, если в границах и равна 0
    }

    private boolean isInBounds(int y, int x) {
        return x >= 0 && x < rows && y >= 0 && y < cols; // Проверка границ лабиринта
    }


    public int getStartX() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 0) return i; // Находим первую проходимую клетку
            }
        }
        return 0; // По умолчанию (если не найдена проходимая точка)
    }

    public int getStartY() {
        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                if (maze[i][j] == 0) return j; // Находим первую проходимую клетку
            }
        }
        return 0; // По умолчанию (если не найдена проходимая точка)
    }

    private void placeItems(int itemType, int count) {
        int placed = 0;
        while (placed < count) {
            int x = random.nextInt(rows);
            int y = random.nextInt(cols);
            if (maze[x][y] == 0) { // Только на проходной клетке
                maze[x][y] = itemType;
                placed++;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] row : maze) {
            for (int cell : row) {
                // Стены отображаются как #
                if (cell == 1) {
                    sb.append("#");
                }
                // Проходы отображаются как .
                else if (cell == 0) {
                    sb.append(".");
                }
                // Монетки отображаются как M
                else if (cell == 2) {
                    sb.append("M");
                }
                // Ловушки отображаются как T
                else if (cell == 3) {
                    sb.append("T");
                }
                // Кубки отображаются как C
                else if (cell == 4) {
                    sb.append("C");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}





