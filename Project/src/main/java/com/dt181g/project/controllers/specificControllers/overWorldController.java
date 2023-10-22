package com.dt181g.project.controllers.specificControllers;

import com.dt181g.project.models.gameInfo;
import com.dt181g.project.models.locations.abstractLocation;
import com.dt181g.project.models.locations.city;
import com.dt181g.project.models.locations.overWorld;
import com.dt181g.project.support.IOHelper;
import com.dt181g.project.support.factories.locationFactory;
import com.dt181g.project.views.GUI;
import com.dt181g.project.views.customUI.locationButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayDeque;

/**
 * Controller class used to manage the overWorld of the game.
 *
 * @author josef Alirani
 */
public class overWorldController implements abstractController, ActionListener {

    private final overWorld map;
    private final ActionListener listener;
    private volatile GUI gui;
    private final gameInfo info;

    /**
     * Constructor for the class, creates the overWorld of the
     * game using locationFactory and sets the ActionListener
     * of the class.
     *
     * @param listener the ActionListener to use.
     * @param info the gameInfo to be used.
     */
    public overWorldController(ActionListener listener, gameInfo info) {
        this.listener = listener;
        // skapar spelets platser
        locationFactory factory = new locationFactory();
        this.map = factory.createOverWorld();

        this.info = info;
    }

