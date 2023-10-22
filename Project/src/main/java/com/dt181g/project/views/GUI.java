package com.dt181g.project.views;

import com.dt181g.project.views.customUI.locationButton;
import com.dt181g.project.views.customUI.fightGUI;
import com.dt181g.project.views.customUI.overlayGUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.ArrayList;

/**
 * View class extending the JFrame-class, representing
 * the main window that the program is displayed in.
 *
 * @author josef Alirani
 */
public class GUI extends JFrame {
    private overlayGUI overlay;
    private final JPanel mainContent;

    /**
     * Constructor for the class, initializes the frame's
     * settings and components.
     */
    public GUI() {
        super("RougeLike Detective game");
        this.setSize(800,600);
        this.getContentPane().setBackground(Color.BLACK);

        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        this.mainContent = new JPanel();
        this.mainContent.setLayout(null);
        this.mainContent.setBounds(100,50,600, 300);
        this.mainContent.setBackground(Color.BLACK);
        this.mainContent.setVisible(false);
        this.mainContent.setOpaque(false);

        this.setVisible(true);
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Function used to add a JPanel to the window.
     *
     * @param panel the JPanel to add.
     * @return instance of this GUI class.
     */
    public GUI addGUIComponent(JPanel panel) {
        if(panel.getClass().equals(overlayGUI.class)) {
            this.overlay = (overlayGUI) panel;

            this.add(this.overlay, BorderLayout.SOUTH);
            this.setVisible(true);
        }
        else { // fight
            fightGUI fightGui = (fightGUI) panel;
            this.mainContent.add(fightGui);
            this.mainContent.setVisible(true);
            this.mainContent.repaint();
            this.add(mainContent);
        }

        this.setVisible(true);
        this.repaint();
        return this;
    }

    /**
     * Function used to clear the contents of the window.
     */
    public void clearScreen() {
        this.getContentPane().removeAll();
        this.setLayout(null);

        if(this.mainContent.getComponentCount()>0) {
            this.mainContent.removeAll();
            this.mainContent.setLayout(null);
            this.mainContent.setOpaque(false);
            this.add(mainContent);
        }
        if(this.overlay !=null) {
            addGUIComponent(this.overlay);
        }

        this.repaint();
    }

    /**
     * Function used to remove all components from the window.
     */
    public void disposeAll() {
        for(Component c: this.getComponents()) {
            this.remove(c);
        }
    }

    /**
     * Function used to print the menu of the program.
     *
     * @param start the 'start game' JButton.
     * @param instructions the 'instructions' JButton.
     * @param exit the 'exit program' JButton.
     * @return instance of this GUI class.
     */
    public GUI printMenu(JButton start, JButton instructions, JButton exit) {
        this.setLayout(new GridLayout(3, 1, 20, 20));

        this.add(start);
        this.add(instructions);
        this.add(exit);

        this.setVisible(true);

        return this;
    }

    /**
     * Function used to print the instruction-screen on the window.
     *
     * @param instructions Array of Strings containing the instructions.
     * @param back JButton used to return back to the menu-screen.
     * @return instance of this GUI class.
     */
    public GUI printInstructions(ArrayList<String> instructions,JButton back){
        this.setLayout(new GridLayout(instructions.size()+1, 1));

        for(String instr: instructions) {
            JLabel i = new JLabel(instr);
            i.setHorizontalAlignment(SwingConstants.CENTER);
            i.setForeground(Color.WHITE);
            this.add(i);
        }

        this.add(back);

        this.setVisible(true);
        return this;
    }

    /**
     * Function used to print the overWorld of the game.
     *
     * @param buttons locationButtons representing the cities of the game.
     * @param backgroundImage the background image of the overWorld.
     * @return instance of this GUI class.
     */
    public GUI printOverWorld(ArrayDeque<locationButton> buttons, ImageIcon backgroundImage) {
        JLabel bg = new JLabel(backgroundImage);
        bg.setBounds(0, 0, this.getWidth(), 400);
        bg.setOpaque(true);
        bg.setVisible(true);
        this.setContentPane(bg);

        this.mainContent.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;
        int x = 0; int y = 0; int iterationCounter = 0;
        for(locationButton button: buttons) {
            if(iterationCounter%3 == 0) {
                y++;
                x = 0;
            } else {
                x++;
            }
            constraints.gridx = x;
            constraints.gridy = y;
            this.mainContent.add(button, constraints);
            iterationCounter++;
        }

        this.mainContent.setVisible(true);
        this.add(mainContent);

        this.setVisible(true);
        return this;
    }

    /**
     * Function used to add a background image to the window.
     *
     * @param backgroundImage the background image to add.
     */
    public void addBackground(ImageIcon backgroundImage) {
        JLabel bg = new JLabel(backgroundImage);
        bg.setBounds(0, 0, this.getWidth(), 400);
        bg.setOpaque(true);
        bg.setVisible(true);

        this.setContentPane(bg);
    }

    /**
     * Function used to print a city in the game to the window.
     *
     * @param buttons locationButtons representing teh city's areas.
     * @param backgroundImage the background image of the city.
     * @return instance of this GUI class.
     */
    public GUI printCity(ArrayDeque<locationButton> buttons, ImageIcon backgroundImage) {
        JLabel bg = new JLabel(backgroundImage);
        bg.setBounds(0, 0, this.getWidth(), 400);
        bg.setOpaque(true);
        bg.setVisible(true);

        this.setContentPane(bg);

        this.mainContent.setLayout(new GridLayout(3, 3, 50, 50));
        for(locationButton button: buttons) {
            this.mainContent.add(button);
        }

        this.mainContent.setVisible(true);
        this.add(mainContent);

        this.setVisible(true);

        return this;
    }

    /**
     * Function used to print the game over screen to the window.
     *
     * @param result boolean representing whether the player won or not.
     * @return instance of this GUI class.
     */
    public GUI printGameOver(boolean result) {
        this.mainContent.removeAll();
        this.remove(this.overlay);
        this.remove(this.mainContent);
        this.overlay = null;

        JLabel bg = new JLabel();
        bg.setBackground(Color.BLACK);
        bg.setOpaque(true);
        this.setContentPane(bg);

        this.getContentPane().removeAll();
        this.setLayout(new GridLayout(1,1));
        JLabel conclusion = new JLabel();
        conclusion.setHorizontalAlignment(SwingConstants.CENTER);
        conclusion.setForeground(Color.YELLOW);
        if(result) {
            conclusion.setText("You won!");
        }else {
            conclusion.setText("You lost...");
        }
        this.add(conclusion);

        this.setVisible(true);
        this.repaint();
        return this;
    }
}
