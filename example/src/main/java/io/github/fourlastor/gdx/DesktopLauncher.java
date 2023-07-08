package io.github.fourlastor.gdx;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.awt.Dimension;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
    private static final float PERCENT_OF_SCREEN_SIZE = 0.7f;
    private static final float ASPECT_RATIO = 16f / 9f;

    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Game");
        setWindowedMode(config);
        new Lwjgl3Application(new ExampleGame(), config);
    }

    private static void setWindowedMode(Lwjgl3ApplicationConfiguration config) {
        Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int height = (int) (dimension.height * PERCENT_OF_SCREEN_SIZE);

        int width = (int) (height * ASPECT_RATIO);

        System.out.println("Window dimensions => width: " + width + ", height: " + height);
        config.setWindowedMode(width, height);
    }
}
