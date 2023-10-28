package com.dt181g.project.controllers.specificControllers;

import com.dt181g.project.support.IOHelper;
import com.dt181g.project.models.gameInfo;
import com.dt181g.project.views.GUI;
import com.dt181g.project.views.customUI.overlayGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Controller class responsible for managing the overlay of the game.
 *
 * @author josef Alirani
 */
public class overlayController implements controllerInterface, ActionListener {

    private final ActionListener listener;
    private final overlayGUI overlayGui;
    private volatile GUI gui;
    private final gameInfo info;

    /**
     * Constructor for the Class, sets the class's ActionListener and
     * gameInfo-variable.
     *
     * @param listener the ActionListener for this class.
     * @param info the gameInfo to be used by the class.
     */
    public overlayController(ActionListener listener, gameInfo info) {
        this.listener = listener;

        this.overlayGui = new overlayGUI();
        try {
            this.overlayGui.setStatsPicture(new ImageIcon(IOHelper.instance.readImageFile("player.jpg")));
        } catch(IOException err) {
            err.printStackTrace();
        }

        this.info = info;
    }

    /**
     * Function used to print the overlay on the GUI.
     *
     * @param gui the GUI-object to which the window is created with, and is to be updated with.
     */
    @Override
    public void doWork(GUI gui) {
        // lägger till overlay till GUI
        this.gui = gui;
        SwingUtilities.invokeLater(() -> {
            updateModels();
            updateView(this.gui.addGUIComponent(this.overlayGui));
        });
    }

    /**
     * Function used to update the UI of the game.
     *
     * @param gui the GUI-object to which the window is created with, and is to be updated with.
     */
    @Override
    public void updateView(GUI gui) {
        this.gui = gui;
        this.gui.repaint();
    }

    /** Updates the player's stats on the overlay */
    @Override
    public void updateModels() {
        this.overlayGui.setPlayerStats(this.info.getPlayer().getStats());
        this.gui.repaint();
    }

    /**
     * Handles events triggered when the content of the overlay needs
     * to be updated.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // ritar ut event-text i overlay:en
        if(e.getActionCommand().contains("event.")) {
            // läser in det event som ska skrivas ut från JSON filen
            String event = e.getActionCommand().substring(e.getActionCommand().indexOf(".")+1);
            JLabel eventText = new JLabel(IOHelper.instance.readFromJSON("events",event));
            eventText.setForeground(Color.WHITE);
            this.overlayGui.addOverlayContent(eventText);

            SwingUtilities.invokeLater(() -> updateView(this.gui.addGUIComponent(this.overlayGui)));
        }
        // läser in det prompt som angivits från JSON filer
        else if(e.getActionCommand().contains("prompt.")) {
            String prompt;
            JLabel promptText = new JLabel();
            promptText.setForeground(Color.WHITE);

            // hämtar prompt text från JSON filen, skrivet i formatet prompt.promptTyp:stad
            if(!e.getActionCommand().contains(":")) {
                prompt = e.getActionCommand().substring(e.getActionCommand().indexOf(".") + 1);
                promptText.setText(IOHelper.instance.readFromJSON("prompts",prompt));
            } else {
                prompt = e.getActionCommand().substring(e.getActionCommand().indexOf(".") + 1, e.getActionCommand().indexOf(":"));
                promptText.setText("["+ e.getActionCommand().substring(e.getActionCommand()
                        .indexOf(":")+1)+"] - "+IOHelper.instance.readFromJSON("prompts",prompt));
            }

            SwingUtilities.invokeLater(() ->this.overlayGui.addOverlayContent(promptText));
            // uppdaterar mördarens mål, avslutar spelet om alla städer blivit attackerade
            if(this.info.getDayCount()%5==0 && !this.info.getSuspect().getRoute().isEmpty()) {
                this.info.getSuspect().setNext();
            }
            // ser till att gui är initierad innan den ska uppdateras
            Timer timer = new Timer(150, (e2)-> {
                if(gui!=null) {
                    if(!(this.info.getSuspect().getRoute().isEmpty())) {
                        SwingUtilities.invokeLater(() -> updateView(this.gui.addGUIComponent(this.overlayGui)));
                    }
                    ((Timer) e2.getSource()).stop();
                }
            });
            timer.start();
        }
        // uppdaterar datumet
        else if(e.getActionCommand().contains("Day:")) {
            // ser till att gui är initierad innan den ska uppdateras
            Timer timer = new Timer(150, (e2)-> {
                if(gui!=null) {
                    this.overlayGui.setDateDisplay(e.getActionCommand());
                    SwingUtilities.invokeLater(() -> updateView(this.gui.addGUIComponent(this.overlayGui)));

                    ((Timer) e2.getSource()).stop();
                }
            });
            timer.start();
        }
        // tömmer innehållet i overlay:en
        else if(e.getActionCommand().equals("clearOverlay")) {
            this.overlayGui.clearOverlayContent();
            SwingUtilities.invokeLater(() -> updateView(this.gui.addGUIComponent(this.overlayGui)));
        }
        // uppdaterar spelarens stats
        else if(e.getActionCommand().equals("updateStats")) {
            SwingUtilities.invokeLater(this::updateModels);
        }
        // vunnit/förlorat
        else {
            // ritar ut game over skärmen, och om man vunnit eller ej
            if (e.getActionCommand().equals("lost")) {
                SwingUtilities.invokeLater(() -> {
                    this.gui.remove(this.overlayGui);
                    this.overlayGui.clearOverlayContent();
                    updateView(this.gui.printGameOver(false));
                });
            }
            else {
                SwingUtilities.invokeLater(() -> updateView(this.gui.printGameOver(true)));
            }

            // avslutar spelet efter 3 sekunder
            Timer timer = new Timer(3000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "stopGame"));
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }

}
