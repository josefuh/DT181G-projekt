package models.factories;

import models.Coordinate;
import models.teams.team;
import models.teams.teamMember;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Random;

public class teamFactory {

    public teamFactory() {}

    public ArrayDeque<team> createTeams(int amount, ArrayDeque<Coordinate> dropSpots) {
        ArrayDeque<team> teams = new ArrayDeque<>();

        Color[] colors = {Color.PINK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.WHITE,
                Color.YELLOW, Color.GRAY}; // TODO lägg till fler färger

        for(int i=0;i<amount;i++) {
            Coordinate temp = dropSpots.pop();
            temp = new Coordinate(temp.getX(), temp.getY(), Integer.toString(i));
            teams.add(new team(i+1, createMembers(temp, colors[i]), temp));
        }

        return teams;
    }

    private ArrayDeque<teamMember> createMembers(Coordinate dropSpot, Color teamColor) {
        ArrayDeque<teamMember> members = new ArrayDeque<>();

        for(int i=0;i<3;i++) {
            Coordinate drop = new Coordinate(dropSpot.getX()+i, dropSpot.getY(), dropSpot.getT());
            teamMember member = new teamMember(createGenes(), drop);
            member.getPosition().setColor(teamColor);
            members.add(member);
        }

        ArrayDeque<teamMember> temp = new ArrayDeque<>();
        for(teamMember m: members) {
            temp.addAll(members);
            temp.remove(m);

            m.addTeamMate(temp);

            temp.removeAll(members);
        }

        return members;
    }

    private String createGenes() {
        String value = "";

        Random rand = new Random();
        value += Integer.toBinaryString(rand.nextInt(20));  // 0. class
        value += ":";
        value += Integer.toBinaryString(rand.nextInt(2));   // 1. play-style, 0=zone, 1=edge
        value += ":";
        value += Integer.toBinaryString(rand.nextInt(9));   // 2. zone-prio
        value += ":";
        value += Integer.toBinaryString(rand.nextInt(9));   // 3. fight-willingness
        value += ":";
        value += Integer.toBinaryString(rand.nextInt(9));   // 4. staying near teammates
        value += ":";
        value += Integer.toBinaryString(rand.nextInt(9));   // 5. ability-use
        value += ":";
        value += Integer.toBinaryString(rand.nextInt(9));   // 6. avoid abilities
        value += ":";
        value += Integer.toBinaryString(rand.nextInt(9));   // 7. distance to shoot
        value += ":";
        value += Integer.toBinaryString(rand.nextInt(2));   // 8. movement while shooting, 0=false, 1=true


        return value;
    }
}
