package com.photobooth.navigator;

import com.photobooth.controller.AppController;
import com.photobooth.controller.spec.AnimationInitializable;
import com.photobooth.model.StateDef;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

/**
 * Obsługuje przechodzenie pomiędzy poszczególnymi widokami.
 *
 * @mst
 */
public class Navigator {

    /** Paths to FXML views definitions. */
    public static final String APP_VIEW = "/view/app.fxml";
    public static final String ENCOURAGMENT_VIEW = "/view/encouragment.fxml";
    public static final String TAKE_PHOTO_VIEW = "/view/takephoto.fxml";
    public static final String GALLERY_VIEW = "/view/gallery.fxml";
    public static final String STATE_EDITOR_VIEW = "/view/stateeditor.fxml";
    public static final String TEMPLATE_EDITOR_VIEW = "view/templateeditor.fxml";
    public static final String END_OPTIONS_VIEW = "view/endoptions.fxml";

    /** Default application flow. Used if there won't be any custom configuration. */
    public static final List<StateDef> DEFAULT_APP_STATES = new ArrayList<StateDef>() {
        {
            add(new StateDef("Animacja zachety", ENCOURAGMENT_VIEW, "Pierwszy_PIONv2_converted.mp4"));
            add(new StateDef("Robienie fotki", TAKE_PHOTO_VIEW, "odliczanie.mp4"));
            add(new StateDef("Galeria", GALLERY_VIEW, ""));
        }
    };

    /** Stores custom application flow. */
    private static List<StateDef> customAppStates = new ArrayList<>();

    private static ListIterator<StateDef> iterator = DEFAULT_APP_STATES.listIterator();

    private static AppController appController;

    private static Stage appContainer;

    public static void setAppController(AppController appController) {
        Navigator.appController = appController;
    }

    public static void nextState() {
        if (iterator.hasNext())
            Navigator.goTo(iterator.next());
        //else reset();
    }

    /** Resarts application flow. */
    private static void reset() {
        iterator = hasCustomStatesConfiguration() ? customAppStates.listIterator() : DEFAULT_APP_STATES.listIterator();
    }

    public static void previousState() {
        if (iterator.hasPrevious())
            Navigator.goTo(iterator.previous());
        //else reset();
    }

    public static boolean hasCustomStatesConfiguration() {
        return !customAppStates.isEmpty();
    }

    public static void goTo(StateDef stateDefinition) {
        FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(stateDefinition.getFxmlViewPath()));

        try {
            appController.setContent(loader.load());
            if (loader.getController() instanceof AnimationInitializable) {
                ((AnimationInitializable) loader.getController()).initAnimation(stateDefinition.getAnimationPath());
            }
            //goTo(stateDefinition.getFxmlViewPath());
        } catch (IOException e) {
            System.out.println("Error loading view: " + stateDefinition.getAnimationPath()
                    + "\n" + e.getMessage());
        }
    }

    /**
     * Changes view directly for given path of FXML file.
     *
     * @param fxmlViewPath path of FXML file
     */
    public static void goTo(String fxmlViewPath) {
        try {
            appController.setContent(FXMLLoader.load(Navigator.class.getResource(fxmlViewPath)));
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public static ListIterator<StateDef> getStates() {
        List<StateDef> states = hasCustomStatesConfiguration() ? customAppStates : DEFAULT_APP_STATES;
        return states.listIterator();
    }

    public static void setCustomStates(List<StateDef> customStates) {
        customAppStates = customStates;
        iterator = customAppStates.listIterator();
    }

    public static Stage getAppContainer() {
        return appContainer;
    }

    public static void setAppContainer(Stage stage) {
        appContainer = stage;
    }
}
