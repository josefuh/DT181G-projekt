package models.teams;

import models.Coordinate;
import support.logisticSupport;

import java.util.ArrayDeque;
import java.util.Comparator;

public class teamMember {
    private final String genetics;
    private int health;
    private final Coordinate position;
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
    private synchronized void moveMember(Coordinate direction) {
        int x, y;
        if(position.getX()<direction.getX()) {
            x = position.getX() + 1;
        } else {
            x = position.getX() - 1;
        }

        if(position.getY()<direction.getY()) {
            y = position.getY() + 1;
        } else {
            y = position.getY() - 1;
        }

        if(y<2) {
            y += 2;
        } else if(y> 57) {
            y -= 2;
        }
        if(x<2) {
            x += 2;
        } else if(x> 57) {
            x -= 2;
        }

        this.position.setX(x);
        this.position.setY(y);
    }

    public void doTurn(Coordinate ending, ArrayDeque<team> teams) {
        byte[] cell = new byte[9];
        byte iteratorCount = 0;
        String[] temp = genetics.split(":");

        for(String t: temp) {
            cell[iteratorCount] = Byte.parseByte(t, 2);
            iteratorCount++;
        }

        iteratorCount = (byte)(Math.random()*3);
        byte bit = cell[iteratorCount];

        Coordinate closest = getClosestEnemy(teams);
        if((position.getX() <= closest.getX()+cell[7]+3 || position.getX() >= closest.getX()-cell[7]+3) ||
                (position.getY() <= closest.getY()+cell[7]+3 || position.getY() >= closest.getY()-cell[7]+3)) {
            isFighting = true;
        }

        switch(iteratorCount) {
            case(0):      // zon-prio, högre än fem flyttar laget mot slutpunkten
                if(!isFighting) {
                    if (bit > 2) {
                        moveMember(ending);
                    } else {
                        moveMember(new Coordinate((int) (Math.random() * 60),
                                (int) (Math.random() * 60), "O"));
                    }
                }
                break;
            case(1):    // 3. fight-willingness
                if(!isFighting || cell[8] == 1) {
                    if (bit > 4) {
                        moveMember(getClosestEnemy(teams));
                    } else if(isFighting){
                        runAway(getClosestEnemy(teams));
                    }
                }
                break;
            case(2):    // 4. staying near teammates
                if(bit>7) {
                    moveMember(teamMates.getFirst().getPosition());
                }
                else {
                    runAway(teamMates.getFirst().getPosition());
                }
                break;
            case(3):    // 5. ability-use, 6. avoid abilities
                break;
        }

        //}
    }

    /**
     * used to get the coordinates of the closes enemy team
     *
     * @param teams other teams
     * @return closest team's coordinates
     */
    private Coordinate getClosestEnemy(ArrayDeque<team> teams) {
        ArrayDeque<teamMember> enemies = new ArrayDeque<>();
        for (team t : teams) {
            enemies.addAll(t.members());
        }

        int[] xPositions = enemies.stream().sorted(Comparator.comparingInt(o-> o.position.getX()))
                .mapToInt(value -> value.position.getX()).toArray();
        int[] yPositions = enemies.stream().sorted(Comparator.comparingInt(o-> o.position.getY()))
                .mapToInt(value -> value.position.getY()).toArray();

        int x = logisticSupport.findClosest(xPositions, position.getX());
        int y = logisticSupport.findClosest(yPositions, position.getY());

        return new Coordinate(x, y, "O");
    }
    public int getHealth() {
        return this.health;
    }

    private synchronized void runAway(Coordinate enemy) {
        int x, y;
        if(enemy.getX()> this.position.getX()) {
            x = this.position.getX() - 1;
        } else {
            x = this.position.getX() + 1;
        }

        if(enemy.getY()> this.position.getY()) {
            y = this.position.getY() - 1;
        } else {
            y = this.position.getY() + 1;
        }

        if(y<2) {
            y += 2;
        } else if(y> 57) {
            y -= 2;
        }
        if(x<2) {
            x += 2;
        } else if(x> 57) {
            x -= 2;
        }
        this.position.setX(x);
        this.position.setY(y);
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
