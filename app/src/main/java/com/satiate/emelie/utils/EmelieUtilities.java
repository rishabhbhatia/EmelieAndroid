package com.satiate.emelie.utils;

import java.util.Random;

/**
 * Created by Rishabh Bhatia on 10/11/2016.
 */

public class EmelieUtilities {

    public static String generateRandomStrings(int length)
    {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static int generateRandomAge(int min, int max)
    {
        return new Random().nextInt((max - min) + 1) + min;
    }

}
