package com.dt181g.project.models;

import com.dt181g.project.models.people.antagonist;
import com.dt181g.project.models.people.detective;

import java.util.ArrayDeque;

/**
 * Model class used to store information about the game.
 *
 * @author josef Alirani
 */
public class gameInfo {
    private int dayCount;
    private final antagonist suspect;
    private final detective player;

    /**
     * Constructor for the class, initializes the class-variables.
     */
    public gameInfo(){
        dayCount = 0;
        this.suspect = new antagonist();
        this.player = new detective();
    }

    /**
     * Function used to increase the dayCount by 1.
     */
    public void updateDate() {
        dayCount++;
    }

    /**
     * Function used to get the dayCount.
     *
     * @return int value representing the dayCount.
     */
    public int getDayCount() {
        return dayCount;
    }

    /**
     * Function used to get 'event'-names of the cities
     * that the suspect has been to.
     *
     * @return Array of 'event'-names of the cities that the suspect has been to.
     */
    public ArrayDeque<String> getVisitedCities() {
        ArrayDeque<String> cities = new ArrayDeque<>();
        // hämtar de städer som attackerats av mördaren
        for(int i = 0;i<this.suspect.getVisitedCities().size();i++) {
            cities.add("city "+ this.suspect.getVisitedCities().get(i));
        }
        return cities;
    }

    /**
     * Function used to get the detective that the player controls.
     *
     * @return the detective that the player controls.
     */
    public detective getPlayer() {
        return this.player;
    }

    /**
     * Function used to set the health-stat of the detective.
     *
     * @param value the value to set the health-stat of the detective to.
     */
    public void setPlayerHealth(int value) {
        this.player.setHealth(value);
    }

    /**
     * Function used to get the suspect/antagonist of the game.
     *
     * @return the suspect/antagonist of the game.
     */
    public antagonist getSuspect() {
        return this.suspect;
    }
}
