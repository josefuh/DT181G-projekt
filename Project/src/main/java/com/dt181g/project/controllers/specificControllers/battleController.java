package com.dt181g.project.controllers.specificControllers;

import com.dt181g.project.support.factories.punkFactory;
import com.dt181g.project.models.gameInfo;
import com.dt181g.project.models.people.detective;
import com.dt181g.project.models.people.person;
import com.dt181g.project.views.GUI;
import com.dt181g.project.views.customUI.fightGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Controller class used to handle battle events in the game.
 *
 * @author josef Alirani
 */
public class battleController implements abstractController {

    private final ArrayDeque<person> punks;
    private final fightGUI fightPanel;
    private final detective player;
    private final ActionListener listener;
    private volatile String logValue;
    private volatile String playerHealth;
    private final String enemyType;

    /**
     * Constructor for class, initializes class variables.
     *
     * @param listener the ActionListener to be used by this controller.
     * @param info the gameInfo-variable used to get info about the game.
     * @param enemyType The type of enemy the player is to face.
     */
    public battleController(ActionListener listener, gameInfo info, String enemyType) {
        this.listener = listener;

        this.player = info.getPlayer();

        this.punks = new ArrayDeque<>();
        if(enemyType.equals("punks")) {
            punkFactory factory = new punkFactory();
            this.punks.addAll(factory.createStreetPunks((int) (Math.random() * 3 + 1), info.getDayCount()));
        } else {
            this.punks.add(info.getSuspect());
        }
        this.logValue = "";
        this.playerHealth = "";

        this.enemyType = enemyType;

        this.fightPanel = new fightGUI();
    }

    /**
     * Creates a fixed threadPool of size 2, that are to handle the updating of UI and data.
     *
     * @param gui the GUI-object to which the window is created with, and is to be updated with.
     */
    @Override
    public void doWork(GUI gui) {
        // uppdaterar GUI
        SwingUtilities.invokeLater(() -> {
            gui.clearScreen();

            gui.addGUIComponent(this.fightPanel);
        });

        ExecutorService service = Executors.newFixedThreadPool(2);

        service.execute(this::updateModels);
        service.execute(() -> this.updateView(gui));

        // avslutar trådarna
        service.shutdown();
        try {
            if(!service.awaitTermination(60, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
        } catch (InterruptedException err) {
            service.shutdownNow();
        }
    }

    /**
     * Updates the GUI every 2 seconds as long as both sides are alive.
     *
     * @param gui the GUI-object to which the window is created with, and is to be updated with.
     */
    @Override
    public synchronized void updateView(GUI gui) {

        int finalPlayerHealth = this.player.getHealth();
        int finalSize = punks.size();
        SwingUtilities.invokeLater(() -> {
            gui.addGUIComponent(fightPanel);
            fightPanel.setPlayerHealth("[Player: "+finalPlayerHealth+" HP]");
            fightPanel.initLog(finalSize, enemyType);
        });

        Timer timer = new Timer(2000, e -> {
            if(!punks.isEmpty() && player.getHealth()>0) {
                if (!logValue.equals("")) {

                    StringBuilder builder = new StringBuilder("Enemies: ");
                    for (person p : punks) {
                        builder.append(" [").append(p.getName()).append(": ").append(p.getHealth()).append(" HP]");
                    }

                    SwingUtilities.invokeLater(() -> {
                        fightPanel.setFightLog(logValue);
                        fightPanel.setPlayerHealth(playerHealth);
                        fightPanel.setEnemyHealth(builder.toString());

                        logValue = "";
                    });
                }
            }else {
                ((Timer) e.getSource()).stop();
            }
        });

        timer.start();
    }

    /**
     * Updates the battle-data every 2 seconds, as long as both sides are alive.
     */
    @Override
    public synchronized void updateModels() {

        ArrayDeque<person> turnOrder = new ArrayDeque<>();
        if(this.punks.size()!=1) {
            turnOrder.addAll(getTurnOrder());
        } else {
            turnOrder.addAll(this.punks);
        }
        ArrayDeque<person> turnDone = new ArrayDeque<>();

        final int[] damage = new int[1];

        Timer timer = new Timer(2000, e -> {
            if(!punks.isEmpty() && player.getHealth()>0) {
                if (logValue.equals("")) {
                    // fiendens tur
                    if (!turnOrder.isEmpty() && turnDone.isEmpty()) {
                        turnDone.add(turnOrder.pop());

                        damage[0] = player.reduceHealth(turnDone.getLast().getDamageValue());

                        // uppdaterar fightGUI
                        int finalDamage1 = damage[0];
                        String finalName = turnDone.getLast().getName();
                        int finalPlayerHealth = player.getHealth();

                        SwingUtilities.invokeLater(() -> {
                            playerHealth = ("[Player: " + finalPlayerHealth+ " HP]");
                            logValue = (finalName + " Strike you for " + finalDamage1 + " damage!");
                        });
                    }
                    // spelarens tur
                    else if (!turnDone.isEmpty()) {
                        // kollar om fienden lever, tar bort dem ifrån loopen i så fall
                        turnOrder.add(turnDone.pop());

                        if (turnOrder.getLast().getHealth() <= 0) {
                            punks.remove(turnOrder.getLast());
                            turnOrder.remove(turnOrder.getLast());
                        }
                        // spelarens attack
                        else {
                            damage[0] = turnOrder.getLast().reduceHealth(player.getDamageValue());

                            // uppdaterar fightGUI
                            int finalDamage = damage[0];
                            String finalName = turnOrder.getLast().getName();
                            SwingUtilities.invokeLater(() -> logValue = ("You Strike " + finalName + " for " + finalDamage + " damage!"));

                            // kollar om fienden fortfarande lever, tar bort dem ifrån loopen i så fall
                            if (turnOrder.getLast().getHealth() <= 0) {
                                punks.remove(turnOrder.getLast());
                                turnOrder.remove(turnOrder.getLast());
                            }
                        }

                    }
                }
            }
            else {
                ((Timer) e.getSource()).stop();


                this.listener.actionPerformed(new ActionEvent(this,
                        ActionEvent.ACTION_PERFORMED, ("fightDone." + this.player.getHealth())));
            }
        });
        timer.start();
    }

    /**
     * Creates a sorted ArrayDeque of punks, based on their
     * speed-stat.
     *
     * @return ArrayDeque of punks, sorted by speed.
     */
    private ArrayDeque<person> getTurnOrder() {
        ArrayDeque<person> personArrayDeque;

        // sorterar streetPunks efter deras snabbhet till en array
        personArrayDeque =punks.stream().sorted(Comparator.comparingInt(person::getSpeed))
                .collect(Collectors.toCollection(ArrayDeque::new));


        return personArrayDeque;
    }
}
