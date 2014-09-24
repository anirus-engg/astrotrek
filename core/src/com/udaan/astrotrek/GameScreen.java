package com.udaan.astrotrek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.udaan.astrotrek.Asteroid.AsteroidState;

import java.util.List;

public class GameScreen implements Screen {
    enum GameState {
        Running,
        Paused,
        GameOver
    }

    private AstroTrekGame game;
    private OrthographicCamera camera;
    private Vector3 touchPoint;
    private Assets assets;

    private GameState gameState = GameState.Running;
    private World world;
    private int score = 0;
    private int stars = 0;
    private int starsRequired = 1;

    public GameScreen(AstroTrekGame game) {
        this.game  =game;
        camera = new OrthographicCamera(320, 480);
        camera.position.set(320 / 2, 480 / 2, 0);
        touchPoint = new Vector3();
        assets = Assets.getInstance();
        world = new World();

        assets.playMusic(assets.getBackgroundMusic(), 1, true);
    }

    private void update(float deltaTime) {
        if(gameState == GameState.Running)
            updateRunning(deltaTime);
        if(gameState == GameState.Paused)
            updatePaused();
        if(gameState == GameState.GameOver)
            updateGameOver();
    }

    private void updateRunning(float deltaTime) {
        world.update(deltaTime);

        if(Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if(touchPoint.x < (300 - assets.getSpaceship(false).getWidth()) / 2)
                world.getSpaceship().goLeft();
            if(touchPoint.x > (300 + assets.getSpaceship(false).getWidth()) / 2)
                world.getSpaceship().goRight();
        }

        world.update(deltaTime);

        if(world.isGameOver()) {
            gameState = GameState.GameOver;
            assets.playSound(assets.getCrash(), 1);
            return;
        }

        if(world.isStarCollect()) {
            assets.playSound(assets.getObjectPick(), 1);
            stars++;
            world.setStarCollect(false);
        }

        if(score != world.getScore())
            score = world.getScore();
    }

    private void updatePaused() {
        if(assets.getBackgroundMusic().isPlaying())
            assets.getBackgroundMusic().stop();

        if(Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if(Screens.inBounds(touchPoint, 20, 20, 300, 460)) {
                assets.playSound(assets.getClick(), 1);
                assets.playMusic(assets.getBackgroundMusic(), 1, true);
                gameState = GameState.Running;
            }
        }
    }

    private void updateGameOver() {
        if(assets.getBackgroundMusic().isPlaying())
            assets.getBackgroundMusic().stop();

        if(Gdx.input.justTouched()) {
            camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            //TODO - updateGameOver
            if(Screens.inBounds(touchPoint, 41, 174, 238, 33)) {
                assets.playSound(assets.getClick(), 1);
                saveScores();
                game.setScreen(new MainScreen((game)));

            }
            else if(Screens.inBounds(touchPoint, 41, 229, 238, 33)) {
                assets.playSound(assets.getClick(), 1);
                if(Settings.checkGoldStars(starsRequired)) {
                    Settings.setGoldStars(Settings.getGoldStars() - starsRequired);
                    starsRequired *= 2;
                    world.setGameOver(false);
                    gameState = GameState.Running;
                    world.getSpaceship().setX(131);
                    world.getSpaceship().setY(58);
                    world.getSpaceship().setSpeed(Spaceship.STABLE);
                    world.getSpaceship().setHandicap(true);
                    assets.playSound(assets.getClick(), 1);
                    assets.playMusic(assets.getBackgroundMusic(), 1, true);
                }
                else {
                    game.myRequestHandler.showShop();
                }
            }

        }
    }

    private void draw() {
        Spaceship spaceship = world.getSpaceship();
        List<Asteroid> asteroids = world.getAsteroids();
        Star star = world.getStar();

        camera.update();

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(assets.getBackground(), 0, 0);
        game.batch.draw(assets.getBronzeStar(), 5, 447);
        game.batch.draw(assets.getSpaceship(spaceship.isHandicap()), spaceship.getX(), spaceship.getY());
        if(star.getY() != -530)
            game.batch.draw(assets.getBronzeStar(),star.getX(), star.getY());

        int len = asteroids.size();
        for (int i = 0; i < len; i++) {
            Asteroid asteroid = asteroids.get(i);

            if(asteroid.getState() == AsteroidState.Active)
                game.batch.draw(assets.getAsteroids(), asteroid.getX(), asteroid.getY(), asteroid.getType() * 32, 0, 32, 32);
            else if(asteroid.getState() == AsteroidState.Explode)
                game.batch.draw(assets.getAsteroidsExplosion(), asteroid.getX(), asteroid.getY(), asteroid.getType() * 32, 0, 32, 32);
        }

        Screens.drawNumbers(game, "" + score, 314 - ("" + score).length() * 30 , 453, assets.getNumbers());
        Screens.drawNumbers(game, "" + stars, 42, 453, assets.getNumbers());

        if(Settings.isTutorial()) {
            switch(world.getTutorialStage()) {
                case 1:
                    game.batch.draw(assets.getTutorial1(), 5, 100, 0, 0, 150, 280);
                    break;
                case 2:
                    game.batch.draw(assets.getTutorial1(), 160 + 5, 100, 150, 0, 150, 280);
                    break;
                case 3:
                    game.batch.draw(assets.getTutorial2(), 32, 378);
                    break;
                default:
                    Settings.setTutorial(false);
                    break;
            }

        }

        if(gameState == GameState.Paused) {
            game.batch.draw(assets.getPauseMenu(), 54, 176);
        }

        if(gameState == GameState.GameOver) {
            game.batch.draw(assets.getGameOverMenu(), 30, 150);
            Screens.drawNumbers(game, "" + starsRequired, 230, 235, assets.getNumbers());
        }

        game.batch.end();
    }

    private void saveScores() {
        Settings.setGoldStars(Settings.getGoldStars() + stars / 10);
        Settings.addScore(score);
        Settings.save();
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        game.myRequestHandler.showAds(true);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {
        saveScores();
        gameState = GameState.Paused;
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
