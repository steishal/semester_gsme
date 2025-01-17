package utils;

public class GameProtocol {
    public static final String MOVE_PREFIX = "MOVE:";
    public static final String PICKUP_PREFIX = "PICKUP:";
    public static final String PLAYER_POSITION = "PLAYER_POSITION:";
    public static final String PLACE_TRAP = "PLACE_TRAP";
    public static final String CLEAR_CELL = "CLEAR_CELL:"; // Новая команда

    public static String playerPositionResponse(int x, int y) {
        return PLAYER_POSITION + " " + x + " " + y;
    }

    public static String moveResponse(int x, int y) {
        return MOVE_PREFIX + " " + x + "," + y;
    }

    public static String placeTrapResponse(int x, int y) {
        return PLACE_TRAP + " " + x + " " + y;
    }

    // Новый метод для очистки клетки
    public static String clearCellResponse(int x, int y) {
        return CLEAR_CELL + " " + x + " " + y;
    }
}
















