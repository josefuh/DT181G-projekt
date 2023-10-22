package com.dt181g.project.models.people;

/**
 * Model class of a punk/hooligan person.
 *
 * @author josef Alirani
 */
public class streetPunk extends person {

    /**
     * Constructor of a punk, sets the punk's name and stats.
     *
     * @param id the ID-number of the streetPunk.
     * @param attack the attack-stat of the punk.
     * @param defense the defense-stat of the punk.
     * @param speed the speed-stat of the punk.
     * @param health the health-stat of the punk.
     */
    public streetPunk(int id, int attack, int defense, int speed, int health) {
        super(("Punk "+id), attack, defense, speed, health);
    }
}
