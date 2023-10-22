package com.dt181g.project.views.customUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class extending JPanel, used to display the stats in the overlay.
 *
 * @author josef Alirani
 */
public class statSheetPanel extends JPanel {
    private final JLabel picture;
    private final JPanel stats;

    /** Constructor for the class, initializes the panel and its components. */
    public statSheetPanel() {
        this.setBounds(5,5,190,165);
        this.setLayout(new GridLayout(2,1));


        this.picture = new JLabel();

        this.stats = new JPanel();
        this.stats.setLayout(new GridLayout(2,2));

        this.add(this.picture);
        this.add(this.stats);
    }

    /**
     * Function used to set the image to be displayed above the stats.
     *
     * @param pic the imageIcon to display.
     */
    public void setPicture(ImageIcon pic) {
        this.picture.setIcon(pic);
    }

    /**
     * Function used to update the stats displayed.
     *
     * @param playerStats new array of stats to display.
     */
    public void addStats(List<Integer> playerStats) {
        this.stats.removeAll();
        this.stats.setLayout(new GridLayout(2, 2));

        List<String> statNames = new ArrayList<>(Arrays.asList("attack", "defense", "speed", "health"));
        int iteratorCount = 0;
        for(int i: playerStats) {
            this.stats.add(new JLabel("|"+statNames.get(iteratorCount)+ ": "+i+"|"));
            iteratorCount++;
        }
        this.add(this.stats);
        this.repaint();
    }
}
