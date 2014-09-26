package com.udaan.astrotrek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class Settings {
    private static final String FILE = ".astrotrek";
    private static boolean soundEnabled = true;
    private static int highScores = 0;
    private static boolean tutorial = true;
    private static int goldStars = 3;

    public static void load() {
        try {
            FileHandle filehandle = Gdx.files.external(FILE);

            String[] strings = filehandle.readString().split("\n");

            soundEnabled = Boolean.parseBoolean(strings[0]);
            tutorial = Boolean.parseBoolean(strings[1]);
            goldStars = Integer.parseInt(strings[2]);
            highScores = Integer.parseInt(strings[3]);
        } catch (Throwable e) {
            // :( It's ok we have defaults
        }
    }

    public static void save() {
        try {
            FileHandle filehandle = Gdx.files.external(FILE);

            filehandle.writeString(Boolean.toString(soundEnabled)+"\n", false);
            filehandle.writeString(Boolean.toString(tutorial)+"\n", true);
            filehandle.writeString(Integer.toString(goldStars)+"\n", true);
            filehandle.writeString(Integer.toString(highScores)+"\n", true );
        } catch (Throwable ignored) {
        }
    }

    public static void addScore(int score) {
        highScores = (highScores > score) ? highScores : score;
    }

    public static boolean isSoundEnabled() {
        return soundEnabled;
    }

    public static void setSoundEnabled(boolean soundEnabled) {
        Settings.soundEnabled = soundEnabled;
    }

    public static int getHighScore() {
        return highScores;
    }

    public static boolean isTutorial() {
        return tutorial;
    }

    public static void setTutorial(boolean tutorial) {
        Settings.tutorial = tutorial;
    }

    public static int getGoldStars() {
        return goldStars;
    }

    public static void setGoldStars(int goldStars) {
        Settings.goldStars = goldStars;
    }

    public static boolean checkGoldStars(int stars) {
        return Settings.goldStars >= stars;
    }
}
