package com.dt181g.project.controllers;

import com.dt181g.project.controllers.specificControllers.controllerInterface;
import com.dt181g.project.controllers.specificControllers.menuController;
import com.dt181g.project.controllers.specificControllers.overWorldController;
import com.dt181g.project.controllers.specificControllers.overlayController;
import com.dt181g.project.models.gameInfo;
import com.dt181g.project.views.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Class used to manage and initialize the specific controllers
 *
 * @author Josef
 */
public class mainController implements ActionListener {
    private GUI gui;

    /**
     * Constructor used to create the window to be used in the program.
     * After creating the window, it initializes the controller responsible
     * for the menu.
     */
    public mainController() {
        // skapa fönstret
        gui = new GUI();

        // tilldela hantering till övriga kontroller:s
        gameLoop(new menuController(this));
    }

    /**
     * Takes a class implementing abstractController and
     * calls it's 'doWork' method.
     *
     * @param controller the controller to run.
     */
    private void gameLoop(controllerInterface controller) {
        controller.doWork(this.gui);
    }

    /**
     * Handles the programs main events such as starting/stopping the game and
     * closing the program.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // skapar en threadPool
        ExecutorService services = Executors.newFixedThreadPool(2);

        // kollar vilket event som körts
        if(e.getActionCommand().equals("startGame")) {
            gameInfo info = new gameInfo();

            overlayController overlay = new overlayController(this, info);       // skickar info tillbaka till mainController
            overWorldController overWorld = new overWorldController(overlay, info); // ska kunna skicka info till overlay

            // kör spelet och dess overlay i respektive trådar
            services.execute(() -> gameLoop(overWorld));
            services.execute(() -> gameLoop(overlay));

        }
        if(e.getActionCommand().equals("stopGame")) {
            // avslutar trådarna
            services.shutdown();
            try {
                if(!services.awaitTermination(60, TimeUnit.SECONDS)) {
                    services.shutdownNow();
                }
            } catch (InterruptedException err) {
                services.shutdownNow();
            }

            this.gui.clearScreen();
            gameLoop(new menuController(this));
        }
        if(e.getActionCommand().equals("closeProgram")) {
            // avslutar trådarna
            services.shutdown();
            try {
                if(!services.awaitTermination(60, TimeUnit.SECONDS)) {
                    services.shutdownNow();
                }
            } catch (InterruptedException err) {
                services.shutdownNow();
            }
            SwingUtilities.invokeLater(() -> {
                this.gui.disposeAll();
                this.gui.setVisible(false);

                this.gui.dispose();
                this.gui = null;
            });
            System.exit(0);
        }
    }
}
