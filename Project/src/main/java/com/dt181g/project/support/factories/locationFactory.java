package com.dt181g.project.support.factories;

import com.dt181g.project.models.locations.abstractLocation;
import com.dt181g.project.models.locations.area;
import com.dt181g.project.models.locations.city;
import com.dt181g.project.models.locations.overWorld;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

/**
 * Factory used to produce a overWorld containing cities, that contain areas.
 *
 * @author josef Alirani
 */
public class locationFactory {

    /**
     * Empty Constructor of the class.
     */
    public locationFactory() {
    }

    /**
     * Public function used to create the game's overWorld that contains cities.
     *
     * @return the game's overWorld.
     */
    public overWorld createOverWorld() {
        ArrayDeque<abstractLocation> cities = new ArrayDeque<>();
        for(int i= 0;i<9;i++) {
            cities.add(createCity(i+1));
        }
        return new overWorld(cities);
    }

    /**
     * Private function used to create a city that contains areas.
     *
     * @param cityNumber ID value representing the city's 'event'-name.
     * @return a city that contains areas.
     */
    private city createCity(int cityNumber) {
        ArrayDeque<abstractLocation> areas = new ArrayDeque<>();

        // garanterar minst 1 tom area
        areas.add(new area("shady street","empty")); // TODO ska byta ut till suspect event
        for(int i = 0;i<5;i++) {
            areas.add(createArea());
        }
        return new city(areas,createCityName(),("city "+cityNumber));
    }

    /**
     * Function used to create a randomized name for a city.
     *
     * @return a randomized name for a city.
     */
    private String createCityName() {
        List<String> prefix = Arrays.asList("Good", "Rotten", "Sleeping", "Raining", "Justice", "Smog", "Spooky", "FastFood");
        List<String> suffix = Arrays.asList("ham", "ton", "borg", "sund", "son", "ville", " york", "hunt", "", "burg");

        int r1 = (int)(Math.random()* prefix.size());
        int r2 = (int)(Math.random() * suffix.size());
        return (prefix.get(r1) + suffix.get(r2));
    }

    /**
     * Private function used to create an area, with a randomly assigned event.
     *
     * @return an area of a city, with a randomly assigned event.
     */
    private area createArea() {
        List<String> events = Arrays.asList("powerUpSpeed", "powerUpAttack", "powerUpDefense", "powerUpHealth");
        List<String> eventName = Arrays.asList("coffee shop", "Gym", "clothing shop", "sallad stand");
        int i = (int)(Math.random() * events.size());
        return new area(eventName.get(i),events.get(i));
    }
}
