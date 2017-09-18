package com.photobooth.navigator;

import com.photobooth.config.Config;
import com.photobooth.controller.AppController;
import com.photobooth.controller.PreviewController;
import com.photobooth.controller.spec.AnimationInitializable;
import com.photobooth.controller.spec.StateFlowConfigurationInitializable;
import com.photobooth.controller.spec.TemplateAndPhotoInitializable;
import com.photobooth.model.Media;
import com.photobooth.model.StateDef;
import com.photobooth.templateEdytor.serializable.TemplateData;
import com.photobooth.util.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Obsługuje przechodzenie pomiędzy poszczególnymi widokami.
 *
 * @author mst
 */
public class Navigator {
    private final static Logger logger = Logger.getLogger(Navigator.class);
    /** Paths to FXML views definitions. */
    public static final String APP_VIEW = "/view/app.fxml";
    public static final String ENCOURAGMENT_VIEW = "/view/encouragement.fxml";
    public static final String PLAY_ONCE_VIEW = "/view/playonce.fxml";
    public static final String TAKE_PHOTO_VIEW = "/view/takephoto.fxml";
    public static final String PREVIEW_VIEW = "/view/preview.fxml";
    public static final String GALLERY_VIEW = "/view/gallery.fxml";
    public static final String STATE_EDITOR_VIEW = "/view/stateeditor.fxml";
    public static final String TEMPLATE_EDITOR_VIEW = "view/templateeditor.fxml";
    public static final String END_OPTIONS_VIEW = "/view/endoptions.fxml";

    /** Default application flow. Used if there won't be any custom configuration. */
    private static final List<StateDef> DEFAULT_APP_STATES = new ArrayList<StateDef>() {
        {
            add(new StateDef("Animacja zęchety", ENCOURAGMENT_VIEW, Collections.singletonList(new Media("Pierwszy_PIONv2_converted.mp4"))));
            add(new StateDef("Robienie fotki", TAKE_PHOTO_VIEW, Collections.singletonList(new Media("odliczanie.mp4"))));
            add(new StateDef("Robienie fotki", TAKE_PHOTO_VIEW, Collections.singletonList(new Media("odliczanie.mp4"))));
            add(new StateDef("Robienie fotki", TAKE_PHOTO_VIEW, Collections.singletonList(new Media("odliczanie.mp4"))));
            add(new StateDef("Galeria", GALLERY_VIEW, Collections.singletonList(null)));
            add(new StateDef("Koniec", END_OPTIONS_VIEW, Collections.singletonList(null), "swinia.ser"));
        }
    };

    /** Stores custom application flow. */
    private static List<StateDef> customAppStates = new ArrayList<>();

    private static ListIterator<StateDef> iterator = DEFAULT_APP_STATES.listIterator();

    private static AppController appController;

    private static Stage appContainer;

    public static void nextState() {
        if (iterator.hasNext())
            Navigator.goTo(iterator.next());
        else start();
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
     * by method {@link AnimationInitializable#initAnimations(List<String>)}.
     *
     * @param stateDefinition {@link StateDef} contains state (view) definition
     */
    private static void goTo(StateDef stateDefinition) {
        Configuration configuration = ConfigurationUtil.initConfiguration();
        FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(stateDefinition.getFxmlViewPath()));

        try {
            appController.setContent(loader.load());
            if (loader.getController() instanceof AnimationInitializable) {
                ((AnimationInitializable) loader.getController()).initAnimations(stateDefinition.getMediaPaths());
            }
            if (loader.getController() instanceof TemplateAndPhotoInitializable) {
                ((TemplateAndPhotoInitializable) loader.getController()).setTemplateAndPhotos(getTemplateDateFromName(
                        stateDefinition.getTemplateName()), FileUtils.getPhotos(configuration.getCurrentPhotosPath()));
            }
        } catch (IOException exception) {
            logger.error("Error loading view: " + stateDefinition.getAnimationPaths()
                    + "\n" + exception.getMessage());
        }
    }

    /** Displays photo preview view after taking photo. After 2-3 seconds state flow will be restored. */
    public static void goToPreview(String photoFilePath) {
        FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(PREVIEW_VIEW));
        try {
            appController.setContent(loader.load());
            PreviewController controller = loader.getController();
            if (controller != null) {
                controller.initPhoto(photoFilePath);
            }
        } catch (IOException exception) {
            System.out.println("Error loading preview view: \n" + exception.getMessage());
        }
    }

