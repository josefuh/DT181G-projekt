package controllers;

import models.Coordinate;
import models.factories.teamFactory;
import models.gameMap;
import models.teams.team;
import models.teams.teamMember;
import views.GUI;

import javax.swing.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class mainController {

    public mainController() {
        GUI gui = new GUI();

        doWork(gui);
    }

    private void doWork(GUI gui) {
        gameMap map = new gameMap();
        teamFactory factory = new teamFactory();
        AtomicReference<ArrayDeque<team>> teams = new AtomicReference<>(factory.createTeams(6, map.getDropSpots()));

        Timer movements = new Timer(150, e -> {
            ArrayDeque<team> updatedTeams = new ArrayDeque<>();
            for(team t: teams.get().clone()) {
                ArrayDeque<team> tempTeams = teams.get().clone();
                tempTeams.remove(t);

                for(teamMember m: t.members()) {
                    m.doTurn(map.getEnding(), tempTeams);
                }

                updatedTeams.add(t);
            }
            teams.set(updatedTeams);
        });

        movements.setRepeats(false);

        Timer gameMap = new Timer(3000, e -> {
            ArrayList<Coordinate> wholeMap = new ArrayList<>();
            teams.get().clone().forEach(team -> wholeMap.addAll(team.getMembersPosition()));

            wholeMap.add(map.getEnding());
            SwingUtilities.invokeLater(() -> gui.printGame(updateGameMap(wholeMap)));
            movements.start();
        });

        gameMap.start();

    }


    private ArrayDeque<Coordinate> updateGameMap(ArrayList<Coordinate> contents) {
        ArrayDeque<Coordinate> map = new ArrayDeque<>();

        for(int y=0;y<60;y++) {
            for(int x=0;x<60;x++) {
                if((x == 0 || y ==0) || (x == 59 || y ==59)) {
                    map.add(new Coordinate(x, y, "#"));
                }
                else if(!(contents.contains(new Coordinate(x, y, "O")))) {
                    map.add(new Coordinate(x, y, "-"));
                }
                else if(contents.contains(new Coordinate(x, y, "*"))) {
                    map.add(contents.get(contents.indexOf(new Coordinate(x, y, "*"))));
                }
                else {

                    map.add(contents.get(contents.indexOf(new Coordinate(x, y, "O"))));
                }
            }
        }

        return map;
    }
}
