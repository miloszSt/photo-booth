package com.photobooth.config;

import com.photobooth.util.FileUtils;
import com.photobooth.util.StateFlowConfiguration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;


public class ConfigReader {

    private static final String STATE_FLOW_CONFIG_PATH = "/photoBooth/stateFlows/config.xml";

    public static boolean hasStateFlowConfigurationDefined() {
        return FileUtils.checkIfFileExistUnderProjectCatalog(STATE_FLOW_CONFIG_PATH);
    }

    public static StateFlowConfiguration readStateFlowCOnfiguration() throws JAXBException {
        File config = new File(STATE_FLOW_CONFIG_PATH);
        JAXBContext jaxbContext = JAXBContext.newInstance(StateFlowConfiguration.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        return (StateFlowConfiguration) jaxbUnmarshaller.unmarshal(config);
    }
}
