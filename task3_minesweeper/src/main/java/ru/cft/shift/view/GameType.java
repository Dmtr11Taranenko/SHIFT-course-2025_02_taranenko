package ru.cft.shift.view;

import lombok.Getter;

@Getter
public enum GameType {
    NOVICE(9, 9, 10),
    MEDIUM(16, 16, 40),
    EXPERT(16, 30 ,99);

    private final int height;
    private final int width;
    private final int mines;

    GameType(int height, int width, int mines) {
        this.height = height;
        this.width = width;
        this.mines = mines;

    }

    public static GameType getTypeByMines(int totalMines) {
        return switch (totalMines) {
            case 10 -> NOVICE;
            case 40 -> MEDIUM;
            case 99 -> EXPERT;
            default -> null;
        };
    }
}
