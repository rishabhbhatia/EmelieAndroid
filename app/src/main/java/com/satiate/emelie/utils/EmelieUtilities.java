package com.satiate.emelie.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.Display;

import com.satiate.emelie.models.User;
import com.satiate.emelie.ui.activities.HomeActivity;

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

    public static String getRandomGender()
    {
        String[] sexArray = {"M", "F"};
        return sexArray[new Random().nextInt(sexArray.length)];
    }

    public static User generateRandomUser()
    {
        User user = new User();
        user.setName(generateRandomStrings(5)+" "+generateRandomStrings(3));
        user.setAge(generateRandomAge(17, 30));
        user.setImageUrl(HomeActivity.randomImageUrl);

        return user;
    }

    public static int getDominantColor(Bitmap bitmap)
    {
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return color;
    }

    public static int getScreenHeight(Activity activity)
    {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int getScreenWidth(Activity activity)
    {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

}
