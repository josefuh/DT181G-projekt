package models.teams;

import models.Coordinate;
import support.logisticSupport;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.stream.Collectors;

public class teamMember {
    private final String genetics;
    private int health;
    private Coordinate position;
    private final ArrayDeque<teamMember> teamMates;
    private boolean isFighting;

    public teamMember(String genetics, Coordinate position) {
        this.genetics = genetics;
        this.health = 100;
        this.position = position;
        this.isFighting = false;
        this.teamMates = new ArrayDeque<>();
    }

    public Coordinate getPosition() {
        return position;
    }
    private void moveMember(Coordinate direction) {
        int x, y;
        if(position.x()<direction.x()) {
            x = position.x() +1;
        } else {
            x = position.x() - 1;
        }

        if(position.y()<direction.y()) {
            y = position.y() +1;
        } else {
            y = position.y() - 1;
        }

        position = new Coordinate(x, y, "O");
    }

    public void doTurn(Coordinate ending, ArrayDeque<team> teams) {
        byte[] cell = new byte[9];
        byte iteratorCount = 0;
        String[] temp = genetics.split(":");

        for(String t: temp) {
            cell[iteratorCount] = Byte.parseByte(t, 2);
            iteratorCount++;
        }

        iteratorCount = 0;
        for(byte bit: cell) {   // beslutar action
            switch(iteratorCount) {
                case(2):      // zon-prio, högre än fem flyttar laget mot slutpunkten
                    if(!isFighting) {
                        if (bit > 5) {
                            moveMember(ending);
                            break;
                        } else {
                            moveMember(new Coordinate((int) (Math.random() * 60),
                                    (int) (Math.random() * 60), "O"));
                        }
                    }
                    break;
                case(3):    // 3. fight-willingness
                    if(!isFighting || cell[8] == 1) {
                        if (bit > 5) {
                            moveMember(getClosestEnemy(teams));
                            break;
                        } else {
                            moveMember(new Coordinate((int) (Math.random() * 60),
                                    (int) (Math.random() * 60), "O"));
                        }
                    }
                    break;
                case(4):    // 4. staying near teammates
                    if(bit>5) {
                        moveMember(teamMates.getFirst().getPosition());
                        break;
                    } else {
                        moveMember(new Coordinate((int)(Math.random()*60),
                                (int)(Math.random()*60), "O"));
                    }
                    break;
                case(5):    // 5. ability-use
                    break;
                case(6):    // 6. avoid abilities
                    break;
                case(7):    // 7. distance to shoot, 8. movement while shooting
                    Coordinate closest = getClosestEnemy(teams);
                    if((position.x() <= closest.x()+cell[7] || position.x() >= closest.x()-cell[7]) ||
                            (position.y() <= closest.y()+cell[7] || position.y() >= closest.y()-cell[7])) {
                        isFighting = true;
                    }
                    break;
            }
            iteratorCount++;
        }
    }
    private Coordinate getClosestEnemy(ArrayDeque<team> teams) {
        ArrayDeque<teamMember> enemies = new ArrayDeque<>();
        for (team t : teams) {
            enemies.addAll(t.members());
        }

        int[] xPositions = enemies.stream().sorted(Comparator.comparingInt(o-> o.position.x()))
                .mapToInt(value -> value.position.x()).toArray();
        int[] yPositions = enemies.stream().sorted(Comparator.comparingInt(o-> o.position.y()))
                .mapToInt(value -> value.position.y()).toArray();

        int x = logisticSupport.findClosest(xPositions, position.x());
        int y = logisticSupport.findClosest(yPositions, position.y());

        return new Coordinate(x, y, "O");
    }
    public int getHealth() {
        return this.health;
    }

    public void addTeamMate(ArrayDeque<teamMember> mates) {
        this.teamMates.addAll(mates);
    }

    public ArrayDeque<teamMember> getTeamMates() {
        return teamMates;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public String getGenetics() {
        return this.genetics;
    }

    public void reduceHealth(int value) {
        if(this.health > value) {
            this.health -= value;
        }
        else {
            this.health = 0;
        }
    }
}
