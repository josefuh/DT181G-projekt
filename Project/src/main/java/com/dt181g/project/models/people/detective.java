package com.dt181g.project.models.people;

/**
 * Model class used to store data about the detective.
 *
 * @author josef Alirani
 */
public class detective extends person {
    private int currentLocation;

    /**
     * Constructor for the class, sets the detective's default stat-values.
     */
    public detective() {
        super("detective",10,10, 10,10);
    }

    /**
     * Function used to increase the value of a stat.
     *
     * @param stat String specifying which stat is to be adjusted.
     * @param value The value to add to the stat.
     */
    public void powerUp(String stat, int value) {
        switch (stat) {
            case ("attack") -> super.attack += value;
            case ("defense") -> super.defense += value;
            case ("speed") -> super.speed += value;
            case ("health") -> super.health += value;
            default -> {
            }
        }
    }

    /**
     * Function used to set the health-stat to a specified value.
     *
     * @param value the value to set the health-stat to.
     */
    public void setHealth(int value) {
        super.health = value;
    }

    /**
     * Getter function used to get the current location of the detective.
     *
     * @return int value specifying which city the detective is at.
     */
    public int getCurrentLocation() {
        return this.currentLocation;
    }

    /**
     * Setter function used to set the location of the detective.
     *
     * @param location int value specifying the location of the detective.
     */
    public void setCurrentLocation(int location) {
        this.currentLocation = location;
    }

    /**
     * Function used to reset the value specifying the location of the detective.
     */
    public void resetLocation() {
        this.currentLocation = 0;
    }
}
