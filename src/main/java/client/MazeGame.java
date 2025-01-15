package client;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class MazeGame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Maze Game");
        GameUI gameUI = new GameUI();
        MazeClient client = new MazeClient("localhost", 12345, gameUI);

        frame.add(gameUI);
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Привязываем KeyListener к gameUI, а не к JFrame
        gameUI.setFocusable(true); // Обязательно добавьте это для получения фокуса
        gameUI.requestFocusInWindow(); // Запрашиваем фокус сразу после создания компонента

        gameUI.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        client.sendMoveCommand("LEFT");
                        break;
                    case KeyEvent.VK_RIGHT:
                        client.sendMoveCommand("RIGHT");
                        break;
                    case KeyEvent.VK_UP:
                        client.sendMoveCommand("UP");
                        break;
                    case KeyEvent.VK_DOWN:
                        client.sendMoveCommand("DOWN");
                        break;
                    default:
                        break;
                }
            }
        });
    }
}

















