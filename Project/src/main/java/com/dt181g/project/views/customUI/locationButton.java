package com.dt181g.project.views.customUI;

import javax.swing.*;

/**
 * Class extending JButton, to represent a location on the overWorld.
 *
 * @author Josef Alirani
 */
public class locationButton extends JButton {
    String locationEvent;

    /**
     * Constructor for the class, sets the location's name
     * and event to trigger when the button is pressed.
     *
     * @param locationName the name of the location.
     * @param locationEvent the event tied to the location.
     */
    public locationButton(String locationName,String locationEvent) {
        this.setBounds(0, 0, 70, 30);

        this.locationEvent = locationEvent;
        this.setText(locationName);

        this.setHorizontalTextPosition(SwingConstants.CENTER);


        this.setVisible(true);

    }

    /**
     * Getter function used to get the name of the location's event.
     *
     * @return the event of this location.
     */
    public String getLocationEvent() {
        return locationEvent;
    }
}
