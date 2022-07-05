package utils;

public class Util {
    public static int getRandomNumber(int from, int to) {
        int range = to - from + 1;
        return (int) (Math.random() * range) + from;
    }
}
