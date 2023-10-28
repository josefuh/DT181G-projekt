package com.dt181g.project.views.customUI;

import javax.swing.*;
import java.awt.*;

/**
 * Class extending JPanel, acts as the main component in the
 * overlay, used as a container for the date and textbox.
 *
 * @author josef Alirani
 */
public class overlayPanel extends JPanel {

    /** Constructor for the class, initializes the panel and sets its default look */
    public overlayPanel() {
        this.setBounds(200, 400, 400, 165);
        this.setBackground(Color.BLACK);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
        this.setLayout(null);

        this.setVisible(true);
    }
}
