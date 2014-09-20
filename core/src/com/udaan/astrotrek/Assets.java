package com.udaan.astrotrek;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Assets {
    private static Assets instance;

    private static Texture asteroids;
    private static Texture asteroidsExplosion;
    private static Texture background;
    private static Texture bronzeStar;
    private static Texture gameLogo;
    private static Texture gameOverMenu;
    private static Texture goldStar;
    private static Texture highscore;
    private static Texture numbers;
    private static Texture pauseMenu;
    private static Texture playMenu;
    private static Texture spaceship;
    private static Texture spaceshipHandicap;
    private static Texture timerPie;
    private static Texture tutorial1;
    private static Texture tutorial2;
    private static Texture tutorialChecked;
    private static Texture tutorialUnchecked;
    private static Texture volume;
    private static Texture volumeMute;
    private static Music backgroundMusic;
    private static Sound click;
    private static Sound crash;
    private static Sound objectPick;

    private Assets() {}

    public static synchronized Assets getInstance() {
        if(instance == null)
            instance = new Assets();
        return instance;
    }

    private static Texture loadTexture(String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public void load() {
        background = loadTexture("images/background.jpg");
        spaceship = loadTexture("images/spaceship.png");
        spaceshipHandicap = loadTexture("images/spaceship_handicap.png");
        gameLogo = loadTexture("images/game_logo.png");
        playMenu = loadTexture("images/play_menu.png");
        volume = loadTexture("images/volume.png");
        volumeMute = loadTexture("images/volume_mute.png");
        asteroids = loadTexture("images/asteroids.png");
        gameOverMenu = loadTexture("images/game_over_menu.png");
        asteroidsExplosion = loadTexture("images/asteroids_explosion.png");
        pauseMenu = loadTexture("images/pause_menu.png");
        goldStar = loadTexture("images/gold_star.png");
        bronzeStar = loadTexture("images/bronze_star.png");
        numbers = loadTexture("images/numbers.png");
        tutorial1 = loadTexture("images/tutorial_1.png");
        tutorial2 = loadTexture("images/tutorial_2.png");
        highscore = loadTexture("images/highscore.png");
        tutorialChecked = loadTexture("images/tutorial_checked.png");
        tutorialUnchecked = loadTexture("images/tutorial_unchecked.png");
        //Assets.setTimerPie(g.newPixmap("images/timer_pie.png", PixmapFormat.ARGB4444));

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/background.wav"));

        click = Gdx.audio.newSound(Gdx.files.internal("sounds/click.wav"));
        crash = Gdx.audio.newSound(Gdx.files.internal("sounds/crash.wav"));
        objectPick = Gdx.audio.newSound(Gdx.files.internal("sounds/object_pick.wav"));
    }

    public void playSound(Sound sound, float volume) {
        if(Settings.isSoundEnabled()) sound.play(volume);
    }

    public void playMusic(Music music, float volume, boolean looping) {
        if(Settings.isSoundEnabled()) {
            music.setLooping(looping);
            music.setVolume(volume);
            music.play();
        }
    }

    public Texture getAsteroids() {
        return asteroids;
    }

    public Texture getAsteroidsExplosion() {
        return asteroidsExplosion;
    }

    public Texture getBackground() {
        return background;
    }

    public Texture getBronzeStar() {
        return bronzeStar;
    }

    public Texture getGameLogo() {
        return gameLogo;
    }

    public Texture getGameOverMenu() {
        return gameOverMenu;
    }

    public Texture getGoldStar() {
        return goldStar;
    }

    public Texture getHighscore() {
        return highscore;
    }

    public Texture getNumbers() {
        return numbers;
    }

    public Texture getPauseMenu() {
        return pauseMenu;
    }

    public Texture getPlayMenu() {
        return playMenu;
    }

    public Texture getSpaceship(boolean isHandicap) {
        return isHandicap ? spaceshipHandicap: spaceship;
    }

    public Texture getTimerPie() {
        return timerPie;
    }

    public Texture getTutorial1() {
        return tutorial1;
    }

    public Texture getTutorial2() {
        return tutorial2;
    }

    public Texture getTutorial(boolean isTutorialChecked) {
        return isTutorialChecked ? tutorialChecked : tutorialUnchecked;
    }

    public Texture getVolume(boolean isSoundEnabled) {
        return isSoundEnabled ? volume : volumeMute;
    }

    public Music getBackgroundMusic() {
        return backgroundMusic;
    }

    public Sound getClick() {
        return click;
    }

    public Sound getCrash() {
        return crash;
    }

    public Sound getObjectPick() {
        return objectPick;
    }
}
