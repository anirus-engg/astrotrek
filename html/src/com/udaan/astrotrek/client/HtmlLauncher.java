package com.udaan.astrotrek.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.udaan.astrotrek.AstroTrekGame;
import com.udaan.astrotrek.IActivityRequestHandler;

public class HtmlLauncher extends GwtApplication implements IActivityRequestHandler{

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(400, 600);
        }

        @Override
        public ApplicationListener getApplicationListener () {
                return new AstroTrekGame(this);
        }

    @Override
    public void showAds(boolean show) {

    }

    @Override
    public void showShop() {

    }
}