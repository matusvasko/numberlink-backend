package sk.tuke.gamestudio.game.numberlink.consoleui;

import lombok.Getter;

@Getter
public enum BackgroundColor {
    RED(1, "\u001B[48;5;196m"),
    BLUE(2, "\u001B[48;5;21m"),
    PURPLE_1(3, "\u001B[48;5;55m"),
    PURPLE_2(7, "\u001B[48;5;135m"),
    PINK(5, "\u001B[48;5;201m"),
    GREEN_1(6, "\u001B[48;5;46m"),
    GREEN_2(4, "\u001B[48;5;42m"),
    CYAN(8, "\u001B[48;5;51m"),
    YELLOW(9, "\u001B[48;5;226m"),
    RESET(0, "\u001B[0m");

    private final int index;
    private final String code;

    BackgroundColor(int index, String code) {
        this.index = index;
        this.code = code;
    }

    public static String getColor(int index) {
        for (BackgroundColor color : BackgroundColor.values()) {
            if (color.getIndex() == index) {
                return color.getCode();
            }
        }
        return null;
    }
}