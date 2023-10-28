package controllers;

import models.Coordinate;
import models.factories.teamFactory;
import models.gameMap;
import models.teams.team;
import models.teams.teamMember;
import views.GUI;

import javax.swing.*;
import java.util.ArrayDeque;

public class mainController {

    public mainController() {
        GUI gui = new GUI();

        doWork(gui);
    }

    private void doWork(GUI gui) {
        gameMap map = new gameMap();
        teamFactory factory = new teamFactory();
        ArrayDeque<team> teams = factory.createTeams(3, map.getDropSpots());

        Timer movements = new Timer(150, e -> {
            for(team t: teams) {
                ArrayDeque<team> tempTeams = teams.clone();
                tempTeams.remove(t);
                for(teamMember m: t.members()) {
                    m.doTurn(map.getEnding(), tempTeams);
                }
            }
        });
        movements.setRepeats(false);

        Timer gameMap = new Timer(3000, e -> {
            ArrayDeque<Coordinate> wholeMap = new ArrayDeque<>();
            teams.forEach(team -> wholeMap.addAll(team.getMembersPosition()));

            SwingUtilities.invokeLater(() -> gui.printGame(updateGameMap(wholeMap)));
            movements.start();
        });

        gameMap.start();

    }

    private void doTurn() {

    }

    private ArrayDeque<Coordinate> updateGameMap(ArrayDeque<Coordinate> contents) {
        ArrayDeque<Coordinate> map = new ArrayDeque<>();

        for(int y=0;y<60;y++) {
            for(int x=0;x<60;x++) {
                if((x == 0 || y ==0) || (x == 59 || y ==59)) {
                    map.add(new Coordinate(x, y, "#"));
                }
                else if(contents.contains(new Coordinate(x, y, "O"))) {
                    map.add(new Coordinate(x, y, "O"));
                }
                else {
                    map.add(new Coordinate(x, y, "-"));
                }
            }
        }

        return map;
    }
}
