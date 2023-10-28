package models;

import java.util.ArrayDeque;
import java.util.Random;

/**
 * Skapar 60x60 karta med POI:s
 *
 * @author josef Alirani
 */
public class gameMap {

    private final Coordinate ending;

    private final ArrayDeque<Coordinate> dropSpots;

    public gameMap() {
        Random random = new Random();
        this.ending = new Coordinate(random.nextInt(50)+4, random.nextInt(50)+4, " ");
        this.dropSpots = createPOIs();
    }

    public Coordinate getEnding() {
        return this.ending;
    }

    public ArrayDeque<Coordinate> getDropSpots() {
        return this.dropSpots;
    }
    private ArrayDeque<Coordinate> createPOIs() {

        ArrayDeque<Coordinate> POIs = new ArrayDeque<>();

        Random random = new Random();

        for(int i = 0;i<20;i++) {
            POIs.add(new Coordinate(random.nextInt(50)+4, random.nextInt(50)+4, "O"));
        }

        return POIs;
    }
}
