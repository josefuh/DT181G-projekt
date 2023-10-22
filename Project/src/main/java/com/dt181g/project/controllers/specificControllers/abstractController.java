package com.dt181g.project.controllers.specificControllers;

import com.dt181g.project.views.GUI;

/**
 * Interface used by controllers to have access to
 * specific functions, that can be run regardless of the
 * type of controller.
 */
public interface abstractController {

    /**
     * Function used by controllers to perform a repeatable action.
     *
     * @param gui the GUI-object to which the window is created with, and is to be updated with.
     */
    void doWork(GUI gui);

    /**
     * Function used by controllers to update the UI in views.
     *
     * @param gui the GUI-object to which the window is created with, and is to be updated with.
     */
    void updateView(GUI gui);

    /** Function used by controllers to update the data in models.   */
    void updateModels();

}
