package com.dt181g.project.models.locations;

import java.util.ArrayDeque;

/**
 * Record used to store an array of cities.
 *
 * @author Josef Alirani
 * @param cities the randomly created cities.
 */
public record overWorld(ArrayDeque<abstractLocation> cities) {
}
