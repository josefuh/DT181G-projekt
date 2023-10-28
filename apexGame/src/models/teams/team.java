package models.teams;

import models.Coordinate;

import java.util.ArrayDeque;

public record team(int teamID, ArrayDeque<teamMember> members, Coordinate dropSpot) {

    public ArrayDeque<Coordinate> getMembersPosition() {
        ArrayDeque<Coordinate> c = new ArrayDeque<>();

        for (teamMember m : members) {
            c.add(m.getPosition());
        }
        return c;
    }

    private boolean isTeamAlive() {
        boolean alive = true;
        for (teamMember m : members) {
            if (!m.isAlive()) {
                alive = false;
            }
        }

        return alive;
    }
}
