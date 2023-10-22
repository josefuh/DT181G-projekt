package com.dt181g.project.models.locations;

/**
 * Concrete location in a city.
 *
 * @author josef Alirani
 */
public class area extends abstractLocation {

    /**
     * Constructor for the class, specifies the area's name
     * and event.
     *
     * @param areaName the name of the area.
     * @param areaEvent the event of the area.
     */
    public area(String areaName, String areaEvent) {
        super(areaName, areaEvent);
    }
}
