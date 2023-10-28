package com.dt181g.project.models.locations;

/**
 * Abstract class used by concrete locations.
 *
 * @author josef Alirani
 */
public abstract class abstractLocation {

    private final String locationName;
    private final String locationEvent;

    /**
     * Constructor for the class, specifies the location's
     * name and event.
     *
     * @param locationName the name of the location.
     * @param locationEvent the event of the location.
     */
    public abstractLocation(String locationName, String locationEvent) {
        this.locationName = locationName;
        this.locationEvent = locationEvent;
    }

    /**
     * Getter-function used to retrieve the name of the location.
     *
     * @return name of the location.
     */
    public String getLocationName() {
        return this.locationName;
    }

    /**
     * Getter-function used to retrieve the event of the location.
     *
     * @return event of the location.
     */
    public String getLocationEvent() {
        return locationEvent;
    }
}
