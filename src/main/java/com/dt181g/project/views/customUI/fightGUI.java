package com.dt181g.project.views.customUI;

import javax.swing.*;
import java.awt.*;

/**
 * Custom JPanel used to display information of a fight.
 *
 * @author josef Alirani
 */
public class fightGUI extends JPanel {
    private final JLabel playerHealth;
    private final JLabel enemyHealth;
    private final JLabel fightLog;

    /**
     * Constructor for the class, sets the panel's and its components
     * initial settings.
     */
    public fightGUI() {
        this.setBounds(0,20,600, 200);
        this.setLayout(new GridLayout(3,1));

        this.playerHealth = new JLabel();
        this.playerHealth.setHorizontalAlignment(SwingConstants.CENTER);

        this.enemyHealth = new JLabel("---enemies---");
        this.enemyHealth.setLayout(new FlowLayout());
        this.enemyHealth.setHorizontalAlignment(SwingConstants.CENTER);

        this.fightLog = new JLabel();
        this.fightLog.setBorder(BorderFactory.createLineBorder(Color.GRAY, 5));
        this.fightLog.setHorizontalAlignment(SwingConstants.CENTER);


        this.add(this.enemyHealth);
        this.add(this.fightLog);
        this.add(this.playerHealth);

        this.setVisible(true);
    }

    /**
     * Setter function used to set the JLabel displaying the detective's health.
     *
     * @param textLog String containing the detective's health.
     */
    public void setPlayerHealth(String textLog) {
        this.playerHealth.setText(textLog);
    }

    /**
     * Setter function used to set the JLabel displaying the enemies' health.
     *
     * @param textLog String containing the enemies' health.
     */
    public void setEnemyHealth(String textLog) {
        this.enemyHealth.setText(textLog);
    }

    /**
     * Setter function used to set the JLabel displaying an action performed in the fight.
     *
     * @param textLog String an action performed in the fight.
     */
    public void setFightLog(String textLog) {
        this.fightLog.setText(textLog);
    }

    /**
     * Function used to set the initial message to be displayed in fightLog,
     * changing depending on what type of enemy the detective is facing.
     *
     * @param amount amount of streetPunks that approach.
     * @param enemyType the type of enemy that the detective is preparing to face.
     */
    public void initLog(int amount, String enemyType) {
        if(enemyType.equals("punks")) {
            this.fightLog.setText(amount + " punk(s) are looking for a fight!");
        } else {
            this.fightLog.setText("You prepare to confront the suspected killer!");
        }
    }
}
