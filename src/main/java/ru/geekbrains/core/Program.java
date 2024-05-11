package ru.geekbrains.core;

import java.util.Random;
import java.util.Scanner;

public class Program {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';
    private static final int fieldSizeX = 5;
    private static final int fieldSizeY = 5;
    private static final int WIN_COUNT = 4;
    private static char[][] field;

    public static void main(String[] args) {
        while (true) {
            initializeMap(fieldSizeX, fieldSizeY);
            printMap();
            while (true) {
                humanTurn();
                printMap();
                if (checkState(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn();
                printMap();
                if (checkState(DOT_AI, "Комп не победим!"))
                    break;
            }
            System.out.println("Еще разок? (Y - да): ");
            if (!scanner.next().equalsIgnoreCase("Y"))
                break;
        }
    }

    /**
     * Инициализация игрового поля
     */
    static void initializeMap(int sizeX, int sizeY) {
        field = new char[sizeX][sizeY];
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    static void printMap() {

        /**
         * Вывод на печать заголовка
         */
        System.out.print("  N" + " ");
        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print("| " + (x + 1) + " ");
        }
        System.out.println("|");

        /**
         * Построчный вывод массива
         */
        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print("  " + (x + 1) + " |");
            for (int y = 0; y < fieldSizeY; y++) {
                System.out.print(" " + field[x][y] + " |");
            }
            System.out.println();
        }
        System.out.println();

    }

    /**
     * Ход компа
     */
    static void aiTurn() {
        int x;
        int y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        } while (isCellHuman(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }

    /**
     * Ход человека
     */
    static void humanTurn() {
        int x;
        int y;
        do {
            // с учетом квадратного массива
            System.out.printf("Введите координаты хода X и Y\n(от 1 до %d) через пробел: ", fieldSizeX);
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        }
        while (!isCellValid(x, y) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    /**
     * Проверка игрового поля на пустое значение
     *
     * @param x
     * @param y
     * @return
     */
    static boolean isCellEmpty(int x, int y) {
        return field[x][y] == DOT_EMPTY;
    }

    static boolean isCellHuman(int x, int y) {
        return field[y][x] == DOT_HUMAN;
    }

    /**
     * Проверка валидности координат
     *
     * @param x
     * @param y
     * @return
     */
    static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Проверка на ничью
     *
     * @return
     */
    static boolean checkDraw() {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y)) return false;
            }
        }
        return true;
    }

    /**
     * Метод проверки победы
     *
     * @param dot фишка игрока
     * @return
     */
//    static boolean checkWin(char dot) {
//        // Проверка победы по горизонталям
//        if (field[0][0] == dot && field[0][1] == dot && field[0][2] == dot) return true;
//        if (field[1][0] == dot && field[1][1] == dot && field[1][2] == dot) return true;
//        if (field[2][0] == dot && field[2][1] == dot && field[2][2] == dot) return true;
//
//        // Проверка победы по вертикалям
//        if (field[0][0] == dot && field[1][0] == dot && field[2][0] == dot) return true;
//        if (field[0][1] == dot && field[1][1] == dot && field[2][1] == dot) return true;
//        if (field[0][2] == dot && field[1][2] == dot && field[2][2] == dot) return true;
//
//        // Проверка победы по диагоналям
//        if (field[0][0] == dot && field[1][1] == dot && field[2][2] == dot) return true;
//        if (field[0][2] == dot && field[1][1] == dot && field[2][0] == dot) return true;
//
//        return false;
//    }

    static boolean checkWinV2(char dot, int win) {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (check1(x, y, dot, win) || check2(x, y, dot, win)
                 || check3(x, y, dot, win) || check4(x, y, dot, win)) return true;
            }
        }
        return false;
    }

    static boolean check1(int x, int y, char dot, int win) {
        int count = 0;
        for (int i = 0; i < win; i++) {
            if (isCellValid(x, y) && field[x][y] == dot) {
                count++;
                y++;
            }
        }
        return count == win;
    }

    static boolean check2(int x, int y, char dot, int win) {
        int count = 0;
        for (int i = 0; i < win; i++) {
            if (isCellValid(x, y) && field[x][y] == dot) {
                count++;
                x++;
            }
        }
        return count == win;
    }

    static boolean check3(int x, int y, char dot, int win) {
        int count = 0;
        for (int i = 0; i < win; i++) {
            if (isCellValid(x, y) && field[x][y] == dot) {
                count++;
                x++;
                y++;
            }
        }
        return count == win;
    }

    static boolean check4(int x, int y, char dot, int win) {
         int count = 0;
        for (int i = 0; i < win; i++) {
            if (isCellValid(x, y) && field[x][y] == dot) {
                count++;
                x--;
                y++;
            }
        }
        return count == win;
    }

    /**
     * Проверка состояния игры
     *
     * @param dot фишка игрока
     * @param s   победный слоган
     * @return
     */
    static boolean checkState(char dot, String s) {
        if (checkWinV2(dot, WIN_COUNT)) {
            System.out.println(s);
            return true;
        } else if (checkDraw()) {
            System.out.println("Ничья");
            return true;
        }
        return false;
    }

}
