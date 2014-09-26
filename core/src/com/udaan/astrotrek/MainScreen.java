package com.udaan.astrotrek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class MainScreen implements Screen {
    private AstroTrekGame game;
    private OrthographicCamera camera;
    private Vector3 touchPoint;
    private Assets assets;

    public MainScreen(AstroTrekGame game) {
        this.game = game;
        camera = new OrthographicCamera(320, 480);
        camera.position.set(320 / 2, 480 / 2, 0);
        touchPoint = new Vector3();
        assets = Assets.getInstance();
    }

    public void update() {
        if(Gdx.input.justTouched()) {
            touchPoint = camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            //touched soundEnabled
            if(Screens.inBounds(touchPoint, 5, 46, 64, 64)) {
                Settings.setSoundEnabled(!Settings.isSoundEnabled());
                assets.playSound(assets.getClick(), 1);
            }
            //touched tutorial
            else if(Screens.inBounds(touchPoint, 100, 53, 124, 25)) {
                Settings.setTutorial(!Settings.isTutorial());
                assets.playSound(assets.getClick(), 1);
            }
            //show GameScreen if volume and  tutorial not selected
            else {
                game.setScreen(new GameScreen(game));
                assets.playSound(assets.getClick(), 1);
            }
        }
    }

    private void draw() {
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(assets.getBackground(), 0, 0);
        game.batch.draw(assets.getGameLogo(), 32, 275);
        game.batch.draw(assets.getPlayMenu(), 64, 167);
        game.batch.draw(assets.getGoldStar(), 5, 447);
        game.batch.draw(assets.getHighscore(), (320 - (assets.getHighscore().getWidth() + ("" + Settings.getHighScore()).length() * 30)) / 2, 120);
        game.batch.draw(assets.getVolume(Settings.isSoundEnabled()), 5, 46);
        game.batch.draw(assets.getTutorial(Settings.isTutorial()), 100, 53);
        Screens.drawNumbers(game, "" + Settings.getHighScore(), (320 - (assets.getHighscore().getWidth() + ("" + Settings.getHighScore()).length() * 30)) / 2
                + assets.getHighscore().getWidth() + 1, 120, assets.getNumbers());
        Screens.drawNumbers(game, "" + Settings.getGoldStars(), 42, 453, assets.getNumbers());
        game.batch.end();
    }

    @Override
    public void render(float delta) {
        update();
        draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        game.myRequestHandler.showAds(false);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        Settings.save();
    }

    @Override
    public void resume() {
        // nothing to do
    }

    @Override
    public void dispose() {
        // nothing to do
    }
}
