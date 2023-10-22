package com.dt181g.project.support.factories;

import com.dt181g.project.models.people.streetPunk;

import java.util.ArrayDeque;

/**
 * Factory class used to create streetPunks.
 *
 * @author josef Alirani
 */
public class punkFactory {

    /**
     * Empty constructor for the class.
     */
    public punkFactory() {}

    /**
     * Function used to create a streetPunk, with randomly chosen stats, the upper
     * limit is decided by the dayCount of the game.
     *
     * @param count amount of punks to generate.
     * @param scaling scaling-value used when assigning stats.
     * @return an Array of created punks.
     */
    public ArrayDeque<streetPunk> createStreetPunks(int count, int scaling) {
        ArrayDeque<streetPunk> annoyances = new ArrayDeque<>();
        int attack, defense, speed, health;
        for(int i=0;i<count;i++) {
            attack = (int)(scaling * Math.random()+ 3);
            defense = (int)(scaling * Math.random()+ 3);
            speed = (int)(scaling * Math.random()+ 3);
            health = (int)(scaling * Math.random()+ 3);
            annoyances.add(new streetPunk(i+1,attack, defense, speed, health));
        }
        return annoyances;
    }
}
