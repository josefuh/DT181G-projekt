package views;

import models.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayDeque;
import java.util.Objects;

public class GUI extends JFrame {

    private final JPanel mainContent;

    private final JPanel statistics;
    public GUI() {
        super("apexSim");

        this.setSize(800,600);
        this.getContentPane().setBackground(Color.BLACK);

        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        this.mainContent = new JPanel();
        this.mainContent.setLayout(null);
        this.mainContent.setBounds(0,0,500, 500);
        this.mainContent.setBackground(Color.BLACK);

        this.statistics = new JPanel();
        this.statistics.setLayout(null);
        this.statistics.setBounds(550,0,200, 400);
        this.statistics.setBackground(Color.ORANGE);

        this.add(mainContent);
        this.add(statistics);
        this.setVisible(true);
        Toolkit.getDefaultToolkit().sync();
    }

    public void printGame(ArrayDeque<Coordinate> map) {
        mainContent.removeAll();
        mainContent.revalidate();

        mainContent.setLayout(new GridLayout(60,60));

        int iteratorCount= 0;
        byte colorChoice = 0;
        Color[] colors = {Color.PINK, Color.CYAN, Color.GREEN, Color.ORANGE, Color.MAGENTA, Color.WHITE,
        Color.YELLOW, Color.GRAY}; // TODO lägg till fler färger

        for(Coordinate c: map) {
            JLabel t = new JLabel();
            if(Objects.equals(c.t(), "O")) {
                t.setForeground(colors[colorChoice]);

                iteratorCount++;
                if(iteratorCount%3==0) {
                    colorChoice++;
                }
            } else if(Objects.equals(c.t(), "#")) {
                t.setForeground(Color.BLUE);
            } else {
                t.setForeground(Color.DARK_GRAY);
            }
            t.setText(String.valueOf(c.t()));
            mainContent.add(t);
        }
        this.add(mainContent);
        this.repaint();
    }
}