    /** Restarts application flow. */
    private static void reset() {
        int errorCount = 0;
        iterator = hasCustomStatesConfiguration() ? customAppStates.listIterator() : DEFAULT_APP_STATES.listIterator();
        moveTempPhotosToArchive(0);
    }

    public static void moveTempPhotosToArchive(int i){
        Configuration configuration = ConfigurationUtil.initConfiguration();
        Path currentPhotoPath = Paths.get(configuration.getCurrentPhotosPath());
        Path archivePhotos = Paths.get(configuration.getArchivePhotosPath());

        List<File> files = Arrays.asList(currentPhotoPath.toFile().listFiles());

        for(File currentFile : files){
            try {
                Files.move(currentFile.toPath(), Paths.get(archivePhotos.toString(), currentFile.getName()), StandardCopyOption.ATOMIC_MOVE);
            } catch (IOException e) {
                logger.error(e);
            }
        }
//        try {
//            Stream<Path> list = Files.list(currentPhotoPath);
//            list.forEach(new Consumer<Path>() {
//                @Override
//                public void accept(Path path) {
//                    try {
//
//                        int index = 1;
//                        Path archivedPhotoPath = Paths.get(archivePhotos.toString(), path.getFileName().toString());
//                        if(Files.exists(archivedPhotoPath)){
//                            archivedPhotoPath = Paths.get(archivePhotos.toString(), path.getFileName().toString().toUpperCase().replace(".JPG", "("+index+").JPG"));
//                        };
//
//                        while (Files.exists(archivedPhotoPath)){
//                            index++;
//                            archivedPhotoPath = Paths.get(archivePhotos.toString(), path.getFileName().toString().toUpperCase().replace("JPG", "("+index+").JPG"));
//                        }
//
//                        Files.move(path, archivedPhotoPath, StandardCopyOption.ATOMIC_MOVE);
//                    } catch (IOException e) {
//                        int i1 = i;
//                        logger.error("Zjebalo sie, poraz : "+ (++ i1) + "  probuje jeszcze raz",e);
//
//                        moveTempPhotosToArchive(++i1);
//                        logger.error(e);
//                    }
//                }
//            });
//        } catch (IOException e) {
//
//            logger.error(e);
//        }
    }

    private static TemplateData getTemplateDateFromName(String templateName){
        TemplateData data1 = null;
        try {
            Configuration configuration = ConfigurationUtil.initConfiguration();

            data1 = TemplateImporter.importTemplateFromDSLRBooth(configuration.getTemplatePath()+templateName);
        } catch (ParserConfigurationException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        } catch (SAXException e) {
            logger.error(e);
        }

        return data1;

    }

    /** Cheks if custom configuration was set. */
    public static boolean hasCustomStatesConfiguration() {
        return !customAppStates.isEmpty();
    }

    /**
     * Changes view directly for given path of FXML file.
     *
     * @param shouldUseDefinedStateFlowConfig if controller should use StateFlowConfiguration or not
     */
    public static void goToStateEditor(boolean shouldUseDefinedStateFlowConfig) {
        FXMLLoader loader = new FXMLLoader(Navigator.class.getResource(Navigator.STATE_EDITOR_VIEW));
        try {
            appController.setContent(loader.load());
            if (shouldUseDefinedStateFlowConfig
                    && loader.getController() instanceof StateFlowConfigurationInitializable) {
                ((StateFlowConfigurationInitializable) loader.getController()).setStateFlowConfiguration(Config.getInstance().getStateFlowConfiguration());
            }
        } catch (IOException e) {
            logger.error(e);
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