    /**
     * Prints the game's cities.
     *
     * @param gui the GUI-object to which the window is created with, and is to be updated with.
     */
    @Override
    public void doWork(GUI gui) {
        this.gui = gui;

        // kollar om spelet ska avslutas
        if((this.info.getSuspect().getRoute().isEmpty())) {
            this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "lost"));
        }
        else {
            updateModels();

            ArrayDeque<locationButton> cityButtons = showLocations(map.cities());
            SwingUtilities.invokeLater(() -> {
                try {
                    updateView(this.gui.printOverWorld(cityButtons,
                            new ImageIcon(IOHelper.instance.readImageFile("overWorld.jpg"))));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    /**
     * Function used when repainting the GUI.
     *
     * @param gui the GUI-object to which the window is created with, and is to be updated with.
     */
    @Override
    public void updateView(GUI gui) {
        gui.repaint();
    }

    /**
     * {@inheritDoc}
     *
     * Calls for the overlay to update by using the ActionListener.
     */
    @Override
    public void updateModels() {
        // kollar vilket prompt som ska skrivas ut
        String prompt = "filler"+(int)(Math.random()*2+1);

        // varje 5:te dag så slår mördaren till i en ny stad, vilket ska rapporteras i overlay:en
        if((this.info.getDayCount() % 5 == 0)) {
            int crimeScene = this.info.getSuspect().getNext();

            // hämtar den attackerade stadens namn med stream
            prompt = "crime:" + map.cities().stream().filter(city -> city.getLocationEvent().equals("city " + crimeScene))
                        .map(abstractLocation::getLocationName).findFirst().orElse(null);

        }
        // uppdaterar overlay med ett prompt
        this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ("prompt." + prompt)));

        // uppdaterar overlay:ens datum
        this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "updateDate"));

        // uppdaterar spelarens position
        this.info.getPlayer().resetLocation();
    }

    /**
     * Creates locationButtons for concrete locations (city/area)
     *
     * @param locations Array of locations
     * @return Array of locationButton
     */
    private ArrayDeque<locationButton> showLocations(ArrayDeque<abstractLocation> locations) {
        ArrayDeque<locationButton> cityButtons = new ArrayDeque<>();
        ArrayDeque<String> visitedCities = this.info.getVisitedCities();

        for(abstractLocation c: locations) {
            locationButton button = new locationButton(c.getLocationName(), c.getLocationEvent());
            button.addActionListener(e -> {
                this.gui.clearScreen();

                this.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, button.getLocationEvent()));
            });
            if(visitedCities.contains(c.getLocationEvent())) {
                button.setForeground(Color.RED);
            }
            cityButtons.add(button);
        }

        return cityButtons;
    }

    /**
     * Handles events triggered on the overWorld.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // kollar från vilken typ av plats som ska ritas ut
        if(e.getActionCommand().contains("city")) {
            // hämtar specifika staden
            abstractLocation locations = map.cities().stream().filter(location ->
                            e.getActionCommand().equals(location.getLocationEvent()))
                    .findAny().orElse(null);

            // ritar ut stadens platser
            if(locations!=null) {
                if (locations.getClass().equals(city.class)) {
                    this.info.getPlayer().setCurrentLocation(Integer.parseInt(locations.getLocationEvent()
                            .substring(locations.getLocationEvent().indexOf(" ")+1)));

                    // skapar locationButton:s till stadens 'area':s
                    ArrayDeque<locationButton> arr = showLocations(((city) locations).getAreas());

                    // ritar ut nya knapparna på GUI:n
                    SwingUtilities.invokeLater(() -> {
                        try {
                            updateView(this.gui.printCity(arr, new ImageIcon(IOHelper.instance.readImageFile("city.jpg"))));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    });
                    // tömmer overlay
                    this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "clearOverlay"));
                }
            }
        }
        else if(e.getActionCommand().equals("updateDate")) { // uppdaterar datumet i overlay:en
            this.info.updateDate();
            this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Day: "+this.info.getDayCount()));
        }
        else if(e.getActionCommand().contains("fightDone")) {
            // uppdaterar spelarens info
            this.info.setPlayerHealth(Integer.parseInt(e.getActionCommand()
                    .substring(e.getActionCommand().indexOf(".")+1)));

            // kollar om spelaren lever
            if(this.info.getPlayer().getHealth()<=0) {
                this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "lost"));
            }
            // kollar om mördaren lever
            else if(this.info.getSuspect().getHealth()<=0) {
                this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "won"));
            }
            else {
                this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "updateStats"));

                // tömmer overlay
                this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "clearOverlay"));

                SwingUtilities.invokeLater(() ->  {
                    this.gui.clearScreen();
                    doWork(this.gui);
                });
            }

        }
        // areas
        else {
            // ritar ut area-bakgrund
            SwingUtilities.invokeLater(() -> {
                try {
                    this.gui.addBackground(new ImageIcon(IOHelper.instance.readImageFile("area.jpg")));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            // uppdaterar overlay så att den skriver ut event-text
            String enemyType = "punks";
            if(this.info.getPlayer().getCurrentLocation() == this.info.getSuspect().getNext()) {
                this.info.getSuspect().setScaledStats(this.info.getDayCount());
                enemyType = "suspect";
                this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                        "event."+enemyType));

            } else {
                this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
                        "event."+e.getActionCommand()));
            }

            // uppdaterar stats
            switch (e.getActionCommand()) {
                case ("empty") -> {
                    new battleController(this, this.info, enemyType).doWork(this.gui);
                    this.info.getPlayer().powerUp("attack", 2);
                    this.info.getPlayer().powerUp("defense", 2);
                    this.info.getPlayer().powerUp("speed", 2);
                }
                case ("powerUpAttack") -> this.info.getPlayer().powerUp("attack", 4);
                case ("powerUpDefense") -> this.info.getPlayer().powerUp("defense", 4);
                case ("powerUpSpeed") -> this.info.getPlayer().powerUp("speed", 4);
                case ("powerUpHealth") -> this.info.getPlayer().powerUp("health", 10);
            }
            // avslutar vanliga event efter 3 sekunder
            if(!e.getActionCommand().contains("empty")) {
                Timer timer = new Timer(3000, e12 -> {
                    this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "clearOverlay"));
                    this.listener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "updateStats"));
                    doWork(this.gui);
                });
                timer.setRepeats(false);

                timer.start();
            }
        }
    }
}
