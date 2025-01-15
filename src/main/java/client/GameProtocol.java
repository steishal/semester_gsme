package client;

public class GameProtocol {
    public static final String MOVE_PREFIX = "MOVE:";
    public static final String PICKUP_PREFIX = "PICKUP:";
    public static final String PLAYER_POSITION = "PLAYER_POSITION:";

    public static String playerPositionResponse(int x, int y) {
        return PLAYER_POSITION + " " + x + " " + y;
    }

    public static String moveResponse(int x, int y) {
        return MOVE_PREFIX + " " + x + "," + y;
    }
}














