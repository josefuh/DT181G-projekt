package com.dt181g.project.models.people;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Model class used to store data about the greasy, smelly main suspect.
 *
 * @author josef Alirani
 */
public class antagonist extends person {
    private final ArrayList<Integer> route;
    private final ArrayList<Integer> visitedCities;
    private int next;

    /**
     * Constructor for the class, initializes the antagonist's stats
     * and route.
     */
    public antagonist() {
        super("The suspect",15, 15, 15, 20);

        visitedCities = new ArrayList<>();
        route = setRoute();

        next = route.get(route.size()-1);
        //setNext();
    }

    /**
     * Function used to adjust the antagonist's stats depending on the
     * day that they are encountered.
     *
     * @param scaling the value used to adjust the stats of the antagonist.
     */
    public void setScaledStats(int scaling) {
        ArrayList<Integer> stats = getStats();

        // scaling = dag 5: (15 + (5 * 0to1)*0.8) = 15 + 0to4
        super.attack =  (int)(stats.get(0)  + (scaling * (Math.random()))*0.8 );
        super.defense = (int)(stats.get(1)  + (scaling * (Math.random()))*0.8 );
        super.speed =   (int)(stats.get(2)  + (scaling * (Math.random()))*0.8 );
        super.health =  (int)(stats.get(3)  + (scaling * (Math.random()))*0.8 );
    }

    /**
     * Setter function used to set the route the killer is going to use
     * in the game.
     *
     * @return ArrayList of integers specifying the route.
     */
    private ArrayList<Integer> setRoute() {
        ArrayList<ArrayList<Integer>> routes = new ArrayList<>();
        routes.add(new ArrayList<>(Arrays.asList(1, 2, 3, 4 ,5 ,6, 7, 8, 9))); // 9-1

        routes.add(new ArrayList<>(Arrays.asList(9, 8, 7, 6 ,5 ,4, 3, 2, 1))); // 1-9

        routes.add(new ArrayList<>(Arrays.asList(5, 4, 6, 3 ,7 ,2, 8, 1, 9))); // alternerar mellan att jobba uppåt/neråt

        routes.add(new ArrayList<>(Arrays.asList(5, 6, 3, 2 ,1 ,4, 7, 8, 9))); // medsols riktning

        routes.add(new ArrayList<>(Arrays.asList(5, 2, 3, 6 ,9 ,8, 7, 4, 1))); // motsols riktning


        int a = (int)(Math.random() * (routes.size()-1));
        return routes.get(a);
    }

    /**
     * Function used to move the suspect to another city, adds
     * previous city to 'visitedCities'-variable.
     */
    public void setNext() {
        visitedCities.add(next);
        route.remove(route.size() - 1);
        if(!(route.size() == 0)) {
            next = route.get(route.size() - 1);
        }
        else {
            next = 0;
        }
    }

    /**
     * Function used to get the suspect's route.
     *
     * @return ArrayList of integers specifying the route.
     */
    public ArrayList<Integer> getRoute() {
        return route;
    }

    /**
     * Function used to get the cities that the suspect has
     * been to.
     *
     * @return ArrayList of integers specifying the cities the antagonist has been to.
     */
    public ArrayList<Integer> getVisitedCities() {
        return this.visitedCities;
    }

    /**
     * Function used to get the location of the antagonist.
     *
     * @return int specifying which city the suspect is at.
     */
    public int getNext() {
        return next;
    }
}
