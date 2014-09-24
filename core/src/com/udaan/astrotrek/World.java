package com.udaan.astrotrek;

import com.udaan.astrotrek.Asteroid.AsteroidState;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    protected static final int AVAILABLE_SLOTS = 10;
    protected static final int ASTEROID_TYPES = 15;
    protected static final int ASTEROID_SPEEDS = 4;
    protected static final float NEW_ASTEROID_TIME = 0.75f;
    protected static final float UPDATE_ASTEROID_TIME = 0.75f;
    protected static final float NEW_STAR_TIME = 10.0f;
    protected static final float UPDATE_SCORE = 1.0f;
    protected static final float UPDATE_TUTORIAL = 3.0f;
    protected static final float HANDICAP = 3.0f;

    private List<Asteroid> asteroids = new ArrayList<Asteroid>();
    private Spaceship spaceship;
    private Star star = new Star();
    private int score = 0;
    private boolean gameOver = false;
    private boolean starCollect = false;
    private float spaceshipTick = 0.0f;
    private float starTick = 0.0f;
    private float newStarTick = 0.0f;
    private float newAsteroidTick = 0.0f;
    private float updateAsteroidTick = 0.0f;
    private float updateScoreTick = 0.0f;
    private float tutorialTick = 0.0f;
    private float handicapTick = 0.0f;
    private Random generator = new Random();
    private int[] speed = {30, 35, 40, 45};
    private int tutorialStage = 1;


    public World() {
        spaceship = new Spaceship(131, 58, Spaceship.STABLE, 0.0625f);
        newAsteroid();
    }

    private void newAsteroid() {
        Asteroid asteroid = new Asteroid(generator.nextInt(AVAILABLE_SLOTS) * 32,
                generator.nextInt(ASTEROID_TYPES), 0.2f / speed[generator.nextInt(ASTEROID_SPEEDS)]);
        asteroid.setState(AsteroidState.Active);
        asteroids.add(asteroid);
    }

    private void newStar() {
        star = new Star(generator.nextInt(AVAILABLE_SLOTS) * 32, 0.005f);
    }


    private void clearAsteroid() {
        int len = asteroids.size();
        for(int j = len - 1; j >= 0; j--) {
            if(asteroids.get(j).getState() == AsteroidState.Clear) {
                asteroids.remove(j);
            }
        }
    }

    private void updateAsteroid() {
        for (Asteroid asteroid : asteroids) {
            if (asteroid.getState() == AsteroidState.Explode) {
                asteroid.setState(AsteroidState.Clear);
            }
        }
    }

    public void update(float deltaTime) {
        if(gameOver)
            return;

        spaceshipTick += deltaTime;
        newAsteroidTick += deltaTime;
        updateAsteroidTick += deltaTime;
        newStarTick += deltaTime;
        starTick += deltaTime;
        updateScoreTick += deltaTime;
        tutorialTick += deltaTime;

        if(spaceshipTick > spaceship.getTick()) {
            spaceshipTick = 0.0f;
            spaceship.move();

            if(spaceship.checkOutBounds()) {
                gameOver = true;
                return;
            }
        }

        if(newAsteroidTick > NEW_ASTEROID_TIME) {
            newAsteroidTick = 0.0f;
            newAsteroid();
        }

        if(newStarTick > NEW_STAR_TIME) {
            newStarTick = 0.0f;
            newStar();
        }

        if(star.getY() != 530 && starTick > star.getTick()) {
            star.move();
            if(spaceship.checkCollect(star.getX(), star.getY())) {
                starCollect = true;
                score ++;
            }
        }

        if(starCollect)
            star = new Star();

        clearAsteroid();
        if(updateAsteroidTick > UPDATE_ASTEROID_TIME) {
            updateAsteroidTick = 0.0f;
            updateAsteroid();
        }

        synchronized(this) {
            int len = asteroids.size();
            for(int i = 0; i < len; i++) {
                Asteroid asteroid = asteroids.get(i);
                asteroid.setTickTime(asteroid.getTickTime() + deltaTime);
                if(asteroid.getTickTime() > asteroid.getTick()) {
                    asteroid.setTickTime(0.0f);
                    asteroid.move();

                    if(asteroid.getY() < -50)
                        asteroid.setState(AsteroidState.Clear);

                    if(spaceship.checkCrash(asteroid.getX(), asteroid.getY())) {
                        gameOver = true;
                        return;
                    }

                    for(int j = 0; j < len; j++) {
                        if(j > i)
                            if(asteroid.checkCrash(asteroids.get(j).getY(), asteroids.get(j).getX()))
                                asteroid.setState(AsteroidState.Explode);
                    }
                }
            }
        }

        if(updateScoreTick > UPDATE_SCORE) {
            updateScoreTick = 0.0f;
            score++;
        }

        if(tutorialTick > UPDATE_TUTORIAL) {
            tutorialTick = 0.0f;
            if(tutorialStage < 5) tutorialStage++;
        }

        if(spaceship.isHandicap()) {
            handicapTick += deltaTime;
            if(handicapTick > HANDICAP) {
                handicapTick = 0.0f;
                spaceship.setHandicap(false);
            }
        }
    }

    public List<Asteroid> getAsteroids() {
            return asteroids;
    }

    public Spaceship getSpaceship() {
        return spaceship;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public Star getStar() {
        return star;
    }

    public boolean isStarCollect() {
        return starCollect;
    }

    public void setStarCollect(boolean starCollect) {
        this.starCollect = starCollect;
    }

    public int getScore() {
        return score;
    }

    public int getTutorialStage() {
        return tutorialStage;
    }

}
