package com.dt181g.project;

import com.dt181g.project.controllers.mainController;

import javax.swing.*;

/**
 * The main starting point for Project Assignment.
 *
 * @author Josef Alirani
 */
public final class Project {

    /** Private constructor for the class. */
    private Project() { // Utility classes should not have a public or default constructor
        throw new IllegalStateException("Utility class");
    }

    /**
     * Main function used to initialize the mainController-class in order to
     * start and manage the program.
     *
     * @param args command arguments.
     */
    public static void main(final String... args) {
        SwingUtilities.invokeLater(mainController::new);
    }
}
