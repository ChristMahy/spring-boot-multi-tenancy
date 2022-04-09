package be.cmahy.multitenantmysqlimpl.helper;

import java.util.Random;

public class GeneratorRandomValue {
    public static long randomLong() {
        return new Random().nextLong();
    }

    private static String letters = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String generateString() {
        return generateString(100);
    }

    public static String generateString(int length) {
        StringBuilder stringGeneration = new StringBuilder(length);
        Random random = new Random();
        int position;

        while(--length > 0) {
            position = (int) (Math.random() * letters.length());

            stringGeneration.append(letters.charAt(position));
        }

        return stringGeneration.toString();
    }
}
