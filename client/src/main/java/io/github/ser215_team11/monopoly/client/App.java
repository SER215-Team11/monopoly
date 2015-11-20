package io.github.ser215_team11.monopoly.client;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Entry point of the client application.
 */
public class App extends JFrame {

    // Window size in pixels
    private static final int SCREEN_WIDTH = 960;
    private static final int SCREEN_HEIGHT = 540;

    // Desired framerate
    private static final double FPS = 60.0;

    // Keeps track of how many times the program failed to keep up with the desired framerate.
    private int slowCnt = 0;

    private Button button;

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

        button = new Button(300, 300, "TEST");
        addMouseListener(button);

        // Start the main execution loop
        mainLoop();
    }

    /**
     * The main execution loop.
     */
    private void mainLoop() {
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
    private void limitFramerate(long frameStart) {
        try {
            // Calculate how long to wait for a consistent 60 frames per second
            long delay = (long)(1000.0 * (1.0/FPS)) - (System.currentTimeMillis() - frameStart);
            if(delay < 0) {
                slowCnt++;
                if(slowCnt > 60) {
                    // Alert the user when the program consistently under-performs
                    System.out.println("Warning: Program is running slowly");
                    slowCnt = 0;
                }

                // The program is running behind, so execute the draw operation ASAP
                delay = 0;
            }
            Thread.sleep(delay);
        } catch(InterruptedException e) {
            System.out.println(e);
        }
    }

    /**
     * Draws a frame into the waiting buffer, then swaps the buffers.
     */
    private void draw() {
        // Get the buffer controller
        BufferStrategy bf = getBufferStrategy();
        // Get the drawing context
        Graphics2D g = (Graphics2D) bf.getDrawGraphics();

        // Clear the screen before drawing
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        button.draw(g, this);

        // Update the graphics on-screen
        bf.show();
        getToolkit().sync();
    }

}
