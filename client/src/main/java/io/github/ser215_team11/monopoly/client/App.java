package io.github.ser215_team11.monopoly.client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;

/**
 * Entry point of the client application.
 */
public class App extends JFrame {

    // Window size in pixels
    private static final int SCREEN_WIDTH = 800;
    private static final int SCREEN_HEIGHT = 800;

    // Desired framerate
    private static final double FPS = 60.0;

    // Keeps track of how many times the program failed to keep up with the desired framerate.
    private int slowCnt = 0;

    private enum Mode {
        TITLE_SCREEN, GAME
    }
    private Mode mode;

    private TitleScreen titleScreen;
    private GameScreen gameScreen;

    /**
     * Entry point of the application. Swing does not work in a static context,
     * so the main method just passes execution off to an instance of the App
     * class.
     */
    public static void main(String[] args) throws Exception {
        new App();
    }

    /**
     * The effective entrypoint of the application.
     */
    public App() throws Exception {
        // Basic Swing window defaults
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setFocusable(true);
		createBufferStrategy(2);
        setLayout(null);

        mode = Mode.TITLE_SCREEN;

        Notification.init(SCREEN_WIDTH, SCREEN_HEIGHT);

        titleScreen = new TitleScreen(this, SCREEN_WIDTH, SCREEN_HEIGHT);
        gameScreen = new GameScreen(this, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Start the main execution loop
        mainLoop();
    }

    /**
     * The main execution loop.
     */
    private void mainLoop() throws IOException, InterruptedException {
        while(true) {
            long frameStart = System.currentTimeMillis();

            draw();

            limitFramerate(frameStart);
        }
    }

    /**
     * Puts the program to sleep for a period of time if it is running faster
     * than the desired FPS. If the program is running behind, it won't sleep at
     * all.
     * @param frameStart Time in milliseconds since the beginning of the frame
     */
    private void limitFramerate(long frameStart) throws InterruptedException {
        // Calculate how long to wait for a consistent 60 frames per second
        long delay = (long)(1000.0 * (1.0/FPS)) - (System.currentTimeMillis() - frameStart);
        if(delay < 0) {
            slowCnt++;
            if (slowCnt > 60) {
                // Alert the user when the program consistently under-performs
                System.out.println("Warning: Program is running slowly");
                slowCnt = 0;
            }

            // The program is running behind, so execute the draw operation ASAP
            delay = 0;
        }
        Thread.sleep(delay);
    }

    /**
     * Draws a frame into the waiting buffer, then swaps the buffers.
     */
    private void draw() throws IOException {
        // Get the buffer controller
        BufferStrategy bf = getBufferStrategy();
        // Get the drawing context
        Graphics2D g = (Graphics2D) bf.getDrawGraphics();

        // Clear the screen before drawing
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        switch(mode) {
            case TITLE_SCREEN:
                titleScreen.draw(g, this);
                if(titleScreen.getFinished()) {
                    mode = Mode.GAME;
                    gameScreen.init(titleScreen.getPlayerCnt());
                }
                break;
            case GAME:
                gameScreen.draw(g, this);
                break;
        }

        Notification.draw(g, this);

        // Update the graphics on-screen
        bf.show();
        getToolkit().sync();
    }

}
