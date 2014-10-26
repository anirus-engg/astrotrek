package com.udaan.astrotrek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by anirus on 10/23/14.
 */
public class ComingSoonScreen implements Screen {
    private AstroTrekGame game;
    private OrthographicCamera camera;
    private Vector3 touchPoint;
    private Assets assets;

    public ComingSoonScreen(AstroTrekGame game) {
        this.game = game;
        camera = new OrthographicCamera(320, 480);
        camera.position.set(320 / 2, 480 / 2, 0);
        touchPoint = new Vector3();
        assets = Assets.getInstance();
    }

    public void update() {
        if(Gdx.input.justTouched()) {
            touchPoint = camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if(Screens.inBounds(touchPoint, 41, 174, 238, 33)) {
                assets.playSound(assets.getClick(), 1);
                game.setScreen(new MainScreen((game)));

            }
        }
    }

    public void draw() {
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(assets.getBackground(), 0, 0);
        game.batch.draw(assets.getComingSoon(), 30, 150);
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

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
