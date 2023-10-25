package com.dt181g.project.models.locations;

import java.util.ArrayDeque;

/**
 * Concrete location on the overWorld map.
 *
 * @author josef Alirani
 */
public class city extends abstractLocation {
    private final ArrayDeque<abstractLocation> areas;

    /**
     * Constructor for the class, specifies the name and event of the city,
     * also sets the array of areas in the city.
     *
     * @param areas the array of areas in the city.
     * @param cityName the name of the city.
     * @param cityID the event ID of the city.
     */
    public city(ArrayDeque<abstractLocation> areas, String cityName, String cityID) {
        super(cityName, cityID);
        this.areas = areas;
    }

    /**
     * Getter-function used to get the array of areas in the city.
     *
     * @return the array of areas in the city.
     */
    public ArrayDeque<abstractLocation> getAreas() {
        return this.areas;
    }
}
