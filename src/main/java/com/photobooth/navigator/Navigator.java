package com.photobooth.navigator;

import com.photobooth.controller.AppController;
import com.photobooth.controller.spec.AnimationInitializable;
import com.photobooth.controller.spec.TemplateAndPhotoInitializable;
import com.photobooth.model.StateDef;
import com.photobooth.templateEdytor.panels.TopPanel;
import com.photobooth.templateEdytor.serializable.TemplateData;
import com.photobooth.util.FileUtils;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.io.*;
import java.util.*;

/**
 * Obsługuje przechodzenie pomiędzy poszczególnymi widokami.
 *
 * @author mst
 */
public class Navigator {

    /** Paths to FXML views definitions. */
    public static final String APP_VIEW = "/view/app.fxml";
    public static final String ENCOURAGMENT_VIEW = "/view/encouragment.fxml";
    public static final String TAKE_PHOTO_VIEW = "/view/takephoto.fxml";
    public static final String GALLERY_VIEW = "/view/gallery.fxml";
    public static final String STATE_EDITOR_VIEW = "/view/stateeditor.fxml";
    public static final String TEMPLATE_EDITOR_VIEW = "view/templateeditor.fxml";
    public static final String END_OPTIONS_VIEW = "/view/endoptions.fxml";

    /** Default application flow. Used if there won't be any custom configuration. */
    private static final List<StateDef> DEFAULT_APP_STATES = new ArrayList<StateDef>() {
        {
            add(new StateDef("Koniec", END_OPTIONS_VIEW, "", "swinia"));
            add(new StateDef("Animacja zachety", ENCOURAGMENT_VIEW, "Pierwszy_PIONv2_converted.mp4"));
            add(new StateDef("Robienie fotki", TAKE_PHOTO_VIEW, "odliczanie.mp4"));
            add(new StateDef("Galeria", GALLERY_VIEW, ""));
        }
    };

    private static final String PHOTOS_PATH = "C:\\Users\\Public\\Pictures\\Sample Pictures";

    /** Stores custom application flow. */
    private static List<StateDef> customAppStates = new ArrayList<>();

    private static ListIterator<StateDef> iterator = DEFAULT_APP_STATES.listIterator();

    private static AppController appController;

    private static Stage appContainer;

    public static void nextState() {
        if (iterator.hasNext())
            Navigator.goTo(iterator.next());
    }

    public static void previousState() {
        if (iterator.hasPrevious())
            Navigator.goTo(iterator.previous());
    }

    public static void start() {
        reset();
        nextState();
    }

    /**
     * Change view with new, defined in given {@link StateDef} obejct. If controller connected with given state (view)
     * is instance of {@link AnimationInitializable}, path to animation file displayed in that view will be passed
     * by method {@link AnimationInitializable#initAnimation(String)}.
     *
     * @param stateDefinition {@link StateDef} contains state (view) definition
     */
    private static void goTo(StateDef stateDefinition) {
        FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(stateDefinition.getFxmlViewPath()));

        try {
            appController.setContent(loader.load());
            if (loader.getController() instanceof AnimationInitializable) {
                ((AnimationInitializable) loader.getController()).initAnimation(stateDefinition.getAnimationPath());
            }
            if (loader.getController() instanceof TemplateAndPhotoInitializable) {
                ((TemplateAndPhotoInitializable) loader.getController()).setTemplateAndPhotos(getTemplateDateFromName(
                        stateDefinition.getTemplateName()), FileUtils.getPhotos(PHOTOS_PATH));
            }
        } catch (IOException e) {
            System.out.println("Error loading view: " + stateDefinition.getAnimationPath()
                    + "\n" + e.getMessage());
        }
    }

    /** Resarts application flow. */
    private static void reset() {
        iterator = hasCustomStatesConfiguration() ? customAppStates.listIterator() : DEFAULT_APP_STATES.listIterator();
    }

    private static TemplateData getTemplateDateFromName(String templateName){
        templateName+= ".ser";

        FileInputStream fin = null;
        TemplateData data = null;
        try {
            fin = new FileInputStream(TopPanel.SRC_RESOURCES_TEMPLATES + templateName);
            ObjectInputStream ois = new ObjectInputStream(fin);

            data = (TemplateData) ois.readObject();

            ois.close();
            fin.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    /** Cheks if custom configuration was set. */
    public static boolean hasCustomStatesConfiguration() {
        return !customAppStates.isEmpty();
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

    public static void setCustomStates(List<StateDef> customStates) {
        customAppStates = customStates;
        iterator = customAppStates.listIterator();
    }

    public static Stage getAppContainer() {
        return appContainer;
    }

    public static void setAppController(AppController appController) {
        Navigator.appController = appController;
    }

    public static void setAppContainer(Stage stage) {
        appContainer = stage;
    }
}
