package com.dt181g.project.controllers.specificControllers;

import com.dt181g.project.support.IOHelper;
import com.dt181g.project.views.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Controller class used to manage the menu screen.
 *
 * @author josef Alirani
 */
public class menuController implements controllerInterface {

    private final ActionListener listener;

    /**
     * Constructor for the class, sets the ActionListener to be used.
     *
     * @param listener the ActionListener to be used.
     */
    public menuController(ActionListener listener) {
        this.listener = listener;
    }

    /**
     * Function used to create the JButtons to be displayed, and set the Actions they
     * are to trigger when pressed.
     *
     * @param gui the GUI-object to which the window is created with, and is to be updated with.
     */
    @Override
    public void doWork(GUI gui) {

        JButton start = new JButton("start game");
        JButton instructions = new JButton("instructions");
        JButton exit = new JButton("exit game");

        start.addActionListener(e -> SwingUtilities.invokeLater(()-> {
            gui.clearScreen();
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "startGame"));
        }));

        instructions.addActionListener(e -> SwingUtilities.invokeLater(()-> {
            gui.clearScreen();
            showInstructions(gui);
            updateView(gui);
        }));

        exit.addActionListener(e -> SwingUtilities.invokeLater(() -> {
            exit.setEnabled(false);
            listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "closeProgram"));
        }));

        SwingUtilities.invokeLater(() -> updateView(gui.printMenu(start, instructions, exit)));

    }

    /**
     * {@inheritDoc}
     *
     * @param gui the GUI-object to which the window is created with, and is to be updated with.
     */
    @Override
    public void updateView(GUI gui) {
        gui.repaint();
    }

    /** {@inheritDoc} */
    @Override
    public void updateModels() {

    }

    /**
     * Function used to read the instructions from the "text.JSON",
     * and send them to the GUI to be printed.
     *
     * @param gui the windows GUI to print to.
     */
    private void showInstructions(GUI gui) {
        ArrayList<String> instructions = new ArrayList<>();

        for(int i=0; i<6;i++) {
            instructions.add(IOHelper.instance.readFromJSON("instructions", String.valueOf(i+1)));
        }

        JButton back = new JButton("return to menu");
        back.addActionListener(e -> {
            gui.clearScreen();

            doWork(gui);
        });

        SwingUtilities.invokeLater(() -> updateView(gui.printInstructions(instructions, back)));
    }
}
