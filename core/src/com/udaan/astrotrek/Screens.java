package com.udaan.astrotrek;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

/**
 * Created on 7/28/14.
 */
public class Screens {

    public static final boolean inBounds(Vector3 touchPoint, int x, int y, int width, int height) {
        return touchPoint.x > x && touchPoint.x < x + width - 1 &&
                touchPoint.y > y && touchPoint.y < y + height - 1;
    }

    public static void drawNumbers(AstroTrekGame game, String line, int x, int y, Texture numbers) {
        int width = numbers.getWidth() / 10;
        int height = numbers.getHeight();
        int len = line.length();
        for (int i = 0; i < len; i++) {
            char character = line.charAt(i);
            if (character == ' ') {
                x += 20;
                continue;
            }
            int srcX;
            int srcWidth;
            if (character == '.') {
                srcX = width * 10;
                srcWidth = 10;
            } else {
                srcX = (character - '0') * width;
                srcWidth = width;
            }
            game.batch.draw(numbers, x, y, srcX, 0, srcWidth, height);
            x += srcWidth;
        }
    }
}
