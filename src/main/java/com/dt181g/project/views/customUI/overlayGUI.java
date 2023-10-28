package com.dt181g.project.views.customUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class extending JPanel, used as a container for the overlay's
 * components.
 *
 * @author josef Alirani
 */
public class overlayGUI extends JPanel {
    private final overlayPanel overlay;
    private final JPanel overlayContent;
    private final JLabel dateDisplay;
    private final statSheetPanel stats;

    /**
     * Constructor for class, initializes the panel and it's contents.
     */
    public overlayGUI() {
        this.setBounds(0, 400, 785, 165);
        this.setLayout(new BorderLayout());

        // initierar overlay-content
        this.overlayContent = new JPanel();
        this.overlayContent.setBounds(200, 30,550, 90);
        this.overlayContent.setBackground(Color.BLUE);
        this.overlayContent.setVisible(true);


        // initierar datum
        this.dateDisplay = new JLabel("Day: 0");
        this.dateDisplay.setBounds(700, 5, 100, 30);
        this.dateDisplay.setForeground(Color.WHITE);
        this.dateDisplay.setVisible(true);

        //initierar statSheet + bild
        this.stats = new statSheetPanel();

        // skapar overlayPanelen och lägger till komponenterna ovan
        this.overlay = new overlayPanel();
        this.overlay.setForeground(Color.WHITE);

        this.overlay.add(dateDisplay);
        this.overlay.add(overlayContent);

        this.add(overlay);
    }

    /**
     * Function used to set the image to be displayed above the stats-
     *
     * @param picture imageIcon of the picture.
     */
    public void setStatsPicture(ImageIcon picture) {
        this.stats.setPicture(picture);
    }

    /**
     * Function used to add a component to the textbox of the overlay.
     *
     * @param component component to add to the textbox of the overlay.
     */
    public void addOverlayContent(JComponent component) {
        this.setVisible(true);
        this.overlayContent.add(component);
    }

    /**
     * Function used to set the date displayed in the overlay.
     *
     * @param text date to display in the overlay.
     */
    public void setDateDisplay(String text) {
        this.dateDisplay.setText(text);
    }

    /**
     * Function used to set the player's stats to be
     * displayed in the overlay.
     *
     * @param playerStats array of stats to display.
     */
    public void setPlayerStats(ArrayList<Integer> playerStats) {
        this.stats.addStats(playerStats);

        this.overlay.add(this.stats);
        this.add(this.overlay);
    }

    /** Function used to clear the contents of the overlay's textbox. */
    public void clearOverlayContent() {
        this.overlayContent.removeAll();

    }
}
