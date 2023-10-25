package com.dt181g.project.models.people;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Abstract class used by concrete people-classes.
 *
 * @author josef Alirani
 */
public abstract class person {
    protected int attack;
    protected int defense;
    protected int speed;
    protected int health;
    private final String name;

    /**
     * Constructor of a person, sets the person's name and stats.
     *
     * @param name the name of the person.
     * @param attack the attack-stat of the person.
     * @param defense the defense-stat of the person.
     * @param speed the speed-stat of the person.
     * @param health the health-stat of the person.
     */
    person(String name, int attack, int defense, int speed, int health) {
        this.attack = attack;
        this.defense = defense;
        this.speed = speed;
        this.health = health;
        this.name = name;
    }

    /**
     * Function used to get all stats of a person.
     *
     * @return ArrayList of all the stats of a person.
     */
    public ArrayList<Integer> getStats() {
        return new ArrayList<>(Arrays.asList(this.attack, this.defense, this.speed, this.health));
    }

    /**
     * Function used to get the health-stat of the person.
     *
     * @return the health-stat of the person.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * Function used to get the speed-stat of the person.
     *
     * @return the speed-stat of the person.
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Function used to get the Damage-value of the person.
     * Is calculated by attackstat + (speed/3)
     *
     * @return the attack-stat of the person.
     */
    public int getDamageValue() {
        return (this.attack + (this.speed/3));
    }

    /**
     * Function used to get the health-stat of the person.
     *
     * @return the health-stat of the person.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Functino used to reduce health-value of the person.
     *
     * @param value value to reduce health-value of the person.
     * @return amount of damage taken.
     */
    public int reduceHealth(int value) {
        int damageValue = ((defense/2) + (speed/4));
        if(value>damageValue) {
            damageValue = value - damageValue;
        }
        else {
            damageValue = 1;
        }

        if(damageValue > this.health) {
            this.health = 0;
        }
        else {
            this.health = health - damageValue;
        }
        return damageValue;
    }
}
