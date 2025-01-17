package entity;

public class Player {
    private final String name;
    private int x, y;
    private int coins;
    public Player(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
    public int getCoins() {
        return coins;
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public void useCoins(int cost) {
        if (coins >= cost) coins -= cost;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}






